package lab1;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.setX(x).setY(y);
    }

    public double getX() {
        return x;
    }

    public Point setX(double x) {
        this.x = x;

        return this;
    }

    public double getY() {
        return y;
    }

    public Point setY(double y) {
        this.y = y;

        return this;
    }

    public Point midPoint(Point secondPoint) {
        double midX = (this.getX() + secondPoint.getX()) / 2;
        double midY = (this.getY() + secondPoint.getY()) / 2;

        return new Point(midX, midY);
    }

    public double distanceTo(Point secondPoint) {
        return Math.hypot(this.getX() - secondPoint.getX(), this.getY() - secondPoint.getY());
    }

    public double angleTo(Point secondPoint) {
        return Math.atan2((secondPoint.getY() - this.getY()), (secondPoint.getX() - this.getX()));
    }

    public Point moveTo(double angle, double distance) {
        Point newPoint = new Point(distance * Math.cos(angle), distance * Math.sin(angle));

        return this.setX(this.getX() + newPoint.getX())
                .setY(this.getY() + newPoint.getY());
    }

    @Override
    public String toString() {
        return String.format("point (%,.3f, %,.3f)", this.getX(), this.getY());
    }
}
