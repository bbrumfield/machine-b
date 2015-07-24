
package basics;

public class EnPassantTarget {

    private Square target;

    public EnPassantTarget() {

    }

    public void update(int row, int col) {
        this.target = new Square(row, col);
    }

    public void update(Board board, Move move) {
        if(board.isVerticalTwoSquarePawnMove(move)) {
            int row = (move.getOriginRow() + move.getTargetRow()) / 2;
            int col = move.getTargetCol();

            this.update(row, col);
        }
        else {
            this.target = null;
        }
    }

    public Square getEnPassantTargetSquare() {
        return this.target;
    }

    public EnPassantTarget deepCopy() {
        EnPassantTarget copy = new EnPassantTarget();

        if(null != this.target) {
            copy.target = this.target.deepCopy();
        }

        return copy;
    }
}
