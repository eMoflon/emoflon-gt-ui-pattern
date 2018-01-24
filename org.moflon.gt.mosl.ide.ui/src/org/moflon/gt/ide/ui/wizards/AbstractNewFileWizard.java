package org.moflon.gt.ide.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.wizards.newresource.BasicNewFileResourceWizard;
import org.moflon.ide.ui.WorkspaceHelperUI;
public abstract class AbstractNewFileWizard  extends BasicNewFileResourceWizard{

	private AbstractNewFileInfoPage mainPage;
	
	protected IStructuredSelection selection;
	
	@Override
	public void addPages() {
		mainPage = createMainPage();
		addPage(mainPage);
	}
	
	abstract protected AbstractNewFileInfoPage createMainPage();

	@Override
	public boolean performFinish(){
		IFile file = mainPage.createNewFile();

		WorkspaceHelperUI.openDefaultEditorForFile(file);
		return true;
	}
	
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
		this.selection = selection;
	}

}