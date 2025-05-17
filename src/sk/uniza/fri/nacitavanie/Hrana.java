package sk.uniza.fri.nacitavanie;

import sk.uniza.fri.gui.IOACanvas;
import sk.uniza.fri.gui.UpravHranu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 12. 4. 2025 - 13:32
 *
 * @author matus
 */
public class Hrana extends JComponent {
    private int idHrany;
    private int idZ;
    private int idDo;
    private double cena;
    private boolean zakaz;
    private boolean vykresliHranu = true;
    private IOACanvas platno;
    private Point start;
    private Point end;

    public Hrana(int idZ, int idDo, double cena) {
        this.idZ = idZ;
        this.idDo = idDo;
        this.cena = cena;
    }

    public Hrana(int idZ, int idDo, Point start, Point end, double cena) {
        this.idZ = idZ;
        this.idDo = idDo;
        this.start = start;
        this.end = end;
        this.cena = cena;

        int minX = Math.min(start.x, end.x);
        int minY = Math.min(start.y, end.y);
        int maxX = Math.max(start.x, end.x);
        int maxY = Math.max(start.y, end.y);
        System.out.println(start.toString());
        System.out.println(end.toString());
        setBounds(minX, minY, maxX - minX == 0 ? 1 : maxX - minX, maxY - minY == 0 ? 1 : maxY - minY);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    System.out.println("Klikol si na hranu zo " + idZ + " do " + idDo);

                    UpravHranu upravHranu = new UpravHranu(Hrana.this, Hrana.this.platno);
                    upravHranu.setSize(400, 300);
                    upravHranu.setContentPane(upravHranu.$$$getRootComponent$$$());
                    upravHranu.pack();
                    upravHranu.setVisible(true);

                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    System.out.println("Klikol si na hranu: " + this.toString());

                    if (platno != null) {
                        platno.hranaKliknuta(Hrana.this);
                    }
                }
            }
        });
    }

    public int getIdHrany() {
        return this.idHrany;
    }

    public void setIdHrany(int idHrany) {
        this.idHrany = idHrany;
    }

    protected void paintComponent(Graphics g) {

        if (vykresliHranu) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));

            if (this.jeZakazana()) {
                g2.setColor(Color.RED);
            } else {
                g2.setColor(Color.BLACK);
            }

            g2.drawLine(start.x - getX(), start.y - getY(), end.x - getX(), end.y - getY());

            g2.dispose();
        }

    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
        repaint();
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
        repaint();
    }

    public void nakresliText(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.BLUE);

        int x1 = start.x;
        int y1 = start.y;
        int x2 = end.x;
        int y2 = end.y;

        int stredX = (x1 + x2) / 2;
        int stredY = (y1 + y2) / 2;

        int dx = x2 - x1;
        int dy = y2 - y1;
        double dlzka = Math.sqrt(dx * dx + dy * dy);
        double offset = 15;

        double normalX = -dy / dlzka;
        double normalY = dx / dlzka;

        int textX = (int)(stredX + normalX * offset);
        int textY = (int)(stredY + normalY * offset);


        g2.setFont(new Font("Arial", Font.BOLD, 12));
        System.out.println(stredX);
        System.out.println(stredY);
        g2.drawString(String.format("%.2f", cena), textX, textY);
    }

    @Override
    public String toString() {
        return "Hrana{" +
                "idZ=" + idZ +
                ", idDo=" + idDo +
                ", cena=" + cena +
                ", zakaz=" + zakaz +
                '}';
    }

    public boolean isVykresliHranu() {
        return this.vykresliHranu;
    }

    public void setVykresliHranu(boolean vykresliHranu) {
        this.vykresliHranu = vykresliHranu;
    }

    public boolean isZakaz() {
        return this.zakaz;
    }

    public void setPlatno(IOACanvas platno) {
        this.platno = platno;
    }
    public IOACanvas getPlatno() {
        return platno;
    }

    public int getIdZ() {
        return idZ;
    }

    public void setIdZ(int idZ) {
        this.idZ = idZ;
    }

    public int getIdDo() {
        return idDo;
    }

    public void setZakaz(boolean zakaz) {
        this.zakaz = zakaz;
    }

    public boolean jeZakazana() {
        return this.zakaz;
    }

    public void setIdDo(int idDo) {
        this.idDo = idDo;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public void printHrana() {
        System.out.println("ID Z: " + idZ + " Do: " + idDo + " Cena: " + cena);
    }
}
