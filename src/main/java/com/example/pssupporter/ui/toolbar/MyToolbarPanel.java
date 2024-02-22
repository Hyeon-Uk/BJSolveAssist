/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.toolbar;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.wm.impl.welcomeScreen.BottomLineBorder;
import com.intellij.ui.components.JBPanel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class MyToolbarPanel extends ActionToolbarPanel {
  public MyToolbarPanel(@NotNull ActionGroup actions, JComponent component) {
    super(new JBPanel(new BorderLayout()), actions, "toolbarPlace", component);
    this.setBorder(new BottomLineBorder());
    this.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.setAlignmentY(Component.CENTER_ALIGNMENT);
  }
}
