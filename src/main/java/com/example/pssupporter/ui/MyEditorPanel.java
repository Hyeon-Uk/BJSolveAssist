/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui;


import com.example.pssupporter.vo.TestData;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;

import javax.swing.*;
import java.awt.*;

public class MyEditorPanel extends JBPanel {

  private final JBTextArea myInputTextArea;
  private final JBTextArea myOutputTextArea;
  private final JBTextArea myLogTextArea;
  private final TestData myTestData;

  public MyEditorPanel() {
    this(new TestData());
  }

  public MyEditorPanel(TestData myTestData) {
    this.myTestData = myTestData;
    this.setName("MyLogPanel");
    this.setLayout(new GridLayout(3, 1));
    this.setBorder(BorderFactory.createLineBorder(JBColor.DARK_GRAY));

    myInputTextArea = new JBTextArea(10, 10);
    myInputTextArea.setText(myTestData.getInput());

    JBScrollPane myInputTextScrollPane = new JBScrollPane(myInputTextArea);
    myInputTextScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(JBColor.DARK_GRAY), "Input"));
    myInputTextScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    myInputTextScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    myOutputTextArea = new JBTextArea(10, 10);
    myOutputTextArea.setText(myTestData.getOutput());

    JBScrollPane myOutputTextScrollPane = new JBScrollPane(myOutputTextArea);
    myOutputTextScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(JBColor.DARK_GRAY), "Output"));
    myOutputTextScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    myOutputTextScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    myLogTextArea = new JBTextArea();
    myLogTextArea.setEditable(false);
    myLogTextArea.setDragEnabled(true);

    setBorder(BorderFactory.createLineBorder(JBColor.LIGHT_GRAY));
    JBScrollPane myLogScrollPane = new JBScrollPane(myLogTextArea);
    myLogScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(JBColor.DARK_GRAY), "Result"));
    myLogScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    myLogScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    this.add(myInputTextScrollPane);
    this.add(myOutputTextScrollPane);
    this.add(myLogScrollPane);

  }

  public TestData getTestData() {
    myTestData.setInput(myInputTextArea.getText());
    myTestData.setOutput(myOutputTextArea.getText());
    myTestData.setResult(myLogTextArea.getText());
    return myTestData;
  }

  public void setResult(String result) {
    myTestData.setResult(result);
    myLogTextArea.setText(result);
  }
}
