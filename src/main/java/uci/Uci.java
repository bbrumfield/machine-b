
package uci;

import java.util.Scanner;

import utils.Mapper;
import basics.Engine;
import exceptions.UnknownCommandException;

public class Uci {

    private static boolean userHasQuit = false;

    public static void loop() {
        Scanner scan = new Scanner(System.in);

        while(!userHasQuit) {
            processCommand(scan.nextLine());
        }

        scan.close();
    }

    /* default (for testing purposes) */static void processCommand(String command) {
        // DO: reorder based on frequency of calls
        try {
            if(command.equals("quit")) {
                in_quit();
            }
            else if(command.equals("uci")) {
                in_uci();
            }
            else if(command.equals("debug")) {
                in_debug();
            }
            else if(command.equals("isready")) {
                in_isready();
            }
            else if(command.startsWith("setoption")) {
                in_setoption(command);
            }
            else if(command.startsWith("register")) {
                in_register(command);
            }
            else if(command.equals("ucinewgame")) {
                in_ucinewgame();
            }
            else if(command.startsWith("position")) {
                in_position(command);
            }
            else if(command.startsWith("go")) {
                in_go(command);
            }
            else if(command.equals("stop")) {
                in_stop();
            }
            else if(command.startsWith("ponderhit")) {
                in_ponderhit();
            }
            else {
                throw new UnknownCommandException();
            }
        }
        catch(UnknownCommandException | IllegalArgumentException e) {
            // move along, as per uci specifications: "if the engine or the GUI receives an
            // unknown command or token it should just ignore it"
            respond("Unable to parse command: <" + command + ">");
        }
    }

    // =============================================================================================
    // input (standard in -> engine)
    // =============================================================================================

    private static void in_uci() {
        out_id();
        out_options();
        out_uciok();
    }

    private static void in_debug() {
        // DO: implement
    }

    private static void in_isready() {
        out_readyok();
    }

    private static void in_setoption(String command) {
        // currently not supported
    }

    private static void in_register(String command) {
        // currently not supported
    }

    private static void in_ucinewgame() {
        // currently not supported
    }

    private static void in_position(String command) throws UnknownCommandException {
        Engine.setGameState(Mapper.toGameState(command));
    }

    private static void in_go(String command) {
        out_bestmove();
    }

    private static void in_stop() {
        // DO: implement
    }

    private static void in_ponderhit() {
        // currently not supported
    }

    private static void in_quit() {
        userHasQuit = true;
    }

    // =============================================================================================
    // output (engine -> standard out)
    // =============================================================================================

    private static void out_id() {
        respond("id name MachineB");
        respond("id author Benjamin Brumfield");
    }

    private static void out_uciok() {
        respond("uciok");
    }

    private static void out_readyok() {
        respond("readyok");
    }

    private static void out_bestmove() {
        respond("bestmove " + Engine.getBestMove().toChessMoveString());
    }

    private static void out_copyprotection() {
        // currently not supported
    }

    private static void out_registration() {
        // currently not supported
    }

    private static void out_info() {
        // currently not supported
    }

    private static void out_options() {
        // currently not supported
    }

    private static void respond(String response) {
        System.out.println(response);
    }
}
