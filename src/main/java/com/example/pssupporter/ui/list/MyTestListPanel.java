/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.list;

import com.example.pssupporter.vo.TestData;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MyTestListPanel extends TestListPanel implements TestList {
  private MyTestList myTestList;

  public MyTestListPanel(MyTestList testList) {
    super(testList);
    this.myTestList = testList;

    this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
  }

  public MyTestListPanel(MyTestList testList, ListSelectionListener clickEvent) {
    super(testList);
    this.myTestList = testList;
    this.myTestList.addListSelectionListener(clickEvent);

    this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
  }

  @Override
  public int getSelectedIndex() {
    return myTestList.getSelectedIndex();
  }

  @Override
  public void addTestData() {
    myTestList.addTest();
  }

  @Override
  public void addTestData(TestData testData) {
    myTestList.addTest(testData);
  }

  @Override
  public void removeTestData(int index) {
    myTestList.removeTest(index);
  }

  @Override
  public List<TestData> getAllTestData() {
    return myTestList.getMyTestListItems()
            .stream()
            .map(MyTestListItem::getTestData)
            .collect(Collectors.toList());
  }

  @Override
  public Optional<TestData> getTestData(int index) {
    return Optional.ofNullable(myTestList.getMyTestListItem(index) == null ? null : myTestList.getMyTestListItem(index).getTestData());
  }

  @Override
  public Optional<TestData> getSelectedTestData() {
    return getTestData(this.getSelectedIndex());
  }

  public void clearSelection() {
    myTestList.clearSelection();
  }

  public List<MyTestListItem> getMyTestListItems() {
    return myTestList.getMyTestListItems();
  }

  public MyTestListItem getMyTestList(int selectedIndex) {
    return myTestList.getMyTestListItem(selectedIndex);
  }
}
