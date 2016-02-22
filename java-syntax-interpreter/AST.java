/**
   Abstract Syntax Tree (AST) is a tree representing a string of characters.
   AST has different types of nodes according to different types of Tokens (generic T).
   Parser eats the tokens returned by Lexer to create an AST.
 */
public abstract class AST<T> {
    protected Token<T> tok;
    public Token<T> root() {
	return tok;
    }
}
