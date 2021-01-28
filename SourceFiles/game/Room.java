package game;

class Room extends Structure {
	
	private int ID;
	private Creature creature;

	//private List<Creature>
	
	public Room() {
		System.out.print("Room \n");
	}
	
	
	public void setID(int _room) {
		ID = _room;
		System.out.print("RoomID: " +ID+ "\n");
	}

	public int getRoomID() {
		return ID;
	}
	
	public void setCreature(Creature _Monster) {
		creature = _Monster;
		System.out.print("Creature: " +creature+ "\n");
	}
	
}