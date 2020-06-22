package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Player;
import helper.HelperFunctions;
import helper.Tile;

public class CommandLineInterface {
	public static String ready() {
		return Boolean.toString(KingdominoApplication.getGameplay().draftReady());
	}
	
	public static String select(String id) {
		int intId = Integer.valueOf(id);
		if (intId == 0 || intId > 48) {
			return intId + " is not in in the valid range [1,48]";
		} else {
			KingdominoApplication.getGameplay().setId(Integer.valueOf(id));
			return Boolean.toString(KingdominoApplication.getGameplay().selectDomino());
		}
	}
	
	public static String place(String x, String y, String dir) {
		KingdominoApplication.getGameplay().setX(Integer.valueOf(x));
		KingdominoApplication.getGameplay().setY(Integer.valueOf(y));
		KingdominoApplication.getGameplay().setDir(dir);
		return Boolean.toString(KingdominoApplication.getGameplay().placeDomino());
	}
	
	public static String discard() {
		return Boolean.toString(KingdominoApplication.getGameplay().discardDomino());
	}
	
	
	public static String kingdom(String player) {
		Player playerObj = null;
		for (Player p : KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
			if (p.getColor().toString().equalsIgnoreCase(player)) {
				playerObj = p;
				break;
			}
		}
		
		if (playerObj == null)
			return "There is no player with color " + player;
		
		StringBuffer kingdom = new StringBuffer(10*9*3);
		Tile[][] tiles = HelperFunctions.getKingdomAsTiles(playerObj, DominoStatus.PlacedInKingdom);
		for (int j = 0; j < 9; ++j) {
			for (int i = 0; i < 9; ++i) {
				if (tiles[j][i] == null) {
					kingdom.append("00 ");
				} else {
					if (tiles[j][i].getKingdomTerritory() instanceof DominoInKingdom) {
						Domino domino = ((DominoInKingdom)tiles[j][i].getKingdomTerritory()).getDomino();
						kingdom.append(String.format("%02d", domino.getId()) + " ");
					} else {
						kingdom.append("CC ");
					}
				}
			}
			kingdom.append('\n');
		}
		
		return kingdom.toString();
	}
	
	public static String selected() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		DominoSelection selected = game.getCurrentPlayer().getDominoSelection();
		Domino domino = selected.getDomino();
		
		return selected == null ? "You haven't selected a domino yet" : helper.HelperFunctions.dominoToString(domino);
	}
	
	public static String selections() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft nextDraft = game.getNextDraft();
		
		StringBuffer ret = new StringBuffer();
		if (nextDraft != null) {
			for (DominoSelection ds : nextDraft.getSelections()) {
				ret.append(HelperFunctions.dominoToString(ds.getDomino()) + '\n');
			}
		}
		
		return nextDraft == null ? "Next draft is not made yet" : ret.toString();
	}
	
	public static String currentDraft() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft currDraft = game.getCurrentDraft();
		
		StringBuffer ret = new StringBuffer();
		if (currDraft != null) {
			for (Domino d : currDraft.getIdSortedDominos()) {
				ret.append(HelperFunctions.dominoToString(d) + '\n');
			}
		}
		
		return currDraft == null ? "Current draft is not available" : ret.toString();
	}
	
	public static String nextDraft() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft nextDraft = game.getNextDraft();
		
		StringBuffer ret = new StringBuffer();
		if (nextDraft != null) {
			for (Domino d : nextDraft.getIdSortedDominos()) {
				ret.append(HelperFunctions.dominoToString(d) + '\n');
			}
		}
		
		return nextDraft == null ? "Next draft is not made yet" : ret.toString();
	}
}
