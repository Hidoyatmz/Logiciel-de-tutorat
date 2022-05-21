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
public class OtherStudent extends Student {
	private Resource resource; // resource this student is tutoring.
	
	/**
	 * Create a new Student in BUT2/3
	 * @param name
	 * @param forename
	 * @param birthday
	 * @param marks
	 */
	public OtherStudent(int id, String name, String forename, LocalDate birthday, HashMap<Resource, Double> marks, int absences, int year) {
		super(id, name, forename, birthday, marks, Groups.OtherBUT, absences, year);
		this.setYear(year);
	}
	
	/**
	 * Create a new Student in BUT2/3 without marks.
	 * @param name
	 * @param forename
	 * @param birthday
	 */
	public OtherStudent(int id, String name, String forename, LocalDate birthday, int year) {
		this(id, name, forename, birthday, new HashMap<Resource, Double>(), 0, year);
	}
	
	/**
	 * Create a new Student in BUT2/3 without marks && id.
	 * @param name
	 * @param forename
	 * @param birthday
	 */
	public OtherStudent(String name, String forename, LocalDate birthday, int year) {
		this(-1, name, forename, birthday, new HashMap<Resource, Double>(), 0, year);
	}
	
	/**
	 * Get 
	 * @return list of resources the student is getting tutored in.
	 */
	public Resource getResources() {
		return this.resource;
	}
	
	private void setResource(Resource resource) {
		this.resource = resource;
	}
	
	@Override
	public boolean isBUT1() {
		return false;
	}

	@Override
	public boolean addResource(Resource resource) {
		boolean res = false;
		if(this.getResources() == null) {
			this.setResource(resource);
			res = true;
		}
		return res;
	}
	
	/**
	 * Remove a resource from the user
	 * @param ressource
	 */
	public void removeResource() {
		this.setResource(null);
	}
	
	@Override
	public boolean canApply() {
		return App.applyOpens() && this.getResources() == null;
	}

	@Override
	public boolean isInResource(Resource resource) {
		return this.getResources().equals(resource) && resource.tutorExist(this);
	}

}
