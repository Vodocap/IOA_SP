package sk.uniza.fri.sweep;

import sk.uniza.fri.nacitavanie.Hrana;
import sk.uniza.fri.nacitavanie.Vrchol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class LabelAlgoritmus {
    private Queue<Vrchol> front;
    private HashMap<Integer,Vrchol> vrcholy;
    private HashMap<Integer,Double> znacky;

    public LabelAlgoritmus(HashMap<Integer, Vrchol> pVrcholy) {
        this.front = new LinkedList<>();
        this.vrcholy = pVrcholy;
    }

    private void inicializuj(HashMap<Integer,Vrchol> vrcholy, Integer koren) {
        this.znacky = new HashMap<>();
        for (Integer i : vrcholy.keySet()) {
            if (i.equals(koren)) {
                znacky.put(i,0.0);
            } else {
                znacky.put(i,Double.MAX_VALUE);
            }
        }
        this.front.add(vrcholy.get(koren));
    }

    public ArrayList<Integer> algoritmus(Integer koren, int koniec) {
        ArrayList<Integer> cesta = new ArrayList<>();
        Map<Integer, Integer> predchodcaMap = new HashMap<>();
        this.inicializuj(this.vrcholy, koren);
        while (true) {
            if (this.front.isEmpty()) {
                break;
            } else {

                Vrchol vrchol = this.front.remove();
                for (Hrana naslednik : vrchol.getSpojenie()) {

                    if (this.znacky.get(vrchol.getId()) + naslednik.getCena() < this.znacky.get(naslednik.getIdDo())) {
                        this.znacky.replace(naslednik.getIdDo(), this.znacky.get(vrchol.getId()) + naslednik.getCena());

                        this.front.add(this.vrcholy.get(naslednik.getIdDo()));
                        predchodcaMap.put(naslednik.getIdDo(), vrchol.getId());
                    }

                }

            }

        }

        Integer aktualny = koniec;
        while (aktualny != null && !aktualny.equals(koren)) {
            cesta.add(aktualny);
            aktualny = predchodcaMap.get(aktualny);
        }

        if (aktualny != null) {
            cesta.add(koren);
        }

        Collections.reverse(cesta);

        return cesta;


    }

    public boolean jeSpojity() {
        for (Integer i : vrcholy.keySet()) {
            for (Integer integer : vrcholy.keySet()) {
                algoritmus(i, integer);
                for (Integer i1 : znacky.keySet()) {
                    if (znacky.get(i1) == Double.MAX_VALUE) {
                        System.out.println("Nie je spojity ");
                        return false;
                    }
                }
            }
            System.out.println(znacky.toString());
        }



        return true;
    }

    public VysledokShortestpath getShortestPath(int zaciatok, int koniec) {
        var erejlistCesta = this.algoritmus(zaciatok, koniec);

        double vyslednaVzdialenost = this.znacky.get(koniec);

//        String cesta = "cesta: ";
//
//        for (Integer i : erejlistCesta) {
//            cesta += " " + i + " ";
//        }
//        System.out.println("Vysledna vzdialenost: " + vyslednaVzdialenost + " medzi vrcholmi " + zaciatok + " - " + koniec);
//        System.out.println(cesta);

        return new VysledokShortestpath(vyslednaVzdialenost, erejlistCesta);
    }
}
