package sk.uniza.fri.sweep;

import java.util.ArrayList;

public class VysledokShortestpath {
    double vzdialenost;
    ArrayList<Integer> vzdialenosti;

    public VysledokShortestpath(double vzdialenost, ArrayList<Integer> vzdialenosti) {
        this.vzdialenost = vzdialenost;
        this.vzdialenosti = vzdialenosti;

    }

    public double getVzdialenost() {
        return vzdialenost;
    }

    public ArrayList<Integer> getVzdialenosti() {
        return vzdialenosti;
    }
}