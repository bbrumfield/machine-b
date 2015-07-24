
package basics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import utils.Mapper;
import exceptions.UnknownCommandException;

public class MoveGeneratorTest {

    @Test
    public void getLegalMoves_allMajorPiecesCanMove() throws UnknownCommandException {
        // DO: write test
    }

    @Test
    public void getLegalMoves_withEnPassantOpportunity() throws UnknownCommandException {
        String positionCommand = "position fen "
                + "8/8/8/p1K1k3/1pP5/1P6/8/8 b - c3 0 47";

        GameState gameState = Mapper.toGameState(positionCommand);
        Set<Move> legalMoves = MoveGenerator.getLegalMoves(gameState);

        assertEquals(7, legalMoves.size());

        Move enPassantCapture = Mapper.toMove("b4c3");
        assertTrue(legalMoves.contains(enPassantCapture));
    }

    @Test
    public void getLegalMoves_withCastlingOpportunity() throws UnknownCommandException {
        String positionCommand = "position fen "
                + "4k2r/5pp1/8/8/6Pp/8/5P1P/4K2R b Kk - 0 7";

        GameState gameState = Mapper.toGameState(positionCommand);
        Set<Move> legalMoves = MoveGenerator.getLegalMoves(gameState);

        assertEquals(15, legalMoves.size());

        Move castleKingside = Mapper.toMove("e8g8");
        assertTrue(legalMoves.contains(castleKingside));
    }

    @Test
    public void getLegalMoves_ensureCannotCastleToGetOutOfCheck() throws UnknownCommandException {
        String positionCommand = "position fen "
                + "r3k1r1/p1qp1p1p/4R1pB/P6R/1p2P3/1Pb1P3/4K1Q1/6Nn b q - 0 41";

        GameState gameState = Mapper.toGameState(positionCommand);
        Set<Move> legalMoves = MoveGenerator.getLegalMoves(gameState);

        Move castleQueenside = Mapper.toMove("e8c8");
        assertFalse(legalMoves.contains(castleQueenside));
    }

    @Test
    public void getLegalMoves_ensureCannotCastleIntoCheck() throws UnknownCommandException {
        String positionCommand = "position fen "
                + "r3k3/8/8/8/8/8/8/2Q1K3 b q - 0 1";

        GameState gameState = Mapper.toGameState(positionCommand);
        Set<Move> legalMoves = MoveGenerator.getLegalMoves(gameState);

        Move castleQueenside = Mapper.toMove("e8c8");
        assertFalse(legalMoves.contains(castleQueenside));
    }

    @Test
    public void getLegalMoves_ensureCannotCastleIfPassingThroughCheck()
            throws UnknownCommandException {
        String positionCommand = "position fen "
                + "r3k3/8/8/8/8/8/8/3QK3 b q - 0 1";

        GameState gameState = Mapper.toGameState(positionCommand);
        Set<Move> legalMoves = MoveGenerator.getLegalMoves(gameState);

        Move castleQueenside = Mapper.toMove("e8c8");
        assertFalse(legalMoves.contains(castleQueenside));
    }
}
