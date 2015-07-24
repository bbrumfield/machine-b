
package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import basics.Move;

public class UtilsTest {

    @Test
    public void rankToRow_oneToSeven() {
        assertEquals(7, Utils.rankToRow(1));
    }

    @Test
    public void rankToRow_threeToFive() {
        assertEquals(5, Utils.rankToRow(3));
    }

    @Test
    public void rankToRow_eightToZero() {
        assertEquals(0, Utils.rankToRow(8));
    }

    @Test
    public void rowToRank_sevenToOne() {
        assertEquals(1, Utils.rowToRank(7));
    }

    @Test
    public void rowToRank_fiveToThree() {
        assertEquals(3, Utils.rowToRank(5));
    }

    @Test
    public void rowToRank_zeroToEight() {
        assertEquals(8, Utils.rowToRank(0));
    }

    @Test
    public void fileToCol_aTo0() {
        assertEquals(0, Utils.fileToCol('a'));
    }

    @Test
    public void fileToCol_fTo5() {
        assertEquals(5, Utils.fileToCol('f'));
    }

    @Test
    public void fileToCol_hTo7() {
        assertEquals(7, Utils.fileToCol('h'));
    }

    @Test
    public void colToFile_zeroToA() {
        assertEquals('a', Utils.colToFile(0));
    }

    @Test
    public void colToFile_fiveToF() {
        assertEquals('f', Utils.colToFile(5));
    }

    @Test
    public void colToFile_sevenToH() {
        assertEquals('h', Utils.colToFile(7));
    }

    @Test
    public void isWithinBounds_happyPath() {
        assertTrue(Utils.isWithinBounds(1, 0, 2));
    }

    @Test
    public void isWithinBounds_shouldReturnFalse_whenNumberIsOutOfBounds() {
        assertFalse(Utils.isWithinBounds(10, -5, 5));
    }

    @Test
    public void isWithinBounds_lowerBoundShouldBeInclusive() {
        assertTrue(Utils.isWithinBounds(-1, -1, 1));
    }

    @Test
    public void isWithinBounds_upperBoundShouldBeExclusive() {
        assertFalse(Utils.isWithinBounds(-1, 1, 1));
    }

    @Test
    public void deepCopy_charArrayArray_happyPath() {
        char[][] original = { {'a', 'b'}, {'c', 'd'}};
        char[][] copy = Utils.deepCopy(original);

        assertFalse(copy == original);

        assertEquals(original.length, copy.length);
        for(int i = 0; i < original.length; ++i) {
            assertEquals(original[i].length, copy[i].length);
            for(int j = 0; j < original[i].length; ++j) {
                assertEquals(original[i][j], copy[i][j]);
            }
        }
    }

    @Test
    public void deepCopy_moveList_happyPath() {
        List<Move> original = new ArrayList<Move>();
        original.add(new Move(0, 0, 1, 1));
        original.add(new Move(1, 6, 0, 6, 'Q'));

        List<Move> copy = Utils.deepCopy(original);

        assertFalse(copy == original);
        assertEquals(original.size(), copy.size());

        // each move in original should be duplicated in copy
        for(Move move : original) {
            assertTrue(copy.contains(move));
        }

        // but no move in original should also be in copy
        for(Move move : original) {
            for(Move copiedMove : copy) {
                assertFalse(copiedMove == move);
            }
        }
    }
}
