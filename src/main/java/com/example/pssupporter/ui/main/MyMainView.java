/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.main;

import com.example.pssupporter.ui.editor.EditorPanel;
import com.example.pssupporter.ui.list.TestListPanel;
import com.example.pssupporter.ui.toolbar.MyToolbarPanel;
import com.example.pssupporter.vo.TestData;
import com.intellij.ui.components.JBPanel;

import java.awt.*;


public class MyMainView extends JBPanel {
  private boolean myHorizontal;

  private final TestListPanel myTestListPanel;
  private final EditorPanel myEditorPanel;
  private final MyToolbarPanel myToolbarPanel;
  private TestData myCurrentTestData;

  public MyMainView(MyToolbarPanel toolbarPanel, TestListPanel testListPanel, EditorPanel editorPanel, boolean horizontal) {
    super(new BorderLayout());

    this.myToolbarPanel = toolbarPanel;
    this.myTestListPanel = testListPanel;
    this.myEditorPanel = editorPanel;

    this.myHorizontal = horizontal;

    this.myCurrentTestData = null;

    setupActionToolbarPanel(myToolbarPanel);
    setupTestListPanel(myTestListPanel);
    setupEditorPanel(myEditorPanel);
  }

  /**
   * implements editor panel
   */
  private void setupEditorPanel(EditorPanel editorPanel) {
    this.add(editorPanel, BorderLayout.CENTER);
  }

  /**
   * implement test list panel
   */
  private void setupTestListPanel(TestListPanel testListPanel) {
    this.add(testListPanel, BorderLayout.WEST);
  }

  /**
   * implement action panel
   */
  private void setupActionToolbarPanel(MyToolbarPanel toolbarPanel) {
    this.add(toolbarPanel, BorderLayout.NORTH);
  }

  public void setLayoutBasedOnOrientation(boolean horizontal) {
    setLayoutBasedOnOrientation(horizontal, false);
  }

  private void setLayoutBasedOnOrientation(boolean horizontal, boolean init) {
    if (init || this.myHorizontal != horizontal) {
      if (horizontal) {
        setHorizontalLayout();
      } else {
        setVerticalLayout();
      }
      this.myHorizontal = horizontal;
    }
  }

  private void setHorizontalLayout() {
    this.add(myTestListPanel, BorderLayout.WEST);
    this.add(myEditorPanel, BorderLayout.CENTER);
  }

  private void setVerticalLayout() {
    this.add(myTestListPanel, BorderLayout.NORTH);
    this.add(myEditorPanel, BorderLayout.CENTER);
  }

  //custom action
  public void changeTestData(){
    //save current input and output if current test data presents
    if(myCurrentTestData != null){
      myCurrentTestData.setInput(myEditorPanel.getInput());
      myCurrentTestData.setOutput(myEditorPanel.getOutput());
    }

    myCurrentTestData = myTestListPanel.getSelectedTestData()
            .orElse(null);

    if(myCurrentTestData != null){
      myEditorPanel.setInput(myCurrentTestData.getInput());
      myEditorPanel.setOutput(myCurrentTestData.getOutput());
      myEditorPanel.setResult(myCurrentTestData.getResult());
    }
  }

  public void saveAndClear(){
    if(myCurrentTestData != null){
      myCurrentTestData.setInput(myEditorPanel.getInput());
      myCurrentTestData.setOutput(myEditorPanel.getOutput());
    }

    myCurrentTestData = null;

    myEditorPanel.clearAll();
  }
}
