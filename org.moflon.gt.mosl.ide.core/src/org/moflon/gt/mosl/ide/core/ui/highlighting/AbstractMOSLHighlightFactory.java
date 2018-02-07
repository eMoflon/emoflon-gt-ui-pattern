package org.moflon.gt.mosl.ide.core.ui.highlighting;

public abstract class AbstractMOSLHighlightFactory {
	
	/**
	 * In this method must all new HighlightingRules be created. If a Rule is not created it will not be used.
	 */
	public abstract void createAllInstances();
}
