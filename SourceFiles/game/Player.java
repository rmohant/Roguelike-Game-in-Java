package game;

// import java Queue
import java.util.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
//import game.ObjectDisplayGrid;

class Player extends Creature implements InputObserver, Runnable {
	
   // pass in odg at a reference function
   // use keyboardlistener or similar

   private Queue<Character> inputQueue = new ConcurrentLinkedQueue<>();
   public static final int rWALL = 1;
   public static final int pWALL = 2;
   public static final int SNAKE = 3;
   public static final int TROLL = 4;
   public static final int HOBGOBLIN = 5;
   public static final int pWALKWAY = 6;
   public static final int rSPACE = 7;
   public static final int pDOORWAY = 8;
   public static final int ARMOR = 9;
   public static final int SWORD = 10;
   public static final int SCROLL = 11;
   private boolean bArmorOn = false;
   private boolean bSword = false;

   private List<Item> inventory = new ArrayList<Item>();
   private List<Item> equipped = new ArrayList<Item>();

   int init;

   int moved = -1;

   // instant this in player or inline

   public Player(int _init) {
      init = _init;
   }

   public int getInit() {
      return init;
   }

	Item weapon;
	int HPMoves;
	
	public void setWeapon(Item _sword) {
		weapon = _sword;
		System.out.print("weapon: " +weapon+ "\n");
	}

	public void setHPMoves(int _HPMoves) {
		HPMoves = _HPMoves;
		System.out.print("HPMoves: " +HPMoves+ "\n");
	}

   public int getHPMoves(){
      return HPMoves;
   }

   public void addItem(Item item) {
      item.setIsEquipped(false);
      inventory.add(item);
   }

	private void movePlayer(Player player, Char ch1, Char ch2, int posX, int posY) {
      int OrigPosX = this.getPosX();
      int OrigPosY = this.getPosY();

      if ((ObjectDisplayGrid.returnObjectGrid()).checkObject(OrigPosX + posX, 2 + OrigPosY + posY) == rWALL) {
         // X
         System.out.println("Trying to move to a wall.");
      } else if ((ObjectDisplayGrid.returnObjectGrid()).checkObject(OrigPosX + posX, 2 + OrigPosY + posY) == pWALL) {
         // Space
         System.out.println("Trying to move to a passage wall.");
      } else if ((ObjectDisplayGrid.returnObjectGrid()).checkObject(OrigPosX + posX, 2 + OrigPosY + posY) == SNAKE) {
         // S
         // Return monster variable
         System.out.println((ObjectDisplayGrid.returnObjectGrid()).getMonster(OrigPosX + posX, 2 + OrigPosY + posY).getHp());
         creatureAttackCreature(this,(ObjectDisplayGrid.returnObjectGrid()).getMonster(OrigPosX + posX, 2 + OrigPosY + posY),OrigPosX + posX, 2 + OrigPosY + posY);
         (ObjectDisplayGrid.returnObjectGrid()).updateHealth(this.getHp(), 4, 0);
         System.out.println("Trying to move to a snake.");
      } else if ((ObjectDisplayGrid.returnObjectGrid()).checkObject(OrigPosX + posX, 2 + OrigPosY + posY) == TROLL) {
         //T
         System.out.println((ObjectDisplayGrid.returnObjectGrid()).getMonster(OrigPosX + posX, 2 + OrigPosY + posY).getHp());
         creatureAttackCreature(this,(ObjectDisplayGrid.returnObjectGrid()).getMonster(OrigPosX + posX, 2 + OrigPosY + posY),OrigPosX + posX, 2 + OrigPosY + posY);
         (ObjectDisplayGrid.returnObjectGrid()).updateHealth(this.getHp(), 4, 0);
         System.out.println("Trying to move to a troll.");
      } else if ((ObjectDisplayGrid.returnObjectGrid()).checkObject(OrigPosX + posX, 2 + OrigPosY + posY) == HOBGOBLIN) {
         System.out.println((ObjectDisplayGrid.returnObjectGrid()).getMonster(OrigPosX + posX, 2 + OrigPosY + posY).getHp());
         creatureAttackCreature(this,(ObjectDisplayGrid.returnObjectGrid()).getMonster(OrigPosX + posX, 2 + OrigPosY + posY),OrigPosX + posX, 2 + OrigPosY + posY);
         (ObjectDisplayGrid.returnObjectGrid()).updateHealth(this.getHp(), 4, 0);
         System.out.println("Trying to move to a hobgoblin.");
      } else if ((ObjectDisplayGrid.returnObjectGrid()).checkObject(OrigPosX + posX, 2 + OrigPosY + posY) == ARMOR) {
         // H
         System.out.println("Trying to move to an armor.");
         (ObjectDisplayGrid.returnObjectGrid()).addObjectToDisplay(player, ch1, OrigPosX + posX, 2 + OrigPosY + posY);
         (ObjectDisplayGrid.returnObjectGrid()).removeObjectToDisplay(OrigPosX, 2 + OrigPosY);
         this.setPosX(OrigPosX + posX);
         this.setPosY(OrigPosY + posY);
      } else if ((ObjectDisplayGrid.returnObjectGrid()).checkObject(OrigPosX + posX, 2 + OrigPosY + posY) == SWORD) {
         // H
         System.out.println("Trying to move to a sword.");
         (ObjectDisplayGrid.returnObjectGrid()).addObjectToDisplay(player, ch1, OrigPosX + posX, 2 + OrigPosY + posY);
         (ObjectDisplayGrid.returnObjectGrid()).removeObjectToDisplay(OrigPosX, 2 + OrigPosY);
         this.setPosX(OrigPosX + posX);
         this.setPosY(OrigPosY + posY);
      } else if ((ObjectDisplayGrid.returnObjectGrid()).checkObject(OrigPosX + posX, 2 + OrigPosY + posY) == SCROLL) {
         // H
         System.out.println("Trying to move to a scroll.");
         (ObjectDisplayGrid.returnObjectGrid()).addObjectToDisplay(player, ch1, OrigPosX + posX, 2 + OrigPosY + posY);
         (ObjectDisplayGrid.returnObjectGrid()).removeObjectToDisplay(OrigPosX, 2 + OrigPosY);
         this.setPosX(OrigPosX + posX);
         this.setPosY(OrigPosY + posY);
      } else {
         if ((ObjectDisplayGrid.returnObjectGrid()).checkObject(OrigPosX + posX, 2 + OrigPosY + posY) == pWALKWAY || (ObjectDisplayGrid.returnObjectGrid()).checkObject(OrigPosX + posX, 2 + OrigPosY + posY) == pDOORWAY) {
            // Passage
            if ((ObjectDisplayGrid.returnObjectGrid()).checkObject(OrigPosX + posX, 2 + OrigPosY + posY) == pWALKWAY) {
               (ObjectDisplayGrid.returnObjectGrid()).addObjectToDisplay(player, ch1, OrigPosX + posX, 2 + OrigPosY + posY);
               (ObjectDisplayGrid.returnObjectGrid()).removeObjectToDisplay(OrigPosX, 2 + OrigPosY);
            } else if ((ObjectDisplayGrid.returnObjectGrid()).checkObject(OrigPosX + posX, 2 + OrigPosY + posY) == pDOORWAY) {
               (ObjectDisplayGrid.returnObjectGrid()).addObjectToDisplay(player, ch1, OrigPosX + posX, 2 + OrigPosY + posY);
               (ObjectDisplayGrid.returnObjectGrid()).removeObjectToDisplay(OrigPosX, 2 + OrigPosY);
            }
         } else if ((ObjectDisplayGrid.returnObjectGrid()).checkObject(OrigPosX + posX, 2 + OrigPosY + posY) == rSPACE) {
            // Room space
            (ObjectDisplayGrid.returnObjectGrid()).addObjectToDisplay(player, ch1, OrigPosX + posX, 2 + OrigPosY + posY);
            (ObjectDisplayGrid.returnObjectGrid()).removeObjectToDisplay(OrigPosX, 2 + OrigPosY);
         }
         this.setPosX(OrigPosX + posX);
         this.setPosY(OrigPosY + posY);
      }
   }

   public void displayInventory() {
      (ObjectDisplayGrid.returnObjectGrid()).printInventory(inventory);
      // [1] armor [2] scroll
   }

   public void pickUpItem(Player player) {
      int OrigPosX = this.getPosX();
      int OrigPosY = this.getPosY();
      //System.out.println("Entered pickUpItem function.");

      if ((ObjectDisplayGrid.returnObjectGrid()).checkForItem(OrigPosX, 2 + OrigPosY) == ARMOR) {
         // ]
         inventory.add((ObjectDisplayGrid.returnObjectGrid()).getItem(OrigPosX, 2 + OrigPosY));
         (ObjectDisplayGrid.returnObjectGrid()).printInfo(inventory.get(inventory.size()-1).getName()+" has been added to inventory.");
         // find what is below the stack
         // add to inventory
      } else if ((ObjectDisplayGrid.returnObjectGrid()).checkForItem(OrigPosX, 2 + OrigPosY) == SWORD) {
         // )
         
         inventory.add((ObjectDisplayGrid.returnObjectGrid()).getItem(OrigPosX, 2 + OrigPosY));
         //(ObjectDisplayGrid.returnObjectGrid()).printInfo(inventory.get(inventory.size()-1).getMessage());
      } else if ((ObjectDisplayGrid.returnObjectGrid()).checkForItem(OrigPosX, 2 + OrigPosY) == SCROLL) {
         // ?
         
         inventory.add((ObjectDisplayGrid.returnObjectGrid()).getItem(OrigPosX, 2 + OrigPosY));
         (ObjectDisplayGrid.returnObjectGrid()).printInfo(inventory.get(inventory.size()-1).getName()+" has been added to inventory.");
      }
      //
   }

   public void dropItem(Player player, int slot) {
      int OrigPosX = this.getPosX();
      int OrigPosY = this.getPosY();
      //System.out.println("Entered dropItem function.");
      if (inventory.size() <= 0) {
         return;
      } else if (slot > inventory.size()) {
         return;
      }
      if (inventory.get(slot-1) instanceof Armor || inventory.get(slot-1) instanceof Sword) {
         for (int i=0; i < equipped.size(); i++) {
            if (equipped.get(i) instanceof Armor) {
               equipped.remove(i);
               bArmorOn = false;
               break;
            }
            if (equipped.get(i) instanceof Sword) {
               equipped.remove(i);
               bSword = false;
               break;
            }
         }
      }
      (inventory.get(slot-1)).setIsEquipped(false);
      Item dropped = inventory.remove(slot-1);
      (ObjectDisplayGrid.returnObjectGrid()).dropItem(OrigPosX, 2 + OrigPosY, dropped);
      (ObjectDisplayGrid.returnObjectGrid()).printInfo(dropped.getName()+" has been dropped.");
   }


   int movecounter;

   public void Movehealth(){
      movecounter++;
      if(movecounter == this.getHPMoves()){
         movecounter = 0;
         this.setHp(this.getHp()+1);
         (ObjectDisplayGrid.returnObjectGrid()).updateHealth(this.getHp(), 4, 0);
      }
   }

   public void hallucinate() {
      System.out.println(moved);
      if (moved == 0) {
         (ObjectDisplayGrid.returnObjectGrid()).removeHallucinate();
      }
      (ObjectDisplayGrid.returnObjectGrid()).hallucinate();
   }
   
   

   public void observerUpdate(char ch) {
      Character ch1 = new Character(ch);
      inputQueue.add(ch1);
      // may need to create a Character
      // place ch into the queue
   }

	private boolean processInput( ) {

      char ch;
      Char ch1 = new Char('@');
      Char ch2 = new Char('.');
        
      boolean isPrevCharI = false;
      boolean isPrevCharD = false;
      boolean processing = false;
      if(this.getHp() >= 0){
         processing = true;
      }
      while (processing) {
         if (inputQueue.peek() == null) {
            processing = false;
            } else {
               ch = inputQueue.poll();
               //System.out.println("character " + isPrevCharI + " entered on the keyboard");
            if (ch == 'h') {
               System.out.println("character " + ch + " entered on the keyboard");
					movePlayer(this, ch1, ch2, -1, 0);
               if (moved >= 0) {
                  //hallucinate();
                  moved--;
               }
               Movehealth();
            } else if (ch == 'j') {
               System.out.println("character " + ch + " entered on the keyboard");
               movePlayer(this, ch1, ch2, 0, 1);
               if (moved >= 0) {
                  //hallucinate();
                  moved--;
               }
               Movehealth();
            } else if (ch == 'k') {
               System.out.println("character " + ch + " entered on the keyboard");
               movePlayer(this, ch1, ch2, 0, -1);
               if (moved >= 0) {
                  //hallucinate();
                  moved--;
               }
               Movehealth();
            } else if (ch == 'l') {
               System.out.println("character " + ch + " entered on the keyboard");
               movePlayer(this, ch1, ch2, 1, 0);
               if (moved >= 0) {
                  //hallucinate();
                  moved--;
               }
               Movehealth();
            } else if (ch == 'i') {
               System.out.println("character " + ch + " entered on the keyboard");
               displayInventory();
            } else if (ch == 'p') {
               System.out.println("character " + ch + " entered on the keyboard");
               pickUpItem(this);
               if (inventory.size() > 0) {
               if (inventory.get(inventory.size()-1) instanceof Scroll){
               if(inventory.get(inventory.size()-1).getItemAction() != null){
                  if(inventory.get(inventory.size()-1).getItemAction().getName().equals("BlessArmor")){
                     (ObjectDisplayGrid.returnObjectGrid()).PrintMessage(inventory.get(inventory.size()-1).getItemAction().getMessage());

                  }
                  else if(inventory.get(inventory.size()-1).getItemAction().getName().equals("Hallucinate")){
                     (ObjectDisplayGrid.returnObjectGrid()).PrintMessage(inventory.get(inventory.size()-1).getItemAction().getMessage());
                  }
               }
            }
            }
            } else if (ch == 'd') {
               System.out.println("character " + ch + " entered on the keyboard");
               while (inputQueue.peek() == null);
               ch = inputQueue.poll();
               if (Character.getNumericValue(ch) > inventory.size() || Character.getNumericValue(ch) <= 0) {
                  (ObjectDisplayGrid.returnObjectGrid()).PrintMessage("Unable to drop: invalid inventory slot.");
               }
               if (ch == '1') {
                  int num = 1;
                  System.out.println("character " + ch + " entered on the keyboard");
                  dropItem(this, num);
               } else if (ch == '2') {
                  int num = 2;
                  System.out.println("character " + ch + " entered on the keyboard");
                  dropItem(this, num);
               } else if (ch == '3') {
                  int num = 3;
                  System.out.println("character " + ch + " entered on the keyboard");
                  dropItem(this, num);
               } else if (ch == '4') {
                  int num = 4;
                  System.out.println("character " + ch + " entered on the keyboard");
                  dropItem(this, num);
               } else if (ch == '5') {
                  int num = 5;
                  System.out.println("character " + ch + " entered on the keyboard");
                  dropItem(this, num);
               } else if (ch == '6') {
                  int num = 6;
                  System.out.println("character " + ch + " entered on the keyboard");
                  dropItem(this, num);
               } else if (ch == '7') {
                  int num = 7;
                  System.out.println("character " + ch + " entered on the keyboard");
                  dropItem(this, num);
               } else if (ch == '8') {
                  int num = 8;
                  System.out.println("character " + ch + " entered on the keyboard");
                  dropItem(this, num);
               } else if (ch == '9') {
                  int num = 9;
                  System.out.println("character " + ch + " entered on the keyboard");
                  dropItem(this, num);
               }
            }else if (ch == 'H') {
               System.out.println("character " + ch + " entered on the keyboard");
               while (inputQueue.peek() == null);
               ch = inputQueue.poll();
               if(ch == 'l'){
                  String right = "Press key to go right";
                  (ObjectDisplayGrid.returnObjectGrid()).PrintMessage(right);
               }else if(ch == 'h'){
                  String left = "Press key to go left";
                  (ObjectDisplayGrid.returnObjectGrid()).PrintMessage(left);
               }
               else if(ch == 'j'){
                  String up = "Press key to go up";
                  (ObjectDisplayGrid.returnObjectGrid()).PrintMessage(up);
               }else if(ch == 'k'){
                  String down = "Press key to go down";
                  (ObjectDisplayGrid.returnObjectGrid()).PrintMessage(down);
               }
               else if(ch == 'd'){
                  String drop = "Press key to drop an item";
                  (ObjectDisplayGrid.returnObjectGrid()).PrintMessage(drop);
               }
               else if(ch == 'p'){
                  String pickup = "Press key to pick up an item";
                  (ObjectDisplayGrid.returnObjectGrid()).PrintMessage(pickup);
               }
               else if(ch == 'c'){
                  String right = "Press key to take off armor";
                  (ObjectDisplayGrid.returnObjectGrid()).PrintMessage(right);
               }else if(ch == 'r'){
                  String right = "Press key to read the scroll";
                  (ObjectDisplayGrid.returnObjectGrid()).PrintMessage(right);
               }else if(ch == 'T'){
                  String right = "Press key to take out weapon";
                  (ObjectDisplayGrid.returnObjectGrid()).PrintMessage(right);
               }else if(ch == 'w'){
                  String right = "Press key to wear armor";
                  (ObjectDisplayGrid.returnObjectGrid()).PrintMessage(right);
               }
               else if(ch == '?'){
                  String right = "Press key to list commands";
                  (ObjectDisplayGrid.returnObjectGrid()).PrintMessage(right);
               }else if(ch == 'E'){
                  String right = "Press key to End Game";
                  (ObjectDisplayGrid.returnObjectGrid()).PrintMessage(right);
               }else if(ch == 'i'){
                  String right = "Press key to show inventory";
                  (ObjectDisplayGrid.returnObjectGrid()).PrintMessage(right);
               }
             }
             else if (ch == 'E') {
               System.out.println("character " + ch + " entered on the keyboard");
               while (inputQueue.peek() == null);
               ch = inputQueue.poll(); 
               if(ch == 'Y'){
                  this.setHp(-1);
                  String endgame = "Game has Ended";
                  (ObjectDisplayGrid.returnObjectGrid()).PrintMessage(endgame);
               }
               else if(ch == 'y'){
                  this.setHp(-1);
                  String endgame = "Game has Ended";
                  (ObjectDisplayGrid.returnObjectGrid()).PrintMessage(endgame);
               }
             }
             else if(ch == '?'){
               String commands = "h,l,k,j,i,?,H,c,d,p,R,T,w,E. H <cmd> for more info";
               (ObjectDisplayGrid.returnObjectGrid()).PrintMessage(commands);
             }
               else if (ch == 'c') {
               System.out.println("character " + ch + " entered on the keyboard");
               if (bArmorOn) {
                  for (int i=0; i < equipped.size(); i++) {
                     if (equipped.get(i) instanceof Armor) {
                        for (int j=0; j < inventory.size(); j++) {
                           if (inventory.get(j) instanceof Armor) {
                              (ObjectDisplayGrid.returnObjectGrid()).printInfo(inventory.get(i).getName()+" has been unequipped.");
                              equipped.remove(i);
                              (inventory.get(j)).setIsEquipped(false);
                              break;
                           }
                        }
                        bArmorOn = false;
                     }
                  }
               } else {
                  (ObjectDisplayGrid.returnObjectGrid()).printInfo("You tried to take off your armor, but you have none equipped.");
               }
            } else if (ch == 'r') {
               System.out.println("character " + ch + " entered on the keyboard");
               while (inputQueue.peek() == null);
               ch = inputQueue.poll();
               if (ch >= 49 && ch <= 57) {
                  System.out.println("character " + ch + " entered on the keyboard");
                  if (inventory.size() > 0 && (inventory.get(Character.getNumericValue(ch)-1) instanceof Scroll)) {
                     if (inventory.get(Character.getNumericValue(ch)-1) instanceof Scroll){
                        if(inventory.get(Character.getNumericValue(ch)-1).getItemAction() != null){
                           if(inventory.get(Character.getNumericValue(ch)-1).getItemAction().getName().equals("BlessArmor")){
                              String Blessing = "You have blessed your armor";
                              (ObjectDisplayGrid.returnObjectGrid()).PrintMessage(Blessing);
                              for (int i=0; i < equipped.size(); i++) {
                                 if (equipped.get(i) instanceof Armor) {
                                    (equipped.get(i)).setName("+0 Armor");
                                 }
                              }

                           }
                           else if(inventory.get(Character.getNumericValue(ch)-1).getItemAction().getName().equals("Hallucinate")){
                              String Hal = "You are now Hallucinating";
                              moved = 5;
                              (ObjectDisplayGrid.returnObjectGrid()).PrintMessage(Hal);
                              //hallucinate();
                           }
                        }
                     }
                     this.setMaxHit(inventory.get(Character.getNumericValue(ch)-1).getItemIntValue());
                     equipped.add(inventory.remove(Character.getNumericValue(ch)-1)); // index 1 reserved for armor
                     break;
                  } else {
                     (ObjectDisplayGrid.returnObjectGrid()).printInfo("Unable to read item.");
                  }
               }
            } else if (ch == 't') {
               System.out.println("character " + ch + " entered on the keyboard");
               while (inputQueue.peek() == null);
               ch = inputQueue.poll();
               if (ch >= 49 && ch <= 57) {
                  System.out.println("character " + ch + " entered on the keyboard");
                  if (inventory.get(Character.getNumericValue(ch)-1) instanceof Sword) {
                     if (equipped.size() > 0 && (equipped.contains(inventory.get(Character.getNumericValue(ch)-1)))) {
                        (ObjectDisplayGrid.returnObjectGrid()).printInfo("Sword already equipped.");
                        break;
                     } else {
                        equipped.add(inventory.get(Character.getNumericValue(ch)-1)); // index 1 reserved for armor
                        (ObjectDisplayGrid.returnObjectGrid()).printInfo("Equipped sword: " +equipped.get(equipped.size() - 1).getName());
                        (inventory.get(Character.getNumericValue(ch)-1)).setIsEquipped(true);
                        bSword = true;
                     }
                  } else {
                     if (inventory.size() > 0 && inventory.get(Character.getNumericValue(ch)-1) instanceof Sword) {
                        (ObjectDisplayGrid.returnObjectGrid()).printInfo("Cannot wield armor.");
                     } else if (inventory.size() > 0 && inventory.get(Character.getNumericValue(ch)-1) instanceof Scroll) {
                        (ObjectDisplayGrid.returnObjectGrid()).printInfo("Cannot wield a scroll.");
                     }
                     (ObjectDisplayGrid.returnObjectGrid()).printInfo("Unable to equip.");
                  }
               }
            } else if (ch == 'w') {
               System.out.println("character " + ch + " entered on the keyboard");
               while (inputQueue.peek() == null);
               ch = inputQueue.poll();
               if (ch >= 49 && ch <= 57) {
                  System.out.println("character " + ch + " entered on the keyboard");
                  if (inventory.get(Character.getNumericValue(ch)-1) instanceof Armor) {
                     if (equipped.size() > 0 && (equipped.contains(inventory.get(Character.getNumericValue(ch)-1)))) {
                        (ObjectDisplayGrid.returnObjectGrid()).printInfo("Armor already equipped.");
                        break;
                     } else {
                        equipped.add(inventory.get(Character.getNumericValue(ch)-1)); // index 1 reserved for armor
                        (ObjectDisplayGrid.returnObjectGrid()).printInfo("Equipped armor: " +equipped.get(equipped.size() - 1).getName());
                        (inventory.get(Character.getNumericValue(ch)-1)).setIsEquipped(true);
                        bArmorOn = true;
                     }
                  } else {
                     if (inventory.size() > 0 && inventory.get(Character.getNumericValue(ch)-1) instanceof Sword) {
                        (ObjectDisplayGrid.returnObjectGrid()).printInfo("Cannot wear a sword.");
                     } else if (inventory.size() > 0 && inventory.get(Character.getNumericValue(ch)-1) instanceof Scroll) {
                        (ObjectDisplayGrid.returnObjectGrid()).printInfo("Cannot wear a scroll.");
                     }
                     (ObjectDisplayGrid.returnObjectGrid()).printInfo("Unable to equip.");
                  }
               }
            }
         }
      }
      return true;
   }

   @Override
   public void run() {
      (ObjectDisplayGrid.returnObjectGrid()).registerInputObserver(this);
      boolean working = true;
      while (working) {
      	rest();
         working = (processInput( ));
      }
   }

    private void rest() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}