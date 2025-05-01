package sk.uniza.fri.sweep;

import sk.uniza.fri.nacitavanie.Hrana;
import sk.uniza.fri.nacitavanie.Vrchol;

import java.util.ArrayList;
import java.util.HashMap;

public class ClusterFirst {
    private HashMap<Integer,Vrchol> vrcholy;
    private int idStrediska;
    private ArrayList<Vrchol> uholy;
    private ArrayList<Cluster> klasteri;
    private double kapacitaVozidla;
    private LabelAlgoritmus labelAlgoritmus;


    public ClusterFirst(HashMap<Integer,Vrchol> pVrcholy, int pIdStrediska, double pKapacitaVozidla) {
        this.vrcholy = pVrcholy;
        this.idStrediska = pIdStrediska;
        this.uholy = new ArrayList<>();
        this.klasteri = new ArrayList<>();
        this.labelAlgoritmus = new LabelAlgoritmus(this.vrcholy);
        kapacitaVozidla = pKapacitaVozidla;
    }

    private void vyratajUhly() {
        for (Integer i : vrcholy.keySet()) {
            if (i != idStrediska) {
                double rozX = (vrcholy.get(i).getSurX() - vrcholy.get(idStrediska).getSurX());
                double rozY = (vrcholy.get(i).getSurY() - vrcholy.get(idStrediska).getSurY());
                double uholVrchola = Math.toDegrees(Math.atan2(rozY, rozX));
                if (uholVrchola <= 0) {
                    uholVrchola += 360;
                }
                vrcholy.get(i).setUhol(uholVrchola);
                this.uholy.add(vrcholy.get(i));

            }
        }
        this.uholy.sort((v1, v2) -> Double.compare(v1.getUhol(), v2.getUhol()));

        for (Vrchol vrchol : uholy) {
            System.out.println(vrchol.toString());
        }


    }

    private double vyratajEuklidovuVzdialenost(Vrchol vrchol1, Vrchol vrchol2) {
        double dx = vrchol1.getSurX() - vrchol2.getSurX();
        double dy = vrchol1.getSurY() - vrchol2.getSurY();
        return Math.sqrt(dx * dx + dy * dy);
    }


    public void vyratajEklidoveVzdialenosti() {
        for (Integer i : vrcholy.keySet()) {
            for (Hrana naslednik : this.vrcholy.get(i).getSpojenie()) {
                naslednik.setCena(this.vyratajEuklidovuVzdialenost(this.vrcholy.get(i),
                        this.vrcholy.get(naslednik.getIdDo())));
            }
        }
    }

    private void najblizsiSused(int idStrediska) {

        for (Cluster cluster : klasteri) {
            int aktualny = idStrediska;
            for (int i = 0; i < cluster.vrchols.size(); i++) {
                double najblizsi = Double.MAX_VALUE;
                int najblizsiVrchol = 0;

                for (Vrchol vrchol : cluster.vrchols) {
                    if (labelAlgoritmus.getShortestPath(aktualny, vrchol.getId()).getVzdialenost() < najblizsi && !cluster.cesta.contains(vrchol.getId())) {
                        najblizsiVrchol = vrchol.getId();
                        najblizsi = labelAlgoritmus.getShortestPath(aktualny, vrchol.getId()).getVzdialenost();
                    }
                }
                aktualny = najblizsiVrchol;
                cluster.cesta.add(aktualny);
                cluster.vzdialenost += najblizsi;
            }
            cluster.vzdialenost += (labelAlgoritmus.getShortestPath(aktualny, idStrediska).getVzdialenost());
            cluster.cesta.add(idStrediska);

        }




    }

    public ArrayList<Cluster> sweep(int idStrediska) {

        int pridavanyIndex = 0;
        double suma = 0;
        Cluster klaster = new Cluster();
        this.vyratajUhly();
        int clusterNum = 1;
        while (pridavanyIndex < uholy.size()) {
            if (suma + uholy.get(pridavanyIndex).getPoziadavkaOrKapacita() <= kapacitaVozidla) {
                suma += uholy.get(pridavanyIndex).getPoziadavkaOrKapacita();
                klaster.addVrchol(uholy.get(pridavanyIndex));
                uholy.get(pridavanyIndex).setCluster(clusterNum);
                pridavanyIndex++;
            } else {
                suma = 0;
                clusterNum++;
                this.klasteri.add(klaster);
                klaster = new Cluster();
            }

        }

        for (Vrchol vrchol : uholy) {
            labelAlgoritmus.getShortestPath(idStrediska, vrchol.getId());
        }

        this.klasteri.add(klaster);
        System.out.println("Výsledné clustre: ");

        this.najblizsiSused(idStrediska);
        for (Cluster cluster : klasteri) {
            System.out.println(cluster.toString());
        }

        return klasteri;



    }



}
