
package basics;

import static constants.Constants.BLACK_BISHOP;
import static constants.Constants.WHITE_BISHOP;
import static constants.Constants.BLACK_KING;
import static constants.Constants.WHITE_KING;
import static constants.Constants.BLACK_KNIGHT;
import static constants.Constants.WHITE_KNIGHT;
import static constants.Constants.BLACK_PAWN;
import static constants.Constants.WHITE_PAWN;
import static constants.Constants.BLACK_QUEEN;
import static constants.Constants.WHITE_QUEEN;
import static constants.Constants.BLACK_ROOK;
import static constants.Constants.WHITE_ROOK;

import java.util.HashSet;
import java.util.Set;

public class MoveGenerator {

    public static Set<Move> getLegalMoves(GameState state) {
        Set<Move> fundamentalMoves = getFundamentalMoves(state);

        Set<Move> legalMoves = filterOutMovesMakingKingCapturable(state, fundamentalMoves);

        return legalMoves;
    }

    private static Set<Move> getFundamentalMoves(GameState gameState) {
        Board board = gameState.getBoard();
        Set<Move> legalMoves = new HashSet<Move>();
        char piece;

        for(int row = 0; row < board.getNumRows(); ++row) {
            for(int col = 0; col < board.getNumCols(); ++col) {
                piece = board.getPieceAt(row, col);

                if(gameState.isWhiteToMove()) {
                    if(piece == WHITE_PAWN) {
                        legalMoves.addAll(getFundamentalPawnMoves(gameState, row, col, -1));
                    }
                    else if(piece == WHITE_ROOK) {
                        legalMoves.addAll(getFundamentalRookMoves(gameState, row, col));
                    }
                    else if(piece == WHITE_KNIGHT) {
                        legalMoves.addAll(getFundamentalKnightMoves(gameState, row, col));
                    }
                    else if(piece == WHITE_BISHOP) {
                        legalMoves.addAll(getFundamentalBishopMoves(gameState, row, col));
                    }
                    else if(piece == WHITE_QUEEN) {
                        legalMoves.addAll(getFundamentalQueenMoves(gameState, row, col));
                    }
                    else if(piece == WHITE_KING) {
                        legalMoves.addAll(getFundamentalKingMoves(gameState, row, col));
                    }
                }
                else { // black to move
                    if(piece == BLACK_PAWN) {
                        legalMoves.addAll(getFundamentalPawnMoves(gameState, row, col, 1));
                    }
                    else if(piece == BLACK_ROOK) {
                        legalMoves.addAll(getFundamentalRookMoves(gameState, row, col));
                    }
                    else if(piece == BLACK_KNIGHT) {
                        legalMoves.addAll(getFundamentalKnightMoves(gameState, row, col));
                    }
                    else if(piece == BLACK_BISHOP) {
                        legalMoves.addAll(getFundamentalBishopMoves(gameState, row, col));
                    }
                    else if(piece == BLACK_QUEEN) {
                        legalMoves.addAll(getFundamentalQueenMoves(gameState, row, col));
                    }
                    else if(piece == BLACK_KING) {
                        legalMoves.addAll(getFundamentalKingMoves(gameState, row, col));
                    }
                }
            }
        }

        return legalMoves;
    }

    private static Set<Move> getFundamentalPawnMoves(GameState state, int row, int col, int forwardOne) {
        Board board = state.getBoard();
        Set<Move> moves = new HashSet<Move>();

        Move proposedMove;

        // check if first square forward is empty
        proposedMove = new Move(row, col, row + forwardOne, col);
        if(board.targetSquareOfMoveIsWithinBounds(proposedMove)) {
            if(board.targetSquareIsUnoccupied(proposedMove)) {
                moves.add(proposedMove);

                // check if pawn is still on it's original square
                if(((forwardOne == 1) && (row == 1)) || ((forwardOne == -1) && (row == 6))) {
                    proposedMove = new Move(row, col, row + forwardOne + forwardOne, col);
                    // check if second square forward is empty
                    if(board.targetSquareIsUnoccupied(proposedMove)) {
                        moves.add(proposedMove);
                    }
                }
            }
        }

        // capture diagonally if possible, including en passant
        proposedMove = new Move(row, col, row + forwardOne, col - 1);
        if(board.targetSquareOfMoveIsWithinBounds(proposedMove)) {
            if(board.squaresOfMoveAreOccupiedByOpposingPieces(proposedMove)) {
                moves.add(proposedMove);
            }
            else if((state.getEnPassantTarget() != null)
                    && (proposedMove.getTargetRow() == state.getEnPassantTarget().getRow())
                    && (proposedMove.getTargetCol() == state.getEnPassantTarget().getCol())) {
                moves.add(proposedMove);
            }
        }
        proposedMove = new Move(row, col, row + forwardOne, col + 1);
        if(board.targetSquareOfMoveIsWithinBounds(proposedMove)) {
            if(board.squaresOfMoveAreOccupiedByOpposingPieces(proposedMove)) {
                moves.add(proposedMove);
            }
            else if((state.getEnPassantTarget() != null)
                    && (proposedMove.getTargetRow() == state.getEnPassantTarget().getRow())
                    && (proposedMove.getTargetCol() == state.getEnPassantTarget().getCol())) {
                moves.add(proposedMove);
            }
        }

        // expand on any pawn promotions
        // (could be promoted to rook, knight, bishop, or queen of same color)
        Set<Move> promotions = getMovesReadyForPromotions(board, moves);
        moves.removeAll(promotions);
        moves.addAll(expandPromotionPossibilities(promotions));

        return moves;
    }

    private static Set<Move> getMovesReadyForPromotions(Board board, Set<Move> moves) {
        Set<Move> readyForPromotions = new HashSet<Move>();

        for(Move move : moves) {
            if(((move.getTargetRow() == 0) && (board.pieceAtOriginIs(move, WHITE_PAWN)))
                    || ((move.getTargetRow() == 7) && (board.pieceAtOriginIs(move, BLACK_PAWN)))) {

                readyForPromotions.add(move);
            }
        }

        return readyForPromotions;
    }

    private static Set<Move> expandPromotionPossibilities(Set<Move> promotions) {
        Set<Move> allPossibilities = new HashSet<Move>();

        String pieces = "RNBQ"; // DO: use the constants in Constants
        String whitePromotionPieces = pieces.toUpperCase();
        String blackPromotionPieces = pieces.toLowerCase();

        for(Move promotion : promotions) {
            for(int i = 0; i < pieces.length(); ++i) {
                char promotedPiece;

                if(promotion.getTargetRow() == 0) /* is white promotion, promote to white piece */{
                    promotedPiece = whitePromotionPieces.charAt(i);
                }
                else /* is black promotion, promote to black piece */{
                    promotedPiece = blackPromotionPieces.charAt(i);
                }

                Move copy = promotion.deepCopy();
                copy.promote(promotedPiece);

                allPossibilities.add(copy);
            }
        }

        return allPossibilities;
    }

    private static Set<Move> getFundamentalRookMoves(GameState state, int row, int col) {
        Set<Move> moves = new HashSet<Move>();

        moves.addAll(getLongDistanceMovesInOneDirection(state, row, col, 1, 0));
        moves.addAll(getLongDistanceMovesInOneDirection(state, row, col, -1, 0));
        moves.addAll(getLongDistanceMovesInOneDirection(state, row, col, 0, 1));
        moves.addAll(getLongDistanceMovesInOneDirection(state, row, col, 0, -1));

        return moves;
    }

    private static Set<Move> getFundamentalBishopMoves(GameState state, int row, int col) {
        Set<Move> moves = new HashSet<Move>();

        moves.addAll(getLongDistanceMovesInOneDirection(state, row, col, 1, 1));
        moves.addAll(getLongDistanceMovesInOneDirection(state, row, col, -1, 1));
        moves.addAll(getLongDistanceMovesInOneDirection(state, row, col, 1, -1));
        moves.addAll(getLongDistanceMovesInOneDirection(state, row, col, -1, -1));

        return moves;
    }

    private static Set<Move> getFundamentalQueenMoves(GameState state, int row, int col) {
        Set<Move> moves = new HashSet<Move>();

        moves.addAll(getFundamentalRookMoves(state, row, col));
        moves.addAll(getFundamentalBishopMoves(state, row, col));

        return moves;
    }

    private static Set<Move> getLongDistanceMovesInOneDirection(
            GameState state, int startRow, int startCol, int rowDirection, int colDirection) {
        Board board = state.getBoard();

        Set<Move> moves = new HashSet<Move>();

        int currentRow = startRow + rowDirection;
        int currentCol = startCol + colDirection;

        Move proposedMove = new Move(startRow, startCol, currentRow, currentCol);

        while(board.targetSquareOfMoveIsWithinBounds(proposedMove)) {
            if(board.targetSquareIsUnoccupied(proposedMove)) {
                moves.add(proposedMove);
            }
            else if(board.squaresOfMoveAreOccupiedByOpposingPieces(proposedMove)) {
                moves.add(proposedMove);
                break;
            }
            else {
                break;
            }

            currentRow += rowDirection;
            currentCol += colDirection;

            proposedMove = new Move(startRow, startCol, currentRow, currentCol);
        }

        return moves;
    }

    private static Set<Move> getFundamentalKnightMoves(GameState state, int row, int col) {
        Board board = state.getBoard();

        Set<Move> moves = new HashSet<Move>();

        Move proposedMove;

        for(int currentRow = 0; currentRow < 8; ++currentRow) {
            for(int currentCol = 0; currentCol < 8; ++currentCol) {
                if(isKnightsMoveDistanceAway(row, col, currentRow, currentCol)) {
                    proposedMove = new Move(row, col, currentRow, currentCol);
                    if(board.targetSquareIsUnoccupied(proposedMove)
                            || board.squaresOfMoveAreOccupiedByOpposingPieces(proposedMove)) {
                        moves.add(proposedMove);
                    }
                }
            }
        }

        return moves;
    }

    private static boolean isKnightsMoveDistanceAway(int fromRow, int fromCol, int toRow, int toCol) {
        int rowDelta = Math.abs(fromRow - toRow);
        int colDelta = Math.abs(fromCol - toCol);

        return ((rowDelta == 2) && (colDelta == 1)) || ((rowDelta == 1) && (colDelta == 2));
    }

    private static Set<Move> getFundamentalKingMoves(GameState state, int row, int col) {
        Board board = state.getBoard();

        Set<Move> moves = new HashSet<Move>();

        Move proposedMove;

        for(int currentRow = row - 1; currentRow <= (row + 1); ++currentRow) {
            for(int currentCol = col - 1; currentCol <= (col + 1); ++currentCol) {
                if((currentRow != row) || (currentCol != col)) {
                    proposedMove = new Move(row, col, currentRow, currentCol);
                    if(board.targetSquareOfMoveIsWithinBounds(proposedMove)) {
                        if(board.targetSquareIsUnoccupied(proposedMove)
                                || board.squaresOfMoveAreOccupiedByOpposingPieces(proposedMove)) {
                            moves.add(proposedMove);
                        }
                    }
                }
            }
        }

        // add castling possibilities (if getting out of, passing through, or ending up in check,
        // then the move will be filtered out at a later stage)
        if(state.isWhiteToMove()) {
            if(state.whiteIsEligibleToCastleKingside()) {
                if(board.isClearToCastleKingsideForWhite()) {
                    proposedMove = new Move(row, col, row, col + 2);
                    moves.add(proposedMove);
                }
            }
            if(state.whiteIsEligibleToCastleQueenside()) {
                if(board.isClearToCastleQueensideForWhite()) {
                    proposedMove = new Move(row, col, row, col - 2);
                    moves.add(proposedMove);
                }
            }
        }
        else { // is black to move
            if(state.blackIsEligibleToCastleKingside()) {
                if(board.isClearToCastleKingsideForBlack()) {
                    proposedMove = new Move(row, col, row, col + 2);
                    moves.add(proposedMove);
                }
            }
            if(state.blackIsEligibleToCastleQueenside()) {
                if(board.isClearToCastleQueensideForBlack()) {
                    proposedMove = new Move(row, col, row, col - 2);
                    moves.add(proposedMove);
                }
            }
        }

        return moves;
    }

    private static Set<Move> filterOutMovesMakingKingCapturable(GameState state, Set<Move> moves) {
        Set<Move> filteredMoves = new HashSet<Move>();

        for(Move move : moves) {
            if(!makesKingCapturableOnNextMove(state, move) && !isIllegalCastlingMove(state, move)) {
                filteredMoves.add(move);
            }
        }

        return filteredMoves;
    }

    private static boolean makesKingCapturableOnNextMove(GameState state, Move move) {
        GameState updatedState = new GameState(state, move);
        Board updatedBoard = updatedState.getBoard();

        Set<Move> moves = getFundamentalMoves(updatedState);

        for(Move current : moves) {
            if(canCaptureKing(updatedBoard, current)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isIllegalCastlingMove(GameState gameState, Move move) {
        // if the move is an attempt to castle
        if(gameState.getBoard().isHorizontalTwoSquareKingMove(move)) {
            // check all three squares the king moves through
            // 1) a king cannot castle out of check (check the origin square)
            Move originToOrigin = new Move(
                    move.getOriginRow(), move.getOriginCol(),
                    move.getOriginRow(), move.getOriginCol());
            // 2) a king cannot move through check to castle (check the middle square)
            Move originToMiddle = new Move(
                    move.getOriginRow(), move.getOriginCol(),
                    move.getOriginRow(), (move.getOriginCol() + move.getTargetCol()) / 2);
            // 3) a king cannot end in check due to castling (check target square)
            Move originToTarget = move.deepCopy();

            if(makesKingCapturableOnNextMove(gameState, originToOrigin)
                    || makesKingCapturableOnNextMove(gameState, originToMiddle)
                    || makesKingCapturableOnNextMove(gameState, originToTarget)) {
                return true;
            }
        }

        return false;
    }

    private static boolean canCaptureKing(Board board, Move move) {
        boolean targetSquareIsOccupiedByKing = board.pieceAtTargetIsIgnoreColor(move, WHITE_KING);

        return targetSquareIsOccupiedByKing && board.squaresOfMoveAreOccupiedByOpposingPieces(move);
    }
}
