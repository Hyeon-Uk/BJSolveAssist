/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.factory;


import javax.swing.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JLabelFactory {
  private static final Map<String, JLabel> ourLabelsWithText = new ConcurrentHashMap<>();

  private JLabelFactory() {
  }

  public static JLabel getJLabel(String text) {
    return ourLabelsWithText.computeIfAbsent(text, JLabel::new);
  }
}
