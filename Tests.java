public class Tests{
	
	public static void main(String []args){
		CircularDoublyList<Player> turns = new CircularDoublyList<Player>();

		Game game = new Game();

		game.order();
		System.out.println("A random order has been assigned, " + game.whichTurn().getName() + " will play first.");

	

	}
}