package org.moflon.ide.mosl.core.ui.highlighting.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import org.moflon.ide.mosl.core.ui.highlighting.rules.AbstractHighlightingRule;

public class HighlightCreationAnnotationProcessor extends AbstractProcessor
{

   @Override
   public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
          for( Element elem : roundEnv.getElementsAnnotatedWith( HighlightCreation.class ) ) {
              System.out.println(elem);
              HighlightCreation hiCre= elem.getAnnotation(HighlightCreation.class);
              Class<? extends AbstractHighlightingRule> clazz = hiCre.clazz();
              try
            {
               Constructor<? extends AbstractHighlightingRule> constructor = clazz.getConstructor(new Class<?>[0]);
               constructor.newInstance(new Object[0]);
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
            {
               e.printStackTrace();
            }
          }
         
      return true;
  }
}
