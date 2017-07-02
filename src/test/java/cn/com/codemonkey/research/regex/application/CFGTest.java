package cn.com.codemonkey.research.regex.application;

import org.junit.Test;

import cn.com.codemonkey.research.regex.application.CFG;

public class CFGTest {

	@Test
	public void testToString() {
		CFG cfg = new CFG();
		cfg.addNonTerminal("EXPR");
		cfg.addNonTerminal("PROD");
		cfg.addNonTerminal("SIMP");
		cfg.addTerminal("+");
		cfg.addTerminal("-");
		cfg.addTerminal("*");
		cfg.addTerminal("/");
		cfg.addTerminal("num");
		cfg.addTerminal("id");
		cfg.setStartSymbol("EXPR");
		cfg.addProduction("EXPR", "EXPR", "+", "PROD");
		cfg.addProduction("EXPR", "EXPR", "-", "PROD");
		cfg.addProduction("EXPR", "PROD");
		cfg.addProduction("PROD", "PROD", "*", "SIMP");
		cfg.addProduction("PROD", "PROD", "/", "SIMP");
		cfg.addProduction("PROD", "SIMP");
		cfg.addProduction("SIMP", "num");
		cfg.addProduction("SIMP", "id");

		System.out.println(cfg);
	}
}
