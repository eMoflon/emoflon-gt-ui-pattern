package org.moflon.gt.mosl.pattern.language.ui.highlighting.rules;

import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.gt.mosl.ide.ui.highlighting.AbstractHighlightProviderController;
import org.moflon.gt.mosl.ide.ui.highlighting.RegisterRule;
import org.moflon.gt.mosl.ide.ui.highlighting.utils.XtextColor;

@RegisterRule
public class CreationOperatorHighlightingRule extends AbstractOperatorHighlightingRule {
	
	public CreationOperatorHighlightingRule(AbstractHighlightProviderController controller) {
		super(controller);
	}

	@Override
	protected TextStyle getTextStyle() {
		TextStyle ts = new TextStyle();
		ts.setColor(controller.getColorManager().getColor(XtextColor.DARK_GREEN));
		return ts;
	}

	@Override
	protected String getOperatorValue() {
		return "++";
	}

	@Override
	protected String id() {
		return "CreationRule";
	}

	@Override
	protected String description() {
		return "If an object starts with operator '++' it gets green";
	}


}
