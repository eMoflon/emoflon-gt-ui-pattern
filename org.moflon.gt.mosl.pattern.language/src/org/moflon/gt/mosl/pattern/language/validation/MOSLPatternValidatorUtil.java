package org.moflon.gt.mosl.pattern.language.validation;

import org.eclipse.emf.ecore.EDataType;
import org.moflon.gt.mosl.pattern.language.moslPattern.BooleanLiteralExpression;
import org.moflon.gt.mosl.pattern.language.moslPattern.DecimalLiteralExpression;
import org.moflon.gt.mosl.pattern.language.moslPattern.IntegerLiteralExpression;
import org.moflon.gt.mosl.pattern.language.moslPattern.LiteralExpression;
import org.moflon.gt.mosl.pattern.language.moslPattern.StringLiteralExpression;
import org.moflon.ide.mosl.core.utils.MOSLUtil;

public class MOSLPatternValidatorUtil
{
   
   
   public static MOSLPatternValidatorUtil instance = new MOSLPatternValidatorUtil();

   private MOSLPatternValidatorUtil()
   {
      
   }

   public EDataType getLiteralExpressionType(LiteralExpression literal){
      if(literal instanceof BooleanLiteralExpression){
        return getEDataTypeByName("EBoolean");
      }else if(literal instanceof DecimalLiteralExpression)
        return getEDataTypeByName("EDouble");
      else if (literal instanceof StringLiteralExpression)
        return getEDataTypeByName("EString");
      else if (literal instanceof IntegerLiteralExpression)
         return getEDataTypeByName("EInt");
      throw new RuntimeException("Wrong Literal Type");
   }

   private EDataType getEDataTypeByName(String typeName){
      return MOSLUtil.getInstance().getEcoreEDataTypes().parallelStream().filter(type -> type.getName().equals(typeName)).findFirst().get();
   }
}
