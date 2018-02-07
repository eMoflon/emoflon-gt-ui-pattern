package org.moflon.gt.mosl.pattern.language.ui.highlighting;

import org.moflon.gt.mosl.ide.ui.highlighting.AbstractTokenMapper;

import com.google.inject.Inject;

public class MOSLPatternTokenMapper extends AbstractTokenMapper {
	@Inject
	static MOSLPatternTokenMapper mapper = new MOSLPatternTokenMapper();
}
