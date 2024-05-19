public class Ice extends GameObject {
    Character.Direction direction;
    int health = 10;
    int spread = 5;

    boolean used = false;

    int index;

    public Ice(Coordinate loc, int health, int spread, int index) {
        super(loc, Type.ICE);
        this.health    = health;
        this.spread    = spread;
        this.index = index;
    }

    public Ice(Coordinate loc, Character.Direction dir, int health, int spread, int index) {
        super(loc, Type.ICE);
        this.direction = dir;
        this.health    = health;
        this.spread    = spread;
        this.index = index;
    }

    public Ice(Coordinate loc, int health, int spread) {
        super(loc, Type.ICE);
        this.health    = health;
        this.spread    = spread;
    }

    public Ice[] spread() {
        Ice[] children = new Ice[4];
        if (!used) {
            int s = this.spread - 1;
            children[0] = new Ice(this.getCoordinate().getLeft(), 10, s);
            children[1] = new Ice(this.getCoordinate().getRight(), 10, s);
            children[2] = new Ice(this.getCoordinate().getUp(), 10, s);
            children[3] = new Ice(this.getCoordinate().getDown(), 10, s);
        }
        return children;
    }

    public Character.Direction getDir() { return direction; }
    public void setDir(Character.Direction dir) { this.direction = dir; }
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public int getSpread() { return spread; }
    public void setSpread(int spread) { this.spread = spread; }
    public void melt(){ health--; }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public boolean isUsed() {
        return used;
    }
    public void setUsed(boolean used) {
        this.used = used;
    }
}
