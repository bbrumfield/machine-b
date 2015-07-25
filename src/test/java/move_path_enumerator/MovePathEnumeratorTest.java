
package move_path_enumerator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import utils.Mapper;
import basics.GameState;
import exceptions.UnknownCommandException;

public class MovePathEnumeratorTest {

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
        long numMovesGenerated = MovePathEnumerator.perft(gameState, plyDepth);

        assertEquals(numMovesExpected, numMovesGenerated);
    }
}
