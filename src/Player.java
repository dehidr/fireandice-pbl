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

    private Player(){
        this.coordinate = GameField.getInstance().getBlank();
        this.life = 1000;
    }

    private Player(int initialX, int initialY, int initialLife) {
        this.coordinate.setX(initialX);
        this.coordinate.setY(initialY);
        this.life = initialLife;
    }

}