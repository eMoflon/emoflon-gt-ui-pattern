package org.moflon.gt.mosl.pattern.language.ui.highlighting.rules;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;
import org.moflon.gt.mosl.ide.ui.highlighting.AbstractHighlightProviderController;
import org.moflon.gt.mosl.ide.ui.highlighting.rules.AbstractHighlightingRule;
import org.moflon.gt.mosl.pattern.language.moslPattern.LinkVariablePattern;
import org.moflon.gt.mosl.pattern.language.moslPattern.ObjectVariablePattern;
import org.moflon.gt.mosl.pattern.language.moslPattern.Operator;
import org.moflon.gt.mosl.pattern.language.moslPattern.PatternVariable;

public abstract class AbstractOperatorHighlightingRule extends AbstractHighlightingRule {



	public AbstractOperatorHighlightingRule(AbstractHighlightProviderController controller) {
		super(controller);
	}

	@Override
	protected boolean getHighlightingConditions(EObject moslObject, INode node) {
		if(moslObject instanceof PatternVariable) {
			String text = node.getText();
			PatternVariable patternVariable = PatternVariable.class.cast(moslObject);
			Operator op = patternVariable.getOp();
			if(op!=null && op.getValue().contentEquals(getOperatorValue())) {
				if(moslObject instanceof LinkVariablePattern) {
					LinkVariablePattern linkVariablePattern = LinkVariablePattern.class.cast(moslObject);
					if(linkVariablePattern.getReference().getName().equals(linkVariablePattern.getTarget().getName()) && text.equals(linkVariablePattern.getTarget().getName())) {
						INode previos = getPreviosNonWhiteSpaceNode(node.getPreviousSibling());
						if(previos.getText().equals("->")) {
							return false;
						}
					}
					return text.equals("-") || text.equals("->") || text.equals(linkVariablePattern.getReference().getName());
				}else {
					ObjectVariablePattern objectVariablePattern = ObjectVariablePattern.class.cast(moslObject);
					return text.equals("{") || text.equals("}") || text.equals(":") || (!objectVariablePattern.getName().equals("this") && objectVariablePattern.getName().equals(text)) || objectVariablePattern.getEType().getName().equals(text);
				}
			}
		}
		else if (moslObject instanceof Operator) {
			return Operator.class.cast(moslObject).getValue() != null && Operator.class.cast(moslObject).getValue().equals(getOperatorValue());
		}
		return false;
	}
	
	private INode getPreviosNonWhiteSpaceNode(INode node) {
		if(node == null)
			return null;
		String text = node.getText();
		if(text.equals(" "))
			return getPreviosNonWhiteSpaceNode(node.getPreviousSibling());
		else
			return node;
	}

	protected abstract String getOperatorValue();
}
