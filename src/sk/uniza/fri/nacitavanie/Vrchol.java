package sk.uniza.fri.nacitavanie;

import sk.uniza.fri.gui.IOACanvas;
import sk.uniza.fri.gui.UpravVrchol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Vrchol extends JComponent {
    private static final int PRIEMER = 20;
    private int id;
    private int cluster;
    private double surX;
    private double surY;
    private ArrayList<Hrana> spojenie;
    private TypVrchola typVrchola;
    private double poziadavkaOrKapacita;
    private double uhol;
    private IOACanvas platno;

    public int getCluster() {
        return this.cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }

    public Vrchol(int id, double surX, double surY, double pPoziadavka , TypVrchola pTypVrchola) {
        this.id = id;
        this.surX = surX;
        this.surY = surY;
        this.spojenie = new ArrayList<>();
        this.typVrchola = pTypVrchola;
        this.poziadavkaOrKapacita = pPoziadavka;

        setBounds((int)surX - PRIEMER/2, (int)surY - PRIEMER/2, PRIEMER, PRIEMER);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    System.out.println("Klikol si na vrchol na pozícii: (" + surX + ", " + surY + ")");

                    System.out.println(Vrchol.this.toString());

                    UpravVrchol upravVrchol = new UpravVrchol(Vrchol.this, Vrchol.this.platno);
                    upravVrchol.setSize(500, 500);
                    upravVrchol.setContentPane(upravVrchol.$$$getRootComponent$$$());
                    upravVrchol.pack();
                    upravVrchol.setVisible(true);

                } else if(SwingUtilities.isLeftMouseButton(e)) {
                    Vrchol.this.platno.vrcholKliknuty(Vrchol.this);
                }
            }
        });


    }


    public void odstranHranuSIdVrchola(int idVrchola) {
        for (int i = 0; i < this.spojenie.size(); i++) {
            if (this.spojenie.get(i).getIdDo() == idVrchola) {
                this.spojenie.remove(i);
            }
        }
    }

    public IOACanvas getPlatno() {
        return this.platno;
    }

    public void setPlatno(IOACanvas platno) {
        this.platno = platno;
    }


    public double getPoziadavkaOrKapacita() {
        return this.poziadavkaOrKapacita;
    }

    public void setPoziadavkaOrKapacita(double poziadavkaOrKapacita) {
        this.poziadavkaOrKapacita = poziadavkaOrKapacita;
    }

    public double getUhol() {
        return this.uhol;
    }

    public void setUhol(double uhol) {
        this.uhol = uhol;
    }
    @Override
    public String toString() {
        return "Vrchol{" +
                "id=" + id +
                ", surX=" + surX +
                ", surY=" + surY +
                ", spojenie=" + spojenie +
                ", typVrchola=" + typVrchola +
                ", poziadavka=" + poziadavkaOrKapacita +
                ", uhol=" + uhol +
                '}';
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.GRAY);

        if (this.getTypVrchola() == TypVrchola.STREDISKO) {
            g2.setColor(Color.GREEN);
        } else if (this.getTypVrchola() == TypVrchola.ZAKAZNIK) {
            g2.setColor(Color.BLUE);
        } else {
            g2.setColor(Color.GRAY);
        }
        g2.fillOval(0, 0, PRIEMER, PRIEMER);
    }

    public TypVrchola getTypVrchola() {
        return this.typVrchola;
    }

    public void setTypVrchola(TypVrchola typVrchola) {
        this.typVrchola = typVrchola;
    }

    public void pridajVychadzajucuHranu(Hrana pHrana) {
        this.spojenie.add(pHrana);
    }

    public ArrayList<Hrana> getSpojenie() {
        return spojenie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSurX() {
        return surX;
    }

    public void setSurX(double surX) {
        this.surX = surX;
    }

    public double getSurY() {
        return surY;
    }

    public void setSurY(double surY) {
        this.surY = surY;
    }

    public void printVrchol() {
        System.out.println(toString());
    }
}