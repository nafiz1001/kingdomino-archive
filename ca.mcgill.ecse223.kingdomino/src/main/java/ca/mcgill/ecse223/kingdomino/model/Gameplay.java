/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.io.Serializable;

/**
 * 
 * NOTE #1: You need to create a current game which includes all players, all dominos and dominos inside the pile 
 * NOTE #2: Every domino inside a pile must have DominoStatus set to InPile
 * Note #3: I'll figure this out later
 */
// line 5 "../../../../../GameplayPersistance.ump"
// line 9 "../../../../../Gameplay.ump"
public class Gameplay implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Gameplay Attributes
  private int id;
  private int x;
  private int y;
  private String dir;
  private int count;

  //Gameplay State Machines
  public enum Gamestatus { Initializing, MidGame, EndGame }
  public enum GamestatusInitializing { Null, CreatingFirstDraft, SelectingFirstDomino }
  public enum GamestatusMidGame { Null, CreatingNextDraft, PlacingOrDiscardingSelectedDomino, SelectingNextDomino }
  public enum GamestatusEndGame { Null, Ranking }
  private Gamestatus gamestatus;
  private GamestatusInitializing gamestatusInitializing;
  private GamestatusMidGame gamestatusMidGame;
  private GamestatusEndGame gamestatusEndGame;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Gameplay()
  {
    id = 0;
    x = 0;
    y = 0;
    dir = null;
    count = 0;
    setGamestatusInitializing(GamestatusInitializing.Null);
    setGamestatusMidGame(GamestatusMidGame.Null);
    setGamestatusEndGame(GamestatusEndGame.Null);
    setGamestatus(Gamestatus.Initializing);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setX(int aX)
  {
    boolean wasSet = false;
    x = aX;
    wasSet = true;
    return wasSet;
  }

  public boolean setY(int aY)
  {
    boolean wasSet = false;
    y = aY;
    wasSet = true;
    return wasSet;
  }

  public boolean setDir(String aDir)
  {
    boolean wasSet = false;
    dir = aDir;
    wasSet = true;
    return wasSet;
  }

  public boolean setCount(int aCount)
  {
    boolean wasSet = false;
    count = aCount;
    wasSet = true;
    return wasSet;
  }

  /**
   * Variables
   */
  public int getId()
  {
    return id;
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  public String getDir()
  {
    return dir;
  }

  public int getCount()
  {
    return count;
  }

  public String getGamestatusFullName()
  {
    String answer = gamestatus.toString();
    if (gamestatusInitializing != GamestatusInitializing.Null) { answer += "." + gamestatusInitializing.toString(); }
    if (gamestatusMidGame != GamestatusMidGame.Null) { answer += "." + gamestatusMidGame.toString(); }
    if (gamestatusEndGame != GamestatusEndGame.Null) { answer += "." + gamestatusEndGame.toString(); }
    return answer;
  }

  public Gamestatus getGamestatus()
  {
    return gamestatus;
  }

  public GamestatusInitializing getGamestatusInitializing()
  {
    return gamestatusInitializing;
  }

  public GamestatusMidGame getGamestatusMidGame()
  {
    return gamestatusMidGame;
  }

  public GamestatusEndGame getGamestatusEndGame()
  {
    return gamestatusEndGame;
  }

  public boolean draftReady()
  {
    boolean wasEventProcessed = false;
    
    GamestatusInitializing aGamestatusInitializing = gamestatusInitializing;
    GamestatusMidGame aGamestatusMidGame = gamestatusMidGame;
    switch (aGamestatusInitializing)
    {
      case CreatingFirstDraft:
        exitGamestatusInitializing();
        // line 15 "../../../../../Gameplay.ump"
        revealNextDraft(); generateInitialPlayerOrder();
        setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    switch (aGamestatusMidGame)
    {
      case CreatingNextDraft:
        exitGamestatusMidGame();
        // line 31 "../../../../../Gameplay.ump"
        revealNextDraft(); generateMidGamePlayerOrder();
        setGamestatusMidGame(GamestatusMidGame.PlacingOrDiscardingSelectedDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean selectDomino()
  {
    boolean wasEventProcessed = false;
    
    GamestatusInitializing aGamestatusInitializing = gamestatusInitializing;
    GamestatusMidGame aGamestatusMidGame = gamestatusMidGame;
    switch (aGamestatusInitializing)
    {
      case SelectingFirstDomino:
        if (isSelectionValid()&&!(isCurrentPlayerTheLastInTurn()))
        {
          exitGamestatusInitializing();
        // line 20 "../../../../../Gameplay.ump"
          concludeSelection();
          setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino);
          wasEventProcessed = true;
          break;
        }
        if (isSelectionValid()&&isCurrentPlayerTheLastInTurn())
        {
          exitGamestatus();
        // line 23 "../../../../../Gameplay.ump"
          concludeSelection();
          setGamestatusMidGame(GamestatusMidGame.CreatingNextDraft);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aGamestatusMidGame)
    {
      case SelectingNextDomino:
        if (isSelectionValid()&&!(isCurrentPlayerTheLastInTurn()))
        {
          exitGamestatusMidGame();
        // line 54 "../../../../../Gameplay.ump"
          concludeSelection();
          setGamestatusMidGame(GamestatusMidGame.PlacingOrDiscardingSelectedDomino);
          wasEventProcessed = true;
          break;
        }
        if (isSelectionValid()&&isCurrentPlayerTheLastInTurn())
        {
          exitGamestatusMidGame();
        // line 57 "../../../../../Gameplay.ump"
          concludeSelection();
          setGamestatusMidGame(GamestatusMidGame.CreatingNextDraft);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean placeDomino()
  {
    boolean wasEventProcessed = false;
    
    GamestatusMidGame aGamestatusMidGame = gamestatusMidGame;
    switch (aGamestatusMidGame)
    {
      case PlacingOrDiscardingSelectedDomino:
        if (isPlacementValid()&&!(isCurrentTurnTheLastInGame()))
        {
          exitGamestatusMidGame();
        // line 37 "../../../../../Gameplay.ump"
          concludePlacement();
          setGamestatusMidGame(GamestatusMidGame.SelectingNextDomino);
          wasEventProcessed = true;
          break;
        }
        if (isPlacementValid()&&isCurrentTurnTheLastInGame()&&!(isCurrentPlayerTheLastInTurn()))
        {
          exitGamestatusMidGame();
        // line 39 "../../../../../Gameplay.ump"
          concludePlacement();
          setGamestatusMidGame(GamestatusMidGame.PlacingOrDiscardingSelectedDomino);
          wasEventProcessed = true;
          break;
        }
        if (isPlacementValid()&&isCurrentTurnTheLastInGame()&&isCurrentPlayerTheLastInTurn())
        {
          exitGamestatus();
        // line 41 "../../../../../Gameplay.ump"
          concludePlacement();
          setGamestatusEndGame(GamestatusEndGame.Ranking);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean discardDomino()
  {
    boolean wasEventProcessed = false;
    
    GamestatusMidGame aGamestatusMidGame = gamestatusMidGame;
    switch (aGamestatusMidGame)
    {
      case PlacingOrDiscardingSelectedDomino:
        if (isDiscardValid()&&!(isCurrentTurnTheLastInGame()))
        {
          exitGamestatusMidGame();
        // line 44 "../../../../../Gameplay.ump"
          concludeDiscarding();
          setGamestatusMidGame(GamestatusMidGame.SelectingNextDomino);
          wasEventProcessed = true;
          break;
        }
        if (isDiscardValid()&&isCurrentTurnTheLastInGame()&&!(isCurrentPlayerTheLastInTurn()))
        {
          exitGamestatusMidGame();
        // line 46 "../../../../../Gameplay.ump"
          concludeDiscarding();
          setGamestatusMidGame(GamestatusMidGame.PlacingOrDiscardingSelectedDomino);
          wasEventProcessed = true;
          break;
        }
        if (isDiscardValid()&&isCurrentTurnTheLastInGame()&&isCurrentPlayerTheLastInTurn())
        {
          exitGamestatus();
        // line 48 "../../../../../Gameplay.ump"
          concludeDiscarding();
          setGamestatusEndGame(GamestatusEndGame.Ranking);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void exitGamestatus()
  {
    switch(gamestatus)
    {
      case Initializing:
        exitGamestatusInitializing();
        break;
      case MidGame:
        exitGamestatusMidGame();
        break;
      case EndGame:
        exitGamestatusEndGame();
        break;
    }
  }

  private void setGamestatus(Gamestatus aGamestatus)
  {
    gamestatus = aGamestatus;

    // entry actions and do activities
    switch(gamestatus)
    {
      case Initializing:
        if (gamestatusInitializing == GamestatusInitializing.Null) { setGamestatusInitializing(GamestatusInitializing.CreatingFirstDraft); }
        break;
      case MidGame:
        if (gamestatusMidGame == GamestatusMidGame.Null) { setGamestatusMidGame(GamestatusMidGame.CreatingNextDraft); }
        break;
      case EndGame:
        if (gamestatusEndGame == GamestatusEndGame.Null) { setGamestatusEndGame(GamestatusEndGame.Ranking); }
        break;
    }
  }

  private void exitGamestatusInitializing()
  {
    switch(gamestatusInitializing)
    {
      case CreatingFirstDraft:
        setGamestatusInitializing(GamestatusInitializing.Null);
        break;
      case SelectingFirstDomino:
        setGamestatusInitializing(GamestatusInitializing.Null);
        break;
    }
  }

  private void setGamestatusInitializing(GamestatusInitializing aGamestatusInitializing)
  {
    gamestatusInitializing = aGamestatusInitializing;
    if (gamestatus != Gamestatus.Initializing && aGamestatusInitializing != GamestatusInitializing.Null) { setGamestatus(Gamestatus.Initializing); }

    // entry actions and do activities
    switch(gamestatusInitializing)
    {
      case CreatingFirstDraft:
        // line 14 "../../../../../Gameplay.ump"
        shuffleDominoPile(); createNextDraft(); orderNextDraft();
        break;
      case SelectingFirstDomino:
        // line 18 "../../../../../Gameplay.ump"
        switchToNextPlayer();
        break;
    }
  }

  private void exitGamestatusMidGame()
  {
    switch(gamestatusMidGame)
    {
      case CreatingNextDraft:
        setGamestatusMidGame(GamestatusMidGame.Null);
        break;
      case PlacingOrDiscardingSelectedDomino:
        setGamestatusMidGame(GamestatusMidGame.Null);
        break;
      case SelectingNextDomino:
        setGamestatusMidGame(GamestatusMidGame.Null);
        break;
    }
  }

  private void setGamestatusMidGame(GamestatusMidGame aGamestatusMidGame)
  {
    gamestatusMidGame = aGamestatusMidGame;
    if (gamestatus != Gamestatus.MidGame && aGamestatusMidGame != GamestatusMidGame.Null) { setGamestatus(Gamestatus.MidGame); }

    // entry actions and do activities
    switch(gamestatusMidGame)
    {
      case CreatingNextDraft:
        // line 30 "../../../../../Gameplay.ump"
        createNextDraft(); orderNextDraft();
        break;
      case PlacingOrDiscardingSelectedDomino:
        // line 34 "../../../../../Gameplay.ump"
        switchToNextPlayer();
        break;
      case SelectingNextDomino:
        // line 51 "../../../../../Gameplay.ump"
        clearStats();
        break;
    }
  }

  private void exitGamestatusEndGame()
  {
    switch(gamestatusEndGame)
    {
      case Ranking:
        setGamestatusEndGame(GamestatusEndGame.Null);
        break;
    }
  }

  private void setGamestatusEndGame(GamestatusEndGame aGamestatusEndGame)
  {
    gamestatusEndGame = aGamestatusEndGame;
    if (gamestatus != Gamestatus.EndGame && aGamestatusEndGame != GamestatusEndGame.Null) { setGamestatus(Gamestatus.EndGame); }

    // entry actions and do activities
    switch(gamestatusEndGame)
    {
      case Ranking:
        // line 63 "../../../../../Gameplay.ump"
        rankPlayers();
        break;
    }
  }

  public void delete()
  {}


  /**
   * Setter for test setup
   */
  // line 73 "../../../../../Gameplay.ump"
   public void setGamestatus(String status){
    switch (status) {
       	case "CreatingFirstDraft":
       	    gamestatus = Gamestatus.Initializing;
       	    gamestatusInitializing = GamestatusInitializing.CreatingFirstDraft;
       	    break;
   	    case "SelectingFirstDomino":
       	    gamestatus = Gamestatus.Initializing;
       	    gamestatusInitializing = GamestatusInitializing.SelectingFirstDomino;
       	    break;
       	case "CreatingNextDraft":
       		gamestatus = Gamestatus.MidGame;
       		gamestatusMidGame = GamestatusMidGame.CreatingNextDraft;
       		break;
       	case "SelectingNextDomino":
       		gamestatus = Gamestatus.MidGame;
       	    gamestatusMidGame = GamestatusMidGame.SelectingNextDomino;
       		break;
       		
       	case "PlacingOrDiscardingSelectedDomino":
       		gamestatus = Gamestatus.MidGame;
       	    gamestatusMidGame = GamestatusMidGame.PlacingOrDiscardingSelectedDomino;
       		break;
       		
    	case "EndGame":
       		gamestatus = Gamestatus.EndGame;
       	    gamestatusEndGame = GamestatusEndGame.Null;
       		break;
       		
    	case "Ranking":
       		gamestatus = Gamestatus.EndGame;
       	    gamestatusEndGame = GamestatusEndGame.Ranking;
       		break;
       	default:
       	    throw new RuntimeException("Invalid gamestatus string was provided: " + status);
       	}
  }


  /**
   * Guards
   */
  // line 115 "../../../../../Gameplay.ump"
   public boolean isCurrentPlayerTheLastInTurn(){
    Game game = ca.mcgill.ecse223.kingdomino.KingdominoApplication.getKingdomino().getCurrentGame();
        return game.getCurrentPlayer() == game.getPlayer(game.getPlayers().size()-1);
  }


  /**
   * 
   * Note: Make sure the topDominoInPile is null when the pile is empty
   */
  // line 123 "../../../../../Gameplay.ump"
   public boolean isCurrentTurnTheLastInGame(){
    return count >= 48;
  }


  /**
   * 
   * Note: Make sure the domino selected is in the next draft, not in the current draft
   * Note: Make sure you set the value of id of class Gameplay
   */
  // line 131 "../../../../../Gameplay.ump"
   public boolean isSelectionValid(){
    return helper.HelperFunctions.getdominoByID(id).getDominoSelection() == null 
    		&& helper.HelperFunctions.getdominoByID(id).getStatus() == Domino.DominoStatus.InNextDraft;
  }


  /**
   * 
   * Note: Make sure you set the value of id of class Gameplay
   */
  // line 139 "../../../../../Gameplay.ump"
   public boolean isPlacementValid(){
    Player player = ca.mcgill.ecse223.kingdomino.KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer();
    	id = player.getDominoSelection().getDomino().getId();

        Domino domino = player.getDominoSelection().getDomino();
                
        DominoInKingdom dik = helper.HelperFunctions.getPreplacedDominoInKingdom(player);
        if (dik == null)
            dik = new DominoInKingdom(x, y, player.getKingdom(), domino);
        
        dik.setX(x);
        dik.setY(y);
        dik.setDirection(helper.HelperFunctions.getDirection(dir));
        
        domino.setStatus(Domino.DominoStatus.ErroneouslyPreplaced);

        helper.HelperFunctions.setPreplacedStatus(domino);
        
        dik.delete();
        
        return domino.getStatus() == Domino.DominoStatus.CorrectlyPreplaced;
  }


  /**
   * 
   * Note: Make sure you set the value of id of class Gameplay
   */
  // line 165 "../../../../../Gameplay.ump"
   public boolean isDiscardValid(){
    Player player = ca.mcgill.ecse223.kingdomino.KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer();
    	id = player.getDominoSelection().getDomino().getId();
        return !helper.HelperFunctions.isPlacementPossible(player.getKingdom(), id);
  }


  /**
   * Actions
   */
  // line 175 "../../../../../Gameplay.ump"
   public void shuffleDominoPile(){
    ca.mcgill.ecse223.kingdomino.controller.KingdominoController.shuffleDominos();
  }

  // line 179 "../../../../../Gameplay.ump"
   public void generateInitialPlayerOrder(){
    Game game = ca.mcgill.ecse223.kingdomino.KingdominoApplication.getKingdomino().getCurrentGame();
        java.util.List<Player> players = new java.util.ArrayList<>(game.getPlayers());
        java.util.Collections.shuffle(players);
        
        for (int i = 0; i < players.size(); ++i) {
			game.addOrMovePlayerAt(players.get(i), i);
		}
		
		game.setCurrentPlayer(game.getPlayer(0));
		game.setNextPlayer(game.getPlayer(0));
  }

  // line 192 "../../../../../Gameplay.ump"
   public void createNextDraft(){
    Game game = ca.mcgill.ecse223.kingdomino.KingdominoApplication.getKingdomino().getCurrentGame();
        game.setCurrentDraft(game.getNextDraft());
        game.setNextDraft(new Draft(Draft.DraftStatus.FaceDown, game));
        ca.mcgill.ecse223.kingdomino.controller.KingdominoController.setNextDraft();
  }

  // line 199 "../../../../../Gameplay.ump"
   public void orderNextDraft(){
    ca.mcgill.ecse223.kingdomino.controller.KingdominoController.initiateOrderingOfNextDraft();
  }

  // line 203 "../../../../../Gameplay.ump"
   public void revealNextDraft(){
    ca.mcgill.ecse223.kingdomino.controller.KingdominoController.revealNextDraft();
  }

  // line 207 "../../../../../Gameplay.ump"
   public void switchToNextPlayer(){
    ++count;
    	if (!isCurrentPlayerTheLastInTurn()) {
	    	clearStats();
	        Game game = ca.mcgill.ecse223.kingdomino.KingdominoApplication.getKingdomino().getCurrentGame();
	        game.setCurrentPlayer(game.getNextPlayer());
	        
	        for (int i = 0; i < game.getPlayers().size()-1; ++i) {
	        	if (game.getCurrentPlayer() == game.getPlayer(i)) {
        			game.setNextPlayer(game.getPlayer(i+1));
        			break;
	        	}
	        }
        }
  }

  // line 223 "../../../../../Gameplay.ump"
   public void clearStats(){
    id = 0;
        x = 0;
        y = 0;
        dir = null;
  }

  // line 230 "../../../../../Gameplay.ump"
   public void concludeSelection(){
    Game game = ca.mcgill.ecse223.kingdomino.KingdominoApplication.getKingdomino().getCurrentGame();
    	new DominoSelection(game.getCurrentPlayer(), helper.HelperFunctions.getdominoByID(id), game.getNextDraft());
  }

  // line 235 "../../../../../Gameplay.ump"
   public void generateMidGamePlayerOrder(){
    // TODO: order player based on id of selected domino
        Game game = ca.mcgill.ecse223.kingdomino.KingdominoApplication.getKingdomino().getCurrentGame();
        
        java.util.List<Player> players = new java.util.ArrayList<>(game.getPlayers());
        java.util.Comparator<Player> bySelectionId = 
			(Player a, Player b)->a.getDominoSelection().getDomino().getId()-b.getDominoSelection().getDomino().getId();
		java.util.Collections.sort(players, bySelectionId);
		
		for (int i = 0; i < players.size(); ++i) {
			game.addOrMovePlayerAt(players.get(i), i);
		}
		
		game.setCurrentPlayer(game.getPlayer(0));
		game.setNextPlayer(game.getPlayer(0));
  }

  // line 252 "../../../../../Gameplay.ump"
   public void concludePlacement(){
    Player player = ca.mcgill.ecse223.kingdomino.KingdominoApplication.getKingdomino().getCurrentGame().getCurrentPlayer();
    	ca.mcgill.ecse223.kingdomino.controller.KingdominoController.addDominoToKingdom(
    		player.getColor().toString(),
    		id,
    		x,
    		y,
    		dir
		);
		ca.mcgill.ecse223.kingdomino.controller.KingdominoController.calculatePlayerScore();
		player.getDominoSelection().delete();
  }

  // line 265 "../../../../../Gameplay.ump"
   public void concludeDiscarding(){
    Game game = ca.mcgill.ecse223.kingdomino.KingdominoApplication.getKingdomino().getCurrentGame();
        Player player = game.getCurrentPlayer();
        Domino domino = player.getDominoSelection().getDomino();
        
        domino.setStatus(Domino.DominoStatus.Discarded);
        player.getDominoSelection().delete();
  }

  // line 275 "../../../../../Gameplay.ump"
   public void rankPlayers(){
    ca.mcgill.ecse223.kingdomino.controller.KingdominoController.calculateRanking();
    	
    	Game game = ca.mcgill.ecse223.kingdomino.KingdominoApplication.getKingdomino().getCurrentGame();        
        java.util.List<Player> players = new java.util.ArrayList<>(game.getPlayers());
        java.util.Comparator<Player> byRanking = 
			(Player a, Player b)->a.getCurrentRanking()-b.getCurrentRanking();
    	java.util.Collections.sort(players, byRanking);
    	
    	boolean sameScore = false;
    	int score = players.get(0).getPropertyScore();
    	
    	for(int index = 1; index < players.size(); index ++)
    	{
    		if(score == players.get(index).getPropertyScore())
    		{
    			sameScore = true;
    			break;
    		}
    		else if (score < players.get(index).getPropertyScore())
    		{
    				score = players.get(index).getPropertyScore();
    		}
    	}
    	 
    	if(sameScore)
    	{
    		ca.mcgill.ecse223.kingdomino.controller.KingdominoController.resolveTiebreak();
    	}
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "x" + ":" + getX()+ "," +
            "y" + ":" + getY()+ "," +
            "dir" + ":" + getDir()+ "," +
            "count" + ":" + getCount()+ "]";
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 9 "../../../../../GameplayPersistance.ump"
  private static final long serialVersionUID = 17L ;

  
}