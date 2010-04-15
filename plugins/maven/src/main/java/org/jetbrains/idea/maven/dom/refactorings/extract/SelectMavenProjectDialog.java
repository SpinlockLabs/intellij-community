/*
 * Copyright 2000-2010 JetBrains s.r.o.
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
package org.jetbrains.idea.maven.dom.refactorings.extract;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.idea.maven.dom.MavenDomBundle;
import org.jetbrains.idea.maven.dom.MavenDomUtil;
import org.jetbrains.idea.maven.dom.model.MavenDomProjectModel;
import org.jetbrains.idea.maven.project.MavenProject;
import org.jetbrains.idea.maven.utils.ComboBoxUtil;

import javax.swing.*;
import java.util.Arrays;
import java.util.Set;

public class SelectMavenProjectDialog extends DialogWrapper {
 private final Set<MavenDomProjectModel> myMavenDomProjectModels;

  private JComboBox myMavenProjectsComboBox;
  private JPanel myMainPanel;

  public SelectMavenProjectDialog(@NotNull Project project,
                                 @NotNull Set<MavenDomProjectModel> mavenDomProjectModels) {
    super(project, true);
    myMavenDomProjectModels = mavenDomProjectModels;

    setTitle(MavenDomBundle.message("choose.project"));
    init();
  }

   protected Action[] createActions() {
    return new Action[]{getOKAction(), getCancelAction()};
  }

  protected void init() {
    super.init();
    updateOkStatus();
  }

  @Nullable
  public MavenDomProjectModel getSelectedProject() {
    return (MavenDomProjectModel)ComboBoxUtil.getSelectedValue((DefaultComboBoxModel)myMavenProjectsComboBox.getModel());
  }

  protected JComponent createCenterPanel() {
    ComboBoxUtil.setModel(myMavenProjectsComboBox, new DefaultComboBoxModel(), myMavenDomProjectModels, new Function<MavenDomProjectModel, Pair<String, ?>>() {
        public Pair<String, ?> fun(MavenDomProjectModel model) {
          String projectName = model.getName().getStringValue();
          MavenProject mavenProject = MavenDomUtil.findProject(model);
          if (mavenProject != null) {
            projectName = mavenProject.getDisplayName();
          }
          if (StringUtil.isEmptyOrSpaces(projectName)) {
            projectName = "pom.xml";
          }
          return Pair.create(projectName, model);
        }
      });

    myMavenProjectsComboBox.setSelectedItem(0);

    return myMainPanel;
  }

  private void updateOkStatus() {
    setOKActionEnabled(getSelectedProject() != null);
  }

  public JComponent getPreferredFocusedComponent() {
    return myMavenProjectsComboBox;
  }
}