package org.moflon.gt.mosl.pattern.language.ui.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.moflon.ide.ui.WorkspaceHelperUI;
import org.moflon.ide.ui.admin.wizards.metamodel.AbstractMoflonWizard;


import static org.moflon.core.utilities.WorkspaceHelper.addAllFoldersAndFile;

public class PatternFileWizard  extends AbstractMoflonWizard implements INewWizard{

	protected PatternFileInfoPage projectInfo;
	protected IStructuredSelection selection;
	
	@Override
	public void addPages() {
		projectInfo = new PatternFileInfoPage("page2",selection, "mpt");
		addPage(projectInfo);//new WizardNewFileCreationPage("page1", selection));		
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
		this.selection = selection;
	}
	
	@Override
	protected void doFinish(IProgressMonitor monitor) throws CoreException {
		
		//String ruleContent = DefaultFilesHelper.generateDefaultRule(projectInfo.getSchema(), projectInfo.getFileName());
		IFile file = projectInfo.createNewFile();
//		addAllFoldersAndFile(project, pathToFile, "", SubMonitor.convert(monitor).split(1));
//
//		IFile file = project.getFile(pathToFile);

		WorkspaceHelperUI.openDefaultEditorForFile(file);
		
	}



}
