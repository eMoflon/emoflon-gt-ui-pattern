package org.moflon.gt.mosl.pattern.language.ui.highlighting;

import org.moflon.gt.mosl.ide.ui.highlighting.AbstractHighlightProviderController;
import org.moflon.gt.mosl.ide.ui.highlighting.AbstractHighlightingConfiguration;
import org.moflon.gt.mosl.ide.ui.highlighting.AbstractSemanticHighlightingCalculator;

public class MOSLPatternHighlightProviderController extends AbstractHighlightProviderController {

	public MOSLPatternHighlightProviderController() {
		super(MOSLPatternTokenMapper.class);
	}

	@Override
	protected Class<? extends AbstractSemanticHighlightingCalculator> getCalculatorClass() {
		return MOSLPatternSemanticHighlightingCalculator.class;
	}

	@Override
	protected Class<? extends AbstractHighlightingConfiguration> getConfigClass() {
		return MOSLPatternHighlightingConfigurator.class;
	}

}
