package kingdomino.features;

import kingdomino.KingdominoApplication;
import kingdomino.model.Kingdomino;
import io.cucumber.java.After;

public class CucumberCleanUp {

	/**
	 * Tear Down
	 * 
	 */
	@After
	public void tearDown() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		if (kingdomino != null) {
			kingdomino.delete();
		}
	}
}
