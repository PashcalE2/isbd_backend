package main.isbd.data;

public class Checker {
    public static boolean isInArea(AreaPoint areaPoint) {
        double x = areaPoint.getX();
        double y = areaPoint.getY();
        double r = areaPoint.getR();

        return  (x >= -r) && (x <= 0) && (y >= 0) && (y <= (x + r) / 2) ||
                (x >= -r) && (x <= 0) && (y >= -r) && (y <= 0) ||
                (x >= 0) && (y <= 0) && (x * x + y * y <= r * r);
    }
}
