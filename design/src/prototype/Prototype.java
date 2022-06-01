package prototype;

public class Prototype {
}

class Point {
    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Line {
    public Point start, end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Line deepCopy() {
        return new Line(new Point(start.x, start.y), new Point(end.x, end.y));
    }
}

