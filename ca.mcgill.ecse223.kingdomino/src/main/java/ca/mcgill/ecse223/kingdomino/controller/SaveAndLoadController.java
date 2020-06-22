package ca.mcgill.ecse223.kingdomino.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;

public class SaveAndLoadController {

	/**
	 * @author Delia Bretan
	 */
	/**
	 * @param nameOfFileK String. the name of the save location for kingdomino
	 * @param nameOfFileG String. the name of the save location for gameplay
	 */
	private static final long serialVersionUID = 1L;
	private static String nameOfFileK = "src/main/java/ca/mcgill/ecse223/kingdomino/controller/Saves/outputKingdomino.txt";
	private static String nameOfFileG = "src/main/java/ca/mcgill/ecse223/kingdomino/controller/Saves/outputGamplay.txt";
	
	/**
	 * @author Delia Bretan
	 * @Method saves the gameplay and the kingdomino
	 * @param kingdomino Kingdomino. the kingdomino object to be saved
	 * @param gameplay Gameplay. the gameplay object to be saved
	 */
	public static void save(Kingdomino kingdomino, Gameplay gameplay)
	{
		saveGameKingdomino(kingdomino);
		saveGameGameplay(gameplay);
	}
	
	
	/**
	 * @author Delia Bretan
	 * @method creates or overrides file, serialized kingdomino and saves it to file
	 * @param kingdomino Kingdomino. the current kingdomino to be saved
	 */
	public static void saveGameKingdomino(Kingdomino kingdomino) {
		try {
			File filePotatial = new File(nameOfFileK);
			if (filePotatial.exists()) {
				filePotatial.delete();
			}
			createFile(nameOfFileK);

			FileOutputStream file = new FileOutputStream(nameOfFileK);
			ObjectOutputStream out = new ObjectOutputStream(file);

			out.writeObject(kingdomino);

			out.close();
			file.close();
		} catch (Exception e) {
			System.out.println("error with saveGameKingdomino");
		}
	}

	/**
	 * @author Delia Bretan
	 * @method creates or overrides file, serialized gameplay and saves it to file
	 * @param gameplay Gameplay. the gameplay object to be saved
	 */
	public static void saveGameGameplay(Gameplay gameplay) {
		try {
			File filePotatial = new File(nameOfFileG);
			if (filePotatial.exists()) {
				filePotatial.delete();
			}
			createFile(nameOfFileG);
			FileOutputStream file = new FileOutputStream(nameOfFileG);
			ObjectOutputStream out = new ObjectOutputStream(file);

			
			out.writeObject(gameplay);

			out.close();
			file.close();
		} catch (Exception e) {
			System.out.println("error with saveGameGameplay");
		}
	}

	/**
	 * @author Delia Bretan 
	 * @Method deserializes the kingdomino in the specified file
	 * @return kindomino Object. the deserialized object 
	 */
	public static Object deserializeGameKingdom() {
		Object kindomino = null;
		ObjectInputStream in;
		try {
			File check = new File(nameOfFileK);
			System.err.println(check.exists());
			// Reading the object from a file
			FileInputStream file = new FileInputStream(nameOfFileK);
			in = new ObjectInputStream(file);

			// Method for deserialization of object
			kindomino = in.readObject();
			in.close();
			file.close();
		}

		catch (IOException ex) {

		} catch (Exception e) {
			kindomino = null;
		}
		return kindomino;
	}
	
	/**
	 * @author Delia Bretan 
	 * @Method deserializes the gameplay in the specified file
	 * @return gameplay Object. the deserialized object 
	 */
	public static Object deserializeGameGameplay() {
		Object gameplay = null;
		ObjectInputStream in;
		try {
			File check = new File(nameOfFileG);
			System.err.println(check.exists());
			// Reading the object from a file
			FileInputStream file = new FileInputStream(nameOfFileG);
			in = new ObjectInputStream(file);

			// Method for deserialization of object
			gameplay = in.readObject();
			in.close();
			file.close();
		}

		catch (IOException ex) {

		} catch (Exception e) {
			gameplay = null;
		}
		return gameplay;
	}

	/**
	 * @author Delia Bretan
	 * @return Kingdomino saved object or new kingdomino object
	 */
	public static Kingdomino loadKingdomino() {
		Kingdomino kingdomino = (Kingdomino) deserializeGameKingdom();
		if (kingdomino == null) {
			kingdomino = new Kingdomino();
		} else {
			kingdomino.reinitialize();
		}
		return kingdomino;
	}
	
	/**
	 * @author Delia Bretan
	 * @return Gameplay saved object or new kingdomino object
	 */
	public static Gameplay loadGameplay() {
		Gameplay gameplay = (Gameplay) deserializeGameGameplay();
		if (gameplay == null) {
			gameplay = new Gameplay();
		} 
		return gameplay;
	}

	/**
	 * @author Delia Bretan
	 * @param saves String. the location of the file to be created
	 * @throws IOException if something goes wrong
	 */
// creates a new empty file
	public static void createFile(String saves) throws IOException {
		File saving = new File(saves);
		saving.createNewFile();
		FileWriter writer = new FileWriter(saves);
		writer.flush();
		writer.close();
	}

	/**
	 * @author Delia Bretan  
	 * @param fileString String. overrides empty file at location fileString
	 */
//overrides an empty file
	public static void overrideFile(String fileString) {
		File file = new File(fileString);
		if (file.exists()) {
			file.delete();
		}
	}
}
