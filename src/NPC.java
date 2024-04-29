public class NPC extends Character{
    public boolean isStuck() {
        return stuck;
    }

    public void setStuck(boolean stuck) {
        this.stuck = stuck;
    }

    boolean stuck = false;
    public NPC(int initialX, int initialY, int initialLife) {
        this.x = initialX;
        this.y = initialY;
        this.life = initialLife;
    }
}
