package org.moflon.gt.ide.ui.wizards;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

public abstract class AbstractNewFileInfoPage extends WizardNewFileCreationPage{
	public AbstractNewFileInfoPage(IStructuredSelection selection, String fileExtension) {
		this("New Pattern file wizard", selection, fileExtension);
	}

	protected static Logger logger;
	
	protected WizardFileTemplate wizardFileTemplate;

	public AbstractNewFileInfoPage(String pageName, IStructuredSelection selection, String fileExtension)
	{
	    super(pageName, selection);
	
	    logger = Logger.getLogger(this.getClass());
	    setFileExtension(fileExtension);
	    wizardFileTemplate = createWizardFileTemplate();
	}

	  
	@Override
	public IFile createNewFile() {
		IFile file = super.createNewFile();
		wizardFileTemplate.setContent(file);		
		return file;
	}

	protected abstract WizardFileTemplate createWizardFileTemplate();
	
	public WizardFileTemplate getWizardFileTemplate() {
		return wizardFileTemplate;
	}
	
	
}
