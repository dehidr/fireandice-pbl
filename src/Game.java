import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import enigma.console.TextAttributes;
import java.awt.Color;

public class Game {
    public enigma.console.Console cn = Enigma.getConsole("Mouse and Keyboard", 226,60);
    public TextMouseListener tmlis;
    public KeyListener klis;

    GameField map = new GameField();

    private TextAttributes red    = new TextAttributes(Color.WHITE, Color.RED);
    private TextAttributes green  = new TextAttributes(Color.WHITE, Color.GREEN);
    private TextAttributes blue   = new TextAttributes(Color.WHITE, Color.BLUE);
    private TextAttributes yellow = new TextAttributes(Color.YELLOW);
    private TextAttributes darkgray = new TextAttributes(Color.DARK_GRAY);
    private TextAttributes gray = new TextAttributes(Color.GRAY);
    private TextAttributes lightgray = new TextAttributes(Color.white, Color.LIGHT_GRAY);
    private TextAttributes black = new TextAttributes(Color.white, Color.BLACK);
    private TextAttributes cyan   = new TextAttributes(Color.CYAN);
    private TextAttributes white   = new TextAttributes(Color.WHITE);
    private TextAttributes color = black;


    // ------ Standard variables for mouse and keyboard ------
    public int mousepr;          // mouse pressed?
    public int mousex, mousey;   // mouse text coords.
    public int keypr;   // key pressed?
    public int rkey;    // key   (for press/release)
    public boolean updated = true;
    public boolean menu = true;
    public int timer = 0;
    public int animationtimer = 0;
    public int menuselect = 0;
    // ----------------------------------------------------

    public void timer() { timer = (timer + 1) % 256; animationtimer = (animationtimer + 1) % 17; }
    public void refresh(){
        int rows = cn.getTextWindow().getRows();
        int cols = cn.getTextWindow().getColumns();

        for(int i = 0; i < rows; i++){ for(int j = 0; j < cols; j++) {cn.getTextWindow().output(j,i,' ');}}
    }

    public void drawArray(int x, int y, char[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                cn.getTextWindow().output(x + i, y + j, a[j][i]);
            }
        }
    }

    public void drawAnimation(int x, int y,int frame) {
        frame = (frame) % 63;
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 29; j++) {
                cn.getTextWindow().output(x + j, y + i, animation[frame][i][j]);
            }
        }
    }

    public void drawMap(int x, int y){
        for(int i = 0; i < 53; i++){
            for(int j = 0; j < 23;j++){
                cn.getTextWindow().output(x + i ,y + j , map.getMap()[j][i]);
            }
        }
    }

    public void drawMenu(int x, int y){
        char[][] logo = new char[][]{
                (" ▄▀▀▀█▄    ▄▀▀█▀▄    ▄▀▀▄▀▀▀▄  ▄▀▀█▄▄▄▄                              .o8       ██                        ").toCharArray(),
                ("█  ▄▀  ▀▄ █   █  █  █   █   █ ▐  ▄▀   ▐                             \"888       ▀▀                        ").toCharArray(),
                ("▐ █▄▄▄▄   ▐   █  ▐  ▐  █▀▀█▀    █▄▄▄▄▄    .oooo.   ooo. .oo.    .oooo888     ████      ▄█████▄   ▄████▄  ").toCharArray(),
                (" █    ▐       █      ▄▀    █    █    ▌   `P  )88b  `888P\"Y88b  d88' `888       ██     ██▀    ▀  ██▄▄▄▄██ ").toCharArray(),
                (" █         ▄▀▀▀▀▀▄  █     █    ▄▀▄▄▄▄     .oP\"888   888   888  888   888       ██     ██        ██▀▀▀▀▀▀ ").toCharArray(),
                ("█         █       █ ▐     ▐    █    ▐    d8(  888   888   888  888   888    ▄▄▄██▄▄▄  ▀██▄▄▄▄█  ▀██▄▄▄▄█ ").toCharArray(),
                ("▐         ▐       ▐            ▐         `Y888\"\"8o o888o o888o `Y8bod88P\"   ▀▀▀▀▀▀▀▀    ▀▀▀▀▀     ▀▀▀▀▀  ").toCharArray()
        };
        for(int i = 0; i < 105; i++){
            for(int j = 0; j < 7;j++){
                cn.getTextWindow().output(x + 2 + i ,y +j , logo[j][i]);
            }
        }
        drawBox(x, y - 2 , 11,109);
        drawBox(x, y + 9, 20,33);

        if(menuselect == 0 || menuselect == 4) color = yellow;
        drawBox(x + 34, y + 9, 5,75,"s t a r t");
        color = white;

        if(menuselect == 1) color = yellow;
        drawBox(x + 34, y + 14, 5,75,"a b o u t");
        color = white;

        if(menuselect == 2) color = yellow;
        drawBox(x + 34, y + 19, 5,75,"l o a d   m a p");
        color = white;

        if(menuselect == 3) color = yellow;
        drawBox(x + 34, y + 24, 5,75,"q u i t");
        color = white;

        //drawAnimation(x + 2,y+10, animationtimer + 46);
    }
    public void drawBox(int x, int y, int length, int width){
      /*
      ╭─────────────╮
      │             │
      │             │
      │             │
      ╰─────────────╯
       */
        for(int i = 0 ; i < length ; i++){
            if(i == 0 || i == length - 1){
                for(int j = 0 ; j < width; j++) { cn.getTextWindow().output( x + j , y + i , '─' , color); }
            }
            if( i > 0 && i < length - 1 ) { cn.getTextWindow().output( x + 0 , y + i , '│' , color); cn.getTextWindow().output( x + width - 1 , y + i , '│' , color); }
        }
        cn.getTextWindow().output( x , y , '╭' , color);
        cn.getTextWindow().output( x + width - 1, y , '╮' , color);
        cn.getTextWindow().output( x , y + length - 1 , '╰' , color);
        cn.getTextWindow().output( x + width - 1, y + length - 1 , '╯' , color);
    }
    public void drawBox(int x, int y, int length, int width, String text){
        drawBox(x, y, length, width);
        int offx = (width - text.length() + 1)/2;
        int offy = (length - length%2)/2;
        cn.getTextWindow().setCursorPosition(x + offx, y + offy);
        cn.getTextWindow().output(text,color);
    }
    Game() throws Exception {   // --- Contructor

        // ------ Standard code for mouse and keyboard ------ Do not change
        tmlis=new TextMouseListener() {
            public void mouseClicked(TextMouseEvent arg0) {}
            public void mousePressed(TextMouseEvent arg0) {
                if(mousepr==0) {
                    mousepr=1;
                    mousex=arg0.getX();
                    mousey=arg0.getY();
                }
            }
            public void mouseReleased(TextMouseEvent arg0) {}
        };
        cn.getTextWindow().addTextMouseListener(tmlis);

        klis=new KeyListener() {
            public void keyTyped(KeyEvent e) {}
            public void keyPressed(KeyEvent e) {
                if(keypr==0) {
                    keypr=1;
                    rkey=e.getKeyCode();
                }
            }
            public void keyReleased(KeyEvent e) {}
        };
        cn.getTextWindow().addKeyListener(klis);
        // ----------------------------------------------------


        int px=5,py=5;
        cn.getTextWindow().output(px,py,'P');

        while(true) {
            if(updated){ refresh(); }
            if (menu) {
                if(updated) { drawMenu(5,5); }
                drawAnimation(5 + 2,5+10, animationtimer + 46);
                updated = false;
            }

            if(updated && !menu) { refresh(); drawMap(5,5); updated = false;}



            if(mousepr==1) {  // if mouse button pressed
                //cn.getTextWindow().output(mousex,mousey,'#');  // write a char to x,y position without changing cursor position
                px=mousex; py=mousey;

                mousepr=0;     // last action
            }
            if(keypr==1) {    // if keyboard button pressed
                if(menu){
                    if(rkey==KeyEvent.VK_LEFT) { menuselect = (menuselect + 3) % 4; updated = true;}
                    if(rkey==KeyEvent.VK_RIGHT){ menuselect = (menuselect + 1) % 4; updated = true;}
                    if(rkey==KeyEvent.VK_UP)   { menuselect = (menuselect + 1) % 4; updated = true;}
                    if(rkey==KeyEvent.VK_DOWN) { menuselect = (menuselect + 3) % 4; updated = true;}

                }
                else {
                    if(rkey==KeyEvent.VK_LEFT) px--;
                    if(rkey==KeyEvent.VK_RIGHT) px++;
                    if(rkey==KeyEvent.VK_UP) py--;
                    if(rkey==KeyEvent.VK_DOWN) py++;

                    char rckey=(char)rkey;
                    //        left          right          up            down
                    if(rckey=='%' || rckey=='\'' || rckey=='&' || rckey=='(') cn.getTextWindow().output(px,py,'P'); // VK kullanmadan test teknigi
                    else cn.getTextWindow().output(rckey);

                    if(rkey==KeyEvent.VK_SPACE) {
                        String str;
                        str=cn.readLine();     // keyboardlistener running and readline input by using enter
                        cn.getTextWindow().setCursorPosition(5, 20);
                        cn.getTextWindow().output(str);
                    }

                    keypr=0;    // last action
                }



            }
            Thread.sleep(25);
            timer();
        }
    }

    char[][][] animation = new char[][][]{
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````88``````````````").toCharArray(),
                    ("````````````88```````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````888`````````````").toCharArray(),
                    ("`````````88`888``````````````").toCharArray(),
                    ("````````88`888```````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````*88*````````````").toCharArray(),
                    ("`````````8888888`````````````").toCharArray(),
                    ("```````88888888``````````````").toCharArray(),
                    ("``````8888*88*```````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("``````````````*8*````````````").toCharArray(),
                    ("`````````*888888*````````````").toCharArray(),
                    ("```````888888888`````````````").toCharArray(),
                    ("``````88888888*``````````````").toCharArray(),
                    ("`````8888**8*````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("``````````*8*888*````````````").toCharArray(),
                    ("```````*88888888`````````````").toCharArray(),
                    ("``````888888888``````````````").toCharArray(),
                    ("`````88888888*```````````````").toCharArray(),
                    ("````*88888```````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("``````````````*8*````````````").toCharArray(),
                    ("````````*8888888*````````````").toCharArray(),
                    ("``````*888888888`````````````").toCharArray(),
                    ("`````888888888*``````````````").toCharArray(),
                    ("````*8888888*88``````````````").toCharArray(),
                    ("````888888``88```````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````*88*888*````````````").toCharArray(),
                    ("```````888888888`````````````").toCharArray(),
                    ("``````888888888``````````````").toCharArray(),
                    ("`````88888888888`````````````").toCharArray(),
                    ("````8888888`888````88````````").toCharArray(),
                    ("````*8888*`888````88`````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("```````````*``***````````````").toCharArray(),
                    ("````````88888888*````````````").toCharArray(),
                    ("``````*88888888*`````````````").toCharArray(),
                    ("`````88888888888*````````````").toCharArray(),
                    ("````*88888888888```888```````").toCharArray(),
                    ("````88888888888```888````````").toCharArray(),
                    ("`````**8*8888*```888`````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````*88**88*````````````").toCharArray(),
                    ("```````888888888`````````````").toCharArray(),
                    ("``````8888888888*````````````").toCharArray(),
                    ("`````88888888888*``*88*``````").toCharArray(),
                    ("````888888888888``8888```````").toCharArray(),
                    ("````*888888888*``88888```````").toCharArray(),
                    ("````````8888*```*8888````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("```````````*```*`````````````").toCharArray(),
                    ("````````88888888`````````````").toCharArray(),
                    ("``````*88888888*`````````````").toCharArray(),
                    ("`````88888888888*```*8*``````").toCharArray(),
                    ("````*88888888888``*888*``````").toCharArray(),
                    ("````88888888888``888888``````").toCharArray(),
                    ("````**8888888*`8888888```````").toCharArray(),
                    ("```````*888```88*8888````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````*88****`````````````").toCharArray(),
                    ("```````88888888*`````````````").toCharArray(),
                    ("``````8888888888*````````````").toCharArray(),
                    ("`````88888888888*``*88*``````").toCharArray(),
                    ("````888888888888``88888*`````").toCharArray(),
                    ("````8888888888*88888888``````").toCharArray(),
                    ("``````*888888888888888```````").toCharArray(),
                    ("```````*8*`88888`888*````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("```````````*`````````````````").toCharArray(),
                    ("````````8888888*`````````````").toCharArray(),
                    ("``````*88888888*`````````````").toCharArray(),
                    ("`````*8888888888*```*8*``````").toCharArray(),
                    ("````*88888888888``*8888*`````").toCharArray(),
                    ("````8888888888888888888*`````").toCharArray(),
                    ("````**88888888888888888``````").toCharArray(),
                    ("```````88888888888888*```````").toCharArray(),
                    ("``````````88888*`*8*`````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````*88***``````````````").toCharArray(),
                    ("```````*8888888*`````````````").toCharArray(),
                    ("``````8888888888*````````````").toCharArray(),
                    ("`````88888888888*``*888*`````").toCharArray(),
                    ("````*88888888888*888888*`````").toCharArray(),
                    ("````*888888888888888888``````").toCharArray(),
                    ("``````*888888888888888```````").toCharArray(),
                    ("```````*8*8888888888*````````").toCharArray(),
                    ("`````````88888*``````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("```````````*`````````````````").toCharArray(),
                    ("````````*8888**``````````````").toCharArray(),
                    ("```````88888888*`````````````").toCharArray(),
                    ("`````*8888888888*```***``````").toCharArray(),
                    ("`````88888888888*`*8888*`````").toCharArray(),
                    ("````*888888888888888888*`````").toCharArray(),
                    ("````**88888888888888888``````").toCharArray(),
                    ("```````88888888888888*```````").toCharArray(),
                    ("`````````88888888*8*`````````").toCharArray(),
                    ("````````*8888`88`````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````*88*````````````````").toCharArray(),
                    ("```````*88888**``````````````").toCharArray(),
                    ("``````888888888*`````````````").toCharArray(),
                    ("`````88888888888*``*888*`````").toCharArray(),
                    ("````*888888888888888888*`````").toCharArray(),
                    ("````*888888888888888888``````").toCharArray(),
                    ("``````*888888888888888```````").toCharArray(),
                    ("```````**88888888888*````````").toCharArray(),
                    ("````````*88888888```88```````").toCharArray(),
                    ("````````*888*888```88````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("```````````*`````````````````").toCharArray(),
                    ("````````*888*````````````````").toCharArray(),
                    ("```````8888888*``````````````").toCharArray(),
                    ("`````*888888888*`````**``````").toCharArray(),
                    ("`````88888888888***8888*`````").toCharArray(),
                    ("````*888888888888888888*`````").toCharArray(),
                    ("`````*8888888888888888*``````").toCharArray(),
                    ("```````*8888888888888*```````").toCharArray(),
                    ("````````*888888888**888``````").toCharArray(),
                    ("````````*88888888``888```````").toCharArray(),
                    ("````````*88**88*``888````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("``````````***````````````````").toCharArray(),
                    ("````````88888*```````````````").toCharArray(),
                    ("``````*8888888*``````````````").toCharArray(),
                    ("`````*888888888*````*8*``````").toCharArray(),
                    ("`````888888888888888888*`````").toCharArray(),
                    ("`````*88888888888888888``````").toCharArray(),
                    ("``````**88888888888888```````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````*888888888`8888``````").toCharArray(),
                    ("````````*8888888*`8888```````").toCharArray(),
                    ("````````888`*8*``*88*````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````*88*````````````````").toCharArray(),
                    ("```````*88888*```````````````").toCharArray(),
                    ("``````88888888*``````````````").toCharArray(),
                    ("`````*8888888888****88*``````").toCharArray(),
                    ("`````*88888888888888888``````").toCharArray(),
                    ("`````**888888888888888*``````").toCharArray(),
                    ("```````*888888888888888*`````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````*88888888`88888``````").toCharArray(),
                    ("````````8888888*`*888*```````").toCharArray(),
                    ("```````*88*`88```888`````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("```````````*`````````````````").toCharArray(),
                    ("````````*888*````````````````").toCharArray(),
                    ("```````888888*```````````````").toCharArray(),
                    ("``````888888888*`````*```````").toCharArray(),
                    ("`````*8888888888888888*``````").toCharArray(),
                    ("`````*8888888888888888*``````").toCharArray(),
                    ("```````*88888888888888*``````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````*888888888*8888``````").toCharArray(),
                    ("````````88888888*`8888```````").toCharArray(),
                    ("```````*8888888``8888````````").toCharArray(),
                    ("```````*88`888``*88*`````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````***`````````````````").toCharArray(),
                    ("```````*8888*````````````````").toCharArray(),
                    ("``````*888888**``````````````").toCharArray(),
                    ("`````*8888888888*******``````").toCharArray(),
                    ("`````*8888888888888888*``````").toCharArray(),
                    ("``````**88888888888888*``````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````888888888*88888``````").toCharArray(),
                    ("```````*88888888`88888```````").toCharArray(),
                    ("```````88888888`*888*````````").toCharArray(),
                    ("``````8888*88*``*8*``````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("````````*88*`````````````````").toCharArray(),
                    ("```````88888*````````````````").toCharArray(),
                    ("``````88888888**`````````````").toCharArray(),
                    ("`````*888888888888888**``````").toCharArray(),
                    ("``````*888888888888888*``````").toCharArray(),
                    ("````````*888888888888**``````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````888888888*8888*``````").toCharArray(),
                    ("```````888888888*8888*```````").toCharArray(),
                    ("``````88888888*`8888`````````").toCharArray(),
                    ("`````8888**8*````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("``````````*``````````````````").toCharArray(),
                    ("````````888*`````````````````").toCharArray(),
                    ("``````*88888**```````````````").toCharArray(),
                    ("``````888888888******````````").toCharArray(),
                    ("``````*88888888888888**``````").toCharArray(),
                    ("```````**888888888888*```````").toCharArray(),
                    ("````````*88888888888*88*`````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````888888888*88888``````").toCharArray(),
                    ("```````888888888*88888```````").toCharArray(),
                    ("``````888888888*88888````````").toCharArray(),
                    ("`````88888888*``*8*``````````").toCharArray(),
                    ("````*88888```````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````**``````````````````").toCharArray(),
                    ("```````*888*`````````````````").toCharArray(),
                    ("``````*888888**``````````````").toCharArray(),
                    ("``````*888888888888**````````").toCharArray(),
                    ("```````**888888888888*```````").toCharArray(),
                    ("````````*88888888888***``````").toCharArray(),
                    ("````````*8888888888*888*`````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("```````*88888888888888*``````").toCharArray(),
                    ("``````*888888888*8888*```````").toCharArray(),
                    ("`````888888888*`8888`````````").toCharArray(),
                    ("````*8888888*88``````````````").toCharArray(),
                    ("````888888``88```````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("````````*8*``````````````````").toCharArray(),
                    ("```````8888**````````````````").toCharArray(),
                    ("``````*8888888*****``````````").toCharArray(),
                    ("```````***888888888**````````").toCharArray(),
                    ("````````*88888888888*````````").toCharArray(),
                    ("````````*8888888888*****`````").toCharArray(),
                    ("````````*888888888*8888*`````").toCharArray(),
                    ("````````888888888888888``````").toCharArray(),
                    ("```````888888888*88888```````").toCharArray(),
                    ("``````888888888**888*````````").toCharArray(),
                    ("`````88888888888***``````````").toCharArray(),
                    ("````8888888`888````88````````").toCharArray(),
                    ("````*8888*`888````88`````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("````````88*``````````````````").toCharArray(),
                    ("```````888****```````````````").toCharArray(),
                    ("```````****8888888*``````````").toCharArray(),
                    ("`````````*888888888**````````").toCharArray(),
                    ("````````*8888888888*`````````").toCharArray(),
                    ("````````*888888888**888*`````").toCharArray(),
                    ("````````*88888888888888``````").toCharArray(),
                    ("```````*88888888888888*``````").toCharArray(),
                    ("``````8888888888*8888````````").toCharArray(),
                    ("`````88888888888888*`````````").toCharArray(),
                    ("````*88888888888```888```````").toCharArray(),
                    ("````88888888888```888````````").toCharArray(),
                    ("`````**8*8888*```888`````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````*```````````````````").toCharArray(),
                    ("```````*8**``````````````````").toCharArray(),
                    ("```````***********```````````").toCharArray(),
                    ("``````````*8888888*``````````").toCharArray(),
                    ("`````````*888888888*`````````").toCharArray(),
                    ("````````*888888888*``**``````").toCharArray(),
                    ("````````*888888888*8888``````").toCharArray(),
                    ("````````88888888888888*``````").toCharArray(),
                    ("```````888888888*8888*```````").toCharArray(),
                    ("`````*88888888888888*````````").toCharArray(),
                    ("`````888888888888*`*88*``````").toCharArray(),
                    ("````888888888888``8888```````").toCharArray(),
                    ("````*888888888*``88888```````").toCharArray(),
                    ("````````8888*```*8888````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("````````**```````````````````").toCharArray(),
                    ("```````***```````````````````").toCharArray(),
                    ("```````````***888*```````````").toCharArray(),
                    ("``````````*8888888*``````````").toCharArray(),
                    ("`````````*88888888*``````````").toCharArray(),
                    ("````````*888888888*`*8*``````").toCharArray(),
                    ("````````*888888888*888*``````").toCharArray(),
                    ("```````*888888888*8888```````").toCharArray(),
                    ("``````888888888888888````````").toCharArray(),
                    ("`````8888888888888***8*``````").toCharArray(),
                    ("````*88888888888*`*888*``````").toCharArray(),
                    ("````88888888888``888888``````").toCharArray(),
                    ("````**8888888*`8888888```````").toCharArray(),
                    ("```````*888```88*8888````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("````````*````````````````````").toCharArray(),
                    ("```````````````**````````````").toCharArray(),
                    ("```````````**8888*```````````").toCharArray(),
                    ("``````````*8888888*``````````").toCharArray(),
                    ("`````````*88888888*``````````").toCharArray(),
                    ("````````*888888888**88*``````").toCharArray(),
                    ("````````888888888**888```````").toCharArray(),
                    ("```````888888888*8888*```````").toCharArray(),
                    ("`````*8888888888888**````````").toCharArray(),
                    ("`````888888888888*`888*``````").toCharArray(),
                    ("````888888888888``88888*`````").toCharArray(),
                    ("````8888888888*88888888``````").toCharArray(),
                    ("``````*888888888888888```````").toCharArray(),
                    ("```````*8*`88888`888*````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("``````````````*8*````````````").toCharArray(),
                    ("```````````*88888*```````````").toCharArray(),
                    ("``````````*8888888*``````````").toCharArray(),
                    ("`````````888888888*``*```````").toCharArray(),
                    ("````````*88888888***88```````").toCharArray(),
                    ("```````*88888888**888*```````").toCharArray(),
                    ("``````88888888888888*````````").toCharArray(),
                    ("`````888888888888****8*``````").toCharArray(),
                    ("````*88888888888*`*8888*`````").toCharArray(),
                    ("````8888888888888888888*`````").toCharArray(),
                    ("````**88888888888888888``````").toCharArray(),
                    ("```````88888888888888*```````").toCharArray(),
                    ("``````````88888*`*8*`````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````*88*````````````").toCharArray(),
                    ("```````````*88888*```````````").toCharArray(),
                    ("``````````88888888*``````````").toCharArray(),
                    ("`````````88888888*``**```````").toCharArray(),
                    ("````````88888888***88*```````").toCharArray(),
                    ("```````888888888**88*````````").toCharArray(),
                    ("`````*88888888888***`````````").toCharArray(),
                    ("`````88888888888*``*888*`````").toCharArray(),
                    ("````888888888888*888888*`````").toCharArray(),
                    ("````8888888888888888888``````").toCharArray(),
                    ("``````*888888888888888```````").toCharArray(),
                    ("```````*8*8888888888*````````").toCharArray(),
                    ("`````````88888*``````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("```````````````*`````````````").toCharArray(),
                    ("````````````*888*````````````").toCharArray(),
                    ("```````````888888*```````````").toCharArray(),
                    ("`````````*8888888*```````````").toCharArray(),
                    ("````````*8888888*``***```````").toCharArray(),
                    ("```````*88888888***8*````````").toCharArray(),
                    ("``````8888888888****`````````").toCharArray(),
                    ("`````88888888888*```***``````").toCharArray(),
                    ("````*88888888888*`*8888*`````").toCharArray(),
                    ("````8888888888888888888*`````").toCharArray(),
                    ("````**88888888888888888``````").toCharArray(),
                    ("```````88888888888888*```````").toCharArray(),
                    ("`````````88888888*8*`````````").toCharArray(),
                    ("````````*8888`88`````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("``````````````**`````````````").toCharArray(),
                    ("````````````8888*````````````").toCharArray(),
                    ("``````````*888888*```````````").toCharArray(),
                    ("`````````8888888*````````````").toCharArray(),
                    ("````````88888888*``**````````").toCharArray(),
                    ("```````888888888****`````````").toCharArray(),
                    ("`````*8888888888*````````````").toCharArray(),
                    ("`````88888888888*``*888*`````").toCharArray(),
                    ("````*888888888888888888*`````").toCharArray(),
                    ("````*888888888888888888``````").toCharArray(),
                    ("``````*888888888888888```````").toCharArray(),
                    ("```````**88888888888*````````").toCharArray(),
                    ("````````*88888888```88```````").toCharArray(),
                    ("````````*888*888```88````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````*8*`````````````").toCharArray(),
                    ("```````````*8888*````````````").toCharArray(),
                    ("``````````888888*````````````").toCharArray(),
                    ("````````*8888888*````````````").toCharArray(),
                    ("```````*88888888*``*`````````").toCharArray(),
                    ("``````8888888888*````````````").toCharArray(),
                    ("`````88888888888*````**``````").toCharArray(),
                    ("````*88888888888***8888*`````").toCharArray(),
                    ("````*888888888888888888*`````").toCharArray(),
                    ("````**8888888888888888*``````").toCharArray(),
                    ("```````*8888888888888*```````").toCharArray(),
                    ("````````*888888888**888``````").toCharArray(),
                    ("````````*88888888``888```````").toCharArray(),
                    ("````````*88**88*``888````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("````````````*88*`````````````").toCharArray(),
                    ("```````````88888*````````````").toCharArray(),
                    ("`````````*888888*````````````").toCharArray(),
                    ("````````88888888*````````````").toCharArray(),
                    ("```````888888888*````````````").toCharArray(),
                    ("`````*8888888888*````````````").toCharArray(),
                    ("`````88888888888*```*8*``````").toCharArray(),
                    ("````*888888888888888888*`````").toCharArray(),
                    ("````*888888888888888888``````").toCharArray(),
                    ("``````*888888888888888```````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````*888888888`8888``````").toCharArray(),
                    ("````````*8888888*`8888```````").toCharArray(),
                    ("````````888`*8*``*88*````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("``````````````*``````````````").toCharArray(),
                    ("````````````888*`````````````").toCharArray(),
                    ("``````````*88888*````````````").toCharArray(),
                    ("`````````8888888*````````````").toCharArray(),
                    ("```````*88888888*````````````").toCharArray(),
                    ("``````*888888888*````````````").toCharArray(),
                    ("`````*8888888888*````````````").toCharArray(),
                    ("`````88888888888****88*``````").toCharArray(),
                    ("````*888888888888888888``````").toCharArray(),
                    ("`````**888888888888888*``````").toCharArray(),
                    ("```````*888888888888888*`````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````*88888888`88888``````").toCharArray(),
                    ("````````8888888*`*888*```````").toCharArray(),
                    ("```````*88*`88```888`````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````***`````````````").toCharArray(),
                    ("```````````*888*`````````````").toCharArray(),
                    ("``````````888888*````````````").toCharArray(),
                    ("````````*8888888*````````````").toCharArray(),
                    ("```````888888888*````````````").toCharArray(),
                    ("``````8888888888*````````````").toCharArray(),
                    ("`````88888888888*````*```````").toCharArray(),
                    ("````*88888888888888888*``````").toCharArray(),
                    ("````*88888888888888888*``````").toCharArray(),
                    ("```````*88888888888888*``````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````*888888888*8888``````").toCharArray(),
                    ("````````88888888*`8888```````").toCharArray(),
                    ("```````*8888888``8888````````").toCharArray(),
                    ("```````*88`888``*88*`````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("````````````*88*`````````````").toCharArray(),
                    ("```````````8888*`````````````").toCharArray(),
                    ("`````````*888888*````````````").toCharArray(),
                    ("````````88888888*````````````").toCharArray(),
                    ("``````*888888888*````````````").toCharArray(),
                    ("`````*8888888888*````````````").toCharArray(),
                    ("`````88888888888*******``````").toCharArray(),
                    ("````*88888888888888888*``````").toCharArray(),
                    ("`````***88888888888888*``````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````888888888*88888``````").toCharArray(),
                    ("```````*88888888`88888```````").toCharArray(),
                    ("```````88888888`*888*````````").toCharArray(),
                    ("``````8888*88*``*8*``````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("``````````````*``````````````").toCharArray(),
                    ("````````````888*`````````````").toCharArray(),
                    ("``````````*8888*`````````````").toCharArray(),
                    ("````````*8888888*````````````").toCharArray(),
                    ("```````*88888888*````````````").toCharArray(),
                    ("``````8888888888*````````````").toCharArray(),
                    ("`````*8888888888*````````````").toCharArray(),
                    ("`````8888888888888888**``````").toCharArray(),
                    ("`````*8888888888888888*``````").toCharArray(),
                    ("````````*888888888888**``````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````888888888*8888*``````").toCharArray(),
                    ("```````888888888*8888*```````").toCharArray(),
                    ("``````88888888*`8888`````````").toCharArray(),
                    ("`````8888**8*````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````**``````````````").toCharArray(),
                    ("```````````*888*`````````````").toCharArray(),
                    ("`````````*88888*`````````````").toCharArray(),
                    ("````````88888888*````````````").toCharArray(),
                    ("```````88888888**````````````").toCharArray(),
                    ("``````8888888888*````````````").toCharArray(),
                    ("`````*8888888888*****````````").toCharArray(),
                    ("`````*888888888888888**``````").toCharArray(),
                    ("``````***888888888888*```````").toCharArray(),
                    ("````````*88888888888*88*`````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````888888888*88888``````").toCharArray(),
                    ("```````888888888*88888```````").toCharArray(),
                    ("``````888888888*88888````````").toCharArray(),
                    ("`````88888888*``*8*``````````").toCharArray(),
                    ("````*88888```````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("````````````*8*``````````````").toCharArray(),
                    ("``````````*8888*`````````````").toCharArray(),
                    ("`````````888888*`````````````").toCharArray(),
                    ("```````*8888888*`````````````").toCharArray(),
                    ("``````*8888888***````````````").toCharArray(),
                    ("``````8888888888*````````````").toCharArray(),
                    ("`````*8888888888888**````````").toCharArray(),
                    ("``````*88888888888888*```````").toCharArray(),
                    ("````````*88888888888***``````").toCharArray(),
                    ("````````*8888888888*888*`````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("```````*88888888888888*``````").toCharArray(),
                    ("``````*888888888*8888*```````").toCharArray(),
                    ("`````888888888*`8888`````````").toCharArray(),
                    ("````*8888888*88``````````````").toCharArray(),
                    ("````888888``88```````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("```````````*88*``````````````").toCharArray(),
                    ("``````````88888*`````````````").toCharArray(),
                    ("````````*888888*`````````````").toCharArray(),
                    ("```````8888888*``````````````").toCharArray(),
                    ("``````*888888****````````````").toCharArray(),
                    ("``````8888888888***``````````").toCharArray(),
                    ("``````*888888888888**````````").toCharArray(),
                    ("```````**88888888888*````````").toCharArray(),
                    ("````````*8888888888*****`````").toCharArray(),
                    ("````````*888888888*8888*`````").toCharArray(),
                    ("````````888888888888888``````").toCharArray(),
                    ("```````888888888*88888```````").toCharArray(),
                    ("``````888888888**888*````````").toCharArray(),
                    ("`````88888888888***``````````").toCharArray(),
                    ("````8888888`888````88````````").toCharArray(),
                    ("````*8888*`888````88`````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````*```````````````").toCharArray(),
                    ("```````````888*``````````````").toCharArray(),
                    ("`````````*88888*`````````````").toCharArray(),
                    ("````````888888*``````````````").toCharArray(),
                    ("```````888888*```````````````").toCharArray(),
                    ("``````*88888*****````````````").toCharArray(),
                    ("``````*88888888888*``````````").toCharArray(),
                    ("```````***888888888**````````").toCharArray(),
                    ("````````*8888888888*`````````").toCharArray(),
                    ("````````*888888888**888*`````").toCharArray(),
                    ("````````*88888888888888``````").toCharArray(),
                    ("```````*88888888888888*``````").toCharArray(),
                    ("``````8888888888*8888````````").toCharArray(),
                    ("`````88888888888888*`````````").toCharArray(),
                    ("````*88888888888```888```````").toCharArray(),
                    ("````88888888888```888````````").toCharArray(),
                    ("`````**8*8888*```888`````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("````````````**```````````````").toCharArray(),
                    ("``````````*888*``````````````").toCharArray(),
                    ("`````````88888*``````````````").toCharArray(),
                    ("```````*88888*```````````````").toCharArray(),
                    ("```````88888*````````````````").toCharArray(),
                    ("``````*8888*******```````````").toCharArray(),
                    ("```````****8888888*``````````").toCharArray(),
                    ("`````````*888888888*`````````").toCharArray(),
                    ("````````*888888888*``**``````").toCharArray(),
                    ("````````*888888888*8888``````").toCharArray(),
                    ("````````88888888888888*``````").toCharArray(),
                    ("```````888888888*8888*```````").toCharArray(),
                    ("`````*88888888888888*````````").toCharArray(),
                    ("`````888888888888*`*88*``````").toCharArray(),
                    ("````888888888888``8888```````").toCharArray(),
                    ("````*888888888*``88888```````").toCharArray(),
                    ("````````8888*```*8888````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("```````````***```````````````").toCharArray(),
                    ("``````````8888*``````````````").toCharArray(),
                    ("````````*8888*```````````````").toCharArray(),
                    ("```````*8888*````````````````").toCharArray(),
                    ("``````*8888*`````````````````").toCharArray(),
                    ("``````********888*```````````").toCharArray(),
                    ("``````````*8888888*``````````").toCharArray(),
                    ("`````````*88888888*``````````").toCharArray(),
                    ("````````*888888888*`*8*``````").toCharArray(),
                    ("````````*888888888*888*``````").toCharArray(),
                    ("```````*888888888*8888```````").toCharArray(),
                    ("``````888888888888888````````").toCharArray(),
                    ("`````8888888888888***8*``````").toCharArray(),
                    ("````*88888888888*`*888*``````").toCharArray(),
                    ("````88888888888``888888``````").toCharArray(),
                    ("````**8888888*`8888888```````").toCharArray(),
                    ("```````*888```88*8888````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("```````````***```````````````").toCharArray(),
                    ("`````````*888*```````````````").toCharArray(),
                    ("````````8888*````````````````").toCharArray(),
                    ("```````8888*`````````````````").toCharArray(),
                    ("``````*88**````**````````````").toCharArray(),
                    ("```````````**8888*```````````").toCharArray(),
                    ("``````````*8888888*``````````").toCharArray(),
                    ("`````````*88888888*``````````").toCharArray(),
                    ("````````*888888888**88*``````").toCharArray(),
                    ("````````888888888**888```````").toCharArray(),
                    ("```````888888888*8888*```````").toCharArray(),
                    ("`````*8888888888888**````````").toCharArray(),
                    ("`````888888888888*`888*``````").toCharArray(),
                    ("````888888888888``88888*`````").toCharArray(),
                    ("````8888888888*88888888``````").toCharArray(),
                    ("``````*888888888888888```````").toCharArray(),
                    ("```````*8*`88888`888*````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {//loop
                    ("``````````****```````````````").toCharArray(),
                    ("`````````888*````````````````").toCharArray(),
                    ("```````*888*`````````````````").toCharArray(),
                    ("```````888*``````````````````").toCharArray(),
                    ("```````*``````*8*````````````").toCharArray(),
                    ("```````````*88888*```````````").toCharArray(),
                    ("``````````*8888888*``````````").toCharArray(),
                    ("`````````888888888*``*```````").toCharArray(),
                    ("````````*88888888***88```````").toCharArray(),
                    ("```````*88888888**888*```````").toCharArray(),
                    ("``````88888888888888*````````").toCharArray(),
                    ("`````888888888888****8*``````").toCharArray(),
                    ("````*88888888888*`*8888*`````").toCharArray(),
                    ("````8888888888888888888*`````").toCharArray(),
                    ("````**88888888888888888``````").toCharArray(),
                    ("```````88888888888888*```````").toCharArray(),
                    ("``````````88888*`*8*`````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },//loop
            {
                    ("``````````***````````````````").toCharArray(),
                    ("````````*88*`````````````````").toCharArray(),
                    ("```````*88*``````````````````").toCharArray(),
                    ("```````**````````````````````").toCharArray(),
                    ("`````````````*88*````````````").toCharArray(),
                    ("```````````*88888*```````````").toCharArray(),
                    ("``````````88888888*``````````").toCharArray(),
                    ("`````````88888888*``**```````").toCharArray(),
                    ("````````88888888***88*```````").toCharArray(),
                    ("```````888888888**88*````````").toCharArray(),
                    ("`````*88888888888***`````````").toCharArray(),
                    ("`````88888888888*``*888*`````").toCharArray(),
                    ("````888888888888*888888*`````").toCharArray(),
                    ("````8888888888888888888``````").toCharArray(),
                    ("``````*888888888888888```````").toCharArray(),
                    ("```````*8*8888888888*````````").toCharArray(),
                    ("`````````88888*``````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````*8*`````````````````").toCharArray(),
                    ("````````888``````````````````").toCharArray(),
                    ("```````*8*```````````````````").toCharArray(),
                    ("```````````````*`````````````").toCharArray(),
                    ("````````````*888*````````````").toCharArray(),
                    ("```````````888888*```````````").toCharArray(),
                    ("`````````*8888888*```````````").toCharArray(),
                    ("````````*8888888*``***```````").toCharArray(),
                    ("```````*88888888***8*````````").toCharArray(),
                    ("``````8888888888****`````````").toCharArray(),
                    ("`````88888888888*```***``````").toCharArray(),
                    ("````*88888888888*`*8888*`````").toCharArray(),
                    ("````8888888888888888888*`````").toCharArray(),
                    ("````**88888888888888888``````").toCharArray(),
                    ("```````88888888888888*```````").toCharArray(),
                    ("`````````88888888*8*`````````").toCharArray(),
                    ("````````*8888`88`````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````88``````````````````").toCharArray(),
                    ("````````88```````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("``````````````**`````````````").toCharArray(),
                    ("````````````8888*````````````").toCharArray(),
                    ("``````````*888888*```````````").toCharArray(),
                    ("`````````8888888*````````````").toCharArray(),
                    ("````````88888888*``**````````").toCharArray(),
                    ("```````888888888****`````````").toCharArray(),
                    ("`````*8888888888*````````````").toCharArray(),
                    ("`````88888888888*``*888*`````").toCharArray(),
                    ("````*888888888888888888*`````").toCharArray(),
                    ("````*888888888888888888``````").toCharArray(),
                    ("``````*888888888888888```````").toCharArray(),
                    ("```````**88888888888*````````").toCharArray(),
                    ("````````*88888888```88```````").toCharArray(),
                    ("````````*888*888```88````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("````````*8*``````````````````").toCharArray(),
                    ("````````*````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````*8*`````````````").toCharArray(),
                    ("```````````*8888*````````````").toCharArray(),
                    ("``````````888888*````````````").toCharArray(),
                    ("````````*8888888*````````````").toCharArray(),
                    ("```````*88888888*``*`````````").toCharArray(),
                    ("``````8888888888*````````````").toCharArray(),
                    ("`````88888888888*````**``````").toCharArray(),
                    ("````*88888888888***8888*`````").toCharArray(),
                    ("````*888888888888888888*`````").toCharArray(),
                    ("````**8888888888888888*``````").toCharArray(),
                    ("```````*8888888888888*```````").toCharArray(),
                    ("````````*888888888**888``````").toCharArray(),
                    ("````````*88888888``888```````").toCharArray(),
                    ("````````*88**88*``888````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("````````**```````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("````````````*88*`````````````").toCharArray(),
                    ("```````````88888*````````````").toCharArray(),
                    ("`````````*888888*````````````").toCharArray(),
                    ("````````88888888*````````````").toCharArray(),
                    ("```````888888888*````````````").toCharArray(),
                    ("`````*8888888888*````````````").toCharArray(),
                    ("`````88888888888*```*8*``````").toCharArray(),
                    ("````*888888888888888888*`````").toCharArray(),
                    ("````*888888888888888888``````").toCharArray(),
                    ("``````*888888888888888```````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````*888888888`8888``````").toCharArray(),
                    ("````````*8888888*`8888```````").toCharArray(),
                    ("````````888`*8*``*88*````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("``````````````*``````````````").toCharArray(),
                    ("````````````888*`````````````").toCharArray(),
                    ("``````````*88888*````````````").toCharArray(),
                    ("`````````8888888*````````````").toCharArray(),
                    ("```````*88888888*````````````").toCharArray(),
                    ("``````*888888888*````````````").toCharArray(),
                    ("`````*8888888888*````````````").toCharArray(),
                    ("`````88888888888****88*``````").toCharArray(),
                    ("````*888888888888888888``````").toCharArray(),
                    ("`````**888888888888888*``````").toCharArray(),
                    ("```````*888888888888888*`````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````*88888888`88888``````").toCharArray(),
                    ("````````8888888*`*888*```````").toCharArray(),
                    ("```````*88*`88```888`````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````***`````````````").toCharArray(),
                    ("```````````*888*`````````````").toCharArray(),
                    ("``````````888888*````````````").toCharArray(),
                    ("````````*8888888*````````````").toCharArray(),
                    ("```````888888888*````````````").toCharArray(),
                    ("``````8888888888*````````````").toCharArray(),
                    ("`````88888888888*````*```````").toCharArray(),
                    ("````*88888888888888888*``````").toCharArray(),
                    ("````*88888888888888888*``````").toCharArray(),
                    ("```````*88888888888888*``````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````*888888888*8888``````").toCharArray(),
                    ("````````88888888*`8888```````").toCharArray(),
                    ("```````*8888888``8888````````").toCharArray(),
                    ("```````*88`888``*88*`````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray(),
                    ("````````````*88*`````````````").toCharArray(),
                    ("```````````8888*`````````````").toCharArray(),
                    ("`````````*888888*````````````").toCharArray(),
                    ("````````88888888*````````````").toCharArray(),
                    ("``````*888888888*````````````").toCharArray(),
                    ("`````*8888888888*````````````").toCharArray(),
                    ("`````88888888888*******``````").toCharArray(),
                    ("````*88888888888888888*``````").toCharArray(),
                    ("`````***88888888888888*``````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````888888888*88888``````").toCharArray(),
                    ("```````*88888888`88888```````").toCharArray(),
                    ("```````88888888`*888*````````").toCharArray(),
                    ("``````8888*88*``*8*``````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("``````````````*``````````````").toCharArray(),
                    ("````````````888*`````````````").toCharArray(),
                    ("``````````*8888*`````````````").toCharArray(),
                    ("````````*8888888*````````````").toCharArray(),
                    ("```````*88888888*````````````").toCharArray(),
                    ("``````8888888888*````````````").toCharArray(),
                    ("`````*8888888888*````````````").toCharArray(),
                    ("`````8888888888888888**``````").toCharArray(),
                    ("`````*8888888888888888*``````").toCharArray(),
                    ("````````*888888888888**``````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````888888888*8888*``````").toCharArray(),
                    ("```````888888888*8888*```````").toCharArray(),
                    ("``````88888888*`8888`````````").toCharArray(),
                    ("`````8888**8*````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("`````````````**``````````````").toCharArray(),
                    ("```````````*888*`````````````").toCharArray(),
                    ("`````````*88888*`````````````").toCharArray(),
                    ("````````88888888*````````````").toCharArray(),
                    ("```````88888888**````````````").toCharArray(),
                    ("``````8888888888*````````````").toCharArray(),
                    ("`````*8888888888*****````````").toCharArray(),
                    ("`````*888888888888888**``````").toCharArray(),
                    ("``````***888888888888*```````").toCharArray(),
                    ("````````*88888888888*88*`````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("````````888888888*88888``````").toCharArray(),
                    ("```````888888888*88888```````").toCharArray(),
                    ("``````888888888*88888````````").toCharArray(),
                    ("`````88888888*``*8*``````````").toCharArray(),
                    ("````*88888```````````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("````````````*8*``````````````").toCharArray(),
                    ("``````````*8888*`````````````").toCharArray(),
                    ("`````````888888*`````````````").toCharArray(),
                    ("```````*8888888*`````````````").toCharArray(),
                    ("``````*8888888***````````````").toCharArray(),
                    ("``````8888888888*````````````").toCharArray(),
                    ("`````*8888888888888**````````").toCharArray(),
                    ("``````*88888888888888*```````").toCharArray(),
                    ("````````*88888888888***``````").toCharArray(),
                    ("````````*8888888888*888*`````").toCharArray(),
                    ("````````*88888888888888*`````").toCharArray(),
                    ("```````*88888888888888*``````").toCharArray(),
                    ("``````*888888888*8888*```````").toCharArray(),
                    ("`````888888888*`8888`````````").toCharArray(),
                    ("````*8888888*88``````````````").toCharArray(),
                    ("````888888``88```````````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````````````````````").toCharArray(),
                    ("```````````*88*``````````````").toCharArray(),
                    ("``````````88888*`````````````").toCharArray(),
                    ("````````*888888*`````````````").toCharArray(),
                    ("```````8888888*``````````````").toCharArray(),
                    ("``````*888888****````````````").toCharArray(),
                    ("``````8888888888***``````````").toCharArray(),
                    ("``````*888888888888**````````").toCharArray(),
                    ("```````**88888888888*````````").toCharArray(),
                    ("````````*8888888888*****`````").toCharArray(),
                    ("````````*888888888*8888*`````").toCharArray(),
                    ("````````888888888888888``````").toCharArray(),
                    ("```````888888888*88888```````").toCharArray(),
                    ("``````888888888**888*````````").toCharArray(),
                    ("`````88888888888***``````````").toCharArray(),
                    ("````8888888`888````88````````").toCharArray(),
                    ("````*8888*`888````88`````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("`````````````*```````````````").toCharArray(),
                    ("```````````888*``````````````").toCharArray(),
                    ("`````````*88888*`````````````").toCharArray(),
                    ("````````888888*``````````````").toCharArray(),
                    ("```````888888*```````````````").toCharArray(),
                    ("``````*88888*****````````````").toCharArray(),
                    ("``````*88888888888*``````````").toCharArray(),
                    ("```````***888888888**````````").toCharArray(),
                    ("````````*8888888888*`````````").toCharArray(),
                    ("````````*888888888**888*`````").toCharArray(),
                    ("````````*88888888888888``````").toCharArray(),
                    ("```````*88888888888888*``````").toCharArray(),
                    ("``````8888888888*8888````````").toCharArray(),
                    ("`````88888888888888*`````````").toCharArray(),
                    ("````*88888888888```888```````").toCharArray(),
                    ("````88888888888```888````````").toCharArray(),
                    ("`````**8*8888*```888`````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("````````````**```````````````").toCharArray(),
                    ("``````````*888*``````````````").toCharArray(),
                    ("`````````88888*``````````````").toCharArray(),
                    ("```````*88888*```````````````").toCharArray(),
                    ("```````88888*````````````````").toCharArray(),
                    ("``````*8888*******```````````").toCharArray(),
                    ("```````****8888888*``````````").toCharArray(),
                    ("`````````*888888888*`````````").toCharArray(),
                    ("````````*888888888*``**``````").toCharArray(),
                    ("````````*888888888*8888``````").toCharArray(),
                    ("````````88888888888888*``````").toCharArray(),
                    ("```````888888888*8888*```````").toCharArray(),
                    ("`````*88888888888888*````````").toCharArray(),
                    ("`````888888888888*`*88*``````").toCharArray(),
                    ("````888888888888``8888```````").toCharArray(),
                    ("````*888888888*``88888```````").toCharArray(),
                    ("````````8888*```*8888````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("```````````***```````````````").toCharArray(),
                    ("``````````8888*``````````````").toCharArray(),
                    ("````````*8888*```````````````").toCharArray(),
                    ("```````*8888*````````````````").toCharArray(),
                    ("``````*8888*`````````````````").toCharArray(),
                    ("``````********888*```````````").toCharArray(),
                    ("``````````*8888888*``````````").toCharArray(),
                    ("`````````*88888888*``````````").toCharArray(),
                    ("````````*888888888*`*8*``````").toCharArray(),
                    ("````````*888888888*888*``````").toCharArray(),
                    ("```````*888888888*8888```````").toCharArray(),
                    ("``````888888888888888````````").toCharArray(),
                    ("`````8888888888888***8*``````").toCharArray(),
                    ("````*88888888888*`*888*``````").toCharArray(),
                    ("````88888888888``888888``````").toCharArray(),
                    ("````**8888888*`8888888```````").toCharArray(),
                    ("```````*888```88*8888````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            },
            {
                    ("```````````***```````````````").toCharArray(),
                    ("`````````*888*```````````````").toCharArray(),
                    ("````````8888*````````````````").toCharArray(),
                    ("```````8888*`````````````````").toCharArray(),
                    ("``````*88**````**````````````").toCharArray(),
                    ("```````````**8888*```````````").toCharArray(),
                    ("``````````*8888888*``````````").toCharArray(),
                    ("`````````*88888888*``````````").toCharArray(),
                    ("````````*888888888**88*``````").toCharArray(),
                    ("````````888888888**888```````").toCharArray(),
                    ("```````888888888*8888*```````").toCharArray(),
                    ("`````*8888888888888**````````").toCharArray(),
                    ("`````888888888888*`888*``````").toCharArray(),
                    ("````888888888888``88888*`````").toCharArray(),
                    ("````8888888888*88888888``````").toCharArray(),
                    ("``````*888888888888888```````").toCharArray(),
                    ("```````*8*`88888`888*````````").toCharArray(),
                    ("`````````````````````````````").toCharArray()
            }
    };

}