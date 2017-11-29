package org.moflon.gt.mosl.pattern.language.validation;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.gt.mosl.pattern.language.exceptions.LibFileDoesnotExitException;
import org.moflon.gt.mosl.pattern.language.exceptions.LibFolderDoesnNotExistException;
import org.moflon.gt.mosl.pattern.language.exceptions.PatternLanguageException;
import org.moflon.gt.mosl.pattern.language.moslPattern.BooleanLiteralExpression;
import org.moflon.gt.mosl.pattern.language.moslPattern.ConstraintDef;
import org.moflon.gt.mosl.pattern.language.moslPattern.DecimalLiteralExpression;
import org.moflon.gt.mosl.pattern.language.moslPattern.IntegerLiteralExpression;
import org.moflon.gt.mosl.pattern.language.moslPattern.LiteralExpression;
import org.moflon.gt.mosl.pattern.language.moslPattern.StringLiteralExpression;
import org.moflon.gt.mosl.pattern.language.scoping.MOSLPatternScopeProvider;
import org.moflon.gt.mosl.pattern.language.utils.MOSLPatternHelper;
import org.moflon.ide.mosl.core.scoping.utils.MOSLScopeUtil;
import org.moflon.ide.mosl.core.utils.MOSLUtil;
import org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintLibUtil;
import org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary;
import org.moflon.sdm.constraints.operationspecification.ConstraintSpecification;

public class MOSLPatternValidatorUtil
{

   public static MOSLPatternValidatorUtil instance = new MOSLPatternValidatorUtil();

   private MOSLPatternHelper patternHelper;
   
   private MOSLPatternValidatorUtil()
   {
      patternHelper = MOSLPatternScopeProvider.patternHelper;
   }

   public EDataType getLiteralExpressionType(LiteralExpression literal)
   {
      if (literal instanceof BooleanLiteralExpression)
      {
         return getEDataTypeByName("EBoolean");
      } else if (literal instanceof DecimalLiteralExpression)
         return getEDataTypeByName("EDouble");
      else if (literal instanceof StringLiteralExpression)
         return getEDataTypeByName("EString");
      else if (literal instanceof IntegerLiteralExpression)
         return getEDataTypeByName("EInt");
      throw new RuntimeException("Wrong Literal Type");
   }

   private EDataType getEDataTypeByName(String typeName)
   {
      return MOSLUtil.getInstance().getEcoreEDataTypes().parallelStream().filter(type -> type.getName().equals(typeName)).findFirst().get();
   }

   private String getProjectNameFromURI(URI uri)
   {
      return Arrays.asList(uri.toString().split("/")).get(2);
   }

   public String getProjectNameFromResource(Resource resource)
   {
      return getProjectNameFromURI(resource.getURI());
   }

   public IProject getProject(EObject eObject)
   {
      Resource resource = eObject.eResource();
      return resource == null? null : getProject(resource);
   }
   
   public IProject getProject(Resource res)
   {
      List<IProject> projects = WorkspaceHelper.getAllProjectsInWorkspace();
      String projectName = getProjectNameFromResource(res);
      return projects.parallelStream().filter(project -> project.getName().equals(projectName)).findAny().get();
   }
   
   public MOSLPatternHelper getPatternHelper()
   {
      return patternHelper;
   }
   
   public IFolder getLibFolder(EObject eObject){
      Resource resource = eObject.eResource();
      return resource == null? null : getLibFolder(resource);
   }
   
   public IFolder getLibFolder(Resource resource){
      IProject project = getProject(resource);
      IFolder libFolder = WorkspaceHelper.getLibFolder(project);
      return libFolder;
   }
   
   public IFile getLibFile(EObject eObject){
      Resource resource = eObject.eResource();
      return resource == null? null : getLibFile(resource);
   }
   
   public IFile getLibFile(Resource resource){
      IFolder libFolder = getLibFolder(resource);
      IFile libraryFile = libFolder.getFile(libFolder.getProject().getName() + "AttributeConstraintsLib.xmi");
      return libraryFile;
   }
   
   public boolean isConnected(ConstraintDef constraintDef) throws PatternLanguageException{
      boolean isAlreadyConnected=patternHelper.isConnected(constraintDef);
      if(isAlreadyConnected)
         return true;
      
      Resource resource = constraintDef.eResource();
      IProject project = getProject(resource);
      IFolder libFolder = WorkspaceHelper.getLibFolder(project);
      if(!libFolder.exists()){
         throw new LibFolderDoesnNotExistException();
      }

      IFile libraryFile = libFolder.getFile(project.getName() + "AttributeConstraintsLib.xmi");
      if(!libraryFile.exists())
         throw new LibFileDoesnotExitException();
      
      AttributeConstraintLibrary library =MOSLScopeUtil.getInstance().getObjectFromResourceSet(URI.createFileURI(libraryFile.getLocation().toString()), resource.getResourceSet(), AttributeConstraintLibrary.class);
      ConstraintSpecification spec= patternHelper.findConstraintSpecification(constraintDef, library);
      if(spec == null)
         return false;
      
      patternHelper.setConnection(constraintDef, spec);
      
      return true;
   }
}
