/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.list;

import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;

public abstract class TestListPanel extends JBScrollPane implements TestList {
  public TestListPanel(JBList list) {
    super(list);
  }
}
