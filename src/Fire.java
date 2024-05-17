import java.awt.*;

public class Fire extends GameObject {

    int health = 10;
    int spread = 10;

    int index;

    boolean used = false;
    public Fire[] spread() {
        Fire[] children = new Fire[4];
        if (!used) {
            int s = this.spread - 1;
            children[0] = new Fire(this.getCoordinate().getLeft(), 10, s);
            children[1] = new Fire(this.getCoordinate().getRight(), 10, s);
            children[2] = new Fire(this.getCoordinate().getUp(), 10, s);
            children[3] = new Fire(this.getCoordinate().getDown(), 10, s);
        }
        return children;
    }

    public Fire(Coordinate loc, int health, int spread) {
        super(loc, Type.FIRE);
        this.health = health;
        this.spread = spread;
        this.used = false;
    }

    public void setUsed(boolean used) {this.used = used;}

    public int getIndex() { return index; }

    public void setIndex(int index) { this.index = index; }

    public Fire() {
        super(Type.FIRE);
        this.health = 10;
        this.spread = 10;
        this.used = false;
    }

    public boolean isUsed() {return used;}

    public int getHealth() { return health; }

    public void setHealth(int health) {this.health = health;}

    public int getSpread() {return spread;}

    public void setSpread(int spread) {this.spread = spread;}

    public void fightFire() {health--;}

    public void fightFire(int dmg) {
        try {

            if (this.type.equals(Type.FIRE) || this.type.equals(Type.ICE))
                health -= dmg;
        } catch (Exception e) {}
    }


}
