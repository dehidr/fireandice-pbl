import java.io.File;
import java.util.Scanner;
import java.util.Random;

public class GameField {

    private static GameField field  = null;
    public  static Player player    = Player.getInstance();
    public static GameObject[] input = new GameObject[10];
    public static NPC[] npcs          = new NPC[1071];// 51*21 = 1071
    public static GameObject[][] objects          = new GameObject[21][51];
    int npcCtr = 0;

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
            case UP     -> { if(map[c.getY()-1][c.getX()] != '#' && field.spaceAvailable(new Coordinate(c.getY()-1,c.getX()))){ c.moveUp()   ; stuck = false; break; } }
            case DOWN   -> { if(map[c.getY()+1][c.getX()] != '#' && field.spaceAvailable(new Coordinate(c.getY()+1,c.getX()))){ c.moveDown() ; stuck = false; break; } }
            case LEFT   -> { if(map[c.getY()][c.getX()-1] != '#' && field.spaceAvailable(new Coordinate(c.getY(),c.getX()-1 ))){ c.moveLeft() ; stuck = false; break; } }
            case RIGHT  -> { if(map[c.getY()][c.getX()+1] != '#' && field.spaceAvailable(new Coordinate(c.getY(),c.getX()+1 ))){ c.moveRight(); stuck = false; break; } }
        }
        return !c.isStuck();
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

    public void initQueue(){
        for(int i = 0; i < 10 ; i++) {
            input();
        }
    }

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

    public String queueToString(){
        String s = "";
        for(int i = 0 ; i < 9 ; i++){
            try {
                s = s + input[i].toString() + " ";
            }
            catch (Exception e){ s += " "; }
        }
        try {
            s = s + input[9].toString();
        } catch (Exception e){
            s = s + " ";
        }
        return s;
    }

    public void addNPC(){
        npcs[npcCtr] = new NPC();
        if(npcCtr < 1070) { npcCtr++; }
    }

    public void addObject(GameObject g){
        objects[g.getY()-1][g.getX()-1] = g;
    }


    public void removeObject(GameObject g){
        objects[g.getY()-1][g.getX()-1] = null;
    }

    public void addNPC(GameObject npc){
        if(npc.getType() == GameObject.Type.NPC){
            npcs[npcCtr] = new NPC(npc.getCoordinate(),1000);
            npcCtr++;
        }
    }

    public boolean spaceAvailable(Coordinate coordinate){
        for(int i = 0 ; i < npcCtr ; i++){
            if(npcs[i].getCoordinate().getX() == coordinate.getX() && npcs[i].getCoordinate().getY() == coordinate.getY()){
                return false;
            }
        }
        return true;
    }

    public void input(){
        try {
            switch (input[0].getType()){
                case NPC -> { addNPC(input[0]);}
                case FIRE -> { addObject(input[0]); }
                default -> { addObject(input[0]); }
            }
        }
        catch (Exception e){}

        for(int i = 0 ; i < 9 ; i++){ input[i] = input[i+1]; }
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
        if      (sel <  5) { input[9] = new GameObject(getBlank(), GameObject.Type.SCORE1   ); }//GameObjects.Type.SCORE1;
        else if (sel < 10) { input[9] = new GameObject(getBlank(), GameObject.Type.SCORE2   ); }//GameObjects.Type.SCORE2;
        else if (sel < 15) { input[9] = new GameObject(getBlank(), GameObject.Type.SCORE3   ); }//GameObjects.Type.SCORE3;
        else if (sel < 21) { input[9] = new GameObject(getBlank(), GameObject.Type.FIRE     ); }//GameObject.Type.FIRE;
        else if (sel < 27) { input[9] = new GameObject(getBlank(), GameObject.Type.PACKEDICE); }//GameObjects.Type.PACKEDICE;
        else if (sel < 30) { input[9] = new GameObject(getBlank(), GameObject.Type.NPC      ); }//GameObjects.Type.NPC;
    }

    public Coordinate getBlank(){
        Random random = new Random();
        int x = random.nextInt(53);
        int y = random.nextInt(23);

        while(map[y][x] != ' '){
            x = random.nextInt(53);
            y = random.nextInt(23);
        }

        return new Coordinate(x,y);
    }

    public void addFire(Coordinate loc, int health){
        addObject(new GameObject(loc, GameObject.Type.FIRE, health));
    }

    public void spreadFire(GameObject fire){
        if(fire.getType() == GameObject.Type.FIRE){

            if(fire.health <= 0){
                removeObject(fire);
            }else {
                if( map[fire.getY()-1][fire.getX()  ] != '#' ){ addFire(new GameObject(fire.getCoordinate().getUp()   , fire.getHealth()-1)); }
                if( map[fire.getY()+1][fire.getX()  ] != '#' ){ addFire(new GameObject(fire.getCoordinate().getDown() , fire.getHealth()-1)); }
                if( map[fire.getY()  ][fire.getX()-1] != '#' ){ addFire(new GameObject(fire.getCoordinate().getLeft() , fire.getHealth()-1)); }
                if( map[fire.getY()  ][fire.getX()+1] != '#' ){ addFire(new GameObject(fire.getCoordinate().getRight(), fire.getHealth()-1)); }
                removeObject(fire);
            }
        }
    }

    public Coordinate getNearestScore(Coordinate c){
        Coordinate nearest = new Coordinate(c.getX(), c.getY());
        Double distance = 99999999.0;

        for(int i = 0; i < 21; i++){
            for(int j = 0; j < 51; j++){
                try {
                    if(objects[i][j].getType()== GameObject.Type.SCORE1 ||
                       objects[i][j].getType()== GameObject.Type.SCORE2 ||
                       objects[i][j].getType()== GameObject.Type.SCORE3){
                        Double tmp = Coordinate.distance(c,objects[i][j].getCoordinate());
                        if(tmp < distance){
                            nearest.set(j+1,i+1);// = new Coordinate();
                            distance = tmp;
                        }
                    }
                }catch (Exception e){}
            }
        }
        return nearest;
    }
}
