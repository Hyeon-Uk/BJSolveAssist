/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.list.function;

import com.example.pssupporter.ui.list.MyTestListItem;
import com.example.pssupporter.vo.TestData;

import java.util.List;

public interface SubTestListFunction {
  void addTest();

  void addTest(TestData testData);

  void removeTest(int index);

  void removeAllTests();

  List<MyTestListItem> getMyTestListItems();

  MyTestListItem getMyTestListItem(int index);
}
