/*
 * generated by Xtext 2.11.0
 */
package org.moflon.gt.mosl.pattern.language.ui.contentassist

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.Assignment
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor
import org.moflon.gt.mosl.pattern.language.validation.MOSLPatternValidatorUtil

/**
 * See https://www.eclipse.org/Xtext/documentation/304_ide_concepts.html#content-assist
 * on how to customize the content assistant.
 */
class MOSLPatternProposalProvider extends AbstractMOSLPatternProposalProvider {
	override completeConstraint_Name(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor){
		super.completeConstraint_Name(model, assignment, context, acceptor)
		val patternHelper = MOSLPatternValidatorUtil.instance.patternHelper
		
		val names = patternHelper.buildInConstraints.map[constrainDef | constrainDef.name]
		
		for(name : names) {
			acceptor.accept(createCompletionProposal(name, context))
		}
	}
}
