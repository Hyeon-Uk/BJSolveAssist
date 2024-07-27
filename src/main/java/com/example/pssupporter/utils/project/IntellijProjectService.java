/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils.project;

import com.example.pssupporter.utils.IntellijUtils;
import com.example.pssupporter.utils.project.vo.CustomFile;
import com.intellij.openapi.project.Project;

import java.util.Optional;

public class IntellijProjectService implements ProjectService {
  private final Project project;

  public IntellijProjectService(Project project) {
    this.project = project;
  }

  @Override
  public void saveFile() {
    IntellijUtils.getSelectedFile(project)
            .ifPresent(IntellijUtils::autoSaveFile);
  }

  @Override
  public Optional<CustomFile> getSelectedFile() {
    return IntellijUtils.getSelectedFile(project)
            .map(file -> CustomFile.builder()
                    .path(file.getPath())
                    .name(file.getName())
                    .build());
  }
}
