
package move_path_enumerator;

import java.util.Set;

import basics.GameState;
import basics.Move;
import basics.MoveGenerator;

public class MovePathEnumerator {

    public static PerftResult perft(GameState gameState, int plyDepth) {
        long startTime = System.nanoTime();
        long numNodes = countNodes(gameState, plyDepth);
        long endTime = System.nanoTime();

        PerftResult result = new PerftResult(gameState, plyDepth, startTime, endTime, numNodes);

        return result;
    }

    public static String divide(GameState gameState, int plyDepth) {
        StringBuilder sb = new StringBuilder();

        long nodesSoFar = 0;

        Set<Move> currentLegalMoves = MoveGenerator.getLegalMoves(gameState);

        GameState updatedGameState;
        int newPlyDepth = plyDepth - 1;
        long currentNumNodes;

        for(Move currentLegalMove : currentLegalMoves) {
            updatedGameState = new GameState(gameState, currentLegalMove);
            currentNumNodes = countNodes(updatedGameState, newPlyDepth);
            nodesSoFar += currentNumNodes;

            sb.append(currentLegalMove.toChessMoveString());
            sb.append(" ");
            sb.append(currentNumNodes);
            sb.append("\n");
        }

        sb.append("moves: ").append(currentLegalMoves.size());
        sb.append("\n");
        sb.append("nodes: ").append(nodesSoFar);

        return sb.toString();
    }

    private static long countNodes(GameState updatedGameState, int remainingPlyDepthToSearch) {
        long numMovesGenerated = 0;

        Set<Move> currentLegalMoves = MoveGenerator.getLegalMoves(updatedGameState);

        if(remainingPlyDepthToSearch < 0) {
            // there must be a mistake, simply return 0
        }
        else if(remainingPlyDepthToSearch == 0) {
            numMovesGenerated = 1;
        }
        else if(remainingPlyDepthToSearch == 1) {
            numMovesGenerated = currentLegalMoves.size();
        }
        else {
            for(Move currentMove : currentLegalMoves) {
                GameState newUpdatedGameState = new GameState(updatedGameState, currentMove);
                numMovesGenerated +=
                        countNodes(newUpdatedGameState, remainingPlyDepthToSearch - 1);
            }
        }

        return numMovesGenerated;
    }
}
