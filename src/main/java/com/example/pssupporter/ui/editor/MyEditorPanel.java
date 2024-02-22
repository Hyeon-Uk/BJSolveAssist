/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.editor;


import com.example.pssupporter.ui.factory.JTitleBorderFactory;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MyEditorPanel extends EditorPanel {

  private final JBTextArea myInputTextArea;
  private final JBTextArea myOutputTextArea;
  private final ConsoleView myLogConsoleView;

  public MyEditorPanel() {
    this.setLayout(new BorderLayout());
    this.setBorder(BorderFactory.createLineBorder(JBColor.DARK_GRAY));

    JBPanel inputOutputPanel = new JBPanel(new GridLayout(1, 2));

    myInputTextArea = new JBTextArea();

    JBScrollPane myInputTextScrollPane = new JBScrollPane(myInputTextArea);
    Border inputBorder = JTitleBorderFactory.getBorder("Input");
    myInputTextScrollPane.setBorder(inputBorder);
    myInputTextScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    myInputTextScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    myOutputTextArea = new JBTextArea();

    JBScrollPane myOutputTextScrollPane = new JBScrollPane(myOutputTextArea);
    Border outputBorder = JTitleBorderFactory.getBorder("Output");
    myOutputTextScrollPane.setBorder(outputBorder);
    myOutputTextScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    myOutputTextScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    myLogConsoleView = createConsoleView();

    setBorder(BorderFactory.createLineBorder(JBColor.LIGHT_GRAY));
    JBScrollPane myLogScrollPane = new JBScrollPane(myLogConsoleView.getComponent());
    Border resultBorder = JTitleBorderFactory.getBorder("Result");
    myLogScrollPane.setBorder(resultBorder);
    myLogScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    myLogScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    inputOutputPanel.add(myInputTextScrollPane);
    inputOutputPanel.add(myOutputTextScrollPane);

    this.add(inputOutputPanel, BorderLayout.NORTH);
    this.add(myLogScrollPane, BorderLayout.SOUTH);

    // JSplitPane
    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, inputOutputPanel, myLogScrollPane);
    splitPane.setDividerSize(1);
    splitPane.setResizeWeight(0.3);
    add(splitPane, BorderLayout.CENTER);
  }

  @Override
  public void clearAll() {
    myInputTextArea.setText("");
    myOutputTextArea.setText("");
    myLogConsoleView.clear();
  }

  @Override
  public String getInput() {
    return myInputTextArea.getText();
  }

  @Override
  public String getOutput() {
    return myOutputTextArea.getText();
  }

  @Override
  public void setInput(String input) {
    myInputTextArea.setText(input);
  }

  @Override
  public void setOutput(String output) {
    myOutputTextArea.setText(output);
  }

  @Override
  public void setResult(String result) {
    myLogConsoleView.clear();
    myLogConsoleView.print(result, ConsoleViewContentType.SYSTEM_OUTPUT);
  }

  private ConsoleView createConsoleView() {
    Project project = ProjectManager.getInstance().getDefaultProject();
    return TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
  }
}
