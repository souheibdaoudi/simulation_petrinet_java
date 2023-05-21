package petri_net;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import structure.place;
import structure.transition;

public class simulation_petrinet {
    public ArrayList<ArrayList<Integer>> generateAllMarkings(ArrayList<place> places,
    		ArrayList<transition> transitions) {
        ArrayList<ArrayList<Integer>> markings = new ArrayList<>();
        ArrayList<Integer> initialMarking = new ArrayList<>();

        // Get the initial marking
        for (place p : places) {
            initialMarking.add(p.getToken());
            p.setInitialtoken(p.getToken());
        }

        // Simulate the Petri net and generate all markings
        Set<String> visitedMarkings = new HashSet<>();
        simulatePetriNet(places, transitions, initialMarking, markings, 1, visitedMarkings);

        return markings;
    }

    private void simulatePetriNet(ArrayList<place> places, ArrayList<transition> transitions,
            ArrayList<Integer> currentMarking, ArrayList<ArrayList<Integer>> markings, int markingNumber,
            Set<String> visitedMarkings) {
        String markingString = getMarkingString(currentMarking);
        if (!visitedMarkings.contains(markingString)) {
            visitedMarkings.add(markingString);
            markings.add(new ArrayList<>(currentMarking));
        } else {
            return; // Skip this marking if it has already been visited
        }

        // Separate transitions by label
        ArrayList<transition> labeledTransitions = new ArrayList<>();
        ArrayList<transition> otherTransitions = new ArrayList<>();
        for (transition t : transitions) {
            if (t.getLabel().equals("t1") || t.getLabel().equals("t2")) {
                labeledTransitions.add(t);
            } else {
                otherTransitions.add(t);
            }
        }

        // Iterate over labeled transitions first
        for (transition t : labeledTransitions) {
            // Check if the transition is enabled
            boolean isEnabled = true;
            for (int i = 0; i < places.size(); i++) {
                int arcWeightIn = t.getArcWeightIn(i);
                if (arcWeightIn > currentMarking.get(i)) {
                    isEnabled = false;
                    break;
                }
            }

            // If the transition is enabled, fire it and simulate the Petri net with the new marking
            if (isEnabled) {
                ArrayList<Integer> newMarking = new ArrayList<>(currentMarking);

                // Update the marking by firing the transition
                for (int i = 0; i < places.size(); i++) {
                    int arcWeightIn = t.getArcWeightIn(i);
                    int arcWeightOut = t.getArcWeightOut(i);
                    newMarking.set(i, currentMarking.get(i) - arcWeightIn + arcWeightOut);
                }

                StringBuilder sb = new StringBuilder();
                sb.append("M").append(markingNumber - 1).append(getMarkingString(currentMarking));
                sb.append(" --").append(t.getLabel()).append("--> ");
                
                sb.append("M").append(markingNumber).append(getMarkingString(newMarking));
                System.out.println(sb.toString());

               

                // Recursively simulate the Petri net with the new marking
                simulatePetriNet(places, transitions, newMarking, markings, markingNumber + 1, visitedMarkings);
            } // Check if the previous marking is the same as the current marking
            
        }

        // Iterate over other transitions
        for (transition t : otherTransitions) {
            // Check if the transition is enabled (same as before)

            // If the transition is enabled, fire it and simulate the Petri net with the new marking (same as before)
        }
    }

    private String getMarkingString(ArrayList<Integer> marking) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        for (int i = 0; i < marking.size(); i++) {
            sb.append("p").append(i + 1).append("=").append(marking.get(i));
            if (i < marking.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("}");

        return sb.toString();
    }
}
