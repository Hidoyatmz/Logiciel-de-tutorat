package app.users;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import app.App;
import app.apply.Apply;
import app.apply.ApplyType;
import app.rbac.Groups;
import app.resources.Resource;
/**
 * Abstract class that represents a user that is a Student
 *
 */
public abstract class Student extends User{
	private HashMap<Resource, Double> marks;
	private int absences;
	private int year;
	
	/**
	 * Create a new student.
	 * @param name
	 * @param forename
	 * @param birthday
	 * @param marks
	 * @param group
	 */
	public Student(int id, String name, String forename, LocalDate birthday, HashMap<Resource, Double> marks, Groups group, int absences, int year) {
		super(id, name, forename, birthday, group);
		this.marks = marks;
		this.absences = absences;
		this.year = year;
	}
	
	public int getAbsences() {
		return absences;
	}

	public void setAbsences(int absences) {
		this.absences = absences;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * 
	 * @return marks of the student as an ArrayList
	 */
	public HashMap<Resource, Double> getMarks() {
		return marks;
	}
	
	public double getMarkFromResource(Resource resource) {
		double res = 0;
		HashMap<Resource, Double> marks = this.getMarks();
		Iterator<Entry<Resource, Double>> iterator = marks.entrySet().iterator();
		boolean found = false;
		while(iterator.hasNext() && !found) {
			Map.Entry<Resource, Double> datas = (Map.Entry<Resource, Double>) iterator.next();
			if(datas.getKey() == resource) {
				found = true;
				res = datas.getValue();
			}
			
		}
		return res;
	}
	
	/**
	 * Add a mark to the student
	 * @param subject
	 * @param mark
	 */
	public void addMark(Resource resource, double mark) {
		this.getMarks().put(resource, mark);
	}
	
	/**
	 * Add a list of marks to the student
	 * @param marks
	 */
	public void addMarks(HashMap<Resource, Double> marks) {
		this.getMarks().putAll(marks);
	}
	
	/**
	 * Create a new apply for a given resource
	 * @param ressource
	 */
	/*public boolean applyResource(Resource resource, App app) {
		boolean res = false;
		if(this.canApply()) {			
			app.addApply(new Apply(this, resource));
			res = true;
		}
		return res;
	}*/
	
	/**
	 * 
	 * @return if the student is a BUT1
	 */
	public boolean isBUT1() {
		return false;
	}
	
	/**
	 * 
	 * @return the proper type of Apply for the student.
	 */
	public ApplyType getApplyType() {
		return this.isBUT1() ? ApplyType.TUTORED : ApplyType.TUTORING;
	}
	
	/**
	 * Add a resource to the user
	 * @param ressourceToAdd
	 */
	public abstract boolean addResource(Resource resource);

	/**
	 * Return true if the student can make an apply.
	 * @return
	 */
	public abstract boolean canApply();
	
	public abstract boolean isInResource(Resource resource);
}
