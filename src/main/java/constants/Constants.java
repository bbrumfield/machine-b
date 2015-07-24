
package constants;

import java.util.HashSet;
import java.util.Set;

public class Constants {

    // board dimensions
    public static final int NUM_ROWS = 8;
    public static final int NUM_COLS = 8;

    // pieces
    public static final char KING_WHITE = 'K';
    public static final char KING_BLACK = 'k';

    public static final char QUEEN_WHITE = 'Q';
    public static final char QUEEN_BLACK = 'q';

    public static final char BISHOP_WHITE = 'B';
    public static final char BISHOP_BLACK = 'b';

    public static final char KNIGHT_WHITE = 'N';
    public static final char KNIGHT_BLACK = 'n';

    public static final char ROOK_WHITE = 'R';
    public static final char ROOK_BLACK = 'r';

    public static final char PAWN_WHITE = 'P';
    public static final char PAWN_BLACK = 'p';

    public static final char EMPTY_PIECE = ' ';

    // collections of pieces
    public static final Set<Character> PIECES_WHITE = new HashSet<Character>();
    static {
        PIECES_WHITE.add(KING_WHITE);
        PIECES_WHITE.add(QUEEN_WHITE);
        PIECES_WHITE.add(BISHOP_WHITE);
        PIECES_WHITE.add(KNIGHT_WHITE);
        PIECES_WHITE.add(ROOK_WHITE);
        PIECES_WHITE.add(PAWN_WHITE);
    }

    public static final Set<Character> PIECES_BLACK = new HashSet<Character>();
    static {
        PIECES_BLACK.add(KING_BLACK);
        PIECES_BLACK.add(QUEEN_BLACK);
        PIECES_BLACK.add(BISHOP_BLACK);
        PIECES_BLACK.add(KNIGHT_BLACK);
        PIECES_BLACK.add(ROOK_BLACK);
        PIECES_BLACK.add(PAWN_BLACK);
    }

    // fen
    public static final String FEN_START_POS =
            "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

}
