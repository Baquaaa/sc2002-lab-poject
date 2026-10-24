package rush;

import rush.boundary.ConsoleUI;
import rush.control.GameController;

/**
 * Starts the game. Use "java -cp out rush.Main --echo &lt; runs/stage1-victory.txt"
 * to replay a saved run; --echo prints each input line so the output is readable.
 */
public class Main {

    public static void main(String[] args) {
        boolean echo = args.length > 0 && args[0].equals("--echo");
        GameController game = new GameController(new ConsoleUI(echo));
        game.play();
    }
}
