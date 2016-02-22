/**
   AST represents a unary operation with single child.
*/
public class UnOpNode extends AST<UnaryOperation> {
    private AST child;

    public UnOpNode(Token<UnaryOperation> op, AST child) {
	this.tok = op;
	this.child = child;
    }

    public AST child() {
	return child;
    }
}
