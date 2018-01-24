package org.moflon.gt.mosl.ide.core.ui.highlighting;

import org.eclipse.swt.SWT;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.gt.mosl.ide.core.ui.highlighting.rules.AbstractHighlightingRule;
import org.moflon.gt.mosl.ide.core.ui.highlighting.utils.MOSLColor;

public class MOSLHighlightingConfiguration extends DefaultHighlightingConfiguration
{
	public final static String BOOL_ID = "bool";
	public final static String ENUM_ID = "enum";
	public final static String SOURCE_ID = "source";
	public final static String TARGET_ID = "target";
	
	private MOSLHighlightProviderController controller;
	
	public MOSLHighlightingConfiguration(MOSLHighlightProviderController controller){
		super();
		this.controller = controller;
	}
	
	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		super.configure(acceptor);
		acceptor.acceptDefaultHighlighting(BOOL_ID, "Boolean", boolTextStyle());
		acceptor.acceptDefaultHighlighting(ENUM_ID, "Enumeration", stringTextStyle());
		acceptor.acceptDefaultHighlighting(SOURCE_ID, "Source Design", sourceTextStyle());
		acceptor.acceptDefaultHighlighting(TARGET_ID, "Target Design", targetTextStyle());		
		for(AbstractHighlightingRule rule : controller.getHighlightRules())
			rule.setHighlightingConfiguration(acceptor);
	}
	
   @Override
   public TextStyle keywordTextStyle()
   {
      TextStyle ts = super.keywordTextStyle();
      ts.setStyle(SWT.ITALIC);
      return ts;
   }
   
   @Override
   public TextStyle commentTextStyle(){
	   TextStyle ts = super.commentTextStyle();
	   ts.setColor(controller.getColorManager().getColor(MOSLColor.GRAY));
	   return ts;
   }
   
   @Override
	public TextStyle stringTextStyle() {
		TextStyle textStyle = super.stringTextStyle();
		textStyle.setColor(controller.getColorManager().getColor(MOSLColor.DARK_ORANGE));
		return textStyle;
	}
   
   public TextStyle boolTextStyle(){
	   TextStyle ts = super.keywordTextStyle();
	   ts.setStyle(SWT.BOLD);
	   return ts;
   }
   
   public TextStyle sourceTextStyle(){
	   TextStyle ts = keywordTextStyle();
	   ts.setBackgroundColor(controller.getColorManager().getColor(MOSLColor.LIGHT_YELLOW));
	   ts.setColor(controller.getColorManager().getColor(MOSLColor.BLACK));
	   return ts;
   }
   
   public TextStyle targetTextStyle(){
	   TextStyle ts = keywordTextStyle();
	   ts.setBackgroundColor(controller.getColorManager().getColor(MOSLColor.MISTY_ROSE));
	   ts.setColor(controller.getColorManager().getColor(MOSLColor.BLACK));
	   return ts;
   }
   
}
