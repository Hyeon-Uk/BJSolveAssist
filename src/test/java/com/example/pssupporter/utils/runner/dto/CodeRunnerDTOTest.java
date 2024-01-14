/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils.runner.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodeRunnerDTOTest {
  @Nested
  @DisplayName("[Response]")
  class ResponseTest {
    @Nested
    @DisplayName("[Output]")
    class OutputTest {
      @Test
      @DisplayName("Success : Output setting with removing carriage return character and last new line character")
      void removeAllSpecialCharacter_Success() {
        String str = "hello\r\nmy name\r\nis hyeon uk\n";
        String expected = "hello\nmy name\nis hyeon uk";

        CodeRunnerDTO.Response response = new CodeRunnerDTO.Response(str);

        assertEquals(expected, response.getOutput());
      }
    }
  }
}