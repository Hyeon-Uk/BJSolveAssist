/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.fake;

import com.example.pssupporter.ui.editor.panel.InputEditorPanel;

public class FakeInputEditorPanel extends InputEditorPanel {
  private String input;

  @Override
  public void setInput(String input) {
    this.input = input;
  }

  @Override
  public String getInput() {
    return this.input;
  }
}
