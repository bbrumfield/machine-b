
package move_path_enumerator;

import java.util.Set;

import basics.GameState;
import basics.Move;
import basics.MoveGenerator;

public class MovePathEnumerator {

    // DO: maybe return a Result, or something, that contains the result and the times and stats
    public static long perft(GameState gameState, int plyDepth) {
        long numMovesGenerated = 0;

        Set<Move> currentLegalMoves = MoveGenerator.getLegalMoves(gameState);

        if(plyDepth < 1) {
            // there must be a mistake, simply return 0
        }
        else if(plyDepth == 1) {
            numMovesGenerated = currentLegalMoves.size();
        }
        else {
            GameState updatedGameState;
            int newPlyDepth = plyDepth - 1;

            for(Move currentMove : currentLegalMoves) {
                updatedGameState = new GameState(gameState, currentMove);

                numMovesGenerated += perft(updatedGameState, newPlyDepth);
            }
        }

        return numMovesGenerated;
    }

    // DO: return a string with the results rather than printing them out directly
    // DO: maybe return a Result, or something, that contains the result and the times and stats
    public static void divide(GameState gameState, int plyDepth) {
        long nodesSoFar = 0;

        Set<Move> currentLegalMoves = MoveGenerator.getLegalMoves(gameState);

        GameState updatedGameState;
        int newPlyDepth = plyDepth - 1;
        long currentNumNodes;

        for(Move currentLegalMove : currentLegalMoves) {
            updatedGameState = new GameState(gameState, currentLegalMove);
            currentNumNodes = perft(updatedGameState, newPlyDepth);
            nodesSoFar += currentNumNodes;

            System.out.println(
                    currentLegalMove.toChessMoveString()
                    + " "
                    + currentNumNodes);
        }

        System.out.println("\nnodes summed: " + nodesSoFar);
        System.out.println("nodes found:  " + perft(gameState, plyDepth));

        System.out.println("moves: " + perft(gameState, 1));
    }
}
