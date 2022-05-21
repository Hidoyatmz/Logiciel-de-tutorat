package app.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import app.App;
import app.apply.Apply;
import app.users.OtherStudent;
import app.users.Professor;
import app.users.Student;
import app.users.StudentBUT1;
import app.users.User;

public class Resource {
	private final int MAX_SPOTS;
	private HashMap<OtherStudent, ArrayList<StudentBUT1>> pairs;
	private HashSet<Professor> directors;
	private ArrayList<Apply> applys;
	private int maxAbsenceTutored;
	private int maxAvgTutored;
	private int maxAbsenceTutor;
	private int minAvgTutor;
	private String label;
	
	public Resource(String label, int spots) {
		this.label = label;
		this.MAX_SPOTS = spots;
		this.maxAbsenceTutored = Integer.MAX_VALUE;
		this.maxAvgTutored = Integer.MAX_VALUE;
		this.maxAbsenceTutor = Integer.MAX_VALUE;
		this.minAvgTutor = Integer.MIN_VALUE;
		this.directors = new HashSet<>();
		this.pairs = new HashMap<>();
		this.applys = new ArrayList<>();
	}
	
	public String getLabel() {
		return this.label;
	}

	public ArrayList<Apply> getApplys() {
		return applys;
	}

	public int getSpots() {
		return MAX_SPOTS;
	}

	public HashMap<OtherStudent, ArrayList<StudentBUT1>> getPairs() {
		return this.pairs;
	}
	
	/**
	 * @Todo : A test je sais pas si ca marche wtf
	 */
	public void updatePairsFromCriteria() {
		HashMap<OtherStudent, ArrayList<StudentBUT1>> pairs = this.getPairs();
		OtherStudent tutor;
		ArrayList<StudentBUT1> tutoredList = new ArrayList<StudentBUT1>();
		for(Entry<OtherStudent, ArrayList<StudentBUT1>> entry: pairs.entrySet()) {
			tutor = entry.getKey();
			tutoredList = entry.getValue();
			for(StudentBUT1 tutored: tutoredList) {
				if(!this.studentCanApply(tutored)) {
					pairs.get(tutor).remove(tutored);
				}
			}
			if(!this.studentCanApply(tutor)) {
				for(StudentBUT1 tutored: tutoredList) {
					this.applyResource(tutored);
				}
				pairs.remove(tutor);
			}
		}	
	}
	
	// rework double canApply
	public boolean applyResource(Student student) {
		boolean res = false;
		if(this.studentCanApply(student) && student.canApply()) {
			this.getApplys().add(new Apply(student, this));
		}
		return res;
	}
	
	/**
	 * Add an apply in the datas.
	 * @param applyToAdd
	 */
	public void addApply(Apply applyToAdd) {
		this.getApplys().add(applyToAdd);
	}
	
	/**
	 * Returns an array of Students who applied to get tutored
	 * @return
	 */
	public ArrayList<Student> getTutoredFromApplys(){
		ArrayList<Apply> applys = this.getApplys();
		ArrayList<Student> res = new ArrayList<Student>();
		for(Apply apply: applys) {
			if(apply.getCandidate().isBUT1()) {
				res.add(apply.getCandidate());
			}
		}
		return res;
	}
	
	/**
	 * Returns an array of Students who applied to be tutor
	 * @return
	 */
	public ArrayList<Student> getTutorFromApplys(){
		ArrayList<Apply> applys = this.getApplys();
		ArrayList<Student> res = new ArrayList<Student>();
		for(Apply apply: applys) {
			if(!apply.getCandidate().isBUT1()) {
				res.add(apply.getCandidate());
			}
		}
		return res;
	}
	
	/**
	 * Return if the given student can apply for the resource.
	 * @param student
	 * @return
	 */
	public boolean studentCanApply(Student student) {
		boolean res = false;
		if(student.isBUT1()) {
			res = student.getAbsences() > this.getMaxAbsenceTutored() || student.getMarkFromResource(this) > this.getMaxAvgTutored() ? false : true;
		} else {
			res = student.getAbsences() > this.getMaxAbsenceTutor() || student.getMarkFromResource(this) < this.getMinAvgTutor() ? false : true;
		}
		return res;
	}
	
	/**
	 * Returns true if the resource has reached its tutored limit.
	 * @return
	 */
	public boolean hasReachedMaximumTutored() {
		int maximum = this.getSpots();
		int currentTutored = 0;
		for(ArrayList<StudentBUT1> tutored: this.getPairs().values()) {
			currentTutored += tutored.size();
		}
		return maximum >= currentTutored;
	}
	
	/**
	 * Returns true if the tutor is already tutoring some students for this resource.
	 * @param tutor
	 * @return
	 */
	public boolean tutorExist(OtherStudent tutor) {
		return this.getPairs().containsKey(tutor);
	}
	
	/**
	 * Returns true if the tutored is already tutored for this resource.
	 * @param tutor
	 * @return
	 */
	public boolean tutoredExist(StudentBUT1 tutored) {
		boolean res = false;
		Iterator<ArrayList<StudentBUT1>> it = this.getPairs().values().iterator();
		while(!res && it.hasNext()) {
			if(it.next().contains(tutored)) {
				res = true;
			}
		}
		return res;
	}
	
	/**
	 * Returns true if the pair of tutor / tutored is already registered for this resource.
	 * @param tutor
	 * @param tutored
	 * @return
	 */
	public boolean pairExist(OtherStudent tutor, StudentBUT1 tutored) {
		return (this.tutorExist(tutor) && this.getPairs().get(tutor).contains(tutored)) ? true : false;
	}
	
	/**
	 * Register a pair of tutor / tutored for this resource if it doesn't already exists.
	 * @param tutor
	 * @param tutored
	 * @return true if the register has been done.
	 */	
	public boolean addPair(OtherStudent tutor, StudentBUT1 tutored) {
		boolean res = false;
		if(!this.pairExist(tutor, tutored) && !this.hasReachedMaximumTutored()) {
			if(this.tutorExist(tutor)) {
				this.getPairs().get(tutor).add(tutored);
			} else {
				this.getPairs().put(tutor, new ArrayList<StudentBUT1>(Arrays.asList(tutored)));
			}
		}
		return res;
	}
	/**
	 * Register a pair of tutor / multiple tutored for this resource if the pair doesn't already exists.
	 * @param tutor
	 * @param tutored
	 * @return 0 if they all have been registered, > 0 that corresponds of pairs that could not be register.
	 */
	
	public int addPair(OtherStudent tutor, ArrayList<StudentBUT1> tutored) {
		int toRegister = tutored.size();
		if(!this.tutorExist(tutor)) {
			this.getPairs().put(tutor, new ArrayList<StudentBUT1>());
		}
		
		for(StudentBUT1 student: tutored) {
			if(this.addPair(tutor, student)) { toRegister --; }
		}
		
		return toRegister;
	}

	public void setPairs(HashMap<OtherStudent, ArrayList<StudentBUT1>> pairs) {
		this.pairs = pairs;
	}

	public HashSet<Professor> getDirectors() {
		return directors;
	}


	public void setMaxAbsenceTutored(int maxAbsenceTutored) {
		this.maxAbsenceTutored = maxAbsenceTutored;
	}

	public void setMaxAvgTutored(int maxAvgTutored) {
		this.maxAvgTutored = maxAvgTutored;
	}

	public void setMaxAbsenceTutor(int maxAbsenceTutor) {
		this.maxAbsenceTutor = maxAbsenceTutor;
	}

	public void setMinAvgTutor(int minAvgTutor) {
		this.minAvgTutor = minAvgTutor;
	}

	public int getMaxAbsenceTutored() {
		return this.maxAbsenceTutored;
	}

	public int getMaxAvgTutored() {
		return this.maxAvgTutored;
	}

	public int getMaxAbsenceTutor() {
		return this.maxAbsenceTutor;
	}

	public int getMinAvgTutor() {
		return this.minAvgTutor;
	}
	
	public String toString() {
		return this.getLabel();
	}

	public boolean isDirectingBy(Professor professor) {
		return this.getDirectors().contains(professor);
	}
	
	public boolean isApply(Student student) {
		boolean res = false;
		int i = 0;
		while(!res && i < this.getApplys().size()) {
			if(this.getApplys().get(i).equals(student)) {
				res = true;
			}
		}
		return res;
	}

}
