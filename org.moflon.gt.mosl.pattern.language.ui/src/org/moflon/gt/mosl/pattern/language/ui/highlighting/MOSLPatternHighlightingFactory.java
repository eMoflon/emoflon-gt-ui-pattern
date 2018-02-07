package org.moflon.gt.mosl.pattern.language.ui.highlighting;

import org.moflon.gt.mosl.ide.ui.highlighting.AbstractHighlightFactory;
import org.moflon.gt.mosl.pattern.language.ui.highlighting.rules.CreationOperatorHighlightingRule;
import org.moflon.gt.mosl.pattern.language.ui.highlighting.rules.DestroyOperatorHighlightingRule;

public class MOSLPatternHighlightingFactory extends AbstractHighlightFactory {

	@Override
	public void createAllInstances() {
		new CreationOperatorHighlightingRule(controller);
		new DestroyOperatorHighlightingRule(controller);
	}

}
