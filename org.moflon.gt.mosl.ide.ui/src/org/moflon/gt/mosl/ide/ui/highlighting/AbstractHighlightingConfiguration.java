package org.moflon.gt.mosl.ide.ui.highlighting;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.moflon.gt.mosl.ide.ui.highlighting.utils.XtextColorManager;



public abstract class AbstractHighlightingConfiguration extends DefaultHighlightingConfiguration
{
	protected XtextColorManager colorManager;
	private static Map<Class<? extends AbstractHighlightingConfiguration>, AbstractHighlightProviderController> staticContollers = new HashMap<>();
	
	
	public AbstractHighlightingConfiguration(){
		super();
		this.colorManager = new XtextColorManager();
		colorManager.setConfig(this);
		AbstractHighlightProviderController controller = staticContollers.get(getClass());
		controller.setColorManager(colorManager);
		controller.setConfig(this);
	}
	
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		super.configure(acceptor);
		staticContollers.get(this.getClass()).getHighlightRules().stream().forEach(conf -> conf.setHighlightingConfiguration(acceptor));
	}
	
	public XtextColorManager getColorManager() {
		return colorManager;
	}
   
	static void setController(Class<? extends AbstractHighlightingConfiguration> clazz, AbstractHighlightProviderController controller) {
		staticContollers.put(clazz, controller);
	}
}
