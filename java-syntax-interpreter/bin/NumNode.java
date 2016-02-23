/**
   AST represents integer node.
*/
public class NumNode extends AST<Integer> {
    private int value;

    public NumNode(Token<Integer> tok) {
	this.tok = tok;
	this.value = tok.symbol();
    }

    public int value() {
	return value;
    }
}
