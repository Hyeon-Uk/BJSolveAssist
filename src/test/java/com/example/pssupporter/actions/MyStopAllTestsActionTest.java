/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.actions;

import com.example.pssupporter.ui.editor.EditorPanel;
import com.example.pssupporter.ui.list.TestListPanel;
import com.example.pssupporter.ui.main.MyMainView;
import com.example.pssupporter.utils.thread.MyThreadStore;
import com.example.pssupporter.utils.thread.vo.ThreadGroupName;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MyStopAllTestsActionTest {
  @Mock
  private TestListPanel mockTestListPanel;
  @Mock
  private EditorPanel mockEditorPanel;
  @Mock
  private MyMainView mockMainView;
  @Mock
  private MyThreadStore mockThreadStore;
  @Mock
  private Presentation presentation;
  @Mock
  private AnActionEvent mockAnActionEvent;
  @Mock
  private Project mockProject;

  @InjectMocks
  private MyStopAllTestsAction stopAllTestsAction;

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
      stopAllTestsAction.update(mockAnActionEvent);

      //then
      assertTrue(isEnabled);
    }

    @Test
    void enableWhenRunningThreadsNotExist() {
      //given
      when(mockThreadStore.hasRunningThreads(any(ThreadGroupName.class))).thenReturn(false);

      //when
      stopAllTestsAction.update(mockAnActionEvent);

      //then
      assertFalse(isEnabled);
    }
  }
  @Nested
  @DisplayName("[ActionPerformed]")
  class ActionPerformedTest {
    @Test
    void stopAllRunningTests() {
      //given
      Map<ThreadGroupName, List<Thread>> threadContainer = new HashMap<>();
      List<Thread> testList = new ArrayList<>();
      testList.add(new Thread());
      threadContainer.put(ThreadGroupName.TEST_RUNNING,testList);
      doAnswer(invocation -> {
        ThreadGroupName threadGroupName = invocation.getArgument(0, ThreadGroupName.class);
        threadContainer.getOrDefault(threadGroupName,new ArrayList<>()).clear();
        return null;
      }).when(mockThreadStore).interruptThreads(any(ThreadGroupName.class));

      //when
      stopAllTestsAction.actionPerformed(mockAnActionEvent);

      //then
      assertEquals(0,threadContainer.get(ThreadGroupName.TEST_RUNNING).size());
    }
  }
}
