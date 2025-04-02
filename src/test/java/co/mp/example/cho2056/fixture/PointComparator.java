package co.mp.example.cho2056.fixture;

import java.util.Comparator;

final class PointComparator implements Comparator<Point> {

    private static final Comparator<Point> COMPARATOR = Comparator.comparingInt(Point::x).thenComparingInt(Point::y);
    @Override
    public int compare(Point px, Point py) {
        return COMPARATOR.compare(px, py);
    }
}
