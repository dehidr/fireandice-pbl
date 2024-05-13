public class NPC extends Character{
    public static int score = 0;

    public Coordinate getTarget() {
        return target;
    }

    public void setTarget(Coordinate target) {
        this.target.set(target);
    }

    Coordinate target;

    public boolean isStuck() {
        return stuck;
    }

    public void setStuck(boolean stuck) {
        this.stuck = stuck;
    }

    public void score (int pts) { score += pts; }
    public void setScore(int score) { this.score = score; }
    public int getScore() { return score; }

    boolean stuck = false;

    public NPC(){
        super(GameField.getInstance().getBlank(), Type.NPC, Direction.UP, 1000, 0);
        setCoordinate( GameField.getInstance().getBlank() );
        this.life    = 1000;
        target = new Coordinate(coordinate);
    }

    public NPC(Coordinate c, int initialLife) {
        super(c, Type.NPC, Direction.UP, 1000, 0);
        setCoordinate(c);
        this.life = initialLife;
        target = new Coordinate(coordinate);

    }
}
