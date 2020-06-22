package ca.mcgill.ecse223.kingdomino;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ca.mcgill.ecse223.kingdomino.controller.CommandLineInterface;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.server.Gameplayer;
import ca.mcgill.ecse223.kingdomino.server.JsonToMethodMapper;
import helper.HelperFunctions;

public class KingdominoApplication {

	private static Kingdomino kingdomino;
	private static Gameplay gameplay;

	public static void main(String[] args) {
		
		kingdomino = new Kingdomino();
		getKingdomino().addAllGame((new Game(48, getKingdomino())));
		getKingdomino().setCurrentGame(getKingdomino().getAllGame(0));
		
		Game game = getKingdomino().getCurrentGame();
		
		HelperFunctions.addDefaultUsersAndPlayers(game);
		HelperFunctions.createAllDominoes(game);
		
		for(int i =0; i<47; i++) {
			game.getAllDominos().get(i).setNextDomino(game.getAllDominos().get(i+1));
		}
		game.setTopDominoInPile(game.getAllDominos().get(0));
		
		//resetGameplay();
		//Gameplayer gameplayer = new Gameplayer(getGameplay(), System.in, System.out);
		//gameplayer.run();
	}
	
	public static void getItFromTheHolySource(String[] userNames)
	{
		getKingdomino().addAllGame((new Game(48, getKingdomino())));
		getKingdomino().setCurrentGame(getKingdomino().getAllGame(getKingdomino().getAllGames().size()-1));
		
		Game game = getKingdomino().getCurrentGame();
		
		for (int i = 0; i < userNames.length; i++) {
			User user = User.getWithName(userNames[i]);
			if (user == null)
				 user = game.getKingdomino().addUser(userNames[i]);
			
			Player player = new Player(game);
			player.setUser(user);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
		HelperFunctions.createAllDominoes(game);
		
		for(int i =0; i<47; i++) {
			game.getAllDominos().get(i).setNextDomino(game.getAllDominos().get(i+1));
		}
		game.setTopDominoInPile(game.getAllDominos().get(0));
		
		//resetGameplay();
	}

	public static Kingdomino getKingdomino() {
		if (kingdomino == null) {
			kingdomino = new Kingdomino();
		}
		return kingdomino;
	}

	public static void setKingdomino(Kingdomino kd) {
		kingdomino = kd;
	}
	public static void setGameplay(Gameplay gg)
	{
		gameplay = gg;
	}
	
	public static Gameplay getGameplay() {
		if (gameplay == null) {
			gameplay = new Gameplay();
		}
		return gameplay;
	}
	
	public static Gameplay resetGameplay() {
		return gameplay = new Gameplay();
	}
}
