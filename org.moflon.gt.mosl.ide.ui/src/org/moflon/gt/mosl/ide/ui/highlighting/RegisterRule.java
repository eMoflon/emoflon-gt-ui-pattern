package org.moflon.gt.mosl.ide.ui.highlighting;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.moflon.gt.mosl.ide.ui.highlighting.rules.AbstractHighlightingRule;

/**
 * 
 * This Annotation is used to create, register and to activate a
 * HighlightingRule. There is one restriction it's only working for
 * HighlightingRules which are in the same Bundle as the implementation of the
 * {@link AbstractHighlightProviderController}
 *
 * @see AbstractHighlightProviderController
 * @see AbstractHighlightingRule
 * @see HighlightAutoFactory
 * 
 * @author SaschaEdwinZander
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface RegisterRule {

}
