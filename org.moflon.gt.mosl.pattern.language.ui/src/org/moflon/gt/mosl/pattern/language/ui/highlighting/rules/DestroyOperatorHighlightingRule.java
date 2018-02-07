package org.moflon.gt.mosl.pattern.language.ui.highlighting.rules;

import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.gt.mosl.ide.ui.highlighting.AbstractHighlightProviderController;
import org.moflon.gt.mosl.ide.ui.highlighting.RegisterRule;
import org.moflon.gt.mosl.ide.ui.highlighting.utils.XtextColor;

@RegisterRule
public class DestroyOperatorHighlightingRule extends AbstractOperatorHighlightingRule {

	
	public DestroyOperatorHighlightingRule(AbstractHighlightProviderController controller) {
		super(controller);
	}

	@Override
	protected String getOperatorValue() {
		return "--";
	}

	@Override
	protected TextStyle getTextStyle() {
		TextStyle ts = new TextStyle();
		ts.setColor(controller.getColorManager().getColor(XtextColor.RED));
		return ts;
	}

	@Override
	protected String id() {
		return "DestroyRule";
	}

	@Override
	protected String description() {
		return "If an object starts with operator '--' it gets red";
	}

}
