
package thinkers_rename;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import thinking_components.Aha;
import thinking_components.Mind;
import thinking_components.ThoughtProcess;
import basics.GameState;
import basics.Move;
import basics.MoveGenerator;

public class Random extends ThoughtProcess {

    public Random(Mind observer, GameState gameState, int levelsToSearch) {
        super(observer, gameState, levelsToSearch);
    }

    @Override
    public void searchAndEvaluate() {
        Set<Move> legalMoves = MoveGenerator.getLegalMoves(this.gameState);

        System.out.println("\n" + this.gameState.getBoard().toGridString() + "\n");

        System.out.print("possible moves:");
        for(Move move : legalMoves) {
            System.out.print(" " + move.toChessMoveString());
        }
        System.out.println();

        java.util.Random randy = new java.util.Random();
        int randomIndex = randy.nextInt(legalMoves.size());

        List<Move> bestLine = new ArrayList<Move>();

        Iterator<Move> it = legalMoves.iterator();
        for(int i = 0; i < randomIndex; i++) {
            it.next();
        }

        bestLine.add(it.next());
        double score = Double.MAX_VALUE;

        this.registerNewAha(new Aha(bestLine, score, true));
    }

}
