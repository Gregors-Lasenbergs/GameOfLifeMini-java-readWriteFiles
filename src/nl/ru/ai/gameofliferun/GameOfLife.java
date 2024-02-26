package nl.ru.ai.gameofliferun;

import nl.ru.ai.gameoflife.Cell;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static nl.ru.ai.gameoflife.Universe.*;

public class GameOfLife {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Color[] colors = new Color[]{Color.pink, Color.red, Color.MAGENTA, Color.ORANGE, Color.yellow, Color.GREEN, Color.CYAN, Color.BLUE};
        System.out.println("Possible configurations: \n10cellrow.txt\nacorn.txt\nglider.txt\ngosperglidergun.txt\npulsar.txt");
        System.out.println("Please give an universe configuration file name: ");
        String fileName = scanner.nextLine();;
        System.out.println("Please give a number of how many generations you would like to see (at least 50 recommended): ");
        int steps = scanner.nextInt();

        Cell[][] universe = readUniverseFile(fileName);
        showUniverse(universe, colors);

        for(int i = 0; i < steps; i++) {
            universe = nextGeneration(universe, colors);
            showUniverse(universe, colors);
            sleep(50);
            if(i == steps-1){
                printOutConfiguration(universe);
            }
        }
    }

    /**
     * Makes a file of the universe configuration - printedConfig.txt.
     * @param universe universe configuration in last iteration
     */
    private static void printOutConfiguration(Cell [][] universe) {
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(Files.newOutputStream(Paths.get("printedConfig.txt")));

        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 60; j++) {
                if(universe[i][j] == Cell.LIVE)
                    writer.write('*');
                else
                    writer.write('.');
            }
            writer.write("\n");
        }
        writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the file of the universe.
     * @param fileName string containing name of text file read.
     * @return universe(initial configuration) created from the text file
     */
    static Cell[][] readUniverseFile(String fileName) {
        assert true;
        Cell[][] universe = new Cell[40][60];
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(fileName))));
            checkFile(fileName);
            String line;
            for (int i = 0; i < 40; i++) {
                line = reader.readLine();
                for (int j = 0; j < 60; j++) {
                    if (line.charAt(j) == '*') {
                        universe[i][j] = Cell.LIVE;
                    } else
                        universe[i][j] = Cell.DEAD;
                }
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return universe;
    }

    /**
     * Checks if the file is valid for further use.
     * @param fileName string containing name of text file checked for use.
     */
    private static void checkFile(String fileName) {
        assert fileName != null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(fileName))));
            String line;
            int lineCount = 0;

            while ((line = reader.readLine()) != null) {

                if (line.length() != 60) {
                    throw new IllegalArgumentException("One of the lines had less than 60 character!");
                }

                for (int i = 0; i < line.length(); i++) {

                    if (((lineCount == 0 || lineCount == 39) && line.charAt(i) == '*') || (line.charAt(0) == '*' && line.charAt(59) == '*')) {
                        throw new IllegalArgumentException("Border has a live cell!");
                    }

                    if ('*' != line.charAt(i) && '.' != line.charAt(i)) {
                        throw new IllegalArgumentException("Illegal character used!");
                    }
                }
                lineCount++;
            }
            reader.close();
            if (lineCount != 40) {
                throw new IllegalArgumentException("There are less than 40 lines!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Display the config of the universe.
     * @param universe current configuration to display
     * @param colors colors
     */
    private static void showUniverse(Cell[][] universe, Color[] colors) {
        assert true;
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 60; j++) {
                if(i < 5) { updateScreen(i, j, universe[i][j], colors[0]); } // This is for the bonus part.
                if(i >= 5 && i < 10){ updateScreen(i, j, universe[i][j], colors[1]); }
                if(i >= 10 && i < 15){ updateScreen(i, j, universe[i][j], colors[2]); }
                if(i >= 15 && i < 20){ updateScreen(i, j, universe[i][j], colors[3]); }
                if(i >= 20 && i < 25){ updateScreen(i, j, universe[i][j], colors[4]); }
                if(i >= 25 && i < 30){ updateScreen(i, j, universe[i][j], colors[5]); }
                if(i >= 30 && i < 35){ updateScreen(i, j, universe[i][j], colors[6]); }
                if(i >= 35){ updateScreen(i, j, universe[i][j], colors[7]); }
            }
        }
    }

    /**
     * Makes next generation of the given config.
     * @param universe current universe configuration
     * @param colors colors
     * @return next configuration based on alive and dead cells in the current configuration
     */
    private static Cell[][] nextGeneration(Cell[][] universe, Color[] colors) {
        assert true;
        Cell[][] newUniverse = new Cell[40][60];
        int i;
        int j;
        int color = 0;
        for (i = 1; i < 39; i++) {
            for (j = 1; j < 59; j++) {
                int liveCells = checkLive(i, j, universe);
                if (universe[i][j] == Cell.LIVE && (liveCells == 2 || liveCells == 3)) {
                    newUniverse[i][j] = Cell.LIVE;

                } else if (universe[i][j] == Cell.DEAD && liveCells == 3) {
                    newUniverse[i][j] = Cell.LIVE;
                    updateScreen(i, j, universe[i][j], colors[color]);

                } else if (universe[i][j] == Cell.LIVE && (liveCells < 2 || liveCells > 3)) {
                    newUniverse[i][j] = Cell.DEAD;
                }
            }
        }
        for (i = 0; i < 40; i++) {
            for (j = 0; j < 60; j++) {
                if (newUniverse[i][j] == null) {
                    newUniverse[i][j] = Cell.DEAD;
                }
            }
        }
        return newUniverse;
    }

    /**
     * Checks how many live neighbors does a cell have.
     * @param i row value
     * @param j column value
     * @param universe current configuration
     * @return return alive neighbor cell count for a specific cell in universe
     */
    private static int checkLive(int i, int j, Cell[][] universe) {
        assert true;
        int liveCells = 0;
        for (int row = i - 1; row <= i + 1; row++) {
            for (int col = j - 1; col <= j + 1; col++) {
                if (universe[row][col] == Cell.LIVE) {
                    liveCells++;
                }
            }
        }
        if (universe[i][j] == Cell.LIVE) {
            liveCells -= 1;
        }
        return liveCells;
    }
}
