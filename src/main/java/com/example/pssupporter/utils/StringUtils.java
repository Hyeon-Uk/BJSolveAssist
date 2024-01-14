/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class StringUtils {
  private StringUtils() {
  }

  private static final Pattern RTRIM = Pattern.compile("\\s+$");
  private static final String EMPTY_STRING = "";

  public static String removeCarriageReturn(@NotNull String str) {
    if (str == null) {
      throw new IllegalArgumentException("Arguement must not be null");
    }
    return str.replaceAll("\r", "");
  }

  public static String rTrim(@NotNull String str) {
    try {
      return RTRIM.matcher(str).replaceAll(EMPTY_STRING);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Argument must not be null");
    }
  }

  public static boolean isNull(String str) {
    return str == null;
  }

  public static boolean isEmpty(String str) {
    return str == null || str.isEmpty();
  }

  public static boolean isBlank(String str) {
    return str == null || str.trim().isEmpty();
  }
}
