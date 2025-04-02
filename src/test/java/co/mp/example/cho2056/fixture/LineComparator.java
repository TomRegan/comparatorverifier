package co.mp.example.cho2056.fixture;

import java.util.Comparator;

public class LineComparator implements Comparator<Line> {

    private final PointComparator POINT_COMPARATOR = new PointComparator();

    @Override
    public int compare(Line l1, Line l2) {
        var p1 = l1.points().min(POINT_COMPARATOR);
        var p2 = l2.points().min(POINT_COMPARATOR);
        if (p1.isEmpty()) {
            return 1;
        }
        if (p2.isEmpty()) {
            return -1;
        }
        return POINT_COMPARATOR.compare(p1.get(), p2.get());
    }
}
