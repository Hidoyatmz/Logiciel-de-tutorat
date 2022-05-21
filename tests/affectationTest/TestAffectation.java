package affectationTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import affectation.Affectation;
import app.App;
import app.resources.Resource;
import app.users.Student;
import fr.ulille.but.sae2_02.graphes.Arete;

class TestAffectation {
	private Affectation affectation;
	private Student s0, s1, s2, s3, s48, s49, s29, s30, s31, s57;
	
	@BeforeEach
	public void initialization() {
		App app = App.getApp();
		s0 = (Student) app.getUserFromId(0);
		s1 = (Student) app.getUserFromId(1);
		s2 = (Student) app.getUserFromId(2);
		s3 = (Student) app.getUserFromId(3);
		s57 = (Student) app.getUserFromId(57);
		s48 = (Student) app.getUserFromId(48);
		s49 = (Student) app.getUserFromId(49);
		s29 = (Student) app.getUserFromId(29);
		s30 = (Student) app.getUserFromId(30);
		s31 = (Student) app.getUserFromId(31);
		this.affectation = new Affectation(Resource.MATHS, true);
		this.affectation.initAffectation();
	}
	
	@Test
	public void testApplys() {
		assertEquals(10, this.affectation.getApplys().size());
		assertEquals(s3, this.affectation.getTutoredApplys().get(0));
		assertEquals(s57, this.affectation.getTutoredApplys().get(this.affectation.getTutoredApplys().size()-1));
		assertEquals(s48, this.affectation.getTutorApplys().get(0));
		assertEquals(s30, this.affectation.getTutorApplys().get(this.affectation.getTutorApplys().size()-1));
	}
	
	@Test
	public void testVertices() {
		assertEquals(10, this.affectation.getGraph().sommets().size());
	}
	
	@Test
	public void testEdges() {
		assertEquals(25, this.affectation.getGraph().aretes().size());
		
		assertEquals(s3, this.affectation.getGraph().aretes().get(0).getExtremite1());
		assertEquals(s48, this.affectation.getGraph().aretes().get(0).getExtremite2());
		
		assertEquals(s57, this.affectation.getGraph().aretes().get(this.affectation.getGraph().aretes().size()-1).getExtremite1());
		assertEquals(s30, this.affectation.getGraph().aretes().get(this.affectation.getGraph().aretes().size()-1).getExtremite2());
	}
	
	@Test
	public void testAffectation() {
		Arete<Student> aff0 = new Arete<>(s0, s29);
		Arete<Student> aff1 = new Arete<>(s57, s30);
		Arete<Student> aff2 = new Arete<>(s3, s48);
		Arete<Student> aff3 = new Arete<>(s2, s31);
		Arete<Student> aff4 = new Arete<>(s1, s49);
		
		assertEquals(aff0.getExtremite1(), this.affectation.getCalcul().getAffectation().get(0).getExtremite1());
		assertEquals(aff0.getExtremite2(), this.affectation.getCalcul().getAffectation().get(0).getExtremite2());
		
		assertEquals(aff1.getExtremite1(), this.affectation.getCalcul().getAffectation().get(1).getExtremite1());
		assertEquals(aff1.getExtremite2(), this.affectation.getCalcul().getAffectation().get(1).getExtremite2());
		
		assertEquals(aff2.getExtremite1(), this.affectation.getCalcul().getAffectation().get(2).getExtremite1());
		assertEquals(aff2.getExtremite2(), this.affectation.getCalcul().getAffectation().get(2).getExtremite2());
		
		assertEquals(aff3.getExtremite1(), this.affectation.getCalcul().getAffectation().get(3).getExtremite1());
		assertEquals(aff3.getExtremite2(), this.affectation.getCalcul().getAffectation().get(3).getExtremite2());
		
		assertEquals(aff4.getExtremite1(), this.affectation.getCalcul().getAffectation().get(4).getExtremite1());
		assertEquals(aff4.getExtremite2(), this.affectation.getCalcul().getAffectation().get(4).getExtremite2());
	}
}
