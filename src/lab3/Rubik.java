package lab3;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Rubik implements Cloneable, SideViewable {
    private final ArrayList<Face> faces;

    Rubik(int[][][] grid) {
        final ArrayList<Face> gridArrayList = new ArrayList<>(grid.length);

        for (int[][] gridFace : grid) {
            gridArrayList.add(new Face(gridFace));
        }

        this.faces = gridArrayList;
    }

    Rubik(Rubik rubik) {
        this(rubik.clone().getFaces());
    }

    protected Rubik(ArrayList<Face> faces) {
        this.faces = Rubik.cloneFaces(faces);
    }

    public ArrayList<Face> getFaces() {
        return Rubik.cloneFaces(this.faces);
    }

    @Override
    public final Rubik clone() {
        return new Rubik(this.getFaces());
    }

    public Rubik left() {
        final Rubik rubikClone = this.clone();
        final ArrayList<Face> rubikFaces = rubikClone.getFaces();
        // turn the middle/third face to left
        final Face newThirdSide = rubikFaces.get(2).left();

        final Face firstSide = rubikFaces.get(0);
        final int[] firstSideBottomRow = firstSide.getRow(2);
        final int[] firstSideBottomRowReversed = Rubik.reverseIntArray(firstSideBottomRow);

        final Face secondSide = rubikFaces.get(1);
        final int[] secondSideLastColumn = secondSide.getColumn(2);

        final Face fifthSide = rubikFaces.get(4);
        final int[] fifthSideFirstRow = fifthSide.getRow(0);
        final int[] fifthSideFirstRowReversed = Rubik.reverseIntArray(fifthSideFirstRow);

        final Face fourthSide = rubikFaces.get(3);
        final int[] fourthSideFirstColumn = fourthSide.getColumn(0);

        // turn all 4 sides - first, second, fourth and fifth

        // first side - move bottom row to second side's last column
        // reverse the order
        final Face newSecondSide = secondSide.setColumn(2, firstSideBottomRowReversed);

        // second side - move last column to fifth side's first row
        // do not reverse the order
        final Face newFifthSide = fifthSide.setRow(0, secondSideLastColumn);

        // fifth side - move first row to fourth side's first column
        // reverse the order
        final Face newFourthSide = fourthSide.setColumn(0, fifthSideFirstRowReversed);

        // fourth side - move first column to first side's last row
        // do not reverse the order
        final Face newFirstSide = firstSide.setRow(2, fourthSideFirstColumn);

        rubikFaces.set(0, newFirstSide);
        rubikFaces.set(1, newSecondSide);
        rubikFaces.set(2, newThirdSide);
        rubikFaces.set(3, newFourthSide);
        rubikFaces.set(4, newFifthSide);

        return new Rubik(rubikFaces);
    }

    public Rubik right() {
        return this.left().left().left();
    }

    public Rubik half() {
        return this.left().left();
    }

    public final Rubik rightView() {
        final Rubik rubikClone = this.clone();
        final ArrayList<Face> rubikFaces = rubikClone.getFaces();

        final Face firstSide = rubikFaces.get(0).clone();
        final Face secondSide = rubikFaces.get(1).clone();
        final Face thirdSide = rubikFaces.get(2).clone();
        final Face fourthSide = rubikFaces.get(3).clone();
        final Face fifthSide = rubikFaces.get(4).clone();
        final Face sixthSide = rubikFaces.get(5).clone();

        final Face rotatedFirstSide = firstSide.right();
        final Face rotatedSecondSide = secondSide.half();
        final Face rotatedFifthSide = fifthSide.left();
        final Face rotatedSixthSide = sixthSide.half();

        rubikFaces.set(0, rotatedFirstSide);
        rubikFaces.set(1, thirdSide);
        rubikFaces.set(2, fourthSide);
        rubikFaces.set(3, rotatedSixthSide);
        rubikFaces.set(4, rotatedFifthSide);
        rubikFaces.set(5, rotatedSecondSide);

        return new Rubik(rubikFaces);
    }

    public final Rubik leftView() {
        return this.rightView().rightView().rightView();
    }

    public final Rubik upView() {
        final Rubik rubikClone = this.clone();
        final ArrayList<Face> rubikFaces = rubikClone.getFaces();

        final Face firstSide = rubikFaces.get(0).clone();
        final Face secondSide = rubikFaces.get(1).clone();
        final Face thirdSide = rubikFaces.get(2).clone();
        final Face fourthSide = rubikFaces.get(3).clone();
        final Face fifthSide = rubikFaces.get(4).clone();
        final Face sixthSide = rubikFaces.get(5).clone();

        final Face rotatedSecondSide = secondSide.right();
        final Face rotatedFourthSide = fourthSide.left();

        rubikFaces.set(0, sixthSide);
        rubikFaces.set(1, rotatedSecondSide);
        rubikFaces.set(2, firstSide);
        rubikFaces.set(3, rotatedFourthSide);
        rubikFaces.set(4, thirdSide);
        rubikFaces.set(5, fifthSide);

        return new Rubik(rubikFaces);
    }

    public final Rubik downView() {
        return this.upView().upView().upView();
    }

    public final Rubik backView() {
        return this.rightView().rightView();
    }

    public final Rubik frontView() {
        return this.clone();
    }

    private static ArrayList<Face> cloneFaces(ArrayList<Face> faces) {
        final ArrayList<Face> faceClone = new ArrayList<>(faces.size());
        for (Face face : faces) {
            faceClone.add(face.clone());
        }
        return faceClone;
    }

    private static int[] reverseIntArray(int[] intArray) {
        return IntStream.rangeClosed(1, intArray.length).map(i -> intArray[intArray.length - i]).toArray();
    }

    /**
     * TODO: Tidy up the whole mess
     *
     * @return String
     */
    @Override
    public final String toString() {
        final int MAX_ROWS = 12;
        final ArrayList<StringBuilder> rowsOfStrings = new ArrayList<>(MAX_ROWS);
        final ArrayList<Face> faces = this.getFaces();

        final int FIRST_FACE_INDEX = 0;
        final int SECOND_FACE_INDEX = 1;
        final int FIFTH_FACE_INDEX = 4;
        final int SIXTH_FACE_INDEX = 5;
        int currentRow = 0;

        for (int i = 0; i < faces.size(); i++) {
            final Face face = faces.get(i);
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
