/*
	Apolline Weinstein
	Mr. Cruté
	AT1
	Date created: wed, jan 25, 2023
	Date last edited: thurs, feb 2, 2023
	Description: Game class is a helper class for goFish that contains various functions to run the game goFish
*/

import java.util.*;

public class Game{
	//PLAYERS
	Player jeffrey;
	Player adam;
	Player natalia;
	Player user = new Player();

	int numPlayers;
	int numDecks;
	DoublyList<Card> mainDeck = new DoublyList<Card>();	
	CircularDoublyList<Player> turns = new CircularDoublyList<Player>();	

	//CONSTRUCTOR
	public Game(){

	}

	//CREATE AI PLAYERS AND GIVE THEM NAMES  - RETURN VOID
	public void createPlayers(int numPlayers){
		this.numPlayers = numPlayers;
		jeffrey = new Player();
		jeffrey.setName("Jeffrey");
		adam = new Player();
		adam.setName("Adam");
		natalia = new Player();
		natalia.setName("Natalia");
	}

	//SET THE USER'S NAME - RETURN VOID
	public void userName(String name){
		user.setName(name);
	}

	//CREATE MAIN DECK ACCORDING TO THE THE NUMBER OF DECKS  - RETURN VOID
	public void createDecks(int numDecks){
		this.numDecks = numDecks;
		if(numDecks == 1){
			//create 52 cards
			for(int i = 0; i < 52; i++){
				Card card = new Card(i);
				mainDeck.append(card);
			}
		}else if(numDecks == 2){
			//create 104 cards
			for(int i = 0; i < 104; i++){
				Card card = new Card(i);
				mainDeck.append(card);
			}
		}else if(numDecks == 3){
			//create 156 cards
			for(int i = 0; i < 156; i++){
				Card card = new Card(i);
				mainDeck.append(card);
			}
		}
	}

	//DISPLAY ENTIRE MAIN DECK (mostly for testing)  - RETURN VOID
	public void displayMainDeck(){
		for(int i = 0; i < mainDeck.size(); i++){
			System.out.print(mainDeck.valueAt(i).getReference() + " ");
		}
	}

	//DISPLAY USER'S CARDS  - RETURN VOID
	public void displayCards(){
		while(true){
			Scanner scan = new Scanner(System.in); 
			System.out.println("\nIf you would like to see your cards, type 's', if not type 'n':");
			String answer = scan.nextLine();
			if(answer.equals("s")|| answer.equals("S")){
				String list = "[";
				//loop through user's deck and add each card to list 
				for(int i = 0; i < user.getDeck().size(); i++){
					if(i == user.getDeck().size() - 1){
						list += user.getDeck().valueAt(i).getStringValue() + " of " + user.getDeck().valueAt(i).getSuit();
					}else{
						list += user.getDeck().valueAt(i).getStringValue() + " of " + user.getDeck().valueAt(i).getSuit() + ", ";
					}
				}
				list += "]";
				System.out.println(list);
				break;
			}else if(answer.equals("n")|| answer.equals("N")){
				break;
			}
			System.out.println("Invalid input.");
		}
	}

	//CREATES ORDER OF GAME - RETURN VOID
	public void order(){
		//temporary list of players, add four players to the list
		DoublyList<Player> tempPlayer = new DoublyList<Player>();
		tempPlayer.append(jeffrey);
		tempPlayer.append(natalia);
		tempPlayer.append(adam);

		//shuffle list
		for(int i = tempPlayer.size()-1; i >= 0; i--){
			int random = (int)(Math.random() * tempPlayer.size());
			Player temp = tempPlayer.valueAt(i);
			tempPlayer.set(i, tempPlayer.valueAt(random));
			tempPlayer.set(random, temp);
		}

		//add players to list according to numPlayers
		turns.append(user);
		for(int i = 0; i < numPlayers-1; i++){
			turns.append(tempPlayer.remove(tempPlayer.valueAt(0)));
		}
		//chose a random player to start		
		turns.start(randPlayer());
	}

	//RETURNS RANDOM PLAYER (user, jeffrey, adam, natalia)
	public Player randPlayer(){
		int rand = 1 + (int)(Math.random() * (numPlayers-1));
		if(rand == 1){
			return user;
		}else if(rand == 2){
			return jeffrey;
		}else if(rand == 3){
			return natalia;
		}else if(rand == 4){
			return adam;
		}
		return null;
	}

	//RETURNS WHO'S TURN IT IS
	public Player whichTurn(){
		return turns.front();
	}

	//DISTRIBUTES CARDS TO USERS - RETURN VOID
	public void distrCards(){
		//loop through players
		for(int i = 0; i < turns.size(); i++){
			//add 7 cards to each of the players' decks
			for(int j = 0; j < 7; j++){
				turns.valueAt(i).getDeck().append(mainDeck.pop());
			}
		}
	}

	//SHUFFLES MAIN DECK - RETURN VOID
	public void shuffleMainDeck(){
		for(int i = mainDeck.size()-1; i >= 0; i--){
			int random = (int)(Math.random() * mainDeck.size());
			Card temp = mainDeck.valueAt(i);
			mainDeck.set(i, mainDeck.valueAt(random));
			mainDeck.set(random, temp);
		}
	}

	//inputs ai, requests a random value (that exists in their deck) to request
	//gets the card, if the requested player doesn't have the card, ai has to go fish
	public boolean requestCardAI(Player pAsking){ 
		int rand = (int)(Math.random() * pAsking.getDeck().size());
		Player randPlayer = randPlayer();
		//while loop making sure the AI cannot pick itself
		while(randPlayer.equals(pAsking)){
			randPlayer = randPlayer();
		}
		Card randCard = pAsking.getDeck().valueAt(rand);
		DoublyList<Card> removedCards = new DoublyList<Card>();
		System.out.println();
		System.out.println(pAsking.getName() + ", is asking " + randPlayer.getName() + " for " + randCard.getStringValue() + "(s)");
		if(randPlayer.findCard(randCard.getIntValue())){
			removedCards = randPlayer.removeCards(randCard.getIntValue());
			System.out.println(randPlayer.getName() + " gave " + pAsking.getName() + " " + removedCards.size() + " " + randCard.getStringValue() + "(s)");
			//loop adding the cards found to the player requesting's deck
			int size = removedCards.size();
			for(int i = 0; i < size; i++){
				pAsking.getDeck().append(removedCards.pop());
			}
			return true;
		}else{
			System.out.println(randPlayer.getName() + " does not have any " + randCard.getStringValue() + "s. GO FISH!");
			if(mainDeck.size() != 0){
				goFish(pAsking);
			}else{
				System.out.println("There are no more cards in the deck, keep playing with your own cards.");
			}
			return false;
		}
	}

	//convert a card value to the to the value displayed
	public String convertValue(int value){
		if(value == 1){
			return "Ace";
		}else if(value == 11){
			return "Jack";
		}else if(value == 12){
			return "Queen";
		}else if(value == 13){
			return "King";
		}else{
			return Integer.toString(value);
		}
	}

	//inputs name of the ai and the value of the requested cards, requests the value to the 
	//inputed ai and gets the card, if the requested ai doesn't have the card, user has to go fish
	public boolean requestCardUser(String name, int value){ 
		//associate name inputed with corresponding ai
		Player player = new Player();
		if(name.equals("natalia")){
			player = natalia;
		}else if(name.equals("jeffrey")){
			player = jeffrey;
		}else if(name.equals("adam")){
			player = adam;
		}
		DoublyList<Card> removedCards = new DoublyList<Card>();
		System.out.println();
		System.out.println(user.getName() + ", is asking " + player.getName() + " for " + convertValue(value) + "(s)");
		
		//if the ai has the card
		if(player.findCard(value)){
			removedCards = player.removeCards(value);
			System.out.println(player.getName() + " gave you " + removedCards.size() + " " + convertValue(value) + "(s)");			
			int size = removedCards.size();
			for(int i = 0; i < size; i++){
				user.getDeck().append(removedCards.pop());
			}
			return true;
		}else{
			//if the ai doesn't have the card
			System.out.println(player.getName() + " does not have any " + convertValue(value) + "s. GO FISH!");
			//making sure the deck isn't empty
			if(mainDeck.size() != 0){
				goFish(user);
			}else{
				System.out.println("There are no more cards in the deck, keep playing with your own cards.");
			}
			return false;
		}
	}

	//remove a card from the main deck it and give it to the inputed player, return void
	public void goFish(Player player){
		Card picked = mainDeck.pop();
		player.getDeck().append(picked);
		if(player.equals(user)){
			System.out.println("You picked a " + picked.getStringValue() + " of " + picked.getSuit());
		}
	}

	//getter for list of players
	public CircularDoublyList<Player> getTurns(){
		return turns;
	}

	//calls the Player putProtect function for player inputed, checking if there are any 
	//4 cards that should be put into protection, return void
	public void checkProtect(Player player){
		player.putProtect();
	}

	//check if any of the players has no more cards, return boolean
	public boolean checkOver(){
		for(int i = 0; i < turns.size(); i++){
			if(turns.valueAt(i).getDeck().size() == 0){
				System.out.println(turns.valueAt(i).getName() + " has no more cards.");
				return true;
			}
		}
		return false;
	}

	//creates a string with all the winners by checking which player has the longest list of cards in protection 
	//returns the endgame string to be printed
	public String checkWinner(){
		String winner = "";
		int maxSize = 0;
		boolean multipleWin = false;
		//loops through players
		for(int i = 0; i < turns.size(); i++){
			if(turns.valueAt(i).getProtection().size() == maxSize){
				if(i == 0){
					winner += turns.valueAt(i).getName(); 
				}else{
					winner += " and " + turns.valueAt(i).getName();
				}
				maxSize = turns.valueAt(i).getProtection().size();
				multipleWin = true;
			}else if(turns.valueAt(i).getProtection().size() > maxSize){
				winner = turns.valueAt(i).getName();
				maxSize = turns.valueAt(i).getProtection().size();
				multipleWin = false;
			}
		}
		//checking for ties
		if(multipleWin){
			return "The winners are " + winner + ". CONGRATS!!!!!";
		}else{
			return "The winner is " + winner + ". CONGRATS!!!!!";
		}
		
	}
	
}