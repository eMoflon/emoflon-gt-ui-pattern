package org.moflon.gt.mosl.pattern.language.ui.highlighting;

import org.moflon.gt.mosl.ide.ui.highlighting.AbstractSemanticHighlightingCalculator;
import org.moflon.gt.mosl.pattern.language.services.MOSLPatternGrammarAccess;

import com.google.inject.Inject;

public class MOSLPatternSemanticHighlightingCalculator extends AbstractSemanticHighlightingCalculator {
	
	@Inject
	MOSLPatternGrammarAccess ga;
}
