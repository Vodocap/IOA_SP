package sk.uniza.fri.sweep;

import sk.uniza.fri.nacitavanie.Vrchol;

import java.util.ArrayList;

public class Cluster {
    ArrayList<Vrchol> vrchols;
    ArrayList<Integer> cesta;
    double vzdialenost;

    public Cluster() {
        this.vrchols = new ArrayList<>();
        this.cesta = new ArrayList<>();

    }

    @Override
    public String toString() {
        return "Cluster{" +
                "vrchols=" + vrchols +
                ", cesta=" + cesta +
                ", vzdialenost=" + vzdialenost +
                '}';
    }

    public void addVrchol(Vrchol vrchol) {
        vrchols.add(vrchol);
    }

    public void addBodCesty(Integer vrchol) {
        cesta.add(vrchol);
    }

    public ArrayList<Vrchol> getVrchols() {
        return vrchols;
    }

    public ArrayList<Integer> getCesta() {
        return cesta;
    }

}
