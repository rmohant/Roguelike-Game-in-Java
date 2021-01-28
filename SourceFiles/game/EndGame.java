package game;

class EndGame extends CreatureAction {
	
	String name;
	
	public EndGame(String _name, Creature owner) {
		super(owner);
		name = _name;
		System.out.print("Endgame: " +name+ "\n");
	}
}