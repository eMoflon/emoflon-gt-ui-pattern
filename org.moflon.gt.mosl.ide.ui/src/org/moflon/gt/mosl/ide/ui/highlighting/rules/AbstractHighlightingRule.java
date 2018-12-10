package org.moflon.gt.mosl.ide.ui.highlighting.rules;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.gt.mosl.ide.ui.highlighting.AbstractHighlightProviderController;
import org.moflon.gt.mosl.ide.ui.highlighting.AbstractHighlightingConfiguration;
import org.moflon.gt.mosl.ide.ui.highlighting.AbstractSemanticHighlightingCalculator;
import org.moflon.gt.mosl.ide.ui.highlighting.HighlightAutoFactory;
import org.moflon.gt.mosl.ide.ui.highlighting.RegisterRule;
import org.moflon.gt.mosl.ide.ui.highlighting.exceptions.IDAlreadyExistException;
import org.moflon.gt.mosl.ide.ui.highlighting.utils.XtextColor;
import org.moflon.gt.mosl.ide.ui.highlighting.utils.XtextColorManager;

/**
 * 
 * The AbstractHighlightingRule is the basic for semantic TextHighlighting. To
 * activate and create a HighlightingRule the Annotation {@link RegisterRule}
 * must be set at the Top of a class. It is not allowed to change the
 * constructor with more arguments, because Reflection is used.
 * 
 * <h1>Example:</h1> <i>A semantic rule for the name this</i>
 * 
 * <pre>
 * <code>@RegisterRule
 * public class HandleThisHighlightingRule extends AbstractHighlightingRule {
 * 
 * 	public HandleThisHighlightingRule(AbstractHighlightProviderController controller) {
 * 		super(controller);
 * 		setPrio(500);
 *	}
 *
 *	{@literal @}Override
 *	protected String id() {
 *		return "handleThis";
 *	}
 *
 *	{@literal @}Override
 *	protected String description() {
 *		return "If the user use the word this it should be handle like a keyword";
 *	}
 *
 *	{@literal @}Override
 *	protected TextStyle getTextStyle() {
 *		return controller.getConfig().keywordTextStyle();
 *	}
 *
 *	{@literal @}Override
 *	protected boolean getHighlightingConditions(EObject moslObject, INode node) {
 *		return "this".equals(node.getText());
 *	}
 *
 * }</code>
 * </pre>
 * 
 * @see RegisterRule
 * @see AbstractSemanticHighlightingCalculator
 * @see AbstractHighlightingConfiguration
 * @see HighlightAutoFactory
 * @see XtextColor
 * @see XtextColorManager
 *
 * @author SaschaEdwinZander
 *
 */

public abstract class AbstractHighlightingRule implements IModularConfiguration {

	protected Logger logger;

	/**
	 * The id must be an unique identifier to save in
	 * {@link AbstractHighlightingConfiguration}. The TextStyle will be identified
	 * from the id.
	 */
	private String id;

	private String description;

	protected AbstractHighlightProviderController controller;

	/**
	 * This is the priority. If the priority is higher the the Rule will be handled
	 * earlier.
	 */
	private int prio = 50;

	public AbstractHighlightingRule(AbstractHighlightProviderController controller) {
		init(controller);
	}

	protected void setPrio(int prio) {
		this.prio = prio;
	}

	private void init(AbstractHighlightProviderController controller) {
		logger = Logger.getLogger(controller.getClass().getName() + "::" + this.getClass().getName());
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

	public void setHighlighting(INode node, IHighlightedPositionAcceptor acceptor) {
		acceptor.addPosition(node.getOffset(), node.getLength(), id);
	}

	/**
	 * Here the style will be defined.
	 * 
	 * @return the new TextStyle for Highlighting
	 */
	protected abstract TextStyle getTextStyle();

	public void setHighlightingConfiguration(IHighlightingConfigurationAcceptor acceptor) {
		acceptor.acceptDefaultHighlighting(id, description, getTextStyle());
	}

	public boolean canProvideHighlighting(EObject moslObject, INode node) {
		if (moslObject.eIsProxy())
			EcoreUtil.resolveAll(moslObject);
		return getHighlightingConditions(moslObject, node);
	}

	public int getPriority() {
		return prio;
	}

	/**
	 * The Highlighting Condition should be defined here
	 * 
	 * @param moslObject the corresponding Xtext EObject which is defined in the
	 *                   DSL.
	 * @param node       the current node. This node is from an editor AST which
	 *                   contains every word or whitespaces.
	 * @return if the Rule fits
	 */
	protected abstract boolean getHighlightingConditions(EObject moslObject, INode node);

	public String getID() {
		return id;
	}
}
