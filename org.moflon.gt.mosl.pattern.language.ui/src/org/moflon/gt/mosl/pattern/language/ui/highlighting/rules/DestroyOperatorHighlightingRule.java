package org.moflon.gt.mosl.pattern.language.ui.highlighting.rules;

import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.gt.mosl.ide.ui.highlighting.AbstractHighlightProviderController;
import org.moflon.gt.mosl.ide.ui.highlighting.utils.XtextColor;

public class DestroyOperatorHighlightingRule extends AbstractOperatorHighlightingRule {

	public DestroyOperatorHighlightingRule(AbstractHighlightProviderController controller) {
		super("DestroyRule", "If an object starts with operator '--' it gets red", controller);
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

}
