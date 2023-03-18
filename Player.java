/*
	Apolline Weinstein
	Mr. Cruté
	AT1
	Date created: wed, jan 25, 2023
	Date last edited: thurs, feb 2, 2023
	Description: Player class has attributes deck, protection, and name and creates a player both AI or user to 
	play goFish
*/

public class Player implements Comparable<Player>{

	String name;
	DoublyList<Card> deck; 
	DoublyList<Card> protection;
	String x;
	int y;

	//CONSTRUCTORS
	public Player(){
		name = "";
		deck = new DoublyList<Card>();
		protection = new DoublyList<Card>();
	}

	public Player(String x, int y){
        this.x = x;
        this.y = y;
    }

    //GETTERS
	public String getName(){
		return name;
	}

	public DoublyList<Card> getDeck(){
		return deck;
	}

	public DoublyList<Card> getProtection(){
		return protection;
	}

	//SETTERS
	public void setName(String name){
		this.name = name;
	}

	public void setDeck(DoublyList<Card> deck){
		this.deck = deck;
	}

	public void setProtection(DoublyList<Card> protection){
		this.protection = protection;
	}

	public void printDeck(){
		System.out.println(deck.toString());
	}

	//override compareTo function of Comparable
	@Override public int compareTo(Player a){
		if (this.x.compareTo(a.x) != 0) {
			return this.x.compareTo(a.x);
		}else{
			return this.y - a.y;
		}
	}

	//check if inputed card exists in this player's deck, returns true if found (else false)
	public boolean findCard(int find){
		for(int i = 0; i < deck.size(); i++){
			if(deck.valueAt(i).getIntValue() == find){
				return true;
			}
		}
		return false;
	}

	//remove all instances of value requested and store them in a DoublyList, return that DoublyList
	public DoublyList<Card> removeCards(int value){
		DoublyList<Card> removed = new DoublyList<Card>();
		for(int i = 0; i < deck.size(); i++){
			//System.out.println("value" + deck.valueAt(i).getIntValue());
			if(deck.valueAt(i).getIntValue() == value){
				//System.out.println("remove" + deck.valueAt(i).getIntValue());
				Card take = deck.remove(deck.valueAt(i));
				//System.out.println("take"+take);
				removed.append(take);
				i--;
				//System.out.println("ahhh"+removed.toString());
			}
		}
		return removed;
	}

	//4 nested for loops, checking for 4 of the same values within this player's deck, return the value if it 
	//appears 4 times (else return -1)
	public int checkFour(){
		if(deck.size() >= 4){
			for(int i = 0; i < deck.size()-3; i++){
				for(int j = i + 1; j < deck.size()-2; j++){
					for(int k = j + 1; k < deck.size()-1; k++){
						for(int l = k + 1; l < deck.size(); l++){
							if(deck.valueAt(i).getIntValue() == deck.valueAt(j).getIntValue() && deck.valueAt(j).getIntValue() == deck.valueAt(k).getIntValue() && deck.valueAt(k).getIntValue() == deck.valueAt(l).getIntValue()){
								System.out.println(name + " has completed a set of four " + deck.valueAt(i).getStringValue() + "s, they have now been but into protection. Congrats!");
								return deck.valueAt(i).getIntValue();
							}
						}
					}
				}
			}
		}
		return -1;
	}

	//if there are 4 of the same cards, remove them from the deck and add them to the protection DoublyList 
	public boolean putProtect(){
		DoublyList<Card> removed = new DoublyList<Card>();	
		int temp = checkFour();

		if(temp != -1){
			removed = removeCards(temp);
			//add all cards to protection array
			for(int i = 0; i < removed.size(); i++){
				protection.append(removed.pop());
			}
			return true;
		}
		return false;
	}

}