package org.moflon.gt.mosl.pattern.language.ui.highlighting.rules;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.gt.mosl.ide.ui.highlighting.AbstractHighlightProviderController;
import org.moflon.gt.mosl.ide.ui.highlighting.RegisterRule;
import org.moflon.gt.mosl.ide.ui.highlighting.rules.AbstractHighlightingRule;
import org.moflon.gt.mosl.pattern.language.moslPattern.Constraint;

@RegisterRule
public class ConstraintHighlightingRule extends AbstractHighlightingRule {

	public ConstraintHighlightingRule(AbstractHighlightProviderController controller) {
		super(controller);
	}

	@Override
	protected String id() {
		return "ConstraintHighlightingRule";
	}

	@Override
	protected String description() {
		return "the name of a constraint should be italic";
	}

	@Override
	protected TextStyle getTextStyle() {
		TextStyle ts = controller.getConfig().defaultTextStyle();
		ts.setStyle(SWT.ITALIC);
		return ts;
	}

	@Override
	protected boolean getHighlightingConditions(EObject moslObject, INode node) {
		if(moslObject instanceof Constraint) {
			String text = node.getText();
			Constraint constraint = Constraint.class.cast(moslObject);
			return constraint.getName().getName().equals(text);
		}
		return false;
	}

}
