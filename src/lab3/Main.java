package lab3;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main.readInputs();
    }

    private static void readInputs() {
        Scanner reader = new Scanner(System.in);
        final Rubik rubik = new Rubik(Main.readGrid(reader));
        ArrayList<String> commandInputs = Main.readCommands(reader);
        reader.close();

        final Rubik rubikAfterCommands = Main.parseCommands(rubik, commandInputs);
        System.out.println(rubikAfterCommands.toString());
    }

    private static int[][][] readGrid(Scanner reader) {
        final int MAX_FACES = 6;
        final int MAX_ROWS = 3;
        final int MAX_COLS = 3;

        int[][][] grid = new int[MAX_FACES][MAX_ROWS][MAX_COLS];
        int face = 0;
        int row = 0;
        int column = 0;
        while (reader.hasNextInt()) {
            if (row < 3) {
                if (column < 3) {
                    grid[face][row][column] = reader.nextInt();
                    column++;
                } else {
                    row++;
                    column = 0;
                }
            } else {
                face++;
                row = 0;
            }
        }
        return grid;
    }

    private static ArrayList<String> readCommands(Scanner reader) {
        final ArrayList<String> commandInputs = new ArrayList<>();

        while (reader.hasNext()) {
            commandInputs.add(reader.next());
        }

        return commandInputs;
    }

    private static Rubik parseCommands(Rubik rubik, ArrayList<String> commands) {
        Rubik newRubik = new Rubik(rubik);
        for (String command : commands) {
            switch (command) {
                case "R":
                    newRubik = new RubikRight(newRubik).right();
                    break;
                case "R'":
                    newRubik = new RubikRight(newRubik).left();
                    break;
                case "R2":
                    newRubik = new RubikRight(newRubik).half();
                    break;
                case "U":
                    newRubik = new RubikUp(newRubik).right();
                    break;
                case "U'":
                    newRubik = new RubikUp(newRubik).left();
                    break;
                case "U2":
                    newRubik = new RubikUp(newRubik).half();
                    break;
                case "L":
                    newRubik = new RubikLeft(newRubik).right();
                    break;
                case "L'":
                    newRubik = new RubikLeft(newRubik).left();
                    break;
                case "L2":
                    newRubik = new RubikLeft(newRubik).half();
                    break;
                case "B":
                    newRubik = new RubikBack(newRubik).right();
                    break;
                case "B'":
                    newRubik = new RubikBack(newRubik).left();
                    break;
                case "B2":
                    newRubik = new RubikBack(newRubik).half();
                    break;
                case "D":
                    newRubik = new RubikDown(newRubik).right();
                    break;
                case "D'":
                    newRubik = new RubikDown(newRubik).left();
                    break;
                case "D2":
                    newRubik = new RubikDown(newRubik).half();
                    break;
                case "F":
                    newRubik = new Rubik(newRubik).right();
                    break;
                case "F'":
                    newRubik = new Rubik(newRubik).left();
                    break;
                case "F2":
                    newRubik = new Rubik(newRubik).half();
                    break;
            }
        }

        return newRubik;
    }
}
