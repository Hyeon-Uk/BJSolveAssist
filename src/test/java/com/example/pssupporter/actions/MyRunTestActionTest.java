/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.actions;

import com.example.pssupporter.ui.editor.EditorPanel;
import com.example.pssupporter.ui.list.MyTestListItem;
import com.example.pssupporter.ui.list.TestListPanel;
import com.example.pssupporter.ui.main.MyMainView;
import com.example.pssupporter.utils.IntellijUtils;
import com.example.pssupporter.utils.thread.MyThreadStore;
import com.example.pssupporter.utils.thread.vo.ThreadGroupName;
import com.example.pssupporter.vo.TestData;
import com.intellij.mock.MockVirtualFile;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MyRunTestActionTest {
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
  private MyRunAllTestsAction runAllTestsAction;

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
      runAllTestsAction.update(mockAnActionEvent);

      //then
      assertFalse(isEnabled);
    }

    @Test
    void enableWhenRunningThreadsNotExist() {
      //given
      when(mockThreadStore.hasRunningThreads(any(ThreadGroupName.class))).thenReturn(false);

      //when
      runAllTestsAction.update(mockAnActionEvent);

      //then
      assertTrue(isEnabled);
    }
  }

  @Nested
  @DisplayName("[ActionPerformed]")
  class ActionPerformedTest {
    MockedStatic<IntellijUtils> mockIntellijUtils;
    VirtualFile mockFile = new MockVirtualFile("mockFile");
    Map<ThreadGroupName, List<Thread>> threadContainer = new HashMap<>();

    @BeforeEach
    void init() {
      mockIntellijUtils = mockStatic(IntellijUtils.class);

    }

    @AfterEach
    void close() {
      mockIntellijUtils.close();
    }

    @Test
    void runOneTest() {
      //given
      when(IntellijUtils.getSelectedFile(any()))
              .thenReturn(Optional.ofNullable(mockFile));
      when(mockAnActionEvent.getProject())
              .thenReturn(mockProject);
      doAnswer(invocation -> {
        ThreadGroupName threadGroupName = invocation.getArgument(0, ThreadGroupName.class);
        Thread threads = invocation.getArgument(1, Thread.class);
        List<Thread> list = threadContainer.getOrDefault(threadGroupName, new ArrayList<>());
        list.add(threads);
        threadContainer.put(threadGroupName, list);
        return null;
      }).when(mockThreadStore).executeThreads(any(ThreadGroupName.class), any(Thread.class));
      when(mockTestListPanel.getMyTestListItems())
              .thenReturn(List.of(new MyTestListItem(new TestData())));

      //when
      runAllTestsAction.actionPerformed(mockAnActionEvent);

      //then
      assertEquals(1, threadContainer.get(ThreadGroupName.TEST_RUNNING).size());
    }
  }
}
