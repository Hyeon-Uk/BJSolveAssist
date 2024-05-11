/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.vo;

import com.example.pssupporter.utils.StringUtils;

public class TestData {
  private String myInput;
  private String myOutput;
  private String myResult;

  public TestData() {
    setInput("");
    setOutput("");
    setResult("");
  }

  public TestData(String myInput, String myOutput) {
    setInput(myInput);
    setOutput(myOutput);
    setResult("");
  }

  public TestData(String myInput, String myOutput, String myResult) {
    setInput(myInput);
    setOutput(myOutput);
    setResult(myResult);
  }

  public String getInput() {
    return StringUtils.rTrim(StringUtils.removeCarriageReturn(myInput));
  }

  public String getOutput() {
    return StringUtils.rTrim(StringUtils.removeCarriageReturn(myOutput));
  }

  public void setInput(String myInput) {
    this.myInput = StringUtils.rTrim(StringUtils.removeCarriageReturn(myInput));
  }

  public void setOutput(String myOutput) {
    this.myOutput = StringUtils.rTrim(StringUtils.removeCarriageReturn(myOutput));
  }

  public void setResult(String myResult) {
    this.myResult = StringUtils.rTrim(StringUtils.removeCarriageReturn(myResult));
  }

  public String getResult() {
    return StringUtils.rTrim(StringUtils.removeCarriageReturn(myResult));
  }

  @Override
  public String toString() {
    return "TestData{" +
            "myInput='" + myInput + '\'' +
            ", myOutput='" + myOutput + '\'' +
            ", myResult='" + myResult + '\'' +
            '}';
  }
}
