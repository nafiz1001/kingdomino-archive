package helper;


import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;

public class Tile {
    private TerrainType terrainType;
    private int crowns;
    private KingdomTerritory kingdomTerritory;
    
    private int x;
    private int y;

    public Tile(TerrainType terrainType, int crowns, KingdomTerritory kingdomTerritory, int x, int y) {
        this.terrainType = terrainType;
        this.crowns = crowns;
        this.kingdomTerritory = kingdomTerritory;
        this.x = x;
        this.y = y;
    }

    public Tile(KingdomTerritory kingdomTerritory) {
        this.kingdomTerritory = kingdomTerritory;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public int getCrowns() {
        return crowns;
    }

    public KingdomTerritory getKingdomTerritory() {
        return kingdomTerritory;
    }

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Tile))
			return false;
		Tile other = (Tile) obj;
		if (crowns != other.crowns)
			return false;
		if (kingdomTerritory == null) {
			if (other.kingdomTerritory != null)
				return false;
		} else if (!kingdomTerritory.equals(other.kingdomTerritory))
			return false;
		if (terrainType != other.terrainType)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
