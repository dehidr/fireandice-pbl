import java.io.File;
import java.util.Scanner;

public class GameField {

    private static char[][] map = new char[23][53];

    public boolean isLoaded() {
        return loaded;
    }

    private boolean loaded = false;

    public GameField(){
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
