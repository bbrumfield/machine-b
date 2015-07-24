
package basics;

import java.util.List;

public final class GameState {

    private final Board board;
    private final InitialCastlingEligibility initialCastlingEligibility;
    private final EnPassantTarget enPassantTarget;
    private final MoveTracker moveTracker;

    public GameState(
            Board board,
            InitialCastlingEligibility initialCastlingEligibility,
            EnPassantTarget enPassantTarget,
            MoveTracker moveTracker) {
        this.board = board;
        this.initialCastlingEligibility = initialCastlingEligibility;
        this.enPassantTarget = enPassantTarget;
        this.moveTracker = moveTracker;
    }

    public GameState(
            Board board,
            InitialCastlingEligibility initialCastlingEligibility,
            EnPassantTarget enPassantTarget,
            MoveTracker moveTracker,
            List<Move> moves) {
        this(board, initialCastlingEligibility, enPassantTarget, moveTracker);
        this.makeMoves(moves);
    }

    public GameState(GameState other, Move move) {
        this.board = other.board.deepCopy();
        this.initialCastlingEligibility = other.initialCastlingEligibility.deepCopy();
        this.enPassantTarget = other.enPassantTarget.deepCopy();
        this.moveTracker = other.moveTracker.deepCopy();

        this.makeMove(move);
    }

    public Board getBoard() {
        return this.board.deepCopy();
    }

    public boolean isWhiteToMove() {
        return this.moveTracker.isWhiteToMove();
    }

    public boolean whiteIsEligibleToCastleKingside() {
        return this.initialCastlingEligibility.isWhiteKingsideEligible()
                && !this.moveTracker.hasMovedWhiteKing()
                && !this.moveTracker.hasMovedWhiteKingsideRook();
    }

    public boolean whiteIsEligibleToCastleQueenside() {
        return this.initialCastlingEligibility.isWhiteQueensideEligible()
                && !this.moveTracker.hasMovedWhiteKing()
                && !this.moveTracker.hasMovedWhiteQueensideRook();
    }

    public boolean blackIsEligibleToCastleKingside() {
        return this.initialCastlingEligibility.isBlackKingsideEligible()
                && !this.moveTracker.hasMovedBlackKing()
                && !this.moveTracker.hasMovedBlackKingsideRook();
    }

    public boolean blackIsEligibleToCastleQueenside() {
        return this.initialCastlingEligibility.isBlackQueensideEligible()
                && !this.moveTracker.hasMovedBlackKing()
                && !this.moveTracker.hasMovedBlackQueensideRook();
    }

    public Square getEnPassantTarget() {
        return this.enPassantTarget.getEnPassantTargetSquare();
    }

    public int getHalfMoveClock() {
        return this.moveTracker.getHalfMoveClock();
    }

    public int getFullMoveNumber() {
        return this.moveTracker.getFullMoveNumber();
    }

    private void makeMoves(List<Move> movesSoFar) {
        for(Move move : movesSoFar) {
            this.makeMove(move);
        }
    }

    private void makeMove(Move move) {
        this.moveTracker.trackMove(this.board, move);
        this.enPassantTarget.update(this.board, move);

        this.board.makeMove(move);
    }

    public String toFenString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.board.toFenPiecePlacementString());
        sb.append(" ").append(this.isWhiteToMove() ? "w" : "b");

        String castlingEligibility = "";
        if(this.whiteIsEligibleToCastleKingside()) {
            castlingEligibility += "K";
        }
        if(this.whiteIsEligibleToCastleQueenside()) {
            castlingEligibility += "Q";
        }
        if(this.blackIsEligibleToCastleKingside()) {
            castlingEligibility += "k";
        }
        if(this.blackIsEligibleToCastleKingside()) {
            castlingEligibility += "q";
        }
        if(castlingEligibility.length() == 0) {
            castlingEligibility = "-";
        }
        sb.append(" ").append(castlingEligibility);

        if(null == this.getEnPassantTarget()) {
            sb.append(" ").append("-");
        }
        else {
            sb.append(" ").append(this.getEnPassantTarget().toChessSquareString());
        }

        sb.append(" ").append(this.getHalfMoveClock());
        sb.append(" ").append(this.getFullMoveNumber());

        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.board.toString());

        char K = this.whiteIsEligibleToCastleKingside() ? 'y' : 'n';
        char Q = this.whiteIsEligibleToCastleQueenside() ? 'y' : 'n';
        char k = this.blackIsEligibleToCastleKingside() ? 'y' : 'n';
        char q = this.blackIsEligibleToCastleQueenside() ? 'y' : 'n';

        sb.append("\n\n");
        sb.append("\n").append("castling   |---------------|");
        sb.append("\n").append("eligibility| white | black |");
        sb.append("\n").append("|----------|---------------|");
        sb.append("\n").append("|kingside  |   " + K + "   |   " + k + "   |");
        sb.append("\n").append("|queenside |   " + Q + "   |   " + q + "   |");
        sb.append("\n").append("|--------------------------|");

        sb.append("\n\n").append("white to move: ").append(this.isWhiteToMove());

        sb.append("\n\n").append("en passant target: ");
        sb.append((null == this.getEnPassantTarget()) ?
                "-" : this.getEnPassantTarget().toChessSquareString());

        sb.append("\n\n").append("half move clock: ").append(this.getHalfMoveClock());
        sb.append("\n").append("full move number: ").append(this.getFullMoveNumber());

        return sb.toString();
    }

    public GameState deepCopy() {
        return new GameState(
                this.board.deepCopy(),
                this.initialCastlingEligibility.deepCopy(),
                this.enPassantTarget.deepCopy(),
                this.moveTracker.deepCopy());
    }

}
