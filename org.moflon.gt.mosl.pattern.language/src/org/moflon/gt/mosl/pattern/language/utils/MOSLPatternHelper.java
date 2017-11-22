package org.moflon.gt.mosl.pattern.language.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.gt.mosl.pattern.language.moslPattern.ConstraintDef;
import org.moflon.gt.mosl.pattern.language.moslPattern.ConstraintDefParameter;
import org.moflon.gt.mosl.pattern.language.moslPattern.GraphTransformationPatternFile;
import org.moflon.gt.mosl.pattern.language.moslPattern.MoslPatternFactory;
import org.moflon.gt.mosl.pattern.language.moslPattern.PatternModule;
import org.moflon.ide.mosl.core.scoping.utils.MOSLScopeUtil;
import org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary;
import org.moflon.sdm.constraints.operationspecification.ConstraintSpecification;
import org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage;
import org.moflon.sdm.constraints.operationspecification.ParameterType;

public class MOSLPatternHelper
{
   private final static String BUILD_IN_PATH ="platform:/plugin/org.moflon.sdm.constraints.operationspecification/lib/buildInConstraintsLibrary/BuildInAttributeVariableConstraintLibrary.xmi";
   
   private final AttributeConstraintLibrary buildInLibrary;
   
   private final List<ConstraintDef> convertedBuildIns;
   
   private ResourceSet resSet;
   
   private Map<ConstraintDef, ConstraintSpecification> specificationMap;
   
   public MOSLPatternHelper(){
      specificationMap = new HashMap<>();
      resSet = MOSLScopeUtil.getInstance().getResourceSet();
      OperationspecificationPackage.eINSTANCE.eClass();
      buildInLibrary = MOSLScopeUtil.getInstance().getObjectFromResourceSet(URI.createURI(BUILD_IN_PATH), resSet, AttributeConstraintLibrary.class);
      convertedBuildIns = buildInLibrary.getConstraintSpecifications().parallelStream().filter(this::isEDataType).map(this::convertToPatternConstraints).collect(Collectors.toList());
      PatternModule pm = MoslPatternFactory.eINSTANCE.createPatternModule();
      pm.setName("BuildInLibrary");
      pm.getDefinitions().addAll(convertedBuildIns);
      GraphTransformationPatternFile gtpf = MoslPatternFactory.eINSTANCE.createGraphTransformationPatternFile();
      gtpf.getModules().add(pm);
      MOSLScopeUtil.getInstance().addToResource(URI.createURI("src/org/moflon/gt/buildinlibrary/library.mpt"), resSet, gtpf); 
   }
   
   public void setConnection(ConstraintDef constraintDef, ConstraintSpecification constraintSpecification){
      specificationMap.put(constraintDef, constraintSpecification);
   }
   
   public boolean isConnected(ConstraintDef constraintDef){
      return specificationMap.containsKey(constraintDef);
   }
   
   private boolean isEDataType(ConstraintSpecification conSpec){
      return !conSpec.getParameterTypes().parallelStream().anyMatch(param -> !(param.getType() instanceof EDataType));
   }
   
   private ConstraintDef convertToPatternConstraints(ConstraintSpecification conSpec){
      ConstraintDef constDef = MoslPatternFactory.eINSTANCE.createConstraintDef();
      List<ParameterType> parameters = conSpec.getParameterTypes();
      Set<String> typeNames = parameters.stream().map(param -> param.getType().getName().substring(1)).collect(Collectors.toSet());
      String name = getNameOfConstraintSpecification(conSpec) + "_" + typeNames.stream().reduce("", (a,b) -> a + b);
      constDef.setName(name);
      ins = 0;
      constDef.getParameters().addAll(parameters.stream().map(this::convertParameters).collect(Collectors.toList()));
      constDef.setIsPublic(true);
      setConnection(constDef, conSpec);
      return constDef;
   }
   
   private String getNameOfConstraintSpecification(ConstraintSpecification constraintSpecification)
   {
      String symbol = constraintSpecification.getSymbol();
      switch (symbol)
      {
      case "+":
         if(constraintSpecification.getParameterTypes().get(0).getType().getName().equals("EString"))
            return "concat";
         else
            return "add";
      case "-":
         return "sub";
      case "/":
         return "diff";
      case "*":
         return "mul";
      case "<":
         return "less";
      case ">":
         return "greater";
      case "=":
         return "eq";
      case "<=":
         return "leq";
      case ">=":
         return "geq";
      case "!=":
         return "uneq";
      default:
         return symbol;
      }
   }
   
   private static int ins;
   
   private ConstraintDefParameter convertParameters(ParameterType paramType){
      ConstraintDefParameter constDefParam = MoslPatternFactory.eINSTANCE.createConstraintDefParameter();
      constDefParam.setName("arg"+ins++);
      constDefParam.setType(EDataType.class.cast(paramType.getType()));
      return constDefParam;
   }
   
   public List<ConstraintDef> getBuildInConstraints(){
      return convertedBuildIns;
   }
 
}
