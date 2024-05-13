public class Ice extends GameObject {
    Character.Direction direction;
    int health = 10;
    int spread = 5;

    public Ice(Coordinate loc, Character.Direction dir, int health, int spread) {
        super(loc, Type.ICE);
        this.direction = dir;
        this.health    = health;
        this.spread    = spread;
    }

    public Character.Direction getDir() { return direction; }
    public void setDir(Character.Direction dir) { this.direction = dir; }
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public int getSpread() { return spread; }
    public void setSpread(int spread) { this.spread = spread; }
}
