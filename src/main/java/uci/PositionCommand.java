
package uci;

import java.util.List;

import utils.Parser;
import exceptions.UnknownCommandException;

public class PositionCommand {

    private String piecePlacement;
    private String activeColor;
    private String castlingAvailability;
    private String enPassantTargetSquare;
    private String halfmoveClock;
    private String fullmoveNumber;

    private List<String> moves;

    public PositionCommand(String positionCommand) throws UnknownCommandException {
        String[] fen = Parser.parseFen(positionCommand).split(" ");

        this.piecePlacement = fen[0];
        this.activeColor = fen[1];
        this.castlingAvailability = fen[2];
        this.enPassantTargetSquare = fen[3];
        this.halfmoveClock = fen[4];
        this.fullmoveNumber = fen[5];

        this.moves = Parser.parseMoves(positionCommand);
    }

    public String getPiecePlacement() {
        return this.piecePlacement;
    }

    public String getActiveColor() {
        return this.activeColor;
    }

    public String getCastlingAvailability() {
        return this.castlingAvailability;
    }

    public String getEnPassantTargetSquare() {
        return this.enPassantTargetSquare;
    }

    public String getHalfmoveClock() {
        return this.halfmoveClock;
    }

    public String getFullmoveNumber() {
        return this.fullmoveNumber;
    }

    public List<String> getMoves() {
        return this.moves;
    }

}
