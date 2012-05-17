/*
 * Copyright 2000-2012 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.core;

import com.intellij.openapi.application.ex.PathManagerEx;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.vfs.StandardFileSystems;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.UsefulTestCase;
import org.jdom.JDOMException;

import java.io.IOException;

/**
 * @author yole
 */
public class CoreModuleManagerTest extends UsefulTestCase {
  public void _testLoadingModules() throws IOException, JDOMException {
    CoreEnvironment env = new CoreEnvironment(getTestRootDisposable());
    ProjectModelEnvironment.register(env);
    final String projectPath = PathManagerEx.getTestDataPath("/compileServer/incremental/annotations/addAnnotationTarget");
    VirtualFile vFile = StandardFileSystems.local().findFileByPath(projectPath);
    CoreProjectLoader.loadProject(env.getProject(), vFile);
    final ModuleManager moduleManager = ModuleManager.getInstance(env.getProject());
    final Module[] modules = moduleManager.getModules();
    assertEquals(1, modules.length);
  }
}
