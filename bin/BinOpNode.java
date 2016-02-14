public class BinOpNode extends AST<BinaryOperation> {
    private AST left, right;

    public BinOpNode(AST left, Token<BinaryOperation> op, AST right) {
	this.left = left;
	this.tok = op;
	this.right = right;
    }

    public AST left() {
	return left;
    }

    public AST right() {
	return right;
    }
}
