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
        return this.grid.clone();
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
