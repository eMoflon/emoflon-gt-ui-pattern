package org.moflon.gt.mosl.pattern.language.ui.wizard

import org.moflon.gt.ide.ui.wizards.WizardFileTemplate
import org.eclipse.core.resources.IFile

class PatternFileTemplate extends WizardFileTemplate {
	
	override setContent(IFile file) {
		val content = createTemplate()
		save(file, content)		
	}
	
	def String createTemplate(){
		'''
		/*
		* import section
		*
		* for imports from plugins use:
		* import "platform:/plugin/something/ecoreFile.ecore"
		*
		*
		* for imports in your current eclipse runtime use:
		* import "platform:/resource/something/ecoreFile.ecore"
		*/
		
		/*
		* module definition
		*/
		'''
	}
	
}