package game;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KeyStrokePrinter implements InputObserver, Runnable {

    private static int DEBUG = 1;
    private static String CLASSID = "KeyStrokePrinter";
    private static Queue<Character> inputQueue = null;
    private ObjectDisplayGrid displayGrid;

    public KeyStrokePrinter(ObjectDisplayGrid grid) {
        inputQueue = new ConcurrentLinkedQueue<>();
        displayGrid = grid;

    }

    @Override
    public void observerUpdate(char ch) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".observerUpdate receiving character " + ch);
        }
        inputQueue.add(ch);
    }

    private void rest() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean processInput() {

        char ch;
        //Char ch1 = new Char('@');
        //Char ch2 = new Char('.');
        //int posX = player.getPosX();
        //int posY = player.getPosY();


        boolean processing = true;
        while (processing) {
            if (inputQueue.peek() == null) {
                processing = false;
            } else {
                ch = inputQueue.poll();
                if (DEBUG > 1) {
                    System.out.println(CLASSID + ".processInput peek is " + ch);
                } if (ch == 'h') {
                    System.out.println("character " + ch + " entered on the keyboard");
                    //if (grid[posX-1][posY].getChar() == 'X') {
                    //    return true;
                    //}
                    //grid[posX-1][posY] = ch1;
                    //grid[posX][posY] = ch2;
                    //terminal.write(ch1, posX-1, posY);
                    //terminal.write(ch2, posX, posY);
                    //terminal.repaint();
                    //addObjectToDisplay(ch1, posX-1, posY);
                    //addObjectToDisplay(ch2, posX, posY);
                    //layer.setPosX(posX-1);
                } else if (ch == 'j') {
                    System.out.println("character " + ch + " entered on the keyboard");
                    //if (grid[posX][posY+1].getChar() == 'X') {
                    //    return true;
                    //}
                    //grid[posX][posY+1] = ch1;
                    //grid[posX][posY] = ch2;
                    //terminal.write(ch1, posX, posY+1);
                    //terminal.write(ch2, posX, posY);
                    //terminal.repaint();
                    //addObjectToDisplay(ch1, posX, posY+1);
                    //addObjectToDisplay(ch2, posX, posY);
                    //player.setPosY(posY+1);
                } else if (ch == 'k') {
                    System.out.println("character " + ch + " entered on the keyboard");
                    //grid[posX][posY-1] = ch1;
                    //grid[posX][posY] = ch2;
                    //terminal.write(ch1, posX, posY-1);
                    //terminal.write(ch2, posX, posY);
                    //terminal.repaint();                    
                    //addObjectToDisplay(ch1, posX, posY-1);
                    //addObjectToDisplay(ch2, posX, posY);
                    //player.setPosY(posY-1);
                } else if (ch == 'l') {
                    System.out.println("character " + ch + " entered on the keyboard");
                    //grid[posX+1][posY] = ch1;
                    //grid[posX][posY] = ch2;
                    //terminal.write(ch1, posX+1, posY);
                    //terminal.write(ch2, posX, posY);
                    //terminal.repaint();                    
                    //addObjectToDisplay(ch1, posX+1, posY);
                    //addObjectToDisplay(ch2, posX, posY);
                    //player.setPosX(posX+1);
                }
            }
        }
        return true;
    }

    @Override
    public void run() {
        displayGrid.registerInputObserver(this);
        boolean working = true;
        while (working) {
            rest();
            working = (processInput( ));
        }
    }
}
