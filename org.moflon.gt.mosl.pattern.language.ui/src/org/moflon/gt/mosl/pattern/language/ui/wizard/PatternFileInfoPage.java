package org.moflon.gt.mosl.pattern.language.ui.wizard;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.moflon.core.utilities.LogUtils;
import org.moflon.gt.mosl.pattern.language.ui.internal.LanguageActivator;
import org.moflon.ide.ui.UIActivator;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;

public class PatternFileInfoPage extends WizardNewFileCreationPage{
	public PatternFileInfoPage(IStructuredSelection selection, String fileExtension) {
		this("New Pattern file wizard", selection, fileExtension);
	}

	private static final Logger logger = Logger.getLogger(PatternFileInfoPage.class);

	   private String fileName;

	   private Text fileNameTextfield;

	   private Optional<IResource> fileLocation = Optional.empty();

	   private Optional<IProject> project = Optional.empty();

	   public PatternFileInfoPage(String pageName, IStructuredSelection selection, String fileExtension)
	   {
	      super(pageName, selection);
	      fileName = "";
	     // setProjectAndRuleLocation();
	      setFileExtension(fileExtension);
	      // Set information on the page
	     // setTitle("New Pattern file wizard");
	      setDescription("Create a new Pattern file");
	      setImageDescriptor(UIActivator.getImage("resources/icon/patternicon.png"));
	   }




//	   @Override
//	   public void createControl(final Composite parent)
//	   {
//		   super.createControl(parent);
////	      // Create root container
////	      Composite container = new Composite(parent, SWT.NULL);
////	      GridLayout layout = new GridLayout();
////	      container.setLayout(layout);
////	      layout.numColumns = 2;
////
////	      createControlsForRuleName(container);
////
////	      // Place cursor in textfield
////	      fileNameTextfield.setFocus();
////
////	      // Set controls and update
////	      setControl(container);
////	      dialogChanged();
//	   }

	  

//	   public void createControlsForRuleName(final Composite container)
//	   {
//	      // Create control for entering project name
//	      Label label = createLabel(container);
//	      label.setText("&File name:");
//
//	      fileNameTextfield = new Text(container, SWT.BORDER | SWT.SINGLE);
//	      GridData gd = new GridData(GridData.FILL_HORIZONTAL);
//	      gd.horizontalSpan = 2;
//	      fileNameTextfield.setLayoutData(gd);
//	      fileNameTextfield.addModifyListener(new ModifyListener() {
//	         @Override
//	         public void modifyText(final ModifyEvent e)
//	         {
//	            fileName = fileNameTextfield.getText();
//
//	            if (fileName.isEmpty())
//	            {
//	               setErrorMessage("File name must not be empty.");
//	            }
//
//	            dialogChanged();
//	         }
//	      });
//	   }
//
//	   public Label createLabel(final Composite container)
//	   {
//	      return new Label(container, SWT.NULL);
//	   }
//
//	   @Override
//	   public boolean canFlipToNextPage()
//	   {
//	      return super.canFlipToNextPage() && getErrorMessage() == null;
//	   }
//
//	   public String getFileName()
//	   {
//	      return fileName;
//	   }
//
//	   public IResource getFileLocation()
//	   {
//	      return fileLocation.get();
//	   }

//	   private Optional<IResource> determineLocationForNewRuleInProject(Object selectedElement)
//	   {
//	      if (selectedElement instanceof IJavaElement)
//	      {
//	         try
//	         {
//	            return Optional.of(((IJavaElement) selectedElement).getCorrespondingResource());
//	         } catch (JavaModelException e)
//	         {
//	            LogUtils.error(logger, e);
//	         }
//	      }
//
//	      if (selectedElement instanceof IFile)
//	         return Optional.of(((IFile) selectedElement).getParent());
//
//	      if (selectedElement instanceof IFolder)
//	         return Optional.of((IFolder) selectedElement);
//
//	      return Optional.empty();
//	   }
//
//
//	   private final void updateStatus(final String message)
//	   {
//	      setErrorMessage(message);
//	      setPageComplete(message == null);
//	   }
//
//	   private void dialogChanged()
//	   {
//	      IStatus validity = validate();
//
//	      if (validity.isOK())
//	         updateStatus(null);
//	      else
//	         updateStatus(validity.getMessage());
//	   }
//
//	   private IStatus validate()
//	   {
//	      if (fileName.isEmpty())
//	         return new Status(IStatus.ERROR, LanguageActivator.ORG_MOFLON_GT_MOSL_PATTERN_LANGUAGE_MOSLPATTERN, "File name must not be empty!");
//
//		return new Status(IStatus.OK, LanguageActivator.ORG_MOFLON_GT_MOSL_PATTERN_LANGUAGE_MOSLPATTERN, "File is valid");
//	}
}
