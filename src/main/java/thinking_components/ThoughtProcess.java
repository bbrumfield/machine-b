
package thinking_components;

import java.util.Observable;
import java.util.Set;

import basics.GameState;
import basics.Move;
import basics.MoveGenerator;

public abstract class ThoughtProcess extends Observable implements Runnable {

    protected GameState gameState;

    protected int plyDepthToSearch;

    protected Set<Move> movesToConsider;

    private Aha mostRecentAha;

    public ThoughtProcess(
            Mind observer,
            GameState gameState,
            int plyDepthToSearch,
            Set<Move> movesToConsider) {
        this.addObserver(observer);

        this.gameState = gameState;
        this.plyDepthToSearch = plyDepthToSearch;

        this.movesToConsider = movesToConsider;
    }

    public ThoughtProcess(Mind observer, GameState gameState, int plyDepthToSearch) {
        this(observer, gameState, plyDepthToSearch, MoveGenerator.getLegalMoves(gameState));
    }

    public abstract void searchAndEvaluate();

    public void run() {
        this.searchAndEvaluate();
    }

    protected void registerNewAha(Aha aha) {
        this.mostRecentAha = aha;
        this.setChanged();
        this.notifyObservers(this.mostRecentAha);
    }
}
