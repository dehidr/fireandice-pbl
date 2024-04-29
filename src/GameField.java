import java.io.File;
import java.util.Scanner;
import java.util.Random;

public class GameField {

    private static GameField field = null;
    public  static Player player = Player.getInstance();
    public static NPC[] npcs = new NPC[]{new NPC(5,5,1000), new NPC(7,7,1000) };

    public static void move(Character.Direction dir){
        switch (dir){
            case UP     -> { if(map[player.getY()-1][player.getX()] != '#'){ player.move(dir); break; } }
            case DOWN   -> { if(map[player.getY()+1][player.getX()] != '#'){ player.move(dir); break; } }
            case LEFT   -> { if(map[player.getY()][player.getX()-1] != '#'){ player.move(dir); break; } }
            case RIGHT  -> { if(map[player.getY()][player.getX()+1] != '#'){ player.move(dir); break; } }
        }
    }

    public static Boolean move(NPC c, Character.Direction dir){
        boolean stuck = c.isStuck();
        switch (dir){
            case UP     -> { if(map[c.getY()-1][c.getX()] != '#'){ c.moveUp()   ; stuck = false; break; } }
            case DOWN   -> { if(map[c.getY()+1][c.getX()] != '#'){ c.moveDown() ; stuck = false; break; } }
            case LEFT   -> { if(map[c.getY()][c.getX()-1] != '#'){ c.moveLeft() ; stuck = false; break; } }
            case RIGHT  -> { if(map[c.getY()][c.getX()+1] != '#'){ c.moveRight(); stuck = false; break; } }
        }
        return !c.isStuck();
    }

    private static char[][] map = new char[23][53];

    public boolean isLoaded() {
        return loaded;
    }

    public int randomInput(){
        Random random = new Random();

        /*   PSCORE, CSCORE, PROB
        * 1  3     , 10    , 5/30
        * 2  10    , 30    , 5/30
        * 3  30    , 90    , 5/30
        * -  -     , -     , 6/30
        * @  -     , -     , 6/30
        * C  100   , -     , 3/30
        * */

        int sel = random.nextInt(30);
        return sel;
    }

    public static synchronized GameField getInstance(){
        if(field == null){
            field = new GameField();
        }
        return field;
    }

    private boolean loaded = false;

    private GameField(){
        importMap();
    }

    public void importMap(){
        int totalRow = 23;
        try {
            File file = new File("test.txt");
            Scanner scanner = new Scanner(file);

            for (int row = 0; scanner.hasNextLine() && row < totalRow; row++) {
                map[row] = scanner.nextLine().toCharArray();
            }

            loaded = true;
        } catch (Exception e) {
            loaded = false;
            System.out.println("could not find file.");
            map = new char[][]{("#####################################################").toCharArray(),
                               ("#                                                   #").toCharArray(),
                               ("#                                                   #").toCharArray(),
                               ("#                                                   #").toCharArray(),
                               ("#                                                   #").toCharArray(),
                               ("#                                                   #").toCharArray(),
                               ("#                                                   #").toCharArray(),
                               ("#       ##  #  ####     #    ##    ##    ####       #").toCharArray(),
                               ("#       ##### ##  ##    ### ###   #  #   #  ##      #").toCharArray(),
                               ("#       #  ## ##  ##    #  #  #  ######  ####       #").toCharArray(),
                               ("#       #   #  ####     #     # ##    ## #          #").toCharArray(),
                               ("#                                                   #").toCharArray(),
                               ("#           ####  ####  #    # ##  # ####           #").toCharArray(),
                               ("#           ###  ##  ## #    # # # # #   #          #").toCharArray(),
                               ("#           #    ##  ## ##  ## #  ## #   #          #").toCharArray(),
                               ("#           #     ####   ####  #   # ####           #").toCharArray(),
                               ("#                                                   #").toCharArray(),
                               ("#                                                   #").toCharArray(),
                               ("#                                                   #").toCharArray(),
                               ("#                                                   #").toCharArray(),
                               ("#                                                   #").toCharArray(),
                               ("#                                                   #").toCharArray(),
                               ("#####################################################").toCharArray()};
        }

    }
    public char[][] getMap() { return map; }
}
