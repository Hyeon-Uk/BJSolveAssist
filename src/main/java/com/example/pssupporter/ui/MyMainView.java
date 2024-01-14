/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui;

import com.example.pssupporter.utils.ComponentManager;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.awt.*;

public class MyMainView extends JBPanel {
  private Project myProject;

  public MyMainView(Project project) {
    super(new BorderLayout());
    this.myProject = project;

    //implement action panel
    ActionGroup myActionGroup = getActionGroup("myActionGroup");
    MyToolbarPanel myActionToolbarPanel = new MyToolbarPanel(myActionGroup, this);

    this.add(myActionToolbarPanel, BorderLayout.NORTH);

    //implement test list panel
    MyTestListPanel myTestListPanel = new MyTestListPanel(this.myProject);
    JBScrollPane myTestListScrollPane = new JBScrollPane(myTestListPanel);
    myTestListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    myTestListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

    this.add(myTestListScrollPane, BorderLayout.WEST);

    //implements editor panel
    JBPanel myEditorPanel = new JBPanel(new GridLayout(1, 1));

    this.add(myEditorPanel, BorderLayout.CENTER);

    //add component in component manager
    ComponentManager.getInstance().addComponent("myTestListPanel", myTestListPanel);
    ComponentManager.getInstance().addComponent("myEditorPanel", myEditorPanel);
  }

  private ActionGroup getActionGroup(String actionGroupId) {
    com.intellij.openapi.actionSystem.ActionManager actionManager = com.intellij.openapi.actionSystem.ActionManager.getInstance();
    return (ActionGroup) actionManager.getAction(actionGroupId);
  }
}
