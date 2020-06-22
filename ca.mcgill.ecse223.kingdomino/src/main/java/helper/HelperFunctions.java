package helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.Property;

public class HelperFunctions {
	public static void addDefaultUsersAndPlayers(Game game) {
		String[] userNames = { "User1", "User2", "User3", "User4" };
		for (int i = 0; i < userNames.length; i++) {
			User user = game.getKingdomino().addUser(userNames[i]);
			Player player = new Player(game);
			player.setUser(user);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
	}

	public static void createAllDominoes(Game game) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
			String line = "";
			String delimiters = "[:\\+()]";
			while ((line = br.readLine()) != null) {
				String[] dominoString = line.split(delimiters); // {id, leftTerrain, rightTerrain, crowns}
				int dominoId = Integer.decode(dominoString[0]);
				TerrainType leftTerrain = getTerrainType(dominoString[1]);
				TerrainType rightTerrain = getTerrainType(dominoString[2]);
				int numCrown = 0;
				if (dominoString.length > 3) {
					numCrown = Integer.decode(dominoString[3]);
				}
				new Domino(dominoId, leftTerrain, rightTerrain, numCrown, game);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new java.lang.IllegalArgumentException(
					"Error occured while trying to read alldominoes.dat: " + e.getMessage());
		}
	}

	public static Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}

	public static TerrainType getTerrainType(String terrain) {
		switch (terrain) {
			case "W":
				return TerrainType.WheatField;
			case "F":
				return TerrainType.Forest;
			case "M":
				return TerrainType.Mountain;
			case "G":
				return TerrainType.Grass;
			case "S":
				return TerrainType.Swamp;
			case "L":
				return TerrainType.Lake;
			default:
				throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
		}
	}

	public static DirectionKind getDirection(String dir) {
		switch (dir) {
			case "up":
				return DirectionKind.Up;
			case "down":
				return DirectionKind.Down;
			case "left":
				return DirectionKind.Left;
			case "right":
				return DirectionKind.Right;
			default:
				throw new java.lang.IllegalArgumentException("Invalid direction: " + dir);
		}
	}

	public static DominoStatus getDominoStatus(String status) {
		switch (status.toLowerCase()) {
			case "inpile":
				return DominoStatus.InPile;
			case "excluded":
				return DominoStatus.Excluded;
			case "incurrentdraft":
				return DominoStatus.InCurrentDraft;
			case "innextdraft":
				return DominoStatus.InNextDraft;
			case "erroneouslypreplaced":
				return DominoStatus.ErroneouslyPreplaced;
			case "correctlypreplaced":
				return DominoStatus.CorrectlyPreplaced;
			case "placednkingdom":
				return DominoStatus.PlacedInKingdom;
			case "discarded":
				return DominoStatus.Discarded;
			default:
				throw new java.lang.IllegalArgumentException("Invalid domino status: " + status);
		}
	}

	public static DominoInKingdom getDominoInKingdom(Player player, DominoStatus includedStatus, DominoStatus ...otherIncludedStatuses) {
		DominoInKingdom currDominoInKingdom = null;
		for (KingdomTerritory territory : player.getKingdom().getTerritories()) {
			if (territory instanceof DominoInKingdom) {
				DominoInKingdom dominoInKingdom = (DominoInKingdom) territory;
				Domino domino = dominoInKingdom.getDomino();
				
				boolean getCurrentDomino = domino.getStatus().equals(includedStatus);
				for (DominoStatus otherIncludedStatus : otherIncludedStatuses) {
					getCurrentDomino = getCurrentDomino || domino.getStatus().equals(otherIncludedStatus);
				}
				
				if (getCurrentDomino) {
					currDominoInKingdom = dominoInKingdom;
					break;
				}
			}
		}
		
		return currDominoInKingdom;
	}
	
	public static DominoInKingdom getPreplacedDominoInKingdom(Player player) {
		DominoInKingdom currDominoInKingdom = null;
		
		for (KingdomTerritory kt : player.getKingdom().getTerritories()) {
			if (kt instanceof DominoInKingdom) {
				if (((DominoInKingdom)kt).getDomino() == player.getDominoSelection().getDomino()) {
					currDominoInKingdom = (DominoInKingdom)kt;
					break;
				}
			}
		}
		
		return currDominoInKingdom;
	}

	public static int[] toIntArray(String[] arr) {
	    int[] ints = new int[arr.length];
	    for (int i = 0; i < arr.length; i++) {
	        ints[i] = Integer.parseInt(arr[i]);
	    }
	    return ints;
	}
	
	public static int[] parseLineToArray(String line) {
		return toIntArray(line.split(","));
	}
	
	public static String changeTypeToLetter(String s) {
		switch (s) {
		case "wheat":
			 return "W";
		case "forest":
			return "F";
		case "mountain":
			return "M";
		case "grass":
			return "G";
		case "swamp":
			return "S";
		case "lake":
			return "L";
		default:
			throw new java.lang.IllegalArgumentException("Invalid terrain type: " + s);
		}
	}
	
	
	public static List<Tile> KingdomTiles(Player aPlayer) {
		List<KingdomTerritory> territories = aPlayer.getKingdom().getTerritories();
		List<Tile> tiles = new ArrayList<Tile>();
		
		for(KingdomTerritory territory : territories) {
			if(territory instanceof DominoInKingdom) {
				DominoInKingdom domInKingdom = (DominoInKingdom) territory;
				int x = territory.getX();
				int y = territory.getY();
				Domino dom = domInKingdom.getDomino();
				Tile tile = new Tile(dom.getLeftTile(), dom.getLeftCrown(), territory, x, y);
				DirectionKind dir = ((DominoInKingdom) territory).getDirection();
				Tile tile2 = null;
				if(dir == DirectionKind.Up) {
					y++;
					tile2 = new Tile(dom.getRightTile(), dom.getRightCrown(), territory, x, y);
				}
				if(dir == DirectionKind.Down) {
					y--;
					tile2 = new Tile(dom.getRightTile(), dom.getRightCrown(), territory, x, y);
				}
				if(dir == DirectionKind.Left) {
					x--;;
					tile2 = new Tile(dom.getRightTile(), dom.getRightCrown(), territory, x, y);
				}
				if(dir == DirectionKind.Right) {
					x++;
					tile2 = new Tile(dom.getRightTile(), dom.getRightCrown(), territory, x, y);
				}
				tiles.add(tile);
				tiles.add(tile2);
			}
		}
		return tiles;
	}
	
	public static List<Tile> getProperty(Player aPlayer, int i) {
		List<Tile> tiles = KingdomTiles(aPlayer);
		List<Tile> propertyTiles = new ArrayList<Tile>();
		
		Tile currentTile = tiles.get(i);
 		propertyTiles.add(currentTile);
		
 		List<Tile> other = new ArrayList<>();
 		List<Tile> sameProperty = sameProperty(tiles, currentTile);
 		for(Tile t : sameProperty) {
 			if(!(propertyTiles.contains(t))) {
 				propertyTiles.add(t);
 			}
 			
 		}
 		
 		return propertyTiles;
	}

	/**
	 * @author Nafiz Islam
	 * @param player
	 * @param x
	 * @param y
	 * @return adjacent KingdomTerritories of a tile at <code>x<code> and
	 *         <code>y<code>
	 */
	public static List<Tile> getAdjacentTiles(Player player, int x, int y, DominoStatus includedStatus, DominoStatus ...otherIncludedStatuses) {
		Tile[][] tiles = getKingdomAsTiles(player, includedStatus, otherIncludedStatuses);
		return getAdjacentTiles(tiles, x, y, includedStatus, otherIncludedStatuses);
	}
	
	public static List<Tile> getAdjacentTiles(Tile[][] tiles, int x, int y, DominoStatus includedStatus, DominoStatus ...otherIncludedStatuses) {
		Set<Tile> adjacentTerritories = new HashSet<>();
		int i = x + 4, j = 4 - y;
		if (i < 8) {
			if (tiles[j][i + 1] != null)
				adjacentTerritories.add(tiles[j][i + 1]);
		}
		if (j < 8) {
			if (tiles[j + 1][i] != null)
				adjacentTerritories.add(tiles[j + 1][i]);
		}
		if (i > 0) {
			if (tiles[j][i - 1] != null)
				adjacentTerritories.add(tiles[j][i - 1]);
		}
		if (j > 0) {
			if (tiles[j - 1][i] != null)
				adjacentTerritories.add(tiles[j - 1][i]);
		}

		return new ArrayList<Tile>(adjacentTerritories);
	}

	public static MyProperty getProperty(Player player, int x, int y, DominoStatus includedStatus, DominoStatus ...otherIncludedStatuses) throws ClassCastException {
		int i = x + 4, j = 4 - y;
		Tile[][] tiles = getKingdomAsTiles(player, includedStatus, otherIncludedStatuses);

		if (tiles[j][i] != null && tiles[j][i].getKingdomTerritory() instanceof DominoInKingdom) {
			MyProperty property = new MyProperty();
			getPropertyHelper(tiles, x, y, property, new ArrayList<>());
			return property;
		} else {
			throw new ClassCastException(
					"The tile at position x and y is not part of an instance of DominoInKingdom");
		}
	}

	private static void getPropertyHelper(Tile[][] tiles, int x, int y, MyProperty property, List<Tile> tilesCovered) {
		int i = x + 4, j = 4 - y;

		if (tiles[j][i] != null && tiles[j][i].getKingdomTerritory() instanceof DominoInKingdom && !tilesCovered.contains(tiles[j][i])) {
			tilesCovered.add(tiles[j][i]);
			DominoInKingdom dominoInKingdom = (DominoInKingdom) tiles[j][i].getKingdomTerritory();
			Domino domino = dominoInKingdom.getDomino();

			// determine the current tile of current domino
			boolean isLeftTile = false;
			switch (dominoInKingdom.getDirection()) {
				case Right:
				case Left:
					if (x == dominoInKingdom.getX()) {
						isLeftTile = true;
					} else {
						isLeftTile = false;
					}
					break;
				case Up:
				case Down:
					if (y == dominoInKingdom.getY()) {
						isLeftTile = true;
					} else {
						isLeftTile = false;
					}
					break;
			}

			// update MyProperty information based on current tile
			if (property.getPropertyType() == null) {
				// this is the first tile for the MyProperty
				// use current tile's TerrainType as the propertType for property
				property.setPropertyType(isLeftTile ? domino.getLeftTile() : domino.getRightTile());
				property.setCrowns(      isLeftTile ? domino.getLeftCrown() : domino.getRightCrown());
				property.setSize(1);
				property.addDominoInKingdom(dominoInKingdom);
			} else {
				// update MyProperty for this new tile (the current)
				if (isLeftTile) {
					if (property.getPropertyType().equals(domino.getLeftTile())) {
						// same TerrainType so update property

						property.addDominoInKingdom(dominoInKingdom);
						property.setSize(property.getSize() + 1);
						property.setCrowns(property.getCrowns() + domino.getLeftCrown());
					} else {
						// base case
						return;
					}
				} else {
					if (property.getPropertyType().equals(domino.getRightTile())) {
						// same TerrainType so update property

						property.addDominoInKingdom(dominoInKingdom);
						property.setSize(property.getSize() + 1);
						property.setCrowns(property.getCrowns() + domino.getRightCrown());
					} else {
						// base case
						return;
					}
				}
			}

			// base cases
			if (i < 8) {
				getPropertyHelper(tiles, x + 1, y + 0, property, tilesCovered);
			}
			if (j < 8) {
				getPropertyHelper(tiles, x + 0, y + 1, property, tilesCovered);
			}
			if (i > 0) {
				getPropertyHelper(tiles, x - 1, y + 0, property, tilesCovered);
			}
			if (j > 0) {
				getPropertyHelper(tiles, x + 0, y - 1, property, tilesCovered);
			}
		}
			
	}
	
	public static KingdomTerritory getTerritoryAtLocation(int x, int y, Kingdom kingdom){
		List<KingdomTerritory> current = kingdom.getTerritories();
		for(KingdomTerritory k : current) {
			
			if (k instanceof Castle) {
				if (k.getX()== x && k.getY()==y) return k;
					
			}
			
			if(k instanceof DominoInKingdom) {
				DominoInKingdom dom = (DominoInKingdom) k;
				if(dom.getX()==x && dom.getY()==y) return dom;
				
				if (dom.getDirection().equals(DirectionKind.Up)) {
					if(dom.getX()==x && dom.getY()+1==y) return dom;
				}
				
				if (dom.getDirection().equals(DirectionKind.Down)) {
					if(dom.getX()==x && dom.getY()-1==y) return dom;
				}
				
				if (dom.getDirection().equals(DirectionKind.Right)) {
					if(dom.getX()+1==x && dom.getY()==y) return dom;
				}
				
				if (dom.getDirection().equals(DirectionKind.Left)) {
					if(dom.getX()-1==x && dom.getY()==y) return dom;
				}
				
			}
			
			
			
		}
		return null;
	}
	
	public static HashMap<String, Player> color2player;
	public static void initializeGame() {
		
		
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		HelperFunctions.addDefaultUsersAndPlayers(game);
		
		color2player = new HashMap<String, Player>();

		// be careful, addDefaultUsers orders colors in very specific way
		// look up addDefaultUsersAndPlayers to know why
		color2player.put("blue", game.getPlayer(0));
		color2player.put("green", game.getPlayer(1));
		color2player.put("yellow", game.getPlayer(2));
		color2player.put("pink", game.getPlayer(3));
		
		kingdomino.addAllGame(game);
		HelperFunctions.createAllDominoes(game);
		
		for(int i =0; i<47; i++) {
			game.getAllDominos().get(i+1).setStatus(DominoStatus.InPile);
			game.getAllDominos().get(i).setNextDomino(game.getAllDominos().get(i+1));
		}
		game.setTopDominoInPile(game.getAllDominos().get(0));
		
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
		
		
	}
	
public static void setPreplacedStatus(Domino domino) {
		
		Game game =KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = game.getCurrentPlayer();
		//domino = player.getDominoSelection().getDomino(); this makes me nervous because i haven't even touched selection yet
		
//		isGridSizeValid();
//		isPreplacedDominoCorrectlyNeighbored();
//		isPreplacedDominoOverlapping();
		
		if (KingdominoController.isKingdomSizeValid()== true) {
				if((KingdominoController.isPreplacedDominoOverlapping()==false)) {
					if ((KingdominoController.isPreplacedDominoCorrectlyNeighbored() == true) || (KingdominoController.isPreplacedDominoAdjacentToCastle() == true)) {
						domino.setStatus(DominoStatus.CorrectlyPreplaced);
						return;
					}
					//else dominoInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
				}
			//else dominoInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);;
		}
		
		domino.setStatus(DominoStatus.ErroneouslyPreplaced);
	}

	public static List<Tile> nextPropertyWithNext(List<Tile> tiles, Tile tile) {
		List<Tile> sameProp = new ArrayList<Tile>();
		
		for(Tile nextTile : tiles) {
			boolean right = nextTile.getX() == tile.getX()+1 && nextTile.getY() == tile.getY();
			boolean left = nextTile.getX() == tile.getX()-1 && nextTile.getY() == tile.getY();
			boolean up = nextTile.getX() == tile.getX() && nextTile.getY() == tile.getY()+1;
			boolean down = nextTile.getX() == tile.getX() && nextTile.getY() == tile.getY()-1;
			
			if(right || left || up || down) {
				if(nextTile.getTerrainType() == tile.getTerrainType()) {
					if(!(sameProp.contains(nextTile))) {
						sameProp.add(nextTile);
					}
				}
			}
		}
		return sameProp;
	}
	
	public static MyProperty makeMyProperty(Player aPlayer, int i) {
		List<Tile> tilesInProperty = getProperty(aPlayer, i);
		Kingdom kingdom = aPlayer.getKingdom();
		MyProperty property = new MyProperty();
		List<DominoInKingdom> included = new ArrayList<DominoInKingdom>();
		List<Integer> domInts = new ArrayList<Integer>();
		
		TerrainType type = null;

		
		for(Tile t : tilesInProperty) {
			type = t.getTerrainType();
			if(!(included.contains((DominoInKingdom) t.getKingdomTerritory()))) {
				included.add((DominoInKingdom) t.getKingdomTerritory());
			}
		}
		
		for(DominoInKingdom dom : included) {
			domInts.add(dom.getDomino().getId());
		}
		
		Collections.sort(domInts);
		included.clear();
		
		for(Integer integer : domInts) {
			DominoInKingdom dik = null;
			
			for (KingdomTerritory terr : aPlayer.getKingdom().getTerritories()) {
				if (terr instanceof DominoInKingdom)
					if (((DominoInKingdom)terr).getDomino().getId() == integer)
						dik = (DominoInKingdom)terr;
			}
			
			included.add(dik);
		}
		
		for(DominoInKingdom d : included) {
			property.addDominoInKingdom(d);
		}
		property.setPropertyType(type);

		return property;
	}
	
	public static boolean propertyEquality(MyProperty p, MyProperty n) {
		List<Domino> pDominos = p.getIncludedDominos1();
		List<Integer> pDominosIDs = new ArrayList<Integer>();
		List<Domino> nDominos = n.getIncludedDominos1();
		List<Integer> nDominosIDs = new ArrayList<Integer>();
		
		for(Domino domino : pDominos) {
			pDominosIDs.add(domino.getId());
		}
		for(Domino domino : nDominos) {
			nDominosIDs.add(domino.getId());
		}
		
		if(p.getPropertyType() != n.getPropertyType()) {
			return false;
		}
		if(p.getCrowns() != n.getCrowns()) {
			return false;
		}
		if(p.getSize() != n.getSize()) {
			return false;
		}
		if(p.getScore() != n.getScore()) {
			return false;
		}
		if(!(pDominos.equals(nDominos))) {
			 return false;
		}
		if(pDominos.containsAll(nDominos)) {
			return false;
		}
		if(pDominosIDs.containsAll(nDominosIDs)) {
			return false;
		}
		return true;
	}
	
	public static void makeProperty(MyProperty mine, Kingdom kingdom) {
		List<Domino> dominos = mine.getIncludedDominos1();
		List<Property> properties = kingdom.getProperties();
		List<MyProperty> myProperties = new ArrayList<MyProperty>();
	    for(Property p : properties) {
	    	TerrainType type = p.getPropertyType();
	    	List<Domino> includedDominos = p.getIncludedDominos();
	    	MyProperty myProperty = new MyProperty();
	    	myProperty.setPropertyType(type);
	    	for(Domino inc : includedDominos) {
	    		myProperty.addIncludedDomino(kingdom, inc);
	    	}
	    	myProperties.add(myProperty);
	    }
	    if(!(myProperties.contains(mine))) {
	    	 Property prop = new Property(kingdom);
	 		
	 		for(Domino d :dominos) {
	 			prop.addIncludedDomino(d);
	 		}
	 		
	 		prop.setPropertyType(mine.getPropertyType());
	    } 
		
	}
	
	public static boolean isBiggerProperty(MyProperty inList, MyProperty check) {
		List<Domino> inListDominos = inList.getIncludedDominos1();
		List<Domino> checkDominos = check.getIncludedDominos1();
		
		if(inList.getPropertyType() != check.getPropertyType()) {
			return false;
		}
		
		if(checkDominos.containsAll(inListDominos)) {
			return true;
		}
		return false;
	}
	
	public static boolean isSmallerProperty(MyProperty inList, MyProperty check) {
		List<Domino> inListDominos = inList.getIncludedDominos1();
		List<Domino> checkDominos = check.getIncludedDominos1();
		
		if(inList.getPropertyType() != check.getPropertyType()) {
			return false;
		}
		
		if(inListDominos.containsAll(checkDominos)) {
			return true;
		}
		return false;
		
	}
	
	public static void getPropertyAttributes(Property property) {
		List<Domino> dominos = property.getIncludedDominos();
		TerrainType type = property.getPropertyType();
		int size = 0;
		int crowns = 0;
		
		for(Domino dom : dominos) {
			if(dom.getLeftTile() == type) {
				size++;
				crowns += dom.getLeftCrown();
			}
			if(dom.getRightTile() == type) {
				size++;
				crowns += dom.getRightCrown();
			}
		}
		
		property.setCrowns(crowns);
		property.setSize(size);
		property.setScore(crowns*size);
		
	}
	
	public static boolean middleKingdomCheck(Player aPlayer) {
		List<KingdomTerritory> territories = aPlayer.getKingdom().getTerritories();
		List<Tile> tilesOccupied = KingdomTiles(aPlayer);
		boolean isMiddle = false;
		boolean inMiddle = false;
		int biggestX = 0;
		int smallestX = 0;
		int biggestY = 0;
		int smallestY = 0;
		for(Tile tile : tilesOccupied) {
			int x = tile.getX();
			int y = tile.getY();
			if(x > biggestX) {
				biggestX = x;
			}
			if(x < smallestX) {
				smallestX = x;
			}
			if(y < smallestY) {
				smallestY = y;
			}
			if(y > biggestY) {
				biggestY = y;
			}
		}
		for(KingdomTerritory ter : territories) {
			if(!(ter instanceof DominoInKingdom)) {
				if(ter.getX() ==0 && ter.getY() == 0) {
					inMiddle = true;
				}
			}
		}
		if(biggestY+smallestY == 0 && biggestX+smallestX ==0 && inMiddle) {
			isMiddle = true;
		}
		
		return isMiddle;
		
	}
	
	public static boolean harmonyCheck(Player aPlayer) {
		List<KingdomTerritory> territories = aPlayer.getKingdom().getTerritories();
		List<Domino> dominoes = aPlayer.getGame().getAllDominos();
		boolean discarded = false;
		for(Domino dom : dominoes) {
			if(dom.getStatus() == DominoStatus.Discarded) {
				discarded = true;
			}
		}
		if(territories.size() == 13) {
			if(discarded == false) {
				return true;
			}
		}
		return false;
	}
	
	public static int getPropertyScore(Player aPlayer) {
		List<Property> properties = aPlayer.getKingdom().getProperties();
		int score = 0;
		for(Property prop : properties) {
			score += prop.getScore();
		}
		return score;
	}
	
	public static void kingdomIncludesDomino(Player aPlayer,Integer int1, Integer int2, Integer int3, String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Kingdom kingdom = game.getPlayer(game.indexOfPlayer(aPlayer)).getKingdom();
		DirectionKind dir = getDirection(string);
		Domino dominoToPlace =getdominoByID(int1);
		DominoInKingdom domInKingdom = new DominoInKingdom(int2, int3, kingdom, dominoToPlace);
		domInKingdom.setDirection(dir);
		dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);
		aPlayer.getKingdom().addTerritory(domInKingdom);
	}

	public static Tile[][] getKingdomAsTiles(Player player, DominoStatus includedStatus, DominoStatus ...otherIncludedStatuses) {
		List<KingdomTerritory> territories = player.getKingdom().getTerritories();
		Tile[][] tiles = new Tile[9][9];

		for (KingdomTerritory territory : territories) {
			int x = territory.getX();
			int y = territory.getY();
			
			if (territory instanceof DominoInKingdom) {
				// territory is instanceof DominoInKingdom
				DominoInKingdom dominoInKingdom = (DominoInKingdom) territory;
				Domino domino = dominoInKingdom.getDomino();
				
				boolean include = domino.getStatus().equals(includedStatus);
				for (DominoStatus otherIncludedStatus : otherIncludedStatuses)
					include = include || domino.getStatus().equals(otherIncludedStatus);
				
				if (include) {
					// set left tile
					tiles[4-y][x+4] = new Tile(domino.getLeftTile(), domino.getLeftCrown(), dominoInKingdom, x, y);

					// set right tile
					int xd = 0, yd = 0;
					switch (dominoInKingdom.getDirection()) {
						case Right:
							xd = +1; 
							break;
						case Up:
							yd = -1;
							break;
						case Left:
							xd = -1; 
							break;
						case Down:
							yd = +1;
							break;
						}
						tiles[(4-y)+yd][(x+4)+xd] = new Tile(domino.getRightTile(), domino.getRightCrown(), dominoInKingdom, x+xd, y+yd);
				}
			} else {
				// territory is instanceof Castle
				tiles[4-y][x+4] = new Tile(territory);
			}
			
		}
		
//		for (int j = 0; j < 9; ++j) {
//			for (int i = 0; i < 9; ++i) {
//				if (tiles[j][i] == null) {
//					System.out.print("00 ");
//				} else {
//					if (tiles[j][i].getKingdomTerritory() instanceof DominoInKingdom) {
//						Domino domino = ((DominoInKingdom)tiles[j][i].getKingdomTerritory()).getDomino();
//						System.out.print(String.format("%02d", domino.getId()) + " ");
//					} else {
//						System.out.print("CC ");
//					}
//				}
//			}
//			System.out.println();
//		}
//		System.out.println();

		return tiles;
	}
	
	public static List<Tile> sameProperty(List<Tile> tiles, Tile tile) {
		List<Tile> sameProp = new ArrayList<Tile>();
		List<Tile> others = new ArrayList<Tile>();
  		
		for(Tile nextTile : tiles) {
			
			boolean right = nextTile.getX() == tile.getX()+1 && nextTile.getY() == tile.getY();
			boolean left = nextTile.getX() == tile.getX()-1 && nextTile.getY() == tile.getY();
			boolean up = nextTile.getX() == tile.getX() && nextTile.getY() == tile.getY()+1;
			boolean down = nextTile.getX() == tile.getX() && nextTile.getY() == tile.getY()-1;
			
			if(right || left || up || down) {
				if(nextTile.getTerrainType() == tile.getTerrainType()) {
					if(!(sameProp.contains(nextTile))) {
						sameProp.add(nextTile);
						others.clear();
						others = nextPropertyWithNext(tiles, nextTile);
						for(Tile o : others) {
							if(!(sameProp.contains(o))) {
								sameProp.add(o);
							}
						}
					}
				}
			}
			
		}
		
		return sameProp;
		
	}
	
	public static boolean isPlacementPossible(Kingdom kingdom, int id) {
		Tile[][] tiles = getKingdomAsTiles(kingdom.getPlayer(), DominoStatus.PlacedInKingdom);
		Domino domino = getdominoByID(id);
		DirectionKind[] dirs = DirectionKind.values();
		
		DominoInKingdom dik = null;
		for (int j = 0; j < 9; ++j) {
			for (int i = 0; i < 9; ++i) {
				if (tiles[j][i] == null) {
					dik = new DominoInKingdom(i - 4, 4 - j, kingdom, domino);
					
					for (DirectionKind d : dirs) {
						dik.setDirection(d);
						setPreplacedStatus(domino);
						if (domino.getStatus() == DominoStatus.CorrectlyPreplaced) {
							dik.delete();
							return true;
						}
					}
					
					dik.delete();
				}
			}
		}
		
		return false;
	}
	
	public static String dominoToString(Domino domino) {
		String selectedBy = domino.getDominoSelection() == null ?
				"" :
				" <- " + domino.getDominoSelection().getPlayer().getColor();
		return domino.getId() + ":" + domino.getLeftTile() + "+" + domino.getRightTile() 
		+ " (" + domino.getLeftCrown() + "+" + domino.getRightCrown() + ")" + selectedBy;
	}
	
	public static Map<String, Object> dominoToMap(Domino domino) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		Map<String, Object> d = new HashMap<>();
		
		d.put("id", domino.getId());
		d.put("lefttile", domino.getLeftTile().toString());
		d.put("righttile", domino.getRightTile().toString());
		d.put("leftcrown", domino.getLeftCrown());
		d.put("rightcrown", domino.getRightCrown());
		d.put("color", domino.getDominoSelection() != null ? 
				domino.getDominoSelection().getPlayer().getColor().toString() :
					null);
		
		return d;
	}
	
	public static Player findPlayer(String color) {
		Player player = null;
		for (Player p : KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
			if (color.equalsIgnoreCase(p.getColor().toString())) {
				player = p;
				break;
			}
		}
		
		return player;
	}
}
