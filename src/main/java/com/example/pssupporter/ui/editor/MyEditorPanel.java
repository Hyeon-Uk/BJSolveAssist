/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.editor;


import com.example.pssupporter.ui.editor.panel.InputEditorPanel;
import com.example.pssupporter.ui.editor.panel.OutputEditorPanel;
import com.example.pssupporter.ui.editor.panel.ResultEditorPanel;
import com.example.pssupporter.ui.factory.JTitleBorderFactory;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MyEditorPanel extends EditorPanel {

  private final InputEditorPanel myInputEditorPanel;
  private final OutputEditorPanel myOutputEditorPanel;
  private final ResultEditorPanel myResultEditorPanel;

  public MyEditorPanel(InputEditorPanel inputEditorPanel, OutputEditorPanel outputEditorPanel, ResultEditorPanel resultEditorPanel) {
    this.setLayout(new BorderLayout());
    this.setBorder(BorderFactory.createLineBorder(JBColor.DARK_GRAY));
    myInputEditorPanel = inputEditorPanel;
    myOutputEditorPanel = outputEditorPanel;
    myResultEditorPanel = resultEditorPanel;

    JBPanel inputOutputPanel = new JBPanel(new GridLayout(1, 2));

    JBScrollPane myInputTextScrollPane = new JBScrollPane(myInputEditorPanel);
    Border inputBorder = JTitleBorderFactory.getBorder("Input");
    myInputTextScrollPane.setBorder(inputBorder);
    myInputTextScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    myInputTextScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    JBScrollPane myOutputTextScrollPane = new JBScrollPane(myOutputEditorPanel);
    Border outputBorder = JTitleBorderFactory.getBorder("Output");
    myOutputTextScrollPane.setBorder(outputBorder);
    myOutputTextScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    myOutputTextScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    setBorder(BorderFactory.createLineBorder(JBColor.LIGHT_GRAY));
    JBScrollPane myLogScrollPane = new JBScrollPane(myResultEditorPanel);
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
    myInputEditorPanel.setInput("");
    myOutputEditorPanel.setOutput("");
    myResultEditorPanel.setResult("");
  }

  @Override
  public String getInput() {
    return myInputEditorPanel.getInput();
  }

  @Override
  public String getOutput() {
    return myOutputEditorPanel.getOutput();
  }

  @Override
  public void setInput(String input) {
    myInputEditorPanel.setInput(input);
  }

  @Override
  public void setOutput(String output) {
    myOutputEditorPanel.setOutput(output);
  }

  @Override
  public void setResult(String result) {
    myResultEditorPanel.setResult(result);
  }

  @Override
  public String getResult() {
    return myResultEditorPanel.getResult();
  }
}
