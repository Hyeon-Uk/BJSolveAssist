/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.editor.panel;

import com.example.pssupporter.ui.editor.function.InputEditorFunction;
import com.intellij.ui.components.JBTextArea;

import java.awt.*;

public class MyInputEditorPanel extends InputEditorPanel implements InputEditorFunction {
  private final JBTextArea myInputTextArea;

  public MyInputEditorPanel() {
    this.setLayout(new BorderLayout());
    myInputTextArea = new JBTextArea();
    this.add(myInputTextArea, BorderLayout.CENTER);
  }

  @Override
  public void setInput(String input) {
    myInputTextArea.setText(input);
  }

  @Override
  public String getInput() {
    return myInputTextArea.getText();
  }
}
