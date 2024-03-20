/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.actions;

import com.example.pssupporter.ui.editor.EditorPanel;
import com.example.pssupporter.ui.list.TestListPanel;
import com.example.pssupporter.utils.thread.MyThreadStore;
import com.example.pssupporter.utils.thread.vo.ThreadGroupName;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class MyRemoveTestAction extends AnAction {
  private final TestListPanel myTestListPanel;
  private final EditorPanel myEditorPanel;
  private final MyThreadStore myThreadStore;

  public MyRemoveTestAction(TestListPanel myTestListPanel, EditorPanel myEditorPanel, MyThreadStore myThreadStore) {
    super("Remove Test Data Set", "This action can remove test data set", AllIcons.General.Remove);
    this.myTestListPanel = myTestListPanel;
    this.myEditorPanel = myEditorPanel;
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
    int selectedIndex = myTestListPanel.getSelectedIndex();
    if (selectedIndex != -1) {
      myEditorPanel.clearAll();
      myTestListPanel.removeTestData(selectedIndex);
    }
  }
}
