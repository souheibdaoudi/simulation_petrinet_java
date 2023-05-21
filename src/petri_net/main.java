package petri_net;
import java.util.ArrayList;
import structure.place;
import structure.transition;
public class main {

    public static void main(String[] args) {
    	place p1 = new place(1,"p1");
    	place p2 = new place(2,"p2");
        place p3 = new place(0,"p3");
        // Create transitions
        int[] arcWeightInT1 = { 1, 2, 0};
        int[] arcWeightOutT1 = { 1, 0, 2};
        transition t1 = new transition("t1",arcWeightInT1, arcWeightOutT1);
       
        int[] arcWeightInT2 = { 0, 0,1};
        int[] arcWeightOutT2 = { 0, 1,0 };
        transition t2 = new transition("t2",arcWeightInT2, arcWeightOutT2);
      

        // Create Petri net
        ArrayList<place> places = new ArrayList<>();
        places.add(p1);
        places.add(p2);
        places.add(p3);

        ArrayList<transition> transitions = new ArrayList<>();
        transitions.add(t1);
        transitions.add(t2);
        
        // Create simulation object
        simulation_petrinet simulation = new simulation_petrinet();

        // Generate all markings
        ArrayList<ArrayList<Integer>> allMarkings = simulation.generateAllMarkings(places, transitions);

        // Print all markings
        for (ArrayList<Integer> marking : allMarkings) {
            System.out.println(marking);
        }
        NuSMVGenerator  nusmvCode = new NuSMVGenerator();
        String nusmvcode = nusmvCode.generateNuSMVCode(places, transitions, allMarkings);

        System.out.println(nusmvcode);

    }
    
}
