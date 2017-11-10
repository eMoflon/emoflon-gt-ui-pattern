package org.moflon.ide.mosl.core.ui.highlighting.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import org.moflon.ide.mosl.core.ui.highlighting.rules.AbstractHighlightingRule;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface HighlightCreation {
   Class<? extends AbstractHighlightingRule> clazz();
}
