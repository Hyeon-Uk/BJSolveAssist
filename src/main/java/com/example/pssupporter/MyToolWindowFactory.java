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
import com.example.pssupporter.utils.thread.MyThreadStore;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
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

public class MyToolWindowFactory implements ToolWindowFactory {
  private MyMainView myMainView;
  private TestListPanel myTestListPanel;
  private EditorPanel myEditorPanel;
  private MyToolbarPanel myToolbarPanel;
  private MyThreadStore myThreadStore;

  private MyThreadStore createThreadStore() {
    return new MyThreadStore();
  }

  private void createActionGroup() {
    DefaultActionGroup myActionGroup = new DefaultActionGroup();
    myActionGroup.add(new MyLoadTestDataAction(myThreadStore));
    myActionGroup.add(new MyAddTestAction(myTestListPanel, myThreadStore));
    myActionGroup.add(new MyRemoveTestAction(myTestListPanel, myEditorPanel, myThreadStore));
    myActionGroup.add(new MyRemoveAllTestAction(myTestListPanel, myEditorPanel, myThreadStore));
    myActionGroup.add(new MyRunAllTestsAction(myTestListPanel, myEditorPanel, myMainView, myThreadStore));
    myActionGroup.add(new MyRunTestAction(myTestListPanel, myEditorPanel, myMainView, myThreadStore));
    myActionGroup.add(new MyStopAllTestsAction(myTestListPanel, myThreadStore));

    ActionManager actionManager = ActionManager.getInstance();
    actionManager.registerAction("myActionGroup", myActionGroup);
  }

  private ActionGroup getActionGroup(String actionGroupId) {
    ActionManager actionManager = ActionManager.getInstance();
    return (ActionGroup) actionManager.getAction(actionGroupId);
  }

  private MyToolbarPanel createToolbarPanel(JComponent targetComponent, String actionId) {
    ActionGroup action = getActionGroup(actionId);
    return new MyToolbarPanel(action, targetComponent);
  }

  private TestListPanel createTestListPanel() {
    MySubTestList mySubTestList = new MySubTestList(new MyCellRenderer());
    return new MyTestListPanel(mySubTestList, (e) -> myMainView.changeTestData());
  }

  private EditorPanel createEditorPanel() {
    InputEditorPanel inputEditorPanel = new MyInputEditorPanel();
    OutputEditorPanel outputEditorPanel = new MyOutputEditorPanel();
    ResultEditorPanel resultEditorPanel = new MyResultEditorPanel();
    return new MyEditorPanel(inputEditorPanel, outputEditorPanel, resultEditorPanel);
  }

  @Override
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
    ApplicationManager.getApplication().invokeLater(() -> {
      myTestListPanel = createTestListPanel();
      myEditorPanel = createEditorPanel();
      myMainView = new MyMainView(myTestListPanel, myEditorPanel, toolWindow.getAnchor().isHorizontal());

      myThreadStore = createThreadStore();
      createActionGroup();
      myToolbarPanel = createToolbarPanel(myMainView, "myActionGroup");

      JBPanel totalView = new JBPanel(new BorderLayout());
      totalView.add(myToolbarPanel, BorderLayout.NORTH);
      totalView.add(myMainView, BorderLayout.CENTER);

      ContentFactory contentFactory = ContentFactory.getInstance();
      ContentManager contentManager = toolWindow.getContentManager();

      Content content = contentFactory.createContent(totalView, "Supporter", false);

      contentManager.addContent(content);
      setupToolWindowEventListener(project);
    });
  }

  private void setupToolWindowEventListener(@NotNull Project project) {
    MessageBusConnection connect = project.getMessageBus().connect();
    connect.subscribe(ToolWindowManagerListener.TOPIC, new ToolWindowManagerListener() {
      @Override
      public void stateChanged(@NotNull ToolWindowManager toolWindowManager, @NotNull ToolWindowManagerEventType changeType) {
        ToolWindowManagerListener.super.stateChanged(toolWindowManager, changeType);
        if (ToolWindowManagerEventType.SetSideToolAndAnchor.equals(changeType)) {
          ToolWindow baekjoonSupporter = toolWindowManager.getToolWindow("BaekjoonSupporter");

          if (baekjoonSupporter.getAnchor() != null) {
            ToolWindowAnchor anchor = baekjoonSupporter.getAnchor();
            myMainView.setLayoutBasedOnOrientation(anchor.isHorizontal());
          }
        }
      }
    });
  }
}
