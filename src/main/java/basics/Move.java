
package basics;

public final class Move {

    private static final char NO_PROMOTION = ' ';

    private Square origin;
    private Square target;

    private char promotedPiece;

    public Move(Square origin, Square target, char promotedPiece) {
        this.origin = origin;
        this.target = target;

        this.promotedPiece = promotedPiece;
    }

    public Move(Square origin, Square target) {
        this(origin, target, NO_PROMOTION);
    }

    public Move(int originRow, int originCol, int targetRow, int targetCol, char promotedPiece) {
        this(new Square(originRow, originCol), new Square(targetRow, targetCol), promotedPiece);
    }

    public Move(int originRow, int originCol, int targetRow, int targetCol) {
        this(originRow, originCol, targetRow, targetCol, NO_PROMOTION);
    }

    public int getOriginRow() {
        return this.origin.getRow();
    }

    public int getOriginCol() {
        return this.origin.getCol();
    }

    public int getTargetRow() {
        return this.target.getRow();
    }

    public int getTargetCol() {
        return this.target.getCol();
    }

    public int getRowDelta() {
        return Math.abs(this.getOriginRow() - this.getTargetRow());
    }

    public int getColDelta() {
        return Math.abs(this.getOriginCol() - this.getTargetCol());
    }

    public void promote(char promotedPiece) {
        this.promotedPiece = promotedPiece;
    }

    public boolean isPromotion() {
        return this.promotedPiece != NO_PROMOTION;
    }

    public char getPromotedPiece() {
        return this.promotedPiece;
    }

    private String getPromotedPieceOrEmptyString() {
        return this.isPromotion() ? String.valueOf(this.promotedPiece) : "";
    }

    public String toChessMoveString() {
        return this.origin.toChessSquareString()
                + this.target.toChessSquareString()
                + this.getPromotedPieceOrEmptyString();
    }

    public String toCoordinateMoveString() {
        return this.origin.toCoordinateSquareString()
                + this.target.toCoordinateSquareString()
                + this.getPromotedPieceOrEmptyString();
    }

    public Move deepCopy() {
        return new Move(
                this.getOriginRow(),
                this.getOriginCol(),
                this.getTargetRow(),
                this.getTargetCol(),
                this.getPromotedPiece());
    }

    /* eclipse-generated method */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.origin == null) ? 0 : this.origin.hashCode());
        result = (prime * result) + ((this.target == null) ? 0 : this.target.hashCode());
        result = (prime * result) + this.promotedPiece;
        return result;
    }

    /* eclipse-generated method */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(this.getClass() != obj.getClass()) {
            return false;
        }
        Move other = (Move) obj;
        if(this.origin == null) {
            if(other.origin != null) {
                return false;
            }
        }
        else if(!this.origin.equals(other.origin)) {
            return false;
        }
        if(this.target == null) {
            if(other.target != null) {
                return false;
            }
        }
        else if(!this.target.equals(other.target)) {
            return false;
        }
        if(this.promotedPiece != other.promotedPiece) {
            return false;
        }
        return true;
    }

}
