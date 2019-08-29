package lab1;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final ArrayList<Point> pointArrayList = Main.readInputs();
        final double searchRadius = 1;

        final int maxDiscCoverage = Main.parsePoints(pointArrayList, searchRadius);

        System.out.println("Maximum Disc Coverage: " + maxDiscCoverage);
    }

    private static ArrayList<Point> readInputs() {
        Scanner reader = new Scanner(System.in);
        final int numberOfInputsToRead = reader.nextInt();
        ArrayList<Point> pointArrayList = new ArrayList<>();

        for (int i = 0; i < numberOfInputsToRead; i++) {
            pointArrayList.add(new Point(reader.nextDouble(), reader.nextDouble()));
        }

        reader.close();

        return pointArrayList;
    }

    private static int parsePoints(ArrayList<Point> pointArrayList, double searchRadius) {
        int max = 0;

        for (int i = 0; i < pointArrayList.size() - 1; i++) {
            for (int j = i + 1; j < pointArrayList.size(); j++) {
                int dc = Main.discCoverage(pointArrayList, pointArrayList.get(i), pointArrayList.get(j), searchRadius);
                if (dc > max) {
                    max = dc;
                }
            }
        }

        return max;
    }

    private static int discCoverage(ArrayList<Point> pointArrayList, Point firstPoint, Point secondPoint, double searchRadius) {
        final double searchRange = searchRadius * 2;
        int firstCircleCoverage = 0;
        int secondCircleCoverage = 0;

        if (firstPoint.distanceTo(secondPoint) <= searchRange) {
            Circle firstCircle = createCircle(firstPoint, secondPoint, searchRadius);
            Circle secondCircle = createCircle(secondPoint, firstPoint, searchRadius);
            for (int i = 0; i < pointArrayList.size(); i++) {
                final Point point = pointArrayList.get(i);
                final Circle pointCircle = Circle.getCircle(point, searchRadius);
                if (firstCircle.intersects(pointCircle)) {
                    firstCircleCoverage++;
                }

                if (secondCircle.intersects(pointCircle)) {
                    secondCircleCoverage++;
                }
            }
        }

        return (firstCircleCoverage >= secondCircleCoverage) ? firstCircleCoverage : secondCircleCoverage;
    }


    public static Circle createCircle(Point firstPoint, Point secondPoint, double radius) {
        final Point midPoint = firstPoint.midPoint(secondPoint);
        final double angleFromFirstPointToSecondPoint = firstPoint.angleTo(secondPoint);
        final double distanceFromFirstPoint = firstPoint.distanceTo(secondPoint);

        if (distanceFromFirstPoint > radius * 2 || distanceFromFirstPoint == 0) {
            return null;
        }

        // Turn left by subtracting half pi
        final double angleFromMidPointToLeftCircle = angleFromFirstPointToSecondPoint + (Math.PI / 2);

        final double adjacent = distanceFromFirstPoint / 2;
        final double distanceToMove = Math.sqrt((radius * radius) - (adjacent * adjacent));

        final Point newPoint = midPoint.moveTo(angleFromMidPointToLeftCircle, distanceToMove);

        return Circle.getCircle(newPoint, radius);
    }
}
