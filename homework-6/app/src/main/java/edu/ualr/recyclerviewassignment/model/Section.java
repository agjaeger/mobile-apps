package edu.ualr.recyclerviewassignment.model;


public class Section extends Item {
    private String label;

    public Section (String label) {
        this.label = label;
        this.section = true;
    }

    public Section () {
        this.section = true;
    }

    public String getLabel () {
        return label;
    }

    public void setLabel (String label) {
        this.label = label;
    }
}
