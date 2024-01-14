/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestDataTest {
  @Nested
  @DisplayName("[Input]")
  class InputTest {
    @Test
    @DisplayName("Input single line")
    void inputSingleLine_Success() {
      String origin = "origin stream\r\n";
      String expected = "origin stream";
      TestData testData = new TestData(origin, origin);

      assertEquals(expected, testData.getInput());
    }

    @Test
    @DisplayName("Input multiple lines")
    void inputMultipleLines_Success() {
      String origin = "origin stream\r\n" +
              "hello world\n\r" +
              "for the test\n\r";
      String expected = "origin stream\n" +
              "hello world\n" +
              "for the test";
      TestData testData = new TestData(origin, origin);
      assertEquals(expected, testData.getInput());
    }
  }

  @Nested
  @DisplayName("[Output]")
  class OutputTest {
    @Test
    @DisplayName("Output single line")
    void outputSingleLine_Success() {
      String origin = "origin stream\r\n";
      String expected = "origin stream";
      TestData testData = new TestData(origin, origin);

      assertEquals(expected, testData.getOutput());
    }

    @Test
    @DisplayName("Output multiple lines")
    void outputMultipleLines_Success() {
      String origin = "origin stream\r\n" +
              "hello world\n\r" +
              "for the test\n\r";
      String expected = "origin stream\n" +
              "hello world\n" +
              "for the test";
      TestData testData = new TestData(origin, origin);
      assertEquals(expected, testData.getOutput());
    }
  }
}