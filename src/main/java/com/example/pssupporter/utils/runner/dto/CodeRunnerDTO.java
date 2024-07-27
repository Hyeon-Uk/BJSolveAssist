/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils.runner.dto;

import com.example.pssupporter.utils.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CodeRunnerDTO {
  public static class Request {
    private String myInput;
    private String myFilePath;

    public Request(String myFilePath, String myInput) {
      this.myFilePath = myFilePath;
      this.myInput = myInput;
    }


    public String getFilePath() {
      return myFilePath;
    }


    public String getMyInput() {
      return myInput;
    }

  }

  public static class Response {
    private String myOutput;

    public Response(String myOutput) {
      String removedCarriageReturn = StringUtils.removeCarriageReturn(myOutput);
      String[] lines = removedCarriageReturn.split("\n");

      this.myOutput = Arrays.stream(lines)
              .filter(line->!StringUtils.isBlank(line))
              .map(StringUtils::rTrim)
              .collect(Collectors.joining("\n"));
    }

    public String getOutput() {
      return myOutput;
    }
  }
}
