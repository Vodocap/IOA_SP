package sk.uniza.fri.nacitavanie;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

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

            System.out.println("Zapisujem vrcholy . . . ");
            Iterator<Vrchol> iteratorV = vrcholy.values().iterator();
            while (iteratorV.hasNext()) {
                Vrchol v = iteratorV.next();
                String riadok = v.getId() + " " +
                        v.getSurX() + " " +
                        v.getSurY() + " " +
                        v.getPoziadavkaOrKapacita() + " " +
                        v.getTypVrchola().getCislo();

                zapisovacVrcholy.write(riadok);

                if (iteratorV.hasNext()) {
                    zapisovacVrcholy.newLine();
                }
            }
            System.out.println("Zapisujem hrany . . . ");
            Iterator<Hrana> iteratorH = hrany.values().iterator();
            while (iteratorH.hasNext()) {
                Hrana h = iteratorH.next();

                String riadok = h.getIdHrany() + " " +
                        h.getIdZ() + " " +
                        h.getIdDo() + " " +
                        h.getCena();

                zapisovacHrany.write(riadok);

                if (iteratorH.hasNext()) {
                    zapisovacHrany.newLine();
                }
            }

            System.out.println("Uloženie prebehlo úspešne!");

        } catch (IOException e) {
            System.out.println("Chyba pri ukladaní: " + e.getMessage());
        }
    }


}
