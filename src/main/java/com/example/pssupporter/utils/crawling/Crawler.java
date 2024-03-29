/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils.crawling;


import com.example.pssupporter.vo.TestData;

import java.util.List;

public interface Crawler {
  List<TestData> getExamples(long number);
}
