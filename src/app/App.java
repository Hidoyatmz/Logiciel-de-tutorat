package app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import app.apply.Apply;
import app.config.ConfigManager;
import app.exceptions.PropertyNotExists;
import app.json.JSONParser;
import app.rbac.Groups;
import app.resources.Resource;
import app.users.OtherStudent;
import app.users.Student;
import app.users.StudentBUT1;
import app.users.User;
import app.utils.Utils;
import fr.ulille.but.sae2_02.donnees.DonneesPourTester;

/**
 * Represents the app and its datas.
 *
 */
public final class App {
	private static App instance;
	private static LocalDate applyLimitDate = Utils.getLimitApplyDateFromConfig();
	private ArrayList<User> users = new ArrayList<User>();
	private HashMap<String, Resource> resources = new HashMap<>();
	
	/**
	 * Used to initialize datas of the app.
	 */
	private App() {
		//this.checkDependencies();
		this.loadResources();
	}
	
	public void checkDependencies() {
		System.err.println("Dependencies missing");
		System.exit(0);
	}
	
	public void loadResources() {
		this.getResources().put("MATHS", new Resource("MATHS", 8));
		this.getResources().put("DEV", new Resource("DEV", 10));
		this.getResources().put("BDD", new Resource("BDD", 6));
		this.loadUsers();
	}
	
	/*
	 * Register users from datas.
	 */
	public void loadUsers() {
		ArrayList<JSONObject> students = null;
		try {
			students = JSONParser.parseJsonFile(ConfigManager.getInstance().getProperty("STUDENTS_DATAS"));
		} catch (PropertyNotExists e) {
			System.err.println("Couldnt load users... exiting app.");
			System.exit(0);
		}
		for(JSONObject student: students) {
			this.registerStudent(student);
		}
	}
	
	public void registerStudent(JSONObject data) {
		String forename = data.getString("forename");
		String name = data.getString("name");
		int year = data.getInt("year");
		int absences = data.getInt("absences");
		int id = data.getInt("id");
		Student student = year == 1 ? new StudentBUT1(id, name, forename, LocalDate.now()) : new OtherStudent(name, forename, LocalDate.now(), year);
		student.setAbsences(absences);
		JSONObject marks = data.getJSONObject("marks");
		for(String resourceMark: JSONObject.getNames(marks)) {
			student.addMark(this.getResource(resourceMark), marks.getInt(resourceMark));
		}
		JSONArray resources = data.getJSONArray("resources");
		for(int i = 0; i < resources.length(); i++) {
			student.addResource(this.getResource((String) resources.get(i)));
		}
		this.addUser(student);
	}
	
	/**
	 * Register some applys (test purpose)
	 */
	public void loadApplys() {
		
	}

	/**
	 * 
	 * @return the App unique instance
	 */
	public static App getApp() {
		if(App.instance == null) {
			App.instance = new App();
		}
		return App.instance;
	}
	
	/**
	 * 
	 * @return All users registered on the App.
	 */
	public ArrayList<User> getUsers() {
		return users;
	}
	
	/**
	 * Return users registered in the App array if their current year is the same as given in parameter.
	 * @param year
	 * @return
	 */
	public ArrayList<User> getStudentsFromYear(int year) {
		ArrayList<User> res = new ArrayList<User>();
		if(year >= 1 && year <= 3) {
			Student student;
			for(User user: this.getUsers()) {
				student = (Student) user;
				if(student.getYear() == year) {
					res.add(user);
				}
			}
		}
		return res;
	}
	
	/**
	 * Return a user from his Id.
	 * @param id
	 * @return
	 */
	public User getUserFromId(int id) {
		boolean find = false;
		int i = 0;
		User res = null;
		while(i < this.getUsers().size() && !find) {
			if(this.getUsers().get(i).getId() == id) {
				find = true;
				res = this.getUsers().get(i);
			}
			i++;
		}
		return res;
	}
	
	/**
	 * Return all the professors registered in the App array.
	 * @return
	 */
	public ArrayList<User> getProfessors(){
		ArrayList<User> res = new ArrayList<User>();
		for(User user: this.getUsers()) {
			if(user.getGroup() == Groups.PROFESSOR) {
				res.add(user);
			}
		}
		return res;
	}
	
	/**
	 * Add a new user in the datas.
	 * @param userToAdd
	 */
	public void addUser(User userToAdd) {
		this.getUsers().add(userToAdd);
	}
	
	/**
	 * Add a list of users in the datas.
	 * @param usersToAdd
	 */
	public void addUsers(ArrayList<User> usersToAdd) {
		this.getUsers().addAll(usersToAdd);
	}
	
	
	/**
	 * Return all the applys for the given resource.
	 * @return ArrayList<Apply>.
	 */
	public ArrayList<Apply> getApplys(Resource resource) {
		ArrayList<Apply> allApplys = resource.getApplys();
		for(Resource res: this.getResources().values()) {
			for(Apply apply: res.getApplys()) {
				allApplys.add(apply);
			}
		}
		return allApplys;
	}

	public static LocalDate getApplyLimitDate() {
		return applyLimitDate;
	}

	public static void setApplyLimitDate(LocalDate applyLimitDate) {
		App.applyLimitDate = applyLimitDate;
	}
	
	public static boolean applyOpens() {
		return App.getApplyLimitDate().isAfter(LocalDate.now());
	}

	public HashMap<String, Resource> getResources() {
		return resources;
	}
	
	public Resource getResource(String label) {
		Resource res = null;
		if(this.getResources().containsKey(label)) {
			res = this.getResources().get(label);
		}
		return res;
	}
}
