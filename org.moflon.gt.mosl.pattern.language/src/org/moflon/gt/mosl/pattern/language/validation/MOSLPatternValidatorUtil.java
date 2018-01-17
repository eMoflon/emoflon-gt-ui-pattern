package org.moflon.gt.mosl.pattern.language.validation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
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
import org.moflon.gt.mosl.pattern.language.moslPattern.ConstraintDefParameter;
import org.moflon.gt.mosl.pattern.language.moslPattern.DecimalLiteralExpression;
import org.moflon.gt.mosl.pattern.language.moslPattern.IntegerLiteralExpression;
import org.moflon.gt.mosl.pattern.language.moslPattern.LiteralExpression;
import org.moflon.gt.mosl.pattern.language.moslPattern.StringLiteralExpression;
import org.moflon.gt.mosl.pattern.language.utils.MOSLPatternHelper;
import org.moflon.ide.mosl.core.scoping.utils.MOSLScopeUtil;
import org.moflon.ide.mosl.core.utils.MOSLUtil;
import org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary;
import org.moflon.sdm.constraints.operationspecification.ConstraintSpecification;
import org.moflon.sdm.constraints.operationspecification.OperationspecificationFactory;
import org.moflon.sdm.constraints.operationspecification.ParameterType;

public class MOSLPatternValidatorUtil
{

   public static MOSLPatternValidatorUtil instance = new MOSLPatternValidatorUtil();

   private MOSLPatternHelper patternHelper;

   private MOSLPatternValidatorUtil()
   {
      patternHelper = new MOSLPatternHelper();

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

   private String getResourceURIPrefix(URI uri)
   {
      return Arrays.asList(uri.toString().split("/")).subList(0, 2).stream().reduce("", (a,b) -> a + b +"/");
   }

   public String getProjectNameFromResource(Resource resource)
   {
      return getProjectNameFromURI(resource.getURI());
   }

   public IProject getProject(EObject eObject)
   {
      Resource resource = eObject.eResource();
      return resource == null ? null : getProject(resource);
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

   public IFolder getLibFolder(EObject eObject)
   {
      Resource resource = eObject.eResource();
      return resource == null ? null : getLibFolder(resource);
   }

   public IFolder getLibFolder(Resource resource)
   {
      IProject project = getProject(resource);
      IFolder libFolder = WorkspaceHelper.getLibFolder(project);
      return libFolder;
   }

   public IFile getLibFile(EObject eObject)
   {
      Resource resource = eObject.eResource();
      return resource == null ? null : getLibFile(resource);
   }

   public IFile getLibFile(Resource resource)
   {
      IFolder libFolder = getLibFolder(resource);
      IFile libraryFile = libFolder.getFile(libFolder.getProject().getName() + "AttributeConstraintsLib.xmi");
      return libraryFile;
   }

   public void addConstraintSpecificationToLib(ConstraintDef constraintDef){
      URI uri = getLibURI(constraintDef);
      AttributeConstraintLibrary library = MOSLScopeUtil.getInstance().getObjectFromResourceSet(uri, constraintDef.eResource().getResourceSet(), AttributeConstraintLibrary.class);
      library.getConstraintSpecifications().add(this.convertFromConstraintDefToConstraintSpecification(constraintDef));
      MOSLScopeUtil.getInstance().saveToResource(uri, constraintDef.eResource().getResourceSet(), library);
   }
   
   private URI getLibURI(EObject eObject){
      Resource resource = eObject.eResource();
      IFile libFile = getLibFile(resource);
      String relativePath = libFile.getProjectRelativePath().toString();
      String uriPrefix = getResourceURIPrefix(resource.getURI());      
      URI uri = URI.createURI(uriPrefix + libFile.getProject().getName() +"/"+ relativePath);
      return uri;
   }
   
   public void createLibFile(ConstraintDef constraintDef)
   {
      URI uri = getLibURI(constraintDef);      
      AttributeConstraintLibrary library = getLibrary(constraintDef); 
      MOSLScopeUtil.getInstance().saveToResource(uri, constraintDef.eResource().getResourceSet(), library);
   }
   
   private AttributeConstraintLibrary getLibrary(ConstraintDef constraintDef){
      AttributeConstraintLibrary library = OperationspecificationFactory.eINSTANCE.createAttributeConstraintLibrary();
      library.getConstraintSpecifications().add(convertFromConstraintDefToConstraintSpecification(constraintDef));
      return library;
   }
   
   private ConstraintSpecification convertFromConstraintDefToConstraintSpecification(ConstraintDef constraintDef){
      ConstraintSpecification constraintSpecification = OperationspecificationFactory.eINSTANCE.createConstraintSpecification();
      constraintSpecification.setSymbol(constraintDef.getName());
      constraintSpecification.getParameterTypes().addAll(constraintDef.getParameters().stream().map(this::convertFromConstraintDefPararmeterToParameterType).collect(Collectors.toList()));
      patternHelper.setConnection(constraintDef, constraintSpecification);
      return constraintSpecification;
   }
   
   private ParameterType convertFromConstraintDefPararmeterToParameterType(ConstraintDefParameter constraintDefParameter)
   {
      ParameterType parameterType = OperationspecificationFactory.eINSTANCE.createParameterType();
      parameterType.setType(constraintDefParameter.getEType());
      return parameterType;
   }
   
   
   public boolean isConnected(ConstraintDef constraintDef) throws PatternLanguageException
   {
      Resource resource = constraintDef.eResource();
      
      boolean isAlreadyConnected = patternHelper.isConnected(constraintDef);
      if (isAlreadyConnected)
         return true;

      
      IProject project = getProject(resource);
      IFolder libFolder = WorkspaceHelper.getLibFolder(project);
      if (!libFolder.exists())
      {
         throw new LibFolderDoesnNotExistException();
      }

      IFile libraryFile = libFolder.getFile(project.getName() + "AttributeConstraintsLib.xmi");
      if (!libraryFile.exists())
         throw new LibFileDoesnotExitException();
      
      AttributeConstraintLibrary library = MOSLScopeUtil.getInstance().getObjectFromResourceSet(URI.createFileURI(libraryFile.getLocation().toString()),
            resource.getResourceSet(), AttributeConstraintLibrary.class);
      ConstraintSpecification spec = patternHelper.findConstraintSpecification(constraintDef, library);
      if (spec == null)
         return false;

      patternHelper.setConnection(constraintDef, spec);
     
      return true;
   }
}
