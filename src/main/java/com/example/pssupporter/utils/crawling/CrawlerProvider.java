/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils.crawling;

import com.example.pssupporter.utils.crawling.vo.Site;

import java.util.Arrays;

public enum CrawlerProvider {
  BAEKJOON(Site.BAEKJOON_OJ, new BaekjoonCrawler("https://www.acmicpc.net/problem/", "sampledata"));

  private final Site mySite;
  private final Crawler myCrawler;

  CrawlerProvider(Site mySite, Crawler myCrawler) {
    this.mySite = mySite;
    this.myCrawler = myCrawler;
  }

  public static Crawler getCrawler(Site site) {
    return Arrays.stream(CrawlerProvider.values())
            .filter(cw -> cw.getSite().equals(site))
            .findFirst()
            .map(CrawlerProvider::getCrawler)
            .orElse(null);
  }

  private Site getSite() {
    return mySite;
  }

  private Crawler getCrawler() {
    return myCrawler;
  }
}
