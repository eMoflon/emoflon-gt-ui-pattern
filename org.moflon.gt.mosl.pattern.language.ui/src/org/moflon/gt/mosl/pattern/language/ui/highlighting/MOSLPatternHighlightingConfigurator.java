package org.moflon.gt.mosl.pattern.language.ui.highlighting;

import org.eclipse.swt.SWT;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.gt.mosl.ide.ui.highlighting.AbstractHighlightingConfiguration;
import org.moflon.gt.mosl.ide.ui.highlighting.utils.XtextColor;

public class MOSLPatternHighlightingConfigurator extends AbstractHighlightingConfiguration {

	
	@Override
	public TextStyle commentTextStyle() {
		TextStyle ts = super.commentTextStyle();
		ts.setStyle(SWT.BOLD);
		ts.setColor(colorManager.getColor(XtextColor.GRAY));
		return ts;
	}
	

}
