package org.moflon.gt.mosl.ide.ui.highlighting;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.xtext.ide.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ide.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.moflon.gt.mosl.ide.ui.highlighting.rules.AbstractHighlightingRule;
import org.moflon.gt.mosl.ide.ui.highlighting.utils.XtextColorManager;

import com.google.inject.Binder;

/**
 *
 * This class is the most important class of the highlighting framework. It
 * shares the information of all different kinds of Highlighting. It also
 * connects the Highlighting It is possible to override the
 * {@link HighlightAutoFactory} by giving the Class of the Subclass to the
 * constructor; The controller must be registered by the UIModule (a Xtend
 * class, it is generated)
 *
 * <h1>Examples
 * <h1>
 *
 * <h2>Register Example:</h1> <i>Register the Controller in the UI</i>
 * <p>
 *
 * <pre>
 * <code>class MyDSLUiModule extends AbstractMyDSLUiModule {
 *	private val controller = new MyDSLProviderController()
 *
 *	override def void configure(Binder binder){
 *		controller.bind(binder)
 *		super.configure(binder)
 *	}
 * }</code>
 * </pre>
 *
 * <h2>Example 1:</h2> <i>A Controller with new HighlightAutoFactory</i>
 * <p>
 *
 * <pre>
 * <code>public class MyDSLProviderController extends AbstractHighlightProviderController {
 *
 *	public MyDSLProviderController(Class<? extends AbstractTokenMapper> tokenClass) {
 *		super(MyDSLAutoFactory.class,tokenClass);
 *	}
 *
 *	{@literal @}Override
 *	protected Class<? extends AbstractSemanticHighlightingCalculator> getCalculatorClass() {
 *		return MyDSLHighlightingCalculator.class;
 *	}
 *
 *	{@literal @}Override
 *	protected Class<? extends AbstractHighlightingConfiguration> getConfigClass() {
 *		return MyDSLHighlightingConfiguration.class;
 *	}
 *
 * }</code>
 * </pre>
 *
 * <h2>Example 2:</h2> <i>A Controller without new HighlightAutoFactory</i>
 *
 * <pre>
 * <code>public class MyDSLProviderController extends AbstractHighlightProviderController {
 *
 *	public MyDSLProviderController(Class<? extends AbstractTokenMapper> tokenClass) {
 *		super(tokenClass);
 *	}
 *
 *	{@literal @}Override
 *	protected Class<? extends AbstractSemanticHighlightingCalculator> getCalculatorClass() {
 *		return MyDSLHighlightingCalculator.class;
 *	}
 *
 *	{@literal @}Override
 *	protected Class<? extends AbstractHighlightingConfiguration> getConfigClass() {
 *		return MyDSLHighlightingConfiguration.class;
 *	}
 *
 * }</code>
 * </pre>
 *
 *
 *
 * @see AbstractTokenMapper
 * @see AbstractHighlightingRule
 * @see AbstractHighlightingConfiguration
 * @see AbstractSemanticHighlightingCalculator
 * @see HighlightAutoFactory
 *
 * @author SaschaEdwinZander
 *
 */
public abstract class AbstractHighlightProviderController {

	private List<AbstractHighlightingRule> rules = new ArrayList<>();
	private Set<String> ruleNames = new HashSet<>();
	private AbstractHighlightingConfiguration config;
	private XtextColorManager colorManager;
	private Class<? extends AbstractTokenMapper> tokenMapperClass;
	private static Logger logger = Logger.getLogger(AbstractHighlightProviderController.class);

	public AbstractHighlightProviderController(Class<? extends HighlightAutoFactory> factoryClass,
			Class<? extends AbstractTokenMapper> tokenClass) {
		this(createInstance(factoryClass), tokenClass);
	}

	public AbstractHighlightProviderController(Class<? extends AbstractTokenMapper> tokenClass) {
		this(new HighlightAutoFactory(), tokenClass);
	}

	private static HighlightAutoFactory createInstance(Class<? extends HighlightAutoFactory> factoryClass) {
		Constructor<?> factoryConstructor = Arrays.asList(factoryClass.getConstructors()).parallelStream()
				.filter(constructor -> constructor.getParameterCount() == 0).findAny().orElse(null);
		try {
			return HighlightAutoFactory.class.cast(factoryConstructor.newInstance());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	private AbstractHighlightProviderController(HighlightAutoFactory rulesFactory,
			Class<? extends AbstractTokenMapper> tokenClass) {
		AbstractHighlightingConfiguration.setController(getConfigClass(), this);
		init(rulesFactory);
		AbstractSemanticHighlightingCalculator.registerController(getCalculatorClass(), this);
		this.tokenMapperClass = tokenClass;
	}

	protected abstract Class<? extends AbstractHighlightingConfiguration> getConfigClass();

	protected abstract Class<? extends AbstractSemanticHighlightingCalculator> getCalculatorClass();

	void setColorManager(XtextColorManager colorManager) {
		this.colorManager = colorManager;
	}

	void setConfig(AbstractHighlightingConfiguration config) {
		this.config = config;
	}

	public AbstractHighlightingConfiguration getConfig() {
		if (config != null)
			return config;
		else
			try {
				return getConfigClass().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				logger.error(e.getMessage(), e);
				return null;
			}
	}

	private void init(HighlightAutoFactory rulesFactory) {
		rules.clear();
		ruleNames.clear();
		rulesFactory.setController(this);
		rulesFactory.createAllInstances();
	}

	public void addHighlightRule(AbstractHighlightingRule rule) {
		if (ruleNames.contains(rule.getID()))
			throw new IllegalArgumentException("ID already in use");
		else {
			rules.add(rule);
			ruleNames.add(rule.getID());
		}
	}

	public XtextColorManager getColorManager() {
		return this.colorManager;
	}

	public void bind(Binder binder) {
		bindSemanticCalculator(binder, getCalculatorClass());
		bindTokenMapper(binder, tokenMapperClass);
		binder.bind(IHighlightingConfiguration.class).to(getConfigClass());
	}

	private void bindSemanticCalculator(Binder binder, Class<? extends AbstractSemanticHighlightingCalculator> clazz) {
		binder.bind(DefaultSemanticHighlightingCalculator.class).to(clazz);
	}

	private void bindTokenMapper(Binder binder, Class<? extends AbstractTokenMapper> clazz) {
		binder.bind(AbstractAntlrTokenToAttributeIdMapper.class).to(clazz);
	}

	public List<AbstractHighlightingRule> getHighlightRules() {
		return rules;
	}

	Comparator<? super AbstractHighlightingRule> getComparator() {
		return new Comparator<AbstractHighlightingRule>() {
			@Override
			public int compare(AbstractHighlightingRule rule1, AbstractHighlightingRule rule2) {
				return (int) Math.signum(rule2.getPriority() - rule1.getPriority());
			}
		};
	}
}
