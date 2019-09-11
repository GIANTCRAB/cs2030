package lab3;

import java.util.ArrayList;

public class Rubik implements Cloneable {
    private final ArrayList<Face> faces;

    Rubik(int[][][] grid) {
        this.faces = new ArrayList<>(grid.length);

        for (int[][] gridFace : grid) {
            this.faces.add(new Face(gridFace));
        }
    }

    private Rubik(ArrayList<Face> faces) {
        this.faces = faces;
    }

    @Override
    public Rubik clone() {
        final ArrayList<Face> faceClone = new ArrayList<>(this.faces.size());
        for (Face face : this.faces) {
            faceClone.add(face.clone());
        }
        return new Rubik(faceClone);
    }

    /**
     * TODO: Tidy up the whole mess
     *
     * @return String
     */
    @Override
    public String toString() {
        final int MAX_ROWS = 12;
        final ArrayList<StringBuilder> rowsOfStrings = new ArrayList<>(MAX_ROWS);

        final int FIRST_FACE_INDEX = 0;
        final int SECOND_FACE_INDEX = 1;
        final int FIFTH_FACE_INDEX = 4;
        final int SIXTH_FACE_INDEX = 5;
        int currentRow = 0;

        for (int i = 0; i < this.faces.size(); i++) {
            final Face face = this.faces.get(i);
            final int[][] faceIntArray = face.toIntArray();

            for (int x = 0; x < faceIntArray.length; x++) {
                if (i == FIRST_FACE_INDEX || i == FIFTH_FACE_INDEX || i == SIXTH_FACE_INDEX) {
                    switch (i) {
                        case FIRST_FACE_INDEX:
                            currentRow = x;
                            break;
                        case FIFTH_FACE_INDEX:
                            currentRow = 6 + x;
                            break;
                        case SIXTH_FACE_INDEX:
                            currentRow = 9 + x;
                            break;
                    }
                    final StringBuilder formattedString = new StringBuilder();
                    for (int y = 0; y < faceIntArray[x].length; y++) {
                        formattedString.append(String.format("%02d", faceIntArray[x][y]));
                    }
                    formattedString.insert(0, "......");
                    formattedString.append("......");
                    rowsOfStrings.add(currentRow, formattedString);
                } else {
                    currentRow = 3 + x;
                    StringBuilder formattedString = new StringBuilder();
                    if (i != SECOND_FACE_INDEX) {
                        formattedString = rowsOfStrings.get(currentRow);
                    }
                    for (int y = 0; y < faceIntArray[x].length; y++) {
                        formattedString.append(String.format("%02d", faceIntArray[x][y]));
                    }
                    if (i != SECOND_FACE_INDEX) {
                        rowsOfStrings.set(currentRow, formattedString);
                    } else {
                        rowsOfStrings.add(currentRow, formattedString);
                    }
                }
            }
        }

        final StringBuilder combinedString = new StringBuilder();
        combinedString.append("\n");
        for (StringBuilder rowOfString : rowsOfStrings) {
            combinedString.append(rowOfString.toString());
            combinedString.append("\n");
        }

        return combinedString.toString();
    }
}
