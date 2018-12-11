package org.moflon.gt.mosl.pattern.language.ui.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.moflon.gt.mosl.ide.ui.wizards.AbstractNewFileInfoPage;
import org.moflon.gt.mosl.ide.ui.wizards.WizardFileTemplate;


public class PatternFileInfoPage extends AbstractNewFileInfoPage{

	public PatternFileInfoPage(IStructuredSelection selection) {
		super("New Pattern File", selection, "mpt");
	}

	@Override
	protected WizardFileTemplate createWizardFileTemplate() {
		return new PatternFileTemplate();
	}

}
