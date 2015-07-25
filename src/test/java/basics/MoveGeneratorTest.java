
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

    @Test
    public void getLegalMoves_pawnCanPromoteToFourDifferentPieces() throws UnknownCommandException {
        String positionCommand = "position fen "
                + "8/6P1/8/8/8/2k5/1r5r/K7 w - - 0 1";

        GameState gameState = Mapper.toGameState(positionCommand);
        Set<Move> legalMoves = MoveGenerator.getLegalMoves(gameState);

        Move queenPromotion = Mapper.toMove("g7g8Q");
        Move rookPromotion = Mapper.toMove("g7g8R");
        Move bishopPromotion = Mapper.toMove("g7g8B");
        Move knightPromotion = Mapper.toMove("g7g8N");

        assertTrue(legalMoves.contains(queenPromotion));
        assertTrue(legalMoves.contains(rookPromotion));
        assertTrue(legalMoves.contains(bishopPromotion));
        assertTrue(legalMoves.contains(knightPromotion));

        assertEquals(4, legalMoves.size());
    }

    @Test
    public void getLegalMoves_cannotCastleIfRookHasBeenCaptured() throws UnknownCommandException {
        String positionCommand = "position fen "
                + "rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8"
                + " moves e2c3 f2h1";

        GameState gameState = Mapper.toGameState(positionCommand);
        Set<Move> legalMoves = MoveGenerator.getLegalMoves(gameState);

        Move impossibleCastleMove = Mapper.toMove("e1g1");

        assertFalse(legalMoves.contains(impossibleCastleMove));
    }

    // =============================================================================================
    // "perft"s below here (see: https://chessprogramming.wikispaces.com/Perft)
    // =============================================================================================

    @Test
    public void perft01() throws UnknownCommandException {
        GameState gameState = Mapper.toGameState("position startpos");

        this.checkExpect(gameState, 20, 400, 8902, 197281);
    }

    @Test
    // Position 2 at https://chessprogramming.wikispaces.com/Perft+Results on 2015-07-24
    public void perft02() throws UnknownCommandException {
        GameState gameState = Mapper.toGameState("position fen "
                + "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");

        this.checkExpect(gameState, 48, 2039, 97862);
    }

    @Test
    // Position 3 at https://chessprogramming.wikispaces.com/Perft+Results on 2015-07-24
    public void perft03() throws UnknownCommandException {
        GameState gameState = Mapper.toGameState("position fen "
                + "8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - 0 1");

        this.checkExpect(gameState, 14, 191, 2812, 43238);
    }

    @Test
    // Position 4a at https://chessprogramming.wikispaces.com/Perft+Results on 2015-07-24
    public void perft04a() throws UnknownCommandException {
        GameState gameState = Mapper.toGameState("position fen "
                + "r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");

        this.checkExpect(gameState, 6, 264, 9467);
    }

    @Test
    // Position 4b at https://chessprogramming.wikispaces.com/Perft+Results on 2015-07-24
    public void perft04b() throws UnknownCommandException {
        GameState gameState = Mapper.toGameState("position fen "
                + "r2q1rk1/pP1p2pp/Q4n2/bbp1p3/Np6/1B3NBn/pPPP1PPP/R3K2R b KQ - 0 1");

        this.checkExpect(gameState, 6, 264, 9467);
    }

    @Test
    // Position 5 at https://chessprogramming.wikispaces.com/Perft+Results on 2015-07-24
    public void perft05() throws UnknownCommandException {
        GameState gameState = Mapper.toGameState("position fen "
                + "rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8");

        this.checkExpect(gameState, 44, 1486, 62379);
    }

    @Test
    // Position 6 at https://chessprogramming.wikispaces.com/Perft+Results on 2015-07-24
    public void perft06() throws UnknownCommandException {
        GameState gameState = Mapper.toGameState("position fen "
                + "r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10");

        this.checkExpect(gameState, 46, 2079, 89890);
    }

    // =============================================================================================
    // helper methods below here
    // =============================================================================================

    private void checkExpect(GameState gameState, long... numMovesExpectedAtEachDepth) {
        for(int plyDepth = 1; plyDepth <= numMovesExpectedAtEachDepth.length; plyDepth++) {
            this.checkExpect(gameState, plyDepth, numMovesExpectedAtEachDepth[plyDepth - 1]);
        }
    }

    private void checkExpect(GameState gameState, int plyDepth, long numMovesExpected) {
        long numMovesGenerated = this.getNumMovesGenerated(gameState, plyDepth);

        assertEquals(numMovesExpected, numMovesGenerated);
    }

    private long getNumMovesGenerated(GameState gameState, int plyDepth) {
        long numMovesGenerated = 0;

        Set<Move> currentLegalMoves = MoveGenerator.getLegalMoves(gameState);

        if(plyDepth > 1) {
            GameState updatedGameState;
            int newPlyDepth = plyDepth - 1;

            for(Move currentMove : currentLegalMoves) {
                updatedGameState = new GameState(gameState, currentMove);

                numMovesGenerated += this.getNumMovesGenerated(updatedGameState, newPlyDepth);
            }
        }
        else {
            numMovesGenerated = currentLegalMoves.size();
        }

        return numMovesGenerated;
    }

    // =============================================================================================
    // exploratory methods below here
    // =============================================================================================

    private void divide(GameState gameState, int plyDepth) {
        long nodesSoFar = 0;

        Set<Move> currentLegalMoves = MoveGenerator.getLegalMoves(gameState);

        GameState updatedGameState;
        int newPlyDepth = plyDepth - 1;
        long currentNumNodes;

        for(Move currentLegalMove : currentLegalMoves) {
            updatedGameState = new GameState(gameState, currentLegalMove);
            currentNumNodes = this.getNumMovesGenerated(updatedGameState, newPlyDepth);
            nodesSoFar += currentNumNodes;

            System.out.println(
                    currentLegalMove.toChessMoveString()
                    + " "
                    + currentNumNodes);
        }

        System.out.println("\nnodes summed: " + nodesSoFar);
        System.out.println("nodes found:  " + this.getNumMovesGenerated(gameState, plyDepth));

        System.out.println("moves: " + this.getNumMovesGenerated(gameState, 1));
    }
}
