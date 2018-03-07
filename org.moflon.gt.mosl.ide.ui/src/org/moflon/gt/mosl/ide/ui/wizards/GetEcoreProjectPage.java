package org.moflon.gt.mosl.ide.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.xtext.ui.shared.JdtHelper;
import org.moflon.gt.mosl.ide.ui.internal.wizards.WizardSelectImportedEPackagePage;


public class GetEcoreProjectPage extends WizardSelectImportedEPackagePage{

	public GetEcoreProjectPage(IStructuredSelection selection) {
		super("GetEcoreProject", selection, new JdtHelper());
	}

}
