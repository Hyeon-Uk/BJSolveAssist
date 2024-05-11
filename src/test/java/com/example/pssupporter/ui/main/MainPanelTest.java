/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.main;

import com.example.pssupporter.ui.editor.EditorPanel;
import com.example.pssupporter.ui.editor.MyEditorPanel;
import com.example.pssupporter.ui.editor.panel.InputEditorPanel;
import com.example.pssupporter.ui.editor.panel.OutputEditorPanel;
import com.example.pssupporter.ui.editor.panel.ResultEditorPanel;
import com.example.pssupporter.ui.fake.FakeInputEditorPanel;
import com.example.pssupporter.ui.fake.FakeOutputEditorPanel;
import com.example.pssupporter.ui.fake.FakeResultEditorPanel;
import com.example.pssupporter.ui.fake.FakeSubTestList;
import com.example.pssupporter.ui.list.MyTestListPanel;
import com.example.pssupporter.ui.list.TestListPanel;
import com.example.pssupporter.ui.list.panel.SubTestList;
import com.example.pssupporter.vo.TestData;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class MainPanelTest {

  private SubTestList fakeSubTestList = new FakeSubTestList();
  private TestListPanel fakeTestListPanel = new MyTestListPanel(fakeSubTestList, e -> {
  });
  private InputEditorPanel fakeInputEditorPanel = new FakeInputEditorPanel();
  private OutputEditorPanel fakeOutputEditorPanel = new FakeOutputEditorPanel();
  private ResultEditorPanel fakeResultEditorPanel = new FakeResultEditorPanel();
  private EditorPanel fakeEditorPanel = new MyEditorPanel(fakeInputEditorPanel, fakeOutputEditorPanel, fakeResultEditorPanel);
  private MyMainView myMainView = new MyMainView(fakeTestListPanel, fakeEditorPanel, true);

  @Test
  void changeTestDataTest() {
    //given
    TestData beforeTestData = new TestData("beforeInput", "beforeOutput");
    TestData afterTestData = new TestData("afterInput", "afterOutput");
    fakeTestListPanel.addTestData(beforeTestData);
    fakeTestListPanel.addTestData(afterTestData);
    fakeSubTestList.setSelectedIndex(0);//Initialize the selection of subTestList to the first data(beforeTestData)

    //when
    myMainView.changeTestData();

    //then
    assertEquals(beforeTestData.getInput(), fakeEditorPanel.getInput());
    assertEquals(beforeTestData.getOutput(), fakeEditorPanel.getOutput());
    assertEquals(beforeTestData.getResult(), fakeEditorPanel.getResult());

    //when
    fakeEditorPanel.setInput("changeInputData");
    fakeEditorPanel.setOutput("changeOutputData");
    fakeEditorPanel.setResult("changeResultData");
    fakeSubTestList.setSelectedIndex(1);//change selection of subTestList to the second data(afterTestData)
    myMainView.changeTestData();

    //then
    assertEquals("changeInputData", beforeTestData.getInput());
    assertEquals("changeOutputData", beforeTestData.getOutput());
  }

  @Test
  void saveAndClearTest() {
    //given
    TestData testData = new TestData("testInput", "testOutput", "testResult");
    fakeSubTestList.addTest(testData);
    fakeSubTestList.setSelectedIndex(0);
    myMainView.changeTestData();

    //when
    fakeEditorPanel.setInput("changeInput");
    fakeEditorPanel.setOutput("changeOutput");
    fakeEditorPanel.setResult("changeResult");
    myMainView.saveAndClear();

    //then
    assertEquals("", fakeEditorPanel.getInput());
    assertEquals("", fakeEditorPanel.getOutput());
    assertEquals("", fakeEditorPanel.getResult());

    assertEquals("changeInput", testData.getInput());
    assertEquals("changeOutput", testData.getOutput());
  }
}
