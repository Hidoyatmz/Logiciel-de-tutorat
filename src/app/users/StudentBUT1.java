package app.users;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import app.App;
import app.rbac.Groups;
import app.resources.Resource;

/**
 * Represents a student in BUT1
 *
 */
public class StudentBUT1 extends Student {
	private ArrayList<Resource> resources;
	
	/**
	 * Create a new Student in BUT1
	 * @param name
	 * @param forename
	 * @param birthday
	 * @param marks
	 */
	public StudentBUT1(int id, String name, String forename, LocalDate birthday, HashMap<Resource, Double> marks, int absences) {
		super(id, name, forename, birthday, marks, Groups.BUT1, absences, 1);
		this.resources = new ArrayList<Resource>();
		this.setYear(1);
	}
	
	/**
	 * Create a new Student in BUT1 without marks.
	 * @param name
	 * @param forename
	 * @param birthday
	 */
	public StudentBUT1(int id, String name, String forename, LocalDate birthday) {
		this(id, name, forename, birthday, new HashMap<Resource, Double>(), 0);
	}
	
	/**
	 * Create a new Student in BUT1 without marks & id.
	 * @param name
	 * @param forename
	 * @param birthday
	 */
	public StudentBUT1(String name, String forename, LocalDate birthday) {
		this(-1, name, forename, birthday, new HashMap<Resource, Double>(), 0);
	}
	
	/**
	 * Get 
	 * @return list of resources the student is getting tutored in.
	 */
	public ArrayList<Resource> getResources() {
		return this.resources;
	}
	
	@Override
	public boolean isBUT1() {
		return true;
	}

	@Override
	public boolean addResource(Resource resource) {
		boolean res = false;
		if(!this.getResources().contains(resource) && !resource.tutoredExist(this)) {
			this.getResources().add(resource);
			res = true;
		}
		return res;
	}

	/**
	 * Remove a resource from the user
	 * @param ressource
	 */
	public boolean removeResource(Resource resource) {
		return this.getResources().remove(resource);
	}
	
	@Override
	public boolean canApply() {
		return App.applyOpens();
	}
	
	@Override
	public boolean isInResource(Resource resource) {
		return resource.tutoredExist(this);
	}
}
