/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ComponentManagerTest {
  @Nested
  @DisplayName("[GetInstance]")
  class GetInstanceTest {
    @Test
    @DisplayName("Success : Get an instance")
    void getInstance_Success() {
      ComponentManager instance = ComponentManager.getInstance();
      assertNotNull(instance);
    }

    @Test
    @DisplayName("Success : Singleton pattern")
    void singletonPattern_Success() {
      ComponentManager instance1 = ComponentManager.getInstance();
      ComponentManager instance2 = ComponentManager.getInstance();
      assertEquals(instance1, instance2);
    }

  }

  @Nested
  @DisplayName("[AddComponent]")
  class AddComponentTest {
    @Test
    @DisplayName("Success : Add JComponent in ComponentManager")
    void addComponent_Success() {
      JComponent mockComponent = Mockito.mock(JComponent.class);
      String name = "mockComponent";
      ComponentManager.getInstance().addComponent(name, mockComponent);
      assertEquals(mockComponent, ComponentManager.getInstance().getComponent(name));
    }

    @Test
    @DisplayName("Success : Overwrite with the same name")
    void overwriteWithTheSameName_Success() {
      JComponent mockComponent = Mockito.mock(JComponent.class);
      JComponent mockForOverwrite = Mockito.mock(JComponent.class);
      String name = "mockComponent";
      assertNotEquals(mockComponent, mockForOverwrite);

      ComponentManager.getInstance().addComponent(name, mockComponent);
      ComponentManager.getInstance().addComponent(name, mockForOverwrite);

      assertEquals(mockForOverwrite, ComponentManager.getInstance().getComponent(name));
    }
  }

  @Nested
  @DisplayName("[GetComponent]")
  class GetComponentTest {
    @Test
    @DisplayName("Success : Get Component correctly")
    void getComponent_Success() {
      JComponent mockComponent = Mockito.mock(JComponent.class);
      String name = "mockComponent";

      ComponentManager.getInstance().addComponent(name, mockComponent);

      assertEquals(mockComponent, ComponentManager.getInstance().getComponent(name));
    }

    @Test
    @DisplayName("Fail : Throw error when component's name does not exist")
    void throwErrorWhenNameDoesNotExist_Failure() {
      assertThrows(IllegalArgumentException.class, () -> ComponentManager.getInstance().getComponent("notExistName"));
    }
  }

  @Nested
  @DisplayName("[RemoveChildrenComponentsByName]")
  class RemoveChildrenComponentsByNameTest {
    @Test
    @DisplayName("Success : Remove all exist component's children component")
    void removeAllChildrenComponents_Success() {
      JComponent children1 = new JLabel("children1");
      JComponent children2 = new JLabel("children2");
      JComponent children3 = new JLabel("children3");

      JComponent component = new JPanel();
      component.add(children1);
      component.add(children2);
      component.add(children3);
      ComponentManager.getInstance().addComponent("component", component);

      assertEquals(component.getComponents().length, 3);

      ComponentManager.getInstance().removeChildrenComponentsByName("component");
      assertEquals(component.getComponents().length, 0);
    }

    @Test
    @DisplayName("Fail : Remove not exist component's children component")
    void removeAllChildrenComponents_Failure() {
      assertThrows(IllegalArgumentException.class, () -> ComponentManager.getInstance().removeChildrenComponentsByName("notExist"));
    }
  }

  @Nested
  @DisplayName("[RemoveChildrenComponents]")
  class RemoveChildrenComponentsTest {
    @Test
    @DisplayName("Success : Remove all exist component's children component")
    void removeAllChildrenComponents_Success() {
      JComponent children1 = new JLabel("children1");
      JComponent children2 = new JLabel("children2");
      JComponent children3 = new JLabel("children3");

      JComponent component = new JPanel();
      component.add(children1);
      component.add(children2);
      component.add(children3);
      ComponentManager.getInstance().addComponent("component", component);

      assertEquals(component.getComponents().length, 3);

      ComponentManager.getInstance().removeChildrenComponents(component);
      assertEquals(component.getComponents().length, 0);
    }

    @Test
    @DisplayName("Fail : Remove null component")
    void removeAllChildrenComponents_Failure() {
      assertThrows(RuntimeException.class, () -> ComponentManager.getInstance().removeChildrenComponents(null));
    }
  }
}