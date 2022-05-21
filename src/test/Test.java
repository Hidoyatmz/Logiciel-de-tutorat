package test;

import java.util.HashMap;
import java.util.Map;

public class Test {
	public static void main(String[] args) {		
		HashMap<String, Integer> myMap = new HashMap<>();
		myMap.put("Java", 17);
		myMap.put("Maths", 8);
		myMap.put("Réseaux", 3);
		
		for(Map.Entry<String, Integer> values : myMap.entrySet()) {
			String matiere = values.getKey();
			int note = values.getValue();
			
			if(note > 10) {
				System.out.println("L'élève peut encadrer du tutorat pour la matière: "+ matiere);
			} else {
				System.out.println("L'élève peut bénéficier du tutorat: "+ matiere);
			}
		}
			
		myMap.forEach((key,value) -> {
			System.out.println(value > 10);
		});
	}
}
