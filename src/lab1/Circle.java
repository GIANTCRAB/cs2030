package lab1;

public class Circle {
    private Point point;
    private double radius;

    private Circle(Point point, double radius) {
        this.setPoint(point).setRadius(radius);
    }

    public static Circle getCircle(Point point, double radius) {
        if (Circle.isValidRadius(radius)) {
            return new Circle(point, radius);
        } else {
            return null;
        }

    }

    public Point getPoint() {
        return point;
    }

    public Circle setPoint(Point point) {
        this.point = point;

        return this;
    }

    public double getRadius() {
        return radius;
    }

    public Circle setRadius(double radius) {
        if (Circle.isValidRadius(radius)) {
            this.radius = radius;

            return this;
        } else {
            return null;
        }
    }

    public boolean intersects(Circle secondCircle) {
        final Point secondCirclePoint = secondCircle.getPoint();

        return this.getPoint().distanceTo(secondCirclePoint) <= this.getRadius();
    }

    private static boolean isValidRadius(double radius) {
        return radius > 0;
    }

    @Override
    public String toString() {
        return "circle of radius " + this.getRadius() + " centered at " + this.getPoint().toString();
    }
}
