
package constants;

import java.util.HashSet;
import java.util.Set;

public class Constants {

    // board dimensions
    public static final int NUM_ROWS = 8;
    public static final int NUM_COLS = 8;

    // pieces
    public static final char WHITE_KING = 'K';
    public static final char BLACK_KING = 'k';

    public static final char WHITE_QUEEN = 'Q';
    public static final char BLACK_QUEEN = 'q';

    public static final char WHITE_BISHOP = 'B';
    public static final char BLACK_BISHOP = 'b';

    public static final char WHITE_KNIGHT = 'N';
    public static final char BLACK_KNIGHT = 'n';

    public static final char WHITE_ROOK = 'R';
    public static final char BLACK_ROOK = 'r';

    public static final char WHITE_PAWN = 'P';
    public static final char BLACK_PAWN = 'p';

    public static final char EMPTY_PIECE = ' ';

    // collections of pieces
    public static final Set<Character> WHITE_PIECES = new HashSet<Character>();
    static {
        WHITE_PIECES.add(WHITE_KING);
        WHITE_PIECES.add(WHITE_QUEEN);
        WHITE_PIECES.add(WHITE_BISHOP);
        WHITE_PIECES.add(WHITE_KNIGHT);
        WHITE_PIECES.add(WHITE_ROOK);
        WHITE_PIECES.add(WHITE_PAWN);
    }

    public static final Set<Character> BLACK_PIECES = new HashSet<Character>();
    static {
        BLACK_PIECES.add(BLACK_KING);
        BLACK_PIECES.add(BLACK_QUEEN);
        BLACK_PIECES.add(BLACK_BISHOP);
        BLACK_PIECES.add(BLACK_KNIGHT);
        BLACK_PIECES.add(BLACK_ROOK);
        BLACK_PIECES.add(BLACK_PAWN);
    }

    // fen
    public static final String START_POSITION_FEN =
            "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

}
