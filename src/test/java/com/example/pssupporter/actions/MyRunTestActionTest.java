/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.actions;

import com.example.pssupporter.fake.action.FakeProjectService;
import com.example.pssupporter.fake.action.FakeThreadStore;
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
import com.example.pssupporter.ui.main.MyMainView;
import com.example.pssupporter.utils.project.ProjectService;
import com.example.pssupporter.utils.project.vo.CustomFile;
import com.example.pssupporter.utils.thread.ThreadStore;
import com.example.pssupporter.utils.thread.vo.ThreadGroupName;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MyRunTestActionTest {
  private InputEditorPanel mockInputEditorPanel = new FakeInputEditorPanel();
  private OutputEditorPanel mockOutputEditorPanel = new FakeOutputEditorPanel();
  private ResultEditorPanel mockResultEditorPanel = new FakeResultEditorPanel();
  private SubTestList mockSubTestList = new FakeSubTestList();
  private TestListPanel mockTestListPanel = new MyTestListPanel(mockSubTestList, e -> {

  });
  private EditorPanel mockEditorPanel = new MyEditorPanel(mockInputEditorPanel, mockOutputEditorPanel, mockResultEditorPanel);
  private MyMainView mockMainView = new MyMainView(mockTestListPanel, mockEditorPanel, true);
  private Map<ThreadGroupName, List<Thread>> threadContainer = new ConcurrentHashMap<>();
  private ThreadStore mockThreadStore = new FakeThreadStore(threadContainer);
  private CustomFile mockFile = new CustomFile("customFilePath", "customFileName");
  private ProjectService mockProjectService = new FakeProjectService(mockFile, true);
  private MyRunTestAction runTestAction = new MyRunTestAction(mockTestListPanel, mockEditorPanel, mockMainView, mockThreadStore, mockProjectService);

  private Presentation presentation = new Presentation("mock");
  @Mock
  private AnActionEvent mockAnActionEvent;

  @Nested
  @DisplayName("[UpdateTest]")
  class UpdateTest {
    @BeforeEach
    void init() {
      when(mockAnActionEvent.getPresentation())
              .thenReturn(presentation);
    }

    @Test
    void disableWhenRunningThreadsExist() {
      //given
      threadContainer.put(ThreadGroupName.TEST_RUNNING,List.of(new Thread()));

      //when
      runTestAction.update(mockAnActionEvent);

      //then
      assertFalse(presentation.isEnabled());
    }

    @Test
    void enableWhenRunningThreadsNotExist() {
      //given
      //when
      runTestAction.update(mockAnActionEvent);

      //then
      assertTrue(presentation.isEnabled());
    }
  }

  @Nested
  @DisplayName("[ActionPerformed]")
  class ActionPerformedTest {
    @Test
    void runOneTest() {
      //given
      mockSubTestList.addTest();
      mockSubTestList.setSelectedIndex(0);

      //when
      runTestAction.actionPerformed(mockAnActionEvent);

      //then
      assertEquals(1, threadContainer.get(ThreadGroupName.TEST_RUNNING).size());
    }
  }
}
