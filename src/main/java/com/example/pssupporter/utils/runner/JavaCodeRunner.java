/*
 * Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.pssupporter.utils.runner;


import com.example.pssupporter.utils.StreamUtils;
import com.example.pssupporter.utils.StringUtils;
import com.example.pssupporter.utils.runner.dto.CodeRunnerDTO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class JavaCodeRunner implements CodeRunner {
  protected JavaCodeRunner() {
  }

  @Override
  public CodeRunnerDTO.Response runCode(CodeRunnerDTO.Request request) {
    String filePath = request.getFilePath();
    String input = request.getMyInput();
    String output = "";
    Process process = null;
    try {
      ProcessBuilder processBuilder = new ProcessBuilder("java", "-Dfile.encoding=UTF-8", filePath);

      processBuilder.redirectErrorStream(true);
      processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);
      process = processBuilder.start();
      process.getOutputStream().write(input.getBytes(StandardCharsets.UTF_8));
      process.getOutputStream().close();

      boolean processCompleted = process.waitFor(10, TimeUnit.SECONDS);

      if (!processCompleted) {
        output = "Time Out";
      } else {
        output = StreamUtils.readStream(process.getInputStream());
      }
    } catch (IOException | InterruptedException e) {
      output = StringUtils.isBlank(e.getMessage()) ? "Stop" : e.getMessage();
    } finally {
      if (process != null) {
        process.destroy();
      }
    }
    return new CodeRunnerDTO.Response(StringUtils.rTrim(output));
  }
}
