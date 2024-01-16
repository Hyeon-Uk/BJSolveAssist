/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.actions;

import com.example.pssupporter.ui.MyTestListPanel;
import com.example.pssupporter.utils.ComponentManager;
import com.example.pssupporter.utils.thread.GlobalThreadStore;
import com.example.pssupporter.utils.thread.vo.ThreadGroupName;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class MyRemoveTestAction extends AnAction {
  @Override
  public void update(@NotNull AnActionEvent e) {
    super.update(e);
    boolean isRunning = GlobalThreadStore.getInstance()
            .hasRunningThreads(ThreadGroupName.TEST_RUNNING);
    e.getPresentation().setEnabled(!isRunning);
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    MyTestListPanel myTestListPanel = ComponentManager.getInstance().getComponent("myTestListPanel", MyTestListPanel.class);
    int selectedIndex = myTestListPanel.getSelectedIndex();
    if (selectedIndex != -1) {
      ComponentManager.getInstance().removeChildrenComponentsByName("myEditorPanel");

      myTestListPanel.removeTest(selectedIndex);
      myTestListPanel.repaint();
    }
  }
}
