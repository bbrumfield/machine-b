
package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import basics.GameState;
import basics.Square;
import exceptions.UnknownCommandException;

public class MapperTest {

    @Test
    public void toGameState_fromStartPosition() throws UnknownCommandException {
        String positionCommand = "position startpos";

        GameState gameState = Mapper.toGameState(positionCommand);

        String expectedFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        String actualFen = gameState.toFenString();
        assertTrue(actualFen.equals(expectedFen));

        assertTrue(gameState.isWhiteToMove());

        assertTrue(gameState.whiteIsEligibleToCastleKingside());
        assertTrue(gameState.whiteIsEligibleToCastleQueenside());
        assertTrue(gameState.blackIsEligibleToCastleKingside());
        assertTrue(gameState.blackIsEligibleToCastleQueenside());

        Square enPassantTarget = gameState.getEnPassantTarget();
        assertTrue(null == enPassantTarget);

        assertTrue(0 == gameState.getHalfMoveClock());
        assertTrue(1 == gameState.getFullMoveNumber());
    }

    @Test
    public void toGameState_fromCustomPosition() throws UnknownCommandException {
        String positionCommand = "position fen "
                + "r1bq1rk1/p1pp1ppp/2p5/4P3/1b3Pn1/2NB4/PPPB2PP/R2QK2R b KQ f3 0 8"
                + " moves d7d5";

        GameState gameState = Mapper.toGameState(positionCommand);

        String expectedFen = "r1bq1rk1/p1p2ppp/2p5/3pP3/1b3Pn1/2NB4/PPPB2PP/R2QK2R w KQ d6 0 9";
        String actualFen = gameState.toFenString();
        assertEquals(expectedFen, actualFen);

        assertTrue(gameState.isWhiteToMove());

        assertTrue(gameState.whiteIsEligibleToCastleKingside());
        assertTrue(gameState.whiteIsEligibleToCastleQueenside());
        assertTrue(!gameState.blackIsEligibleToCastleKingside());
        assertTrue(!gameState.blackIsEligibleToCastleQueenside());

        Square enPassantTarget = gameState.getEnPassantTarget();
        assertTrue(null != enPassantTarget);
        assertTrue(enPassantTarget.toChessSquareString().equals("d6"));

        assertTrue(0 == gameState.getHalfMoveClock());
        assertTrue(9 == gameState.getFullMoveNumber());
    }

    @Test
    public void toGameState_fromCustomPosition_withPromotionMove() throws UnknownCommandException {
        String positionCommand = "position fen "
                + "8/4P3/k7/8/8/K7/8/8 b - - 0 54"
                + " moves a6b7 e7e8Q";

        GameState gameState = Mapper.toGameState(positionCommand);

        String expectedFen = "4Q3/1k6/8/8/8/K7/8/8 b - - 0 55";
        String actualFen = gameState.toFenString();
        assertEquals(expectedFen, actualFen);

        assertTrue(!gameState.isWhiteToMove());

        assertTrue(!gameState.whiteIsEligibleToCastleKingside());
        assertTrue(!gameState.whiteIsEligibleToCastleQueenside());
        assertTrue(!gameState.blackIsEligibleToCastleKingside());
        assertTrue(!gameState.blackIsEligibleToCastleQueenside());

        Square enPassantTarget = gameState.getEnPassantTarget();
        assertTrue(null == enPassantTarget);

        assertTrue(0 == gameState.getHalfMoveClock());
        assertTrue(55 == gameState.getFullMoveNumber());
    }

    @Test
    public void toGameState_fromStartPosition_withKingsideCastleMoves()
            throws UnknownCommandException {
        String positionCommand = "position startpos "
                + " moves e2e3 e7e5 f1e2 f8e7 g1f3 g8f6 e1g1 e8g8";

        GameState gameState = Mapper.toGameState(positionCommand);

        String expectedFen = "rnbq1rk1/ppppbppp/5n2/4p3/8/4PN2/PPPPBPPP/RNBQ1RK1 w - - 6 5";
        String actualFen = gameState.toFenString();
        assertEquals(expectedFen, actualFen);

        assertTrue(gameState.isWhiteToMove());

        assertTrue(!gameState.whiteIsEligibleToCastleKingside());
        assertTrue(!gameState.whiteIsEligibleToCastleQueenside());
        assertTrue(!gameState.blackIsEligibleToCastleKingside());
        assertTrue(!gameState.blackIsEligibleToCastleQueenside());

        Square enPassantTarget = gameState.getEnPassantTarget();
        assertTrue(null == enPassantTarget);

        assertTrue(6 == gameState.getHalfMoveClock());
        assertTrue(5 == gameState.getFullMoveNumber());
    }

    @Test
    public void toGameState_fromStartPosition_withQueensideCastleMoves()
            throws UnknownCommandException {
        String positionCommand = "position startpos "
                + " moves d2d4 d7d5 c1e3 c8e6 d1d2 d8d7 b1c3 b8c6 e1c1 e8c8";

        GameState gameState = Mapper.toGameState(positionCommand);

        String expectedFen = "2kr1bnr/pppqpppp/2n1b3/3p4/3P4/2N1B3/PPPQPPPP/2KR1BNR w - - 8 6";
        String actualFen = gameState.toFenString();
        assertEquals(expectedFen, actualFen);

        assertTrue(gameState.isWhiteToMove());

        assertTrue(!gameState.whiteIsEligibleToCastleKingside());
        assertTrue(!gameState.whiteIsEligibleToCastleQueenside());
        assertTrue(!gameState.blackIsEligibleToCastleKingside());
        assertTrue(!gameState.blackIsEligibleToCastleQueenside());

        Square enPassantTarget = gameState.getEnPassantTarget();
        assertTrue(null == enPassantTarget);

        assertTrue(8 == gameState.getHalfMoveClock());
        assertTrue(6 == gameState.getFullMoveNumber());
    }
}
