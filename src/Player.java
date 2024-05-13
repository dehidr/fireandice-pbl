class Player extends Character{

    private static Player player = null;
    int packedIce = 0;

    public static Player getInstance(){
        if(player == null){
            player = new Player();
        }
        return player;
    }

    public void score (int pts) { score += pts; }
    public void setScore(int score) { this.score = score; }
    public int getScore() { return score; }


    private Player() {
        super(GameField.getInstance().getBlank(), Type.PLAYER, Direction.UP, 1000, 0);
        this.life = 1000;
        this.score = 0;
    }

    private Player(int initialX, int initialY, int initialLife) {
        super(new Coordinate(initialX, initialY), Type.PLAYER, Direction.UP, initialLife, 0);
    }

}