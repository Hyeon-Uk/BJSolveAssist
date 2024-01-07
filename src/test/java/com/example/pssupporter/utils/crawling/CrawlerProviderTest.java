/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils.crawling;

import com.example.pssupporter.utils.crawling.vo.Site;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CrawlerProviderTest {
  @Nested
  @DisplayName("[GetCrawler]")
  class GetCrawlerTest {
    @Test
    @DisplayName("Success : Get Baekjoon site crawler with Site.BAEKJOON parameter")
    void baekjoonCrawlerWithBaekjoon_Success() {
      assertTrue(CrawlerProvider.getCrawler(Site.BAEKJOON_OJ) instanceof BaekjoonCrawler);
    }
  }
}