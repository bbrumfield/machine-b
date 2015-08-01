
package thinkers;

import thinking_components.Mind;
import thinking_components.ThoughtProcess;
import basics.GameState;

public class OpponentPieceValueMinimizer extends ThoughtProcess {

    public OpponentPieceValueMinimizer(Mind observer, GameState gameState, int levelsToSearch) {
        super(observer, gameState, levelsToSearch);
    }

    @Override
    public void searchAndEvaluate() {
        // DO: implement
    }

}
