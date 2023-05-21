package structure;

import java.util.ArrayList;



public class transition {
    private int[] arcWeightIn;
    private int[] arcWeightOut;
    private String name ;
    private String label;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public transition(String label, int[] arcWeightIn, int[] arcWeightOut) {
        this.arcWeightIn = arcWeightIn;
        this.arcWeightOut = arcWeightOut;
        this.label = label;

    }

    public int[] getArcWeightIn() {
		return arcWeightIn;
	}

	public void setArcWeightIn(int[] arcWeightIn) {
		this.arcWeightIn = arcWeightIn;
	}

	public int[] getArcWeightOut() {
		return arcWeightOut;
	}

	public void setArcWeightOut(int[] arcWeightOut) {
		this.arcWeightOut = arcWeightOut;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public int getArcWeightIn(int index) {
        return arcWeightIn[index];
    }

    public int getArcWeightOut(int index) {
        return arcWeightOut[index];
    }

  
}
