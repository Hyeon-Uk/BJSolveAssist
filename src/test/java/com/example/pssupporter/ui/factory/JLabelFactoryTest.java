/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JLabelFactoryTest {
  @Nested
  @DisplayName("[GetJLabelWithText]")
  class GetJLabelWithTextTest {
    @Test
    @DisplayName("Success : Get an instance with text")
    void getAnInstanceWithText_Success() {
      JLabel jLabel = JLabelFactory.getJLabel("test");
      assertNotNull(jLabel);
      assertEquals("test", jLabel.getText());
    }

    @Test
    @DisplayName("Success : Get same instances")
    void getSameInstances_Success() {
      JLabel jLabel1 = JLabelFactory.getJLabel("test");
      JLabel jLabel2 = JLabelFactory.getJLabel("test");

      assertEquals(jLabel1, jLabel2);
    }
  }
}