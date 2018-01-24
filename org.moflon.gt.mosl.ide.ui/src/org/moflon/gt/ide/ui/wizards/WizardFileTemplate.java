package org.moflon.gt.ide.ui.wizards;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public abstract class WizardFileTemplate {
		
	public abstract void setContent(IFile file);
	
	protected void save(IFile file, String content) throws CoreException {
		ByteArrayInputStream source = new ByteArrayInputStream(content.getBytes());
		
		if(file.exists()){
			file.setContents(source, IFile.FORCE | IFile.KEEP_HISTORY, null);
		}
		else {
			file.create(source, true, null);
		}
	}
}
