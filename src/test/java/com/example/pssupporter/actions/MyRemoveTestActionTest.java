/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.actions;

import com.example.pssupporter.ui.editor.EditorPanel;
import com.example.pssupporter.ui.list.TestListPanel;
import com.example.pssupporter.utils.thread.MyThreadStore;
import com.example.pssupporter.utils.thread.vo.ThreadGroupName;
import com.example.pssupporter.vo.TestData;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyRemoveTestActionTest {
  @Mock
  private TestListPanel mockTestListPanel;
  @Mock
  private EditorPanel mockEditorPanel;
  @Mock
  private MyThreadStore mockThreadStore;
  @Mock
  private Presentation presentation;
  @Mock
  private AnActionEvent mockAnActionEvent;

  @InjectMocks
  private MyRemoveTestAction removeTestAction;

  @Nested
  @DisplayName("[UpdateTest]")
  class UpdateTest {
    boolean isEnabled;

    @BeforeEach
    void init() {
      when(mockAnActionEvent.getPresentation())
              .thenReturn(presentation);
      doAnswer(invocation -> {
        Boolean argument = invocation.getArgument(0, Boolean.class);
        isEnabled = argument;
        return isEnabled;
      }).when(presentation).setEnabled(anyBoolean());
    }

    @Test
    void disableWhenRunningThreadsExist() {
      //given
      when(mockThreadStore.hasRunningThreads(any(ThreadGroupName.class))).thenReturn(true);

      //when
      removeTestAction.update(mockAnActionEvent);

      //then
      assertFalse(isEnabled);
    }

    @Test
    void enableWhenRunningThreadsNotExist() {
      //given
      when(mockThreadStore.hasRunningThreads(any(ThreadGroupName.class))).thenReturn(false);

      //when
      removeTestAction.update(mockAnActionEvent);

      //then
      assertTrue(isEnabled);
    }
  }

  @Nested
  @DisplayName("[actionPerformed]")
  class ActionPerformedTest{
    String input = "input";
    String output = "output";
    String result = "result";
    List<TestData> testDataList = new ArrayList<>();

    @BeforeEach
    void init(){
      doAnswer(invocation -> {
        input = null;
        output = null;
        result = null;
        return null;
      }).when(mockEditorPanel).clearAll();
      doAnswer(invocation -> {
        Integer index = invocation.getArgument(0, Integer.class);
        testDataList.remove(index.intValue());
        return null;
      }).when(mockTestListPanel).removeTestData(anyInt());
    }

    @Test
    void clearEditorAndRemoveSelectedTestWhenRemoveActionPerformed(){
      //given
      when(mockTestListPanel.getSelectedIndex())
              .thenReturn(1);
      TestData testData1 = new TestData("1", "1");
      TestData testData2 = new TestData("2", "2");
      TestData testData3 = new TestData("3", "3");
      testDataList.add(testData1);
      testDataList.add(testData2);
      testDataList.add(testData3);

      //when
      removeTestAction.actionPerformed(mockAnActionEvent);

      //then
      assertEquals(2,testDataList.size());
      assertTrue(!testDataList.contains(testData2));
      assertNull(input);
      assertNull(output);
      assertNull(result);
    }
  }
}