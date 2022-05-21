package affectation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import app.App;
import app.apply.Apply;
import app.apply.ApplyType;
import app.resources.Resource;
import app.users.OtherStudent;
import app.users.Student;
import app.users.StudentBUT1;
import app.users.User;
import fr.ulille.but.sae2_02.graphes.Arete;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

public class Affectation {
	private GrapheNonOrienteValue<Student> graph;
	private ArrayList<Apply> applys;
	private ArrayList<Student> tutorApplys;
	private ArrayList<Student> tutoredApplys;
	private Resource resource;
	private CalculAffectation<Student> calcul;
	private boolean useMissing;
	private double malus;
	private final double incrMalus = -0.51;
	
	public Affectation(Resource resource, boolean useMissing){
		this.graph = new GrapheNonOrienteValue<Student>();
		this.resource = resource;
		this.setUseMissing(useMissing);
		this.malus = 1;
	}
	
	public void initAffectation() {
		this.registerApplys();
		this.registerArraysFromApplys();
		this.registerVertices();
		this.registerEdges();
		this.registerCalc();
	}

	public void setApplys(ArrayList<Apply> applys) {
		this.applys = applys;
	}
	
	public void setMalus(double malus) {
		this.malus = malus;
	}
	
	public double getIncrMalus() {
		return this.incrMalus;
	}
	
	public double getMalus() {
		return this.malus;
	}
	
	public ArrayList<Student> getTutorApplys() {
		return tutorApplys;
	}

	public ArrayList<Student> getTutoredApplys() {
		return tutoredApplys;
	}

	public boolean isUseMissing() {
		return useMissing;
	}

	public void setUseMissing(boolean useMissing) {
		this.useMissing = useMissing;
	}

	public ArrayList<Apply> getApplys(){
		return this.applys;
	}
	
	public GrapheNonOrienteValue<Student> getGraph() {
		return this.graph;
	}
	
	public Resource getResource() {
		return resource;
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

	public void registerApplys() {
		App app = App.getApp();
		ArrayList<Apply> applys = app.getApplys();
		this.setApplys(applys);
		this.fixApplys();
	}
	
	private void registerArraysFromApplys() {
		this.tutorApplys = this.getTutorFromApplys();
		this.tutoredApplys = this.getTutoredFromApplys();
		
		StudentsComparator comparator = new StudentsComparator(this.getResource());
		this.tutorApplys.sort(comparator);
		Collections.sort(this.tutoredApplys, Collections.reverseOrder(comparator));
		
	}
	
	public void fixApplys() {
		ArrayList<Student> appliedTutored = this.getTutoredFromApplys();
		ArrayList<Student> appliedTutor = this.getTutorFromApplys();
		int tutoredCount = appliedTutored.size();
		int tutorCount = appliedTutor.size();
		if(tutorCount > tutoredCount) {
			Student fakeStudent;
			for(int i = 0; i < (tutorCount-tutoredCount); i++) {
				fakeStudent = new StudentBUT1("Fake", "Student", LocalDate.now());
				fakeStudent.addMark(Resource.valueOf("MATHS"), 20);
				this.getApplys().add(new Apply(fakeStudent, this.getResource()));
				App.getApp().addUser(fakeStudent);
			}
		} else if(tutoredCount > tutorCount) { // Problème "telle arête existe déjà..."
			int i = 0;
			ArrayList<Student> thirdYearTutor = new ArrayList<Student>();
			Student toDuplicate;
			OtherStudent newStudent;
			for(Student tutor: appliedTutor) {
				if(tutor.getYear() == 3) {
					thirdYearTutor.add(tutor);
				}
			}
			while(tutorCount != tutoredCount) {
				toDuplicate = thirdYearTutor.get(i);
				newStudent = new OtherStudent(toDuplicate.getName(), toDuplicate.getForename(), toDuplicate.getBirthday(), toDuplicate.getYear());
				newStudent.addMark(Resource.valueOf("MATHS"), toDuplicate.getMarkFromResource(this.getResource()));
				this.getApplys().add(new Apply(newStudent, this.getResource()));
				tutorCount++;
				i = i+1 >= thirdYearTutor.size() ? 0 : i++;
			}
		}
	}
	
	public void registerVertices() {
		ArrayList<Apply> applys = this.getApplys();
		GrapheNonOrienteValue<Student> graph = this.getGraph();
		for(Apply apply: applys) {
			graph.ajouterSommet(apply.getCandidate());
		}
	}
	
	public void registerEdges() {
		GrapheNonOrienteValue<Student> graph = this.getGraph();
		
		ArrayList<Student> appliedTutored = this.tutoredApplys;
		ArrayList<Student> appliedTutor = this.tutorApplys;
		
		double edgeWeight;
		
		for(Student tutored: appliedTutored) {
			for(Student tutor: appliedTutor) {
				edgeWeight = this.calcWeight(tutored, tutor);
				graph.ajouterArete(tutored, tutor, edgeWeight);
			}
			this.setMalus(this.getMalus() + this.getIncrMalus());
		}
	}
	
	public void registerCalc() {
		this.calcul = this.createCalcul();
	}
	
	private double calcWeight(Student tutored, Student tutor) {
		double tutoredMark = this.calcWeightTutored(tutored);
		double tutorMark = this.calcWeightTutor(tutor);
		
		double weight = tutoredMark - tutorMark;
		weight *= this.getMalus();
		
		// Cas forcage affectaton if(tutored.getId() == 3 && tutor.getId() == 31) { weight = -5000; }
		
		System.out.println("["+tutored.getId()+"] ("+tutored.getMarkFromResource(this.getResource())+" "+tutor.getMarkFromResource(this.getResource())+") --> " + weight + " ["+tutor.getId()+" - "+ tutor.getYear() +"]");
		return weight;
	}
	
	private double calcWeightTutored(Student tutored) {
		double mark = tutored.getMarkFromResource(this.getResource());
		return mark + tutored.getAbsences();
	}
	
	private double calcWeightTutor(Student tutor) {
		double mark = tutor.getMarkFromResource(this.getResource());
		// Cas pondération if(tutor.getId() == 48) { tutor.setAbsences(25); }
		return mark + 10*(tutor.getYear()-2) - tutor.getAbsences();
	}
	
	private CalculAffectation<Student> createCalcul() {
		return new CalculAffectation<Student>(this.getGraph(), this.tutorApplys, this.tutoredApplys);
	}	
	
	public CalculAffectation<Student> getCalcul() {
		return this.calcul;
	}

	public static void main(String[] args) {
		Affectation affectation = new Affectation(Resource.MATHS, true);
		affectation.initAffectation();
		CalculAffectation<Student> calcul = affectation.getCalcul();
		ArrayList<Arete<Student>> couples = (ArrayList<Arete<Student>>) calcul.getAffectation();
		ArrayList<Arete<Student>> toRemove = new ArrayList<Arete<Student>>();
		for(Arete<Student> arete: couples) {
			if(arete.getExtremite1().getName() == "Fake") { toRemove.add(arete); }
		}
		couples.removeAll(toRemove);
		for(Arete<Student> arete: couples) {
			System.out.println("Affectation du tutoré " + arete.getExtremite1().getName() + " ("+arete.getExtremite1().getId()+") au tuteur " + arete.getExtremite2().getName() + " ("+arete.getExtremite2().getId()+")");
		}
		System.out.println("Cout total de l'affectation: " + calcul.getCout());
	}
}
