package game;
class Action {
	
	String msg;
	int v;
	char c;
	String name;
	String type;
	
	public void setMessage(String _msg) {
		msg = _msg;
		System.out.print("Message: " +msg+ "\n");
	}

	public String getMessage(){
		return msg;
	}

	public void setIntValue(int _v) {
		v = _v;
		System.out.print("ActionIntValue: " +v+ "\n");
	}
	
	public void setCharValue(char _c) {
		c = _c;
		System.out.print("ActionCharValue: " +c+ "\n");
	}
		
}