package org.moflon.gt.mosl.ide.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.wizards.newresource.BasicNewFileResourceWizard;
import org.moflon.core.ui.UiUtilities;
public abstract class AbstractNewFileWizard  extends BasicNewFileResourceWizard{

	private AbstractNewFileInfoPage mainPage;
	private GetEcoreProjectPage ecoreProjectPage;
	protected IStructuredSelection selection;

	@Override
	public void addPages() {
			mainPage = createMainPage();
			addPage(mainPage);

			ecoreProjectPage = new GetEcoreProjectPage(selection);
			addPage(ecoreProjectPage);

			ecoreProjectPage.setPreviousPage(mainPage);
	}

	abstract protected AbstractNewFileInfoPage createMainPage();

	@Override
	public boolean performFinish(){
		mainPage.getWizardFileTemplate().setEPackageImports(ecoreProjectPage.getSelectedPackages());
		IFile file = mainPage.createNewFile();
		UiUtilities.openDefaultEditorForFile(file);
		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
		this.selection = selection;
	}

}
