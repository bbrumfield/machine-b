
package basics;

import thinking_components.Mind;

public class Engine {

    private static Mind mind;

    private static GameState gameState;

    private Engine() {

    }

    public static void setGameState(GameState gState) {
        gameState = gState;
    }

    public static GameState getGameState() {
        return gameState;
    }

    public static Move getBestMove() {
        mind = new Mind(gameState);
        return mind.getBestMoveSoFar();
    }
}
