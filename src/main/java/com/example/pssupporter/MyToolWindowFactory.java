/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter;

import com.example.pssupporter.ui.MyMainView;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import org.jetbrains.annotations.NotNull;

public class MyToolWindowFactory implements ToolWindowFactory {

  @Override
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
    ApplicationManager.getApplication().invokeLater(() -> {
      ContentManager contentManager = toolWindow.getContentManager();
      ContentFactory contentFactory = ContentFactory.getInstance();

      MyMainView myMainView = new MyMainView(project);

      Content content = contentFactory.createContent(myMainView, "Supporter", false);

      contentManager.addContent(content);
    });
  }
}
