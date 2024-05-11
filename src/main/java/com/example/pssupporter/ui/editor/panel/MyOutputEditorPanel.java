/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.editor.panel;

import com.example.pssupporter.ui.editor.function.OutputEditorFunction;
import com.intellij.ui.components.JBTextArea;

import java.awt.*;

public class MyOutputEditorPanel extends OutputEditorPanel implements OutputEditorFunction {
  private final JBTextArea myOutputTextArea;

  public MyOutputEditorPanel() {
    this.setLayout(new BorderLayout());
    myOutputTextArea = new JBTextArea();
    this.add(myOutputTextArea, BorderLayout.CENTER);
  }

  @Override
  public void setOutput(String output) {
    myOutputTextArea.setText(output);
  }

  @Override
  public String getOutput() {
    return myOutputTextArea.getText();
  }
}
