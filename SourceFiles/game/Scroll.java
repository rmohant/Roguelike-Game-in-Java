package game;

class Scroll extends Item {
	
	String name;
	int roomID;
	int serial;
	
	public Scroll(String _name) {
		name = _name;
		System.out.print("Scroll: " +name+ "\n");
	}

	public void setName(String _name) {
		name = _name;
		System.out.print("name: " +name+ "\n");
	}

	public String getName() {
		return name;
	}
	
	public void setID(int room, int _serial) {
		roomID = room;
		serial = _serial;
		System.out.print("RoomID: " +roomID+ "\n");
		System.out.print("Serial: " +serial+ "\n");
	}

}