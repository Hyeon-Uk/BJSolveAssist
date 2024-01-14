/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringUtilsTest {
  private final String emptyString = "";
  private final String blankString = "         \n       \r \n";
  private final String nullString = null;

  @Nested
  @DisplayName("[RTrim]")
  class RTrimTest {
    @Test
    @DisplayName("Success : RTrim the right whitespace of valid data")
    void success() {
      String data = " \nhello world!  \n\r";
      String expected = " \nhello world!";
      assertEquals(expected, StringUtils.rTrim(data));
    }

    @Test
    @DisplayName("Success : RTrim the blankstring")
    void blankString_Success() {
      String expected = "";
      assertEquals(expected, StringUtils.rTrim(blankString));
    }

    @Test
    @DisplayName("Fail : Return error when the input data is null")
    void throwErrorWithNullInput_Failure() {
      assertThrows(IllegalArgumentException.class, () ->
              StringUtils.rTrim(nullString));
    }

  }

  @Nested
  @DisplayName("[IsNull]")
  class IsNullTest {
    @Test
    @DisplayName("Success : Return true when the input string is null")
    void returnTrueWithNullString_Success() {
      assertTrue(StringUtils.isNull(nullString));
    }

    @Test
    @DisplayName("Success :Return false when the input string is not null")
    void returnFalseWithNotNullString_Success() {
      String data = "NotNullString";
      assertFalse(StringUtils.isNull(data));
    }
  }

  @Nested
  @DisplayName("[IsEmpty]")
  class IsEmptyTest {
    @Test
    @DisplayName("Success : Return true when the input string is empty")
    void returnTrueWithEmptyString_Success() {
      assertTrue(StringUtils.isEmpty(emptyString));
    }

    @Test
    @DisplayName("Success : Return true when the input string is null")
    void returnTrueWithNullString_Success() {
      assertTrue(StringUtils.isEmpty(nullString));
    }

    @Test
    @DisplayName("Success : Return false when the input string is not empty")
    void returnFalseWithNotEmptyString_Success() {
      String data = "NotEmpty";
      assertFalse(StringUtils.isEmpty(data));
    }
  }

  @Nested
  @DisplayName("[IsBlank]")
  class IsBlankTest {
    @Test
    @DisplayName("Success : Return true when the input string is blank")
    void returnTrueWithBlankString_Success() {
      assertTrue(StringUtils.isBlank(blankString));
    }

    @Test
    @DisplayName("Success : Return true when the input string is empty")
    void returnTrueWithEmptyString_Success() {
      assertTrue(StringUtils.isBlank(emptyString));
    }

    @Test
    @DisplayName("Success : Return true when the input string is null")
    void returnTrueWithNullString_Success() {
      assertTrue(StringUtils.isBlank(nullString));
    }

    @Test
    @DisplayName("Success : Return false when the input string is not blank")
    void returnFalseWithNotBlankString_Success() {
      String notBlankString = " Hello world! \n\r";
      assertFalse(StringUtils.isBlank(notBlankString));
    }
  }

  @Nested
  @DisplayName("[RemoveCarriageReturn]")
  class removeCarriageReturnTest {
    @Test
    @DisplayName("Success : Remove all carriage return characters")
    void removeAllCarriageReturnCharacters_Success() {
      String str = "hello\r\nworld!\n";
      String expected = "hello\nworld!\n";

      assertEquals(expected, StringUtils.removeCarriageReturn(str));
    }

    @Test
    @DisplayName("Fail : Throw error when string is null")
    void throwsErrorWhenStringIsNull_Failure() {
      assertThrows(IllegalArgumentException.class, () -> {
        StringUtils.removeCarriageReturn(null);
      });
    }
  }
}
