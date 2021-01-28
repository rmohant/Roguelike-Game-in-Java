package game;

class YouWin extends CreatureAction {
	
	String name;
	
	public YouWin(String _name, Creature owner) {
		super(owner);
		name = _name;
		System.out.print("YouWin: " +name+ "\n");
	}
}