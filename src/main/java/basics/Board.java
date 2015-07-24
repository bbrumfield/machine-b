
package basics;

import static constants.Constants.EMPTY_PIECE;
import static constants.Constants.KING_WHITE;
import static constants.Constants.PAWN_WHITE;
import static constants.Constants.PIECES_BLACK;
import static constants.Constants.PIECES_WHITE;

import java.util.Arrays;

import utils.Utils;

public class Board {

    private char[][] position;

    public Board(char[][] position) {
        this.position = position;
    }

    public void makeMove(Move move) {
        this.removePawnCapturedThroughEnPassantIfNecessary(move);

        this.moveRookIfCastling(move);
        this.movePiece(move);

        this.promoteIfNecessary(move);
    }

    private void movePiece(Move move) {
        char pieceToPlaceOnNewSquare = this.position[move.getOriginRow()][move.getOriginCol()];

        this.position[move.getOriginRow()][move.getOriginCol()] = EMPTY_PIECE;
        this.position[move.getTargetRow()][move.getTargetCol()] = pieceToPlaceOnNewSquare;
    }

    private void moveRookIfCastling(Move move) {
        if(this.isKingsideCastleMove(move)) {
            Square kingsideRookOrigin = new Square(move.getOriginRow(), 7);
            Square kingsideRookTarget = new Square(move.getTargetRow(), 5);

            Move kingsideRookMove = new Move(kingsideRookOrigin, kingsideRookTarget);

            this.makeMove(kingsideRookMove);
        }
        else if(this.isQueensideCastleMove(move)) {
            Square queensideRookOrigin = new Square(move.getOriginRow(), 0);
            Square queensideRookTarget = new Square(move.getTargetRow(), 3);

            Move queensideRookMove = new Move(queensideRookOrigin, queensideRookTarget);

            this.makeMove(queensideRookMove);
        }

    }

    private void promoteIfNecessary(Move move) {
        if(move.isPromotion()) {
            int row = move.getTargetRow();
            int col = move.getTargetCol();

            // assume black is promoting, for now
            char promotedPiece = Character.toLowerCase(move.getPromotedPiece());

            // check if white is promoting
            if(move.getTargetRow() == 0) {
                promotedPiece = Character.toUpperCase(promotedPiece);
            }

            this.position[row][col] = promotedPiece;
        }
    }

    private void removePawnCapturedThroughEnPassantIfNecessary(Move move) {
        if(this.isEnPassantCapture(move)) {
            int row = move.getOriginRow();
            int col = move.getTargetCol();

            this.position[row][col] = EMPTY_PIECE;
        }
    }

    private boolean isEnPassantCapture(Move move) {
        return this.isPawnMove(move)
                && this.isDiagonalMove(move)
                && this.targetSquareIsUnoccupied(move);
    }

    private boolean isDiagonalMove(Move move) {
        return move.getRowDelta() == move.getColDelta();
    }

    public int getNumRows() {
        return this.position.length;
    }

    public int getNumCols() {
        return this.position[0].length;
    }

    public char getPieceAt(int row, int col) {
        return this.position[row][col];
    }

    public boolean targetSquareOfMoveIsWithinBounds(Move move) {
        return this.rowIsWithinBounds(move.getTargetRow()) &&
                this.colIsWithinBounds(move.getTargetCol());
    }

    private boolean rowIsWithinBounds(int row) {
        return Utils.isWithinBounds(row, 0, this.getNumRows());
    }

    private boolean colIsWithinBounds(int col) {
        return Utils.isWithinBounds(col, 0, this.getNumCols());
    }

    public boolean isCaptureMove(Move proposedMove) {
        return !this.targetSquareIsUnoccupied(proposedMove);
    }

    public boolean targetSquareIsUnoccupied(Move move) {
        return this.pieceAtSquareIs(move.getTargetRow(), move.getTargetCol(), EMPTY_PIECE);
    }

    public boolean squareIsUnoccupied(int row, int col) {
        return this.pieceAtSquareIs(row, col, EMPTY_PIECE);
    }

    public boolean squaresOfMoveAreOccupiedByOpposingPieces(Move move) {
        return piecesAreOfOpposingColors(
                this.getPieceAt(move.getOriginRow(), move.getOriginCol()),
                this.getPieceAt(move.getTargetRow(), move.getTargetCol()));
    }

    private static boolean piecesAreOfOpposingColors(char pieceA, char pieceB) {
        boolean aIsWhite = PIECES_WHITE.contains(pieceA);
        boolean bIsWhite = PIECES_WHITE.contains(pieceB);
        boolean aIsBlack = PIECES_BLACK.contains(pieceA);
        boolean bIsBlack = PIECES_BLACK.contains(pieceB);

        return (aIsWhite && bIsBlack) || (aIsBlack && bIsWhite);
    }

    public boolean pieceAtTargetIsIgnoreColor(Move move, char piece) {
        return this.pieceAtTargetIs(move, Character.toUpperCase(piece)) ||
                this.pieceAtTargetIs(move, Character.toLowerCase(piece));
    }

    public boolean pieceAtTargetIs(Move move, char piece) {
        return this.pieceAtSquareIs(move.getTargetRow(), move.getTargetCol(), piece);
    }

    public boolean pieceAtOriginIsIgnoreColor(Move move, char piece) {
        return this.pieceAtOriginIs(move, Character.toUpperCase(piece)) ||
                this.pieceAtOriginIs(move, Character.toLowerCase(piece));
    }

    private boolean pieceAtOriginIs(Move move, char piece) {
        return this.pieceAtSquareIs(move.getOriginRow(), move.getOriginCol(), piece);
    }

    private boolean pieceAtSquareIs(int row, int col, char piece) {
        return this.getPieceAt(row, col) == piece;
    }

    public boolean isPawnMove(Move proposedMove) {
        return this.movingPieceIsIgnoreColor(proposedMove, PAWN_WHITE);
    }

    private boolean isKingMove(Move proposedMove) {
        return this.movingPieceIsIgnoreColor(proposedMove, KING_WHITE);
    }

    private boolean movingPieceIsIgnoreColor(Move proposedMove, char piece) {
        return this.pieceAtOriginIsIgnoreColor(proposedMove, piece);
    }

    private boolean isVerticalTwoSquareMove(Move move) {
        return (move.getRowDelta() == 2) && (move.getColDelta() == 0);
    }

    public boolean isVerticalTwoSquarePawnMove(Move move) {
        return this.isPawnMove(move) && this.isVerticalTwoSquareMove(move);
    }

    private boolean isHorizontalTwoSquareMove(Move move) {
        return (move.getRowDelta() == 0) && (move.getColDelta() == 2);
    }

    public boolean isHorizontalTwoSquareKingMove(Move move) {
        return this.isKingMove(move) && this.isHorizontalTwoSquareMove(move);
    }

    private boolean isKingsideCastleMove(Move move) {
        return this.isHorizontalTwoSquareKingMove(move) && (move.getTargetCol() == 6);
    }

    private boolean isQueensideCastleMove(Move move) {
        return this.isHorizontalTwoSquareKingMove(move) && (move.getTargetCol() == 2);
    }

    public boolean isClearToCastleKingsideForWhite() {
        return this.squareIsUnoccupied(7, 5) && this.squareIsUnoccupied(7, 6);
    }

    public boolean isClearToCastleQueensideForWhite() {
        return this.squareIsUnoccupied(7, 1)
                && this.squareIsUnoccupied(7, 2)
                && this.squareIsUnoccupied(7, 3);
    }

    public boolean isClearToCastleKingsideForBlack() {
        return this.squareIsUnoccupied(0, 5) && this.squareIsUnoccupied(0, 6);
    }

    public boolean isClearToCastleQueensideForBlack() {
        return this.squareIsUnoccupied(0, 1)
                && this.squareIsUnoccupied(0, 2)
                && this.squareIsUnoccupied(0, 3);
    }

    public Board deepCopy() {
        return new Board(Utils.deepCopy(this.position));
    }

    public String toFenPiecePlacementString() {
        StringBuilder sb = new StringBuilder();

        int consecutiveEmpties = 0;
        char currentPiece;

        for(int i = 0; i < 64; ++i) {
            if((i > 0) && ((i % 8) == 0)) {
                if(consecutiveEmpties > 0) {
                    sb.append(consecutiveEmpties);
                }
                sb.append("/");
                consecutiveEmpties = 0;
            }

            currentPiece = this.getPieceAt(i / 8, i % 8);

            if(currentPiece == EMPTY_PIECE) {
                ++consecutiveEmpties;
            }
            else {
                if(consecutiveEmpties > 0) {
                    sb.append(consecutiveEmpties);
                }
                sb.append(currentPiece);
                consecutiveEmpties = 0;
            }
        }

        if(consecutiveEmpties > 0) {
            sb.append(consecutiveEmpties);
        }

        return sb.toString();
    }

    public String toCoordinateBoardString() {
        StringBuilder sb = new StringBuilder();

        char currentPiece;

        sb.append("  ");
        for(int column = 0; column < this.getNumCols(); column++) {
            sb.append("   " + column);
        }

        sb.append("\n   +---+---+---+---+---+---+---+---+\n");
        for(int i = 0; i < this.position.length; ++i) {
            if(i > 0) {
                sb.append("\n");
            }
            sb.append(" ").append(i).append(" | ");
            for(int j = 0; j < this.position[i].length; ++j) {
                if(j > 0) {
                    sb.append(' ');
                }
                currentPiece = this.position[i][j];
                sb.append(currentPiece).append(" |");
            }
            sb.append("\n   +---+---+---+---+---+---+---+---+");
        }

        return sb.toString();
    }

    public String toChessBoardString() {
        StringBuilder sb = new StringBuilder();

        char currentPiece;

        sb.append("   +---+---+---+---+---+---+---+---+\n");
        for(int i = 0; i < this.position.length; ++i) {
            if(i > 0) {
                sb.append("\n");
            }
            sb.append(" ").append(8 - i).append(" | ");
            for(int j = 0; j < this.position[i].length; ++j) {
                if(j > 0) {
                    sb.append(' ');
                }
                currentPiece = this.position[i][j];
                sb.append(currentPiece).append(" |");
            }
            sb.append("\n   +---+---+---+---+---+---+---+---+");
        }

        sb.append("\n  ");
        for(char file = 'A'; file < ('A' + this.getNumCols()); file++) {
            sb.append("   " + file);
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return this.toChessBoardString();
    }

    /* eclipse-generated method */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + Arrays.hashCode(this.position);
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
        Board other = (Board) obj;
        if(!Arrays.deepEquals(this.position, other.position)) {
            return false;
        }
        return true;
    }

}
