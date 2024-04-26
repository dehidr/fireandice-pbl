class Player {
    private int x;
    private int y;

    private int life;

    private static Player player = null;

    public static Player getInstance(){
        if(player == null){
            player = new Player(0,0,1000);
        }
        return player;
    }

    private Player(int initialX, int initialY, int initialLife) {
        this.x = initialX;
        this.y = initialY;
        this.life = initialLife;
    }

    public void setLife(int life) { this.life = life; }
    public void setY(int y) { this.y = y; }
    public void setX(int x) { this.x = x; }

    public int getX() { return x; }
    public int getY() { return y; }

    public void moveUp(){ y++; }
    public void moveDown(){ y--; }
    public void moveLeft(){ x--; }
    public void moveRight(){ x++; }

    public int getLife() { return life; }


}