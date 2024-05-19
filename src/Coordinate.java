public class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate c) {
        this.x = c.getX();
        this.y = c.getY();
    }

    public static Double distance(Coordinate start, Coordinate finish) {
        Double a = Double.valueOf(start.getX() - finish.getX());
        Double b = Double.valueOf(start.getY() - finish.getY());
        return Math.sqrt(a * a + b * b);
    }

    public static Double angle(Coordinate c1, Coordinate c2) {
        Double angle = 0.0;

        /*Double tan = (double) (c2.getY() - c1.getY())
                   / (double) (c2.getY() - c1.getY());

        Double cot = (double) (c2.getX() - c1.getX())
                   / (double) (c2.getY() - c1.getY());*/

        angle = Math.toDegrees(Math.atan2((double) (c2.getY() - c1.getY()), (double) (c2.getY() - c1.getY())));
        if (angle < 0) {angle += 360.0;}

        return angle;
    }

    public static Coordinate[] path(Coordinate start, Coordinate finish) {

        /*
        bresenham's line algorithm
        https://www.geeksforgeeks.org/bresenhams-line-generation-algorithm/
        */

        int x0 = start.getX();
        int y0 = start.getY();
        int x1 = finish.getX();
        int y1 = finish.getY();

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        int i = 0;
        int steps = Math.max(dx, dy);

        Coordinate[] path = new Coordinate[steps];
        path[i++] = (new Coordinate(x0, y0));

        while ((x0 != x1 || y0 != y1) && i < steps) {
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
            path[i++] = (new Coordinate(x0, y0));
        }

        return path;
    }

    public int getX() {return x;}

    public void setX(int x) {this.x = x;}

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(Coordinate c) {
        this.x = c.getX();
        this.y = c.getY();
    }

    public int getY() {return y;}

    public void setY(int y) {this.y = y;}

    public void moveUp() {y--;}

    public void moveDown() {y++;}

    public void moveLeft() {x--;}

    public void moveRight() {x++;}

    public Coordinate getUp() {return new Coordinate(x, y - 1);}

    public Coordinate getDown() {return new Coordinate(x, y + 1);}

    public Coordinate getLeft() {return new Coordinate(x - 1, y);}

    public Coordinate getRight() {return new Coordinate(x + 1, y);}

    public String toString() {
        return "(" + String.format("%2s", x) + "," + String.format("%2s", y) + ")";
    }

    public static String toString(int x, int y) {
        return "(" + String.format("%2s", x) + "," + String.format("%2s", y) + ")";
    }

    public String toString(Coordinate c) {
        return "(" + String.format("%2s", c.getX()) + "," + String.format("%2s", c.getY()) + ")";
    }
}
