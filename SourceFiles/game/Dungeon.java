package game;

import java.util.*;

class Dungeon {
	
	String name;
	int width;
	int gameHeight;
	int tHeight;
	int bHeight;
	Room room;
	ArrayList<Room> rooms = new ArrayList<Room>();
	ArrayList<Monster> monsters = new ArrayList<Monster>();
	ArrayList<Passage> passages = new ArrayList<Passage>();
	ArrayList<Item> items = new ArrayList<Item>();
	Player player = new Player(-1);
	
	public void getDungeon(String _name, int _width, int _gameHeight) {
		name = _name;
		width = _width;
		gameHeight = _gameHeight;
		System.out.print("Dungeon \n");
		System.out.print("Name: " +name+ "\n");
		System.out.print("Width: " +width+ "\n");
		System.out.print("GameHeight: " +gameHeight+ "\n");
	}

	public void setTopHeight(int _tHeight) {
		tHeight = _tHeight;
	}

	public int getTopHeight() {
		return tHeight;
	}

	public void setBottomHeight(int _bHeight) {
		bHeight = _bHeight;
	}

	public int getBottomHeight() {
		return bHeight;
	}

	public int getDungeonHeight() {
		return gameHeight;
	}

	public int getDungeonWidth() {
		return width;
	}

	public void addRoom(Room _room) {
		rooms.add(_room);
		//System.out.print("Room: " +room+ "\n");
	}

	public Room getRoom(int index) {
		return rooms.get(index);
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}
	
	public void addMonster(Monster _monster) {
		monsters.add(_monster);
		//System.out.print("Creature: " +creature+ "\n");
	}

	public void addPlayer(Player _player) {
		player = _player;
		//System.out.print("Creature: " +creature+ "\n");
	}

	public Creature getMonster(int index) {
		return monsters.get(index);
	}
	
	public ArrayList<Monster> getMonsters() {
		return monsters;
	}

	public Player getPlayer() {
			if (player.getInit() != -1) {
				return player;
			} else {
				throw new NullPointerException("Could not find player in dungeon.");
			}
	}

	public void addPassage(Passage _passage) {
		passages.add(_passage);
		//System.out.print("Passage: " +passage+ "\n");
	}

	public Passage getPassage(int index) {
		return passages.get(index);
	}

	public ArrayList<Passage> getPassages() {
		return passages;
	}
	
	public void addItem(Item _item) {
		items.add(_item);
		//System.out.print("Item: " +item+ "\n");
	}	

	public Item getItem(int index) {
		return items.get(index);
	}

	public ArrayList<Item> getItems() {
		return items;
	}
	
}