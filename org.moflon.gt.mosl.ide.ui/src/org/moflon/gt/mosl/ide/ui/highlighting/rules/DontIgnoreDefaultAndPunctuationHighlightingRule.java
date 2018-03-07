package org.moflon.gt.mosl.ide.ui.highlighting.rules;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.nodemodel.INode;
import org.moflon.gt.mosl.ide.ui.highlighting.AbstractHighlightProviderController;
import org.moflon.gt.mosl.ide.ui.highlighting.AbstractHighlightingConfiguration;
import org.moflon.gt.mosl.ide.ui.highlighting.AbstractTokenMapper;



public class DontIgnoreDefaultAndPunctuationHighlightingRule extends AbstractIgnoreHighlightingRule {

public DontIgnoreDefaultAndPunctuationHighlightingRule(AbstractHighlightProviderController controller) {
		super(controller);
	}

	@Override
	protected boolean getIgnoreConditions(EObject moslObject, INode node) {
		if(node != null && node.getGrammarElement() instanceof Keyword){
			return !isDefaultOrPunctuation(Keyword.class.cast(node.getGrammarElement()));
		}
		return false;
	}

	private boolean isDefaultOrPunctuation(Keyword keyword){
		String id = AbstractTokenMapper.getIDFromToken(keyword.getValue());
		return id !=null && (id.equals(AbstractHighlightingConfiguration.DEFAULT_ID) || id.equals(AbstractHighlightingConfiguration.PUNCTUATION_ID));
	}

	@Override
	protected String id() {
		return "DontIgnoreDefault";
	}

	@Override
	protected String description() {
		return "An Ignore Highlighting Rule";
	}


}
