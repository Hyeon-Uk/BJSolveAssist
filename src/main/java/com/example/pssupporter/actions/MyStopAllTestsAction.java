/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.actions;

import com.example.pssupporter.ui.list.TestListPanel;
import com.example.pssupporter.utils.thread.MyThreadStore;
import com.example.pssupporter.utils.thread.vo.ThreadGroupName;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class MyStopAllTestsAction extends AnAction {
  private final TestListPanel myTestListPanel;
  private final MyThreadStore myThreadStore;

  public MyStopAllTestsAction(TestListPanel myTestListPanel, MyThreadStore myThreadStore) {
    super("Stop All Running Tests", "This action can stop all tests", AllIcons.Actions.Suspend);
    this.myTestListPanel = myTestListPanel;
    this.myThreadStore = myThreadStore;
  }

  @Override
  public void update(@NotNull AnActionEvent e) {
    super.update(e);
    boolean isRunning = myThreadStore
            .hasRunningThreads(ThreadGroupName.TEST_RUNNING);
    e.getPresentation().setEnabled(isRunning);

    myTestListPanel.setEnabled(!isRunning);
  }

  @Override
  public void actionPerformed(AnActionEvent e) {
    myThreadStore
            .interruptThreads(ThreadGroupName.TEST_RUNNING);
  }
}
