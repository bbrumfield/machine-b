
package thinking_components;

import java.util.List;

import utils.Utils;
import basics.Move;

public class Aha implements Comparable<Aha> {

    private final List<Move> bestLineSoFar;
    private final double bestLineSoFarScore;

    private final boolean isDoneThinking;

    public Aha(List<Move> bestLineSoFar, double bestLineSoFarScore, boolean isDoneThinking) {
        this.bestLineSoFarScore = bestLineSoFarScore;
        this.bestLineSoFar = bestLineSoFar;
        this.isDoneThinking = isDoneThinking;
    }

    public Move getBestMoveSoFar() {
        return this.bestLineSoFar.get(0).deepCopy();
    }

    public double getBestLineSoFarScore() {
        return this.bestLineSoFarScore;
    }

    public List<Move> getBestLineSoFar() {
        return this.bestLineSoFar;
    }

    public boolean isDoneThinking() {
        return this.isDoneThinking;
    }

    public Aha deepCopy() {
        return new Aha(
                Utils.deepCopy(this.bestLineSoFar),
                this.bestLineSoFarScore,
                this.isDoneThinking);
    }

    public int compareTo(Aha other) {
        if(null == other) {
            return 1;
        }

        if(this.bestLineSoFarScore > other.bestLineSoFarScore) {
            return 1;
        }
        else if(this.bestLineSoFarScore < other.bestLineSoFarScore) {
            return -1;
        }
        else {
            return 0;
        }
    }

}
