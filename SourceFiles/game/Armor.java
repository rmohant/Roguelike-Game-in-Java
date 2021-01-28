package game;

class Armor extends Item {
	
	String name;
	int ID;
	int serial;
	
	public Armor(String _name) {
		name = _name;
		System.out.print("Armor: " +name+ "\n");
	}
	
	public void setName(String _name) {
		name = _name;
		System.out.print("name: " +name+ "\n");
	}

	public String getName() {
		return name;
	}

	public String getNameA() {
		return (name+" (a)");
	}
	
	public void setID(int _room, int _serial) {
		ID = _room;
		serial = _serial;
		System.out.print("ID: " +ID+ "\n");
		System.out.print("serial: " +serial+ "\n");
	}
	
}