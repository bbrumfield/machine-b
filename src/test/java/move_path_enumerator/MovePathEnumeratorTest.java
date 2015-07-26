
package move_path_enumerator;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Scanner;

import org.junit.Test;

import utils.Mapper;
import basics.GameState;

public class MovePathEnumeratorTest {

    private static final String PATH_TO_PERFT_DIR = "perfts";

    private static final long MAX_NODES_TO_SEARCH_FOR = 250000;

    @Test
    public void runAllPerfts() throws Exception {
        File perftDir = new File(PATH_TO_PERFT_DIR);
        File[] perftFiles = perftDir.listFiles();
        Scanner perftFileScanner;
        String currentLine;

        for(File perftFile : perftFiles) {
            System.out.println("\n\nreading " + perftFile.getAbsolutePath());
            perftFileScanner = new Scanner(perftFile);
            while(perftFileScanner.hasNext()) {
                currentLine = perftFileScanner.nextLine();
                if(!this.isACommentLine(currentLine)) {
                    System.out.println("testing " + currentLine);
                    this.runPerft(currentLine.split(","));
                }
            }
        }

        System.out.println("\nDONE");
    }

    private boolean isACommentLine(String line) {
        return (line.charAt(0) == '/') && (line.charAt(1) == '/');
    }

    private void runPerft(String... fenAndNumMovesExpectedAtEachDepth) throws Exception {
        String fen = fenAndNumMovesExpectedAtEachDepth[0];
        for(int plyDepth = 1; plyDepth < fenAndNumMovesExpectedAtEachDepth.length; plyDepth++) {
            long expectedAtDepth = Long.parseLong(fenAndNumMovesExpectedAtEachDepth[plyDepth]);
            if(expectedAtDepth <= MAX_NODES_TO_SEARCH_FOR) {
                GameState gameState = Mapper.toGameState("position fen " + fen);

                this.checkExpect(gameState, plyDepth, expectedAtDepth);
            }
        }
    }

    private void checkExpect(GameState gameState, int plyDepth, long numMovesExpected) {
        long numMovesGenerated = MovePathEnumerator.perft(gameState, plyDepth);

        assertEquals(numMovesExpected, numMovesGenerated);
    }
}
