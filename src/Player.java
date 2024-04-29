class Player extends Character{

    private static Player player = null;

    public static Player getInstance(){
        if(player == null){
            player = new Player(1,1,1000);
        }
        return player;
    }

    private Player(int initialX, int initialY, int initialLife) {
        this.x = initialX;
        this.y = initialY;
        this.life = initialLife;
    }

}