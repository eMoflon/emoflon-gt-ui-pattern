package org.moflon.gt.mosl.ide.ui.highlighting;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ide.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator;
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.moflon.gt.mosl.ide.ui.highlighting.rules.AbstractHighlightingRule;



public abstract class AbstractSemanticHighlightingCalculator extends DefaultSemanticHighlightingCalculator {
   
	private AbstractHighlightProviderController controller;
	private static Map<Class<? extends AbstractSemanticHighlightingCalculator>, AbstractHighlightProviderController> staticContollers = new HashMap<>();
	private static Map<Class<? extends AbstractSemanticHighlightingCalculator>, AbstractSemanticHighlightingCalculator> instanceMap = new HashMap<>();
	
	public AbstractSemanticHighlightingCalculator() {
		controller = staticContollers.get(this.getClass());
		if(controller == null) {
			staticContollers.put(getClass(), null);
		}
		addInstance();
	}
	
	private void addInstance() {
		instanceMap.put(getClass(), this);
	}
	
	static void registerController (Class<? extends AbstractSemanticHighlightingCalculator> clazz, AbstractHighlightProviderController controller) {
		if(staticContollers.containsKey(clazz)) {
			AbstractHighlightProviderController otherController = staticContollers.get(clazz);
			if(controller.equals(otherController))
				return;
			else {
				instanceMap.get(clazz).controller = controller;
			}
		}
		staticContollers.put(clazz, controller);
	}
   
	@Override
	protected void doProvideHighlightingFor(XtextResource resource, IHighlightedPositionAcceptor acceptor,
			CancelIndicator cancelIndicator) {

		if (resource == null || resource.getParseResult() == null)
			return;
		INode rootNode = resource.getParseResult().getRootNode();
		List<AbstractHighlightingRule> rules = controller.getHighlightRules();
		for (INode node : rootNode.getLeafNodes()) {
			findHighlightingRuleForNode(node, rules, acceptor);
		}
		super.doProvideHighlightingFor(resource, acceptor, cancelIndicator);

	}
	
	private void findHighlightingRuleForNode(INode node, List<AbstractHighlightingRule> rules, IHighlightedPositionAcceptor acceptor){
		EObject moslObject = NodeModelUtils.findActualSemanticObjectFor(node);
		rules.sort(controller.getComparator());
		for (AbstractHighlightingRule rule : rules){
			if(rule.canProvideHighlighting(moslObject, node)) {
				rule.setHighlighting(node, acceptor);
				return;
			}
		}
	}
}
