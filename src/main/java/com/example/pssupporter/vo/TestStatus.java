/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.vo;

import com.intellij.icons.AllIcons;

import javax.swing.*;

public enum TestStatus {
  READY("Ready", AllIcons.Actions.Execute), RUNNING("Running", AllIcons.Actions.Pause),
  ACCEPT("Success", AllIcons.General.InspectionsOK), FAIL("Fail", AllIcons.General.ExclMark),
  STOP("Stop", AllIcons.Actions.Cancel);

  private final String myKey;
  private final Icon myIcon;

  TestStatus(String myKey, Icon myIcon) {
    this.myKey = myKey;
    this.myIcon = myIcon;
  }

  public String getKey() {
    return myKey;
  }

  public Icon getIcon() {
    return myIcon;
  }
}
