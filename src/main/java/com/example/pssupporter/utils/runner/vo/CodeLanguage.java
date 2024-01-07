/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils.runner.vo;

import com.intellij.psi.PsiJavaFile;

public enum CodeLanguage {
  JAVA(PsiJavaFile.class, ".java");
  private final Class<?> myFileType;
  private final String myExtension;

  CodeLanguage(Class<?> myFileType, String myExtension) {
    this.myFileType = myFileType;
    this.myExtension = myExtension;
  }

  public Class<?> getFileType() {
    return myFileType;
  }

  public String getExtension() {
    return myExtension;
  }
}
