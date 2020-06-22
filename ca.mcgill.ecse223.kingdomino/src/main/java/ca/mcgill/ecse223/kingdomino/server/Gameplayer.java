package ca.mcgill.ecse223.kingdomino.server;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.CommandLineInterface;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;

public class Gameplayer implements Runnable {
	
	private BufferedReader cliReader;
	private PrintWriter outPrinter;
	private Gameplay gameplay;

	public Gameplayer(Gameplay gameplay, InputStream in, OutputStream out) {
		this.cliReader = new BufferedReader(new InputStreamReader(in));
		this.outPrinter = new PrintWriter(out);
		this.gameplay = gameplay;
	}
	
	public void run() {
		try {
			Game game = KingdominoApplication.getKingdomino().getCurrentGame();
			
			JsonToMethodMapper mapper = new JsonToMethodMapper("exit");
			Method[] methods = CommandLineInterface.class.getMethods();
			for (Method m : methods) {
				mapper.put(m.getName(), m);
			}
			
			while (gameplay.getGamestatus() != Gameplay.Gamestatus.EndGame) {
				gameplay.draftReady();
				
				outPrinter.println("Current status: " + gameplay.getGamestatusFullName());
				
				if (gameplay.getGamestatusInitializing() == Gameplay.GamestatusInitializing.SelectingFirstDomino
						|| gameplay.getGamestatusMidGame() == Gameplay.GamestatusMidGame.SelectingNextDomino) {
					outPrinter.println(CommandLineInterface.nextDraft());
				}
				
				if (gameplay.getGamestatusMidGame() == Gameplay.GamestatusMidGame.PlacingOrDiscardingSelectedDomino) {
					outPrinter.println(CommandLineInterface.kingdom(game.getCurrentPlayer().getColor().toString()));
				}
				
				outPrinter.print(game.getCurrentPlayer().getColor() + " > ");
				String line;
				
				outPrinter.flush();
				while ((line = cliReader.readLine()) == null);
				//outPrinter.println(line);
				String[] splitted = line.split(" ");
				
				if (splitted[0].equalsIgnoreCase("exit")) {
					outPrinter.println("Exiting game");
					break;
				}
				
				Map<String, Object> json = new HashMap<>();
				json.put("cmd", splitted[0]);
				
				List<Object> methodArgs = new ArrayList<>();
				for (int i = 1; i < splitted.length; ++i) {
					methodArgs.add(splitted[i]);
				}
				json.put("args", methodArgs);
				
				Map<String, Object> res = mapper.process(json);
				
				outPrinter.println((String)res.get("data"));
				
				outPrinter.flush();
			}
			outPrinter.println(KingdominoApplication.getKingdomino().getCurrentGame().getPlayers());
			outPrinter.println("Game ended");
		} catch (IOException e) {
			outPrinter.println(e.getMessage());
		}
		
		outPrinter.flush();
	}
	
	public void close() throws IOException {
		cliReader.close();
		outPrinter.close();
	}
}
