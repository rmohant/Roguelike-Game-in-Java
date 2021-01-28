package game;

import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class ObjectDisplayGrid extends JFrame implements KeyListener, InputSubject {

    private static final int DEBUG = 0;
    private static final String CLASSID = ".ObjectDisplayGrid";

    private static AsciiPanel terminal;
    private Char[][] objectGrid = null;

    private List<InputObserver> inputObservers = null;

    private static int height;
    private static int width;
    private static ObjectDisplayGrid objectDisplayGrid;
    //private stack
    private Stack<Displayable>[][] gridStack = null;
    // add to stack for certain position
    // then pop and push to next stack when moved
    private int bHeight;
    private int tHeight;

    public static final int rWALL = 1;
    public static final int pWALL = 2;
    public static final int SNAKE = 3;
    public static final int TROLL = 4;
    public static final int HOBGOBLIN = 5;
    public static final int pWALKWAY = 6;
    public static final int rSPACE = 7;
    public static final int pDOORWAY = 8;

    private ObjectDisplayGrid(int _width, int _height) {
        width = _width;
        height = _height;
        System.out.println("dungeon width: "+width+ " and dungeon height " +height);

        terminal = new AsciiPanel(width, height);

        objectGrid = new Char[width][height];
        gridStack = (Stack<Displayable>[][]) new Stack[width][height];
        //objectGrid = (Stack<Displayable>[][]) new Stack[width][height];

        initializeDisplay();

        super.add(terminal);
        super.setSize(width * 9, height * 16);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // super.repaint();
        // terminal.repaint( );
        super.setVisible(true);
        terminal.setVisible(true);
        super.addKeyListener(this);
        inputObservers = new ArrayList<>();
        super.repaint();

    }

    // static function that returns reference to displaygrid
    // - static reference to displaygrid
    public static ObjectDisplayGrid returnObjectGrid() {
        if (objectDisplayGrid == null) {
            throw new NullPointerException("Call parameterized ODG method first.");
        }
        return objectDisplayGrid;
    }
    
    public static ObjectDisplayGrid returnObjectGrid(int width, int height) {
        if (objectDisplayGrid == null) {
            objectDisplayGrid = new ObjectDisplayGrid(width, height);
        }
        return objectDisplayGrid;
    }

    public void set_bHeight(int val) {
        bHeight = val;
    }

    public int get_bHeight() {
        return bHeight;
    }

    public void set_tHeight(int val) {
        tHeight = val;
    }

    public int get_tHeight() {
        return tHeight;
    }

    @Override
    public void registerInputObserver(InputObserver observer) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".registerInputObserver " + observer.toString());
        }
        inputObservers.add(observer);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".keyTyped entered" + e.toString());
        }
        KeyEvent keypress = (KeyEvent) e;
        notifyInputObservers(keypress.getKeyChar());
    }

    private void notifyInputObservers(char ch) {
        for (InputObserver observer : inputObservers) {
            observer.observerUpdate(ch);
            if (DEBUG > 0) {
                System.out.println(CLASSID + ".notifyInputObserver " + ch);
            }
        }
    }

    // we have to override, but we don't use this
    @Override
    public void keyPressed(KeyEvent even) {
    }

    // we have to override, but we don't use this
    @Override
    public void keyReleased(KeyEvent e) {
    }

    // initializedisplay, addobject, writeterminal

    public final void initializeDisplay() {
        Char ch = new Char(' ');
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                gridStack[i][j] = new Stack<Displayable>();
                addObjectToDisplay(null, ch, i, j);
            }
        }
        terminal.repaint();
    }

    public final void initializeDisplayTopHeight() {
        int i;
        int x=0;
        char[] str1 = ("HP:").toCharArray();
        for (i=0; i < str1.length; i++) {
            Char a = new Char(str1[i]);
            addCharToDisplay(a, x++, 0);
        }    
        x += 6;
        char[] str2 = ("Score: 0").toCharArray();
        for (i=0; i < str2.length; i++) {
            //System.out.println(i);
            Char b = new Char(str2[i]);
            addCharToDisplay(b, x++, 0);
        } 
        terminal.repaint();
    }

    public final void initializeDisplayBottomHeight(int height) {
        int i;
        int x=0;
        char[] str1 = ("Pack:").toCharArray();
        for (i=0; i < str1.length; i++) {
            Char a = new Char(str1[i]);
            addCharToDisplay(a, x++, height-3);
        }    
        x = 0;
        char[] str2 = ("Info:").toCharArray();
        for (i=0; i < str2.length; i++) {
            //System.out.println(i);
            Char b = new Char(str2[i]);
            addCharToDisplay(b, x++, height-1);
        } 

        terminal.repaint();
    }

    public final void initializeDisplayHeight() {
        Char ch = new Char(' ');
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                addCharToDisplay(ch, i, j);
            }
        }
        terminal.repaint();
    }

    public final void initializeDisplayRoom(Room room, int tHeight) {
        Char ch1 = new Char('X');
		Char ch2 = new Char('.');
        System.out.println("PosX_1: "+room.getPosX()+ " and PosY: " +room.getPosY());
        for (int i = room.getPosX(); i < room.getWidth() + room.getPosX(); i++) {
            for (int j = room.getPosY(); j < room.getHeight() + room.getPosY(); j++) {
					if (j == room.getHeight() + room.getPosY() - 1 || i == room.getWidth() + room.getPosX() - 1 || i == room.getPosX() || j == room.getPosY()) {
                        // wall class
                        addObjectToDisplay(room, ch1, i, j + tHeight);
					} else {
                        // floor class
                        addObjectToDisplay(room, ch2, i, j + tHeight);
					}               
            }
        }
        terminal.repaint();
    }

    public final void initializeDisplayPassage(Passage passage, int tHeight) {
        Char ch1 = new Char('#');
		Char ch2 = new Char('+');
        int i1 = 0;
        int i2 = 0;
        int diffX = 0;
        int diffY = 0;


        List<Integer> posX = passage.getPassagePosX();
        List<Integer> posY = passage.getPassagePosY();

        //Iterator iterX = posX.iterator();
        //Iterator iterY = posY.iterator();

        while ((i1 < posX.size() - 1)) {
            //System.out.println(i1);
            //System.out.println(i2);
            int i;
            int j;

            for (i = posX.get(i1); (i < posX.get(i1+1)); i++) {
                addObjectToDisplay(passage, ch1, i, posY.get(i2) + tHeight);
            }
            i = posX.get(i1);
            if (i > posX.get(i1+1)) {
                for (i = posX.get(i1); (i > posX.get(i1+1)); i--) {
                    addObjectToDisplay(passage, ch1, i, posY.get(i2) + tHeight);
                }
            }
            
            for (j = posY.get(i2); (j < posY.get(i2+1)); j++) {
                addObjectToDisplay(passage, ch1, posX.get(i1), j + tHeight);
            }
            j = posY.get(i2);
            if (j > posY.get(i2+1)) {
                for (j = posY.get(i2); (j > posY.get(i2+1)); j--) {
                    addObjectToDisplay(passage, ch1, posX.get(i1), j + tHeight);
                }
            }
            int x = posX.get(i1);
            int y = posY.get(i2);

            if (x == posX.get(0) && y == posY.get(0)) {
                addObjectToDisplay(passage, ch2, x, y + tHeight);
            }
            System.out.println(i2);

            i1++;
            i2++;

            if (i1 == posX.size() - 1 && i2 == posX.size() - 1) {
                x = posX.get(i1);
                y = posY.get(i2);
                addObjectToDisplay(passage, ch2, x, y + tHeight);
            }
            

        }
        
		//System.out.println("PosX: "+room.getPosX()+ " and PosY: " +room.getPosY());

        terminal.repaint();
    }

    public final void initializeDisplayItem(Item item, Room room, int tHeight) {
        Char chSword = new Char(')');
        Char chArmor = new Char(']');
        Char chScroll = new Char('?');

        if (item instanceof Sword) {
            addObjectToDisplay(item, chSword, item.getPosX() + room.getPosX(), item.getPosY() + room.getPosY() + tHeight);
        } else if (item instanceof Armor) {
            addObjectToDisplay(item, chArmor, item.getPosX() + room.getPosX(), item.getPosY() + room.getPosY() + tHeight);
        } else if (item instanceof Scroll) {
            addObjectToDisplay(item, chScroll, item.getPosX() + room.getPosX(), item.getPosY() + room.getPosY() + tHeight);
        }
        terminal.repaint();
    }

    public final void initializeDisplayPlayer(Player player, Room room, int tHeight) {
        Char chPlayer = new Char('@');
        if (player.getCreatureRoom() == room.getRoomID()) {
            addObjectToDisplay(player, chPlayer, player.getPosX() + room.getPosX(), player.getPosY() + room.getPosY() + tHeight);
        }
    }

    public final void initializeDisplayMonster(Monster monster, Room room, int tHeight) {
        Char chTroll = new Char('T');
        Char chSnake = new Char('S');
        Char chHobgoblin = new Char('H');
	    System.out.println("Player Room: "+ monster.getCreatureRoom()+ " Room ID: " +room.getRoomID());
        if ((monster.getName()).matches("Troll") && monster.getCreatureRoom() == room.getRoomID()) {
            //System.out.println("Monster Room: "+monster.getCreatureRoom()+ " Room ID: " +room.getRoomID());
            System.out.println("Troll PosX: "+(monster.getPosX() + room.getPosX())+ " and Troll PosY: " +(monster.getPosY() + room.getPosY()));
            addObjectToDisplay(monster, chTroll, monster.getPosX() + room.getPosX(), monster.getPosY() + room.getPosY() + tHeight);
            terminal.repaint();
        } else if (((monster.getName()).matches("Snake") || (monster.getName()).matches("S")) && monster.getCreatureRoom() == room.getRoomID()) {
            System.out.println("Snake PosX: "+ (monster.getPosX() + room.getPosX())+ " and Snake PosY: " +(monster.getPosY() + room.getPosY()));
            addObjectToDisplay(monster, chSnake, monster.getPosX() + room.getPosX(), monster.getPosY() + room.getPosY() + tHeight);
            terminal.repaint();
        } else if ((monster.getName()).matches("Hobgoblin") && monster.getCreatureRoom() == room.getRoomID()) {
            addObjectToDisplay(monster, chHobgoblin, monster.getPosX() + room.getPosX(), monster.getPosY() + room.getPosY() + tHeight);
            terminal.repaint();
        }
        terminal.repaint();
    }

    public void setObserver(Player player) {
        registerInputObserver(player);
    }

    public void fireUp() {
        if (terminal.requestFocusInWindow()) {
            System.out.println(CLASSID + ".ObjectDisplayGrid(...) requestFocusInWindow Succeeded");
        } else {
            System.out.println(CLASSID + ".ObjectDisplayGrid(...) requestFocusInWindow FAILED");
        }
    }

    public int checkObject(int x, int y) {
        System.out.println("test");
        if ((objectGrid[x][y].getChar()) == ('X')) {
            System.out.println("Found a wall.");
            return 1;
        } else if ((objectGrid[x][y].getChar()) == (' ')) {
            System.out.println("Found a passage wall.");
            return 2;
        } else if ((objectGrid[x][y].getChar()) == ('S')) {
            System.out.println("Found a Snake.");
            return 3;
        } else if ((objectGrid[x][y].getChar()) == ('T')) {
            System.out.println("Found a Troll.");
            return 4;
        } else if ((objectGrid[x][y].getChar()) == ('H')) {
            System.out.println("Found a Hobgoblin.");
            return 5;
        } else if ((objectGrid[x][y].getChar()) == ('#')) {
            System.out.println("Moving to a passage.");
            return 6;
        } else if ((gridStack[x][y].peek()) instanceof Room) {
            System.out.println("Moving to a room space.");
            return 7;
        } else if ((objectGrid[x][y].getChar()) == ('+')) {
            System.out.println("Passage start.");
            return 8;
        } else if ((objectGrid[x][y].getChar()) == (']')) {
            System.out.println("Armor on floor.");
            return 9;
        } else if ((objectGrid[x][y].getChar()) == (')')) {
            System.out.println("Sword on floor.");
            return 10;
        } else if ((objectGrid[x][y].getChar()) == ('?')) {
            System.out.println("Scroll on floor.");
            return 11;
        }
        return 0;
    }

    public int checkForItem(int x, int y) {
        //System.out.println("x: " +x);
        //System.out.println("y: " +y);
        //Char newCh = new Char('X');
        //System.out.println(gridStack[x][y].get(2));
        if (gridStack[x][y].elementAt(2) instanceof Armor) {
            //System.out.println("Picked up armor.");
            return 9;
        } else if (gridStack[x][y].elementAt(2) instanceof Sword) {
            //System.out.println("Picked up a sword.");
            return 10;
        } else if (gridStack[x][y].elementAt(2) instanceof Scroll) {
            //System.out.println("Picked up a scroll.");
            return 11;
        }
        return 0;
    }

    public void updateHealth(int Health,int x, int y) {
        Char space = new Char(' ');
        Char H = new Char('H');
        addCharToDisplay(H, 0, 0);
        for(int j = 4;j<9;j++)
        {
            addCharToDisplay(space,j,y);
        }
        char[] health = (Integer.toString(Health)).toCharArray();
        for(int i = 0; i<health.length ; i++){
            Char a = new Char(health[i]);
            addCharToDisplay(a, x++, y);
        }
        terminal.repaint();
    }

    public Monster getMonster(int x, int y){
        return (Monster) gridStack[x][y].peek();
    }

    public Item getItem(int x, int y) {
        System.out.println("stack at the item " + gridStack[x][y].toString());
        if (gridStack[x][y].get(gridStack[x][y].size()-2) instanceof Player) { // 2 worked but is odd behavior when multiple items are on one square
            Item i = new Item();
            return i;
        }
        Item item = (Item) gridStack[x][y].elementAt(gridStack[x][y].size()-2);
        //Item item = new Item();
        gridStack[x][y].remove(gridStack[x][y].size()-2); 

        //System.out.println("stack after removal of item: " + gridStack);
        return item;

    }

    public void dropItem(int x, int y, Item item) {
        int i = 1;
        while (gridStack[x][y].get(gridStack[x][y].size()-1) instanceof Item) {
            i++;
        }
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                gridStack[x][y].add(gridStack[x][y].size()-i, item);
                writeToTerminal(x, y);
            }
        }
    }

    public void hallucinate() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height-4; j++) {
					if (gridStack[i][j].peek() instanceof Displayable) {
                        Char ch = new Char('x');
                        objectGrid[i][j] = ch;
					} else {
                        // floor class
					}               
            }
        }
    }

    public void removeHallucinate() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height-4; j++) {
					if (gridStack[i][j].peek() instanceof Displayable) {
                        if ((gridStack[i][j].peek()) instanceof Room) {
                            if (i == gridStack[i][j].get(1).getPosX() || j == gridStack[i][j].get(1).getPosY() || i == gridStack[i][j].get(1).getWidth()-1 || j == gridStack[i][j].get(1).getHeight()-1) {
                                objectGrid[i][j] = new Char('X');
                            } else {
                                objectGrid[i][j] = new Char('.');
                            }
                        } else if ((gridStack[i][j].peek()) instanceof Passage) {
                            if (gridStack[i+1][j].peek() instanceof Passage && gridStack[i-1][j].peek() instanceof Room) {
                                objectGrid[i][j] = new Char('+');
                            } else {
                                objectGrid[i][j] = new Char('#');
                            }
                        } else if ((gridStack[i][j].peek()) instanceof Monster) {
                            Monster m = (Monster) gridStack[i][j].peek();
                            if ((m.getName()).equals("Snake")) {
                                objectGrid[i][j] = new Char('S');
                            } else if ((m.getName()).equals("Troll")) {
                                objectGrid[i][j] = new Char('T');
                            } else if ((m.getName()).equals("Hobgoblin")) {
                                objectGrid[i][j] = new Char('H');
                            }
                        } else if ((gridStack[i][j].peek()) instanceof Sword) {
                            objectGrid[i][j] = new Char(')');
                        } else if ((gridStack[i][j].peek()) instanceof Armor) {
                            objectGrid[i][j] = new Char(']');
                        } else if ((gridStack[i][j].peek()) instanceof Scroll) {
                            objectGrid[i][j] = new Char('?');
                        } else if ((gridStack[i][j].peek()) instanceof Player){
                            objectGrid[i][j] = new Char('@');
                        }
                        writeToTerminal(i, j);
					} else {
                        // do nothing
					}               
            }
        }
    }

    public void printInventory(List<Item> inventory) {
        int i;
        int j;
        int k;
        int l;
        int x=6;
        int h = height - 3;
        //clear inventory space text
        System.out.println("width: " + width);
        int a = x;
            for (l=0; l < width*2; l++) {
                Char c = new Char(' ');
                addCharToDisplay(c, x++, h);
                if (l > width && h == (height - 2)) {
                    break;
                } else if (l > width) {
                    x = 0;
                    h += 1;
                }
            }
        x = a;
        h = height - 3;
        for (i=0; i < inventory.size(); i++) {
            if (x > width || (x+10 > width)) {
                x = 0;
                h += 1;
            }
            char[] index = ("["+(i+1)+"]").toCharArray();
            char[] str1 = null;
            if ((inventory.get(i)).getIsEquipped()) { 
                str1 = ((inventory.get(i)).getNameA()).toCharArray();
            } else {
                str1 = ((inventory.get(i)).getName()).toCharArray();
            }
            
            for (k=0; k < index.length; k++) {
                Char c = new Char(index[k]);
                addCharToDisplay(c, x++, h);
            }
            x += 1;
            //System.out.println("i: " + i);
            //System.out.println("inventory thing " + (inventory.get(i)).getName());
            
            for (j=0; j < str1.length; j++) {
                Char b = new Char(str1[j]);
                addCharToDisplay(b, x++, h);
            }
            x += 2;
        } 

        terminal.repaint();
    }

    public void printInfo(String info) {
        int i;
        int l;
        int x=6;
        int h = height - 1;
        int a = x;
            for (l=0; l < width*2; l++) {
                Char c = new Char(' ');
                addCharToDisplay(c, x++, h);
                if (l > width && h == (height - 2)) {
                    break;
                } else if (l > width) {
                    x = 0;
                    h += 1;
                }
            }
        x = a;
        h = height - 1;
        char[] displayed = info.toCharArray();
        for (i=0; i < displayed.length; i++) {
            Char c = new Char(displayed[i]);
            addCharToDisplay(c, x++, h);
        } 

        terminal.repaint();
    }

    public void PrintDamage(int Pdamage,int Mdamage)
    {
        ClearInfo();
        int xpos = 6;
        int ypos = height - 1;
        Char P = new Char('P');
        Char l = new Char('l');
        Char a = new Char('a');
        Char y = new Char('y');
        Char e = new Char('e');
        Char r = new Char('r');
        
        Char d = new Char('d');
        Char t = new Char('t');

        Char m = new Char('m');
        Char g = new Char('g');

        Char n = new Char('n');

        Char c = new Char('c');
        Char i = new Char('i');
        Char v = new Char('v');


        addCharToDisplay(P,xpos++,ypos);
        addCharToDisplay(l,xpos++,ypos);
        addCharToDisplay(a,xpos++,ypos);
        addCharToDisplay(y,xpos++,ypos);
        addCharToDisplay(e,xpos++,ypos);
        addCharToDisplay(r,xpos++,ypos);
        xpos++;
        addCharToDisplay(d,xpos++,ypos);
        addCharToDisplay(e,xpos++,ypos);
        addCharToDisplay(a,xpos++,ypos);
        addCharToDisplay(l,xpos++,ypos);
        addCharToDisplay(t,xpos++,ypos);
        xpos++;
        char[] pdam = (Integer.toString(Pdamage)).toCharArray();
        for(int j = 0; j<pdam.length ; j++){
            Char dam = new Char(pdam[j]);
            addCharToDisplay(dam, xpos++, ypos);
        }
        xpos++;
        addCharToDisplay(d,xpos++,ypos);
        addCharToDisplay(a,xpos++,ypos);
        addCharToDisplay(m,xpos++,ypos);
        addCharToDisplay(a,xpos++,ypos);
        addCharToDisplay(g,xpos++,ypos);
        addCharToDisplay(e,xpos++,ypos);
        xpos++;
        addCharToDisplay(a,xpos++,ypos);
        addCharToDisplay(n,xpos++,ypos);
        addCharToDisplay(d,xpos++,ypos);
        xpos++;
        addCharToDisplay(r,xpos++,ypos);
        addCharToDisplay(e,xpos++,ypos);
        addCharToDisplay(c,xpos++,ypos);
        addCharToDisplay(e,xpos++,ypos);
        addCharToDisplay(i,xpos++,ypos);
        addCharToDisplay(v,xpos++,ypos);
        addCharToDisplay(e,xpos++,ypos);
        addCharToDisplay(d,xpos++,ypos);
        xpos++;
        char[] mdam = (Integer.toString(Mdamage)).toCharArray();
        for(int j = 0; j<mdam.length ; j++){
            Char dama = new Char(mdam[j]);
            addCharToDisplay(dama, xpos++, ypos);
        }
        xpos++;
        addCharToDisplay(d,xpos++,ypos);
        addCharToDisplay(a,xpos++,ypos);
        addCharToDisplay(m,xpos++,ypos);
        addCharToDisplay(a,xpos++,ypos);
        addCharToDisplay(g,xpos++,ypos);
        addCharToDisplay(e,xpos++,ypos);

        terminal.repaint();
        
    }

    public void PrintMessage(String msg){
        ClearInfo();
        char[] Printmsg = msg.toCharArray();
        int xpos = 6;
        int ypos = height - 1;
        for(int j = 0; j<Printmsg.length ; j++){
            Char dam = new Char(Printmsg[j]);
            addCharToDisplay(dam, xpos++, ypos);
        }
    }

    public void ClearInfo(){
        Char space = new Char(' ');
        int xpos = 6;
        int ypos = height - 1; 
        for(int j = xpos; j<width ; j++){
            addCharToDisplay(space, xpos++, ypos);
        }
    }

    public void addObjectToDisplay(Displayable d, Char ch, int x, int y) {
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                gridStack[x][y].push(d);
                objectGrid[x][y] = ch;
                writeToTerminal(x, y);
            }
        }
    }

    public void addCharToDisplay(Char ch, int x, int y) {
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                objectGrid[x][y] = ch;
                writeToTerminal(x, y);
            }
        }
    }

    public void removeObjectToDisplay(int x, int y){
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                gridStack[x][y].pop();
                if ((gridStack[x][y].peek()) instanceof Room) {
                    objectGrid[x][y] = new Char('.');
                } else if ((gridStack[x][y].peek()) instanceof Passage) {
                    if (gridStack[x+1][y].peek() instanceof Passage && gridStack[x-1][y].peek() instanceof Room) {
                        objectGrid[x][y] = new Char('+');
                    } else {
                        objectGrid[x][y] = new Char('#');
                    }
                } else if ((gridStack[x][y].peek()) instanceof Monster) {
                    Monster m = (Monster) gridStack[x][y].peek();
                    if ((m.getName()).equals("Snake")) {
                        objectGrid[x][y] = new Char('S');
                    } else if ((m.getName()).equals("Troll")) {
                        objectGrid[x][y] = new Char('T');
                    } else if ((m.getName()).equals("Hobgoblin")) {
                        objectGrid[x][y] = new Char('H');
                    }
                } else if ((gridStack[x][y].peek()) instanceof Sword) {
                    objectGrid[x][y] = new Char(')');
                } else if ((gridStack[x][y].peek()) instanceof Armor) {
                    objectGrid[x][y] = new Char(']');
                } else if ((gridStack[x][y].peek()) instanceof Scroll) {
                    objectGrid[x][y] = new Char('?');
                } else if ((gridStack[x][y].peek()) instanceof Player){
                    objectGrid[x][y] = new Char('@');
                }
                writeToTerminal(x, y);
            }
        }
    }

    public void changeObjectToDisplay(Char ch, int x, int y) {
        if (!((objectGrid[x][y]).equals(' '))) {
            Char newCh = new Char('\0');
            (objectGrid[x][y]) = newCh;
        }
        objectGrid[x][y] = ch;
        writeToTerminal(x, y);
    }

    private void writeToTerminal(int x, int y) {
        char ch = objectGrid[x][y].getChar();
        terminal.write(ch, x, y);
        terminal.repaint();
    }
}
