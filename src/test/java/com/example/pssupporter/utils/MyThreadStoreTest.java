/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils;

import com.example.pssupporter.utils.thread.MyThreadStore;
import com.example.pssupporter.utils.thread.vo.ThreadGroupName;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MyThreadStoreTest {
  class TestThread extends Thread {
    private boolean flag = true;

    @Override
    public void run() {
      while (flag) {
      }
    }

    @Override
    public void interrupt() {
      flag = false;
      super.interrupt();
    }
  }

  private MyThreadStore myThreadStore;

  @BeforeEach
  void init() {
    myThreadStore = new MyThreadStore();
  }

  @AfterEach
  void shutdownAllExecutor() {
    Arrays.stream(ThreadGroupName.values()).forEach(tg -> {
      myThreadStore.getThreadExecutorByName(tg).shutdownNow();
    });
  }

  @Nested
  @DisplayName("[GetThreadExecutorByName]")
  class GetThreadExecutorByNameTest {
    @Test
    @DisplayName("Success : Getting ThreadExecutor by name")
    void success() {
      ThreadGroupName groupName = ThreadGroupName.TEST_RUNNING;
      ThreadPoolExecutor threadPoolExecutor = myThreadStore.getThreadExecutorByName(groupName);
      assertNotNull(threadPoolExecutor);
      assertEquals(0, threadPoolExecutor.getActiveCount());
      assertFalse(threadPoolExecutor.isTerminated());
      assertFalse(threadPoolExecutor.isTerminating());
      assertFalse(threadPoolExecutor.isShutdown());
    }

    @Test
    @DisplayName("Success : Getting same thread executor when executor not shutdowned or terminated")
    void getSameThreadExecutorWhenWasNotShutdownedOrTerminated_Success() {
      ThreadGroupName groupName = ThreadGroupName.TEST_RUNNING;
      ThreadPoolExecutor threadPoolExecutor1 = myThreadStore.getThreadExecutorByName(groupName);
      ThreadPoolExecutor threadPoolExecutor2 = myThreadStore.getThreadExecutorByName(groupName);

      assertEquals(threadPoolExecutor1, threadPoolExecutor2);
    }

    @Test
    @DisplayName("Success : Getting same thread executor when executor working")
    void getSameThreadExecutorWhenWorking_Success() {
      ThreadGroupName groupName = ThreadGroupName.TEST_RUNNING;
      ThreadPoolExecutor threadPoolExecutor1 = myThreadStore.getThreadExecutorByName(groupName);
      Thread testThread = new TestThread();

      threadPoolExecutor1.execute(testThread);
      assertEquals(1, threadPoolExecutor1.getActiveCount());

      ThreadPoolExecutor threadPoolExecutor2 = myThreadStore.getThreadExecutorByName(groupName);

      assertEquals(threadPoolExecutor1, threadPoolExecutor2);
      assertEquals(1, threadPoolExecutor2.getActiveCount());

      testThread.interrupt();
      threadPoolExecutor1.shutdownNow();
    }

    @Test
    @DisplayName("Fail : Trying to get with not exist GroupName")
    void withNotExistGroupName_Failure() throws NoSuchFieldException, IllegalAccessException {
      MyThreadStore instance = myThreadStore;
      Field myThreadStoreField = MyThreadStore.class.getDeclaredField("myThreadStore");
      myThreadStoreField.setAccessible(true);
      EnumMap<ThreadGroupName, ThreadPoolExecutor> origin = (EnumMap<ThreadGroupName, ThreadPoolExecutor>) myThreadStoreField.get(instance);
      try {

        //Temporary change to mocking object for testing
        EnumMap mockEnumMap = Mockito.mock(EnumMap.class);

        myThreadStoreField.set(instance, mockEnumMap);

        when(mockEnumMap.get(any(ThreadGroupName.class)))
                .thenReturn(null);

        // TEST_RUNING assumes that the value is not defined
        assertThrows(IllegalArgumentException.class, () -> instance.getThreadExecutorByName(ThreadGroupName.TEST_RUNNING));

      } finally {
        myThreadStoreField.set(instance, origin);
        myThreadStoreField.setAccessible(false);
      }
    }
  }

  @Nested
  @DisplayName("[ExecuteThreads]")
  class ExecuteThreadsTest {
    @Test
    @DisplayName("Success : execute only one thread")
    void executeOneThread_Success() {
      ThreadGroupName groupName = ThreadGroupName.TEST_RUNNING;
      MyThreadStore instance = myThreadStore;
      TestThread testThread = new TestThread();

      instance.executeThreads(groupName, testThread);

      ThreadPoolExecutor executor = instance.getThreadExecutorByName(groupName);
      assertEquals(1, executor.getActiveCount());

      testThread.interrupt();
    }

    @Test
    @DisplayName("Success : execute lots of threads")
    void executeLotsOfThreads_Success() {
      TestThread[] threads = new TestThread[10];
      for (int i = 0; i < threads.length; i++) {
        threads[i] = new TestThread();
      }
      MyThreadStore instance = myThreadStore;
      ThreadGroupName groupName = ThreadGroupName.TEST_RUNNING;
      instance.executeThreads(groupName, threads);

      ThreadPoolExecutor executor = instance.getThreadExecutorByName(groupName);
      assertEquals(threads.length, executor.getActiveCount());
    }
  }

  @Nested
  @DisplayName("[InterruptThreads]")
  class InterruptThreadsTest {
    @Test
    @DisplayName("Success : interrupt all threads by groupName")
    void interruptAllThreads_Success() {
      ThreadGroupName groupName = ThreadGroupName.TEST_RUNNING;
      MyThreadStore instance = myThreadStore;
      ThreadPoolExecutor executor = instance.getThreadExecutorByName(groupName);

      Thread[] testThreads = new TestThread[10];
      for (int i = 0; i < testThreads.length; i++) {
        testThreads[i] = new TestThread();
      }

      instance.executeThreads(groupName, testThreads);
      assertEquals(testThreads.length, executor.getActiveCount());


      instance.interruptThreads(groupName);
      assertTrue(executor.isShutdown());

      executor = instance.getThreadExecutorByName(groupName);
      assertEquals(0, executor.getActiveCount());
    }
  }

  @Nested
  @DisplayName("[HasRunningThreads]")
  class HasRunningThreadsTest {
    @Test
    @DisplayName("Success : Return false when running threads not exist")
    void returnFalseWhenNotExistRunningThreads_Success() {
      MyThreadStore instance = myThreadStore;
      ThreadGroupName groupName = ThreadGroupName.TEST_RUNNING;

      assertFalse(instance.hasRunningThreads(groupName));
    }

    @Test
    @DisplayName("Success : Return true when running threads exists")
    void returnTrueWhenExistsRunningThreads_Success() {
      MyThreadStore instance = myThreadStore;
      ThreadGroupName groupName = ThreadGroupName.TEST_RUNNING;

      instance.executeThreads(groupName, new TestThread());

      assertTrue(instance.hasRunningThreads(groupName));
    }

    @Test
    @DisplayName("Success : Return false when shutdown executor")
    void returnFalseWhenShutdown_Success() {
      MyThreadStore instance = myThreadStore;
      ThreadGroupName groupName = ThreadGroupName.TEST_RUNNING;

      instance.executeThreads(groupName, new TestThread());
      assertTrue(instance.hasRunningThreads(groupName));

      instance.interruptThreads(groupName);
      assertFalse(instance.hasRunningThreads(groupName));
    }
  }
}