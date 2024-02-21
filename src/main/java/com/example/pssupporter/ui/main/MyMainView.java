/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.main;

import com.example.pssupporter.ui.toolbar.MyToolbarPanel;
import com.example.pssupporter.ui.list.MyTestListPanel;
import com.example.pssupporter.utils.ComponentManager;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.awt.*;

public class MyMainView extends JBPanel {
  private Project myProject;
  private boolean myHorizontal;
  private JBPanel myTestPanel;
  private MyTestListPanel myTestListPanel;
  private JBScrollPane myTestListScrollPane;
  private JBPanel myEditorPanel;


  public MyMainView(Project project, boolean horizontal) {
    super(new BorderLayout());
    this.myProject = project;
    this.myHorizontal = horizontal;

    setupActionToolbarPanel();
    setupTestListPanel();
    setupEditorPanel();
    setupTestPanel();
    addComponentsToManager();
  }

  /**
   * add component in component manager
   */
  private void addComponentsToManager() {
    ComponentManager.getInstance().addComponent("myTestListPanel", myTestListPanel);
    ComponentManager.getInstance().addComponent("myEditorPanel", myEditorPanel);
  }

  /**
   * implements test panel that includes testList and editor panel.
   */
  private void setupTestPanel() {
    myTestPanel = new JBPanel(new BorderLayout());
    this.setLayoutBasedOnOrientation(this.myHorizontal, true);
    this.add(myTestPanel, BorderLayout.CENTER);
  }

  /**
   * implements editor panel
   */
  private void setupEditorPanel() {
    myEditorPanel = new JBPanel(new GridLayout(1, 1));
  }

  /**
   * implement test list panel
   */
  private void setupTestListPanel() {
    myTestListPanel = new MyTestListPanel(this.myProject);
    myTestListScrollPane = new JBScrollPane(myTestListPanel);
    myTestListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    myTestListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
  }

  /**
   * implement action panel
   */
  private void setupActionToolbarPanel() {
    ActionGroup myActionGroup = getActionGroup("myActionGroup");
    MyToolbarPanel myActionToolbarPanel = new MyToolbarPanel(myActionGroup, this);

    this.add(myActionToolbarPanel, BorderLayout.NORTH);
  }

  private ActionGroup getActionGroup(String actionGroupId) {
    com.intellij.openapi.actionSystem.ActionManager actionManager = com.intellij.openapi.actionSystem.ActionManager.getInstance();
    return (ActionGroup) actionManager.getAction(actionGroupId);
  }

  public void setLayoutBasedOnOrientation(boolean horizontal) {
    setLayoutBasedOnOrientation(horizontal, false);
  }

  private void setLayoutBasedOnOrientation(boolean horizontal, boolean init) {
    if (init || this.myHorizontal != horizontal) {
      if (horizontal) {
        setHorizontalLayout();
      } else {
        setVerticalLayout();
      }
      this.myHorizontal = horizontal;
    }
  }

  private void setHorizontalLayout() {
    myTestPanel.add(myTestListScrollPane, BorderLayout.WEST);
    myTestPanel.add(myEditorPanel, BorderLayout.CENTER);
  }

  private void setVerticalLayout() {
    myTestPanel.add(myTestListScrollPane, BorderLayout.NORTH);
    myTestPanel.add(myEditorPanel, BorderLayout.CENTER);
  }
}
