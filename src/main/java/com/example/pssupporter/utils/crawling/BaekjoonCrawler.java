/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils.crawling;

import com.example.pssupporter.vo.TestData;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class BaekjoonCrawler implements Crawler {
  private final String myBaseURL;
  private final String myTargetClassName;

  protected BaekjoonCrawler(String baseURL, String targetClassName) {
    myBaseURL = baseURL;
    myTargetClassName = targetClassName;
  }

  @Override
  public List<TestData> getExamples(long number) {
    try {
      Connection con = establishConnection(number);
      Elements sampleDataElements = extractSampleData(con);

      return transformToTestDataList(sampleDataElements);
    } catch (IOException | IllegalArgumentException e) {
      return Collections.emptyList();
    }
  }

  private Connection establishConnection(long number) {
    try {
      return Jsoup.connect(myBaseURL.concat(Long.toString(number)));
    } catch (NumberFormatException numberFormatException) {
      throw new IllegalArgumentException();
    }
  }

  private Elements extractSampleData(Connection con) throws IOException {
    return con.get().body().getElementsByClass(myTargetClassName);
  }

  private List<TestData> transformToTestDataList(Elements sampleDataElements) {
    return IntStream.range(0, sampleDataElements.size() / 2)
            .mapToObj(i -> new TestData(sampleDataElements.get(i * 2).text(), sampleDataElements.get(i * 2 + 1).text()))
            .toList();
  }
}
