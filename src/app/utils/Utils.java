package app.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.util.InputMismatchException;
import java.util.Scanner;

import app.config.ConfigManager;
import app.exceptions.PropertyNotExists;

/**
 * Collection of static methods designed to be useful in different cases
 *
 */
public abstract class Utils {
	public static int getAgeFromLocalDate(LocalDate date) {
		return Period.between(date, LocalDate.now()).getYears();
	}
	
	public static double formatMark(double value) {
		return Math.round(value*100)/100D;
	}
	
	public static int randInt(int min, int max) {
		return (int) (Math.random() * (max - min)) + min;
	}
	
	public static File openFile(String path) throws FileNotFoundException{
		File file = new File(path);
		if(!file.exists()) {
			throw new FileNotFoundException();
		}
		return file;
	}
	
	public static String fileToString(File file) {
		String res = "";
		try {
			res = Files.readString(Paths.get(file.getPath()));
		} catch (IOException e) {
			System.err.println(e);
			return "";
		}
		return res;
	}
	
	public static int askInt(String message, Scanner scanner) {
		System.out.println(message);
		while(true) {
			try {
				if(scanner.hasNext()) {						
					return Integer.parseInt(scanner.next());
				}
			} catch(NumberFormatException ne) {
			       System.out.println(message);
			}
		}
	}
	
	public static LocalDate getLimitApplyDateFromConfig() {
		String[] configDate;
		try {
			configDate = ConfigManager.getInstance().getProperty("LIMIT_APPLY_DATE").split("/");
		} catch (PropertyNotExists e) {
			System.err.println(e);
			return LocalDate.of(1970, 1, 1);
		}
		return LocalDate.of(Integer.parseInt(configDate[2]), Integer.parseInt(configDate[1]), Integer.parseInt(configDate[0]));
	}
	
}
