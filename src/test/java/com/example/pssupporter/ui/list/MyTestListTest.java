/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.list;

import com.example.pssupporter.vo.TestData;
import com.example.pssupporter.vo.TestStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

class MyTestListTest {
  private MyCellRenderer myCellRenderer = new MyCellRenderer();
  private MyTestList myTestList = new MyTestList(myCellRenderer);

  @Test
  void addTestWithoutParam() {
    //given

    //when
    myTestList.addTest();

    //then
    List<MyTestListItem> myTestListItems = myTestList.getMyTestListItems();
    assertEquals(1, myTestListItems.size());
    MyTestListItem item = myTestListItems.get(0);
    assertEquals("", item.getTestData().getInput());
    assertEquals("", item.getTestData().getOutput());
    assertEquals("", item.getTestData().getResult());
    assertEquals(TestStatus.READY, item.getStatus());
  }

  @Test
  void addTestWithParam() {
    //given
    String testInput = "testInput";
    String testOutput = "testOutput";
    String testResult = "testResult";
    TestData testData = new TestData(testInput, testOutput, testResult);

    //when
    myTestList.addTest(testData);

    //then
    List<MyTestListItem> myTestListItems = myTestList.getMyTestListItems();
    assertEquals(1, myTestListItems.size());
    MyTestListItem item = myTestListItems.get(0);
    assertEquals(testInput, item.getTestData().getInput());
    assertEquals(testOutput, item.getTestData().getOutput());
    assertEquals(testResult, item.getTestData().getResult());
    assertEquals(TestStatus.READY, item.getStatus());
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
    testDatas.stream().forEach(myTestList::addTest);
    int removeIndex = 2;

    //when
    myTestList.removeTest(removeIndex);

    //then
    List<MyTestListItem> items = myTestList.getMyTestListItems();
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
    testDatas.stream().forEach(myTestList::addTest);
    int removeIndex = testDatas.size() + 10;

    //when
    myTestList.removeTest(removeIndex);

    //then
    List<MyTestListItem> items = myTestList.getMyTestListItems();
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
    testDatas.stream().forEach(myTestList::addTest);

    //when
    myTestList.removeAllTests();

    //then
    assertEquals(0, myTestList.getMyTestListItems().size());
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
    testDatas.stream().forEach(myTestList::addTest);

    //when
    List<MyTestListItem> items = myTestList.getMyTestListItems();
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
    testDatas.stream().forEach(myTestList::addTest);
    int selectedIndex = 2;

    //when
    MyTestListItem item = myTestList.getMyTestListItem(selectedIndex);
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
    testDatas.stream().forEach(myTestList::addTest);
    int selectedIndex = -1;//select none item

    //when
    MyTestListItem item = myTestList.getMyTestListItem(selectedIndex);
    assertNull(item);
  }
}