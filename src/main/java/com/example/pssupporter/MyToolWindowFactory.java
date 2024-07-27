/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter;

import com.example.pssupporter.actions.*;
import com.example.pssupporter.ui.editor.EditorPanel;
import com.example.pssupporter.ui.editor.MyEditorPanel;
import com.example.pssupporter.ui.editor.panel.*;
import com.example.pssupporter.ui.list.MyCellRenderer;
import com.example.pssupporter.ui.list.MySubTestList;
import com.example.pssupporter.ui.list.MyTestListPanel;
import com.example.pssupporter.ui.list.TestListPanel;
import com.example.pssupporter.ui.main.MyMainView;
import com.example.pssupporter.ui.toolbar.MyToolbarPanel;
import com.example.pssupporter.utils.project.IntellijProjectService;
import com.example.pssupporter.utils.project.ProjectService;
import com.example.pssupporter.utils.thread.MyThreadStore;
import com.example.pssupporter.utils.thread.ThreadStore;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class MyToolWindowFactory implements ToolWindowFactory {
  private MyMainView myMainView;
  private TestListPanel myTestListPanel;
  private EditorPanel myEditorPanel;
  private MyToolbarPanel myToolbarPanel;
  private ThreadStore myThreadStore;
  private ProjectService myProjectService;


  @Override
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
    ApplicationManager.getApplication().invokeLater(() -> {
      initializeComponents(project, toolWindow);
      setupUI(toolWindow);
      setupToolWindowEventListener(project);
    });
  }

  private void initializeComponents(Project project, ToolWindow toolWindow) {
    myTestListPanel = createTestListPanel();
    myEditorPanel = createEditorPanel();
    myMainView = createMainView(toolWindow);
    myThreadStore = createThreadStore();
    myProjectService = createProjectService(project);
    createActionGroup();
    myToolbarPanel = createToolbarPanel(myMainView, "myActionGroup");
  }

  private ProjectService createProjectService(Project project) {
    return new IntellijProjectService(project);
  }

  private MyMainView createMainView(ToolWindow toolWindow) {
    return new MyMainView(myTestListPanel, myEditorPanel, toolWindow.getAnchor().isHorizontal());
  }

  private ThreadStore createThreadStore() {
    return new MyThreadStore();
  }

  private ActionGroup createActionGroup() {
    DefaultActionGroup actionGroup = new DefaultActionGroup();
    actionGroup.add(new MyLoadTestDataAction(myThreadStore));
    actionGroup.add(new MyAddTestAction(myTestListPanel, myThreadStore));
    actionGroup.add(new MyRemoveTestAction(myTestListPanel, myEditorPanel, myThreadStore));
    actionGroup.add(new MyRemoveAllTestAction(myTestListPanel, myEditorPanel, myThreadStore));
    actionGroup.add(new MyRunAllTestsAction(myTestListPanel, myEditorPanel, myMainView, myThreadStore, myProjectService));
    actionGroup.add(new MyRunTestAction(myTestListPanel, myEditorPanel, myMainView, myThreadStore, myProjectService));
    actionGroup.add(new MyStopAllTestsAction(myTestListPanel, myThreadStore));

    ActionManager.getInstance().registerAction("myActionGroup", actionGroup);

    return actionGroup;
  }

  private Optional<ActionGroup> getActionGroup(String actionGroupId) {
    return Optional.ofNullable((ActionGroup) ActionManager.getInstance().getAction(actionGroupId));
  }

  private MyToolbarPanel createToolbarPanel(JComponent targetComponent, String actionId) {
    ActionGroup actionGroup = getActionGroup(actionId).orElseGet(this::createActionGroup);
    return new MyToolbarPanel(actionGroup, targetComponent);
  }

  private TestListPanel createTestListPanel() {
    MySubTestList subTestList = new MySubTestList(new MyCellRenderer());
    return new MyTestListPanel(subTestList, e -> myMainView.changeTestData());
  }

  private EditorPanel createEditorPanel() {
    InputEditorPanel inputEditorPanel = new MyInputEditorPanel();
    OutputEditorPanel outputEditorPanel = new MyOutputEditorPanel();
    ResultEditorPanel resultEditorPanel = new MyResultEditorPanel();
    return new MyEditorPanel(inputEditorPanel, outputEditorPanel, resultEditorPanel);
  }

  private void setupUI(ToolWindow toolWindow) {
    JBPanel totalView = new JBPanel(new BorderLayout());
    totalView.add(myToolbarPanel, BorderLayout.NORTH);
    totalView.add(myMainView, BorderLayout.CENTER);

    ContentFactory contentFactory = ContentFactory.getInstance();
    Content content = contentFactory.createContent(totalView, "Supporter", false);

    ContentManager contentManager = toolWindow.getContentManager();
    contentManager.addContent(content);
  }

  private void setupToolWindowEventListener(@NotNull Project project) {
    MessageBusConnection connection = project.getMessageBus().connect();
    connection.subscribe(ToolWindowManagerListener.TOPIC, new ToolWindowManagerListener() {
      @Override
      public void stateChanged(@NotNull ToolWindowManager toolWindowManager, @NotNull ToolWindowManagerEventType changeType) {
        if (ToolWindowManagerEventType.SetSideToolAndAnchor.equals(changeType)) {
          ToolWindow toolWindow = toolWindowManager.getToolWindow("BaekjoonSupporter");
          if (toolWindow != null && toolWindow.getAnchor() != null) {
            myMainView.setLayoutBasedOnOrientation(toolWindow.getAnchor().isHorizontal());
          }
        }
      }
    });
  }
}
