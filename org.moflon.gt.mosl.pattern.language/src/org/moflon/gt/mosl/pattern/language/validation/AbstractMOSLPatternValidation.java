package org.moflon.gt.mosl.pattern.language.validation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;

public abstract class AbstractMOSLPatternValidation extends AbstractMOSLPatternValidator
{
   public AbstractMOSLPatternValidation(){
      super();
      instance = this;
   }
   
   public static AbstractMOSLPatternValidation instance;
   
   @Override
   protected List<EPackage> getEPackages() {
      List<EPackage> result = new ArrayList<EPackage>();
      result.add(org.moflon.gt.mosl.pattern.language.moslPattern.MoslPatternPackage.eINSTANCE);
      return result;
   }
   
}
