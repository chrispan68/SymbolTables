/**   
Compilation: javac Parser.java

Java Syntax Interpreter
@author Alex Chen
 */

import java.util.HashMap;

public class Parser {
    public static final String EOF = "EOF",INT="INT",LPAR="LPAR",RPAR="RPAR";
    public static final char EMPTY = '\0';
    private Token curr_token;
    private Lexer lex;

    public Parser(Lexer lex) {
	this.lex = lex;
	curr_token = lex.next();	
    }
    
    public void error() {
	throw new Error("Parsing Error");
    }

    public void update() {
	curr_token = lex.next();
    }

    public void eat(String name) {
	if(curr_token.name().equals(name)) {
	    update();
	} else {
	    error();
	}
    }

    public AST nextInt() {
	if(curr_token.name().equals(INT)) {	    
	    Token<Integer> curr = (Token<Integer>)curr_token;
	    eat(INT);
	    return new NumNode(curr);
	} else if(curr_token.name().equals(LPAR)) {
	    eat(LPAR);
	    AST res = eval();
	    eat(RPAR);
	    return res;
	} else {
	    error();
	    return null;
	}
    }

    public AST nextTerm() {
        AST res = nextInt();

	while(curr_token.equals(Lexer.ops.get('*')) || curr_token.equals(Lexer.ops.get('/'))) {
	    Token<BinaryOperation> tok = curr_token;
	    eat(curr_token.name());
	    res = new BinOpNode(res,tok,nextInt());
	}	
	
	return res;
    }
    
    // calculator evaluates arithmetic expressions
    public AST eval() {
	AST res = nextTerm();
	
	while(curr_token.equals(Lexer.ops.get('+')) || curr_token.equals(Lexer.ops.get('-'))) {
	    Token<BinaryOperation> tok = curr_token;
	    eat(curr_token.name());
	    res = new BinOpNode(res,tok,nextTerm());
	}	

	return res;
    }
}
