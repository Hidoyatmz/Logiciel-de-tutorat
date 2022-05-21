package app.json;

import java.time.LocalDate;

import org.json.JSONArray;
import org.json.JSONObject;

import app.App;
import app.apply.Apply;
import app.resources.Resource;
import app.users.OtherStudent;
import app.users.Professor;
import app.users.Student;
import app.users.StudentBUT1;
import app.users.User;

public abstract class JSONSerializer {
	
	private static JSONObject serializeUser(User user) {
		JSONObject res = new JSONObject();
		res.put("id", user.getId());
		res.put("name", user.getName());
		res.put("forename", user.getForename());
		res.put("group", user.getGroup());
		res.put("birthday", user.getBirthday());
		return res;
	}
	
	// TODO : Serialize pairs -> how ? idid
	public static JSONObject serializeResource(Resource resource) {
		JSONObject res = new JSONObject();
		res.put("label", resource.getLabel());
		res.put("minAvgTutor", resource.getMinAvgTutor());
		res.put("maxAbsenceTutor", resource.getMaxAbsenceTutor());
		res.put("maxAvgTutored", resource.getMaxAvgTutored());
		res.put("maxAbsenceTutored", resource.getMaxAbsenceTutored());
		return res;
	}
	
	public static JSONObject serializeApplys(Apply apply) {
		JSONObject res = new JSONObject();
		res.put("id", apply.getId());
		res.put("state", apply.getState());
		res.put("type", apply.getType());
		res.put("candidate", apply.getCandidate().getId());
		res.put("resource", apply.getResource());
		return res;
	}
	
	private static JSONObject serializeStudent(Student student) {
		JSONObject res = serializeUser(student);
		res.put("absences", student.getAbsences());
		res.put("year", student.getYear());
		res.put("marks", student.getMarks());
		return res;
	}
	
	public static JSONObject serializeProfessor(Professor professor) {
		JSONObject res = serializeUser(professor);
		res.put("directing", new JSONArray(professor.getDirecting().toString()));
		return res;
	}
	
	public static JSONObject serializeStudentBUT1(StudentBUT1 student) {
		JSONObject res = serializeStudent(student);
		res.put("resources", new JSONArray(student.getResources().toString()));
		return res;
	}
	
	public static JSONObject serializeOtherStudent(OtherStudent student) {
		JSONObject res = serializeStudent(student);
		res.put("resources", student.getResources());
		return res;
	}
	
	public static void main(String[] args) {
		StudentBUT1 user = new StudentBUT1("Test", "Test2", LocalDate.now());
		Professor prof = new Professor("Test", "Test2", LocalDate.now());
		prof.addDirecting(App.getApp().getResource("MATHS"));
		prof.addDirecting(App.getApp().getResource("DEV"));
		user.addMark(App.getApp().getResource("MATHS"),12);
		user.addResource(App.getApp().getResource("MATHS"));
		user.addMark(App.getApp().getResource("DEV"),12);
		user.addResource(App.getApp().getResource("DEV"));
		System.out.println(serializeStudentBUT1(user));
		System.out.println(serializeProfessor(prof));
		JSONSaver.saveProfessor(prof, false);
		JSONSaver.saveStudent(user, true);
	}
}
