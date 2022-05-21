package app.json;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import app.config.ConfigManager;
import app.exceptions.PropertyNotExists;
import app.utils.Utils;

public class JSONParser {

	public static ArrayList<JSONObject> parseJsonFile(File file){
		ArrayList<JSONObject> res = new ArrayList<>();
		if(!file.exists() && file.canRead() && file.length() > 0) { return res; }
		JSONArray arr = new JSONArray(Utils.fileToString(file));
		for(int i = 0; i < arr.length(); i++) {
			res.add(arr.getJSONObject(i));
		}
		return res;
	}
	
	public static ArrayList<JSONObject> parseJsonFile(String path){
		return JSONParser.parseJsonFile(new File(path));
	}
	
	
	public static void main(String[] args) {
		File file = null;
		try {
			file = new File(ConfigManager.getInstance().getProperty("PROFESSORS_DATAS"));
		} catch (PropertyNotExists e) {
			System.err.println(e);
			return;
		}
		ArrayList<JSONObject> res = parseJsonFile(file);
		for(JSONObject obj: res) {
			System.out.println(obj);
			JSONArray arr = (JSONArray) obj.get("directing");
			System.out.println(arr.get(0));
		}
	}
}
