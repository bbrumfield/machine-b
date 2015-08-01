
package thinkers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import thinking_components.Aha;
import thinking_components.Mind;
import thinking_components.ThoughtProcess;
import basics.GameState;
import basics.Move;

public class RandomMoveSelector extends ThoughtProcess {

    public RandomMoveSelector(Mind observer, GameState gameState, int plyDepthToSearch) {
        super(observer, gameState, plyDepthToSearch);
    }

    public RandomMoveSelector(
            Mind observer,
            GameState gameState,
            int plyDepthToSearch,
            Set<Move> movesToConsider) {
        super(observer, gameState, plyDepthToSearch, movesToConsider);
    }

    @Override
    public void searchAndEvaluate() {
        java.util.Random randy = new java.util.Random();
        int randomIndex = randy.nextInt(this.movesToConsider.size());

        List<Move> bestLine = new ArrayList<Move>();

        Iterator<Move> it = this.movesToConsider.iterator();
        for(int i = 0; i < randomIndex; i++) {
            it.next();
        }

        bestLine.add(it.next());
        double score = Double.MAX_VALUE;

        this.registerNewAha(new Aha(bestLine, score, true));
    }

}
