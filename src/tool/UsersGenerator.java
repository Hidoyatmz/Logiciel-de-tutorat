package tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import app.config.ConfigManager;
import app.resources.Resource;
import app.utils.Utils;

public class UsersGenerator {
	
	public static ArrayList<String> allNames = new ArrayList<>();
	public static String namesPath = "resources/names.txt";
	public static String resourcesPath = ConfigManager.getInstance().getProperty("RESOURCE_DIR");
	
	public static void registerNames() {
		UsersGenerator.allNames.clear();
		System.out.println("Registering names");
		File file = null;
		try {
			file = Utils.openFile(namesPath);
		} catch(FileNotFoundException e) {
			System.err.println("Dependencie File Missing");
			return;
		}
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			String line = br.readLine();
			while(line != null) {
				UsersGenerator.allNames.add(line);
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String generateNew1Student() {
		StringBuilder res = new StringBuilder();
		res.append("{");
		int[] grades = new int[Resource.values().length];
		int namesCount = allNames.size()-1;
		LocalDate birthday = LocalDate.now();
		birthday = birthday.minusDays(Utils.randInt(0, 30));
		birthday = birthday.minusMonths(Utils.randInt(0, 12));
		birthday = birthday.minusYears(Utils.randInt(18, 23));
		String name = allNames.get(Utils.randInt(0, namesCount));
		String forename = allNames.get(Utils.randInt(0, namesCount));
		for(int i = 0; i < grades.length; i++) {
			grades[i] = Utils.randInt(0, 21);
		}
		res.append("\n\t\"name\":\"" + name + "\",");
		res.append("\n\t\"forename\":\"" + forename + "\",");
		res.append("\n\t\"birthday\":\"" + birthday.toString() + "\",");
		res.append("\n\t\"absences\":\"" + Utils.randInt(0, 20) + "\",");
		res.append("\n\t\"marks\": {\n"
				+ "\t\t\"dev:\" : " + grades[0]+",\n"
				+ "\t\t\"maths:\" : " + grades[1]+",\n"
				+ "\t\t\"sql:\" : " + grades[2]+"\n"
				+"\t}");
		res.append("\n},");
		return res.toString();
	}
	
	public static String generateNewOtherStudent() {
		StringBuilder res = new StringBuilder();
		res.append("{");
		int[] grades = new int[Resource.values().length];
		int namesCount = allNames.size()-1;
		LocalDate birthday = LocalDate.now();
		birthday = birthday.minusDays(Utils.randInt(0, 30));
		birthday = birthday.minusMonths(Utils.randInt(0, 12));
		birthday = birthday.minusYears(Utils.randInt(18, 23));
		String name = allNames.get(Utils.randInt(0, namesCount));
		String forename = allNames.get(Utils.randInt(0, namesCount));
		for(int i = 0; i < grades.length; i++) {
			grades[i] = Utils.randInt(0, 21);
		}
		res.append("\n\t\"name\":\"" + name + "\",");
		res.append("\n\t\"forename\":\"" + forename + "\",");
		res.append("\n\t\"birthday\":\"" + birthday.toString() + "\",");
		res.append("\n\t\"absences\":\"" + Utils.randInt(0, 20) + "\",");
		res.append("\n\t\"marks\": {\n"
				+ "\t\t\"dev:\" : " + grades[0]+",\n"
				+ "\t\t\"maths:\" : " + grades[1]+",\n"
				+ "\t\t\"sql:\" : " + grades[2]+"\n"
				+"\t},");
		res.append("\n\t\"year\": " + Utils.randInt(2,4));
		res.append("\n},");
		return res.toString();
	}
	
	public static String generateNewProfessor() {
		StringBuilder res = new StringBuilder();
		res.append("{");
		int[] grades = new int[Resource.values().length];
		Resource[] resources = Resource.values();
		int namesCount = allNames.size()-1;
		LocalDate birthday = LocalDate.now();
		birthday = birthday.minusDays(Utils.randInt(0, 30));
		birthday = birthday.minusMonths(Utils.randInt(0, 12));
		birthday = birthday.minusYears(Utils.randInt(35, 50));
		String name = allNames.get(Utils.randInt(0, namesCount));
		String forename = allNames.get(Utils.randInt(0, namesCount));
		for(int i = 0; i < grades.length; i++) {
			grades[i] = Utils.randInt(0, 21);
		}
		res.append("\n\t\"name\":\"" + name + "\",");
		res.append("\n\t\"forename\":\"" + forename + "\",");
		res.append("\n\t\"birthday\":\"" + birthday.toString() + "\",");
		res.append("\n\t\"resource\":\"" + resources[Utils.randInt(0, resources.length)].toString() + "\"");
		res.append("\n},");
		return res.toString();
	}
	
	public static void main(String[] args) {
		registerNames();
		Scanner scanner = new Scanner(System.in);
		String fileToSave = resourcesPath;
		int choice = -1;
		while(choice < 1 || choice > 3) {			
			choice = Utils.askInt("1. G�n�rer des �tudiants de premi�re ann�e.\n2. G�n�rer des �tudiants de 2 & 3 �me ann�e\n3. G�n�rer des professeurs.\nEntrez votre choix:", scanner);
		}
		int usersToGenerate = Utils.askInt("Enter a number of users to generate:", scanner);
		ArrayList<String> usersGeneretad = new ArrayList<>();
		switch(choice) {
			case 1:
				for(int i = 0; i < usersToGenerate; i++) {
					usersGeneretad.add(generateNew1Student());
				}
				fileToSave += "firstYearStudents.json";
				break;
			case 2:
				for(int i = 0; i < usersToGenerate; i++) {
					usersGeneretad.add(generateNewOtherStudent());
				}
				fileToSave += "otherStudents.json";
				break;
			case 3:
				for(int i = 0; i < usersToGenerate; i++) {
					usersGeneretad.add(generateNewProfessor());
				}
				fileToSave += "Professors.json";
				break;
		}
		File fileSave = new File(fileToSave);
		try(FileWriter fw = new FileWriter(fileSave)){
			for(String ostudent: usersGeneretad) {
				fw.write(ostudent+"\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(usersToGenerate + " users generated in " + fileToSave);
	}
}
