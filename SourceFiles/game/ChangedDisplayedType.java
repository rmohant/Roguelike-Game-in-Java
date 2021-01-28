package game;

class ChangedDisplayedType extends CreatureAction {
	
	String name;
	
	public ChangedDisplayedType(String _name, Creature owner) {
		super(owner);
		name = _name;
		System.out.print("Name: " +name+ "\n");
	}
	
}