/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.toolbar;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.ui.roots.ToolbarPanel;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public abstract class ActionToolbarPanel extends ToolbarPanel implements Toolbar {
  public ActionToolbarPanel(@NotNull JComponent contentComponent, @NotNull ActionGroup actions, @NotNull @NonNls String toolbarPlace, @Nullable JComponent targetComponent) {
    super(contentComponent, actions, toolbarPlace, targetComponent);
  }
}
