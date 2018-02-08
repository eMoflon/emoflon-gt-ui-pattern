package org.moflon.gt.mosl.pattern.language.ui.highlighting;

import org.moflon.gt.mosl.ide.ui.highlighting.AbstractHighlightProviderController;
import org.moflon.gt.mosl.ide.ui.highlighting.AbstractHighlightingConfiguration;
import org.moflon.gt.mosl.ide.ui.highlighting.AbstractSemanticHighlightingCalculator;

public class MOSLPatternHighlightProviderController extends AbstractHighlightProviderController {

	public MOSLPatternHighlightProviderController() {
		super(MOSLPatternTokenMapper.class);
	}

	@Override
	protected AbstractHighlightingConfiguration createConfig() {
		return new MOSLPatternHighlightingConfigurator();
	}

	@Override
	protected AbstractSemanticHighlightingCalculator createtSemanticHighlightingCalculator() {
		return new MOSLPatternSemanticHighlightingCalculator();
	}

}
