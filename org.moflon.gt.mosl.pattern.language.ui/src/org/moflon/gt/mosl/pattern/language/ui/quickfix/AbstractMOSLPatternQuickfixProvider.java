package org.moflon.gt.mosl.pattern.language.ui.quickfix;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;

public abstract class AbstractMOSLPatternQuickfixProvider extends DefaultQuickfixProvider
{
   protected void update (Resource resource){
      resource.setModified(true);
      resource.setModified(false);
   }
   
}
