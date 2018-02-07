package org.moflon.gt.ide.ui.highlighting;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.moflon.gt.ide.ui.highlighting.rules.IModularConfiguration;
import org.moflon.gt.ide.ui.highlighting.utils.XtextColorManager;



public abstract class AbstractHighlightingConfiguration extends DefaultHighlightingConfiguration
{
	protected XtextColorManager colorManager;
	
	private static List<IModularConfiguration> modularConfigs = new ArrayList<>();
	
	public AbstractHighlightingConfiguration(){
		super();
		this.colorManager = new XtextColorManager();
		colorManager.setConfig(this);
	}
	
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		super.configure(acceptor);
		modularConfigs.stream().forEach(conf -> conf.setHighlightingConfiguration(acceptor));
	}
	
	public XtextColorManager getColorManager() {
		return colorManager;
	}

	public static void addModularConfig(IModularConfiguration config) {
		modularConfigs.add(config);
	}
   
}
