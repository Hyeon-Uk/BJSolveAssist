/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class StreamUtils {
  private StreamUtils() {
  }

  public static String readStream(InputStream is) {
    try (
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
    ) {
      StringBuilder result = new StringBuilder();
      int character = -1;
      while ((character = bufferedReader.read()) != -1) {
        result.append((char) character);
      }
      return result.toString();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
