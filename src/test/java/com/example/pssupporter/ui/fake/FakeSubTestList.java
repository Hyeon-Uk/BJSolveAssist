/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.fake;

import com.example.pssupporter.ui.list.MyTestListItem;
import com.example.pssupporter.ui.list.panel.SubTestList;
import com.example.pssupporter.vo.TestData;

import java.util.ArrayList;
import java.util.List;

public class FakeSubTestList extends SubTestList {
  List<MyTestListItem> lists = new ArrayList<>();

  @Override
  public void addTest() {
    lists.add(new MyTestListItem());
  }

  @Override
  public void addTest(TestData testData) {
    lists.add(new MyTestListItem(testData));
  }

  @Override
  public void removeTest(int index) {
    lists.remove(index);
  }

  @Override
  public void removeAllTests() {
    lists.clear();
  }

  @Override
  public List<MyTestListItem> getMyTestListItems() {
    return lists;
  }

  @Override
  public MyTestListItem getMyTestListItem(int index) {
    return index < 0 || index >= lists.size() ? null : lists.get(index);
  }
}
