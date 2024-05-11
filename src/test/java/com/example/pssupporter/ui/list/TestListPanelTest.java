/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.list;

import com.example.pssupporter.ui.fake.FakeSubTestList;
import com.example.pssupporter.ui.list.panel.SubTestList;
import com.example.pssupporter.vo.TestData;
import org.junit.jupiter.api.Test;

import javax.swing.event.ListSelectionListener;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

class TestListPanelTest {
  private SubTestList subTestList = new FakeSubTestList();
  private int clickCount = 0;
  private ListSelectionListener listener = (e) -> {
    clickCount++;
  };
  private TestListPanel testListPanel = new MyTestListPanel(subTestList, listener);

  @Test
  void addTestDataWithoutParam() {
    //given

    //when
    testListPanel.addTestData();

    //then
    assertEquals(1, subTestList.getMyTestListItems().size());
  }

  @Test
  void addTestDataWithParam() {
    //given
    String testInput = "testInput";
    String testOutput = "testOutput";
    String testResult = "testResult";
    TestData testData = new TestData(testInput, testOutput, testResult);

    //when
    testListPanel.addTestData(testData);

    //then
    List<MyTestListItem> items = subTestList.getMyTestListItems();
    assertEquals(1, items.size());
    MyTestListItem item = items.get(0);
    assertEquals(testInput, item.getTestData().getInput());
    assertEquals(testOutput, item.getTestData().getOutput());
    assertEquals(testResult, item.getTestData().getResult());
  }

  @Test
  void removeTest() {
    //given
    List<TestData> testDatas = List.of(
            new TestData("0", "0", "0")
            , new TestData("1", "1", "1")
            , new TestData("2", "2", "2")
            , new TestData("3", "3", "3")
            , new TestData("4", "4", "4")
            , new TestData("2", "2", "2"));
    testDatas.stream().forEach(subTestList::addTest);
    int removeIndex = 2;

    //when
    testListPanel.removeTestData(removeIndex);

    //then
    List<MyTestListItem> items = subTestList.getMyTestListItems();
    assertEquals(testDatas.size() - 1, items.size());
    for (int i = 0; i < items.size(); i++) {
      int index = i >= removeIndex ? i + 1 : i;
      assertEquals(testDatas.get(index).getInput(), items.get(i).getTestData().getInput());
      assertEquals(testDatas.get(index).getOutput(), items.get(i).getTestData().getOutput());
      assertEquals(testDatas.get(index).getResult(), items.get(i).getTestData().getResult());
    }
  }

  @Test
  void removeOutOfIndexTest() {
    //given
    List<TestData> testDatas = List.of(
            new TestData("0", "0", "0")
            , new TestData("1", "1", "1")
            , new TestData("2", "2", "2")
            , new TestData("3", "3", "3")
            , new TestData("4", "4", "4")
            , new TestData("2", "2", "2"));
    testDatas.stream().forEach(subTestList::addTest);
    int removeIndex = testDatas.size() + 10;

    //when
    testListPanel.removeTestData(removeIndex);

    //then
    List<MyTestListItem> items = subTestList.getMyTestListItems();
    assertEquals(testDatas.size(), items.size());
    for (int i = 0; i < items.size(); i++) {
      assertEquals(testDatas.get(i).getInput(), items.get(i).getTestData().getInput());
      assertEquals(testDatas.get(i).getOutput(), items.get(i).getTestData().getOutput());
      assertEquals(testDatas.get(i).getResult(), items.get(i).getTestData().getResult());
    }
  }

  @Test
  void removeAllTests() {
    //given
    List<TestData> testDatas = List.of(
            new TestData("0", "0", "0")
            , new TestData("1", "1", "1")
            , new TestData("2", "2", "2")
            , new TestData("3", "3", "3")
            , new TestData("4", "4", "4")
            , new TestData("2", "2", "2"));
    testDatas.stream().forEach(subTestList::addTest);

    //when
    testListPanel.removeAllTestDatas();

    //then
    assertEquals(0, subTestList.getMyTestListItems().size());
  }

  @Test
  void getMyTestListItemsTest() {
    //given
    List<TestData> testDatas = List.of(
            new TestData("0", "0", "0")
            , new TestData("1", "1", "1")
            , new TestData("2", "2", "2")
            , new TestData("3", "3", "3")
            , new TestData("4", "4", "4")
            , new TestData("2", "2", "2"));
    testDatas.stream().forEach(subTestList::addTest);

    //when
    List<MyTestListItem> items = testListPanel.getMyTestListItems();
    assertEquals(testDatas.size(), items.size());
  }

  @Test
  void getMyTestListItemWithSelectedIndexTest() {
    //given
    List<TestData> testDatas = List.of(
            new TestData("0", "0", "0")
            , new TestData("1", "1", "1")
            , new TestData("2", "2", "2")
            , new TestData("3", "3", "3")
            , new TestData("4", "4", "4")
            , new TestData("2", "2", "2"));
    testDatas.stream().forEach(subTestList::addTest);
    int selectedIndex = 2;

    //when
    MyTestListItem item = testListPanel.getMyTestList(selectedIndex);
    assertEquals(testDatas.get(selectedIndex).getInput(), item.getTestData().getInput());
    assertEquals(testDatas.get(selectedIndex).getOutput(), item.getTestData().getOutput());
    assertEquals(testDatas.get(selectedIndex).getResult(), item.getTestData().getResult());
  }

  @Test
  void getMyTestListItemWithNoneSelectTest() {
    //given
    List<TestData> testDatas = List.of(
            new TestData("0", "0", "0")
            , new TestData("1", "1", "1")
            , new TestData("2", "2", "2")
            , new TestData("3", "3", "3")
            , new TestData("4", "4", "4")
            , new TestData("2", "2", "2"));
    testDatas.stream().forEach(subTestList::addTest);
    int selectedIndex = -1;//select none item

    //when
    MyTestListItem item = testListPanel.getMyTestList(selectedIndex);
    assertNull(item);
  }

  @Test
  void getMyTestListItemWithOverIndexTest() {
    //given
    List<TestData> testDatas = List.of(
            new TestData("0", "0", "0")
            , new TestData("1", "1", "1")
            , new TestData("2", "2", "2")
            , new TestData("3", "3", "3")
            , new TestData("4", "4", "4")
            , new TestData("2", "2", "2"));
    testDatas.stream().forEach(subTestList::addTest);
    int selectedIndex = testDatas.size() + 10;

    //when
    MyTestListItem item = testListPanel.getMyTestList(selectedIndex);
    assertNull(item);
  }
}