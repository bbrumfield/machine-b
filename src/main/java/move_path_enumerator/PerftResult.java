
package move_path_enumerator;

import basics.GameState;

public class PerftResult {

    private final GameState gameState;
    private final int plyDepth;

    private final long startTime;
    private final long endTime;

    private final long numNodes;

    public PerftResult(
            GameState gameState, int plyDepth, long startTime, long endTime, long numNodes) {
        this.gameState = gameState;
        this.plyDepth = plyDepth;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numNodes = numNodes;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public int getPlyDepth() {
        return this.plyDepth;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public long getDurationInNanoseconds() {
        return this.endTime - this.startTime;
    }

    public long getNumNodes() {
        return this.numNodes;
    }

    @Override
    public String toString() {
        return "nodes: " + this.getNumNodes() + "\nnanos: " + this.getDurationInNanoseconds();
    }

}
