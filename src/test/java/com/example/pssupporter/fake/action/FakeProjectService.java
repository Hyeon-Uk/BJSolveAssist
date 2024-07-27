/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.fake.action;

import com.example.pssupporter.utils.project.ProjectService;
import com.example.pssupporter.utils.project.vo.CustomFile;

import java.util.Optional;

public class FakeProjectService implements ProjectService {
  private CustomFile customFile;
  private boolean isSelected;
  public FakeProjectService(CustomFile customFile,boolean isSelected){
    this.customFile = customFile;
    this.isSelected = isSelected;
  }
  @Override
  public void saveFile() {

  }

  @Override
  public Optional<CustomFile> getSelectedFile() {
    return Optional.ofNullable(isSelected ? customFile : null);
  }
}
