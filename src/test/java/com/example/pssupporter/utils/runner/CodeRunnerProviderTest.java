/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils.runner;

import com.example.pssupporter.utils.runner.vo.CodeLanguage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CodeRunnerProviderTest {
  @Nested
  @DisplayName("[GetCodeRunnerTest]")
  class GetCodeRunnerTest {
    @Test
    @DisplayName("Success : Get JavaCodeRunner with CodeLanguage.JAVA parameter")
    void javaCodeRunnerWithJava_Success() {
      assertTrue(CodeRunnerProvider.getCodeRunner(CodeLanguage.JAVA) instanceof JavaCodeRunner);
    }
  }
}