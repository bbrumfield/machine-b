
package basics;

import java.util.ArrayList;
import java.util.List;

import utils.Utils;
import constants.Constants;

public class MoveTracker {

    private boolean whiteToMove;

    private boolean hasMovedWhiteKing;
    private boolean hasMovedOrLostWhiteKingsideRook;
    private boolean hasMovedOrLostWhiteQueensideRook;

    private boolean hasMovedBlackKing;
    private boolean hasMovedOrLostBlackKingsideRook;
    private boolean hasMovedOrLostBlackQueensideRook;

    // if >= 100 then either player may declare a draw according to the fifty-move rule
    private int halfMoveClock;

    private int fullMoveNumber;

    // should this be a field? it might not include all moves of the game...
    private List<Move> movesSoFar;

    public MoveTracker(boolean whiteToMove, int halfMoveClock, int fullMoveNumber) {
        this.whiteToMove = whiteToMove;
        this.halfMoveClock = halfMoveClock;
        this.fullMoveNumber = fullMoveNumber;
        this.movesSoFar = new ArrayList<Move>();
    }

    public void trackMove(Board board, Move move) {
        // alternate whose move it is
        this.whiteToMove = !this.whiteToMove;

        // update king/rook hasMoved fields
        this.checkIfKingOrRookHasMoved(board, move);

        // update halfMoveClock
        if(board.isCaptureMove(move) || board.isPawnMove(move)) {
            this.halfMoveClock = 0;
        }
        else {
            this.halfMoveClock++;
        }

        // update fullMoveNumber
        if(this.isWhiteToMove()) {
            this.fullMoveNumber++;
        }

        // add move to list of moves (the list of moves *should* never be null)
        this.movesSoFar.add(move);
    }

    private void checkIfKingOrRookHasMoved(Board board, Move move) {
        char movingPiece = board.getPieceAt(move.getOriginRow(), move.getOriginCol());

        if(Constants.ROOK_WHITE == movingPiece) {
            if(move.getOriginRow() == 7) {
                if(move.getOriginCol() == 0) {
                    this.hasMovedOrLostWhiteQueensideRook = true;
                }
                else if(move.getOriginCol() == 7) {
                    this.hasMovedOrLostWhiteKingsideRook = true;
                }
            }
        }
        else if(Constants.ROOK_BLACK == movingPiece) {
            if(move.getOriginRow() == 0) {
                if(move.getOriginCol() == 0) {
                    this.hasMovedOrLostBlackQueensideRook = true;
                }
                else if(move.getOriginCol() == 7) {
                    this.hasMovedOrLostBlackKingsideRook = true;
                }
            }
        }
        else if(Constants.KING_BLACK == movingPiece) {
            this.hasMovedBlackKing = true;
        }
        else if(Constants.KING_WHITE == movingPiece) {
            this.hasMovedWhiteKing = true;
        }
        // DO: do these next two blocks better
        else if(7 == move.getTargetRow()) {
            if(0 == move.getTargetCol()) {
                this.hasMovedOrLostWhiteQueensideRook = true;
            }
            else if(7 == move.getTargetCol()) {
                this.hasMovedOrLostWhiteKingsideRook = true;
            }
        }
        else if(0 == move.getTargetRow()) {
            if(0 == move.getTargetCol()) {
                this.hasMovedOrLostBlackQueensideRook = true;
            }
            else if(7 == move.getTargetCol()) {
                this.hasMovedOrLostBlackKingsideRook = true;
            }
        }

    }

    public boolean isWhiteToMove() {
        return this.whiteToMove;
    }

    public boolean hasMovedWhiteKing() {
        return this.hasMovedWhiteKing;
    }

    public boolean hasMovedOrLostWhiteKingsideRook() {
        return this.hasMovedOrLostWhiteKingsideRook;
    }

    public boolean hasMovedOrLostWhiteQueensideRook() {
        return this.hasMovedOrLostWhiteQueensideRook;
    }

    public boolean hasMovedBlackKing() {
        return this.hasMovedBlackKing;
    }

    public boolean hasMovedOrLostBlackKingsideRook() {
        return this.hasMovedOrLostBlackKingsideRook;
    }

    public boolean hasMovedOrLostBlackQueensideRook() {
        return this.hasMovedOrLostBlackQueensideRook;
    }

    public int getHalfMoveClock() {
        return this.halfMoveClock;
    }

    public int getFullMoveNumber() {
        return this.fullMoveNumber;
    }

    public List<Move> getMovesSoFar() {
        return this.movesSoFar;
    }

    public MoveTracker deepCopy() {
        MoveTracker copy =
                new MoveTracker(this.whiteToMove, this.halfMoveClock, this.fullMoveNumber);

        copy.hasMovedWhiteKing = this.hasMovedWhiteKing;
        copy.hasMovedOrLostWhiteKingsideRook = this.hasMovedOrLostWhiteKingsideRook;
        copy.hasMovedOrLostWhiteQueensideRook = this.hasMovedOrLostWhiteQueensideRook;
        copy.hasMovedBlackKing = this.hasMovedBlackKing;
        copy.hasMovedOrLostBlackKingsideRook = this.hasMovedOrLostBlackKingsideRook;
        copy.hasMovedOrLostBlackQueensideRook = this.hasMovedOrLostBlackQueensideRook;

        copy.movesSoFar = Utils.deepCopy(this.movesSoFar);

        return copy;
    }

}
