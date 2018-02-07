package org.moflon.gt.mosl.ide.ui.highlighting;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.gt.mosl.ide.ui.highlighting.rules.AbstractHighlightingRule;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.wiring.BundleWiring;

public abstract class AbstractHighlightFactory {
	protected AbstractHighlightProviderController controller;
	
	protected Logger logger;
	
	public AbstractHighlightFactory() {
		logger = Logger.getLogger(this.getClass());
	}
	
	public void createAllInstances() {
		String pluginName = WorkspaceHelper.getPluginId(this.getClass());
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);
		Collection<String> resources = bundleWiring.listResources(pluginName.replaceAll("\\.", "/"), "*.class", BundleWiring.LISTRESOURCES_RECURSE);
		List<String> classNames = resources.parallelStream().map(resource -> resource.replaceAll("/", "\\.").replace(".class", "")).collect(Collectors.toList());
		List<Class<?>> classes = classNames.parallelStream().map(this::loadClass).collect(Collectors.toList());
		createInstances(classes.parallelStream().filter(this::isExecutableAndRegisteredRule).collect(Collectors.toList()));
	}
	
	private boolean isExecutableAndRegisteredRule(Class<?> ruleClass) {
		return ruleClass != null && AbstractHighlightingRule.class.isAssignableFrom(ruleClass) && !Modifier.isAbstract(ruleClass.getModifiers()) && ruleClass.isAnnotationPresent(RegisterRule.class) && hasCorrectConstructor(ruleClass);
	}
	
	private void createInstances(List<Class<?>> classes) {
		classes.parallelStream().map(this::getConstructor).forEach(this::createInstance);
	}
	
	private void createInstance(Constructor<?> constructor) {
		try {
			constructor.newInstance(controller);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException	| InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private Constructor<?> getConstructor(Class<?> clazz){
		try {
			return clazz.getConstructor(AbstractHighlightProviderController.class);
		} catch (NoSuchMethodException | SecurityException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	private boolean hasCorrectConstructor(Class<?> clazz) {
		try {
			clazz.getConstructor(AbstractHighlightProviderController.class);
			return true;
		} catch (NoSuchMethodException | SecurityException e) {
			logger.error("Class " + clazz.getName() + " has no Constructor with only the Controller as argument");
			return false;
		}
	}
	
	void setController(AbstractHighlightProviderController controller){
		this.controller = controller;
	}
	
	private Class<?> loadClass(String className){
		try {
			return this.getClass().getClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
}
