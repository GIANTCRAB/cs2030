package lab0;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        final int numberOfInputsToRead = 5;
        ArrayList<Double> inputs = new ArrayList<>();

        for (int i = 0; i < numberOfInputsToRead; i++) {
            inputs.add(reader.nextDouble());
        }

        reader.close();

        Point firstPoint = new Point(inputs.get(0), inputs.get(1));
        Point secondPoint = new Point(inputs.get(2), inputs.get(3));
        double radius = inputs.get(4);

        Circle createdCircle = Main.createCircle(firstPoint, secondPoint, radius);

        if (createdCircle == null) {
            System.out.println("No valid circle can be created");
        } else {
            System.out.println("Created: " + createdCircle.toString());
        }
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
