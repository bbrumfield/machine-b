
package thinking_components;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import thinkers.RandomMoveSelector;
import basics.GameState;
import basics.Move;

public class Mind implements Observer {

    protected final GameState gameState;

    private Set<ThoughtProcess> thoughts;

    private Aha mostRecentAha;

    public Mind(GameState gameState) {
        this.gameState = gameState;
        ThoughtProcess randy = new RandomMoveSelector(this, this.gameState, 0);

        (new Thread(randy)).start();
    }

    public Move getBestMoveSoFar() {
        while(null == this.mostRecentAha) {
            try {
                Thread.sleep(100);
            }
            catch(InterruptedException e) {
                // DO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return this.mostRecentAha.getBestMoveSoFar().deepCopy();
    }

    @Override
    public void update(Observable o, Object arg) {
        Aha newAha = ((Aha) arg).deepCopy();

        if(newAha.compareTo(this.mostRecentAha) > 0) {
            this.mostRecentAha = newAha;
        }
    }
}
