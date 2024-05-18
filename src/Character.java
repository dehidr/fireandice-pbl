public abstract class Character extends GameObject {
    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    Direction direction;
    protected int life;
    protected int score;

    public Character(Coordinate c, Type t, Direction direction, int life, int score) {
        super(c, t);
        this.direction = direction;
        this.life = life;
        this.score = score;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setLife(int life)   { this.life = life; }

    public int getScore() { return score; }
    public int getLife()  { return life; }

    public void damage(int dmg) { life  -= dmg; }

    public int getX(){ return coordinate.getX(); }
    public int getY(){ return coordinate.getY(); }

    public void move(Direction dir){
        switch (dir){
            case UP     : { coordinate.moveUp(); break; }
            case DOWN   : { coordinate.moveDown(); break; }
            case LEFT   : { coordinate.moveLeft(); break; }
            case RIGHT  : { coordinate.moveRight(); break; }
        }
        this.setDirection(dir);
    }


    public void moveUp()    { coordinate.moveUp()   ; }
    public void moveDown()  { coordinate.moveDown() ; }
    public void moveLeft()  { coordinate.moveLeft() ; }
    public void moveRight() { coordinate.moveRight(); }
}