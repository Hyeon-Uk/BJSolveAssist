/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.actions;

import com.example.pssupporter.utils.thread.GlobalThreadStore;
import com.example.pssupporter.utils.thread.vo.ThreadGroupName;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class MyLoadTestDataAction extends AnAction {
  @Override
  public void update(@NotNull AnActionEvent e) {
    super.update(e);
    boolean isRunning = GlobalThreadStore.getInstance()
            .hasRunningThreads(ThreadGroupName.TEST_RUNNING);
    e.getPresentation().setEnabled(!isRunning);
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    Messages.showMessageDialog("Currently, this feature is not available because Baekjun has banned scraping.\nReference : <a href=\"https://help.acmicpc.net/rule\">BOJ Homepage</a>","Caution", AllIcons.Actions.Exit);
    return;
//    MyTestListPanel myTestListPanel = ComponentManager.getInstance().getComponent("myTestListPanel", MyTestListPanel.class);
//    String selected = Messages.showInputDialog(e.getProject(), "Input Baekjoon number", "Load Test Data", null);
//    if (!StringUtils.isBlank(selected)) {
//      ComponentManager.getInstance().removeChildrenComponentsByName("myEditorPanel");
//
//      try {
//        long number = Long.parseLong(selected);
//        List<TestData> examples = CrawlerProvider.getCrawler(Site.BAEKJOON_OJ).getExamples(number);
//
//        myTestListPanel.removeAllTests();
//
//        for (TestData testData : examples) {
//          myTestListPanel.addTest(testData);
//        }
//
//      } catch (NumberFormatException ne) {
//        Messages.showMessageDialog(e.getProject(), "Input number!", "Load Test Error", null);
//      }
//    }
  }
}
