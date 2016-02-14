public abstract class AST<T> {
    protected Token<T> tok;
    public Token<T> root() {
	return tok;
    }
}
