
package uci;

import org.junit.Test;

import exceptions.UnknownCommandException;

public class UciTest {

    /*
     * While letting MachineB play itself over and over (mid-July 2015), every now and then an
     * ArrayIndexOutOfBoundException would pop up, all seemed similar. This is the most recent one
     * that occurred. Turns out I hadn't updated MoveTracker#deepCopy() to include the flags for
     * hasMoved*.
     */
    @Test
    public void toGameState_shouldNotThrowArrayIndexOutOfBoundsException()
            throws UnknownCommandException {
        String positionCommand = "position startpos"
                + " moves a2a3 d7d6 e2e3 g7g5 h2h3 f8g7 f1e2 d6d5 c2c3 b7b6 f2f3 h7h5 e2a6 c8b7"
                + " e1f1 f7f5 d1a4 b6b5 a6b5 b8d7 a4h4 a7a5 h4a4 g7e5 a4b4 e5c3 b5a6 d7c5 b1c3 b7c8"
                + " b4g4 c5a6 f1f2 e8d7 g4e4 d5e4 f2g3 e7e6 c3d1 a8b8 a1a2 b8b4 d1f2 d8e7 f2g4 b4b2"
                + " f3f4 g8h6 g4h6 e7d6 a3a4 d7e8 a2b2 h5h4 g3h2 c8d7 b2b3 c7c5 b3a3 d6d4 a3b3 h8h7"
                + " b3b2 d4e3 b2a2 e3h3 g1h3";

        Uci.processCommand("ucinewgame");
        Uci.processCommand(positionCommand);
        Uci.processCommand("go");
    }
}
