package org.moflon.gt.mosl.pattern.language.ui.highlighting.rules;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.gt.mosl.ide.ui.highlighting.AbstractHighlightProviderController;
import org.moflon.gt.mosl.ide.ui.highlighting.RegisterRule;
import org.moflon.gt.mosl.ide.ui.highlighting.rules.AbstractHighlightingRule;

@RegisterRule
public class HandleThisHighlightingRule extends AbstractHighlightingRule {

	public HandleThisHighlightingRule(AbstractHighlightProviderController controller) {
		super(controller);
		setPrio(500);
	}

	@Override
	protected String id() {
		return "handleThis";
	}

	@Override
	protected String description() {
		return "If the user use the word this it should be handle like a keyword";
	}

	@Override
	protected TextStyle getTextStyle() {
		return controller.getConfig().keywordTextStyle();
	}

	@Override
	protected boolean getHighlightingConditions(EObject moslObject, INode node) {
		return "this".equals(node.getText());
	}

}
