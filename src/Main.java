public class Main {
    static Game myGame;
    public static void main(String[] args) throws Exception {
        myGame = new Game();
    }

    public static void restart() throws Exception {
        myGame = null;
        GameField.restart();
        NPC.resetScore();
        System.gc();
        myGame = new Game();
    }
}
