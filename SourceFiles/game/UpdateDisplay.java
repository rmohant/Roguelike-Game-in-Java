package game;

class UpdateDisplay extends CreatureAction {
	
	String name;
	
	public UpdateDisplay(String _name, Creature owner) {
		super(owner);
		name = _name;
		System.out.print("UpdateDisplay: " +name+ "\n");
	}
}