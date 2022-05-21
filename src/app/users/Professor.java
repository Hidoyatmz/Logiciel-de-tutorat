package app.users;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import app.apply.Apply;
import app.apply.ApplyState;
import app.exceptions.CantAcceptApply;
import app.rbac.Groups;
import app.resources.Resource;

/**
 * Abstract class that represents a user that is a Professor.
 *
 */
public class Professor extends User{
	private HashSet<Resource> directing;
	
	/**
	 * Create a new Professor.
	 * @param name
	 * @param forename
	 * @param birthday
	 */
	public Professor(int id, String name, String forename, LocalDate birthday) {
		super(id, name, forename, birthday, Groups.PROFESSOR);
		this.directing = new HashSet<>();
	}
	
	/**
	 * Create a new Professor without id.
	 * @param name
	 * @param forename
	 * @param birthday
	 */
	public Professor(String name, String forename, LocalDate birthday) {
		super(-1, name, forename, birthday, Groups.PROFESSOR);
		this.directing = new HashSet<>();
	}
	
	/**
	 * @TODO
	 */
	public void showApplies() {
		System.out.println("@Todo");
	}
	
	/**
	 * 
	 * @return resources that the professor is directing.
	 */
	public HashSet<Resource> getDirecting() {
		return this.directing;
	}
	
	/**
	 * Set professor directing a new resource.
	 * @param ressource
	 */
	public void addDirecting(Resource resource) {
		this.getDirecting().add(resource);
	}
	
	/**
	 * return whether a professor is directing a resource or not.
	 * @param resource
	 * @return boolean
	 */
	public boolean isDirecting(Resource resource) {
		return this.getDirecting().contains(resource) && resource.isDirectingBy(this);
	}
	
	/**
	 * Accept the given apply.
	 * @param apply
	 * @throws CantAcceptApply 
	 */
	public boolean acceptApply(Apply apply) throws CantAcceptApply {
		boolean res = false;
		Student candidate = apply.getCandidate();
		Resource resource = apply.getResource();
		if(this.isDirecting(resource) && candidate.canApply()) {
			apply.setState(ApplyState.TO_AFFECT);
			candidate.addResource(resource);
			res = true;
		} else {
			throw new CantAcceptApply();
		}
		return res;
	}
	
	/**
	 * Decline the given apply.
	 * @param apply
	 */
	public boolean declineApply(Apply apply) {
		boolean res = false;
		if(this.isDirecting(apply.getResource())) {
			apply.setState(ApplyState.DECLINED);
			res = true;
		}
		return res;
	}
}
