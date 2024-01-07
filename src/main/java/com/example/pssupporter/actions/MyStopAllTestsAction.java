/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.actions;

import com.example.pssupporter.ui.MyTestListPanel;
import com.example.pssupporter.utils.thread.GlobalThreadStore;
import com.example.pssupporter.utils.thread.vo.ThreadGroupName;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class MyStopAllTestsAction extends AnAction {
  @Override
  public void update(@NotNull AnActionEvent e) {
    super.update(e);
    boolean isRunning = GlobalThreadStore.getInstance()
            .hasRunningThreads(ThreadGroupName.TEST_RUNNING);
    e.getPresentation().setEnabled(isRunning);

    MyTestListPanel.getInstance().setEnabled(!isRunning);
  }

  @Override
  public void actionPerformed(AnActionEvent e) {
    GlobalThreadStore.getInstance()
            .interruptThreads(ThreadGroupName.TEST_RUNNING);
  }
}
