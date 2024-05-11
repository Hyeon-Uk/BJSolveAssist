/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.list;

import com.example.pssupporter.ui.list.panel.SubTestList;
import com.example.pssupporter.vo.TestData;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MySubTestList extends SubTestList {
  private DefaultListModel<MyTestListItem> myModel;

  public MySubTestList(ListCellRenderer<MyTestListItem> cellRenderer) {
    this.setCellRenderer(cellRenderer);
    myModel = new DefaultListModel<>();

    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    this.setModel(myModel);
  }

  public void addTest() {
    myModel.addElement(new MyTestListItem());
  }

  public void addTest(TestData testData) {
    myModel.addElement(new MyTestListItem(testData));
  }

  public void removeTest(int index) {
    if (0 <= index && index < myModel.size()) {
      myModel.removeElementAt(index);
    }
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
    return selectedIndex < 0 || selectedIndex >= myModel.getSize() ? null : myModel.getElementAt(selectedIndex);
  }
}
