/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui;

import com.example.pssupporter.vo.TestStatus;

public class MyTestListItem {
  private final MyEditorPanel myEditorPanel;
  private TestStatus myStatus;

  public MyTestListItem(MyEditorPanel myEditorPanel) {
    this(myEditorPanel, TestStatus.READY);
  }

  public MyTestListItem(MyEditorPanel myEditorPanel, TestStatus myStatus) {
    this.myEditorPanel = myEditorPanel;
    this.myStatus = myStatus;
  }

  public MyEditorPanel getMyEditorPanel() {
    return myEditorPanel;
  }

  public TestStatus getStatus() {
    return myStatus;
  }

  public void setStatus(TestStatus myStatus) {
    this.myStatus = myStatus;
  }
}
