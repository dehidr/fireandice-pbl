import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;
import enigma.event.TextMouseMotionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import enigma.console.TextAttributes;

import java.awt.Color;

public class Game {

    private final Color CatppuccinRosewater = new Color(245, 224, 220);
    private final Color CatppuccinFlamingo = new Color(242, 205, 205);
    private final Color CatppuccinPink = new Color(245, 194, 231);
    private final Color CatppuccinMauve = new Color(203, 166, 247);
    private final Color CatppuccinRed = new Color(243, 139, 168);
    private final Color CatppuccinMaroon = new Color(235, 160, 172);
    private final Color CatppuccinPeach = new Color(250, 179, 135);
    private final Color CatppuccinYellow = new Color(249, 226, 175);
    private final Color CatppuccinGreen = new Color(166, 227, 161);
    private final Color CatppuccinTeal = new Color(148, 226, 213);
    private final Color CatppuccinSky = new Color(137, 220, 235);
    private final Color CatppuccinSapphire = new Color(116, 199, 236);
    private final Color CatppuccinBlue = new Color(137, 180, 250);
    private final Color CatppuccinLavender = new Color(180, 190, 254);
    private final Color CatppuccinText = new Color(205, 214, 244);
    private final Color CatppuccinSubtext1 = new Color(186, 194, 222);
    private final Color CatppuccinSubtext0 = new Color(166, 173, 200);
    private final Color CatppuccinOverlay2 = new Color(147, 153, 178);
    private final Color CatppuccinOverlay1 = new Color(127, 132, 156);
    private final Color CatppuccinOverlay0 = new Color(108, 112, 134);
    private final Color CatppuccinSurface2 = new Color(88, 91, 112);
    private final Color CatppuccinSurface1 = new Color(69, 71, 90);
    private final Color CatppuccinSurface0 = new Color(49, 50, 68);
    private final Color CatppuccinBase = new Color(30, 30, 46);
    private final Color CatppuccinMantle = new Color(24, 24, 37);
    private final Color CatppuccinCrust = new Color(17, 17, 27);

    public enigma.console.Console cn = Enigma.getConsole("[ f i r e   a n d   i c e ]", 120, 37, 18, 1);
    public TextMouseListener tmlis;
    public TextMouseMotionListener mouseListener;
    public KeyListener klis;

    GameField map = GameField.getInstance();
    //private TextAttributes wall = new TextAttributes(CatppuccinOverlay2,CatppuccinSubtext1);
    private TextAttributes wall = new TextAttributes(CatppuccinRosewater, CatppuccinOverlay2);
    private TextAttributes red = new TextAttributes(CatppuccinRed, CatppuccinBase);
    private TextAttributes black = new TextAttributes(CatppuccinText, CatppuccinBase);
    private TextAttributes white = new TextAttributes(CatppuccinText, CatppuccinBase);
    private TextAttributes color = black;

    char[][] logo = new char[][]{(
            " ▄▀▀▀█▄    ▄▀▀█▀▄    ▄▀▀▄▀▀▀▄  ▄▀▀█▄▄▄▄                              .o8       ██ " +
            "                       ").toCharArray(), ("█  ▄▀  ▀▄ █   █  █  █   █   █ ▐  ▄▀   ▐                      " +
            "       \"888       ▀▀                        ").toCharArray(), ("▐ █▄▄▄▄   ▐   █  ▐  ▐  █▀▀█▀    █▄▄▄▄▄ " +
            "   .oooo.   ooo. .oo.    .oooo888     ████      ▄█████▄   ▄████▄  ").toCharArray(), (" █    ▐       █   " +
            "   ▄▀    █    █    ▌   `P  )88b  `888P\"Y88b  d88' `888       ██     ██▀    ▀  ██▄▄▄▄██ ").toCharArray()
            , (" █         ▄▀▀▀▀▀▄  █     █    ▄▀▄▄▄▄     .oP\"888   888   888  888   888       ██     ██        " +
            "██▀▀▀▀▀▀ ").toCharArray(), ("█         █       █ ▐     ▐    █    ▐    d8(  888   888   888  888   888   " +
            " ▄▄▄██▄▄▄  ▀██▄▄▄▄█  ▀██▄▄▄▄█ ").toCharArray(), ("▐         ▐       ▐            ▐         `Y888\"\"8o " +
            "o888o o888o `Y8bod88P\"   ▀▀▀▀▀▀▀▀    ▀▀▀▀▀     ▀▀▀▀▀  ").toCharArray()};

    //char wallChar = '▞';
    boolean isWindows = false;
    char wallChar = '▀';
    char playerChar = '▀';
    char npcChar = '▀';
    char[] boxChars = "─│╭╮╰╯".toCharArray(); //


    // ------ Standard variables for mouse and keyboard ------
    public int mousepr;          // mouse pressed?
    public int mousex, mousey;   // mouse text coords.
    public int motx = 0 , moty = 0;// motion coordinates
    public int keypr;   // key pressed?
    public int rkey;    // key   (for press/release)
    public boolean updated = true;
    public boolean menu = true;
    public boolean menumap = false;
    public boolean menuabout = false;
    public boolean end = false;
    public boolean debug = false;
    public int timer = 0;
    public int animationtimer = 0;
    public int menuselect = 0;
    // ----------------------------------------------------

    public void restart(){
        mousepr = 0;
        mousex = 0;
        mousey = 0;
        keypr = 0;
        rkey = 0;
        updated = true;
        menu = true;
        end = false;
        menumap = false;
        menuabout = false;
        timer = 0;
        menuselect = 0;
        animationtimer = 0;
    }

    public void setTitle(String title){
        cn.setTitle(title);
    }

    public void timer() {
        timer = (timer + 1) % 80;
        animationtimer = (animationtimer + 1) % 17;
    }

    public void refresh() {
        int rows = cn.getTextWindow().getRows();
        int cols = cn.getTextWindow().getColumns();

        for (int i = 0; i < rows; i++) {for (int j = 0; j < cols; j++) {cn.getTextWindow().output(j, i, ' ', color);}}
    }

    public void drawEmpty(int x, int y, int length, int width) {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {cn.getTextWindow().output(x + j, y + i, ' ', color);}
        }
    }

    public void drawArray(int x, int y, char[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                cn.getTextWindow().output(x + i, y + j, a[j][i]);
            }
        }
    }

    public void drawAnimation(int x, int y, int frame) {
        frame = (frame) % 63;
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 29; j++) {
                color = new TextAttributes(CatppuccinRed, CatppuccinPeach);
                if (animation[frame][i][j] == '`') {
                    color = white;
                    if (i > 10 && i < 17 && j > 8 && j < 19) {color = new TextAttributes(CatppuccinRed, Color.white);}
                } else if (animation[frame][i][j] == '*') {color = new TextAttributes(CatppuccinPeach, CatppuccinRed);}
                cn.getTextWindow().output(x + j, y + i, animation[frame][i][j], color);
            }
        }
        color = white;
    }

    public void moveNPCs() {

        for (int i = 0; i < map.npcCtr; i++) {
            if (GameField.objects[GameField.npcs[i].getY() - 1][GameField.npcs[i].getX() - 1] != null) {
                switch (GameField.objects[GameField.npcs[i].getY() - 1][GameField.npcs[i].getX() - 1].getType()) {
                    case SCORE1 -> GameField.npcs[i].score(10);
                    case SCORE2 -> GameField.npcs[i].score(30);
                    case SCORE3 -> GameField.npcs[i].score(100);
                    case ICE -> GameField.npcs[i].damage(100);
                }
                GameField.objects[GameField.npcs[i].getY() - 1][GameField.npcs[i].getX() - 1] = null;
            }
            GameField.npcs[i].setTarget(new Coordinate(GameField.getInstance().getNearestScore(GameField.npcs[i].getCoordinate())));
            boolean moved = false;
            if(GameField.npcs[i].getStuckfor() > 2 || timer == 36 ){
                GameField.moveRandom(GameField.npcs[i]);
                moved = true;
            }
            if(!moved){
                if (!GameField.npcs[i].isStuck()) {
                    if (GameField.npcs[i].getX() < GameField.npcs[i].getTarget().getX()) {
                        GameField.npcs[i].setStuck(GameField.move(GameField.npcs[i], Character.Direction.RIGHT));
                    } else if (GameField.npcs[i].getX() > GameField.npcs[i].getTarget().getX()) {
                        GameField.npcs[i].setStuck(GameField.move(GameField.npcs[i], Character.Direction.LEFT));
                    } else if (GameField.npcs[i].getY() < GameField.npcs[i].getTarget().getY()) {
                        GameField.npcs[i].setStuck(GameField.move(GameField.npcs[i], Character.Direction.DOWN));
                    } else if (GameField.npcs[i].getY() > GameField.npcs[i].getTarget().getY()) {
                        GameField.npcs[i].setStuck(GameField.move(GameField.npcs[i], Character.Direction.UP));
                    } else {GameField.npcs[i].setStuck(true);}
                } else if (GameField.npcs[i].isStuck()) {
                    if (GameField.npcs[i].getY() < GameField.npcs[i].getTarget().getY()) {
                        GameField.npcs[i].setStuck(GameField.move(GameField.npcs[i], Character.Direction.DOWN));
                    } else if (GameField.npcs[i].getY() > GameField.npcs[i].getTarget().getY()) {
                        GameField.npcs[i].setStuck(GameField.move(GameField.npcs[i], Character.Direction.UP));
                    } else if (GameField.npcs[i].getX() < GameField.npcs[i].getTarget().getX()) {
                        GameField.npcs[i].setStuck(GameField.move(GameField.npcs[i], Character.Direction.RIGHT));
                    } else if (GameField.npcs[i].getX() > GameField.npcs[i].getTarget().getX()) {
                        GameField.npcs[i].setStuck(GameField.move(GameField.npcs[i], Character.Direction.LEFT));
                    } else {GameField.npcs[i].setStuck(true);}
                }
            }

        }
    }

    public void drawPlayer(int x, int y) {
        /*
        if(playerChar == '▀'){
            playerChar = '▄';
        }else{
            playerChar = '▀';
        }
        */
        //char playerChar = '🯅';
        //char playerChar = java.lang.Character.;
        cn.getTextWindow().output(x + GameField.player.getX(), y + GameField.player.getY(), playerChar,
                new TextAttributes(CatppuccinSky, CatppuccinBlue));
    }

    public void drawNPCs(int x, int y) {
        /*
        if(timer % 2 == 1){
            npcChar = '▌';
        }else{
            npcChar = '▐';
        }*/
        for (int i = 0; i < map.npcCtr; i++) {
            cn.getTextWindow().output(x + GameField.npcs[i].getX(), y + GameField.npcs[i].getY(), npcChar,
                    new TextAttributes(CatppuccinRed, CatppuccinPeach));

        }
    }

    public void drawCursor(){
    }

    public void fireSpread() {
        if (timer % 4 == 0) {
            map.spreadFire();
        }
        /*
        for (int i = 0; i < GameField.fire.length; i++) {
            try {
                if (timer == 1 || timer == 40) {
                    color = new TextAttributes(CatppuccinPeach, CatppuccinBase);
                    GameField.getInstance().spreadFire(i);
                    updated = true;
                    color = white;
                }
            } catch (Exception e) {
                if(debug){
                    drawBox(5,32,5,109, e.toString(), new TextAttributes(CatppuccinText, CatppuccinBase), new TextAttributes(CatppuccinText, CatppuccinBase ));
                }            }
        }*/
    }

    public void drawFire(int x, int y) {
        for (int i = 0; i < GameField.fireCtr; i++) {
            try {
                color = new TextAttributes(CatppuccinPeach, CatppuccinBase);
                cn.getTextWindow().output(x + GameField.fire[i].getX(), y + GameField.fire[i].getY(),
                        GameField.fire[i].toString().charAt(0), color);
            } catch (Exception e) {
                if(debug){
                    //drawBox(5,32,5,109, e.toString(), new TextAttributes(CatppuccinText, CatppuccinBase), new TextAttributes(CatppuccinText, CatppuccinBase ));
                }
            }
        }
        color = white;
    }

    public void drawObjects(int x, int y) {
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 51; j++) {
                try {
                    switch (GameField.objects[i][j].type) {
                        case SCORE1 -> color = new TextAttributes(CatppuccinYellow, CatppuccinBase);
                        case SCORE2 -> color = new TextAttributes(CatppuccinYellow, CatppuccinBase);
                        case SCORE3 -> color = new TextAttributes(CatppuccinYellow, CatppuccinBase);
                        case ICE -> color = new TextAttributes(CatppuccinSky, CatppuccinBase);
                        case PACKEDICE -> color = new TextAttributes(CatppuccinSapphire, CatppuccinBase);
                        case FIRE -> { color = new TextAttributes(CatppuccinPeach, CatppuccinBase);
                            //map.spreadFire(GameField.objects[i][j]); if(timer == 1 || timer == 40 ) { GameField
                            // .objects[i][j].fightFire(); }; updated=true; }
                        }
                    }
                    if( GameField.objects[i][j].getType() != GameObject.Type.FIRE){
                        cn.getTextWindow().output(x + GameField.objects[i][j].getX(), y + GameField.objects[i][j].getY(),
                                GameField.objects[i][j].toString().charAt(0), color);
                    }
                } catch (Exception e) {}
                color = white;
            }
        }
    }

    public void drawMap(int x, int y) {
        char tmp = wallChar;
        for (int i = 0; i < 53; i++) {
            for (int j = 0; j < 23; j++) {
                if (j < 22) {

                    if (map.getMap()[j + 1][i] == '#') {tmp = '█';} else
                        tmp = '▀';

                }
                if (isWindows) {tmp = '#';}
                if (map.getMap()[j][i] == '#') {color = wall;}
                cn.getTextWindow().output(x + i, y + j, map.getMap()[j][i] == '#' ? tmp : map.getMap()[j][i], color);
                color = white;
                tmp = '▀';
                if (isWindows) {tmp = '#';}
            }
        }
        if (!menu) {
            drawFire(x, y);
            drawNPCs(x, y);
            drawObjects(x, y);
            drawPlayer(x, y);
        }
    }

    public void drawLogo(int x, int y) {
        for (int i = 0; i < 105; i++) {
            for (int j = 0; j < 7; j++) {
                if (i < 41) {color = red;} else if (i < 74) {color = white;} else {
                    color = new TextAttributes(CatppuccinSky, CatppuccinBase);
                }
                cn.getTextWindow().output(x + 2 + i, y + j, logo[j][i], color);
            }
        }
    }

    public void winChars() {
        if (isWindows) {
            npcChar = 'C';
            wallChar = '#';
            playerChar = 'P';
            boxChars = "-|++++".toCharArray();
        } else {
            wallChar = '▀';
            playerChar = '▀';
            npcChar = '▀';
            boxChars = "─│╭╮╰╯".toCharArray(); //
        }
        updated = true;
        refresh();
    }

    public void startGame() {
        refresh();
        map.importMap();
        map.initQueue();
        menu = false;
        updated = true;
    }

    public void showAbout() {
        menumap = false;
        menuabout = true;        //drawBox(5,5,);
    }

    public void loadMap() {
        map.importMap();
        menumap = true;
        menuabout = false;
    }

    public void drawEnd(int x, int y) {
        color = red;
        drawLogo(x, y);

        drawBox(x, y - 2, 11, 109);
        drawBox(x, y + 9, 20, 33);
        drawBox(x + 34, y + 9, 5, 75, " [ g a m e   o v e r ] ", new TextAttributes(CatppuccinPeach, CatppuccinBase),
                new TextAttributes(CatppuccinPeach, CatppuccinBase));

    }

    public void drawMenu(int x, int y) {
        drawLogo(x, y);
        TextAttributes text = white;
        TextAttributes box = white;
        color = white;
        drawBox(x, y - 2, 11, 109);
        drawBox(x, y + 9, 20, 33);
        color = white;


        if (menuselect == 0 || menuselect == 4) {
            text = new TextAttributes(CatppuccinBase, CatppuccinRed);
            box = new TextAttributes(CatppuccinRed, CatppuccinBase);
        }
        drawBox(x + 34, y + 9, 5, 75, " [ s t a r t ] ", text, box);
        text = white;
        box = white;

        if (menuselect == 1) {
            text = new TextAttributes(CatppuccinBase, CatppuccinRed);
            box = new TextAttributes(CatppuccinRed, CatppuccinBase);
        }
        drawBox(x + 34, y + 14, 5, 75, " [ a b o u t ] ", text, box);
        text = white;
        box = white;

        if (menuselect == 2) {
            text = new TextAttributes(CatppuccinBase, CatppuccinRed);
            box = new TextAttributes(CatppuccinRed, CatppuccinBase);
        }
        drawBox(x + 34, y + 19, 5, 75, map.isLoaded() ? " [ m a p   l o a d e d ] " : " [ m a p   n o t   f o u n d ]" +
                " ", text, box);
        text = white;
        box = white;

        if (menuselect == 3) {
            text = new TextAttributes(CatppuccinBase, CatppuccinRed);
            box = new TextAttributes(CatppuccinRed, CatppuccinBase);
        }
        drawBox(x + 34, y + 24, 5, 75, " [ q u i t ] ", text, box);
        text = white;
        box = white;

        //drawAnimation(x + 2,y+10, animationtimer + 46);
    }

    public void drawBox(int x, int y, int length, int width) {
      /*
      ╭─────────────╮
      │             │
      │             │
      │             │
      ╰─────────────╯
          char[] boxChars = "─│╭╮╰╯".toCharArray(); //
       */
        for (int i = 0; i < length; i++) {
            if (i == 0 || i == length - 1) {
                for (int j = 0; j < width; j++) {cn.getTextWindow().output(x + j, y + i, boxChars[0], color);}
            }
            if (i > 0 && i < length - 1) {
                cn.getTextWindow().output(x + 0, y + i, boxChars[1], color);
                cn.getTextWindow().output(x + width - 1, y + i, '│', color);
            }
        }
        cn.getTextWindow().output(x, y, boxChars[2], color);
        cn.getTextWindow().output(x + width - 1, y, boxChars[3], color);
        cn.getTextWindow().output(x, y + length - 1, boxChars[4], color);
        cn.getTextWindow().output(x + width - 1, y + length - 1, boxChars[5], color);
    }


    public void drawBox(int x, int y, int length, int width, String text, TextAttributes textC, TextAttributes box) {
        color = box;
        String[] par = text.split("\n");
        drawBox(x, y, length, width);

        color = textC;
        for (int i = 0; i < par.length; i++) {
            try {
                if (par[i].charAt(0) == '[') {color = new TextAttributes(CatppuccinBase, CatppuccinRed);}
            } catch (Exception E) {}
            int offx = (width - par[i].length() + 1) / 2;
            int offy = (length - par.length - (length) % 2) / 2;
            cn.getTextWindow().setCursorPosition(x + offx, y + offy + i + 1);
            cn.getTextWindow().output(par[i], color);
            color = textC;
        }
    }

    Game() throws Exception {   // --- Contructor

        // ------ Standard code for mouse and keyboard ------ Do not change
        tmlis = new TextMouseListener() {
            public void mouseClicked(TextMouseEvent arg0) {}

            public void mousePressed(TextMouseEvent arg0) {
                if (mousepr == 0) {
                    mousepr = 1;
                    mousex = arg0.getX();
                    mousey = arg0.getY();
                }
            }

            public void mouseReleased(TextMouseEvent arg0) {}
        };
        cn.getTextWindow().addTextMouseListener(tmlis);

        mouseListener = new TextMouseMotionListener() {
            @Override
            public void mouseMoved(TextMouseEvent textMouseEvent) {
                motx = textMouseEvent.getX();
                moty = textMouseEvent.getY();
            }

            @Override
            public void mouseDragged(TextMouseEvent textMouseEvent) {
                motx = textMouseEvent.getX();
                moty = textMouseEvent.getY();
            }
        };
        cn.getTextWindow().addTextMouseMotionListener(mouseListener);

        klis = new KeyListener() {
            public void keyTyped(KeyEvent e) {}

            public void keyPressed(KeyEvent e) {
                if (keypr == 0) {
                    keypr = 1;
                    rkey = e.getKeyCode();
                }
            }

            public void keyReleased(KeyEvent e) {}
        };
        cn.getTextWindow().addKeyListener(klis);
        // ----------------------------------------------------


        int px = 5, py = 5;
        cn.getTextWindow().output(px, py, 'P');

        while (true) {
            try {
                setTitle("[ f i r e   a n d   i c e ] - Ice : " + GameField.player.getScore() + " pts, Fire: " + GameField.npcs[0].getScore() + " pts " + motx + " " + moty);
            } catch (Exception e){
                setTitle("[ f i r e   a n d   i c e ] - Ice : " + GameField.player.getScore() + "pts, Fire: 0 pts");
            }
            if (updated) {refresh();}
            if (debug) {
                drawBox(5, 0, 3, 109, "[ d e v   m o d e   a c t i v a t e d ]", new TextAttributes(CatppuccinBase,
                        CatppuccinRed), red);
                color = white;
            }
            if (menu) { menu(); }
            //drawBox( 5,34,3,30,"menuselect : " + menuselect);
            if (!menu) { fireandice(); }

            if (end) {
                gameover();
            }

            if (mousepr == 1) {  // if mouse button pressed
                //cn.getTextWindow().output(mousex,mousey,'#');  // write a char to x,y position without changing
                // cursor position
                px = mousex;
                py = mousey;

                mousepr = 0;     // last action
            }
            if (keypr == 1) {    // if keyboard button pressed
                if(end){
                    if (rkey == KeyEvent.VK_ENTER) {
                        restart();
                        refresh();
                        Main.restart();
                    }
                }

                if (menu) {
                    if (rkey == KeyEvent.VK_LEFT) {
                        menuselect = (menuselect + 3) % 4;
                        menuabout = false;
                        menumap = false;
                        updated = true;
                    }
                    if (rkey == KeyEvent.VK_RIGHT) {
                        menuselect = (menuselect + 1) % 4;
                        menuabout = false;
                        menumap = false;
                        updated = true;
                    }
                    if (rkey == KeyEvent.VK_UP) {
                        menuselect = (menuselect + 3) % 4;
                        menuabout = false;
                        menumap = false;
                        updated = true;
                    }
                    if (rkey == KeyEvent.VK_DOWN) {
                        menuselect = (menuselect + 1) % 4;
                        menuabout = false;
                        menumap = false;
                        updated = true;
                    }
                    if (menuselect == 2) {loadMap();}
                    if (menuselect == 1) {showAbout();}
                    if (rkey == KeyEvent.VK_ENTER) {
                        switch (menuselect) {
                            case 0: {
                                startGame();
                                break;
                            }
                            case 3: {
                                System.exit(0);
                                break;
                            }
                        }
                    }

                } else {
                    if (rkey == KeyEvent.VK_LEFT || rkey == KeyEvent.VK_A) {
                        GameField.move(Character.Direction.LEFT);
                        GameField.player.setDirection(Character.Direction.LEFT);
                        updated = true;
                    }
                    if (rkey == KeyEvent.VK_RIGHT || rkey == KeyEvent.VK_D) {
                        GameField.move(Character.Direction.RIGHT);
                        GameField.player.setDirection(Character.Direction.RIGHT);

                        updated = true;
                    }
                    if (rkey == KeyEvent.VK_UP || rkey == KeyEvent.VK_W) {
                        GameField.move(Character.Direction.UP);
                        GameField.player.setDirection(Character.Direction.UP);
                        updated = true;
                    }
                    if (rkey == KeyEvent.VK_DOWN || rkey == KeyEvent.VK_S) {
                        GameField.move(Character.Direction.DOWN);
                        GameField.player.setDirection(Character.Direction.DOWN);

                        updated = true;
                    }
                    if (rkey == KeyEvent.VK_SPACE) {
                        map.throwIce();
                        updated = true;
                    }
                    if (rkey == KeyEvent.VK_F && debug) {
                        map.addNPC();
                        updated = true;
                    }
                    if (rkey == KeyEvent.VK_G && debug) {
                        map.addObject(new GameObject(map.getBlank(), GameObject.Type.SCORE3));
                        updated = true;
                    }
                    if (rkey == KeyEvent.VK_R && debug) {
                        map.addObject(new GameObject(map.getBlank(), GameObject.Type.PACKEDICE));
                        updated = true;
                    }
                    if (rkey == KeyEvent.VK_T && debug) {
                        map.addFire();
                        //map.addObject(new GameObject(map.getBlank(), GameObject.Type.FIRE));
                        updated = true;
                    }


                    char rckey = (char) rkey;
                    //        left          right          up            down
                    //if(rckey=='%' || rckey=='\'' || rckey=='&' || rckey=='(') cn.getTextWindow().output(px,py,'P');
                    // VK kullanmadan test teknigi
                    //else cn.getTextWindow().output(rckey);

                }
                if (rkey == KeyEvent.VK_J) {
                    debug = !debug;
                    updated = true;
                }
                if (rkey == KeyEvent.VK_Y) {
                    isWindows = !isWindows;
                    winChars();
                }
                if (rkey == KeyEvent.VK_Q) {System.exit(0);}
                keypr = 0;
            }
            Thread.sleep(25);
            timer();
        }
    }

    private void gameover() {
        refresh();
        drawAnimation(5 + 2, 5 + 10, animationtimer + 46);
        if (updated) {drawEnd(5, 5);}
        updated = false;
    }

    private void fireandice() {
        fireSpread();
        if (GameField.objects[GameField.player.getY() - 1][GameField.player.getX() - 1] != null) {
            switch (GameField.objects[GameField.player.getY() - 1][GameField.player.getX() - 1].getType()) {
                case SCORE1 -> GameField.player.score(3);
                case SCORE2 -> GameField.player.score(10);
                case SCORE3 -> GameField.player.score(30);
                case PACKEDICE -> GameField.player.packedIce++;
                case FIRE -> { GameField.player.damage(100);
                    map.removeFire(GameField.player.getCoordinate());
                }
            }
            GameField.objects[GameField.player.getY() - 1][GameField.player.getX() - 1] = null;
        }
        if (timer == 1) {map.input();}
        if (timer % 4 == 0) {
            moveNPCs();
            updated = true;
        }
        if (updated) {
            color = red;

            drawBox(31, 7, 25, 57);
            drawMap(33, 8);
            drawBox(5, 7, 25, 25,
                    GameField.player.getCoordinate().toString() + " " + map.getNearestScore(GameField.player.getCoordinate()).toString(), white, red);
            drawBox(89, 7, 25, 25, GameField.player.getLife() + " " + GameField.player.getScore(), white, red);
            //drawBox(5,2,5,109, map.queueToString(),white,red);
            color = red;

            drawBox(48, 3, 3, 23, map.queueToString(), white, red);
            color = white;

            updated = false;
        }
        if (GameField.player.getLife() <= 0) {
            end = true;
            menu = false;
            updated = true;
        }}

    private void menu() {
        drawAnimation(5 + 2, 5 + 10, animationtimer + 46);
        if (updated) {drawMenu(5, 5);}
        updated = false;
        if (menumap) {
            drawEmpty(31, 4, 27, 57);
            drawBox(31, 4, 3, 57, map.isLoaded() ? "[ m a p   l o a d e d ]" : "[ m a p   n o t   f o u n d " +
                    "]", new TextAttributes(CatppuccinBase, CatppuccinRed), red);
            color = red;
            drawBox(31, 7, 25, 57);
            drawMap(33, 8);
            color = white;
            // drawBox(5,34, 5, 51 , map.isLoaded() ? "m a p   l o a d e d" : "m a p   n o t   f o u n d");
        }
        if (menuabout) {
            color = red;
            String desc = "try to collect more points than the opponent.\n" + "escape from the fire (-) and " +
                    "throw ice (+) at\n" + "the opponent                                 \n" + " \n" + "[ c o" +
                    " n t r o l s ]\n" + " \n" + "> you can move with WASD or the arrow keys.  \n" + "> you " +
                    "can throw ice with spacebar.           \n" + "> you can enable the developer mode with J" +
                    "   \n" + "> while in dev mode summon packed ice, fire, \n" + "  points and robots with " +
                    "RTFG                \n" + "> you can quit with Q                        \n" + "\n" + "[ " +
                    "c r e d i t s ]\n" + " \n" + "> logo created with figlet using             \n" + "  " +
                    "github.com/xero/figlet-fonts               \n" + "> animation created by aem1k with " +
                    "javascript \n" + "  aem1k.com/fire/                            \n" + " \n" + "... \n";
            drawEmpty(31, 4, 27, 57);
            drawBox(31, 7, 25, 57, desc, white, red);
            drawBox(31, 4, 3, 57, "[ a b o u t ]", new TextAttributes(CatppuccinBase, CatppuccinRed), red);
            color = red;
            // drawBox(5,34, 5, 51 , map.isLoaded() ? "m a p   l o a d e d" : "f i l e   n o t   f o u n d");
        }
    }

    char[][][] animation = new char[][][]{{("`````````````````````````````").toCharArray(), (
            "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````88``````````````").toCharArray(), ("````````````88```````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray()
            , ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````888`````````````").toCharArray(), (
                    "`````````88`888``````````````").toCharArray(), ("````````88`888```````````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````*88*````````````").toCharArray(), ("`````````8888888`````````````").toCharArray(),
            ("```````88888888``````````````").toCharArray(), ("``````8888*88*```````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray()
            , ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("``````````````*8*````````````").toCharArray(),
            ("`````````*888888*````````````").toCharArray(), ("```````888888888`````````````").toCharArray(), (
                    "``````88888888*``````````````").toCharArray(), ("`````8888**8*````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("``````````*8*888*````````````").toCharArray(), (
                    "```````*88888888`````````````").toCharArray(), ("``````888888888``````````````").toCharArray(),
            ("`````88888888*```````````````").toCharArray(), ("````*88888```````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray()
            , ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "``````````````*8*````````````").toCharArray(), ("````````*8888888*````````````").toCharArray(),
            ("``````*888888888`````````````").toCharArray(), ("`````888888888*``````````````").toCharArray(), (
                    "````*8888888*88``````````````").toCharArray(), ("````888888``88```````````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````*88*888*````````````").toCharArray(), ("```````888888888`````````````").toCharArray(), (
                    "``````888888888``````````````").toCharArray(), ("`````88888888888`````````````").toCharArray(),
            ("````8888888`888````88````````").toCharArray(), ("````*8888*`888````88`````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray()
            , ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("```````````*``***````````````").toCharArray(), (
                    "````````88888888*````````````").toCharArray(), ("``````*88888888*`````````````").toCharArray(),
            ("`````88888888888*````````````").toCharArray(), ("````*88888888888```888```````").toCharArray(), (
                    "````88888888888```888````````").toCharArray(), ("`````**8*8888*```888`````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````*88**88*````````````").toCharArray(),
            ("```````888888888`````````````").toCharArray(), ("``````8888888888*````````````").toCharArray(), (
                    "`````88888888888*``*88*``````").toCharArray(), ("````888888888888``8888```````").toCharArray(),
            ("````*888888888*``88888```````").toCharArray(), ("````````8888*```*8888````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray()
            , ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("```````````*```*`````````````").toCharArray(), ("````````88888888`````````````").toCharArray(), (
                    "``````*88888888*`````````````").toCharArray(), ("`````88888888888*```*8*``````").toCharArray(),
            ("````*88888888888``*888*``````").toCharArray(), ("````88888888888``888888``````").toCharArray(), (
                    "````**8888888*`8888888```````").toCharArray(), ("```````*888```88*8888````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````*88****`````````````").toCharArray(), ("```````88888888*`````````````").toCharArray(),
            ("``````8888888888*````````````").toCharArray(), ("`````88888888888*``*88*``````").toCharArray(), (
                    "````888888888888``88888*`````").toCharArray(), ("````8888888888*88888888``````").toCharArray(),
            ("``````*888888888888888```````").toCharArray(), ("```````*8*`88888`888*````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray()
            , ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("```````````*`````````````````").toCharArray(),
            ("````````8888888*`````````````").toCharArray(), ("``````*88888888*`````````````").toCharArray(), (
                    "`````*8888888888*```*8*``````").toCharArray(), ("````*88888888888``*8888*`````").toCharArray(),
            ("````8888888888888888888*`````").toCharArray(), ("````**88888888888888888``````").toCharArray(), (
                    "```````88888888888888*```````").toCharArray(), ("``````````88888*`*8*`````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````*88***``````````````").toCharArray(), (
                    "```````*8888888*`````````````").toCharArray(), ("``````8888888888*````````````").toCharArray(),
            ("`````88888888888*``*888*`````").toCharArray(), ("````*88888888888*888888*`````").toCharArray(), (
                    "````*888888888888888888``````").toCharArray(), ("``````*888888888888888```````").toCharArray(),
            ("```````*8*8888888888*````````").toCharArray(), ("`````````88888*``````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray()
            , ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "```````````*`````````````````").toCharArray(), ("````````*8888**``````````````").toCharArray(),
            ("```````88888888*`````````````").toCharArray(), ("`````*8888888888*```***``````").toCharArray(), (
                    "`````88888888888*`*8888*`````").toCharArray(), ("````*888888888888888888*`````").toCharArray(),
            ("````**88888888888888888``````").toCharArray(), ("```````88888888888888*```````").toCharArray(), (
                    "`````````88888888*8*`````````").toCharArray(), ("````````*8888`88`````````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````*88*````````````````").toCharArray(), ("```````*88888**``````````````").toCharArray(), (
                    "``````888888888*`````````````").toCharArray(), ("`````88888888888*``*888*`````").toCharArray(),
            ("````*888888888888888888*`````").toCharArray(), ("````*888888888888888888``````").toCharArray(), (
                    "``````*888888888888888```````").toCharArray(), ("```````**88888888888*````````").toCharArray(),
            ("````````*88888888```88```````").toCharArray(), ("````````*888*888```88````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray()
            , ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("```````````*`````````````````").toCharArray(), (
                    "````````*888*````````````````").toCharArray(), ("```````8888888*``````````````").toCharArray(),
            ("`````*888888888*`````**``````").toCharArray(), ("`````88888888888***8888*`````").toCharArray(), (
                    "````*888888888888888888*`````").toCharArray(), ("`````*8888888888888888*``````").toCharArray(),
            ("```````*8888888888888*```````").toCharArray(), ("````````*888888888**888``````").toCharArray(), (
                    "````````*88888888``888```````").toCharArray(), ("````````*88**88*``888````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("``````````***````````````````").toCharArray(),
            ("````````88888*```````````````").toCharArray(), ("``````*8888888*``````````````").toCharArray(), (
                    "`````*888888888*````*8*``````").toCharArray(), ("`````888888888888888888*`````").toCharArray(),
            ("`````*88888888888888888``````").toCharArray(), ("``````**88888888888888```````").toCharArray(), (
                    "````````*88888888888888*`````").toCharArray(), ("````````*888888888`8888``````").toCharArray(),
            ("````````*8888888*`8888```````").toCharArray(), ("````````888`*8*``*88*````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray()
            , ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````*88*````````````````").toCharArray(), (
                    "```````*88888*```````````````").toCharArray(), ("``````88888888*``````````````").toCharArray(),
            ("`````*8888888888****88*``````").toCharArray(), ("`````*88888888888888888``````").toCharArray(), (
                    "`````**888888888888888*``````").toCharArray(), ("```````*888888888888888*`````").toCharArray(),
            ("````````*88888888888888*`````").toCharArray(), ("````````*88888888`88888``````").toCharArray(), (
                    "````````8888888*`*888*```````").toCharArray(), ("```````*88*`88```888`````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "```````````*`````````````````").toCharArray(), ("````````*888*````````````````").toCharArray(),
            ("```````888888*```````````````").toCharArray(), ("``````888888888*`````*```````").toCharArray(), (
                    "`````*8888888888888888*``````").toCharArray(), ("`````*8888888888888888*``````").toCharArray(),
            ("```````*88888888888888*``````").toCharArray(), ("````````*88888888888888*`````").toCharArray(), (
                    "````````*888888888*8888``````").toCharArray(), ("````````88888888*`8888```````").toCharArray(),
            ("```````*8888888``8888````````").toCharArray(), ("```````*88`888``*88*`````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray()
            , ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````***`````````````````").toCharArray(), ("```````*8888*````````````````").toCharArray(), (
                    "``````*888888**``````````````").toCharArray(), ("`````*8888888888*******``````").toCharArray(),
            ("`````*8888888888888888*``````").toCharArray(), ("``````**88888888888888*``````").toCharArray(), (
                    "````````*88888888888888*`````").toCharArray(), ("````````*88888888888888*`````").toCharArray(),
            ("````````888888888*88888``````").toCharArray(), ("```````*88888888`88888```````").toCharArray(), (
                    "```````88888888`*888*````````").toCharArray(), ("``````8888*88*``*8*``````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "````````*88*`````````````````").toCharArray(), ("```````88888*````````````````").toCharArray(),
            ("``````88888888**`````````````").toCharArray(), ("`````*888888888888888**``````").toCharArray(), (
                    "``````*888888888888888*``````").toCharArray(), ("````````*888888888888**``````").toCharArray(),
            ("````````*88888888888888*`````").toCharArray(), ("````````*88888888888888*`````").toCharArray(), (
                    "````````888888888*8888*``````").toCharArray(), ("```````888888888*8888*```````").toCharArray(),
            ("``````88888888*`8888`````````").toCharArray(), ("`````8888**8*````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray()
            , ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("``````````*``````````````````").toCharArray(),
            ("````````888*`````````````````").toCharArray(), ("``````*88888**```````````````").toCharArray(), (
                    "``````888888888******````````").toCharArray(), ("``````*88888888888888**``````").toCharArray(),
            ("```````**888888888888*```````").toCharArray(), ("````````*88888888888*88*`````").toCharArray(), (
                    "````````*88888888888888*`````").toCharArray(), ("````````888888888*88888``````").toCharArray(),
            ("```````888888888*88888```````").toCharArray(), ("``````888888888*88888````````").toCharArray(), (
                    "`````88888888*``*8*``````````").toCharArray(), ("````*88888```````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("`````````**``````````````````").toCharArray(), (
                    "```````*888*`````````````````").toCharArray(), ("``````*888888**``````````````").toCharArray(),
            ("``````*888888888888**````````").toCharArray(), ("```````**888888888888*```````").toCharArray(), (
                    "````````*88888888888***``````").toCharArray(), ("````````*8888888888*888*`````").toCharArray(),
            ("````````*88888888888888*`````").toCharArray(), ("```````*88888888888888*``````").toCharArray(), (
                    "``````*888888888*8888*```````").toCharArray(), ("`````888888888*`8888`````````").toCharArray(),
            ("````*8888888*88``````````````").toCharArray(), ("````888888``88```````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray()
            , ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("````````*8*``````````````````").toCharArray(),
            ("```````8888**````````````````").toCharArray(), ("``````*8888888*****``````````").toCharArray(), (
                    "```````***888888888**````````").toCharArray(), ("````````*88888888888*````````").toCharArray(),
            ("````````*8888888888*****`````").toCharArray(), ("````````*888888888*8888*`````").toCharArray(), (
                    "````````888888888888888``````").toCharArray(), ("```````888888888*88888```````").toCharArray(),
            ("``````888888888**888*````````").toCharArray(), ("`````88888888888***``````````").toCharArray(), (
                    "````8888888`888````88````````").toCharArray(), ("````*8888*`888````88`````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("````````88*``````````````````").toCharArray(), (
                    "```````888****```````````````").toCharArray(), ("```````****8888888*``````````").toCharArray(),
            ("`````````*888888888**````````").toCharArray(), ("````````*8888888888*`````````").toCharArray(), (
                    "````````*888888888**888*`````").toCharArray(), ("````````*88888888888888``````").toCharArray(),
            ("```````*88888888888888*``````").toCharArray(), ("``````8888888888*8888````````").toCharArray(), (
                    "`````88888888888888*`````````").toCharArray(), ("````*88888888888```888```````").toCharArray(),
            ("````88888888888```888````````").toCharArray(), ("`````**8*8888*```888`````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray()
            , ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````*```````````````````").toCharArray(), ("```````*8**``````````````````").toCharArray(),
            ("```````***********```````````").toCharArray(), ("``````````*8888888*``````````").toCharArray(), (
                    "`````````*888888888*`````````").toCharArray(), ("````````*888888888*``**``````").toCharArray(),
            ("````````*888888888*8888``````").toCharArray(), ("````````88888888888888*``````").toCharArray(), (
                    "```````888888888*8888*```````").toCharArray(), ("`````*88888888888888*````````").toCharArray(),
            ("`````888888888888*`*88*``````").toCharArray(), ("````888888888888``8888```````").toCharArray(), (
                    "````*888888888*``88888```````").toCharArray(), ("````````8888*```*8888````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("````````**```````````````````").toCharArray(), ("```````***```````````````````").toCharArray(), (
                    "```````````***888*```````````").toCharArray(), ("``````````*8888888*``````````").toCharArray(),
            ("`````````*88888888*``````````").toCharArray(), ("````````*888888888*`*8*``````").toCharArray(), (
                    "````````*888888888*888*``````").toCharArray(), ("```````*888888888*8888```````").toCharArray(),
            ("``````888888888888888````````").toCharArray(), ("`````8888888888888***8*``````").toCharArray(), (
                    "````*88888888888*`*888*``````").toCharArray(), ("````88888888888``888888``````").toCharArray(),
            ("````**8888888*`8888888```````").toCharArray(), ("```````*888```88*8888````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray()
            , ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "````````*````````````````````").toCharArray(), ("```````````````**````````````").toCharArray(),
            ("```````````**8888*```````````").toCharArray(), ("``````````*8888888*``````````").toCharArray(), (
                    "`````````*88888888*``````````").toCharArray(), ("````````*888888888**88*``````").toCharArray(),
            ("````````888888888**888```````").toCharArray(), ("```````888888888*8888*```````").toCharArray(), (
                    "`````*8888888888888**````````").toCharArray(), ("`````888888888888*`888*``````").toCharArray(),
            ("````888888888888``88888*`````").toCharArray(), ("````8888888888*88888888``````").toCharArray(), (
                    "``````*888888888888888```````").toCharArray(), ("```````*8*`88888`888*````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("``````````````*8*````````````").toCharArray(), (
                    "```````````*88888*```````````").toCharArray(), ("``````````*8888888*``````````").toCharArray(),
            ("`````````888888888*``*```````").toCharArray(), ("````````*88888888***88```````").toCharArray(), (
                    "```````*88888888**888*```````").toCharArray(), ("``````88888888888888*````````").toCharArray(),
            ("`````888888888888****8*``````").toCharArray(), ("````*88888888888*`*8888*`````").toCharArray(), (
                    "````8888888888888888888*`````").toCharArray(), ("````**88888888888888888``````").toCharArray(),
            ("```````88888888888888*```````").toCharArray(), ("``````````88888*`*8*`````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray()
            , ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````*88*````````````").toCharArray(),
            ("```````````*88888*```````````").toCharArray(), ("``````````88888888*``````````").toCharArray(), (
                    "`````````88888888*``**```````").toCharArray(), ("````````88888888***88*```````").toCharArray(),
            ("```````888888888**88*````````").toCharArray(), ("`````*88888888888***`````````").toCharArray(), (
                    "`````88888888888*``*888*`````").toCharArray(), ("````888888888888*888888*`````").toCharArray(),
            ("````8888888888888888888``````").toCharArray(), ("``````*888888888888888```````").toCharArray(), (
                    "```````*8*8888888888*````````").toCharArray(), ("`````````88888*``````````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("```````````````*`````````````").toCharArray(), ("````````````*888*````````````").toCharArray(), (
                    "```````````888888*```````````").toCharArray(), ("`````````*8888888*```````````").toCharArray(),
            ("````````*8888888*``***```````").toCharArray(), ("```````*88888888***8*````````").toCharArray(), (
                    "``````8888888888****`````````").toCharArray(), ("`````88888888888*```***``````").toCharArray(),
            ("````*88888888888*`*8888*`````").toCharArray(), ("````8888888888888888888*`````").toCharArray(), (
                    "````**88888888888888888``````").toCharArray(), ("```````88888888888888*```````").toCharArray(),
            ("`````````88888888*8*`````````").toCharArray(), ("````````*8888`88`````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray()
            , ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "``````````````**`````````````").toCharArray(), ("````````````8888*````````````").toCharArray(),
            ("``````````*888888*```````````").toCharArray(), ("`````````8888888*````````````").toCharArray(), (
                    "````````88888888*``**````````").toCharArray(), ("```````888888888****`````````").toCharArray(),
            ("`````*8888888888*````````````").toCharArray(), ("`````88888888888*``*888*`````").toCharArray(), (
                    "````*888888888888888888*`````").toCharArray(), ("````*888888888888888888``````").toCharArray(),
            ("``````*888888888888888```````").toCharArray(), ("```````**88888888888*````````").toCharArray(), (
                    "````````*88888888```88```````").toCharArray(), ("````````*888*888```88````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("`````````````*8*`````````````").toCharArray(), ("```````````*8888*````````````").toCharArray(), (
                    "``````````888888*````````````").toCharArray(), ("````````*8888888*````````````").toCharArray(),
            ("```````*88888888*``*`````````").toCharArray(), ("``````8888888888*````````````").toCharArray(), (
                    "`````88888888888*````**``````").toCharArray(), ("````*88888888888***8888*`````").toCharArray(),
            ("````*888888888888888888*`````").toCharArray(), ("````**8888888888888888*``````").toCharArray(), (
                    "```````*8888888888888*```````").toCharArray(), ("````````*888888888**888``````").toCharArray(),
            ("````````*88888888``888```````").toCharArray(), ("````````*88**88*``888````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray()
            , ("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "````````````*88*`````````````").toCharArray(), ("```````````88888*````````````").toCharArray(),
            ("`````````*888888*````````````").toCharArray(), ("````````88888888*````````````").toCharArray(), (
                    "```````888888888*````````````").toCharArray(), ("`````*8888888888*````````````").toCharArray(),
            ("`````88888888888*```*8*``````").toCharArray(), ("````*888888888888888888*`````").toCharArray(), (
                    "````*888888888888888888``````").toCharArray(), ("``````*888888888888888```````").toCharArray(),
            ("````````*88888888888888*`````").toCharArray(), ("````````*888888888`8888``````").toCharArray(), (
                    "````````*8888888*`8888```````").toCharArray(), ("````````888`*8*``*88*````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("``````````````*``````````````").toCharArray(),
            ("````````````888*`````````````").toCharArray(), ("``````````*88888*````````````").toCharArray(), (
                    "`````````8888888*````````````").toCharArray(), ("```````*88888888*````````````").toCharArray(),
            ("``````*888888888*````````````").toCharArray(), ("`````*8888888888*````````````").toCharArray(), (
                    "`````88888888888****88*``````").toCharArray(), ("````*888888888888888888``````").toCharArray(),
            ("`````**888888888888888*``````").toCharArray(), ("```````*888888888888888*`````").toCharArray(), (
                    "````````*88888888888888*`````").toCharArray(), ("````````*88888888`88888``````").toCharArray(),
            ("````````8888888*`*888*```````").toCharArray(), ("```````*88*`88```888`````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray()
            , ("`````````````````````````````").toCharArray(), ("`````````````***`````````````").toCharArray(), (
                    "```````````*888*`````````````").toCharArray(), ("``````````888888*````````````").toCharArray(),
            ("````````*8888888*````````````").toCharArray(), ("```````888888888*````````````").toCharArray(), (
                    "``````8888888888*````````````").toCharArray(), ("`````88888888888*````*```````").toCharArray(),
            ("````*88888888888888888*``````").toCharArray(), ("````*88888888888888888*``````").toCharArray(), (
                    "```````*88888888888888*``````").toCharArray(), ("````````*88888888888888*`````").toCharArray(),
            ("````````*888888888*8888``````").toCharArray(), ("````````88888888*`8888```````").toCharArray(), (
                    "```````*8888888``8888````````").toCharArray(), ("```````*88`888``*88*`````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("````````````*88*`````````````").toCharArray(),
            ("```````````8888*`````````````").toCharArray(), ("`````````*888888*````````````").toCharArray(), (
                    "````````88888888*````````````").toCharArray(), ("``````*888888888*````````````").toCharArray(),
            ("`````*8888888888*````````````").toCharArray(), ("`````88888888888*******``````").toCharArray(), (
                    "````*88888888888888888*``````").toCharArray(), ("`````***88888888888888*``````").toCharArray(),
            ("````````*88888888888888*`````").toCharArray(), ("````````*88888888888888*`````").toCharArray(), (
                    "````````888888888*88888``````").toCharArray(), ("```````*88888888`88888```````").toCharArray(),
            ("```````88888888`*888*````````").toCharArray(), ("``````8888*88*``*8*``````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray()
            , ("``````````````*``````````````").toCharArray(), ("````````````888*`````````````").toCharArray(), (
                    "``````````*8888*`````````````").toCharArray(), ("````````*8888888*````````````").toCharArray(),
            ("```````*88888888*````````````").toCharArray(), ("``````8888888888*````````````").toCharArray(), (
                    "`````*8888888888*````````````").toCharArray(), ("`````8888888888888888**``````").toCharArray(),
            ("`````*8888888888888888*``````").toCharArray(), ("````````*888888888888**``````").toCharArray(), (
                    "````````*88888888888888*`````").toCharArray(), ("````````*88888888888888*`````").toCharArray(),
            ("````````888888888*8888*``````").toCharArray(), ("```````888888888*8888*```````").toCharArray(), (
                    "``````88888888*`8888`````````").toCharArray(), ("`````8888**8*````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "`````````````**``````````````").toCharArray(), ("```````````*888*`````````````").toCharArray(),
            ("`````````*88888*`````````````").toCharArray(), ("````````88888888*````````````").toCharArray(), (
                    "```````88888888**````````````").toCharArray(), ("``````8888888888*````````````").toCharArray(),
            ("`````*8888888888*****````````").toCharArray(), ("`````*888888888888888**``````").toCharArray(), (
                    "``````***888888888888*```````").toCharArray(), ("````````*88888888888*88*`````").toCharArray(),
            ("````````*88888888888888*`````").toCharArray(), ("````````888888888*88888``````").toCharArray(), (
                    "```````888888888*88888```````").toCharArray(), ("``````888888888*88888````````").toCharArray(),
            ("`````88888888*``*8*``````````").toCharArray(), ("````*88888```````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray()
            , ("````````````*8*``````````````").toCharArray(), ("``````````*8888*`````````````").toCharArray(), (
                    "`````````888888*`````````````").toCharArray(), ("```````*8888888*`````````````").toCharArray(),
            ("``````*8888888***````````````").toCharArray(), ("``````8888888888*````````````").toCharArray(), (
                    "`````*8888888888888**````````").toCharArray(), ("``````*88888888888888*```````").toCharArray(),
            ("````````*88888888888***``````").toCharArray(), ("````````*8888888888*888*`````").toCharArray(), (
                    "````````*88888888888888*`````").toCharArray(), ("```````*88888888888888*``````").toCharArray(),
            ("``````*888888888*8888*```````").toCharArray(), ("`````888888888*`8888`````````").toCharArray(), (
                    "````*8888888*88``````````````").toCharArray(), ("````888888``88```````````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("`````````````````````````````").toCharArray(), (
                    "```````````*88*``````````````").toCharArray(), ("``````````88888*`````````````").toCharArray(),
            ("````````*888888*`````````````").toCharArray(), ("```````8888888*``````````````").toCharArray(), (
                    "``````*888888****````````````").toCharArray(), ("``````8888888888***``````````").toCharArray(),
            ("``````*888888888888**````````").toCharArray(), ("```````**88888888888*````````").toCharArray(), (
                    "````````*8888888888*****`````").toCharArray(), ("````````*888888888*8888*`````").toCharArray(),
            ("````````888888888888888``````").toCharArray(), ("```````888888888*88888```````").toCharArray(), (
                    "``````888888888**888*````````").toCharArray(), ("`````88888888888***``````````").toCharArray(),
            ("````8888888`888````88````````").toCharArray(), ("````*8888*`888````88`````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("`````````````*```````````````").toCharArray()
            , ("```````````888*``````````````").toCharArray(), ("`````````*88888*`````````````").toCharArray(), (
                    "````````888888*``````````````").toCharArray(), ("```````888888*```````````````").toCharArray(),
            ("``````*88888*****````````````").toCharArray(), ("``````*88888888888*``````````").toCharArray(), (
                    "```````***888888888**````````").toCharArray(), ("````````*8888888888*`````````").toCharArray(),
            ("````````*888888888**888*`````").toCharArray(), ("````````*88888888888888``````").toCharArray(), (
                    "```````*88888888888888*``````").toCharArray(), ("``````8888888888*8888````````").toCharArray(),
            ("`````88888888888888*`````````").toCharArray(), ("````*88888888888```888```````").toCharArray(), (
                    "````88888888888```888````````").toCharArray(), ("`````**8*8888*```888`````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("````````````**```````````````").toCharArray(), (
                    "``````````*888*``````````````").toCharArray(), ("`````````88888*``````````````").toCharArray(),
            ("```````*88888*```````````````").toCharArray(), ("```````88888*````````````````").toCharArray(), (
                    "``````*8888*******```````````").toCharArray(), ("```````****8888888*``````````").toCharArray(),
            ("`````````*888888888*`````````").toCharArray(), ("````````*888888888*``**``````").toCharArray(), (
                    "````````*888888888*8888``````").toCharArray(), ("````````88888888888888*``````").toCharArray(),
            ("```````888888888*8888*```````").toCharArray(), ("`````*88888888888888*````````").toCharArray(), (
                    "`````888888888888*`*88*``````").toCharArray(), ("````888888888888``8888```````").toCharArray(),
            ("````*888888888*``88888```````").toCharArray(), ("````````8888*```*8888````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {("```````````***```````````````").toCharArray()
            , ("``````````8888*``````````````").toCharArray(), ("````````*8888*```````````````").toCharArray(), (
                    "```````*8888*````````````````").toCharArray(), ("``````*8888*`````````````````").toCharArray(),
            ("``````********888*```````````").toCharArray(), ("``````````*8888888*``````````").toCharArray(), (
                    "`````````*88888888*``````````").toCharArray(), ("````````*888888888*`*8*``````").toCharArray(),
            ("````````*888888888*888*``````").toCharArray(), ("```````*888888888*8888```````").toCharArray(), (
                    "``````888888888888888````````").toCharArray(), ("`````8888888888888***8*``````").toCharArray(),
            ("````*88888888888*`*888*``````").toCharArray(), ("````88888888888``888888``````").toCharArray(), (
                    "````**8888888*`8888888```````").toCharArray(), ("```````*888```88*8888````````").toCharArray(),
            ("`````````````````````````````").toCharArray()}, {("```````````***```````````````").toCharArray(), (
                    "`````````*888*```````````````").toCharArray(), ("````````8888*````````````````").toCharArray(),
            ("```````8888*`````````````````").toCharArray(), ("``````*88**````**````````````").toCharArray(), (
                    "```````````**8888*```````````").toCharArray(), ("``````````*8888888*``````````").toCharArray(),
            ("`````````*88888888*``````````").toCharArray(), ("````````*888888888**88*``````").toCharArray(), (
                    "````````888888888**888```````").toCharArray(), ("```````888888888*8888*```````").toCharArray(),
            ("`````*8888888888888**````````").toCharArray(), ("`````888888888888*`888*``````").toCharArray(), (
                    "````888888888888``88888*`````").toCharArray(), ("````8888888888*88888888``````").toCharArray(),
            ("``````*888888888888888```````").toCharArray(), ("```````*8*`88888`888*````````").toCharArray(), (
                    "`````````````````````````````").toCharArray()}, {//loop
            ("``````````****```````````````").toCharArray(), ("`````````888*````````````````").toCharArray(), (
                    "```````*888*`````````````````").toCharArray(), ("```````888*``````````````````").toCharArray(),
            ("```````*``````*8*````````````").toCharArray(), ("```````````*88888*```````````").toCharArray(), (
                    "``````````*8888888*``````````").toCharArray(), ("`````````888888888*``*```````").toCharArray(),
            ("````````*88888888***88```````").toCharArray(), ("```````*88888888**888*```````").toCharArray(), (
                    "``````88888888888888*````````").toCharArray(), ("`````888888888888****8*``````").toCharArray(),
            ("````*88888888888*`*8888*`````").toCharArray(), ("````8888888888888888888*`````").toCharArray(), (
                    "````**88888888888888888``````").toCharArray(), ("```````88888888888888*```````").toCharArray(),
            ("``````````88888*`*8*`````````").toCharArray(), ("`````````````````````````````").toCharArray()},//loop
            {("``````````***````````````````").toCharArray(), ("````````*88*`````````````````").toCharArray(), (
                    "```````*88*``````````````````").toCharArray(), ("```````**````````````````````").toCharArray(),
                    ("`````````````*88*````````````").toCharArray(), ("```````````*88888*```````````").toCharArray(),
                    ("``````````88888888*``````````").toCharArray(), ("`````````88888888*``**```````").toCharArray(),
                    ("````````88888888***88*```````").toCharArray(), ("```````888888888**88*````````").toCharArray(),
                    ("`````*88888888888***`````````").toCharArray(), ("`````88888888888*``*888*`````").toCharArray(),
                    ("````888888888888*888888*`````").toCharArray(), ("````8888888888888888888``````").toCharArray(),
                    ("``````*888888888888888```````").toCharArray(), ("```````*8*8888888888*````````").toCharArray(),
                    ("`````````88888*``````````````").toCharArray(), ("`````````````````````````````").toCharArray()}
            , {("`````````*8*`````````````````").toCharArray(), ("````````888``````````````````").toCharArray(), (
                    "```````*8*```````````````````").toCharArray(), ("```````````````*`````````````").toCharArray(),
            ("````````````*888*````````````").toCharArray(), ("```````````888888*```````````").toCharArray(), (
                    "`````````*8888888*```````````").toCharArray(), ("````````*8888888*``***```````").toCharArray(),
            ("```````*88888888***8*````````").toCharArray(), ("``````8888888888****`````````").toCharArray(), (
                    "`````88888888888*```***``````").toCharArray(), ("````*88888888888*`*8888*`````").toCharArray(),
            ("````8888888888888888888*`````").toCharArray(), ("````**88888888888888888``````").toCharArray(), (
                    "```````88888888888888*```````").toCharArray(), ("`````````88888888*8*`````````").toCharArray(),
            ("````````*8888`88`````````````").toCharArray(), ("`````````````````````````````").toCharArray()}, {(
                    "`````````88``````````````````").toCharArray(), ("````````88```````````````````").toCharArray(),
            ("`````````````````````````````").toCharArray(), ("``````````````**`````````````").toCharArray(), (
                    "````````````8888*````````````").toCharArray(), ("``````````*888888*```````````").toCharArray(),
            ("`````````8888888*````````````").toCharArray(), ("````````88888888*``**````````").toCharArray(), (
                    "```````888888888****`````````").toCharArray(), ("`````*8888888888*````````````").toCharArray(),
            ("`````88888888888*``*888*`````").toCharArray(), ("````*888888888888888888*`````").toCharArray(), (
                    "````*888888888888888888``````").toCharArray(), ("``````*888888888888888```````").toCharArray(),
            ("```````**88888888888*````````").toCharArray(), ("````````*88888888```88```````").toCharArray(), (
                    "````````*888*888```88````````").toCharArray(), ("`````````````````````````````").toCharArray()},
            {("````````*8*``````````````````").toCharArray(), ("````````*````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("`````````````*8*`````````````").toCharArray(),
                    ("```````````*8888*````````````").toCharArray(), ("``````````888888*````````````").toCharArray(),
                    ("````````*8888888*````````````").toCharArray(), ("```````*88888888*``*`````````").toCharArray(),
                    ("``````8888888888*````````````").toCharArray(), ("`````88888888888*````**``````").toCharArray(),
                    ("````*88888888888***8888*`````").toCharArray(), ("````*888888888888888888*`````").toCharArray(),
                    ("````**8888888888888888*``````").toCharArray(), ("```````*8888888888888*```````").toCharArray(),
                    ("````````*888888888**888``````").toCharArray(), ("````````*88888888``888```````").toCharArray(),
                    ("````````*88**88*``888````````").toCharArray(), ("`````````````````````````````").toCharArray()}
            , {("````````**```````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````````````````````").toCharArray(), ("````````````*88*`````````````").toCharArray(),
            ("```````````88888*````````````").toCharArray(), ("`````````*888888*````````````").toCharArray(), (
                    "````````88888888*````````````").toCharArray(), ("```````888888888*````````````").toCharArray(),
            ("`````*8888888888*````````````").toCharArray(), ("`````88888888888*```*8*``````").toCharArray(), (
                    "````*888888888888888888*`````").toCharArray(), ("````*888888888888888888``````").toCharArray(),
            ("``````*888888888888888```````").toCharArray(), ("````````*88888888888888*`````").toCharArray(), (
                    "````````*888888888`8888``````").toCharArray(), ("````````*8888888*`8888```````").toCharArray(),
            ("````````888`*8*``*88*````````").toCharArray(), ("`````````````````````````````").toCharArray()}, {(
                    "`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(),
            ("``````````````*``````````````").toCharArray(), ("````````````888*`````````````").toCharArray(), (
                    "``````````*88888*````````````").toCharArray(), ("`````````8888888*````````````").toCharArray(),
            ("```````*88888888*````````````").toCharArray(), ("``````*888888888*````````````").toCharArray(), (
                    "`````*8888888888*````````````").toCharArray(), ("`````88888888888****88*``````").toCharArray(),
            ("````*888888888888888888``````").toCharArray(), ("`````**888888888888888*``````").toCharArray(), (
                    "```````*888888888888888*`````").toCharArray(), ("````````*88888888888888*`````").toCharArray(),
            ("````````*88888888`88888``````").toCharArray(), ("````````8888888*`*888*```````").toCharArray(), (
                    "```````*88*`88```888`````````").toCharArray(), ("`````````````````````````````").toCharArray()},
            {("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "`````````````***`````````````").toCharArray(), ("```````````*888*`````````````").toCharArray(),
                    ("``````````888888*````````````").toCharArray(), ("````````*8888888*````````````").toCharArray(),
                    ("```````888888888*````````````").toCharArray(), ("``````8888888888*````````````").toCharArray(),
                    ("`````88888888888*````*```````").toCharArray(), ("````*88888888888888888*``````").toCharArray(),
                    ("````*88888888888888888*``````").toCharArray(), ("```````*88888888888888*``````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(), ("````````*888888888*8888``````").toCharArray(),
                    ("````````88888888*`8888```````").toCharArray(), ("```````*8888888``8888````````").toCharArray(),
                    ("```````*88`888``*88*`````````").toCharArray(), ("`````````````````````````````").toCharArray()}
            , {("`````````````````````````````").toCharArray(), ("`````````````````````````````").toCharArray(), (
                    "````````````*88*`````````````").toCharArray(), ("```````````8888*`````````````").toCharArray(),
            ("`````````*888888*````````````").toCharArray(), ("````````88888888*````````````").toCharArray(), (
                    "``````*888888888*````````````").toCharArray(), ("`````*8888888888*````````````").toCharArray(),
            ("`````88888888888*******``````").toCharArray(), ("````*88888888888888888*``````").toCharArray(), (
                    "`````***88888888888888*``````").toCharArray(), ("````````*88888888888888*`````").toCharArray(),
            ("````````*88888888888888*`````").toCharArray(), ("````````888888888*88888``````").toCharArray(), (
                    "```````*88888888`88888```````").toCharArray(), ("```````88888888`*888*````````").toCharArray(),
            ("``````8888*88*``*8*``````````").toCharArray(), ("`````````````````````````````").toCharArray()}, {(
                    "`````````````````````````````").toCharArray(), ("``````````````*``````````````").toCharArray(),
            ("````````````888*`````````````").toCharArray(), ("``````````*8888*`````````````").toCharArray(), (
                    "````````*8888888*````````````").toCharArray(), ("```````*88888888*````````````").toCharArray(),
            ("``````8888888888*````````````").toCharArray(), ("`````*8888888888*````````````").toCharArray(), (
                    "`````8888888888888888**``````").toCharArray(), ("`````*8888888888888888*``````").toCharArray(),
            ("````````*888888888888**``````").toCharArray(), ("````````*88888888888888*`````").toCharArray(), (
                    "````````*88888888888888*`````").toCharArray(), ("````````888888888*8888*``````").toCharArray(),
            ("```````888888888*8888*```````").toCharArray(), ("``````88888888*`8888`````````").toCharArray(), (
                    "`````8888**8*````````````````").toCharArray(), ("`````````````````````````````").toCharArray()},
            {("`````````````````````````````").toCharArray(), ("`````````````**``````````````").toCharArray(), (
                    "```````````*888*`````````````").toCharArray(), ("`````````*88888*`````````````").toCharArray(),
                    ("````````88888888*````````````").toCharArray(), ("```````88888888**````````````").toCharArray(),
                    ("``````8888888888*````````````").toCharArray(), ("`````*8888888888*****````````").toCharArray(),
                    ("`````*888888888888888**``````").toCharArray(), ("``````***888888888888*```````").toCharArray(),
                    ("````````*88888888888*88*`````").toCharArray(), ("````````*88888888888888*`````").toCharArray(),
                    ("````````888888888*88888``````").toCharArray(), ("```````888888888*88888```````").toCharArray(),
                    ("``````888888888*88888````````").toCharArray(), ("`````88888888*``*8*``````````").toCharArray(),
                    ("````*88888```````````````````").toCharArray(), ("`````````````````````````````").toCharArray()}
            , {("`````````````````````````````").toCharArray(), ("````````````*8*``````````````").toCharArray(), (
                    "``````````*8888*`````````````").toCharArray(), ("`````````888888*`````````````").toCharArray(),
            ("```````*8888888*`````````````").toCharArray(), ("``````*8888888***````````````").toCharArray(), (
                    "``````8888888888*````````````").toCharArray(), ("`````*8888888888888**````````").toCharArray(),
            ("``````*88888888888888*```````").toCharArray(), ("````````*88888888888***``````").toCharArray(), (
                    "````````*8888888888*888*`````").toCharArray(), ("````````*88888888888888*`````").toCharArray(),
            ("```````*88888888888888*``````").toCharArray(), ("``````*888888888*8888*```````").toCharArray(), (
                    "`````888888888*`8888`````````").toCharArray(), ("````*8888888*88``````````````").toCharArray(),
            ("````888888``88```````````````").toCharArray(), ("`````````````````````````````").toCharArray()}, {(
                    "`````````````````````````````").toCharArray(), ("```````````*88*``````````````").toCharArray(),
            ("``````````88888*`````````````").toCharArray(), ("````````*888888*`````````````").toCharArray(), (
                    "```````8888888*``````````````").toCharArray(), ("``````*888888****````````````").toCharArray(),
            ("``````8888888888***``````````").toCharArray(), ("``````*888888888888**````````").toCharArray(), (
                    "```````**88888888888*````````").toCharArray(), ("````````*8888888888*****`````").toCharArray(),
            ("````````*888888888*8888*`````").toCharArray(), ("````````888888888888888``````").toCharArray(), (
                    "```````888888888*88888```````").toCharArray(), ("``````888888888**888*````````").toCharArray(),
            ("`````88888888888***``````````").toCharArray(), ("````8888888`888````88````````").toCharArray(), (
                    "````*8888*`888````88`````````").toCharArray(), ("`````````````````````````````").toCharArray()},
            {("`````````````*```````````````").toCharArray(), ("```````````888*``````````````").toCharArray(), (
                    "`````````*88888*`````````````").toCharArray(), ("````````888888*``````````````").toCharArray(),
                    ("```````888888*```````````````").toCharArray(), ("``````*88888*****````````````").toCharArray(),
                    ("``````*88888888888*``````````").toCharArray(), ("```````***888888888**````````").toCharArray(),
                    ("````````*8888888888*`````````").toCharArray(), ("````````*888888888**888*`````").toCharArray(),
                    ("````````*88888888888888``````").toCharArray(), ("```````*88888888888888*``````").toCharArray(),
                    ("``````8888888888*8888````````").toCharArray(), ("`````88888888888888*`````````").toCharArray(),
                    ("````*88888888888```888```````").toCharArray(), ("````88888888888```888````````").toCharArray(),
                    ("`````**8*8888*```888`````````").toCharArray(), ("`````````````````````````````").toCharArray()}
            , {("````````````**```````````````").toCharArray(), ("``````````*888*``````````````").toCharArray(), (
                    "`````````88888*``````````````").toCharArray(), ("```````*88888*```````````````").toCharArray(),
            ("```````88888*````````````````").toCharArray(), ("``````*8888*******```````````").toCharArray(), (
                    "```````****8888888*``````````").toCharArray(), ("`````````*888888888*`````````").toCharArray(),
            ("````````*888888888*``**``````").toCharArray(), ("````````*888888888*8888``````").toCharArray(), (
                    "````````88888888888888*``````").toCharArray(), ("```````888888888*8888*```````").toCharArray(),
            ("`````*88888888888888*````````").toCharArray(), ("`````888888888888*`*88*``````").toCharArray(), (
                    "````888888888888``8888```````").toCharArray(), ("````*888888888*``88888```````").toCharArray(),
            ("````````8888*```*8888````````").toCharArray(), ("`````````````````````````````").toCharArray()}, {(
                    "```````````***```````````````").toCharArray(), ("``````````8888*``````````````").toCharArray(),
            ("````````*8888*```````````````").toCharArray(), ("```````*8888*````````````````").toCharArray(), (
                    "``````*8888*`````````````````").toCharArray(), ("``````********888*```````````").toCharArray(),
            ("``````````*8888888*``````````").toCharArray(), ("`````````*88888888*``````````").toCharArray(), (
                    "````````*888888888*`*8*``````").toCharArray(), ("````````*888888888*888*``````").toCharArray(),
            ("```````*888888888*8888```````").toCharArray(), ("``````888888888888888````````").toCharArray(), (
                    "`````8888888888888***8*``````").toCharArray(), ("````*88888888888*`*888*``````").toCharArray(),
            ("````88888888888``888888``````").toCharArray(), ("````**8888888*`8888888```````").toCharArray(), (
                    "```````*888```88*8888````````").toCharArray(), ("`````````````````````````````").toCharArray()},
            {("```````````***```````````````").toCharArray(), ("`````````*888*```````````````").toCharArray(), (
                    "````````8888*````````````````").toCharArray(), ("```````8888*`````````````````").toCharArray(),
                    ("``````*88**````**````````````").toCharArray(), ("```````````**8888*```````````").toCharArray(),
                    ("``````````*8888888*``````````").toCharArray(), ("`````````*88888888*``````````").toCharArray(),
                    ("````````*888888888**88*``````").toCharArray(), ("````````888888888**888```````").toCharArray(),
                    ("```````888888888*8888*```````").toCharArray(), ("`````*8888888888888**````````").toCharArray(),
                    ("`````888888888888*`888*``````").toCharArray(), ("````888888888888``88888*`````").toCharArray(),
                    ("````8888888888*88888888``````").toCharArray(), ("``````*888888888888888```````").toCharArray(),
                    ("```````*8*`88888`888*````````").toCharArray(), ("`````````````````````````````").toCharArray()}};

}