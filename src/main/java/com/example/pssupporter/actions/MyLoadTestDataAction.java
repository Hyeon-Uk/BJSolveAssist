/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.actions;

import com.example.pssupporter.utils.thread.MyThreadStore;
import com.example.pssupporter.utils.thread.vo.ThreadGroupName;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class MyLoadTestDataAction extends AnAction {
  private final MyThreadStore myThreadStore;

  public MyLoadTestDataAction(MyThreadStore myThreadStore) {
    super("Load Test DataSets", "This action can load test data sets", AllIcons.Actions.Find);
    this.myThreadStore = myThreadStore;
  }

  @Override
  public void update(@NotNull AnActionEvent e) {
    super.update(e);
    boolean isRunning = myThreadStore
            .hasRunningThreads(ThreadGroupName.TEST_RUNNING);
    e.getPresentation().setEnabled(!isRunning);
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    Messages.showMessageDialog("Currently, this feature is not available because Baekjun has banned scraping.\nReference : <a href=\"https://help.acmicpc.net/rule\">BOJ Homepage</a>", "Caution", AllIcons.Actions.Exit);
  }
}
