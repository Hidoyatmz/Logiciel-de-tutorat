package tpGraphe;

import java.util.ArrayList;
import java.util.List;

import fr.ulille.but.sae2_02.graphes.Arete;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

public class TPAffectation {
	public static void main(String[] args) {
		GrapheNonOrienteValue graphe = new GrapheNonOrienteValue<String>();
		graphe.ajouterSommet("A");
		graphe.ajouterSommet("B");
		graphe.ajouterSommet("C");
		graphe.ajouterSommet("D");
		graphe.ajouterSommet("W");
		graphe.ajouterSommet("X");
		graphe.ajouterSommet("Y");
		graphe.ajouterSommet("Z");
		
		graphe.ajouterArete("A", "W", 13);
		graphe.ajouterArete("A", "X", 4);
		graphe.ajouterArete("A", "Y", 7);
		graphe.ajouterArete("A", "Z", 6);
		
		graphe.ajouterArete("B", "W", 1);
		graphe.ajouterArete("B", "X", 11);
		graphe.ajouterArete("B", "Y", 5);
		graphe.ajouterArete("B", "Z", 4);
		
		graphe.ajouterArete("C", "W", 6);
		graphe.ajouterArete("C", "X", 7);
		graphe.ajouterArete("C", "Y", 2);
		graphe.ajouterArete("C", "Z", 8);
		
		graphe.ajouterArete("D", "W", 1);
		graphe.ajouterArete("D", "X", 3);
		graphe.ajouterArete("D", "Y", 5);
		graphe.ajouterArete("D", "Z", 9);
		System.out.println(graphe);
		
		List<String> list1 = graphe.sommets();
		list1 = list1.subList(0, list1.size()/2);
		List<String> list2 = graphe.sommets();
		list2 = list2.subList(list2.size()/2, list2.size());
		
		System.out.println(list1.size());
		System.out.println(list2.size());
		
		CalculAffectation calcul = new CalculAffectation(graphe, list1, list2);
		System.out.println(calcul.getAffectation());
		System.out.println(calcul.getCout());
	}
}
