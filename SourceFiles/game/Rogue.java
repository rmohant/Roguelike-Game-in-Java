package game;
import asciiPanel.AsciiPanel;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;


import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;


public class Rogue implements Runnable {

    private static final int DEBUG = 0;
    private boolean isRunning;
    public static final int FRAMESPERSECOND = 60;
    public static final int TIMEPERLOOP = 1000000000 / FRAMESPERSECOND;
    private static ObjectDisplayGrid displayGrid = null;
    private Thread keyStrokePrinter;
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;


    public Rogue(int width, int height, int tHeight, int bHeight, Dungeon dungeon) {
        displayGrid = ObjectDisplayGrid.returnObjectGrid(width, height + tHeight + bHeight);
        displayGrid.initializeDisplay();

        displayGrid.set_tHeight(tHeight);
        displayGrid.set_bHeight(bHeight);

        for (Room room : (dungeon.getRooms())) {
            displayGrid.initializeDisplayRoom(room, tHeight);
            for (Item item : dungeon.getItems()) {
                if (item.getPosX() != -1) { //&& (item.getRoomID() == room.getRoomID())) {
                    System.out.println("item name: "+item.getName());
                    displayGrid.initializeDisplayItem(item, room, tHeight);
                }
            }
            for (Monster monster : dungeon.getMonsters()) {
                displayGrid.initializeDisplayMonster(monster, room, tHeight);
            }
            displayGrid.initializeDisplayPlayer(dungeon.getPlayer(), room, tHeight);
        }

        for (Passage passage : dungeon.getPassages()) {
            displayGrid.initializeDisplayPassage(passage, tHeight);
        }

        //registerInputObserver(players.get(0));

        for (Room room : dungeon.getRooms()) {
            if ((dungeon.getPlayer()).getCreatureRoom() == room.getRoomID()) {
                ((dungeon.getPlayer())).setPosX(room.getPosX() + ((dungeon.getPlayer())).getPosX());
                ((dungeon.getPlayer())).setPosY(room.getPosY() + ((dungeon.getPlayer())).getPosY());
            }
        }

        displayGrid.initializeDisplayTopHeight();
        displayGrid.initializeDisplayBottomHeight(height + tHeight + bHeight);
        displayGrid.updateHealth((dungeon.getPlayer()).getHp(), 4, 0);
        
        //KeyStrokePrinter key = new KeyStrokePrinter(displayGrid);
        (dungeon.getPlayer()).run();
         
            //System.out.println("Item: " +item);

    }

    @Override
    public void run() {
        displayGrid.fireUp();
        //for (int step = 1; step < WIDTH / 2; step *= 2) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            
            //List<Room> rooms = handler.getRooms();
        //}
    }

    public static void main(String[] args) throws Exception {

       	// check if a filename is passed in.  If not, print a usage message.
	// If it is, open the file
        String fileName = null;
        switch (args.length) {
        case 1:
           // note that the relative file path may depend on what IDE you are
	   // using.  This worked for NetBeans.
           fileName = "xmlFiles/" + args[0];
           break;
        default:
           System.out.println("java Test <xmlfilename>");
	   return;
        }

	// Create a saxParserFactory, that will allow use to create a parser
	// Use this line unchanged
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

	// We haven't covered exceptions, so just copy the try { } catch {...}
	// exactly, // filling in what needs to be changed between the open and 
	// closed braces.
        try {
	    // just copy this
            SAXParser saxParser = saxParserFactory.newSAXParser();
	    // just copy this
            DungeonXMLHandler handler = new DungeonXMLHandler();
	    // just copy this.  This will parse the xml file given by fileName
            saxParser.parse(new File(fileName), handler);
	    // This will change depending on what kind of XML we are parsing
            List<Room> rooms = handler.getRooms();
            List<Passage> passages = handler.getPassages();
            List<Armor> armors = handler.getArmors();
            List<Scroll> scrolls = handler.getScrolls();
            List<Sword> swords = handler.getSwords();
            List<Player> players = handler.getPlayers();
            List<Monster> monsters = handler.getMonsters();
            Dungeon dungeon = handler.getDungeon();
	    // print out all of the students.  This will change depending on 
	    // what kind of XML we are parsing
            for (Room room : rooms) {
               System.out.println("Width: "+room.getWidth()+ " and Height: " +room.getHeight());
               System.out.println("PosX: "+room.getPosX()+ " and PosY: " +room.getPosY());
            }

              
            Rogue test = new Rogue(dungeon.getDungeonWidth(), dungeon.getDungeonHeight(), dungeon.getTopHeight(), dungeon.getBottomHeight(), dungeon);
            Thread testThread = new Thread(test);
            testThread.start();

            test.keyStrokePrinter = new Thread(new KeyStrokePrinter(displayGrid));
            test.keyStrokePrinter.start();

            testThread.join();
            test.keyStrokePrinter.join();

	// these lines should be copied exactly.
      } catch (ParserConfigurationException | SAXException | IOException e) {
         e.printStackTrace(System.out);
      }
    }
}