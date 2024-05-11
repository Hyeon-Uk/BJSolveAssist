/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.fake;

import com.example.pssupporter.ui.editor.panel.ResultEditorPanel;

public class FakeResultEditorPanel extends ResultEditorPanel {
  private String result;
  @Override
  public void setResult(String result) {
    this.result = result;
  }

  @Override
  public String getResult() {
    return this.result;
  }
}
