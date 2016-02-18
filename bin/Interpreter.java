/**   
Compilation: javac Interpreter.java
Execution: java Interpreter

Java Syntax Interpreter
@author Alex Chen

Parser generates a syntax tree representing arithmetic expressions
Follows rules according to the Lexer's symbol table
Interpreter traverses the tree to evaluate the expression
Calculator supports all basic operations + - * / and parentheses ( )

Sample Output:
$ java Interpreter
>>> 2/2
1
>>> 2*   7
14
>>> 2  /5 +1*4
4
>>> 30/  2 * 6-  1
89
>>> 78*14+   3/3
1093
>>> 1*(2+3)
5
>>> 1* (2/(9   -7))
1
>>> 10*(2/(33 -(4*(2-2)) +6) + 1)
10
>>> 2+-2
0
>>> 2++2
4
 */

import java.util.HashMap;
import java.util.Scanner;

public class Interpreter {
    public static final String EOF = "EOF",INT="INT",LPAR="LPAR",RPAR="RPAR";
    public static final char EMPTY = '\0';
    private Parser p;

    public Interpreter(Parser par) {
	p = par;
    }
    
    public void error() {
	throw new Error("Syntax Error");
    }

    public int visitBinOpNode(BinOpNode bon) {
	return bon.root().symbol().operate(visit(bon.left()),visit(bon.right()));
    }

    public int visitUnOpNode(UnOpNode uon) {
	System.out.println(uon.root());
	return uon.root().symbol().operate(visit(uon.child()));
    }

    public int visitNumNode(NumNode nn) {
	return nn.value();
    }

    // generalize visit method
    // getClass().getName()
    public int visit(AST node) {	
	Token t = node.root();
	//System.out.println("++"+node);
	//System.out.println(node.getClass());
	//String class_name = node.getClass().toString();
	String class_name = node.getClass().getName();
	System.out.println(class_name);
	if(class_name.equals("UnOpNode")) {
	    return visitUnOpNode((UnOpNode)node);
	} else if(class_name.equals("BinOpNode")) {
	    return visitBinOpNode((BinOpNode)node);
	}
	
	if(Lexer.ops.containsValue(t)) {
	    return visitBinOpNode((BinOpNode)node);
	} else if(t.name().equals("INT")) {
	    return visitNumNode(new NumNode(new Token<Integer>(t.name(),(int)t.symbol())));
	} /*else if(t.name().equals("PLUS") || t.name().equals("MINUS")) {
	    //return visitUnOpNode((UnOpNode)node);
	    //return visitBinOpNode((BinOpNode)node);
	    //return visitBinOpNode(new BinOpNode(node.left(),new AddOp(),node.right()));
	    }*/
	else {
	    return 0;
	}
    }

    public int interpret() {
	AST syntax_tree = p.eval();
	return visit(syntax_tree);
    }
    
    public static void main(String[]args) {	
	Scanner in = new Scanner(System.in);
	while(true) {
	    System.out.print(">>> ");
	    String text = in.nextLine();
	    Lexer lex = new Lexer(text);
	    Parser p = new Parser(lex);
	    Interpreter z = new Interpreter(p);
	    int res = z.interpret();
	    System.out.println(res);
	}
    }
}
