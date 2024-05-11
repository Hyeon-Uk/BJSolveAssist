/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.actions;

import com.example.pssupporter.ui.editor.EditorPanel;
import com.example.pssupporter.ui.list.MyTestListItem;
import com.example.pssupporter.ui.list.TestListPanel;
import com.example.pssupporter.ui.main.MyMainView;
import com.example.pssupporter.utils.IntellijUtils;
import com.example.pssupporter.utils.runner.CodeRunner;
import com.example.pssupporter.utils.runner.CodeRunnerProvider;
import com.example.pssupporter.utils.runner.vo.CodeLanguage;
import com.example.pssupporter.utils.thread.MyThreadStore;
import com.example.pssupporter.utils.thread.TestRunningThread;
import com.example.pssupporter.utils.thread.vo.ThreadGroupName;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class MyRunAllTestsAction extends AnAction {
  private final TestListPanel myTestListPanel;
  private final EditorPanel myEditorPanel;
  private final MyMainView myMainView;
  private final MyThreadStore myThreadStore;

  public MyRunAllTestsAction(TestListPanel myTestListPanel, EditorPanel myEditorPanel, MyMainView myMainView, MyThreadStore myThreadStore) {
    super("Run All Tests", "This action can run all tests", AllIcons.Actions.RunAll);
    this.myTestListPanel = myTestListPanel;
    this.myEditorPanel = myEditorPanel;
    this.myMainView = myMainView;
    this.myThreadStore = myThreadStore;
  }

  @Override
  public void update(@NotNull AnActionEvent e) {
    super.update(e);
    boolean isRunning = myThreadStore
            .hasRunningThreads(ThreadGroupName.TEST_RUNNING);
    e.getPresentation().setEnabled(!isRunning);
    myTestListPanel.setEnabled(!isRunning);
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    myMainView.saveAndClear();
    myTestListPanel.clearSelection();
    myEditorPanel.clearAll();

    List<MyTestListItem> myTestListItems = myTestListPanel.getMyTestListItems();
    IntellijUtils.getSelectedFile(Objects.requireNonNull(e.getProject()))
            .ifPresentOrElse(selectedFile -> {
              IntellijUtils.autoSaveFile(selectedFile);

              String path = selectedFile.getPath();
              CodeRunner codeRunner = CodeRunnerProvider.getCodeRunner(CodeLanguage.JAVA);
              TestRunningThread[] testRunningThreads = myTestListItems.stream()
                      .map(item -> new TestRunningThread(path, codeRunner, item))
                      .toArray(TestRunningThread[]::new);
              myThreadStore.executeThreads(ThreadGroupName.TEST_RUNNING, testRunningThreads);
            }, () -> Messages.showMessageDialog("No selected file", "Run All Tests", AllIcons.Actions.Exit));
  }
}
