/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.editor;

public interface Editor {
  String getInput();
  String getOutput();
  void setInput(String input);
  void setOutput(String output);
  void setResult(String result);
  String getResult();
  void clearAll();
}
