/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.swing.border.Border;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JTitleBorderFactoryTest {
  @Nested
  @DisplayName("[GetTitleBorder]")
  class GetTitleBorderTest {
    @Test
    @DisplayName("Success : Get an instance with title")
    void getAnInstanceWithTitle_Success() {
      Border border = JTitleBorderFactory.getBorder("test");
      assertNotNull(border);
    }

    @Test
    @DisplayName("Success : Get same instances")
    void getSameInstances_Success() {
      Border border1 = JTitleBorderFactory.getBorder("test");
      Border border2 = JTitleBorderFactory.getBorder("test");

      assertEquals(border1, border2);
    }
  }
}