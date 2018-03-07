package org.moflon.gt.mosl.pattern.language.ui.highlighting.rules;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.gt.mosl.ide.ui.highlighting.AbstractHighlightProviderController;
import org.moflon.gt.mosl.ide.ui.highlighting.RegisterRule;
import org.moflon.gt.mosl.ide.ui.highlighting.rules.AbstractHighlightingRule;
import org.moflon.gt.mosl.ide.ui.highlighting.utils.XtextColor;
import org.moflon.gt.mosl.pattern.language.moslPattern.ObjectVariablePattern;
import org.moflon.gt.mosl.pattern.language.moslPattern.TemporaryVariable;
import org.moflon.gt.mosl.pattern.language.moslPattern.TemporaryVariableExpression;

@RegisterRule
public class MarkLocalObjectsHighlightingRule extends AbstractHighlightingRule {
	
	
	public MarkLocalObjectsHighlightingRule(AbstractHighlightProviderController controller) {
		super(controller);
	}

	@Override
	protected String id() {
		return "MarkLocalObject";
	}

	@Override
	protected String description() {
		return "if an Object is not created or destroyed it gets the color brown for better recognizing";
	}

	@Override
	protected TextStyle getTextStyle() {
		TextStyle ts = new TextStyle();
		ts.setColor(controller.getColorManager().getColor(XtextColor.BROWN));
		return ts;
	}

	@Override
	protected boolean getHighlightingConditions(EObject moslObject, INode node) {
		if (moslObject instanceof ObjectVariablePattern) {
			String text = node.getText();
			ObjectVariablePattern objectVariablePattern = ObjectVariablePattern.class.cast(moslObject);
			return objectVariablePattern.getOp() == null && objectVariablePattern.getName().equals(text);
		}else if(moslObject instanceof TemporaryVariable) {
			String text = node.getText();
			TemporaryVariable temporaryVariable = TemporaryVariable.class.cast(moslObject);
			return temporaryVariable.getName().equals(text);
		}else if(moslObject instanceof TemporaryVariableExpression) {
			String text = node.getText();
			TemporaryVariableExpression temporaryVariableExpression = TemporaryVariableExpression.class.cast(moslObject);
			return temporaryVariableExpression.getValue().getName().equals(text);
		}
		return false;
	}

}
