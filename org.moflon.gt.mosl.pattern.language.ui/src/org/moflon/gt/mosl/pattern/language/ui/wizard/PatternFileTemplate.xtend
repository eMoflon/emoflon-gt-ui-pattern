package org.moflon.gt.mosl.pattern.language.ui.wizard

import org.eclipse.core.resources.IFile
import org.moflon.gt.mosl.ide.ui.wizards.WizardFileTemplate

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
		«createImportText»
		
		/*
		* module definition
		*/
		'''
	}
	
	def String createImportText(){
			
		'''
		«FOR epackage : epackageImports»
			import "«epackage.nsURI»"
		«ENDFOR»
		'''
	}
}