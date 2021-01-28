package game;

class Sword extends Item {
	
	String name;
	int roomID;
	int serial;
	
	public Sword(String _name) {
		name = _name;
		System.out.print("Sword: " +name+ "\n");
	}
	
	public void setID(int room, int _serial) {
		roomID = room;
		serial = _serial;
		System.out.print("RoomID: " +roomID+ "\n");
		System.out.print("Serial: " +serial+ "\n");
	}

	public void setName(String _name) {
		name = _name;
		System.out.print("name: " +name+ "\n");
	}

	public String getName() {
		return name;
	}

	public String getNameA() {
		return (name+" (w)");
	}
	
}