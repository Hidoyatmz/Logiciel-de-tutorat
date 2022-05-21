package app.apply;

import app.resources.Resource;
import app.users.Student;

/**
 * Represents an Apply.
 */
public class Apply {
	private static int counter = 0;
	private final int ID;
	private ApplyState state;
	private final ApplyType TYPE;
	private final Student CANDIDATE;
	private Resource resource;
	
	/**
	 * Create a new Apply
	 * @param candidate
	 * @param resource
	 */
	public Apply(Student candidate, Resource resource) {
		this.state = ApplyState.WAITING;
		this.CANDIDATE = candidate;
		this.setResource(resource);
		this.ID = Apply.counter;
		this.TYPE = candidate.getApplyType();
		Apply.counter++;
	}
	
	/**
	 * 
	 * @return ID of the apply
	 */
	public int getId() {
		return ID;
	}
	
	/**
	 * 
	 * @return state of the apply
	 */
	public ApplyState getState() {
		return this.state;
	}
	
	/**
	 * Set a new given state to the apply
	 * @param state
	 */
	public void setState(ApplyState state) {
		this.state = state;
	}
	
	/**
	 * 
	 * @return CANDIDATE of the apply
	 */
	public Student getCandidate() {
		return CANDIDATE;
	}
	
	/**
	 * 
	 * @return RESOURCE of the apply
	 */
	public Resource getResource() {
		return resource;
	}
	
	/**
	 * Set a new given resource to the apply
	 * @param resource
	 */
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	/**
	 * 
	 * @return TYPE of apply
	 */
	public ApplyType getType() {
		return TYPE;
	}
	
	/**
	 * Represents the apply as a string
	 */
	public String toString() {
		StringBuilder sBuilder = new StringBuilder("[("+this.getId()+") : " + this.getCandidate() + " applied to " + this.getResource() + " " + this.getType() + " -- " + this.getState());
		return sBuilder.toString();
	}
}
