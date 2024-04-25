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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyRemoveAllTestActionTest {
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
  private MyRemoveAllTestAction removeAllTestAction;

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
      removeAllTestAction.update(mockAnActionEvent);

      //then
      assertFalse(isEnabled);
    }

    @Test
    void enableWhenRunningThreadsNotExist() {
      //given
      when(mockThreadStore.hasRunningThreads(any(ThreadGroupName.class))).thenReturn(false);

      //when
      removeAllTestAction.update(mockAnActionEvent);

      //then
      assertTrue(isEnabled);
    }
  }

  @Nested
  @DisplayName("[actionPerformed]")
  class ActionPerformedTest {
    String input = "input";
    String output = "output";
    String result = "result";
    List<TestData> testDataList = new ArrayList<>();

    @BeforeEach
    void init() {
      doAnswer(invocation -> {
        input = null;
        output = null;
        result = null;
        return null;
      }).when(mockEditorPanel).clearAll();
      doAnswer(invocation -> {
        testDataList.clear();
        return null;
      }).when(mockTestListPanel).removeAllTestDatas();
    }

    @Test
    void clearEditorAndTestListWhenRemoveActionPerformed() {
      //given
      testDataList.add(new TestData());

      //when
      removeAllTestAction.actionPerformed(mockAnActionEvent);

      //then
      assertTrue(testDataList.isEmpty());
      assertNull(input);
      assertNull(output);
      assertNull(result);
    }
  }
}