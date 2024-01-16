/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter;

import com.example.pssupporter.ui.MyMainView;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.annotations.NotNull;

public class MyToolWindowFactory implements ToolWindowFactory {
  private MyMainView myMainView;

  @Override
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
    ApplicationManager.getApplication().invokeLater(() -> {
      ContentManager contentManager = toolWindow.getContentManager();
      ContentFactory contentFactory = ContentFactory.getInstance();

      myMainView = new MyMainView(project, toolWindow.getAnchor().isHorizontal());

      Content content = contentFactory.createContent(myMainView, "Supporter", false);

      contentManager.addContent(content);
      setupToolWindowEventListener(project);
    });
  }

  private void setupToolWindowEventListener(@NotNull Project project) {
    MessageBusConnection connect = project.getMessageBus().connect();
    connect.subscribe(ToolWindowManagerListener.TOPIC, new ToolWindowManagerListener() {
      @Override
      public void stateChanged(@NotNull ToolWindowManager toolWindowManager, @NotNull ToolWindowManagerEventType changeType) {
        ToolWindowManagerListener.super.stateChanged(toolWindowManager, changeType);
        if (ToolWindowManagerEventType.SetSideToolAndAnchor.equals(changeType)) {
          ToolWindow baekjoonSupporter = toolWindowManager.getToolWindow("BaekjoonSupporter");

          if (baekjoonSupporter.getAnchor() != null) {
            ToolWindowAnchor anchor = baekjoonSupporter.getAnchor();
            myMainView.setLayoutBasedOnOrientation(anchor.isHorizontal());
          }
        }
      }
    });
  }
}
