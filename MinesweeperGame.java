package cs1302.game;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * This class is implemented by MinesweeperDriver to start the game.
 */
public class MinesweeperGame {

    private String[][] minesweeperGrid;
    private boolean[][] mineGrid;
    private String[][] noFogGrid;
    private int totalMines = 0;
    private int roundsFinished = 0;
    private boolean printNoFog = false;
    Scanner keyboard = new Scanner(System.in);
    private int row = 0;
    private int col = 0;

    /**
     * Constructs a object of the {@link MinesweeperGame} class using
     * the numerical information from the seed file. This constructor
     * will also exit the system if the seed file is not accessible or
     * if the information in the file provided is not formatted
     * correctly.
     *
     * @param seed a file containing the information to create the minesweeper grid
     */
    public MinesweeperGame(String seed) {
        try {
            File configFile = new File(seed);
            Scanner configScanner = new Scanner(configFile);
            if (configScanner.hasNextInt()) {
                row = configScanner.nextInt();
                if (configScanner.hasNextInt()) {
                    col = configScanner.nextInt();
                    if (configScanner.hasNextInt()) {
                        totalMines = configScanner.nextInt();
                    }
                }
            } else {
                System.err.println("Seedfile Not Found Error: Cannot create game with "
                    + configFile + ", because it is not formatted correctly.");
                System.exit(1);
            }
            this.construct();
            if ((totalMines > (row * col)) || totalMines == 0) {
                System.err.println("\n" + "Seedfile Value Error: " +
                    " Cannot create a mine field with that many rows/ and or columns!");
                System.exit(3);
            }
            int mineCounter = 0;
            for (int i = 0; i < totalMines; i++) {
                int mineRow = 0, mineCol = 0;
                if (configScanner.hasNextInt()) {
                    mineRow = configScanner.nextInt();
                    if (configScanner.hasNextInt()) {
                        mineCol = configScanner.nextInt();
                        if ((mineRow >= 0 && mineRow <= row) && (mineCol >= 0 && mineCol <= col)) {
                            mineGrid[mineRow][mineCol] = true;
                        } else {
                            System.err.println("Seedfile Format Error: Cannot create game with "
                                + configFile +  " because it is not formatted correctly.");
                            System.exit(1);
                        }
                    }
                }
                mineCounter++;
            }
            if (mineCounter != totalMines) {
                System.err.println("Seedfile Format Error: Cannot creat a game with "
                    + configFile + " because it is not formatted correctly.");
                System.exit(1);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Seedfile Not Found Error: Cannot create game with "
                + e + ", because it cannot be foud or cannot be read due to permission.");
            System.exit(1);
        } // try-catch
    } // MinesweeperGame

    /**
     * An additional method that shortens the line length of the constructor.
     */
    public void construct() {
        if (row < 5 || col < 5) {
            System.err.println("\n" + "Seedfile Value Error: "
                + "Cannot create a mine field with that many rows/ and or columns!");
            System.exit(3);
        } else {
            minesweeperGrid = new String[row][col];
            mineGrid = new boolean[row][col];
            noFogGrid = new String[row][col];
            if (col < 11) {
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        minesweeperGrid[i][j] = "   ";
                        mineGrid[i][j] = false;
                        noFogGrid[i][j] = " ";
                    }
                }
            }
            if (col > 10) {
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        minesweeperGrid[i][j] = "    ";
                        mineGrid[i][j] = false;
                        noFogGrid[i][j] = "  ";                         }
                }

            }
        } //stores grid inoformation: rows, columns, and mines
    }

    /**
     * Prints out welcome or the minesweeper sign to indicate the beggining of the game.
     */
    public void printWelcome() {
        System.out.println("        _");
        System.out.println("  /\\/\\ (_)_ __   ___  _____      _____  ___ _ __   ___ _ __");
        System.out.println(" /    \\| | '_ \\ / _ \\/ __\\ \\ /\\ / / _ \\/ _ \\ '_ \\" +
            "/ _ \\ '__| ");
        System.out.println("/ /\\/\\ \\ | | | |  __/\\__ \\\\ V  V /  __/  __/ |_) |  __/ |");
        System.out.println("\\/    \\/_|_| |_|\\___||___/ \\_/\\_/ \\___|\\___| .__/ \\___|_|");
        System.out.println("                                     ALPHA |_| EDITION");
        System.out.println();
    } // printWelcome

    /**
     * Prints out winning banner along with score if {@code isWon()} is true.
     */
    public void printWin() {
        System.out.println();
        System.out.println(" ░░░░░░░░░▄░░░░░░░░░░░░░░▄░░░░  \"So Doge\"");
        System.out.println(" ░░░░░░░░▌▒█░░░░░░░░░░░▄▀▒▌░░░");
        System.out.println(" ░░░░░░░░▌▒▒█░░░░░░░░▄▀▒▒▒▐░░░  \"Such Score\"");
        System.out.println(" ░░░░░░░▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐░░░");
        System.out.println(" ░░░░░▄▄▀▒░▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐░░░  \"Much Minesweeping\"");
        System.out.println(" ░░░▄▀▒▒▒░░░▒▒▒░░░▒▒▒▀██▀▒▌░░░");
        System.out.println(" ░░▐▒▒▒▄▄▒▒▒▒░░░▒▒▒▒▒▒▒▀▄▒▒▌░░  \"Wow\"");
        System.out.println(" ░░▌░░▌█▀▒▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐░░");
        System.out.println(" ░▐░░░▒▒▒▒▒▒▒▒▌██▀▒▒░░░▒▒▒▀▄▌░");
        System.out.println(" ░▌░▒▄██▄▒▒▒▒▒▒▒▒▒░░░░░░▒▒▒▒▌░");
        System.out.println(" ▀▒▀▐▄█▄█▌▄░▀▒▒░░░░░░░░░░▒▒▒▐░");
        System.out.println(" ▐▒▒▐▀▐▀▒░▄▄▒▄▒▒▒▒▒▒░▒░▒░▒▒▒▒▌");
        System.out.println(" ▐▒▒▒▀▀▄▄▒▒▒▄▒▒▒▒▒▒▒▒░▒░▒░▒▒▐░");
        System.out.println(" ░▌▒▒▒▒▒▒▀▀▀▒▒▒▒▒▒░▒░▒░▒░▒▒▒▌░");
        System.out.println(" ░▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▒▄▒▒▐░░");
        System.out.println(" ░░▀▄▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▄▒▒▒▒▌░░");
        System.out.println(" ░░░░▀▄▒▒▒▒▒▒▒▒▒▒▄▄▄▀▒▒▒▒▄▀░░░  CONGRATULATIONS! ");
        System.out.println(" ░░░░░░▀▄▄▄▄▄▄▀▀▀▒▒▒▒▒▄▄▀░░░░░  YOU HAVE WON!");
        System.out.print(" ░░░░░░░░░▒▒▒▒▒▒▒▒▒▒▀▀░░░░░░░░  SCORE: ");
        System.out.printf("%.2f", this.score());
        System.out.println(" ");
        System.exit(0);
    } // printWin

    /**
     * Prints out gameover banner when a mine is revealed.
     */
    public void printLoss() {
        System.out.println();
        System.out.println(" Oh no... You revealed a mine!");
        System.out.println("  __ _  __ _ _ __ ___   ___    _____   _____ _ __ ");
        System.out.println(" / _` |/ _` | '_ ` _ \\ / _ \\  / _ \\ \\ / / _ \\ '__|");
        System.out.println("| (_| | (_| | | | | | |  __/ | (_) \\ V /  __/ |   ");
        System.out.println(" \\__, |\\__,_|_| |_| |_|\\___|  \\___/ \\_/ \\___|_|   ");
        System.out.println(" |___/                                            ");
        System.out.println();
        System.exit(0);
    } //printLoss

    /**
     * This method prints out the number of rounds finished/completed
     * everytime is is implemented through {@code play()}.
     */
    public void roundsFinished() {
        System.out.println();
        System.out.println("Rounds Completed: " + roundsFinished);
    } //roundsFinished

    /**
     * Calculates the final score base off of {@code minesweeperGrid} and
     * {@code roundsFinished}.
     * @return the final score of the game
     */
    public double score() {
        double score = 0.0;
        score = (100.0 * minesweeperGrid.length
            * minesweeperGrid[0].length) / ((double) roundsFinished);
        return score;
    } //score

    /**
     * Prints the {@code minesweeperGrid} that the player will see and interact with and accounts
     * for padding required to maintian proper spacing for the grid.
     */
    public void printMineField() {
        System.out.println();
        for (int i = 0; i < minesweeperGrid.length; i++) {
            if (minesweeperGrid.length > 10 && i < 10) {
                System.out.print(i + "  |");
            }
            if (minesweeperGrid.length > 10 && i > 9) {
                System.out.print(i + " |");
            }
            if (minesweeperGrid.length < 11) {
                System.out.print(i + " |");
            }
            for (int j = 0; j < minesweeperGrid[i].length; j++) {
                System.out.print(minesweeperGrid[i][j]);
                if (j < minesweeperGrid[i].length - 1) {
                    System.out.print("|");
                }
            }
            System.out.println("|");
        }
        if (minesweeperGrid[0].length < 11) {
            if (minesweeperGrid.length < 11) {
                System.out.print("    ");
            } else {
                System.out.print("     ");
            }
        }
        if (minesweeperGrid[0].length > 10) {
            if (minesweeperGrid.length < 10) {
                System.out.print("     ");
            } else {
                System.out.print("      ");
            }
        }
        for (int k = 0; k < minesweeperGrid[0].length; k++) {
            if (minesweeperGrid[0].length < 11) {
                System.out.print(k + "   ");
            }
            if (minesweeperGrid[0].length > 11) {
                if (k < 9) {
                    System.out.print(k + "    ");
                } else {
                    System.out.print(k + "   ");
                }
            }
        } // Prints out the number of columns
        System.out.println();
    } //printMineField

    /**
     * Prints the {@code noFogGrid} which reveals the location of the mines.
     */
    public void printNoFogGrid() {
        for (int i = 0; i < noFogGrid.length; i++) {
            if (minesweeperGrid.length > 10 && i < 10) {
                System.out.print(i + "  |");
            }
            if (minesweeperGrid.length > 10 && i > 9) {
                System.out.print(i + " |");
            }
            if (minesweeperGrid.length < 11) {
                System.out.print(i + " |");
            }
            for (int j = 0; j < noFogGrid[i].length; j++) {
                if (mineGrid[i][j] == true) {
                    System.out.print("<" + noFogGrid[i][j] + ">");
                } else {
                    System.out.print(minesweeperGrid[i][j]);
                }
                if (j < noFogGrid[i].length - 1) {
                    System.out.print("|");
                }
            }
            System.out.println("|");
        }
        if (minesweeperGrid[0].length < 11) {
            if (minesweeperGrid.length < 10) {
                System.out.print("    ");
            } else {
                System.out.print("     ");
            }
        }
        if (minesweeperGrid[0].length > 10) {
            if (minesweeperGrid.length < 10) {
                System.out.print("     ");
            } else {
                System.out.print("      ");
            }
        }
        for (int k = 0; k < noFogGrid[0].length; k++) {
            if (minesweeperGrid[0].length < 11) {
                System.out.print(k + "   ");
            }
            if (minesweeperGrid[0].length > 10) {
                if (k < 9) {
                    System.out.print(k + "    ");
                } else {
                    System.out.print(k + "   ");
                }
            }
        } //Prints out the number of columns
        System.out.println();
    } //printNoFogGrid

    /**
     * Provides main game loop by invoking other instance methods.
     */
    public void play() {
        this.printWelcome();
        this.roundsFinished();
        this.printMineField();
        while (this.isWon() == false) {
            this.newRound();
            this.roundsFinished();
            if (printNoFog == true) {
                this.printNoFogGrid();
                printNoFog = false;
            } else {
                this.printMineField();
            }
            this.isWon();
        }
    } // play

    /**
     * This methods calls for {@code promptUser()} and {@code command()} to start a new round.
     */
    public void newRound() {
        this.promptUser();
        this.command();
    } //newRound

    /**
     * This prompts the user for the command and reads the command.
     * After this command, the method {@code command()} is implemented.
     * several other methods throughout {@link  MinesweeperGame}.
     */
    public void promptUser() {
        System.out.println();
        System.out.print("minesweeper-alpha: ");
    } //promptUser

    /**
     * Reads command that is inputted by user and calls methods related to these specific commands.
     */
    public void command() {
        String command = keyboard.nextLine().trim();
        Scanner keyboard2 = new Scanner(command);
        String modifiedCommand = keyboard2.next().trim();
        if (modifiedCommand.equals("r") || modifiedCommand.equals("reveal")) {
            this.reveal(keyboard2);
        } else if (modifiedCommand.equals("m") || modifiedCommand.equals("mark")) {
            this.mark(keyboard2);
        } else if (modifiedCommand.equals("g") || modifiedCommand.equals("guess")) {
            this.guess(keyboard2);
        } else if (command.equals("h") || command.equals("help")) {
            this.help();
        } else if (command.equals("q") || command.equals("quit")) {
            this.quit();
        } else if (command.equals("nofog")) {
            this.noFog();
        } else {
            System.out.println();
            System.out.println("Input Error: Command not recognized!");
        }
    } //command

    /**
     * Returns the number of mines adjacent to the specified
     * square in the grid.
     *
     * @param row the row index of the square
     * @param col the column index of the square
     * @return the number of adjacent mines
     */
    private int getNumAdjMines(int row, int col) {
        int getNumAdjMines = 0;
        if (row - 1 < minesweeperGrid.length && row - 1 >= 0) {
            if (col - 1 < minesweeperGrid[0].length && col - 1 >= 0) {
                if (mineGrid[row - 1][col - 1] == true) {
                    getNumAdjMines++;
                }
            }
            if (mineGrid[row - 1][col] == true) {
                getNumAdjMines++;
            }
            if (col + 1 < minesweeperGrid[0].length && col + 1 >= 0) {
                if (mineGrid[row - 1][col + 1] == true) {
                    getNumAdjMines++;
                }
            }
        }
        if (col - 1 < minesweeperGrid[0].length && col - 1 >= 0) {
            if (mineGrid[row][col - 1] == true) {
                getNumAdjMines++;
            }
        }
        if (col + 1 < minesweeperGrid[0].length && col + 1 >= 0) {
            if (mineGrid[row][col + 1] == true) {
                getNumAdjMines++;
            }
        }
        if (row + 1 < minesweeperGrid.length && row + 1 >= 0) {
            if (col - 1 < minesweeperGrid[0].length && col - 1 >= 0) {
                if (mineGrid[row + 1][col - 1] == true) {
                    getNumAdjMines++;
                }
            }
            if (mineGrid[row + 1][col] == true) {
                getNumAdjMines++;
            }
            if (col + 1 < minesweeperGrid[0].length && col + 1 >= 0) {
                if (mineGrid[row + 1][col + 1] == true) {
                    getNumAdjMines++;
                }
            }
        }
        return getNumAdjMines;
    } //getNumAdjMines

    /**
     * Indicates whether or not the square is in the game grid.
     *
     * @param row the row index of the square
     * @param col the column index of the square
     * @return true if the square is in the game grid; false otherwise
     */
    private boolean isInBounds(int row, int col) {
        boolean isInBound = false;
        if (row >= 0 && row < minesweeperGrid.length && col >= 0 &&
            col < minesweeperGrid[0].length) {
            isInBound = true;
        } else {
            isInBound = false;
        }
        return isInBound;
    } //isInBounds

    /**
     * Reveals if the the square contains a mine or the number of adjacent mines.
     *
     * @param keyboard2 the second keyboard object that contains the trimmed command
     */
    public void reveal(Scanner keyboard2) {
        int revealRow = 0;
        int revealCol = 0;
        if (keyboard2.hasNextInt()) {
            revealRow = keyboard2.nextInt();
            if (keyboard2.hasNextInt()) {
                revealCol = keyboard2.nextInt();
            }
        } else {
            System.out.println("Input Error: Command not recognized!");
        }
        if (this.isInBounds(revealRow, revealCol) == true
            && mineGrid[revealRow][revealCol] == false) {
            if (minesweeperGrid[0].length < 11) {
                minesweeperGrid[revealRow][revealCol] = " " +
                    getNumAdjMines(revealRow, revealCol) + " ";
            }
            if (minesweeperGrid[0].length > 10) {
                minesweeperGrid[revealRow][revealCol] = "  " +
                    getNumAdjMines(revealRow, revealCol) + " ";
            }
            roundsFinished++;
        }
        if (this.isInBounds(revealRow, revealCol) == true &&
            mineGrid[revealRow][revealCol] == true) {
            this.printLoss();
        }
        if (this.isInBounds(revealRow, revealCol) == false) {
            System.out.print("Input Error: Index out of Bounds!");
            System.out.println();
        }
    } // reveal

    /**
     * Marks row if user suspects that a mine is located in that square.
     *
     * @param keyboard2 the second keyboard object that contains the trimmed command
     */
    public void mark(Scanner keyboard2) {
        int markRow = 0;
        int markCol = 0;
        if (keyboard2.hasNextInt()) {
            markRow = keyboard2.nextInt();
            if (keyboard2.hasNextInt()) {
                markCol = keyboard2.nextInt();
            }
        } else {
            System.out.println("Input Error: Command not recognized!");
        }
        if (this.isInBounds(markRow, markCol) == true) {
            if (minesweeperGrid[0].length < 11) {
                minesweeperGrid[markRow][markCol] = " F ";
                noFogGrid[markRow][markCol] = "F";
            }
            if (minesweeperGrid[0].length > 10) {
                minesweeperGrid[markRow][markCol] = "  F ";
                noFogGrid[markRow][markCol] = " F";
            }
            roundsFinished++;
        }
        if (this.isInBounds(markRow, markCol) == false) {
            System.out.print("Input Error: Index out of Bounds!");
            System.out.println();
        }
    }  // mark

    /**
     * Marks square if the player think a square potentially contains a mine.
     *
     * @param keyboard2 the second keyboard object that contains the trimmed command
     */
    public void guess(Scanner keyboard2) {
        int guessRow = 0;
        int guessCol = 0;
        if (keyboard2.hasNextInt()) {
            guessRow = keyboard2.nextInt();
            if (keyboard2.hasNextInt()) {
                guessCol = keyboard2.nextInt();
            }
        } else {
            System.out.println("Input Error: Command not recognized!");
        }
        if (this.isInBounds(guessRow, guessCol) == true) {
            if (minesweeperGrid[0].length < 11) {
                minesweeperGrid[guessRow][guessCol] = " ? ";
                noFogGrid[guessRow][guessCol] = "?";
            }
            if (minesweeperGrid[0].length > 10) {
                minesweeperGrid[guessRow][guessCol] = "  ? ";
                noFogGrid[guessRow][guessCol] = " ?";
            }
            roundsFinished++;
        }
        if (this.isInBounds(guessRow, guessCol) == false) {
            System.out.print("Input Error: Index out of Bounds!");
            System.out.println();
        }
    } // guess

    /**
     * Shows the menus of available options the player can type in to play.
     */
    private void help() {
        System.out.println();
        System.out.println("Commands Available...");
        System.out.println(" - Reveal: r/reveal row col");
        System.out.println(" -   Mark: m/mark   row col");
        System.out.println(" -  Guess: g/guess  row col");
        System.out.println(" -   Help: h/help");
        System.out.println(" -   Quit: q/quit");
        roundsFinished++;
    }

    /**
     * Exits out of the game when user wants to stop playing.
     */
    public void quit() {
        System.out.println();
        System.out.println("Quitting the game...");
        System.out.println("Bye!");
        System.exit(0);
    }

    /**
     * Makes {@code printNofog} true in order to print the {@code noFogGrid}
     * in the loop in {@code play()}.
     */
    public void noFog() {
        roundsFinished++;
        printNoFog = true;
    } //noFog

    /**
     * Indicates if game is won or not. The loop in {@code play()} depends on this method to run.
     *
     * @return true if normal squares without mines are revealed and
     * sqaures with mines are marked as having mines.
     */
    public boolean isWon() {
        boolean isWon = false;
        boolean minesRevealed = true;
        boolean normalSquaresRevealed  = true;
        for (int i = 0; i < minesweeperGrid.length; i++) {
            for (int j = 0; j < minesweeperGrid[0].length; j++) {
                if (mineGrid[i][j] == true && !( minesweeperGrid[i][j].trim().equals("F"))) {
                    minesRevealed = false;
                }
                if (mineGrid[i][j] == false) {
                    if (minesweeperGrid[i][j].trim().equals("?")) {
                        normalSquaresRevealed = false;
                    }
                    if (minesweeperGrid[i][j].trim().equals("F")) {
                        normalSquaresRevealed = false;
                    }
                    if (minesweeperGrid[i][j].equals("   ") ||
                        minesweeperGrid[i][j].equals("    ")) {
                        normalSquaresRevealed = false;
                    }
                }
            }
        }
        if (minesRevealed == true && normalSquaresRevealed == true) {
            isWon = true;
            this.printWin();
        }
        return isWon;
    } //isWon

} // MinesweeperGame
