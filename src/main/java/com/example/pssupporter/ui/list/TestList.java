/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.list;

import com.example.pssupporter.vo.TestData;

import java.util.List;
import java.util.Optional;

public interface TestList {
  void addTestData();
  void addTestData(TestData testData);
  void removeTestData(int index);
  List<TestData> getAllTestData();
  Optional<TestData> getTestData(int index);
  Optional<TestData> getSelectedTestData();
  int getSelectedIndex();
}
