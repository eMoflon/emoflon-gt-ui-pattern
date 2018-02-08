package org.moflon.gt.mosl.ide.ui.highlighting.rules;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.gt.mosl.ide.ui.highlighting.AbstractHighlightProviderController;
import org.moflon.gt.mosl.ide.ui.highlighting.exceptions.IDAlreadyExistException;



public abstract class AbstractHighlightingRule implements IModularConfiguration{

	protected Logger logger;
	
	private String id;
	
	private String description;
	
	protected AbstractHighlightProviderController controller;
	
	private int prio=50;
	
	public AbstractHighlightingRule(AbstractHighlightProviderController controller){
		init(controller);
	}
	
	protected void setPrio(int prio) {
		this.prio = prio;
	}
	
	private void init(AbstractHighlightProviderController controller){
		logger = Logger.getLogger(this.getClass());
		this.id = id();
		this.description = description();
		this.controller = controller;
		try {
			this.controller.addHighlightRule(this);
		} catch (IDAlreadyExistException e) {
			logger.error("ID already exist", e);
		}
	}
	
	protected abstract String id();
	
	protected abstract String description();
	
	public void setHighlighting(INode node, IHighlightedPositionAcceptor acceptor){
		acceptor.addPosition(node.getOffset(), node.getLength() , id);
	}
	
	protected abstract TextStyle getTextStyle();
	
	public void setHighlightingConfiguration(IHighlightingConfigurationAcceptor acceptor){
		acceptor.
		acceptDefaultHighlighting(id, description, getTextStyle());
	}
	
	public boolean canProvideHighlighting(EObject moslObject, INode node){
		if(!moslObject.eIsProxy())
			EcoreUtil.resolveAll(moslObject);
		return getHighlightingConditions(moslObject, node);
	}
	
	public int getPriority(){
		return prio;
	}
	
	protected abstract boolean getHighlightingConditions(EObject moslObject, INode node);
	
	public String getID(){
		return id;
	}
}
