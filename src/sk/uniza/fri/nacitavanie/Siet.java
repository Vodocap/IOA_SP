package sk.uniza.fri.nacitavanie;

/**
 * 12. 4. 2025 - 13:32
 *
 * @author matus
 */
import sk.uniza.fri.gui.IOACanvas;
import sk.uniza.fri.sweep.LabelAlgoritmus;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Siet {

    private HashMap<Integer,Vrchol> vrcholy;
    private HashMap<Integer, Hrana> hrany;
    private int idVrcholaCounter;
    private int idHranyCounter;
    private Ukladac ukladac;

    public Siet() {
        this.vrcholy = new HashMap<>();
        this.hrany = new HashMap<>();
        this.ukladac = new Ukladac();
        idVrcholaCounter = 0;
        idHranyCounter = 0;
    }

    public int sweepMozny(double kapacitaVozidla) {
        LabelAlgoritmus labelAlgoritmus = new LabelAlgoritmus(vrcholy);
        if (!labelAlgoritmus.jeSpojity()) {
            return 1;
        }
        boolean jeStredisko = false;
        for (Integer i : vrcholy.keySet()) {
            if (vrcholy.get(i).getPoziadavkaOrKapacita() == 0 && vrcholy.get(i).getTypVrchola() != TypVrchola.STREDISKO) {
                return 2;
            }

            if (vrcholy.get(i).getPoziadavkaOrKapacita() > kapacitaVozidla && vrcholy.get(i).getTypVrchola() != TypVrchola.STREDISKO) {
                return 3;
            }

            if (vrcholy.get(i).getTypVrchola() == TypVrchola.STREDISKO) {
                jeStredisko = true;
            }
        }

        if (!jeStredisko) {
            return 4;
        }

        if (this.vrcholy.size() == 1) {
            return 5;
        }

        return 0;
    }

    public HashMap<Integer,Vrchol> nacitaj(String hrany, String vrcholy) {

        System.out.println("Nacitava sa dopravna siet zo suborov. . . .");
        File hranySubor = new File(hrany);
        File vrcholySubor = new File(vrcholy);

        try {

            Scanner hranyScanner = new Scanner(hranySubor);
            Scanner vrcholyScanner = new Scanner(vrcholySubor);

            while (vrcholyScanner.hasNextLine()) {
                String riadok = vrcholyScanner.nextLine();
                String[] splitnuty = riadok.split(" ");

                int id = Integer.parseInt(splitnuty[0]);
                double surX = Double.parseDouble(splitnuty[1]);
                double surY = Double.parseDouble(splitnuty[2]);
                double poziadavka = Double.parseDouble(splitnuty[3]);
                int typCislo = Integer.parseInt(splitnuty[4]);
                TypVrchola typVrchola = TypVrchola.zCisla(typCislo);

                Vrchol vrchol = new Vrchol(id, surX, surY, poziadavka, typVrchola);
                this.vrcholy.put(id, vrchol);

                idVrcholaCounter = Math.max(idVrcholaCounter, id);
            }

            while (hranyScanner.hasNextLine()) {
                String riadok = hranyScanner.nextLine();
                String[] splitnuty = riadok.split(" ");

                int idHrany = Integer.parseInt(splitnuty[0]);
                int idZ = Integer.parseInt(splitnuty[1]);
                int idDo = Integer.parseInt(splitnuty[2]);
                double cena = Double.parseDouble(splitnuty[3]);
                boolean zakaz = Integer.parseInt(splitnuty[4]) == 1;

                Vrchol vrcholZ = this.vrcholy.get(idZ);
                Vrchol vrcholDo = this.vrcholy.get(idDo);

                Hrana pridanaHrana = new Hrana(idZ, idDo,
                        new Point((int) vrcholZ.getSurX(), (int) vrcholZ.getSurY()),
                        new Point((int) vrcholDo.getSurX(), (int) vrcholDo.getSurY()),
                        cena);
                pridanaHrana.setZakaz(zakaz);
                pridanaHrana.setIdHrany(idHrany);

                this.hrany.put(idHrany, pridanaHrana);
                this.vrcholy.get(idZ).pridajVychadzajucuHranu(pridanaHrana);

                idHranyCounter = Math.max(idHranyCounter, idHrany);
            }
            

            System.out.println("Nacitali sa vrcholy: ");
            for (Integer i : this.vrcholy.keySet()) {
                this.vrcholy.get(i).printVrchol();
            }

            System.out.println("Nacitaly sa hrany: ");
            for (Integer i : this.hrany.keySet()) {
                this.hrany.get(i).printHrana();
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        return this.vrcholy;


    }

    public void uloz(String vrcholySubor, String hranySubor) {
        this.ukladac.ulozDoSuborov(hranySubor, vrcholySubor, hrany, vrcholy);
    }

    public void pridajVrchol(Vrchol novyVrchol) {
        this.vrcholy.put(novyVrchol.getId(), novyVrchol);
    }

    public void odstranVrchol(Vrchol odstranovany) {
        this.vrcholy.remove(odstranovany.getId());
    }

    public void upravVrchol(Vrchol upraveny) {
        this.vrcholy.replace(upraveny.getId(), upraveny);
    }

    public void vycistiSiet() {
        vrcholy.clear();
        hrany.clear();
    }

    public void spojHranou(int iDVrchola1, int iDVrchola2, Hrana hrana, double cena) {

        Hrana novaHranaOpacna = new Hrana(iDVrchola2, iDVrchola1, cena);
        this.hrany.put(getIdNHranyaNajnovsiAIteruj(), hrana);
        this.hrany.put(getIdNHranyaNajnovsiAIteruj(), novaHranaOpacna);
        novaHranaOpacna.setIdHrany(getIdNHranyaNajnovsiAIteruj());
        this.vrcholy.get(iDVrchola1).pridajVychadzajucuHranu(hrana);
        this.vrcholy.get(iDVrchola2).pridajVychadzajucuHranu(novaHranaOpacna);
    }

    public int getIDStrediska() {
        for (Integer i : vrcholy.keySet()) {
            if (vrcholy.get(i).getTypVrchola() == TypVrchola.STREDISKO) {
                return i;
            }
        }
        return -1;
    }

    public HashMap<Integer, Vrchol> getVrcholy() {
        return this.vrcholy;
    }
    public void odstranHranu(Hrana odstranenaHrana) {
        this.hrany.remove(odstranenaHrana.getIdHrany());
    }

    public HashMap<Integer, Hrana> getHrany() {
        return this.hrany;
    }

    public int getIdNaNajnovsiAIteruj() {
        this.idVrcholaCounter++;
        return this.idVrcholaCounter;
    }

    public int getIdNHranyaNajnovsiAIteruj() {
        this.idHranyCounter++;
        return this.idHranyCounter;
    }
}


