package app.users;

import java.time.LocalDate;

import app.App;
import app.rbac.Groups;
import app.utils.Utils;

/**
 * Abstract class representing all the users
 * 
 */
public abstract class User {
	private static int counter = 0;
	private final int ID;
	private final String NAME;
	private final String FORENAME;
	private final LocalDate BIRTHDAY;
	private Groups group;

	public static int getCounter() {
		return User.counter;
	}
	
	/**
	 * Create a new user
	 * @param name
	 * @param forename
	 * @param birthday
	 * @param group
	 */
	public User(int id, String name, String forename, LocalDate birthday, Groups group) {
		if(id >= 0) {	
			this.ID = id;
		} else {
			this.ID = User.counter;
		}
		this.NAME = name;
		this.FORENAME = forename;
		this.BIRTHDAY = birthday;
		this.group = group;
		User.counter++;
	}
	
	/**
	 * Represents a user as a string.
	 */
	public String toString() {
		StringBuilder sBuilder = new StringBuilder(this.getClass().getSimpleName() + " [("+this.getId()+") " + this.getName() + " " + this.getForename() + " " + Utils.getAgeFromLocalDate(this.getBirthday()) +" years old]");
		return sBuilder.toString();
	}
	
	/**
	 * 
	 * @return user's id
	 */
	public int getId() {
		return ID;
	}
	
	/**
	 * 
	 * @return user's name
	 */
	public String getName() {
		return NAME;
	}
	
	/**
	 * 
	 * @return user's forename
	 */
	public String getForename() {
		return FORENAME;
	}
	
	/**
	 * 
	 * @return user's birthday date
	 */
	public LocalDate getBirthday() {
		return BIRTHDAY;
	}

	/**
	 * 
	 * @return user's group
	 */
	public Groups getGroup() {
		return group;
	}
	
	/**
	 * Set a new group for the user
	 * @param group
	 */
	public void setGroup(Groups group) {
		this.group = group;
	}
}
