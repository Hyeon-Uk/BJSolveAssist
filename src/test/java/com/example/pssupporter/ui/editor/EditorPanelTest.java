/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.editor;

import com.example.pssupporter.ui.editor.panel.InputEditorPanel;
import com.example.pssupporter.ui.editor.panel.OutputEditorPanel;
import com.example.pssupporter.ui.editor.panel.ResultEditorPanel;
import com.example.pssupporter.ui.fake.FakeInputEditorPanel;
import com.example.pssupporter.ui.fake.FakeOutputEditorPanel;
import com.example.pssupporter.ui.fake.FakeResultEditorPanel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EditorPanelTest {
  private InputEditorPanel inputEditorPanel = new FakeInputEditorPanel();
  private OutputEditorPanel outputEditorPanel = new FakeOutputEditorPanel();
  private ResultEditorPanel resultEditorPanel = new FakeResultEditorPanel();
  private EditorPanel editorPanel = new MyEditorPanel(inputEditorPanel, outputEditorPanel, resultEditorPanel);

  @Test
  void inputDataTest() {
    //given
    String testText = "For Input Test";
    editorPanel.setInput(testText);

    //when
    String input = editorPanel.getInput();

    //then
    assertEquals(testText, input);
  }

  @Test
  void outputDataTest() {
    //given
    String testText = "For Output Test";
    editorPanel.setOutput(testText);

    //when
    String output = editorPanel.getOutput();

    //then
    assertEquals(testText, output);
  }

  @Test
  void resultDataTest() {
    //given
    String testText = "For Result Test";
    editorPanel.setResult(testText);

    //when
    String result = editorPanel.getResult();

    //then
    assertEquals(testText, result);
  }

  @Test
  void clearAllTest() {
    //given
    String testInput = "For Input Test";
    String testOutput = "For Output Test";
    String testResult = "For Result Test";
    editorPanel.setInput(testInput);
    editorPanel.setOutput(testOutput);
    editorPanel.setResult(testResult);

    //when
    editorPanel.clearAll();

    //then
    assertEquals("", editorPanel.getInput());
    assertEquals("", editorPanel.getOutput());
    assertEquals("", editorPanel.getResult());
  }
}