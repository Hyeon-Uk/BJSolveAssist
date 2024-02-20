/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.factory;

import com.intellij.ui.JBColor;

import javax.swing.*;
import javax.swing.border.Border;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JTitleBorderFactory {
  private static final Map<String, Border> ourBordersWithTitle = new ConcurrentHashMap<>();

  private JTitleBorderFactory() {
  }

  public static Border getBorder(String title) {
    return ourBordersWithTitle.computeIfAbsent(title, t -> BorderFactory.createTitledBorder(BorderFactory.createLineBorder(JBColor.DARK_GRAY), title));
  }
}
