/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IntellijUtilsTest {
  @Mock
  private Project project;
  @Mock
  private FileEditorManager fileEditorManager;
  @Mock
  private FileEditor fileEditor;
  @Mock
  private PsiManager psiManager;
  private MockedStatic<FileEditorManager> fileEditorManagerMockedStatic;
  private MockedStatic<PsiManager> psiManagerMockedStatic;

  @BeforeEach
  void init() {
    fileEditorManagerMockedStatic = Mockito.mockStatic(FileEditorManager.class);
    psiManagerMockedStatic = Mockito.mockStatic(PsiManager.class);
    when(FileEditorManager.getInstance(any(Project.class)))
            .thenReturn(fileEditorManager);
    when(PsiManager.getInstance(any(Project.class)))
            .thenReturn(psiManager);
  }

  @AfterEach
  void destroy() {
    fileEditorManagerMockedStatic.close();
    psiManagerMockedStatic.close();
  }

  @Nested
  @DisplayName("[CheckTypeTest]")
  class CheckTypeTest {
    @Test
    @DisplayName("Success when the received object's class matches the clazz")
    void success() {
      PsiJavaFile mockPsiJavaFile = Mockito.mock(PsiJavaFile.class);
      boolean result = IntellijUtils.checkType(mockPsiJavaFile, PsiJavaFile.class);
      assertTrue(result);
    }

    @Test
    @DisplayName("Fail : Failure when the received object's class does not match the clazz")
    void objectClassMismatch_Failure() {
      PsiFile mockPsiFile = Mockito.mock(PsiFile.class);
      boolean result = IntellijUtils.checkType(mockPsiFile, PsiJavaFile.class);
      assertFalse(result);
    }
  }

  @Nested
  @DisplayName("[GetSelectedEditor]")
  class GetSelectedEditorTest {
    @Test
    @DisplayName("Success : Get the selected file editor successfully when it is open")
    void success() {
      when(fileEditorManager.getSelectedEditor())
              .thenReturn(fileEditor);
      Optional<FileEditor> selectedEditor = IntellijUtils.getSelectedEditor(project);
      assertTrue(selectedEditor.isPresent());
      assertEquals(selectedEditor.get(), fileEditor);
    }

    @Test
    @DisplayName("Fail : Get the selected file editor when there is no selected editor")
    void noSelectedEditor_Failure() {
      when(fileEditorManager.getSelectedEditor())
              .thenReturn(null);
      Optional<FileEditor> selectedEditor = IntellijUtils.getSelectedEditor(project);
      assertFalse(selectedEditor.isPresent());
    }

    @Test
    @DisplayName("Fail : Get the selected file editor when the project is null")
    void nullProject_Failure() {
      project = null;
      assertThrows(IllegalArgumentException.class, () -> {
        IntellijUtils.getSelectedEditor(project);
      });
    }
  }

  @Nested
  @DisplayName("[GetSelectedFile]")
  class GetSelectedFileTest {
    @Test
    @DisplayName("Success : Get the selected file from the editor successfully")
    void success() {
      VirtualFile mockSelectedFile = Mockito.mock(VirtualFile.class);
      when(fileEditorManager.getSelectedEditor())
              .thenReturn(fileEditor);
      when(fileEditor.getFile())
              .thenReturn(mockSelectedFile);
      Optional<VirtualFile> result = IntellijUtils.getSelectedFile(project);
      assertTrue(result.isPresent());
      assertEquals(result.get(), mockSelectedFile);
    }

    @Test
    @DisplayName("Fail : Get the file when the selected file does not exist or there is no open SelectedEditor")
    void selectedFileNotExists_Failure() {
      when(fileEditorManager.getSelectedEditor())
              .thenReturn(null);
      Optional<VirtualFile> result = IntellijUtils.getSelectedFile(project);
      assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Fail : Get the file when the project is null")
    void nullProject_Failure() {
      project = null;
      assertThrows(IllegalArgumentException.class, () -> {
        IntellijUtils.getSelectedFile(project);
      });
    }
  }
}
