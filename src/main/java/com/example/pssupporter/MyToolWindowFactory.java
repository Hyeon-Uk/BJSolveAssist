/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter;

import com.example.pssupporter.ui.MyTestListPanel;
import com.example.pssupporter.ui.MyToolbarPanel;
import com.example.pssupporter.utils.ComponentManager;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class MyToolWindowFactory implements ToolWindowFactory {
  private ActionGroup getActionGroup(String actionGroupId) {
    ActionManager actionManager = ActionManager.getInstance();
    return (ActionGroup) actionManager.getAction(actionGroupId);
  }

  @Override
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
    ApplicationManager.getApplication().invokeLater(() -> {
      ContentManager contentManager = toolWindow.getContentManager();
      ContentFactory contentFactory = ContentFactory.getInstance();

      JBPanel myMainView = new JBPanel(new BorderLayout());
      MyTestListPanel myTestListPanel = new MyTestListPanel(project);
      JBScrollPane scrollPane = new JBScrollPane(myTestListPanel);

      scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

      JBPanel myEditorPanel = new JBPanel(new GridLayout(1, 1));
      ActionGroup myActionGroup = getActionGroup("myActionGroup");
      MyToolbarPanel myToolbarPanel = new MyToolbarPanel(myActionGroup, myMainView);

      myMainView.add(myToolbarPanel, BorderLayout.NORTH);
      myMainView.add(scrollPane, BorderLayout.WEST);
      myMainView.add(myEditorPanel, BorderLayout.CENTER);

      ComponentManager.getInstance().addComponent("myTestListPanel", myTestListPanel);
      ComponentManager.getInstance().addComponent("myEditorPanel", myEditorPanel);

      Content content = contentFactory.createContent(myMainView, "Supporter", false);

      contentManager.addContent(content);
    });
  }
}
