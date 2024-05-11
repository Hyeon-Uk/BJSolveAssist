/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.editor.panel;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;

import java.awt.*;

public class MyResultEditorPanel extends ResultEditorPanel {
  private String myResult;
  private final ConsoleView myResultConsoleView;

  public MyResultEditorPanel() {
    this.myResult = null;
    this.myResultConsoleView = createConsoleView();
    this.setLayout(new BorderLayout());
    this.add(myResultConsoleView.getComponent(), BorderLayout.CENTER);
  }

  private ConsoleView createConsoleView() {
    Project project = ProjectManager.getInstance().getDefaultProject();
    return TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
  }

  @Override
  public void setResult(String result) {
    this.myResult = result;
    this.myResultConsoleView.clear();
    this.myResultConsoleView.print(result, ConsoleViewContentType.SYSTEM_OUTPUT);
  }

  @Override
  public String getResult() {
    return this.myResult;
  }
}
