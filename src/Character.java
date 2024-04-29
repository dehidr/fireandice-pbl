public abstract class Character {
    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    protected int x;
    protected int y;

    protected int life;
    protected int score;

    public void setScore(int score) { this.score = score; }
    public void setLife(int life) { this.life = life; }
    public void setY(int y) { this.y = y; }
    public void setX(int x) { this.x = x; }

    public int getScore() { return score; }
    public int getLife() { return life; }
    public int getX() { return x; }
    public int getY() { return y; }

    public void damage(int dmg) { life  -= dmg; }
    public void score (int pts) { score += pts; }

    public void move(Direction dir){
        switch (dir){
            case UP     : { y--; break; }
            case DOWN   : { y++; break; }
            case LEFT   : { x--; break; }
            case RIGHT  : { x++; break; }
        }
    }

    public void moveUp()    { y--; }
    public void moveDown()  { y++; }
    public void moveLeft()  { x--; }
    public void moveRight() { x++; }
}