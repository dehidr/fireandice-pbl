import java.awt.*;

public class Fire extends GameObject {
    int health = 10;
    int spread = 10;

    public Fire(Coordinate loc, int health, int spread) {
        this.type = Type.FIRE;
        this.coordinate = loc;
        this.health = health;
        this.spread = spread;
    }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public int getSpread() { return spread; }
    public void setSpread(int spread) { this.spread = spread; }
    public void fightFire(){
        if(this.type.equals(Type.FIRE) || this.type.equals(Type.ICE)) health--;
    }
    public void fightFire(int dmg){
        if(this.type.equals(Type.FIRE) || this.type.equals(Type.ICE)) health -= dmg;
    }


}
