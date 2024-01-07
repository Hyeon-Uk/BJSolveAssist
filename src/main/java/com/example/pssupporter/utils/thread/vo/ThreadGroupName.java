/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils.thread.vo;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public enum ThreadGroupName {
  TEST_RUNNING {
    @Override
    public @NotNull ThreadPoolExecutor getThreadPoolExecutor() {
      return new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
    }
  };

  @NotNull
  public abstract ThreadPoolExecutor getThreadPoolExecutor();
}