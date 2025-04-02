package co.mp.fixture;

import java.util.Comparator;
import java.util.StringJoiner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface BadExamples {
    // TODO integrate these tests correctly so that example generation works

    interface Confserver45910 {
        class LongTaskStatus {
            private final Message name;

            public LongTaskStatus(Message name) {
                this.name = name;
            }

            Message getName() {
                return name;
            }

            @Override
            public String toString() {
                return new StringJoiner(", ", LongTaskStatus.class.getSimpleName() + "[", "]")
                        .add("name=" + name)
                        .toString();
            }
        }

        record Message(String key) { }

        /**
         * This comparator is incorrect because it does not return 0 when both
         * {@link LongTaskStatus#name} properties are null. A simple way to
         * make this comparator compliant with the contact would be to
         * check both names at the head of the compare method.
         * <pre>{@code
         * if (o1Name == null && o2Name == null) {
         *     return 0;
         * }
         * }</pre>
         */
        class LongTaskStatusComparator implements Comparator<LongTaskStatus> {
            @Override
            public int compare(LongTaskStatus o1, LongTaskStatus o2) {
                // Sorting by name is not as good as sorting by start time (unavailable) but better than anything
                // else we can think of this iteration. Changing this order later is unable to raise any blockers
                // in production.
                Message o1Name = o1.getName();
                if (o1Name == null || o1Name.key() == null) {
                    return 1;  // push o1 to bottom
                }

                Message o2Name = o2.getName();
                if (o2Name == null || o2Name.key() == null) {
                    return -1;  // push o2 to bottom
                }

                return o1Name.key().compareTo(o2Name.key());
            }
        }
    }

    interface Cho2056 {
        final class LineComparator implements Comparator<Line> {

            private static final PointComparator POINT_COMPARATOR = new PointComparator();

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

        final class Line {
            private final int x;
            private final int lowerY;
            private final int upperY;

            Line(int x, int lowerY, int upperY) {
                this.x = x;
                this.lowerY = lowerY;
                this.upperY = upperY;
            }

            public Stream<Point> points() {
                return IntStream.rangeClosed(lowerY(), upperY())
                        .mapToObj(y -> new Point(x, y));
            }

            public int x() {
                return x;
            }

            public int lowerY() {
                return lowerY;
            }

            public int upperY() {
                return upperY;
            }

            @Override
            public String toString() {
                return new StringJoiner(", ", Line.class.getSimpleName() + "[", "]")
                        .add("x=" + x)
                        .add("lowerY=" + lowerY)
                        .add("upperY=" + upperY)
                        .toString();
            }
        }

        final class PointComparator implements Comparator<Point> {

            private static final Comparator<Point> COMPARATOR = Comparator.comparingInt(Point::x).thenComparingInt(Point::y);
            @Override
            public int compare(Point px, Point py) {
                return COMPARATOR.compare(px, py);
            }
        }

        final class Point {
            private final int x;
            private final int y;

            Point(int x, int y) {
                this.x = x;
                this.y = y;
            }

            public int x() {
                return x;
            }

            public int y() {
                return y;
            }

            @Override
            public String toString() {
                return new StringJoiner(", ", Point.class.getSimpleName() + "[", "]")
                        .add("x=" + x)
                        .add("y=" + y)
                        .toString();
            }
        }
    }
}
