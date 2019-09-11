package lab3;

public class Face implements Cloneable {
    private final int[][] grid;

    Face(int[][] grid) {
        this.grid = grid;
    }

    @Override
    public final Face clone() {
        return new Face(this.toIntArray());
    }

    public final Face right() {
        final int[][] newGrid = {
                {
                        this.grid[2][0],
                        this.grid[1][0],
                        this.grid[0][0],
                },
                {
                        this.grid[2][1],
                        this.grid[1][1],
                        this.grid[0][1],
                },
                {
                        this.grid[2][2],
                        this.grid[1][2],
                        this.grid[0][2],
                }
        };

        return new Face(newGrid);
    }

    public final Face left() {
        final int[][] newGrid = {
                {
                        this.grid[0][2],
                        this.grid[1][2],
                        this.grid[2][2],
                },
                {
                        this.grid[0][1],
                        this.grid[1][1],
                        this.grid[2][1],
                },
                {
                        this.grid[0][0],
                        this.grid[1][0],
                        this.grid[2][0],
                }
        };

        return new Face(newGrid);
    }

    public final Face half() {
        final int[][] newGrid = {
                {
                        this.grid[2][2],
                        this.grid[2][1],
                        this.grid[2][0],
                },
                {
                        this.grid[1][2],
                        this.grid[1][1],
                        this.grid[1][0],
                },
                {
                        this.grid[0][2],
                        this.grid[0][1],
                        this.grid[0][0],
                }
        };

        return new Face(newGrid);
    }

    public final int[][] toIntArray() {
        final int ROWS = this.grid.length;
        final int COLS = this.grid[0].length;
        final int[][] intClone = new int[ROWS][COLS];
        for (int row = 0; row < ROWS; row++) {
            System.arraycopy(this.grid[row], 0, intClone[row], 0, COLS);
        }

        return intClone;
    }

    public final int[] getRow(int rowNumber) {
        final int[][] intArray = this.toIntArray();

        return intArray[rowNumber];
    }

    public final Face setRow(int rowNumber, int[] rowValues) {
        int[][] intArray = this.toIntArray();

        intArray[rowNumber] = rowValues;

        return new Face(intArray);
    }

    public final int[] getColumn(int colNumber) {
        final int[][] intArray = this.toIntArray();
        int[] columnValues = new int[intArray.length];

        for (int i = 0; i < intArray.length; i++) {
            columnValues[i] = intArray[i][colNumber];
        }

        return columnValues;
    }

    public final Face setColumn(int colNumber, int[] colValues) {
        int[][] intArray = this.toIntArray();

        for (int i = 0; i < intArray.length; i++) {
            intArray[i][colNumber] = colValues[i];
        }


        return new Face(intArray);
    }

    @Override
    public final String toString() {
        StringBuilder formattedString = new StringBuilder();

        for (int[] ints : this.grid) {
            formattedString.append("\n");
            for (int anInt : ints) {
                formattedString.append(String.format("%02d", anInt));
            }
        }
        formattedString.append("\n");
        return formattedString.toString();
    }
}
