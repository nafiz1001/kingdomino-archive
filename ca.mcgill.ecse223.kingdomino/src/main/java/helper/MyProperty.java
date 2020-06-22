package helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;

public class MyProperty {
	private TerrainType propertyType;
	private int size;
	private int crowns;
	private Set<DominoInKingdom> includedDominos = new HashSet<>();
	private int score;
	
	public MyProperty() {
		score = 0;
	    size = 0;
	    crowns = 0;
	}

	public TerrainType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(TerrainType propertyType) {
		this.propertyType = propertyType;
	}

	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getCrowns() {
		return crowns;
	}

	public void setCrowns(int crowns) {
		this.crowns = crowns;
	}

	public List<DominoInKingdom> getIncludedDominos() {
		return Collections.unmodifiableList(new ArrayList<>(includedDominos));
	}
	
	public List<Domino> getIncludedDominos1() {
		List<Domino> dominos = new ArrayList<>();
		for (DominoInKingdom d : includedDominos)
			dominos.add(d.getDomino());
		return Collections.unmodifiableList(dominos);
	}

	public void addDominoInKingdom(DominoInKingdom dominoInKingdom) {
		includedDominos.add(dominoInKingdom);
	}

	public boolean addIncludedDomino(Kingdom kingdom, Domino aIncludedDomino) {
	    boolean wasAdded = false;
	    if (includedDominos.contains(aIncludedDomino)) { return false; }
	    
	    DominoInKingdom dik = null;
	    for (KingdomTerritory terr : new ArrayList<>(kingdom.getTerritories())) {
	    	if (terr instanceof DominoInKingdom)
	    		if (((DominoInKingdom)terr).getDomino().getId() == aIncludedDomino.getId())
	    			dik = (DominoInKingdom)terr;
	    }
	    
	    includedDominos.add(dik);
	    wasAdded = true;
	    return wasAdded;
	 }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof MyProperty))
			return false;
		MyProperty other = (MyProperty) obj;
		if (crowns != other.crowns)
			return false;
		if (includedDominos == null) {
			if (other.includedDominos != null)
				return false;
		} else if (!includedDominos.equals(other.includedDominos))
			return false;
		if (propertyType != other.propertyType)
			return false;
		if (size != other.size)
			return false;
		if(includedDominos.contains(other.includedDominos)) {
			return false;
		}
		return true;
	}
}
