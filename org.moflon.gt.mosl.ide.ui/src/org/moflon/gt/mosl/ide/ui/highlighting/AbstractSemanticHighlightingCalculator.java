package org.moflon.gt.mosl.ide.ui.highlighting;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ide.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator;
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.moflon.gt.mosl.ide.ui.highlighting.rules.AbstractHighlightingRule;



public abstract class AbstractSemanticHighlightingCalculator extends DefaultSemanticHighlightingCalculator {
   
	private static AbstractHighlightProviderController controller;
	
	static void setController (AbstractHighlightProviderController _controller) {
		controller = _controller;
	}
   
	@Override
	protected void doProvideHighlightingFor(XtextResource resource, IHighlightedPositionAcceptor acceptor,
			CancelIndicator cancelIndicator) {

		if (resource == null || resource.getParseResult() == null)
			return;
		INode rootNode = resource.getParseResult().getRootNode();
		Collection<AbstractHighlightingRule> rules = controller.getHighlightRules();
		for (INode node : rootNode.getLeafNodes()) {
			findHighlightingRuleForNode(node, rules, acceptor);
		}
		super.doProvideHighlightingFor(resource, acceptor, cancelIndicator);

	}
	
	private void findHighlightingRuleForNode(INode node, Collection<AbstractHighlightingRule> rules, IHighlightedPositionAcceptor acceptor){
		EObject moslObject = NodeModelUtils.findActualSemanticObjectFor(node);
		List<AbstractHighlightingRule> candidates = rules.parallelStream().filter(rule -> rule.canProvideHighlighting(moslObject, node)).collect(Collectors.toList());
		AbstractHighlightingRule rule = candidates.stream().sorted(controller.getComparator()).findFirst().orElse(null);
		if(rule != null) {
			rule.setHighlighting(node, acceptor);
		}
	}
}
