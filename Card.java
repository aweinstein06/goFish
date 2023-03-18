/*
	Apolline Weinstein
	Mr. Cruté
	AT1
	Date created: wed, jan 25, 2023
	Date last edited: sun, feb 6, 2023
	Description: Card class has attribute reference(1-52, 104, 156) and can find the suit and value according to it
*/

public class Card implements Comparable<Card>{
	
	int reference;
	String x;
    int y;

    //CONSTRUCTORS
	public Card(){
		reference = 0;
	}

	public Card(int ref){
		reference = ref;
	}

    public Card(String x, int y){
        this.x = x;
        this.y = y;
    }

	//GETTERS
	public String getSuit(){
		String suit = "";
		if((reference >= 0 && reference < 13) || (reference-52 >= 0 && reference-52 < 13) || (reference-104 >= 0 && reference-104 < 13)){
			suit = "hearts";
		}else if((reference >= 13 && reference < 26) || (reference-52 >= 13 && reference-52 < 26) || (reference-104 >= 13 && reference-104 < 26)){
			suit = "diamonds";
		}else if((reference >= 26 && reference < 39) || (reference-52 >= 26 && reference-52 < 39) || (reference-104 >= 26 && reference-104 < 39)){
			suit = "spades";
		}else if((reference >= 39 && reference < 52) || (reference-52 >= 39 && reference-52 < 52) || (reference-104 >= 39 && reference-104 < 52)){
			suit = "clubs";
		}
		return suit;
	}

	public String getStringValue(){
		int value = ((reference+1)%52%13)+1;
		if(value == 1){
			return "Ace";
		}else if(value == 11){
			return "Jack";
		}else if(value == 12){
			return "Queen";
		}else if(value == 13){
			return "King";
		}
		return Integer.toString(value);
	}

	public int getIntValue(){
		return ((reference+1)%52%13)+1;
	}

	public int getReference(){
		return reference+1;
	}

	//SETTERS
	public void setReference(int reference){
		this.reference = reference;
	}
	
	//override the compare to function of comparable
	@Override public int compareTo(Card a)
    {
		if (this.x.compareTo(a.x) != 0) {
			return this.x.compareTo(a.x);
        }
        else {
            return this.y - a.y;
        }
    }

}