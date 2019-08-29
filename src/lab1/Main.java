package lab1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final ArrayList<Point> pointArrayList = Main.readInputs();
        final double searchRadius = 1;

        final HashMap<Point, Integer> pointIntegerHashMap = Main.parsePoints(pointArrayList, searchRadius);

        // Increment by 1 to count for itself
        final int highestPointCountNearby = Collections.max(pointIntegerHashMap.values()) + 1;

        System.out.println("Maximum Disc Coverage: " + highestPointCountNearby);
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

    private static HashMap<Point, Integer> parsePoints(ArrayList<Point> pointArrayList, double searchRadius) {
        HashMap<Point, Integer> pointIntegerHashMap = new HashMap<>();

        for (int i = 0; i < pointArrayList.size(); i++) {
            final Point parsePoint = pointArrayList.get(i);

            final int foundPoints = Main.searchPointsNearby(parsePoint, pointArrayList, searchRadius);

            pointIntegerHashMap.put(parsePoint, foundPoints);
        }

        return pointIntegerHashMap;
    }

    private static int searchPointsNearby(Point primaryPoint, ArrayList<Point> pointArrayList, double searchRadius) {
        final double searchRange = searchRadius * 2;
        int foundPoints = 0;

        for (int i = 0; i < pointArrayList.size(); i++) {
            final Point searchPoint = pointArrayList.get(i);

            if (primaryPoint.distanceTo(searchPoint) <= searchRange) {
                foundPoints++;
            }
        }

        // Decrement by 1 to prevent double counting self
        return foundPoints - 1;
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
