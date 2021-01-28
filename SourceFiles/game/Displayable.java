package game;

class Displayable {
	
	int maxHit;
	int hpMoves;
	int HP;
	char t;
	int v;
	int x;
	int y;
	int width;
	int height;
	int visibility;
	int id;
	
	public Displayable() {
	}
	
	public void setInvisible() {
		visibility = 0;
		System.out.print("Visibility: " +visibility+ "\n");
	}
	
	public void setVisible() {
		visibility = 1;
		System.out.print("Visibility: " +visibility+ "\n");
	}
	
	public void setMaxHit(int _maxHit) {
		maxHit = _maxHit;
		System.out.print("maxHit: " +maxHit+ "\n");
	}

	public int getMaxHit() {
		return maxHit;
	}
	
	public void setHpMove(int _hpMoves) {
		hpMoves = _hpMoves;
		System.out.print("hpMoves: " +hpMoves+ "\n");
	}
	
	public void setHp(int _HP) {
		HP = _HP;
		System.out.print("HP: " +HP+ "\n");
	}

	public int getHp() {
		return HP;
	}
	
	public void setType(char _t) {
		t = _t;
		System.out.print("Type: " +t+ "\n");
	}
	
	public void setIntValue(int _v) {
		v = _v;
		System.out.print("IntValue: " +v+ "\n");
	}
	
	public void setPosX(int _x) {
		x = _x;
		System.out.print("PosX: " +x+ "\n");
	}

	public int getPosX() {
		return x;
	}
	
	public void setPosY(int _y) {
		y = _y;
		System.out.print("PosY: " +y+ "\n");
	}

	public int getPosY() {
		return y;
	}
	
	public void setWidth(int _x) {
		width = _x;
		System.out.print("width: " +width+ "\n");
	}

	public int getWidth() {
		return width;
	}
	
	public void setHeight(int _y) {
		height = _y;
		System.out.print("height: " +height+ "\n");
	}		

	public int getHeight() {
		return height;
	}

	public int getRoomID() {
		return id;
	}
	
}