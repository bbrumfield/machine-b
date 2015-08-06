
package utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import constants.Constants;
import exceptions.UnknownCommandException;

public class Parser {

    public static String parseFen(String positionCommand) throws UnknownCommandException {
        String fen;

        if(positionCommand.contains("startpos")) {
            fen = Constants.START_POSITION_FEN;
        }
        else if(positionCommand.contains("fen")) {
            int beginIndex = positionCommand.lastIndexOf("fen") + 4;
            fen = positionCommand.substring(beginIndex);
            if(fen.contains(" moves")) {
                fen = fen.substring(0, fen.indexOf(" moves"));
            }
        }
        else {
            throw new UnknownCommandException("Unknown 'position' command: " + positionCommand);
        }

        return fen;
    }

    public static List<String> parseMoves(String positionCommand) {
        List<String> moves;

        if(positionCommand.contains("moves")) {
            int beginIndex = positionCommand.lastIndexOf("moves") + 6;
            String movesString = positionCommand.substring(beginIndex);
            moves = Arrays.asList(movesString.split(" "));
        }
        else {
            moves = Collections.emptyList();
        }

        return moves;
    }

    public static int parseDividePlyDepth(String divideCommand) throws UnknownCommandException {
        return parsePlyDepth(divideCommand, "divide");
    }

    public static int parsePerftPlyDepth(String perftCommand) throws UnknownCommandException {
        return parsePlyDepth(perftCommand, "perft");
    }

    private static int parsePlyDepth(String command, String expectedFirstToken)
            throws UnknownCommandException {
        String[] tokens = command.split(" ");

        if((tokens.length < 2) || !tokens[0].equals(expectedFirstToken)) {
            throw new UnknownCommandException("Unkown command: " + command);
        }

        try {
            return Integer.parseInt(tokens[1]);
        }
        catch(NumberFormatException e) {
            throw new UnknownCommandException(tokens[1] + " is not a valid ply depth.");
        }
    }

}
