/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public class IntellijUtils {
  private IntellijUtils() {
  }

  public static Optional<FileEditor> getSelectedEditor(@NotNull Project project) {
    return Optional.ofNullable(FileEditorManager.getInstance(Objects.requireNonNull(project)))
            .flatMap(manager -> Optional.ofNullable(manager.getSelectedEditor()));
  }

  public static Optional<VirtualFile> getSelectedFile(@NotNull Project project) {
    return getSelectedEditor(project)
            .flatMap(selectedEditor -> Optional.ofNullable(selectedEditor.getFile()));
  }

  public static boolean checkType(@NotNull Object object, @NotNull Class<?> clazz) {
    return clazz.isAssignableFrom(object.getClass());
  }

  public static void autoSaveFile(@NotNull VirtualFile virtualFile) {
    FileDocumentManager manager = FileDocumentManager.getInstance();
    Document document = manager.getDocument(virtualFile);
    if (document != null) {
      manager.saveDocument(document);
    }
  }
}
