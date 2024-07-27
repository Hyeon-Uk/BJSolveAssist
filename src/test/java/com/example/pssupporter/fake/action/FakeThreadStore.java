/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.fake.action;

import com.example.pssupporter.utils.thread.ThreadStore;
import com.example.pssupporter.utils.thread.vo.ThreadGroupName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FakeThreadStore implements ThreadStore {
  private Map<ThreadGroupName, List<Thread>> store;

  public FakeThreadStore(Map<ThreadGroupName, List<Thread>> store) {
    this.store = store;
  }

  @Override
  public ThreadPoolExecutor getThreadExecutorByName(ThreadGroupName groupName) {
    return new ThreadPoolExecutor(1, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
  }

  @Override
  public void executeThreads(ThreadGroupName groupName, Thread... threads) {
    List<Thread> threadsList = Arrays.stream(threads).toList();
    System.out.println("before = "+store.get(groupName));
    List<Thread> origin = store.getOrDefault(groupName, new ArrayList<>());
    origin.addAll(threadsList);
    store.put(groupName, origin);
    System.out.println("after = "+store.get(groupName));
  }

  @Override
  public void interruptThreads(ThreadGroupName groupName) {
    store.put(groupName, new ArrayList<>());
  }

  @Override
  public boolean hasRunningThreads(ThreadGroupName groupName) {
    return !store.getOrDefault(groupName, new ArrayList<>()).isEmpty();
  }
}
