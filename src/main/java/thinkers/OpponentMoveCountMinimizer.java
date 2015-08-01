
package thinkers;

import java.util.Set;

import thinking_components.Mind;
import thinking_components.ThoughtProcess;
import basics.GameState;
import basics.Move;
import basics.MoveGenerator;

public class OpponentMoveCountMinimizer extends ThoughtProcess {

    public OpponentMoveCountMinimizer(Mind observer, GameState gameState, int levelsToSearch) {
        super(observer, gameState, levelsToSearch);
    }

    @Override
    public void searchAndEvaluate() {
        Set<Move> legalMoves = MoveGenerator.getLegalMoves(this.gameState);

        // DO: implement
    }

}
