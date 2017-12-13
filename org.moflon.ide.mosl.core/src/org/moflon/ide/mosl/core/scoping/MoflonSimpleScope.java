package org.moflon.ide.mosl.core.scoping;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.SimpleScope;
import org.eclipse.xtext.util.SimpleAttributeResolver;

import com.google.common.base.Function;

public class MoflonSimpleScope extends SimpleScope
{

   public MoflonSimpleScope(Collection<? extends EObject> candidates)
   {
      super(IScope.NULLSCOPE, createDescriptions(candidates));
   }

   private static Iterable<IEObjectDescription> createDescriptions(Collection<? extends EObject> candidates){
      Set<IEObjectDescription> descriptions = candidates.parallelStream().map(eObject -> new MoflonEObjectDescription(eObject)).collect(Collectors.toSet());      
      return descriptions;
   }
   
   public Iterable<IEObjectDescription> getElements(QualifiedName qn){
      Iterable<IEObjectDescription> descriptions = super.getElements(qn);
      return descriptions;
   }
   
   public IEObjectDescription getSigleElement(QualifiedName qn){
      return super.getSingleElement(qn);
   }

}
