package game;

class Item extends Displayable {
	
	Creature owner;
	int val;
	int x;
	int y;
	String name;
	String nameA;
	int roomID;
	boolean isEquipped = false;

	public void setOwner(Creature _owner) {
		owner = _owner;
		System.out.print("Owner: " + owner + "\n");
	}
	
	public void setItemIntValue(int _val) {
		val = _val;
		System.out.print("ItemIntValue: " + val + "\n");
	}

	public int getItemIntValue() {
		return val;
	}

	ItemAction holder;

	public void setItemAction(ItemAction ia){
		holder = ia;

	}

	public ItemAction getItemAction(){
		return holder;
	}

	public void setPosX(int _x) {
		x = _x;
		if (x != -1) {
			System.out.print("PosX: " +x+ "\n");
		}
	}

	public int getPosX() {
		return x;
	}
	
	public void setPosY(int _y) {
		y = _y;
		if (y != -1) {
			System.out.print("PosY: " +y+ "\n");
		}
	}

	public int getPosY() {
		return y;
	}

	public void setName(String _name) {
		name = _name;
	}

	public String getName() {
		return name;
	}

	public String getNameA() {
		if (this instanceof Armor) {
			return (name+" (a)");
		} else if (this instanceof Sword) {
			return (name+" (w)");
		} else {
			return name;
		}
	}

	public void setIsEquipped(boolean choice) {
		isEquipped = choice;
	}

	public boolean getIsEquipped() {
		return isEquipped;
	}

	public int getRoomID() {
		return roomID;
	}
	
}