
package utils;

import java.util.ArrayList;
import java.util.List;

import uci.PositionCommand;
import basics.Board;
import basics.EnPassantTarget;
import basics.GameState;
import basics.InitialCastlingEligibility;
import basics.Move;
import basics.MoveTracker;
import exceptions.UnknownCommandException;

public class Mapper {

    /**
     * Converts a valid UCI position command to a GameState object.
     *
     * @throws UnknownCommandException
     *             If the given string is a malformed UCI position command.
     */
    public static GameState toGameState(String positionCommand) throws UnknownCommandException {
        PositionCommand pCommand = new PositionCommand(positionCommand);

        Board board = toBoard(pCommand.getPiecePlacement());
        MoveTracker moveTracker = toMoveTracker(
                pCommand.getActiveColor(),
                pCommand.getHalfmoveClock(),
                pCommand.getFullmoveNumber());

        InitialCastlingEligibility initialCastlingEligibility =
                toInitialCastlingEligibility(pCommand.getCastlingAvailability());

        EnPassantTarget enPassantTarget = toEnPassantTarget(
                pCommand.getEnPassantTargetSquare());

        List<Move> moves = toMoveList(pCommand.getMoves());

        return new GameState(
                board, initialCastlingEligibility, enPassantTarget, moveTracker, moves);
    }

    private static Board toBoard(String fenPiecePlacement) {
        char[][] position = new char[8][8];

        char currentChar;
        char currentSquare = 0;

        for(int i = 0; (i < fenPiecePlacement.length()) && (currentSquare < 64); ++i) {
            currentChar = fenPiecePlacement.charAt(i);
            if(currentChar == '/') {
                continue;
            }
            else if(Character.isDigit(currentChar)) {
                int numEmptySquares = currentChar - 48; // convert from char to correct int

                for(int count = 0; count < numEmptySquares; ++count) {
                    position[currentSquare / 8][currentSquare % 8] = ' ';
                    ++currentSquare;
                }
            }
            else {
                position[currentSquare / 8][currentSquare % 8] = currentChar;
                ++currentSquare;
            }
        }

        return new Board(position);
    }

    private static MoveTracker toMoveTracker(
            String fenActiveColor, String fenHalfmoveClock, String fenFullmoveNumber) {
        boolean whiteToMove = fenActiveColor.equals("w");
        int halfMoveClock = Integer.parseInt(fenHalfmoveClock);
        int fullMoveNumber = Integer.parseInt(fenFullmoveNumber);

        return new MoveTracker(whiteToMove, halfMoveClock, fullMoveNumber);
    }

    private static InitialCastlingEligibility toInitialCastlingEligibility(
            String fenCastlingAvailability) {
        boolean whiteKingside = fenCastlingAvailability.contains("K");
        boolean whiteQueenside = fenCastlingAvailability.contains("Q");
        boolean blackKingside = fenCastlingAvailability.contains("k");
        boolean blackQueenside = fenCastlingAvailability.contains("q");

        return new InitialCastlingEligibility(
                whiteKingside, whiteQueenside, blackKingside, blackQueenside);
    }

    private static EnPassantTarget toEnPassantTarget(String fenEnPassantTarget) {
        EnPassantTarget enPassantTarget = new EnPassantTarget();

        if(!fenEnPassantTarget.equals("-")) {
            int row = Utils.rankToRow(fenEnPassantTarget.charAt(1) - 48);
            int col = Utils.fileToCol(fenEnPassantTarget.charAt(0));

            enPassantTarget.update(row, col);
        }

        return enPassantTarget;
    }

    private static List<Move> toMoveList(List<String> moves) {
        List<Move> moveList = new ArrayList<Move>();

        for(int i = 0; i < moves.size(); ++i) {
            moveList.add(toMove(moves.get(i)));
        }

        return moveList;
    }

    /**
     * Converts a String to a {@link Move} object.
     *
     * @param chessMove
     *            Must represent a valid chess move in either form "e2e4" or "a2a1q". 'Valid' in
     *            this context simply means that the squares indicated should exist within the
     *            bounds of a standard chess board and any promoted piece must be for a queen, rook,
     *            bishop, or knight.
     */
    public static Move toMove(String chessMove) {
        int fromRank = chessMove.charAt(1) - 48;
        char fromFile = chessMove.charAt(0);
        int toRank = chessMove.charAt(3) - 48;
        char toFile = chessMove.charAt(2);

        int fromRow = Utils.rankToRow(fromRank);
        int fromCol = Utils.fileToCol(fromFile);
        int toRow = Utils.rankToRow(toRank);
        int toCol = Utils.fileToCol(toFile);

        if(5 == chessMove.length()) {
            char promotedPiece = chessMove.charAt(4);
            return new Move(fromRow, fromCol, toRow, toCol, promotedPiece);
        }
        else {
            return new Move(fromRow, fromCol, toRow, toCol);
        }
    }
}
