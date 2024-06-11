/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils.thread;

import com.example.pssupporter.utils.thread.vo.ThreadGroupName;

import java.util.concurrent.ThreadPoolExecutor;

public interface ThreadStore {
  ThreadPoolExecutor getThreadExecutorByName(ThreadGroupName groupName);

  void executeThreads(ThreadGroupName groupName, Thread... threads);

  void interruptThreads(ThreadGroupName groupName);

  boolean hasRunningThreads(ThreadGroupName groupName);
}
