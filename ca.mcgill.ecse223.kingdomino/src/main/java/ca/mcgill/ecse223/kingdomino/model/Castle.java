/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.io.Serializable;

// line 58 "../../../../../KingdominoPersiatance.ump"
// line 81 "../../../../../Kingdomino.ump"
public class Castle extends KingdomTerritory implements Serializable
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
      throw new RuntimeException("Unable to create Castle due to aPlayer");
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
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 61 "../../../../../KingdominoPersiatance.ump"
  private static final long serialVersionUID = 10L ;

  
}