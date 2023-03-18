/*
	Apolline Weinstein
	Mr. Cruté
	AT1
	Date created: wed, jan 25, 2023
	Date last edited: thurs, feb 2, 2023
	Description: goFish class that is programmed to run the game: Go Fish and uses methods from Game.java
*/
import java.util.*;

public class goFish{
	public static void main(String []args){
		
		//INTRO + RULES
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("------------------------------------ GO FISH! ------------------------------------");
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("\n------------------------------------- RULES! -------------------------------------");
		System.out.println("\nObject: make the most matching card sets.");
		System.out.println("\nPlay:");
		System.out.println("- If you have a set of four matching cards, you may put them in protection");
		System.out.println("- If you need other cards to complete a set, you may ask another player for specific cards. For example, if you have two aces, you may ask a particular player for all of his/her aces.");		
		System.out.println("- That player must then give you what you've asked for.");		
		System.out.println("- If you receive the cards you asked for, you may continue to play by asking another player for cards. As long as you get the cards you ask for, you may keep going");		
		System.out.println("- If the player you asked doesn't have the cards you requested, he/she should tell you to \"Go Fish!\"");				
		System.out.println("- You must then pick a card from the main deck and it is the next player's turn.");				
		System.out.println("\nWinning: When a player has no more cards, the game is over. If you're the player who has collected the most sets of cards, you win!");

		//SETUP
		Game game = new Game();
		String playerName = "";
		Scanner scan1 = new Scanner(System.in);
		System.out.print("\nEnter your name: "); 
		playerName = scan1.nextLine();

		//NAME
		game.userName(playerName);

		//GET NUMBER OF PLAYERS
		while(true){
			Scanner scan = new Scanner(System.in); 
			System.out.println("\nHow many players would you like this game to have? (2-4 players)"); //scanner takes in user input
			String answer = scan.nextLine();
			if(answer.equals("2")){
				System.out.println("You will be playing with the computer, Jeffrey");
				game.createPlayers(2);	
				break;
			}else if(answer.equals("3")){
				System.out.println("You will be playing with the computers, Adam and Natalia");
				game.createPlayers(3);
				break;
			}else if(answer.equals("4")){
				System.out.println("You will be playing with the computers Jeffrey, Adam, and Natalia");
				game.createPlayers(4);
				break;
			}
			System.out.println("Invalid input."); 
		}

		
		//CREATE DECK(S)
		while(true){
			Scanner scan = new Scanner(System.in); 
			System.out.println("\nHow many decks would you like this game to have? (1-3 decks)"); //scanner takes in user input
			String answer = scan.nextLine();
			if(answer.equals("1")){
				game.createDecks(1); 
				break;
			}else if(answer.equals("2")){
				game.createDecks(2);
				break;
			}else if(answer.equals("3")){
				game.createDecks(3);
				break;
			}
			System.out.println("Invalid input."); 
		}

		//START GAME
		System.out.println("\n-------------------------------------- START --------------------------------------");
		System.out.println("\nSeven cards have been dealt to each player, the rest was used for the main deck.");

		//SHUFFLE MAIN DECK
		game.shuffleMainDeck();

		//TURNS
		game.order();
		System.out.println("\nA random order has been assigned: ");
		for(int i = 0; i < game.getTurns().size(); i++){
			System.out.println((i+1) + ". " + game.getTurns().valueAt(i).getName());
		}
		System.out.println(game.whichTurn().getName() + " will play first.");

		//DISTRIBUTE CARDS
		game.distrCards();

		//DISPLAY CARDS
		game.displayCards();

		//GAME STARTS
		boolean gameOn = true;
		while(gameOn){
			if(game.getTurns().front().getName().equals(playerName)){
				boolean again = true;
				while(again){
					//MAKE SURE NOBODY HAS 0 CARDS
					if(game.checkOver()){
						gameOn = false;
					}
					int value = 0;
					game.displayCards();
					//INPUT TO REQUEST CARD VALUE
					while(true){
						Scanner scan = new Scanner(System.in); 
						System.out.println("\nWhich value would you like to request? (enter the value of the card, 1 for Ace, 11, for Jack, 12 for Queen, 13 for King)"); //scanner takes in user input
						String answer = scan.nextLine();
						if(answer.equals("1")){
							value = 1;
							break;
						}else if(answer.equals("2")){
							value = 2; 
							break;
						}else if(answer.equals("3")){
							value = 3;
							break;
						}else if(answer.equals("4")){
							value = 4;
							break;
						}else if(answer.equals("5")){
							value = 5;
							break;
						}else if(answer.equals("6")){
							value = 6;
							break;
						}else if(answer.equals("7")){
							value = 7;
							break;
						}else if(answer.equals("8")){
							value = 8;
							break;
						}else if(answer.equals("9")){
							value = 9;
							break;
						}else if(answer.equals("10")){
							value = 10;
							break;
						}else if(answer.equals("11")){
							value = 11;
							break;
						}else if(answer.equals("12")){
							value = 12;
							break;
						}else if(answer.equals("13")){
							value = 13;
							break;
						}
						System.out.println("Invalid input."); 
					}
					//INPUT WHICH PLAYER THE CARD WILL BE REQUESTED TO
					String playerAsk;
					while(true){
						Scanner scan = new Scanner(System.in); 
						System.out.println("\nWhich player would you like to request this card from?"); //scanner takes in user input
						String answer = scan.nextLine();
						if(answer.equals("natalia") || answer.equals("Natalia")){
							playerAsk = "natalia";
							break;
						}else if(answer.equals("adam") || answer.equals("Adam")){
							playerAsk = "adam"; 
							break;
						}else if(answer.equals("jeffrey") || answer.equals("Jeffrey")){
							playerAsk = "jeffrey";
							break;
						}
						System.out.println("Invalid input."); 
					}
					again = game.requestCardUser(playerAsk, value);
					//CHECK IF THERE ARE ANY SETS OF 4
					game.checkProtect(game.getTurns().front());
					//IF THE PLAYER SUCCESSFULLY GOT A CARD, THEY CAN PLAY AGAIN
					if(again){
						System.out.println("\nYou get to play again!");
					}					
				}
			}else{
				boolean again = true;
				Player ai = game.getTurns().front();
				while(again){
					//MAKE SURE NOBODY HAS 0 CARDS
					if(game.checkOver()){
						gameOn = false;
					}
					again = game.requestCardAI(ai);
					//System.out.println("reached 1");
					//System.out.println(game.getTurns().toString());
					//CHECK IF THERE ARE ANY SETS OF 4
					game.checkProtect(ai);
					//IF THE AI SUCCESSFULLY GOT A CARD, THEY CAN PLAY AGAIN
					if(again){
						System.out.println("\n" + ai.getName() + " gets to play again!");
					}
				}
			}
			//MAKE SURE NOBODY HAS 0 CARDS	
			if(game.checkOver()){
				gameOn = false;
			}
			//NEXT PLAYER'S TURN
			game.getTurns().moveDummy();
			System.out.println("\nIt is now " + game.getTurns().front().getName() + "'s turn.");
		}
		
		//ENDGAME
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("----------------------------------- GAME OVER! -----------------------------------");
		System.out.println("----------------------------------------------------------------------------------");
		
		//CHECK AND PRINT OUT WHO WON
		System.out.println(game.checkWinner());

		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("----------------------------------------------------------------------------------");	

	}




}