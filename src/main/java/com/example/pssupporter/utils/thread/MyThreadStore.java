/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils.thread;

import com.example.pssupporter.utils.thread.vo.ThreadGroupName;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * This class will support you to maintain and run threads group easier
 */
public class MyThreadStore {
  private final Map<ThreadGroupName, ThreadPoolExecutor> myThreadStore;

  public MyThreadStore() {
    myThreadStore = new EnumMap<>(ThreadGroupName.class);

    Arrays.stream(ThreadGroupName.values()).forEach(tg -> myThreadStore.put(tg, tg.getThreadPoolExecutor()));
  }

  /**
   * This method returns threadPoolExecutor defined in ThreadGroupName.
   * When the threadPoolExecutor is shutdowned, a new threadPoolExecutor that defines in ThreadGroupName will return
   *
   * @param groupName ThreadGroupName enum value
   * @return ThreadPoolExecutor It defines in ThreadGroupName.
   * @throws IllegalArgumentException Throw error when the param is not a value defined in ThreadGroupName.
   */
  public synchronized ThreadPoolExecutor getThreadExecutorByName(@NotNull ThreadGroupName groupName) {
    ThreadPoolExecutor threadPoolExecutor = Optional.ofNullable(myThreadStore.get(groupName))
            .orElseThrow(() -> new IllegalArgumentException("Not Exist GroupName"));

    if (threadPoolExecutor.isShutdown() || threadPoolExecutor.isTerminated() || threadPoolExecutor.isTerminating()) {
      ThreadPoolExecutor newThreadPoolExecutor = groupName.getThreadPoolExecutor();
      myThreadStore.put(groupName, newThreadPoolExecutor);
      return newThreadPoolExecutor;
    } else {
      return threadPoolExecutor;
    }

  }

  /**
   * This method executes threads with ThreadPoolExecutor defined by ThreadGroupName.
   *
   * @param groupName Value of the ThreadGroup from which you want to run the thread
   * @param threads   Threads to run
   */
  public void executeThreads(@NotNull ThreadGroupName groupName, @NotNull Thread... threads) {
    ThreadPoolExecutor threadPoolExecutor = getThreadExecutorByName(groupName);
    for (Thread thread : threads) {
      threadPoolExecutor.execute(thread);
    }
  }

  /**
   * This method interrupts threads by groupName.
   *
   * @param groupName Value of the ThreadGroup from which you want to interrupt the thread group.
   */
  public void interruptThreads(@NotNull ThreadGroupName groupName) {
    ThreadPoolExecutor threadPoolExecutor = getThreadExecutorByName(groupName);
    threadPoolExecutor.shutdownNow();
  }

  /**
   * This method returns true/false value of whether there is a running thread.
   *
   * @param groupName GroupName to see if there are running threads
   * @return True/false value for whether there are running threads in the thread group corresponding to the parameter
   */
  public boolean hasRunningThreads(@NotNull ThreadGroupName groupName) {
    return getThreadExecutorByName(groupName)
            .getActiveCount() > 0;
  }
}
