package game;

class ItemAction extends Action {
	
	Item owner;
	
	public ItemAction(Item _owner) {
		super();
		owner = _owner;
	}
	
	public void setName(String _name) {
		name = _name;
		System.out.print("ItemAction Name: " +name+ "\n");
	}

	public String getName(){
		return name;
	}

	public void setType(String _type) {
		type = _type;
		System.out.print("ItemAction Type: " +type+ "\n");
	}

	public String getType(){
		return type;
	}
	
}