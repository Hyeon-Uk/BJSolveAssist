/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.list;

import com.intellij.icons.AllIcons;

import javax.swing.*;
import java.awt.*;

public class MyCellRenderer extends JPanel implements ListCellRenderer<MyTestListItem> {
  private final JLabel myStatusLabel;

  public MyCellRenderer() {
    this.setLayout(new GridLayout());

    myStatusLabel = new JLabel(AllIcons.Actions.Execute);
    myStatusLabel.setOpaque(false);
    this.add(myStatusLabel, BorderLayout.EAST);
  }

  @Override
  public Component getListCellRendererComponent(JList<? extends MyTestListItem> list, MyTestListItem value, int index, boolean isSelected, boolean cellHasFocus) {
    myStatusLabel.setIcon(value.getStatus().getIcon());

    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    } else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }

    return this;
  }
}
