package org.moflon.gt.mosl.pattern.language.ui.highlighting;

import org.moflon.gt.mosl.ide.ui.highlighting.AbstractTokenMapper;

import com.google.inject.Inject;

public class MOSLPatternTokenMapper extends AbstractTokenMapper {
	@Inject
	static MOSLPatternTokenMapper mapper = new MOSLPatternTokenMapper();
	
	@Override
	protected String calculateId(String tokenName, int tokenType) {
		String trimmedTokenName = tokenName.replaceAll("'", "");
		String id = super.calculateId(tokenName, tokenType);
		if("this".equals(trimmedTokenName))
			id = MOSLPatternHighlightingConfigurator.KEYWORD_ID;
		
		mappedTokens.put(trimmedTokenName, id);
		return id;
	}
}
