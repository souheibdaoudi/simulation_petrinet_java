package petri_net;

import java.util.ArrayList;
import structure.place;
import structure.transition;

public class NuSMVGenerator {

	private static int getMaxTokenCount(ArrayList<ArrayList<Integer>> markingTable, int placeIndex) {
		int maxToken = 0;
		for (ArrayList<Integer> row : markingTable) {
			int token = row.get(placeIndex);
			if (token > maxToken) {
				maxToken = token;
			}
		}
		return maxToken;
	}

	static String generateNuSMVCode(ArrayList<place> places, ArrayList<transition> transitions,
			ArrayList<ArrayList<Integer>> markings) {
		StringBuilder sb = new StringBuilder();

		// Module declaration
		sb.append("MODULE main\n");
		sb.append("VAR\n");
		sb.append("\tstate : {");
		for (int i = 0; i < markings.size(); i++) {
			sb.append("M").append(i);
			if (i < markings.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append("};\n");
		for (place p : places) {
			sb.append("\t").append(p.getName()).append(" : 0..").append(getMaxTokenCount(markings, places.indexOf(p)))
					.append(";\n");
		}

		// Transition rules
		sb.append("ASSIGN\n");
		sb.append("init(state) := M0;\n");
		sb.append("next(state) := case\n");

		int markingCount = 0;
		for (ArrayList<Integer> marking : markings) {
			sb.append("\tstate = M").append(markingCount).append(" : {");

			boolean isFirst = true;
			for (transition t : transitions) {
				boolean isEnabled = true;
				for (int i = 0; i < places.size(); i++) {
					int arcWeightIn = t.getArcWeightIn()[i];
					if (arcWeightIn > marking.get(i)) {
						isEnabled = false;
						break;
					}
				}

				if (isEnabled) {
					if (!isFirst) {
						sb.append(", ");
					}
					
					sb.append("M").append(markings.indexOf(getMarkingAfterTransition(marking, t)));
					isFirst = false;}
			
			}

			sb.append("};\n");
			markingCount++;
		}

		// Default rule for unchanged state
		sb.append("\tTRUE : state;\n");
		sb.append("esac;\n");

		// Variable assignments
		for (place p : places) {
			sb.append(p.getName()).append(" := case\n");
			markingCount = 0;
			for (ArrayList<Integer> marking : markings) {
				int token = marking.get(places.indexOf(p));
				if (token != 0) {
					sb.append("\tstate = M").append(markingCount).append(" : ").append(token).append(";\n");
				}
				markingCount++;
			}
			sb.append("\tTRUE : 0;\n");
			sb.append("esac;\n\n");
		}

		return sb.toString();
	}

	private static ArrayList<Integer> getMarkingAfterTransition(ArrayList<Integer> marking, transition t) {
		ArrayList<Integer> newMarking = new ArrayList<>();
		int[] arcWeightIn = t.getArcWeightIn();
		int[] arcWeightOut = t.getArcWeightOut();
		for (int i = 0; i < marking.size(); i++) {
			int token = marking.get(i);
			token -= arcWeightIn[i];
			token += arcWeightOut[i];
			newMarking.add(token);
		}
		return newMarking;
	}

	
}
