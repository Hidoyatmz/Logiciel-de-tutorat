package app.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import app.exceptions.PropertyNotExists;

public class ConfigManager {
	private static ConfigManager instance;
	private static String configFilePath = "resources/config/config.properties";
	private static FileInputStream stream;
	private static Properties prop;
	
	private ConfigManager() {
		if(ConfigManager.stream == null) {
			try {
				ConfigManager.stream = new FileInputStream(ConfigManager.configFilePath);
				ConfigManager.prop = new Properties();
				try {
					ConfigManager.prop.load(ConfigManager.stream);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static ArrayList<String> getAllProperties() {
		ArrayList<String> properties = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new FileReader(new File(ConfigManager.configFilePath)))){
			String line = br.readLine();
			while(line != null) {
				properties.add(line.split("=")[0]);
				line = br.readLine();
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
	
	private Properties getProp() {
		return ConfigManager.prop;
	}
	
	public static ConfigManager getInstance() {
		if(ConfigManager.instance == null) {
			ConfigManager.instance = new ConfigManager();
		}
		return ConfigManager.instance;
	}
	
	public String getProperty(String property) throws PropertyNotExists {
		String res = "";
		if(!ConfigManager.getAllProperties().contains(property)) {
			throw new PropertyNotExists("Property " +property + " does not exists");
		} else {
			res = this.getProp().getProperty(property);
		}
		return res;
	}
}

