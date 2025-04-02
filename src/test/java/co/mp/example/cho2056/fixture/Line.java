package co.mp.example.cho2056.fixture;

import java.util.StringJoiner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class Line {
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
