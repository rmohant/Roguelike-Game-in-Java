package game;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;

public class DungeonXMLHandler extends DefaultHandler {
	
    // the two lines that follow declare a DEBUG flag to control
    // debug print statements and to allow the class to be easily
    // printed out.  These are not necessary for the parser.
    private static final int DEBUG = 1;
    private static final String CLASSID = "DungeonXMLHandler";
    
    // data can be called anything, but it is the variables that
    // contains information found while parsing the xml file
    private StringBuilder data = null;
    
    // When the parser parses the file it will add references to
    // Student objects to this array so that it has a list of 
    // all specified students.  Had we covered containers at the
    // time I put this file on the web page I would have made this
    // an ArrayList of Students (ArrayList<Student>) and not needed
    // to keep tract of the length and maxStudents.  You should use
    // an ArrayList in your project.
    private ArrayList<Room> rooms = new ArrayList<Room>();
    //private ArrayList<Creature> creatures  = new ArrayList<Creature>();
    private ArrayList<Monster> monsters  = new ArrayList<Monster>();
    private ArrayList<Player> players  = new ArrayList<Player>();
    //private ArrayList<Item> items = new ArrayList<Item>();
    private ArrayList<Armor> armors = new ArrayList<Armor>();
    private ArrayList<Sword> swords = new ArrayList<Sword>();
    private ArrayList<Scroll> scrolls = new ArrayList<Scroll>();

    private ArrayList<Passage> passages = new ArrayList<Passage>();
    private Dungeon dungeon = new Dungeon();
    
    private int maxCreatures = 0;
    private int creatureCount = 0;
    private int maxRooms = 0;
    private int roomCount = 0;
    private int maxPassages= 0;
    private int passagesCount = 0;
    private int maxPlayers = 0;
    private int playerCount = 0;
    //private int maxItems = 0;
    //private int itemCount = 0;
    private int maxMonsters = 0;
    private int monsterCount = 0;
    private int armorCount = 0;
    private int scrollCount = 0;
    private int swordCount = 0;
    
    // The XML file contains a list of Students, and within each 
    // Student a list of activities (clubs and classes) that the
    // student participates in.  When the XML file initially
    // defines a student, many of the fields of the object have
    // not been filled in.  Additional lines in the XML file 
    // give the values of the fields.  Having access to the 
    // current Student and Activity allows setters on those 
    // objects to be called to initialize those fields.
    //private Creature creatureBeingParsed = null;
    private Action actionBeingParsed = null;
    //private Displayable displayableBeingParsed = null;
    //private Item itemBeingParsed = null;
    private Room roomBeingParsed = null;
    private Monster monsterBeingParsed = null;
    private Player playerBeingParsed = null;
    private Passage passageBeingParsed = null;

    private Armor armorBeingParsed = null;
    private Scroll scrollBeingParsed = null;
    private Sword swordBeingParsed = null;
    
    // The bX fields here indicate that at corresponding field is
    // having a value defined in the XML file.  In particular, a
    // line in the xml file might be:
    // <instructor>Brook Parke</instructor> 
    // The startElement method (below) is called when <instructor>
    // is seen, and there we would set bInstructor.  The endElement
    // method (below) is called when </instructor> is found, and
    // in that code we check if bInstructor is set.  If it is,
    // we can extract a string representing the instructor name 
    // from the data variable above.
    private boolean bVisible = false;
    private boolean bPosX = false;
    private boolean bPosY = false;
    private boolean bWidth = false;
    private boolean bHeight = false;
    private boolean bType = false;
    private boolean bHP = false;
    private boolean bHPMoves = false;
    private boolean bMaxHit= false;
    private boolean bActionMessage = false;
    private boolean bActionCharValue = false;
    private boolean bActionIntValue = false;
    private boolean bItemAction = false;
    private boolean bItemIntValue = false;

    private boolean bMonster = false;
    private boolean bPlayer = false;
    private boolean bSword = false;
    private boolean bArmor = false;
    private boolean bScroll = false;
    private boolean bPassage = false;
    
    // Used by code outside the class to get the list of Student objects
    // that have been constructed.
    public ArrayList<Room> getRooms() {
        return rooms;
    }  
    public ArrayList<Passage> getPassages() {
        return passages;
    }   
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }
    public Dungeon getDungeon() {
        return dungeon;
    }
    public ArrayList<Armor> getArmors() {
        return armors;
    }
    public ArrayList<Scroll> getScrolls() {
        return scrolls;
    }
    public ArrayList<Sword> getSwords() {
        return swords;
    }

    // A constructor for this class.  It makes an implicit call to the
    // DefaultHandler zero arg constructor, which does the real work
    // DefaultHandler is defined in org.xml.sax.helpers.DefaultHandler;
    // imported above, and we don't need to write it.  We get its 
    // functionality by deriving from it!
    public DungeonXMLHandler() {
    }

    // startElement is called when a <some element> is called as part of 
    // <some element> ... </some element> start and end tags.
    // Rather than explain everything, look at the xml file in one screen
    // and the code below in another, and see how the different xml elements
    // are handled.
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (DEBUG > 1) {
            System.out.println(CLASSID + ".startElement qName: " + qName);
        }

        if (qName.equalsIgnoreCase("Dungeon")) {
        	String nameDungeon = attributes.getValue("name");
        	int width = Integer.parseInt(attributes.getValue("width"));
        	int topHeight = Integer.parseInt(attributes.getValue("topHeight"));
        	int gameHeight = Integer.parseInt(attributes.getValue("gameHeight"));
        	int bottomHeight = Integer.parseInt(attributes.getValue("bottomHeight"));
        	Dungeon d = new Dungeon();
        	d.getDungeon(nameDungeon, width, gameHeight);
            d.setTopHeight(topHeight);
            d.setBottomHeight(bottomHeight);
            dungeon = d;
        } else if (qName.equalsIgnoreCase("Rooms")) {
        	roomCount = 0;
        } else if (qName.equalsIgnoreCase("Room")) {
        	roomCount++;
        	int roomID = Integer.parseInt(attributes.getValue("room"));
        	Room r = new Room();
        	r.setID(roomID);
            roomBeingParsed = r;
        } else if (qName.equalsIgnoreCase("visible")) {
        	bVisible = true;
        } else if (qName.equalsIgnoreCase("posX")) {
            bPosX = true;
        } else if (qName.equalsIgnoreCase("posY")) {
            bPosY = true;
        } else if (qName.equalsIgnoreCase("width")) {
            bWidth = true;
        } else if (qName.equalsIgnoreCase("height")) {
            bHeight = true;
        } else if (qName.equalsIgnoreCase("type")) {
            bType = true;
        } else if (qName.equalsIgnoreCase("hp")) {
            bHP = true;
        } else if (qName.equalsIgnoreCase("hpMoves")) {
            bHPMoves = true;
        } else if (qName.equalsIgnoreCase("maxhit")) {
            bMaxHit = true;
        } else if (qName.equalsIgnoreCase("Monster")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
        	Monster monster = new Monster();
        	monster.setName(name);
        	monster.setID(room, serial);
            monsterBeingParsed = monster;
            dungeon.addMonster(monsterBeingParsed);
            creatureCount++;
            bMonster = true;
        } else if (qName.equalsIgnoreCase("CreatureAction")) {
            String name = attributes.getValue("name");
            String type = attributes.getValue("type");
            if (bMonster == true) {
                CreatureAction ca = new CreatureAction(dungeon.getMonster(creatureCount-1));
                ca.setName(name);
                ca.setType(type);
                if(type.equals("death")){
                dungeon.getMonster(creatureCount-1).setDeathAction(ca);
                }
                else if(type.equals("hit")){
                    dungeon.getMonster(creatureCount-1).setHitAction(ca);
                }
                actionBeingParsed = ca; 
                
            } else if (bPlayer == true) {
                CreatureAction ca = new CreatureAction(dungeon.getPlayer());
                ca.setName(name);
                ca.setType(type);
                actionBeingParsed = ca; 
                if(type.equals("death")){
                    dungeon.getPlayer().setDeathAction(ca);
                }
                else if(type.equals("hit")){
                    dungeon.getPlayer().setHitAction(ca);
                }
            } 
        } else if (qName.equalsIgnoreCase("actionMessage")) {
            bActionMessage = true;
        } else if (qName.equalsIgnoreCase("actionIntValue")) {
            bActionIntValue = true;
        } else if (qName.equalsIgnoreCase("actionCharValue")) {
            bActionCharValue = true;
        } else if (qName.equalsIgnoreCase("Player")) {
        	String name = attributes.getValue("name");
        	int room = Integer.parseInt(attributes.getValue("room"));
        	int serial = Integer.parseInt(attributes.getValue("serial"));
        	Player player = new Player(0);
            player.setID(room, serial);
        	playerBeingParsed = player;
            dungeon.addPlayer(playerBeingParsed);
            players.add(player);
            playerCount++;
            bPlayer = true;
        } else if (qName.equalsIgnoreCase("Sword")) {
        	String name = attributes.getValue("name");
        	int room = Integer.parseInt(attributes.getValue("room"));
        	int serial = Integer.parseInt(attributes.getValue("serial"));
        	Sword sw = new Sword(name);
            sw.setPosX(-1);
            sw.setPosY(-1);
        	swords.add(sw);
        	sw.setID(room, serial);
            swordBeingParsed = sw;
            if (bPlayer == true) {
                (players.get(playerCount-1)).addItem(swordBeingParsed);
            } else {
                dungeon.addItem(swordBeingParsed);
            }
            swordCount++;
            bSword = true;
        } else if (qName.equalsIgnoreCase("ItemAction")) {
            String name = attributes.getValue("name");
            String type = attributes.getValue("type");
            if (bSword) {
                ItemAction ia = new ItemAction(swords.get(swordCount-1));
                ia.setName(name);
                ia.setType(type);
                dungeon.getItem(swordCount+scrollCount+armorCount-1).setItemAction(ia);
                actionBeingParsed = ia; 

            } else if (bScroll) {
                ItemAction ia = new ItemAction(scrolls.get(scrollCount-1));
                ia.setName(name);
                ia.setType(type);
                dungeon.getItem(swordCount+scrollCount+armorCount-1).setItemAction(ia);
                actionBeingParsed = ia;
            } else if (bArmor) {
                ItemAction ia = new ItemAction(armors.get(armorCount-1));
                ia.setName(name);
                ia.setType(type);
                dungeon.getItem(swordCount+scrollCount+armorCount-1).setItemAction(ia);
                actionBeingParsed = ia;
            }
        } else if (qName.equalsIgnoreCase("ItemIntValue")) {
            bItemIntValue = true;
        } else if (qName.equalsIgnoreCase("Armor")) {
        	String name = attributes.getValue("name");
        	int room = Integer.parseInt(attributes.getValue("room"));
        	int serial = Integer.parseInt(attributes.getValue("serial"));
        	Armor ar = new Armor(name);
            ar.setPosX(-1);
            ar.setPosY(-1);
        	armors.add(ar);
        	ar.setID(room, serial);
            armorBeingParsed = ar;
            if (bPlayer == true) {
                (players.get(playerCount-1)).addItem(armorBeingParsed);
            } else {
                dungeon.addItem(armorBeingParsed);
            }
            armorCount++;
            bArmor = true;
        } else if (qName.equalsIgnoreCase("CreatureAction")) {
            String name = attributes.getValue("name");
            String type = attributes.getValue("type");
            System.out.println(playerCount);
            if (bMonster == true) {
                CreatureAction ca = new CreatureAction(monsters.get(monsterCount-1));
                ca.setName(name);
                ca.setType(type);
                actionBeingParsed = ca; 
            } else if (bPlayer == true) {
                CreatureAction ca = new CreatureAction(players.get(playerCount-1));
                ca.setName(name);
                ca.setType(type);
                actionBeingParsed = ca; 
            }
        } else if (qName.equalsIgnoreCase("ItemIntValue")) {
            bItemIntValue = true;
        } else if (qName.equalsIgnoreCase("Scroll")) {
        	String name = attributes.getValue("name");
        	int room = Integer.parseInt(attributes.getValue("room"));
        	int serial = Integer.parseInt(attributes.getValue("serial"));
        	Scroll sc = new Scroll(name);
            sc.setPosX(-1);
            sc.setPosY(-1);
        	scrolls.add(sc);
        	sc.setID(room, serial);
            scrollBeingParsed = sc;
            dungeon.addItem(scrollBeingParsed);
            scrollCount++;
            bScroll = true;
            /*
        	String name = attributes.getValue("name");
        	int room = Integer.parseInt(attributes.getValue("room"));
        	int serial = Integer.parseInt(attributes.getValue("serial"));
        	Scroll sc = new Scroll(name);
            sc.setPosX(-1);
            sc.setPosY(-1);
        	scrolls.add(sc);
        	sc.setID(room, serial);
            scrollBeingParsed = sc;
            dungeon.addItem(scrollBeingParsed);
            scrollCount++;
            bScroll = true;
            */
        } else if (qName.equalsIgnoreCase("CreatureAction")) {
            String name = attributes.getValue("name");
            String type = attributes.getValue("type");
            CreatureAction ca = new CreatureAction(players.get(playerCount-1));
            ca.setName(name);
            ca.setType(type);
            actionBeingParsed = ca; 
        } else if (qName.equalsIgnoreCase("ItemIntValue")) {
            bItemIntValue = true;
        } else if (qName.equalsIgnoreCase("Passages")) {
        	passagesCount = 0;
        } else if (qName.equalsIgnoreCase("Passage")) {
            passagesCount++;
        	int room1 = Integer.parseInt(attributes.getValue("room1"));
            int room2 = Integer.parseInt(attributes.getValue("room2"));
           	Passage p = new Passage();
        	p.setID(room1, room2);
            passageBeingParsed = p;
            bPassage = true;
        } else {
            System.out.println("Unknown qname: " + qName);
        }
        data = new StringBuilder();
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (bVisible) {
        	int visibility = Integer.parseInt(data.toString());
            switch (visibility) {
            case 0:
            if (roomBeingParsed != null) {
                roomBeingParsed.setInvisible();
            } else if (monsterBeingParsed != null) {
                monsterBeingParsed.setInvisible();
            } else if (playerBeingParsed != null) {
                playerBeingParsed.setInvisible();
            } else if (passageBeingParsed != null) {
                passageBeingParsed.setInvisible();
            }
            	bVisible = false;
                break;
            case 1:
            if (roomBeingParsed != null) {
                roomBeingParsed.setVisible();
            } else if (monsterBeingParsed != null) {
                monsterBeingParsed.setVisible();
            } else if (playerBeingParsed != null) {
                playerBeingParsed.setVisible();
            } else if (passageBeingParsed != null) {
                passageBeingParsed.setVisible();
            }
            	bVisible = false;
                break;
            default:
                System.out.println("Unknown visibility: " + visibility);
                bVisible = false;
                break;	
            }
        } else if (bPosX) {
            if (swordBeingParsed != null) {
                if (playerBeingParsed == null) {
                    swordBeingParsed.setPosX(Integer.parseInt(data.toString()));
                } else { }
            } else if (armorBeingParsed != null) {
                if (playerBeingParsed == null) {
                    armorBeingParsed.setPosX(Integer.parseInt(data.toString()));
                } else { }
            } else if (scrollBeingParsed != null) {
                if (playerBeingParsed == null) {
                    scrollBeingParsed.setPosX(Integer.parseInt(data.toString()));
                } else { }
            } else if (monsterBeingParsed != null) {
                monsterBeingParsed.setPosX(Integer.parseInt(data.toString()));
            } else if (playerBeingParsed != null) {
                playerBeingParsed.setPosX(Integer.parseInt(data.toString()));
            } else if (roomBeingParsed != null) {
                roomBeingParsed.setPosX(Integer.parseInt(data.toString()));
            } else if (bPassage == true) {
                passageBeingParsed.setPassagePosX(Integer.parseInt(data.toString()));
            }
            bPosX = false;
        } else if (bPosY) {
            if (swordBeingParsed != null) {
                if (playerBeingParsed == null) {
                    swordBeingParsed.setPosY(Integer.parseInt(data.toString()));
                } else { }
            } else if (armorBeingParsed != null) {
                if (playerBeingParsed == null) {
                    armorBeingParsed.setPosY(Integer.parseInt(data.toString()));
                } else { }
            } else if (scrollBeingParsed != null) {
                if (playerBeingParsed == null) {
                    scrollBeingParsed.setPosY(Integer.parseInt(data.toString()));
                } else { }
            } else if (monsterBeingParsed != null) {
                monsterBeingParsed.setPosY(Integer.parseInt(data.toString()));
            } else if (playerBeingParsed != null) {
                playerBeingParsed.setPosY(Integer.parseInt(data.toString()));
            } else if (roomBeingParsed != null) {
                roomBeingParsed.setPosY(Integer.parseInt(data.toString()));
            } if (bPassage == true) {
                passageBeingParsed.setPassagePosY(Integer.parseInt(data.toString()));
            }
            bPosY = false;
        } else if (bWidth) {
            if (monsterBeingParsed != null) {
                monsterBeingParsed.setWidth(Integer.parseInt(data.toString()));
            } else if (playerBeingParsed != null) {
                playerBeingParsed.setWidth(Integer.parseInt(data.toString()));
            } else if (roomBeingParsed != null) {
                roomBeingParsed.setWidth(Integer.parseInt(data.toString()));
            } else if (passageBeingParsed != null) {
                passageBeingParsed.setWidth(Integer.parseInt(data.toString()));
            }
            bWidth = false;
        } else if (bHeight) {
            if (monsterBeingParsed != null) {
                monsterBeingParsed.setHeight(Integer.parseInt(data.toString()));
            } else if (playerBeingParsed != null) {
                playerBeingParsed.setHeight(Integer.parseInt(data.toString()));
            } else if (roomBeingParsed != null) {
                roomBeingParsed.setHeight(Integer.parseInt(data.toString()));
            } else if (passageBeingParsed != null) {
                passageBeingParsed.setHeight(Integer.parseInt(data.toString()));
            }
            bHeight = false;
        } else if (bType) {
            if (monsterBeingParsed != null) {
                monsterBeingParsed.setType((data.toString()).charAt(0));
            } else if (playerBeingParsed != null) {
                playerBeingParsed.setType((data.toString()).charAt(0));
            }   
            bType = false;
        } else if (bHP) {
            if (monsterBeingParsed != null) {
                monsterBeingParsed.setHp(Integer.parseInt((data.toString())));
            } else if (playerBeingParsed != null) {
                playerBeingParsed.setHp(Integer.parseInt((data.toString())));
            }   
            bHP = false;
        } else if (bMaxHit) {
            if (monsterBeingParsed != null) {
                monsterBeingParsed.setMaxHit(Integer.parseInt((data.toString())));
            } else if (playerBeingParsed != null) {
                playerBeingParsed.setMaxHit(Integer.parseInt((data.toString())));
            } 
            bMaxHit = false;
        } else if (bHPMoves) {
            // not currently working
            playerBeingParsed.setHPMoves(Integer.parseInt((data.toString())));
            bHPMoves = false;
        } else if (bActionMessage) {
            actionBeingParsed.setMessage(data.toString());
            bActionMessage = false;
	    } else if (bActionIntValue) {
	        actionBeingParsed.setIntValue(Integer.parseInt((data.toString())));
	        bActionIntValue = false;
		} else if (bActionCharValue) {
		    actionBeingParsed.setCharValue((data.toString()).charAt(0));
		    bActionCharValue = false;
        } else if (bItemIntValue && (swordBeingParsed != null)) {
            swordBeingParsed.setItemIntValue(Integer.parseInt(data.toString()));
            bItemIntValue = false;
        } else if (bItemIntValue && (armorBeingParsed != null)) {
            armorBeingParsed.setItemIntValue(Integer.parseInt(data.toString()));
            bItemIntValue = false;
        } else if (bItemIntValue && (scrollBeingParsed != null)) {
            scrollBeingParsed.setItemIntValue(Integer.parseInt(data.toString()));
		    bItemIntValue = false;
		}

        if (qName.equalsIgnoreCase("Room")) {
            dungeon.addRoom(roomBeingParsed);
            roomBeingParsed = null;
        } else if (qName.equalsIgnoreCase("Monster")) {
            monsterBeingParsed = null;
            bMonster = false;
        } else if (qName.equalsIgnoreCase("Player")) {
            playerBeingParsed = null;
            bPlayer = false;
        } else if (qName.equalsIgnoreCase("Scroll")) {
            //scrolls.add(scrollBeingParsed);
            scrollBeingParsed = null;
            bScroll = false;
        } else if (qName.equalsIgnoreCase("Sword")) {
            //swords.add(swordBeingParsed);
            swordBeingParsed = null;
            bSword = false;
        } else if (qName.equalsIgnoreCase("Armor")) {
            //armors.add(armorBeingParsed);
            armorBeingParsed = null;
            bArmor = false;
        } else if (qName.equalsIgnoreCase("Passage")) {
            dungeon.addPassage(passageBeingParsed);
            passageBeingParsed = null;
            bPassage = false;
        }
    }
    
    /*
    private void addCreature(Creature creature) {
        creatures.add(creature);
    }
    private void addItem(Item item) {
        items.add(item);
    }
    private void addRoom(Room room) {
        rooms.add(room);
    }
    */
    
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
        if (DEBUG > 1) {
            System.out.println(CLASSID + ".characters: " + new String(ch, start, length));
            System.out.flush();
        }
    }
    
}