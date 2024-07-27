/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils.project.vo;

public class CustomFile {
  private String path;
  private String name;

  public CustomFile(String path, String name) {
    this.path = path;
    this.name = name;
  }

  public CustomFile() {
  }

  public String getPath() {
    return path;
  }

  public String getName() {
    return name;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private CustomFile customFile = new CustomFile();

    public Builder path(String path) {
      customFile.path = path;
      return this;
    }

    public Builder name(String name) {
      customFile.name = name;
      return this;
    }

    public CustomFile build() {
      return this.customFile;
    }
  }
}
