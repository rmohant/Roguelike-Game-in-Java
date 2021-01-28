package game;
import java.util.Random;

class Creature extends Displayable {
	
	String name;
	int room;
	int serial;
	Item armor;
	
	public Creature() {
	}
	
	public void setName(String _name) {
		name = _name;
		System.out.print("Name: " +name+ "\n");
	}

	public String getName() {
		//System.out.print("Name: " +name+ "\n");
		return name;
	}
	
	public void setID(int _room, int _serial) {
		room = _room;
		serial = _serial;
		System.out.print("Room: " +room+ "\n");
		System.out.print("Serial: " +serial+ "\n");
	}	

	public int getCreatureRoom() {
		return room;
	}

	public void setArmor(Item _armor) {
		armor = _armor;
		System.out.print("armor: " +armor+ "\n");
	}
	CreatureAction me;
	public void setDeathAction(CreatureAction da) {
		me = da;
		System.out.print("deathAction: " +da+ "\n");
	}

	public CreatureAction getDeathAction(){
		return me;
	}

	CreatureAction he;
	public void setHitAction(CreatureAction ha) {
		he = ha;
		System.out.print("hitAction: " +ha+ "\n");
	}

	public CreatureAction getHitAction(){
		return he;
	}

	

	public void creatureAttackCreature(Player player1,Creature monster1,int x,int y ) {

		Random Rand = new Random();
		int Pdamage = Rand.nextInt(player1.getMaxHit()+1);
		monster1.setHp(monster1.getHp() - Pdamage);

		if(monster1.getHitAction() != null){
			if(monster1.getHitAction().getName().equals("Teleport")){
				boolean check = true;
				Char Troll = new Char('T');
				while(check){
				int teleposY = Rand.nextInt(10);
				int teleposX = Rand.nextInt(10);
				if((ObjectDisplayGrid.returnObjectGrid()).checkObject(x+teleposX,y+teleposY) == 6 || (ObjectDisplayGrid.returnObjectGrid()).checkObject(x+teleposX,y+teleposY) == 7||(ObjectDisplayGrid.returnObjectGrid()).checkObject(x+teleposX,y+teleposY) == 8){
					(ObjectDisplayGrid.returnObjectGrid()).addObjectToDisplay(monster1,Troll, x+teleposX, y+teleposY);
					(ObjectDisplayGrid.returnObjectGrid()).removeObjectToDisplay(x, y);
					monster1.setPosX(x+teleposX);
					monster1.setPosY(y+teleposY);
					String Telestring = "PLayer caused the Monster to Teleport";
					(ObjectDisplayGrid.returnObjectGrid()).PrintMessage(Telestring);
					check = false;
					
				}
				}
			}

		}
		if(monster1.getHitAction() == null){
		int Mdamage = Rand.nextInt(monster1.getMaxHit()+1);
		player1.setHp(player1.getHp() - Mdamage);
		System.out.println("Damage of Monster="+ Mdamage);
		(ObjectDisplayGrid.returnObjectGrid()).PrintDamage(Pdamage, Mdamage);
	}
		if(player1.getHitAction() != null){
			player1.dropItem(player1,1);
			(ObjectDisplayGrid.returnObjectGrid()).PrintMessage(player1.getHitAction().getMessage());
		}
		

		if(monster1.getHp() < 0 ){
			if(monster1.getDeathAction() != null){
			if(monster1.getDeathAction().getType().equals("death")){
				(ObjectDisplayGrid.returnObjectGrid()).PrintMessage(monster1.getDeathAction().getMessage());
				(ObjectDisplayGrid.returnObjectGrid()).removeObjectToDisplay(x, y);
			}
		}
		else{
			 monster1.setMaxHit(0);
		}
	}
		if(player1.getHp()< 0){
			if(player1.getDeathAction() != null){
			if(player1.getDeathAction().getType().equals("death")){
				if(player1.getDeathAction().getName().equals("YouWin")){
					(ObjectDisplayGrid.returnObjectGrid()).removeObjectToDisplay(player1.getPosX(),player1.getPosY()+2);
				}
				if(player1.getDeathAction().getName().equals("EndGame")){
					Char question = new Char('?');
					(ObjectDisplayGrid.returnObjectGrid()).removeObjectToDisplay(player1.getPosX(),player1.getPosY()+2);
					(ObjectDisplayGrid.returnObjectGrid()).addCharToDisplay(question,player1.getPosX(),player1.getPosY()+2);
				}
				(ObjectDisplayGrid.returnObjectGrid()).PrintMessage(player1.getDeathAction().getMessage());

			}
		}
		}


	}
	

	
}