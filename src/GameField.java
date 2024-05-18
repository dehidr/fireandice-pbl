import java.io.File;
import java.security.interfaces.EdECKey;
import java.util.Objects;
import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class GameField {

    public static void restart() {
        /*field = null;
        player = null;
        input = null;
        npcs = null;
        fire = null;
        ice = null;
        objects = null;

        System.gc();*/

        Player.restart();
        player = Player.getInstance();
        GameObject.Type[] input = new GameObject.Type[10];
        npcs = new NPC[1071];// 51*21 = 1071
        fire = new Fire[1071];
        ice = new Ice[100];
        objects = new GameObject[21][51];
        npcCtr = 0;
        fireCtr = 0;
        iceCtr = 0;
    }

    private static GameField field = null;
    public static Player player = Player.getInstance();
    public static GameObject.Type[] input = new GameObject.Type[10];
    public static NPC[] npcs = new NPC[1071];// 51*21 = 1071
    public static Fire[] fire = new Fire[1071];
    public static Ice[] ice = new Ice[100];
    public static GameObject[][] objects = new GameObject[21][51];
    public static int npcCtr = 0;
    public static int fireCtr = 0;
    public static int iceCtr = 0;


    public static void move(Character.Direction dir) {
        switch (dir) {
            case UP -> {
                if (map[player.getY() - 1][player.getX()] != '#') {
                    player.move(dir);
                    break;
                }
            }
            case DOWN -> {
                if (map[player.getY() + 1][player.getX()] != '#') {
                    player.move(dir);
                    break;
                }
            }
            case LEFT -> {
                if (map[player.getY()][player.getX() - 1] != '#') {
                    player.move(dir);
                    break;
                }
            }
            case RIGHT -> {
                if (map[player.getY()][player.getX() + 1] != '#') {
                    player.move(dir);
                    break;
                }
            }
        }
    }

    public static void fireCounter(int fire) {
        fireCtr += fire;
        if (fireCtr > 1070) {fireCtr = 1070;}
    }

    public static void iceCounter(int ice) {
        iceCtr += ice;
        if (iceCtr > 99) {iceCtr = 99;}
    }


    public static void npcCouncter(int npc) {
        npcCtr += npc;
        if (npcCtr > 1070) {npcCtr = 1070;}
    }

    public static void moveRandom(NPC npc){
        Random rand = new Random();
        switch(rand.nextInt(4)){
            case 0 -> { move(npc, Character.Direction.UP) ;}
            case 1 -> { move(npc, Character.Direction.DOWN) ;}
            case 2 -> { move(npc, Character.Direction.RIGHT) ;}
            case 3 -> { move(npc, Character.Direction.LEFT) ;}
        }
        npc.setStuckfor(0);
    }


    public static Boolean move(NPC c, Character.Direction dir) {
        boolean stuck = c.isStuck();
        switch (dir) {
            case UP -> {
                if (map[c.getY() - 1][c.getX()] != '#' && field.spaceAvailable(c.getCoordinate().getUp())) {
                    c.moveUp();
                    stuck = false;
                    break;
                }
            }
            case DOWN -> {
                if (map[c.getY() + 1][c.getX()] != '#' && field.spaceAvailable(c.getCoordinate().getDown())) {
                    c.moveDown();
                    stuck = false;
                    break;
                }
            }
            case LEFT -> {
                if (map[c.getY()][c.getX() - 1] != '#' && field.spaceAvailable(c.getCoordinate().getLeft())) {
                    c.moveLeft();
                    stuck = false;
                    break;
                }
            }
            case RIGHT -> {
                if (map[c.getY()][c.getX() + 1] != '#' && field.spaceAvailable(c.getCoordinate().getRight())) {
                    c.moveRight();
                    stuck = false;
                    break;
                }
            }
        }
        return !c.isStuck();
    }

    private static char[][] map = new char[23][53];

    public boolean isLoaded() {
        return loaded;
    }

    public static synchronized GameField getInstance() {
        if (field == null) {
            field = new GameField();
        }
        return field;
    }

    private boolean loaded = false;

    public void initQueue() {
        for (int i = 0; i < 10; i++) {
            input();
        }
    }

    private GameField() {
        importMap();
    }

    public void importMap() {
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
            map = new char[][]{("#####################################################").toCharArray(), ("#          "
                    + "                                         #").toCharArray(), ("#                               "
                    + "  " + "                  #").toCharArray(), ("#                                               " +
                    "  " + "  #").toCharArray(),
                    ("#                     " + "                              #").toCharArray(), ("#                "
                    + "                            " + "       #").toCharArray(), ("#                                " +
                    "  " + "                 #").toCharArray(), ("#       ##  #  ####   " + "  #    ##    ##    #### " +
                    "      " + "#").toCharArray(),
                    ("#       ##### ##  ##    ### ###   #  #   #  " + "##      #").toCharArray(), ("#       #  ## ## " +
                    " ##    #  #  #  ######  ####       #").toCharArray(), ("#       #   #  ####   " + "  #     # ## " +
                    "   ## #          #").toCharArray(), ("#                                          " + "  " + "   " +
                    "    #").toCharArray(), ("#           ####  ####  #    # ##  # ####           #").toCharArray(),
                    ("#           ###  ##  " + "## #    # # # # #   #          #").toCharArray(), ("#           #    "
                    + "##  ## ##  ## #  ## #   #  " + "        #").toCharArray(), ("#           #     ####   ####  # " +
                    "  " + "# ####           #").toCharArray(), ("#                     " + "                        " +
                    "      " + "#").toCharArray(),
                    ("#                                            " + "       #").toCharArray(), ("#                " +
                    "                                   #").toCharArray(), ("#                     " + "             " +
                    "                 #").toCharArray(), ("#                                          " + "  " + "   " +
                    "    #").toCharArray(), ("#                                                   #").toCharArray(),
                    ("#####################################################").toCharArray()};
        }
    }

    public char[][] getMap() {return map;}

    public String queueToString() {
        StringBuilder s = new StringBuilder();

        String tmp;
        for (int i = 0; i < 9; i++) {
            try {
                switch (input[i]) {
                    case NPC -> {tmp = "C";}
                    case FIRE -> {tmp = "-";}
                    case PACKEDICE -> {tmp = "@";}
                    case SCORE1 -> {tmp = "1";}
                    case SCORE2 -> {tmp = "2";}
                    case SCORE3 -> {tmp = "3";}
                    default -> tmp = "?";
                }

                s.append(tmp).append(" ");
            } catch (Exception e) {s.append(" ");}
        }
        try {
            switch (input[9]) {
                case NPC -> {tmp = "C";}
                case FIRE -> {tmp = "-";}
                case PACKEDICE -> {tmp = "@";}
                case SCORE1 -> {tmp = "1";}
                case SCORE2 -> {tmp = "2";}
                case SCORE3 -> {tmp = "3";}
                default -> tmp = "?";
            }
            s.append(tmp);
        } catch (Exception e) {
            s.append(" ");
        }
        return s.toString();
    }

    public void addNPC() {
        npcs[npcCtr] = new NPC();
        if (npcCtr < 1070) {npcCouncter(1);}
    }

    public void addObject(GameObject g) {
        try {
            if (g.getY() != 0 && g.getX() != 0) {
                objects[g.getY() - 1][g.getX() - 1] = g;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public GameObject getObject(Coordinate loc) {
        return objects[loc.getY() - 1][loc.getX() - 1];
    }

    public void removeObject(Coordinate loc) {
        objects[loc.getY() - 1][loc.getX() - 1] = null;
    }

    public void removeObject(GameObject g) {
        objects[g.getY() - 1][g.getX() - 1] = null;
    }

    public void addNPC(GameObject npc) {
        if (npc.getType() == GameObject.Type.NPC) {
            npcs[npcCtr] = new NPC(npc.getCoordinate(), 1000);
            npcCouncter(1);
        }
    }

    public void throwIce(){
        Ice breeze = null;
        switch (player.getDirection()){
            case UP    -> { breeze = new Ice(player.getCoordinate().getUp   (), player.getDirection(), 10, 10); }
            case DOWN  -> { breeze = new Ice(player.getCoordinate().getDown (), player.getDirection(), 10, 10); }
            case LEFT  -> { breeze = new Ice(player.getCoordinate().getLeft (), player.getDirection(), 10, 10); }
            case RIGHT -> { breeze = new Ice(player.getCoordinate().getRight(), player.getDirection(), 10, 10); }
        }
        if(
                breeze.getSpread() > 0 &&
                        isValid(breeze.getCoordinate()) &&
                        !isWall(breeze.getCoordinate())
        ) { addIce(breeze);freeze(breeze); }
    }

    public void freeze(Ice breeze) {
        Ice snow = null;
        try {
            switch (breeze.getDir()){
                case UP    -> { snow = new Ice(breeze.getCoordinate().getUp   (), breeze.getDir(),10, breeze.getSpread() - 1 ); }
                case DOWN  -> { snow = new Ice(breeze.getCoordinate().getDown (), breeze.getDir(),10, breeze.getSpread() - 1 ); }
                case LEFT  -> { snow = new Ice(breeze.getCoordinate().getLeft (), breeze.getDir(),10, breeze.getSpread() - 1 ); }
                case RIGHT -> { snow = new Ice(breeze.getCoordinate().getRight(), breeze.getDir(),10, breeze.getSpread() - 1 ); }
            }


            if(
               snow.getSpread() > 0 &&
               isValid(snow.getCoordinate()) &&
               !isWall(snow.getCoordinate())
            ) { addIce(snow);freeze(snow); }
        }catch (Exception e){}
    }

    public void addIce(Ice cream){
        ice[iceCtr] = cream;
        addObject(cream);
        iceCounter(1);
    }

    public boolean spaceAvailable(Coordinate coordinate) {
        for (int i = 0; i < npcCtr; i++) {
            if (npcs[i].getCoordinate().getX() == coordinate.getX() && npcs[i].getCoordinate().getY() == coordinate.getY()) {
                return false;
            }
        }
        return true;
    }

    public void input() {
        try {
            switch (input[0]) {//
                case NPC -> {addNPC(new GameObject(getBlank(), input[0]));}
                case FIRE -> {addFire();} //addObject(input[0]); }
                default -> {addObject(new GameObject(getBlank(), input[0]));}
            }
        } catch (Exception e) {}

        for (int i = 0; i < 9; i++) {input[i] = input[i + 1];}
        Random random = new Random();

        /*   PSCORE, CSCORE, PROB
         * 1  3     , 10    , 5/30  0  - 4
         * 2  10    , 30    , 5/30  5  - 9
         * 3  30    , 90    , 5/30  10 - 14
         * -  -     , -     , 6/30  15 - 20
         * @  -     , -     , 6/30  21 - 26
         * C  100   , -     , 3/30  27 - 29
         * */
        int sel = random.nextInt(30);
        if (sel < 5) {input[9] = GameObject.Type.SCORE1;}//GameObjects.Type.SCORE1;
        else if (sel < 10) {input[9] = GameObject.Type.SCORE2;}//GameObjects.Type.SCORE2;
        else if (sel < 15) {input[9] = GameObject.Type.SCORE3;}//GameObjects.Type.SCORE3;
        else if (sel < 21) {input[9] = GameObject.Type.FIRE;}//GameObject.Type.FIRE;
        else if (sel < 27) {input[9] = GameObject.Type.PACKEDICE;}//GameObjects.Type.PACKEDICE;
        else if (sel < 30) {input[9] = GameObject.Type.NPC;}//GameObjects.Type.NPC;
    }

    public boolean validateFireCoordinate(Coordinate loc) {
        return !GameField.getInstance().checkFire(loc) && !GameField.getInstance().isWall(loc) && GameField.getInstance().isValid(loc);
    }

    public Coordinate getBlank() {
        Random random = new Random();
        int x = random.nextInt(53);
        int y = random.nextInt(23);

        while (map[y][x] != ' ' || map[y][x] == '#') {
            x = random.nextInt(53);
            y = random.nextInt(23);
        }

        return new Coordinate(x, y);
    }

    public boolean isWall(Coordinate loc) {
        try {
            return map[loc.getY()][loc.getX()] == '#';
        } catch (Exception e) {
            return true;
        }
    }

    public Boolean checkFire(Coordinate loc) {
        //return false;
        if (getObject(loc) == null) {
            return false;
        } else {
            return getObject(loc).getType() == GameObject.Type.FIRE;
        }
    }

    public void addFire(Fire flame) {
        if (checkFire(flame.getCoordinate())) {
            removeObject(flame.getCoordinate());
        } else if (isValid(flame.getCoordinate()) && !isWall(flame.getCoordinate()) && !checkFire(flame.getCoordinate())) {
            GameField.fire[fireCtr] = flame;
            GameField.fire[fireCtr].setIndex(fireCtr);
            addObject(flame);
            fireCounter(1);
        }
    }

    public void addFire() {
        addFire(new Fire());
    }

    public void removeFire(Coordinate loc) {
        try {
            int index = ((Fire) getObject(loc)).getIndex();
            removeFire(index);
        } catch (Exception e) {}
    }

    public void removeFire(int index) {
        removeObject(fire[index]);
        fire[index] = null;
        for (int i = index; i + 1 < fireCtr; i++) {
            fire[i] = fire[i + 1];
        }
        fire[fireCtr] = null;
        fireCounter(-1);
    }

    public void spreadFire() {
        int t = fireCtr;

        for (int i = 0; i < t; i++) {
            try {spreadFire(i);} catch (Exception e) {}
        }
    }

    public Boolean isValid(Coordinate loc) {
        boolean validity = true;
        if (loc.getY() < 0 || loc.getY() > 21 || loc.getX() < 0 || loc.getX() > 51) {validity = false;}
        return validity;
    }

    public void spreadFire(int index) {
        if (fire[index].getHealth() <= 0) {
            removeFire(index);
        } else if (!fire[index].isUsed() && fire[index].getSpread() > 0) {
            for (Fire f : fire[index].spread()) {
                try {if (f != null && validateFireCoordinate(f.coordinate)) {addFire(f);}} catch (Exception e) {}
            }
            fire[index].setUsed(true);
        }
        fire[index].fightFire(1);
    }

    public Coordinate getNearestScore(Coordinate c) {
        Coordinate nearest = new Coordinate(c.getX(), c.getY());
        Double distance = Double.MAX_VALUE;

        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 51; j++) {
                try {
                    if (objects[i][j].getType() == GameObject.Type.SCORE1 || objects[i][j].getType() == GameObject.Type.SCORE2 || objects[i][j].getType() == GameObject.Type.SCORE3) {
                        Double tmp = Coordinate.distance(c, objects[i][j].getCoordinate());
                        if (tmp < distance) {
                            nearest.set(j + 1, i + 1);// = new Coordinate();
                            distance = tmp;
                        }
                    }
                } catch (Exception e) {}
            }
        }
        return nearest;
    }
}
