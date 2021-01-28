package game;

class Teleport extends CreatureAction {
	
	String name;
	
	public Teleport(String _name, Creature owner) {
		super(owner);
		name = _name;
		System.out.print("Teleport: " +name+ "\n");
	}
	
}