/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.actions;

import com.example.pssupporter.ui.editor.MyEditorPanel;
import com.example.pssupporter.ui.list.MyTestListItem;
import com.example.pssupporter.ui.list.MyTestListPanel;
import com.example.pssupporter.ui.main.MyMainView;
import com.example.pssupporter.utils.ComponentManager;
import com.example.pssupporter.utils.IntellijUtils;
import com.example.pssupporter.utils.runner.CodeRunner;
import com.example.pssupporter.utils.runner.CodeRunnerProvider;
import com.example.pssupporter.utils.runner.vo.CodeLanguage;
import com.example.pssupporter.utils.thread.GlobalThreadStore;
import com.example.pssupporter.utils.thread.TestRunningThread;
import com.example.pssupporter.utils.thread.vo.ThreadGroupName;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MyRunTestAction extends AnAction {
  @Override
  public void update(@NotNull AnActionEvent e) {
    super.update(e);
    boolean isRunning = GlobalThreadStore.getInstance()
            .hasRunningThreads(ThreadGroupName.TEST_RUNNING);
    e.getPresentation().setEnabled(!isRunning);

    MyTestListPanel myTestListPanel = ComponentManager.getInstance().getComponent("myTestListPanel", MyTestListPanel.class);
    myTestListPanel.setEnabled(!isRunning);
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    MyTestListPanel myTestListPanel = ComponentManager.getInstance().getComponent("myTestListPanel", MyTestListPanel.class);
    MyEditorPanel myEditorPanel = ComponentManager.getInstance().getComponent("myEditorPanel", MyEditorPanel.class);
    MyMainView myMainView = ComponentManager.getInstance().getComponent("myMainView", MyMainView.class);
    int selectedIndex = myTestListPanel.getSelectedIndex();
    if (selectedIndex == -1) {
      return;
    }

    myMainView.saveAndClear();

    myEditorPanel.clearAll();
    myTestListPanel.clearSelection();

    MyTestListItem selectedItem = myTestListPanel.getMyTestList(selectedIndex);
    IntellijUtils.getSelectedFile(Objects.requireNonNull(e.getProject()))
            .ifPresentOrElse(selectedFile -> {
                      IntellijUtils.autoSaveFile(selectedFile);

                      String path = selectedFile.getPath();
                      CodeRunner codeRunner = CodeRunnerProvider.getCodeRunner(CodeLanguage.JAVA);
                      TestRunningThread testRunningThread = new TestRunningThread(path, codeRunner, selectedItem);
                      GlobalThreadStore.getInstance().executeThreads(ThreadGroupName.TEST_RUNNING, testRunningThread);
                    }
                    , () -> Messages.showMessageDialog("No selected file", "Run Test", AllIcons.Actions.Exit));
  }
}
