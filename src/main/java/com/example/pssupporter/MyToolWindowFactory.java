/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter;

import com.example.pssupporter.ui.editor.EditorPanel;
import com.example.pssupporter.ui.editor.MyEditorPanel;
import com.example.pssupporter.ui.list.MyCellRenderer;
import com.example.pssupporter.ui.list.MyTestList;
import com.example.pssupporter.ui.list.MyTestListPanel;
import com.example.pssupporter.ui.list.TestListPanel;
import com.example.pssupporter.ui.main.MyMainView;
import com.example.pssupporter.ui.toolbar.MyToolbarPanel;
import com.example.pssupporter.utils.ComponentManager;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
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
  private MyTestList myTestList;
  private TestListPanel myTestListPanel;
  private EditorPanel myEditorPanel;
  private MyToolbarPanel myToolbarPanel;

  private ActionGroup getActionGroup(String actionGroupId) {
    ActionManager actionManager = ActionManager.getInstance();
    return (ActionGroup) actionManager.getAction(actionGroupId);
  }

  private void createToolbarPanel(JComponent targetComponent, String actionId) {
    ActionGroup action = getActionGroup(actionId);
    myToolbarPanel = new MyToolbarPanel(action, targetComponent);
  }

  private void createTestListPanel() {
    myTestList = new MyTestList(new MyCellRenderer());
    myTestListPanel = new MyTestListPanel(myTestList, (e) -> myMainView.changeTestData());
  }

  private void createEditorPanel() {
    myEditorPanel = new MyEditorPanel();
  }

  @Override
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
    ApplicationManager.getApplication().invokeLater(() -> {
      ContentManager contentManager = toolWindow.getContentManager();
      ContentFactory contentFactory = ContentFactory.getInstance();

      //ToolbarPanel
      createToolbarPanel(null, "myActionGroup");

      //EditorPanel
      createEditorPanel();

      //TestListPanel
      createTestListPanel();

      myMainView = new MyMainView(myTestListPanel, myEditorPanel, toolWindow.getAnchor().isHorizontal());

      JBPanel totalView = new JBPanel(new BorderLayout());
      totalView.add(myToolbarPanel, BorderLayout.NORTH);
      totalView.add(myMainView, BorderLayout.CENTER);

      Content content = contentFactory.createContent(totalView, "Supporter", false);

      contentManager.addContent(content);
      setupToolWindowEventListener(project);

      ComponentManager.getInstance().addComponent("myMainView", myMainView);
      ComponentManager.getInstance().addComponent("myTestListPanel", myTestListPanel);
      ComponentManager.getInstance().addComponent("myEditorPanel", myEditorPanel);
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
