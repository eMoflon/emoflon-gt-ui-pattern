package org.moflon.gt.mosl.ide.ui.highlighting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.xtext.ide.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ide.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.moflon.gt.mosl.ide.ui.highlighting.exceptions.IDAlreadyExistException;
import org.moflon.gt.mosl.ide.ui.highlighting.rules.AbstractHighlightingRule;
import org.moflon.gt.mosl.ide.ui.highlighting.utils.XtextColorManager;

import com.google.inject.Binder;




public abstract class AbstractHighlightProviderController {
	
	private List<AbstractHighlightingRule> rules = new ArrayList<>(); 
	private Set<String> ruleNames = new HashSet<>();
	private final AbstractSemanticHighlightingCalculator semanticCalculator;
	private XtextColorManager colorManager;
	private AbstractHighlightingConfiguration config;
	private Class<? extends AbstractTokenMapper> tokenMapperClass;
	
	public AbstractHighlightProviderController(AbstractHighlightFactory rulesFactory, Class<? extends AbstractTokenMapper> tokenClass) {
	   init(rulesFactory);
	   this.config = createConfig();
	   this.colorManager = config.getColorManager();
	   semanticCalculator = this.createtSemanticHighlightingCalculator();
	   AbstractSemanticHighlightingCalculator.setController(this);
	   this.tokenMapperClass = tokenClass;
	}
	
	protected abstract AbstractHighlightingConfiguration createConfig();
	
	protected abstract AbstractSemanticHighlightingCalculator createtSemanticHighlightingCalculator();
	
	public void init(AbstractHighlightFactory rulesFactory){
		rules.clear();
		ruleNames.clear();
		rulesFactory.setController(this);
		rulesFactory.createAllInstances();
		rules.sort(getComparator());
	}
	
	public void addHighlightRule(AbstractHighlightingRule rule) throws IDAlreadyExistException{
		if(ruleNames.contains(rule.getID()))
			throw new IDAlreadyExistException();
		else{
			rules.add(rule);
			ruleNames.add(rule.getID());
			AbstractHighlightingConfiguration.addModularConfig(rule);
		}
	}
	
	public XtextColorManager getColorManager(){
	   return this.colorManager;
	}
	
	public AbstractSemanticHighlightingCalculator getSemanticCalculator() {
		return this.semanticCalculator;
	}
	
	public AbstractHighlightingConfiguration getConfig(){
	   return this.config;
	}
	
	public void bind(Binder binder) {
		bindSemanticCalculator(binder, semanticCalculator.getClass());
		bindTokenMapper(binder, tokenMapperClass);
		binder.bind(IHighlightingConfiguration.class).to(config.getClass());
   }
	
	private void bindSemanticCalculator(Binder binder, Class<? extends AbstractSemanticHighlightingCalculator> clazz) {
		binder.bind(DefaultSemanticHighlightingCalculator.class).to(clazz);
	}
	
	private void bindTokenMapper(Binder binder, Class<? extends AbstractTokenMapper> clazz) {
		binder.bind(AbstractAntlrTokenToAttributeIdMapper.class).to(clazz);
	}
	
	public  Collection<AbstractHighlightingRule> getHighlightRules(){
		return rules;
	}
	
	private  Comparator<? super AbstractHighlightingRule> getComparator(){
		return new Comparator<AbstractHighlightingRule>() {
			@Override
			public int compare(AbstractHighlightingRule rule1, AbstractHighlightingRule rule2) {
				return (int) Math.signum(rule2.getPriority()-rule1.getPriority());
			}
		};
	}
}
