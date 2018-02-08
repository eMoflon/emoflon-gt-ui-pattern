package org.moflon.gt.mosl.ide.ui.highlighting;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.gt.mosl.ide.ui.highlighting.rules.AbstractHighlightingRule;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.wiring.BundleWiring;

public class HighlightAutoFactory {
	protected AbstractHighlightProviderController controller;
	
	protected Logger logger;
	
	public HighlightAutoFactory() {
		logger = Logger.getLogger(this.getClass());
	}
	
	public void createAllInstances() {
		String pluginName = WorkspaceHelper.getPluginId(controller.getClass());
		Bundle bundle = FrameworkUtil.getBundle(controller.getClass());
		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);
		Collection<String> resources = bundleWiring.listResources(pluginName.replaceAll("\\.", "/"), "*.class", BundleWiring.LISTRESOURCES_RECURSE);
		List<String> classNames = resources.parallelStream().map(resource -> resource.replaceAll("/", "\\.").replace(".class", "")).collect(Collectors.toList());
		List<Class<?>> classes = classNames.parallelStream().map(className -> loadClass(className, bundle)).collect(Collectors.toList());
		List<Class<?>> ruleClasses = classes.parallelStream().filter(this::isExecutableAndRegisteredRule).collect(Collectors.toList());
		ruleClasses.addAll(manuallyLoadedClasses().parallelStream().filter(this::isConcreteHighlightingRule).map(classRule -> (Class<?>) classRule).collect(Collectors.toList()));
		createInstances(ruleClasses);
	}
	
	private boolean isExecutableAndRegisteredRule(Class<?> ruleClass) {
		return isConcreteHighlightingRule(ruleClass) && ruleClass.isAnnotationPresent(RegisterRule.class);
	}
	
	private boolean isConcreteHighlightingRule(Class<?> ruleClass) {
		return ruleClass != null && AbstractHighlightingRule.class.isAssignableFrom(ruleClass) && !Modifier.isAbstract(ruleClass.getModifiers()) && hasCorrectConstructor(ruleClass);
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
	
	protected List<Class<? extends AbstractHighlightingRule>> manuallyLoadedClasses(){		
		return new ArrayList<>();
	}
	
	private Class<?> loadClass(String className, Bundle bundle){
		try {
			return bundle.loadClass(className);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
}
