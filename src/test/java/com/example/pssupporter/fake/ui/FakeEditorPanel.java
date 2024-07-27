/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.fake.ui;

import com.example.pssupporter.ui.editor.EditorPanel;
import com.example.pssupporter.ui.editor.panel.InputEditorPanel;
import com.example.pssupporter.ui.editor.panel.OutputEditorPanel;
import com.example.pssupporter.ui.editor.panel.ResultEditorPanel;
import com.example.pssupporter.ui.fake.FakeInputEditorPanel;
import com.example.pssupporter.ui.fake.FakeOutputEditorPanel;
import com.example.pssupporter.ui.fake.FakeResultEditorPanel;

public class FakeEditorPanel extends EditorPanel {
  private final InputEditorPanel inputEditorPanel;
  private final OutputEditorPanel outputEditorPanel;
  private final ResultEditorPanel resultEditorPanel;

  public FakeEditorPanel() {
    this.inputEditorPanel = new FakeInputEditorPanel();
    this.outputEditorPanel = new FakeOutputEditorPanel();
    this.resultEditorPanel = new FakeResultEditorPanel();
  }

  @Override
  public String getInput() {
    return inputEditorPanel.getInput();
  }

  @Override
  public String getOutput() {
    return outputEditorPanel.getOutput();
  }

  @Override
  public void setInput(String input) {
    inputEditorPanel.setInput(input);
  }

  @Override
  public void setOutput(String output) {
    outputEditorPanel.setOutput(output);
  }

  @Override
  public void setResult(String result) {
    resultEditorPanel.setResult(result);
  }

  @Override
  public String getResult() {
    return resultEditorPanel.getResult();
  }

  @Override
  public void clearAll() {
    inputEditorPanel.setInput("");
    outputEditorPanel.setOutput("");
    resultEditorPanel.setResult("");
  }
}
