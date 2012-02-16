/*
 * CompilePdfPreferencesPane.java
 *
 * Copyright (C) 2009-11 by RStudio, Inc.
 *
 * This program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 *
 */
package org.rstudio.studio.client.workbench.prefs.views;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import org.rstudio.core.client.prefs.PreferencesDialogBaseResources;
import org.rstudio.studio.client.common.latex.LatexProgramSelectWidget;
import org.rstudio.studio.client.common.rnw.RnwWeaveSelectWidget;
import org.rstudio.studio.client.workbench.prefs.model.RPrefs;
import org.rstudio.studio.client.workbench.prefs.model.UIPrefs;
import org.rstudio.studio.client.workbench.prefs.model.CompilePdfPrefs;

public class CompilePdfPreferencesPane extends PreferencesPane
{
   @Inject
   public CompilePdfPreferencesPane(UIPrefs prefs,
                                    PreferencesDialogResources res)
   {
      prefs_ = prefs;
      res_ = res;
      PreferencesDialogBaseResources baseRes = PreferencesDialogBaseResources.INSTANCE;

      Label programDefaultsLabel = new Label(
                           "Program defaults (when not in a project)");
      programDefaultsLabel.addStyleName(baseRes.styles().headerLabel());
      nudgeRight(programDefaultsLabel);
      add(programDefaultsLabel);
            
      defaultSweaveEngine_ = new RnwWeaveSelectWidget();
      defaultSweaveEngine_.setValue(
                              prefs.defaultSweaveEngine().getGlobalValue());
      add(defaultSweaveEngine_);
      
      defaultLatexProgram_ = new LatexProgramSelectWidget();
      defaultLatexProgram_.setValue(
                              prefs.defaultLatexProgram().getGlobalValue());
      add(defaultLatexProgram_);
      
      Label perProjectLabel = new Label(
            "NOTE: The Rnw weave and LaTeX compilation options are also set on a " +
            "per-project (and optionally per-file) basis. Click the help " +
            "icons above for more details.");
           
      perProjectLabel.addStyleName(baseRes.styles().infoLabel());
      nudgeRight(perProjectLabel);
      spaced(perProjectLabel);
      add(perProjectLabel);
      
      Label compilationOptionsLabel = new Label("Compilation options");
      compilationOptionsLabel.addStyleName(baseRes.styles().headerLabel());
      nudgeRight(compilationOptionsLabel);
      add(compilationOptionsLabel);
      
      CheckBox chkConcordance = checkboxPref(
                                       "Always enable Rnw concordance",
                                       prefs_.alwaysEnableRnwConcordance());
      spaced(chkConcordance);
      add(chkConcordance);
      
      chkUseTexi2Dvi_ = new CheckBox( "Use texi2dvi for LaTeX compilation");
      spaced(chkUseTexi2Dvi_);
      add(chkUseTexi2Dvi_);
      
      chkCleanTexi2DviOutput_ = new CheckBox(
                                     "Clean auxiliary output after compile");
      spaced(chkCleanTexi2DviOutput_);
      add(chkCleanTexi2DviOutput_);
      
      chkEnableShellEscape_ = new CheckBox("Enable shell escape commands");
      add(chkEnableShellEscape_);
   }

  


   @Override
   public ImageResource getIcon()
   {
      return PreferencesDialogBaseResources.INSTANCE.iconWriting();
   }

   @Override
   public boolean validate()
   {
      return true;
   }

   @Override
   public String getName()
   {
      return "Compile PDF";
   }

   @Override
   protected void initialize(RPrefs prefs)
   {
      CompilePdfPrefs compilePdfPrefs = prefs.getCompilePdfPrefs();
      chkUseTexi2Dvi_.setValue(compilePdfPrefs.getUseTexi2Dvi());
      chkCleanTexi2DviOutput_.setValue(compilePdfPrefs.getCleanOutput());
      chkEnableShellEscape_.setValue(compilePdfPrefs.getEnableShellEscape());
   }
   
   @Override
   public boolean onApply(RPrefs rPrefs)
   {
      boolean requiresRestart = super.onApply(rPrefs);
      
      prefs_.defaultSweaveEngine().setGlobalValue(
                                    defaultSweaveEngine_.getValue());
      prefs_.defaultLatexProgram().setGlobalValue(
                                    defaultLatexProgram_.getValue());
         
      CompilePdfPrefs prefs = CompilePdfPrefs.create(
                                       chkUseTexi2Dvi_.getValue(), 
                                       chkCleanTexi2DviOutput_.getValue(),
                                       chkEnableShellEscape_.getValue());
      rPrefs.setCompilePdfPrefs(prefs);
      
      return requiresRestart;
   }

   private final UIPrefs prefs_;
   @SuppressWarnings("unused")
   private final PreferencesDialogResources res_;
   
   private RnwWeaveSelectWidget defaultSweaveEngine_;
   private LatexProgramSelectWidget defaultLatexProgram_;
   private CheckBox chkUseTexi2Dvi_;
   private CheckBox chkCleanTexi2DviOutput_;
   private CheckBox chkEnableShellEscape_;
   
}