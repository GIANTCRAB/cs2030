package lab9;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

class MatrixMultiplication extends RecursiveTask<Matrix> {

    /**
     * The fork threshold.
     */
    private static final int FORK_THRESHOLD = 128;

    /**
     * The first matrix to multiply with.
     */
    private Matrix m1;

    /**
     * The second matrix to multiply with.
     */
    private Matrix m2;

    /**
     * The starting row of m1.
     */
    private int m1Row;

    /**
     * The starting col of m1.
     */
    private int m1Col;

    /**
     * The starting row of m2.
     */
    private int m2Row;

    /**
     * The starting col of m2.
     */
    private int m2Col;

    /**
     * The dimension of the input (sub)-matrices and the size of the output
     * matrix.
     */
    private int dimension;

    /**
     * A constructor for the Matrix Multiplication class.
     *
     * @param m1        The matrix to multiply with.
     * @param m2        The matrix to multiply with.
     * @param m1Row     The starting row of m1.
     * @param m1Col     The starting col of m1.
     * @param m2Row     The starting row of m2.
     * @param m2Col     The starting col of m2.
     * @param dimension The dimension of the input (sub)-matrices and the size
     *                  of the output matrix.
     */
    MatrixMultiplication(Matrix m1, Matrix m2, int m1Row, int m1Col, int m2Row,
                         int m2Col, int dimension) {
        this.m1 = m1;
        this.m2 = m2;
        this.m1Row = m1Row;
        this.m1Col = m1Col;
        this.m2Row = m2Row;
        this.m2Col = m2Col;
        this.dimension = dimension;
    }


    @Override
    public Matrix compute() {
        if (this.dimension <= MatrixMultiplication.FORK_THRESHOLD) {
            return Matrix.nonRecursiveMultiply(this.m1, this.m2, this.m1Row, this.m1Col, this.m2Row, this.m2Col, this.dimension);
        }

        int size = dimension / 2;
        Matrix result = new Matrix(dimension);
        final List<MatrixMultiplication> subtasks = new CopyOnWriteArrayList<>();
        final List<MatrixMultiplication> subtasks2 = new CopyOnWriteArrayList<>();
        final List<MatrixMultiplication> subtasks3 = new CopyOnWriteArrayList<>();
        final List<MatrixMultiplication> subtasks4 = new CopyOnWriteArrayList<>();
        final MatrixMultiplication a11b11Thread = new MatrixMultiplication(m1, m2, m1Row, m1Col, m2Row, m2Col, size);
        final MatrixMultiplication a12b21Thread = new MatrixMultiplication(m1, m2, m1Row, m1Col + size, m2Row + size, m2Col, size);
        subtasks.add(a11b11Thread);
        subtasks.add(a12b21Thread);
        final MatrixMultiplication a11b12Thread = new MatrixMultiplication(m1, m2, m1Row, m1Col, m2Row, m2Col + size, size);
        final MatrixMultiplication a12b12Thread = new MatrixMultiplication(m1, m2, m1Row, m1Col + size, m2Row + size, m2Col + size, size);
        subtasks2.add(a11b12Thread);
        subtasks2.add(a12b12Thread);
        final MatrixMultiplication a21b11Thread = new MatrixMultiplication(m1, m2, m1Row + size, m1Col, m2Row, m2Col, size);
        final MatrixMultiplication a21b21Thread = new MatrixMultiplication(m1, m2, m1Row + size, m1Col + size, m2Row + size, m2Col, size);
        subtasks3.add(a21b11Thread);
        subtasks3.add(a21b21Thread);
        final MatrixMultiplication a21b12Thread = new MatrixMultiplication(m1, m2, m1Row + size, m1Col, m2Row, m2Col + size, size);
        final MatrixMultiplication a22b22Thread = new MatrixMultiplication(m1, m2, m1Row + size, m1Col + size, m2Row + size, m2Col + size, size);
        subtasks4.add(a21b12Thread);
        subtasks4.add(a22b22Thread);

        ForkJoinTask.invokeAll(subtasks)
                .parallelStream()
                .map(ForkJoinTask::join)
                .reduce((matrix, matrix2) -> {
                    for (int i = 0; i < size; i++) {
                        final double[] m1m = matrix.m[i];
                        final double[] m2m = matrix2.m[i];
                        for (int j = 0; j < size; j++) {
                            result.m[i][j] = m1m[j] + m2m[j];
                        }
                    }

                    return result;
                });

        ForkJoinTask.invokeAll(subtasks2)
                .parallelStream()
                .map(ForkJoinTask::join)
                .reduce((matrix, matrix2) -> {
                    for (int i = 0; i < size; i++) {
                        final double[] m1m = matrix.m[i];
                        final double[] m2m = matrix2.m[i];
                        for (int j = 0; j < size; j++) {
                            result.m[i][j + size] = m1m[j] + m2m[j];
                        }
                    }

                    return result;
                });

        ForkJoinTask.invokeAll(subtasks3)
                .parallelStream()
                .map(ForkJoinTask::join)
                .reduce((matrix, matrix2) -> {
                    for (int i = 0; i < size; i++) {
                        final double[] m1m = matrix.m[i];
                        final double[] m2m = matrix2.m[i];
                        for (int j = 0; j < size; j++) {
                            result.m[i + size][j] = m1m[j] + m2m[j];
                        }
                    }

                    return result;
                });

        ForkJoinTask.invokeAll(subtasks4)
                .parallelStream()
                .map(ForkJoinTask::join)
                .reduce((matrix, matrix2) -> {
                    for (int i = 0; i < size; i++) {
                        final double[] m1m = matrix.m[i];
                        final double[] m2m = matrix2.m[i];
                        for (int j = 0; j < size; j++) {
                            result.m[i + size][j + size] = m1m[j] + m2m[j];
                        }
                    }

                    return result;
                });
        return result;
    }
}
