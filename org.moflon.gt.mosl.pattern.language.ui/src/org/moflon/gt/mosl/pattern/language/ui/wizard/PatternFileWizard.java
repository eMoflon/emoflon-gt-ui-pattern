package org.moflon.gt.mosl.pattern.language.ui.wizard;

import org.moflon.gt.ide.ui.wizards.AbstractNewFileInfoPage;
import org.moflon.gt.ide.ui.wizards.AbstractNewFileWizard;

public class PatternFileWizard  extends AbstractNewFileWizard{

	@Override
	protected AbstractNewFileInfoPage createMainPage() {
		return new PatternFileInfoPage(selection);
	}

}
