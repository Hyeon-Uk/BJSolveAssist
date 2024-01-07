/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StreamUtilsTest {
  @Nested
  @DisplayName("[ReadStream]")
  class ReadStreamTest {
    @Test
    @DisplayName("Success : Reading inputStream and converting it to String")
    void readInputStreamToString_Success() {
      String sampleData = "Hello everyone!\nThis is Hyeonuk\nNice to meet you!\n";
      InputStream is = new ByteArrayInputStream(sampleData.getBytes(StandardCharsets.UTF_8));

      assertEquals(sampleData, StreamUtils.readStream(is));
    }

    @Test
    @DisplayName("Fail : Reading inputStream with other encoding type")
    void readInputStreamWithOtherEncoding_Failure() {
      ByteArrayInputStream is = Mockito.mock(ByteArrayInputStream.class);
      when(is.read()).thenThrow(new IOException());

      assertThrows(RuntimeException.class, () -> StreamUtils.readStream(is));
    }
  }
}