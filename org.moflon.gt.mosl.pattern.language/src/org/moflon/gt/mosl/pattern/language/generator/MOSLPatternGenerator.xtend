/*
 * generated by Xtext 2.11.0
 */
package org.moflon.gt.mosl.pattern.language.generator

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext

/**
 * Generates code from your model files on save.
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
class MOSLPatternGenerator extends AbstractGenerator {

	override void doGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
//				val constDefs = MOSLScopeUtil.instance.getObjectsFromResource(resource, ConstraintDef)
				//constDefs.forEach[constDef | checkConstraintDefIsConnected(constDef)]

//fsa.generateFile('greetings.txt', 'People to greet: ' +
//			resource.allContents
//				.filter(Greeting)
//				.map[name]
//				.join(', '))
	}


}
