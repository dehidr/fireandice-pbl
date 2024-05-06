public class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate c){
        this.x = c.getX();
        this.y = c.getY();
    }

    public static Double distance(Coordinate c1, Coordinate c2){
        Double a = Double.valueOf(c1.getX() -  c2.getX());
        Double b = Double.valueOf(c1.getY() -  c2.getY());
        return Math.sqrt(a*a + b*b);
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public void set(int x, int y) { this.x = x; this.y = y; }
    public void set(Coordinate c) { this.x = c.getX(); this.y = c.getY(); }

    public int getY() { return y; }
    public void setY(int y) { this.y = y;}

    public void moveUp()    { y--; }
    public void moveDown()  { y++; }
    public void moveLeft()  { x--; }
    public void moveRight() { x++; }

    public Coordinate getUp()    { return new Coordinate(x, y-1); }
    public Coordinate getDown()  { return new Coordinate(x, y+1); }
    public Coordinate getLeft()  { return new Coordinate(x-1, y); }
    public Coordinate getRight() { return new Coordinate(x+1, y); }

    public String  toString(){
        return "(" + x + ", " + y + ")";
    }
}
