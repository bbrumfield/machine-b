
package thinkers;

import thinking_components.Mind;
import thinking_components.ThoughtProcess;
import basics.GameState;

public class OpponentMoveCountMinimizer extends ThoughtProcess {

    public OpponentMoveCountMinimizer(Mind observer, GameState gameState, int levelsToSearch) {
        super(observer, gameState, levelsToSearch);
    }

    @Override
    public void searchAndEvaluate() {
        // DO: implement
    }

}
