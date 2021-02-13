package cs1302.game;

import cs1302.game.MinesweeperGame;
import java.io.File;

/**
 * This is the main method of the minesweeper. It handles
 * the command line arguments inputted by the user to read
 * {@code seedFile}.
 */
public class MinesweeperDriver {
    /**
     * Creates an object of {@link MinesweeperGame} if the
     * proper conditons are met in the command line. Otherwise,
     * It also starts the main game loop of {@link MinesweeperGame}.
     *
     * @param args the command line argument inputted by the user
     */
    public static void main(String[] args) {
        if (args[0].equals("--seed")) {
            String seedFile = args[1];
            File file = new File(seedFile);
            MinesweeperGame game = new MinesweeperGame(seedFile);
            game.play();
        } else if (args[0].equals("--gen")) {
            System.err.println("Seedfile generation not supported.");
            System.exit(2);
        } else {
            System.err.println("Unable to interpret supplied command-line arguements.");
        }
    }
}
