package org.moflon.gt.mosl.ide.ui.highlighting;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.xtext.ide.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.util.Arrays;
/**
 * The AbstractTokenMapper provides text highlighting for Xtext terminals or specific keywords.
 * The AbstractTokenMapper has to be implemented and an Injection has to be created;
 * 
 *	<h1>Example:</h1> <i>new Coloring for a terminal and a keyword</i> <p>
 *  <b><i>Xtext DSL file</i></b><p>
 * <pre>
 * <code>
 * AnyXtextGrammarRule:'keyword' ...
 * 
 * terminal TerminalRule: ...
 * </code>
 * </pre>
 * 
 * <p><b><i>TokenMapper file</i></b><p>
 * <pre>
 * <code>public class MyDSLTokenMapper extends AbstractTokenMapper{
 * 	{@literal @}Inject 
 * 	static MyDSLTokenMapper mapper = new MyDSLTokenMapper();
 *
 * 	{@literal @}Override
 *  	protected String calculateId(String tokenName, int tokenType) {
 *  		String trimmedTokenName = tokenName.replaceAll("'", "");
 *		String id = super.calculateId(tokenName, tokenType);
 *		if(trimmedTokenName.equals("keyword")){
 *			id = MyDSLHighlightingConfiguration.MY_KEYWORD_ID;
 *		}
 *		else if(trimmedTokenName.equals("TerminalRule_Rule")){ //For terminals the token "[terminalRuleName]_Rule" will be created
 *			id = MyDSLHighlightingConfiguration.MY_DSL_TERMIANAL_RULE_ID;
 *		}
 *  		return id; 
 *  	} 
 * }</code>
 * </pre>
 *
 * @see AbstractHighlightProviderController
 * 
 * @author SaschaEdwinZander
 * 
 */
public abstract class AbstractTokenMapper extends DefaultAntlrTokenToAttributeIdMapper {

	public AbstractTokenMapper(){
		super();
		init();
	}
	
	private void init() {
		mappedTokens.clear();		
	}

	protected static Map<String, String> mappedTokens = new HashMap<>();
	
	private static final String[] delemiters = {":","{","}","(",")"};
	
	@Override
	protected String calculateId(String tokenName, int tokenType) {
		String trimmedTokenName = tokenName.replaceAll("'", "");
		String id = super.calculateId(tokenName, tokenType);		

		if(Arrays.contains(delemiters, trimmedTokenName)){
			id = AbstractHighlightingConfiguration.DEFAULT_ID;
		}
	
		mappedTokens.put(trimmedTokenName, id);
		return id;
	}
	
	public static String getIDFromToken(String token){
		String trimmedTokenName = token.replaceAll("'", "");
		return mappedTokens.get(trimmedTokenName);
	}
}
