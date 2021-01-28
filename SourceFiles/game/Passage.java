package game;
import java.util.ArrayList;
import java.util.List;

class Passage extends Structure {
	
	String name;
	int room1_ID;
	int room2_ID;
	List<Integer> posX = new ArrayList<Integer>();
	List<Integer> posY = new ArrayList<Integer>();
	
	public Passage() {
		System.out.print("Passage \n");
	}
	
	public void setName(String _name) {
		name = _name;
		System.out.print("Name: " +name+ "\n");
	}
	
	public void setID(int room1, int room2) {
		room1_ID = room1;
		room2_ID = room2;
		System.out.print("Room1_ID: " +room1_ID+ "\n");
		System.out.print("Room2_ID: " +room2_ID+ "\n");
	}

	public void setPassagePosX(int _x) {
		posX.add(_x);
	}

	public List<Integer> getPassagePosX() {
		return posX;
	}	

	public void setPassagePosY(int _y) {
		posY.add(_y);
	}

	public List<Integer> getPassagePosY() {
		return posY;
	}		
}