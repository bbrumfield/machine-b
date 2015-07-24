
package basics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MoveTest {

    @Test
    public void isPromotion_shouldReturnTrue_whenMoveIsPromotion() {
        Move move = new Move(1, 0, 0, 0, 'Q');
        assertTrue(move.isPromotion());
    }

    @Test
    public void isPromotion_shouldReturnFalse_whenMoveIsNotPromotion() {
        Move move = new Move(1, 0, 0, 0);
        assertTrue(!move.isPromotion());
    }

    @Test
    public void toChessMoveString_withPromotion() {
        Move move = new Move(1, 0, 0, 0, 'Q');

        String expect = "a7a8Q";
        String actual = move.toChessMoveString();

        assertEquals(expect, actual);
    }

    @Test
    public void toChessMoveString_withoutPromotion() {
        Move move = new Move(0, 0, 1, 0);

        String expect = "a8a7";
        String actual = move.toChessMoveString();

        assertEquals(expect, actual);
    }

    @Test
    public void toCordinateMoveString_withPromotion() {
        Move move = new Move(1, 0, 0, 0, 'Q');

        String expect = "1000Q";
        String actual = move.toCoordinateMoveString();

        assertEquals(expect, actual);
    }

    @Test
    public void toCordinateMoveString_withoutPromotion() {
        Move move = new Move(0, 0, 1, 0);

        String expect = "0010";
        String actual = move.toCoordinateMoveString();

        assertEquals(expect, actual);
    }

    @Test
    public void ensure_move_equals_itself() {
        Move move = new Move(0, 1, 0, 2);

        assertTrue(move.equals(move));
    }

    @Test
    public void ensure_equivalent_moves_equal_each_other() {
        Move moveA = new Move(0, 1, 0, 2);
        Move moveB = new Move(0, 1, 0, 2);

        assertTrue(moveA.equals(moveB));
    }

    @Test
    public void ensure_deepCopy_move_equality() {
        Move original = new Move(0, 1, 0, 2);
        Move deepCopy = original.deepCopy();

        assertTrue(original.equals(deepCopy));
        assertTrue(original != deepCopy);
    }

    @Test
    public void ensure_nonequivalent_moves_do_not_equal_each_other() {
        Move moveA = new Move(0, 1, 0, 2);
        Move moveB = new Move(0, 2, 0, 1);

        assertTrue(!moveA.equals(moveB));
    }

    @Test
    public void ensure_move_does_not_equal_null() {
        Move move = new Move(0, 1, 0, 2);

        assertTrue(!move.equals(null));
    }

}
