package org.moflon.ide.mosl.core.ui.highlighting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.moflon.ide.mosl.core.ui.highlighting.exceptions.IDAlreadyExistException;
import org.moflon.ide.mosl.core.ui.highlighting.rules.AbstractHighlightingRule;
import org.moflon.ide.mosl.core.ui.highlighting.utils.MOSLColorManager;

import com.google.inject.Binder;

public class MOSLHighlightProviderController {
	
	private List<AbstractHighlightingRule> rules = new ArrayList<>(); 
	private Set<String> ruleNames = new HashSet<>();
	
	private MOSLColorManager colorManager;
	private MOSLHighlightingConfiguration config;
	
	public MOSLHighlightProviderController() {
	   this.colorManager = new MOSLColorManager(this);
	   this.config = new MOSLHighlightingConfiguration(this);
	}
	
	public void init(AbstractMOSLHighlightFactory rulesFactory){
		rules.clear();
		ruleNames.clear();
		rulesFactory.createAllInstances();
		rules.sort(getComparator());
	}
	
	public void addHighlightRule(AbstractHighlightingRule rule) throws IDAlreadyExistException{
		if(ruleNames.contains(rule.getID()))
			throw new IDAlreadyExistException();
		else{
			rules.add(rule);
			ruleNames.add(rule.getID());
		}
	}
	
	public MOSLColorManager getColorManager(){
	   return this.colorManager;
	}
	
	public MOSLHighlightingConfiguration getConfig(){
	   return this.config;
	}
	
	public void bind(Binder binder) {
//      binder.bind(DefaultSemanticHighlightingCalculator.class).to(MOSLSemanticHighlightCalculator.class);
//      binder.bind(AbstractAntlrTokenToAttributeIdMapper.class).to(MOSLTokenMapper.class);      
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
