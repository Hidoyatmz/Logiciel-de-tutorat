package app.json;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import org.json.JSONArray;

import app.config.ConfigManager;
import app.exceptions.PropertyNotExists;
import app.resources.Resource;
import app.users.OtherStudent;
import app.users.Professor;
import app.users.Student;
import app.users.StudentBUT1;
import app.utils.Utils;

public abstract class JSONSaver {
	private static JSONArray setUpJSONArray(String path, boolean add) throws Exception {
		JSONArray res = new JSONArray();
		if(!add) { return res; }
		File datas;
		String content;
		try {
			datas = new File(path);
		}
		catch(NullPointerException e) { throw new NullPointerException(); }
		if(datas.length() > 0) {
			content = Utils.fileToString(datas);
			res = new JSONArray(content);
		}
		return res;
	}
	
	public static boolean saveStudent(Student student, boolean add) {	
		JSONArray baseDatas = new JSONArray();
		try {
			baseDatas = JSONSaver.setUpJSONArray(ConfigManager.getInstance().getProperty("STUDENTS_DATAS"), add);
		} 
		catch (PropertyNotExists e) { System.err.println(e); return false; }
		catch (NullPointerException e) { System.err.println(e); return false; }
		catch (Exception e) { System.err.println(e); return false; }
		
		if(student.isBUT1()) {
			baseDatas.put(JSONSerializer.serializeStudentBUT1((StudentBUT1) student));
		} else {
			baseDatas.put(JSONSerializer.serializeOtherStudent((OtherStudent) student));
		}
		
		try (FileWriter fileWriter = new FileWriter(new File(ConfigManager.getInstance().getProperty("STUDENTS_DATAS")))){
            fileWriter.write(baseDatas.toString());
            fileWriter.flush();
        } catch (Exception e) { e.printStackTrace(); }
		return true;
	}
	
	public static boolean saveStudent(Student student) {
		return saveStudent(student, false);
	}
	
	public static void saveAllStudents(ArrayList<Student> students) {
		boolean add = false;
		for(Student student: students) {
			saveStudent(student, add);
			add = true;
		}
	}
	
	public static boolean saveProfessor(Professor professor, boolean add) {
		JSONArray baseDatas = new JSONArray();
		try {
			baseDatas = JSONSaver.setUpJSONArray(ConfigManager.getInstance().getProperty("PROFESSORS_DATAS"), add);
		} 
		catch (PropertyNotExists e) { System.err.println(e); return false; }
		catch (NullPointerException e) { System.err.println(e); return false; }
		catch (Exception e) { System.err.println(e); return false; }
		
		baseDatas.put(JSONSerializer.serializeProfessor(professor));
		
		try (FileWriter fileWriter = new FileWriter(new File(ConfigManager.getInstance().getProperty("PROFESSORS_DATAS")))){
            fileWriter.write(baseDatas.toString());
            fileWriter.flush();
        } catch (Exception e) { e.printStackTrace(); }
		return true;
	}
	
	public static boolean saveProfessor(Professor professor) {
		return saveProfessor(professor, false);
	}
	
	public static void saveAllProfessors(ArrayList<Professor> professors) {
		boolean add = false;
		for(Professor professor: professors) {
			saveProfessor(professor, add);
			add = true;
		}
	}
	
	public static boolean saveResource(Resource resource, boolean add) {
		JSONArray baseDatas = new JSONArray();
		try {
			baseDatas = JSONSaver.setUpJSONArray(ConfigManager.getInstance().getProperty("RESOURCES_DATAS"), add);
		} 
		catch (PropertyNotExists e) { System.err.println(e); return false; }
		catch (NullPointerException e) { System.err.println(e); return false; }
		catch (Exception e) { System.err.println(e); return false; }
		
		baseDatas.put(JSONSerializer.serializeResource(resource));
		
		try (FileWriter fileWriter = new FileWriter(new File(ConfigManager.getInstance().getProperty("RESOURCES_DATAS")))){
            fileWriter.write(baseDatas.toString());
            fileWriter.flush();
        } catch (Exception e) { e.printStackTrace(); }
		return true;
	}
	
	public static boolean saveResource(Resource resource) {
		return saveResource(resource, false);
	}
	
	public static void saveAllResources(ArrayList<Resource> resources) {
		boolean add = false;
		for(Resource resource: resources) {
			saveResource(resource, add);
			add = true;
		}
	}
}
