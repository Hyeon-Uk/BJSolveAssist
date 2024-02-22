/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.list;

import com.example.pssupporter.vo.TestData;
import com.example.pssupporter.vo.TestStatus;

public class MyTestListItem {
  private TestData myTestData;
  private TestStatus myStatus;

  public MyTestListItem() {
    this(new TestData(), TestStatus.READY);
  }

  public MyTestListItem(TestData testData) {
    this(testData, TestStatus.READY);
  }

  public MyTestListItem(TestData myTestData, TestStatus myStatus) {
    this.myTestData = myTestData;
    this.myStatus = myStatus;
  }

  public TestData getTestData() {
    return myTestData;
  }

  public TestStatus getStatus() {
    return myStatus;
  }
  public void setStatus(TestStatus status){
    this.myStatus = status;
  }
}
