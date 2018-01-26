package org.moflon.gt.ide.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.xtext.ui.shared.JdtHelper;
import org.eclipse.xtext.xtext.ui.wizard.ecore2xtext.WizardSelectImportedEPackagePage;

@SuppressWarnings("restriction")
public class GetEcoreProjectPage extends WizardSelectImportedEPackagePage{

	public GetEcoreProjectPage(IStructuredSelection selection) {
		super("GetEcoreProject", selection, new JdtHelper());
		// TODO Auto-generated constructor stub
	}

}
