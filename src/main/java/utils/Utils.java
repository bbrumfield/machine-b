
package utils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import basics.Move;

public class Utils {

    public static int rankToRow(int rank) {
        return 8 - rank;
    }

    public static int rowToRank(int row) {
        return 8 - row;
    }

    public static int fileToCol(char file) {
        return file - 97;
    }

    public static char colToFile(int coordinate) {
        return (char) (coordinate + 97);
    }

    public static boolean isWithinBounds(int value, int lowerInclusive, int upperExclusive) {
        return (lowerInclusive <= value) && (value < upperExclusive);
    }

    public static char[][] deepCopy(char[][] charsToCopy) {
        char[][] copy = new char[charsToCopy.length][];

        for(int i = 0; i < charsToCopy.length; ++i) {
            copy[i] = new char[charsToCopy[i].length];
            for(int j = 0; j < charsToCopy[i].length; ++j) {
                copy[i][j] = charsToCopy[i][j];
            }
        }

        return copy;
    }

    public static List<Move> deepCopy(List<Move> movesToCopy) {
        List<Move> copy = new ArrayList<Move>();

        for(Move move : movesToCopy) {
            copy.add(move.deepCopy());
        }

        return copy;
    }

    public static String format(long number) {
        return NumberFormat.getInstance().format(number);
    }

}
