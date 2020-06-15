/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4823.43e750332 modeling language!*/

package kingdomino.model;

// line 79 "../../Kingdomino.ump"
public class Castle extends KingdomTerritory
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Castle Associations
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Castle(int aX, int aY, Kingdom aKingdom, Player aPlayer)
  {
    super(aX, aY, aKingdom);
    if (!setPlayer(aPlayer))
    {
      throw new RuntimeException("Unable to create Castle due to aPlayer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setPlayer(Player aNewPlayer)
  {
    boolean wasSet = false;
    if (aNewPlayer != null)
    {
      player = aNewPlayer;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    player = null;
    super.delete();
  }

}