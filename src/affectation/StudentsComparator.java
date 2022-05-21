package affectation;

import java.util.Comparator;

import app.resources.Resource;
import app.users.Student;

public class StudentsComparator implements Comparator<Student>{
	Resource resource;
	
	public StudentsComparator(Resource resource) {
		this.resource = resource;
	}
	
	@Override
	public int compare(Student o1, Student o2) {
		if(o1.getYear() == o2.getYear()) {
			if(o1.getMarkFromResource(resource) < o2.getMarkFromResource(resource)) {
				return 1;
			} else if(o1.getMarkFromResource(resource) == o2.getMarkFromResource(resource)) {
				return 0;
			} else {
				return -1;
			}
		} else if (o1.getYear() < o2.getYear()) {
			return 1;
		} else {
			return -1;
		}
	}

}
