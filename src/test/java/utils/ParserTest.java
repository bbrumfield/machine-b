
package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import constants.Constants;
import exceptions.UnknownCommandException;

public class ParserTest {

    @Test
    public void parseFen_startPosition_withoutMoves() throws UnknownCommandException {
        String positionCommand = "position startpos";

        String actual = Parser.parseFen(positionCommand);
        String expect = Constants.FEN_START_POS;

        assertEquals(expect, actual);
    }

    @Test
    public void parseFen_startPosition_withMoves() throws UnknownCommandException {
        String positionCommand = "position startpos moves e2e4 e7e5";

        String actual = Parser.parseFen(positionCommand);
        String expect = Constants.FEN_START_POS;

        assertEquals(expect, actual);
    }

    @Test
    public void parseFen_customPosition_withoutMoves() throws UnknownCommandException {
        String positionCommand =
                "position fen r1bq1rk1/p1p2ppp/2p5/3pP3/1b4n1/2NB4/PPPB1PPP/R2QK2R b KQ - 0 1";

        String actual = Parser.parseFen(positionCommand);
        String expect = "r1bq1rk1/p1p2ppp/2p5/3pP3/1b4n1/2NB4/PPPB1PPP/R2QK2R b KQ - 0 1";

        assertEquals(expect, actual);
    }

    @Test
    public void parseFen_customPosition_withMoves() throws UnknownCommandException {
        String positionCommand =
                "position fen r1bq1rk1/p1p2ppp/2p5/3pP3/1b4n1/2NB4/PPPB1PPP/R2QK2R b KQ - 0 1 moves g4e5";

        String actual = Parser.parseFen(positionCommand);
        String expect = "r1bq1rk1/p1p2ppp/2p5/3pP3/1b4n1/2NB4/PPPB1PPP/R2QK2R b KQ - 0 1";

        assertEquals(expect, actual);
    }

    @Test(expected = UnknownCommandException.class)
    public void parseFen_shouldThrowException_whenPassedInvalidCommand()
            throws UnknownCommandException {
        Parser.parseFen("position invalid");
    }

    @Test
    public void parseMoves_shouldReturnEmptyList_whenCommandContainsZeroMoves() {
        String positionCommand =
                "position fen r1bq1rk1/p1p2ppp/2p5/3pP3/1b4n1/2NB4/PPPB1PPP/R2QK2R b KQ - 0 1";

        int expectedNumberOfMoves = 0;
        int actualNumberOfMoves = Parser.parseMoves(positionCommand).size();

        assertEquals(expectedNumberOfMoves, actualNumberOfMoves);
    }

    @Test
    public void parseMoves_happyPath_oneMove() {
        String positionCommand = "position startpos moves e2e4";

        List<String> moves = Parser.parseMoves(positionCommand);

        int expectedNumberOfMoves = 1;
        int actualNumberOfMoves = moves.size();

        assertEquals(expectedNumberOfMoves, actualNumberOfMoves);
        assertTrue(moves.contains("e2e4"));
    }

    @Test
    public void parseMoves_positionCommandContainsTwoMoves_includingOnePromotion() {
        String positionCommand = "position startpos moves a7a8Q b8c6";

        List<String> moves = Parser.parseMoves(positionCommand);

        int expectedNumberOfMoves = 2;
        int actualNumberOfMoves = moves.size();

        assertEquals(expectedNumberOfMoves, actualNumberOfMoves);
        assertTrue(moves.contains("a7a8Q"));
        assertTrue(moves.contains("b8c6"));
    }

}
