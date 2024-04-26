/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.list;

import com.example.pssupporter.ui.list.panel.SubTestList;
import com.example.pssupporter.vo.TestData;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.util.List;
import java.util.Optional;

public class MyTestListPanel extends TestListPanel {
  private final SubTestList mySubTestList;

  public MyTestListPanel(SubTestList testList, ListSelectionListener clickEvent) {
    super(testList);
    this.mySubTestList = testList;
    this.mySubTestList.addListSelectionListener(clickEvent);

    this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
  }

  @Override
  public int getSelectedIndex() {
    return mySubTestList.getSelectedIndex();
  }

  @Override
  public void addTestData() {
    mySubTestList.addTest();
  }

  @Override
  public void addTestData(TestData testData) {
    mySubTestList.addTest(testData);
  }

  @Override
  public void removeTestData(int index) {
    if (0 <= index && index < mySubTestList.getMyTestListItems().size()) {
      mySubTestList.removeTest(index);
    }
  }

  @Override
  public void removeAllTestDatas() {
    mySubTestList.removeAllTests();
  }

  @Override
  public List<TestData> getAllTestData() {
    return mySubTestList.getMyTestListItems().stream().map(MyTestListItem::getTestData).toList();
  }

  @Override
  public Optional<TestData> getTestData(int index) {
    return Optional.ofNullable(mySubTestList.getMyTestListItem(index) == null ? null : mySubTestList.getMyTestListItem(index).getTestData());
  }

  @Override
  public Optional<TestData> getSelectedTestData() {
    return getTestData(this.getSelectedIndex());
  }

  @Override
  public void clearSelection() {
    mySubTestList.clearSelection();
  }

  @Override
  public List<MyTestListItem> getMyTestListItems() {
    return mySubTestList.getMyTestListItems();
  }

  @Override
  public MyTestListItem getMyTestList(int selectedIndex) {
    return mySubTestList.getMyTestListItem(selectedIndex);
  }
}
