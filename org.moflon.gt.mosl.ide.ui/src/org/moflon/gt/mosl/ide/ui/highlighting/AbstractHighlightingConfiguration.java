package org.moflon.gt.mosl.ide.ui.highlighting;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.moflon.gt.mosl.ide.ui.highlighting.rules.AbstractHighlightingRule;
import org.moflon.gt.mosl.ide.ui.highlighting.utils.XtextColor;
import org.moflon.gt.mosl.ide.ui.highlighting.utils.XtextColorManager;

/**
 * 
 * The AbstractHighlightingConfiguration provides the TextStyles to the TextEditor from Xtext.
 * So it needs for semantical highlighting the information of the TextStyles. It gets it from the {@link AbstractHighlightProviderController}
 * which gets the styles from the {@link AbstractHighlightingRule} provided by {@link AbstractSemanticHighlightingCalculator}. 
 * For special Token Highlighting for {@link AbstractHighlightingConfiguration} a static configuration like in {@link DefaultHighlightingConfiguration} has to be implemented.  
 * 
 * <h1> Example: </h1><i>A static Configuration</i><p>
 * 
 * <pre>
 * <code>public class MyDSLHighlightingConfiguration extends AbstractHighlightingConfiguration{
 * 	public static final String MY_KEYWORD_ID = "MyKeyword";
 * 	...	
 * 	{@literal @}Override 
 * 	public void configure(IHighlightingConfigurationAcceptor acceptor) {
 * 		super.configure(acceptor);
 * 		acceptor.acceptDefaultHighlighting(MY_KEYWORD_ID, "MyKeyword", myKeywordTextStyle());
 * 		...
 *	}
 *	public TextStyle myKeywordTextStyle() {
 *		TextStyle textStyle = defaultTextStyle().copy();
 *		textStyle.setColor(colorManager.getColor(XtextColor.VIOLETT));
 *		textStyle.setStyle(SWT.BOLD);
 *		return textStyle;
 *	}
 *	...
 * }</code>
 * </pre>
 * 
 * @see AbstractHighlightingRule
 * @see AbstractSemanticHighlightingCalculator
 * @see AbstractHighlightProviderController
 * @see AbstractTokenMapper
 * @see DefaultHighlightingConfiguration
 * @see XtextColorManager
 * @see XtextColor
 * 
 * @author SaschaEdwinZander
 *
 */

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
