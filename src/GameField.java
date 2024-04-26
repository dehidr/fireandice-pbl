import java.io.File;
import java.util.Scanner;

public class GameField {

    private static GameField field = null;
    public  static Player player = Player.getInstance();

    public static void move(Player.Direction dir){
        switch (dir){
            case UP     -> { if(map[player.getY()-1][player.getX()] != '#'){ player.move(dir); break; } }
            case DOWN   -> { if(map[player.getY()+1][player.getX()] != '#'){ player.move(dir); break; } }
            case LEFT   -> { if(map[player.getY()][player.getX()-1] != '#'){ player.move(dir); break; } }
            case RIGHT  -> { if(map[player.getY()][player.getX()+1] != '#'){ player.move(dir); break; } }
        }
    }

    private static char[][] map = new char[23][53];

    public boolean isLoaded() {
        return loaded;
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
