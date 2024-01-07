/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils.runner;


import com.example.pssupporter.utils.runner.vo.CodeLanguage;

import java.util.Arrays;

public enum CodeRunnerProvider {
  JAVA(CodeLanguage.JAVA, new JavaCodeRunner());

  CodeRunnerProvider(CodeLanguage myCodeLanguage, CodeRunner myCodeRunner) {
    this.myCodeLanguage = myCodeLanguage;
    this.myCodeRunner = myCodeRunner;
  }

  public static CodeRunner getCodeRunner(CodeLanguage language) {
    return Arrays.stream(CodeRunnerProvider.values())
            .filter(provider -> provider.getCodeLanguage().equals(language))
            .findFirst()
            .map(CodeRunnerProvider::getCodeRunner)
            .orElse(null);
  }

  private final CodeLanguage myCodeLanguage;
  private final CodeRunner myCodeRunner;

  public CodeLanguage getCodeLanguage() {
    return myCodeLanguage;
  }

  public CodeRunner getCodeRunner() {
    return myCodeRunner;
  }
}
