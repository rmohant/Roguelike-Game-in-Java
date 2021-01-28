package game;

class Remove extends CreatureAction {
	
	String name;
	
	public Remove(String _name, Creature owner) {
		super(owner);
		name = _name;
		System.out.print("Remove: " +name+ "\n");
	}
}