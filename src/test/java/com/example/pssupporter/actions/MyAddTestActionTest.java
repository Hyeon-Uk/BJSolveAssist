/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.actions;

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
class MyAddTestActionTest {
  @Mock
  private TestListPanel mockTestListPanel;
  @Mock
  private MyThreadStore mockThreadStore;
  @Mock
  private Presentation presentation;
  @Mock
  private AnActionEvent mockAnActionEvent;

  @InjectMocks
  private MyAddTestAction addTestAction;

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
      addTestAction.update(mockAnActionEvent);

      //then
      assertFalse(isEnabled);
    }

    @Test
    void enableWhenRunningThreadsNotExist() {
      //given
      when(mockThreadStore.hasRunningThreads(any(ThreadGroupName.class))).thenReturn(false);

      //when
      addTestAction.update(mockAnActionEvent);

      //then
      assertTrue(isEnabled);
    }
  }

  @Nested
  @DisplayName("[ActionPerformed]")
  class ActionPerformedTest {
    List<TestData> testDataList = new ArrayList<>();

    @BeforeEach
    void init() {
      doAnswer(invocation -> {
        testDataList.add(new TestData());
        return null;
      }).when(mockTestListPanel).addTestData();
    }

    @Test
    void increaseTestListItem() {
      //given

      //when
      addTestAction.actionPerformed(mockAnActionEvent);

      //then
      assertEquals(1, testDataList.size());
    }
  }
}