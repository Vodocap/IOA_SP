package sk.uniza.fri.gui;

import sk.uniza.fri.nacitavanie.Hrana;
import sk.uniza.fri.nacitavanie.Siet;
import sk.uniza.fri.nacitavanie.TypVrchola;
import sk.uniza.fri.nacitavanie.Vrchol;
import sk.uniza.fri.sweep.Cluster;
import sk.uniza.fri.sweep.ClusterFirst;
import sk.uniza.fri.sweep.LabelAlgoritmus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class IOACanvas extends JLayeredPane {
    private boolean canAddNew;
    private boolean canConnect;
    private boolean canDelete;
    private boolean canDisconnect;
    private boolean hranyAutomaticky;
    private boolean vykresliSweep;
    private ArrayList<Cluster> klastre;
    private Cursor cursor;
    private Siet siet;
    ClusterFirst clusterFirst;
    private HashMap<Integer,Point> vrcholy = new HashMap<>();
    private HashMap<Integer, Hrana> hrany = new HashMap<>();
    private Vrchol prvyVrcholNaSpojenie = null;
    private GrafickyEditor grafickyEditor;

    public IOACanvas(boolean pCanAddNew, boolean pCanConnect, Siet pSiet, GrafickyEditor pGrafickyEditor) {
        this.canAddNew = pCanAddNew;
        this.canConnect = pCanConnect;
        this.siet = pSiet;
        grafickyEditor = pGrafickyEditor;
        this.obnovPlatnoZoSiete();
        this.cursor = new Cursor(Cursor.HAND_CURSOR);
        this.klastre = new ArrayList<>();
        this.setCursor(cursor);
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(600, 600));
        this.setLayout(null);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (canAddNew) {
                    Point bodVrchola = e.getPoint();

                    Vrchol pridanyVrchol = new Vrchol(siet.getIdNaNajnovsiAIteruj(), e.getPoint().x, e.getPoint().y, 0, TypVrchola.NESPECIFIKOVANY);
                    pridanyVrchol.setPlatno(IOACanvas.this);
                    IOACanvas.this.vrcholy.put(pridanyVrchol.getId(), bodVrchola);
                    siet.pridajVrchol(pridanyVrchol);
                    IOACanvas.this.add(pridanyVrchol, JLayeredPane.PALETTE_LAYER);
                    LabelAlgoritmus labelAlgoritmus = new LabelAlgoritmus(siet.getVrcholy());
                    grafickyEditor.setSuvislyLabel(labelAlgoritmus.jeSpojity());
                    repaint();
                }
            }
        });
    }

    public int sweepMozny(double kapacita) {
        return this.siet.sweepMozny(kapacita);

    }

    public boolean isVykresliSweep() {
        return this.vykresliSweep;
    }

    public void setVykresliSweep(boolean vykresliSweep) {
        this.vykresliSweep = vykresliSweep;
    }


    public void obnovPlatnoZoSiete() {
        this.removeAll();
        this.vrcholy.clear();
        this.hrany.clear();

        for (Hrana hrana : siet.getHrany().values()) {
            hrana.setPlatno(this);
            this.hrany.put(hrana.getIdHrany(), hrana);
            this.add(hrana, JLayeredPane.DEFAULT_LAYER);
        }

        for (Vrchol vrchol : siet.getVrcholy().values()) {
            Point bod = new Point((int) vrchol.getSurX(), (int) vrchol.getSurY());

            vrchol.setPlatno(this);
            this.vrcholy.put(vrchol.getId(), bod);
            this.add(vrchol, JLayeredPane.PALETTE_LAYER);

        }

        LabelAlgoritmus labelAlgoritmus = new LabelAlgoritmus(siet.getVrcholy());
        grafickyEditor.setSuvislyLabel(labelAlgoritmus.jeSpojity());

        this.revalidate();
        this.repaint();
    }




    public void setCanAddNew(boolean newStatus) {
        this.canAddNew = newStatus;
    }

    public void setCanConnect(boolean newStatus) {
        this.canConnect = newStatus;
    }

    public boolean isCanDelete() {
        return this.canDelete;
    }

    public void setHranyAutomaticky(boolean hranyAutomaticky) {
        this.hranyAutomaticky = hranyAutomaticky;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public boolean isCanDisconnect() {
        return this.canDisconnect;
    }

    public void setCanDisconnect(boolean canDisconnect) {
        this.canDisconnect = canDisconnect;
    }

    public void vypocitajAutomatickeVzdialenosti(double kapacitaVozidla) {
        this.clusterFirst = new ClusterFirst(siet.getVrcholy(), siet.getIDStrediska(), kapacitaVozidla);
        clusterFirst.vyratajEklidoveVzdialenosti();
    }

    public void vrcholKliknuty(Vrchol kliknutyVrchol) {
        System.out.println("Klikol si na vrchol: " + kliknutyVrchol.toString());


        if (canConnect) {
            if (prvyVrcholNaSpojenie == null) {
                prvyVrcholNaSpojenie = kliknutyVrchol;
                System.out.println("Prvý vrchol vybraný: " + prvyVrcholNaSpojenie.getId());
            } else {
                Vrchol druhyVrchol = kliknutyVrchol;
                System.out.println("Druhý vrchol vybraný: " + druhyVrchol.getId());

                Point start = new Point((int) prvyVrcholNaSpojenie.getSurX(), (int) prvyVrcholNaSpojenie.getSurY());
                Point end = new Point((int) druhyVrchol.getSurX(), (int) druhyVrchol.getSurY());

                Hrana novaHrana = new Hrana(prvyVrcholNaSpojenie.getId(), druhyVrchol.getId(), start, end, 0.0);
                novaHrana.setIdHrany(IOACanvas.this.siet.getIdNHranyaNajnovsiAIteruj());
                novaHrana.setPlatno(this);
                add(novaHrana);
                novaHrana.repaint();
                siet.spojHranou(prvyVrcholNaSpojenie.getId(), druhyVrchol.getId(), novaHrana, 0);
                repaint();
                LabelAlgoritmus labelAlgoritmus = new LabelAlgoritmus(siet.getVrcholy());
                grafickyEditor.setSuvislyLabel(labelAlgoritmus.jeSpojity());

                prvyVrcholNaSpojenie = null;
            }
        } else if (canDelete) {

            kliknutyVrchol.printVrchol();
            var vychadzajuceHrany = kliknutyVrchol.getSpojenie();

            for (Hrana hrana : vychadzajuceHrany) {
                var hranyDruheho = IOACanvas.this.siet.getVrcholy().get(hrana.getIdDo()).getSpojenie();
                for (Hrana hrana1 : hranyDruheho) {
                    if (hrana1.getIdDo() == kliknutyVrchol.getId()) {
                        IOACanvas.this.remove(hrana1);
                        IOACanvas.this.siet.odstranHranu(hrana1);
                    }
                }
                IOACanvas.this.siet.getVrcholy().get(hrana.getIdDo()).odstranHranuSIdVrchola(hrana.getIdZ());
                IOACanvas.this.siet.odstranHranu(hrana);

                IOACanvas.this.remove(hrana);
            }

            IOACanvas.this.remove(kliknutyVrchol);
            siet.odstranVrchol(kliknutyVrchol);
            IOACanvas.this.vrcholy.remove(kliknutyVrchol.getId());
            IOACanvas.this.repaint();
            LabelAlgoritmus labelAlgoritmus = new LabelAlgoritmus(siet.getVrcholy());
            grafickyEditor.setSuvislyLabel(labelAlgoritmus.jeSpojity());

        } else {
            System.out.println("Klikol si na vrchol: " + kliknutyVrchol.getId());
        }


    }

    public void vypocitajSweep(double kapacitaStrediska) {
        clusterFirst = new ClusterFirst(siet.getVrcholy(), siet.getIDStrediska(), kapacitaStrediska);
        System.out.println(siet.getVrcholy().toString());
        this.klastre = clusterFirst.sweep(siet.getIDStrediska());
        if (klastre != null) {
            vykresliSweep = true;
        }
        this.repaint();

    }

    public void hranaKliknuta(Hrana hrana) {
        if (canDisconnect) {

            var hranyPrveho = IOACanvas.this.siet.getVrcholy().get(hrana.getIdZ()).getSpojenie();
            var hranyDruheho = IOACanvas.this.siet.getVrcholy().get(hrana.getIdDo()).getSpojenie();

            IOACanvas.this.siet.getVrcholy().get(hrana.getIdDo()).odstranHranuSIdVrchola(hrana.getIdZ());
            IOACanvas.this.siet.getVrcholy().get(hrana.getIdZ()).odstranHranuSIdVrchola(hrana.getIdDo());

            IOACanvas.this.siet.odstranHranu(hrana);
            IOACanvas.this.remove(hrana);


            for (int i = 0; i < hranyPrveho.size(); i++) {
                if (hranyPrveho.get(i).getIdDo() == hrana.getIdDo() && hranyPrveho.get(i).getIdZ() == hrana.getIdZ()) {

                    IOACanvas.this.siet.odstranHranu(hranyPrveho.get(i));
                    IOACanvas.this.remove(hranyPrveho.get(i));
                }
            }

            for (int i = 0; i < hranyDruheho.size(); i++) {
                if (hranyDruheho.get(i).getIdDo() == hrana.getIdDo() && hranyDruheho.get(i).getIdZ() == hrana.getIdZ()) {
                    IOACanvas.this.siet.odstranHranu(hranyDruheho.get(i));
                    IOACanvas.this.remove(hranyDruheho.get(i));
                }
            }

            LabelAlgoritmus labelAlgoritmus = new LabelAlgoritmus(siet.getVrcholy());
            grafickyEditor.setSuvislyLabel(labelAlgoritmus.jeSpojity());

            IOACanvas.this.repaint();
        }
    }


    public boolean isHranyAutomaticky() {
        return this.hranyAutomaticky;
    }

    public Siet getSiet() {
        return this.siet;
    }

    public void vycistiPlatno() {
        var vrchlyHashMap = this.siet.getVrcholy();

        for (Integer i : vrchlyHashMap.keySet()) {
            this.remove(vrchlyHashMap.get(i));
        }

        var hranyHashMap = this.siet.getHrany();

        for (Integer i : hranyHashMap.keySet()) {
            this.remove(hranyHashMap.get(i));
        }

        this.siet.vycistiSiet();
        vrcholy.clear();

        hrany.clear();
        LabelAlgoritmus labelAlgoritmus = new LabelAlgoritmus(siet.getVrcholy());
        grafickyEditor.setSuvislyLabel(labelAlgoritmus.jeSpojity());

        repaint();
    }


    public void nakresliSipku(Graphics2D g2, int x1, int y1, int x2, int y2) {
        g2.setStroke(new BasicStroke(4));
        g2.drawLine(x1, y1, x2, y2);

        double phi = Math.toRadians(20);
        int barb = 20;

        double dx = x2 - x1;
        double dy = y2 - y1;
        double theta = Math.atan2(dy, dx);

        double x, y;
        x = x2 - barb * Math.cos(theta + phi);
        y = y2 - barb * Math.sin(theta + phi);
        g2.drawLine(x2, y2, (int)x, (int)y);

        x = x2 - barb * Math.cos(theta - phi);
        y = y2 - barb * Math.sin(theta - phi);
        g2.drawLine(x2, y2, (int)x, (int)y);
    }




    @Override
    protected void paintComponent(Graphics g) {
        Hrana predoslaHrana = null;
        for (Component component : this.getComponents()) {
            if (component instanceof Hrana) {
                if (!vykresliSweep) {
                    if (!(predoslaHrana != null && ((Hrana) component).jeOpacna(predoslaHrana))) {
                        ((Hrana) component).nakresliText(g);
                    }
                    ((Hrana) component).setVykresliHranu(true);
                } else {
                    ((Hrana) component).setVykresliHranu(false);
                }
                predoslaHrana = (Hrana) component;
            } else if (component instanceof Vrchol) {
                ((Vrchol) component).nakresliText(g);
            }
        }

        if (vykresliSweep) {
            int farbaIter = 0;
            HashMap<Integer, Color> farbyKalstra = new HashMap<>();
            int cisloKlastra = 1;

            for (Cluster cluster : klastre) {
                Color farba = new Color(farbaIter % 255, (int)(farbaIter * 0.5) % 255, Math.abs(255 - farbaIter) % 255);
                farbyKalstra.put(cisloKlastra, farba);
                var cesta = cluster.getCesta();
                for (int i = 0; i < cesta.size() - 1; i++) {
                    System.out.println("kresli sa sipka");
                    g.setColor(farba);
                    nakresliSipku((Graphics2D) g.create(), (int)this.vrcholy.get(cesta.get(i)).getX(), (int)this.vrcholy.get(cesta.get(i)).getY(),
                            (int)this.vrcholy.get(cesta.get(i + 1)).getX(), (int)this.vrcholy.get(cesta.get(i + 1)).getY());
                }
                cisloKlastra++;
                farbaIter += 100;
            }

            int legendaX = this.getWidth() - 80;
            int legendaY = 20;

            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.setColor(Color.BLACK);
            g.drawString("Legenda:", legendaX, legendaY);
            legendaY += 15;

            for (Map.Entry<Integer, Color> entry : farbyKalstra.entrySet()) {
                int klasterId = entry.getKey();
                Color farba = entry.getValue();

                g.setColor(farba);
                g.fillRect(legendaX, legendaY, 15, 15);

                g.setColor(Color.BLACK);
                g.drawRect(legendaX, legendaY, 15, 15);
                g.drawString("Klaster " + klasterId, legendaX + 20, legendaY + 12);

                legendaY += 20;
            }
        }
        g.setColor(Color.BLACK);

        super.paintComponent(g);

    }
}