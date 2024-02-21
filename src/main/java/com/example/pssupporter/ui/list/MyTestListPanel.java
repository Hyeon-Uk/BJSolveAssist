/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.list;

import com.example.pssupporter.ui.editor.MyEditorPanel;
import com.example.pssupporter.utils.ComponentManager;
import com.example.pssupporter.vo.TestData;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MyTestListPanel extends JBList<MyTestListItem> {

  private DefaultListModel<MyTestListItem> myModel;
  private Project myProject;

  protected MyTestListPanel(Project project) {
    this.myProject = project;
    myModel = new DefaultListModel<>();
    setCellRenderer(new MyCellRenderer());

    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    addListSelectionListener((e) -> {
      int selectedIndex = this.getSelectedIndex();

      if (selectedIndex == -1) {
        return;
      }
      MyTestListItem selectedItem = myModel.getElementAt(selectedIndex);

      JBPanel myEditorPanel = (JBPanel) ComponentManager.getInstance().getComponent("myEditorPanel");

      ComponentManager.getInstance().removeChildrenComponents(myEditorPanel);

      myEditorPanel.add(selectedItem.getMyEditorPanel());
      myEditorPanel.repaint();
    });

    this.setModel(myModel);
  }

  public void addTest() {
    myModel.addElement(new MyTestListItem(new MyEditorPanel(this.myProject)));
  }

  public void addTest(TestData testData) {
    myModel.addElement(new MyTestListItem(new MyEditorPanel(this.myProject, testData)));
  }

  public void removeTest(int index) {
    myModel.removeElementAt(index);
  }

  public void removeAllTests() {
    myModel.removeAllElements();
  }

  public List<MyTestListItem> getMyTestListItems() {
    List<MyTestListItem> result = new ArrayList<>(myModel.size());
    for (int i = 0; i < myModel.size(); i++) {
      result.add(myModel.getElementAt(i));
    }

    return result;
  }

  public MyTestListItem getMyTestListItem(int selectedIndex) {
    return myModel.getElementAt(selectedIndex);
  }
}
