/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.ui.list.panel;

import com.example.pssupporter.ui.list.MyTestListItem;
import com.example.pssupporter.ui.list.function.SubTestListFunction;
import com.intellij.ui.components.JBList;

public abstract class SubTestList extends JBList<MyTestListItem> implements SubTestListFunction {
}
