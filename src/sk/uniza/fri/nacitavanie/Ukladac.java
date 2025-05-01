package sk.uniza.fri.nacitavanie;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * 12. 4. 2025 - 13:32
 *
 * @author matus
 */
public class Ukladac {

    public Ukladac() {

    }

    public void ulozDoSuborov(String suborHrany, String suborVrcholy, HashMap<Integer, Hrana> hrany, HashMap<Integer, Vrchol> vrcholy) {
        try (BufferedWriter zapisovacVrcholy = new BufferedWriter(new FileWriter(suborVrcholy));
             BufferedWriter zapisovacHrany = new BufferedWriter(new FileWriter(suborHrany))) {

            for (Vrchol v : vrcholy.values()) {
                String riadok = v.getId() + " " +
                        v.getSurX() + " " +
                        v.getSurY() + " " +
                        v.getPoziadavkaOrKapacita() + " " +
                        v.getTypVrchola().getCislo();
                zapisovacVrcholy.write(riadok);
                zapisovacVrcholy.newLine();
            }

            for (Hrana h : hrany.values()) {
                int zakazCislo = h.jeZakazana() ? 1 : 0;
                String riadok = h.getIdHrany() + " " +
                        h.getIdZ() + " " +
                        h.getIdDo() + " " +
                        h.getCena() + " " +
                        zakazCislo;
                zapisovacHrany.write(riadok);
                zapisovacHrany.newLine();
            }

            System.out.println("Uloženie prebehlo úspešne!");

        } catch (IOException e) {
            System.out.println("Chyba pri ukladaní: " + e.getMessage());
        }
    }


}
