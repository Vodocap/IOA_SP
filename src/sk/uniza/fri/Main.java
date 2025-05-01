package sk.uniza.fri;

import sk.uniza.fri.gui.UvodneOkno;

/**
 * Created by IntelliJ IDEA.
 * User: matus
 * Date: 12. 4. 2025
 * Time: 13:32
 */
public class Main {

    public static void main(String[] args) {
        UvodneOkno uvodneOkno = new UvodneOkno();
        uvodneOkno.setSize(1000, 800);
        uvodneOkno.setContentPane(uvodneOkno.$$$getRootComponent$$$());
        uvodneOkno.pack();
        uvodneOkno.setVisible(true);
        System.out.println("Hello world");
    }
}
