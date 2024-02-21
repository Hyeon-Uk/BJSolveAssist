/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils.thread;

import com.example.pssupporter.ui.list.MyTestListItem;
import com.example.pssupporter.utils.runner.CodeRunner;
import com.example.pssupporter.utils.runner.dto.CodeRunnerDTO;
import com.example.pssupporter.vo.TestData;
import com.example.pssupporter.vo.TestStatus;

public class TestRunningThread extends Thread {
  private String myFilePath;
  private CodeRunner myCodeRunner;
  private MyTestListItem myTestListItem;
  private TestData myTestData;

  public TestRunningThread(String myFilePath, CodeRunner myCodeRunner, MyTestListItem myTestListItem) {
    this.myFilePath = myFilePath;
    this.myCodeRunner = myCodeRunner;
    this.myTestListItem = myTestListItem;
    this.myTestData = myTestListItem.getMyEditorPanel().getTestData();
  }

  @Override
  public void run() {
    super.run();
    myTestListItem.setStatus(TestStatus.RUNNING);

    String input = myTestData.getInput();
    String output = myTestData.getOutput();

    CodeRunnerDTO.Response response = myCodeRunner.runCode(new CodeRunnerDTO.Request(myFilePath, input));
    String result = response.getOutput();

    if (result.equals(output)) {
      myTestListItem.setStatus(TestStatus.ACCEPT);
    } else {
      myTestListItem.setStatus(TestStatus.FAIL);
    }

    myTestListItem.getMyEditorPanel().setResult(result);
  }

  @Override
  public void interrupt() {
    myTestListItem.setStatus(TestStatus.STOP);
    super.interrupt();
  }
}
