package game;

class CreatureAction extends Action {
	
	Creature creature;
	
	
	public CreatureAction(Creature _owner) {
		creature = _owner;
	}

	public Creature getOwner(){
		return creature;
	}


	 public void setName(String _name) {
		 name = _name;
		System.out.print("CreatureAction Name: " +name+ "\n");
	}

	public String getName(){
		return name;
	}

	public void setType(String _type) {
		type = _type;
		System.out.print("CreatureAction Type: " +type+ "\n");
	}

	public String getType(){
		return type;
	}
	
}