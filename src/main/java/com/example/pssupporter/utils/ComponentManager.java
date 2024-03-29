/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ComponentManager {
  private static final ComponentManager ourComponentManager = new ComponentManager();
  private static final Map<String, JComponent> ourComponentMap = new HashMap<>();

  private ComponentManager() {
  }

  public static ComponentManager getInstance() {
    return ourComponentManager;
  }

  public void addComponent(String name, JComponent object) {
    ourComponentMap.put(name, object);
  }

  public JComponent getComponent(String componentName) {
    return Optional.ofNullable(ourComponentMap.get(componentName))
            .orElseThrow(() -> new IllegalArgumentException("Not exist component"));
  }

  public <T extends JComponent> T getComponent(String componentName, Class<T> clazz) {
    try {
      return clazz.cast(Optional.ofNullable(ourComponentMap.get(componentName))
              .orElseThrow(() -> new IllegalArgumentException("Not exist component")));
    } catch (ClassCastException e) {
      throw new IllegalArgumentException("Class Type Not Match");
    }
  }

  public void removeChildrenComponentsByName(String componentName) {
    JComponent component = getComponent(componentName);
    removeChildrenComponents(component);
  }

  public void removeChildrenComponents(@NotNull JComponent component) {
    component.removeAll();
    component.repaint();
  }
}
