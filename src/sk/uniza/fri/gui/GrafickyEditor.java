package sk.uniza.fri.gui;

import sk.uniza.fri.nacitavanie.Siet;
import sk.uniza.fri.sweep.LabelAlgoritmus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 12. 4. 2025 - 13:32
 *
 * @author matus
 */
public class GrafickyEditor extends JFrame {
    private JCheckBox pridajVrcholyCheckBox;
    private JCheckBox spajajHranamiCheckBox;
    private JButton ulozSietButton;
    private JCheckBox mazVrcholyCheckBox;
    private JCheckBox mazHranyCheckBox;
    private JButton ukonciEditovanieButton;
    private JPanel rootComponent;
    private JPanel platno;
    private JTextField textField1;
    private JTextField textField2;
    private JButton vycistiSietButton;
    private JRadioButton cenyHranSaPocitajuRadioButton;
    private JRadioButton cenyHranSaZadajuRadioButton;
    private JLabel suvislyLabel;
    private JButton vypocitajSweepAlgoritmusButton;
    private JTextField kapacitaTF;
    private IOACanvas ioaCanvas;
    private double kapacitaVozidla;


    public GrafickyEditor(Siet upravovanaSiet) {
        ioaCanvas = new IOACanvas(false, false, upravovanaSiet, this);
        ioaCanvas.setPreferredSize(new Dimension(500, 500));
        ButtonGroup skupina = new ButtonGroup();
        skupina.add(cenyHranSaPocitajuRadioButton);
        skupina.add(cenyHranSaZadajuRadioButton);
        kapacitaVozidla = 0;


        pridajVrcholyCheckBox.addActionListener(e -> {
            if (pridajVrcholyCheckBox.isSelected()) {

                spajajHranamiCheckBox.setSelected(false);
                mazVrcholyCheckBox.setSelected(false);
                mazHranyCheckBox.setSelected(false);

                GrafickyEditor.this.ioaCanvas.setCanConnect(false);
                GrafickyEditor.this.ioaCanvas.setCanAddNew(true);
                GrafickyEditor.this.ioaCanvas.setCanDisconnect(false);
                GrafickyEditor.this.ioaCanvas.setCanDelete(false);
            } else {
                GrafickyEditor.this.ioaCanvas.setCanAddNew(false);
            }
        });

        this.vypocitajSweepAlgoritmusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var clusteryArrayList = GrafickyEditor.this.ioaCanvas.vypocitajSweep(Double.parseDouble(kapacitaTF.getText()));
            }
        });

        this.cenyHranSaPocitajuRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ioaCanvas.setHranyAutomaticky(true);
                ioaCanvas.vypocitajAutomatickeVzdialenosti(kapacitaVozidla);
                LabelAlgoritmus labelAlgoritmus = new LabelAlgoritmus(ioaCanvas.getSiet().getVrcholy());
                GrafickyEditor.this.setSuvislyLabel(labelAlgoritmus.jeSpojity());
                ioaCanvas.repaint();
            }
        });

        this.cenyHranSaZadajuRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ioaCanvas.setHranyAutomaticky(false);
            }
        });

        this.vycistiSietButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ioaCanvas.vycistiPlatno();
            }
        });

        spajajHranamiCheckBox.addActionListener(e -> {
            if (spajajHranamiCheckBox.isSelected()) {

                pridajVrcholyCheckBox.setSelected(false);
                mazVrcholyCheckBox.setSelected(false);
                mazHranyCheckBox.setSelected(false);
                GrafickyEditor.this.ioaCanvas.setCanConnect(true);
                GrafickyEditor.this.ioaCanvas.setCanAddNew(false);
                GrafickyEditor.this.ioaCanvas.setCanDisconnect(false);
                GrafickyEditor.this.ioaCanvas.setCanDelete(false);
            } else {
                GrafickyEditor.this.ioaCanvas.setCanConnect(false);
            }
        });

        mazVrcholyCheckBox.addActionListener(e -> {
            if (mazVrcholyCheckBox.isSelected()) {

                pridajVrcholyCheckBox.setSelected(false);
                mazHranyCheckBox.setSelected(false);
                spajajHranamiCheckBox.setSelected(false);
                GrafickyEditor.this.ioaCanvas.setCanConnect(false);
                GrafickyEditor.this.ioaCanvas.setCanAddNew(false);
                GrafickyEditor.this.ioaCanvas.setCanDisconnect(false);
                GrafickyEditor.this.ioaCanvas.setCanDelete(true);
            } else {
                GrafickyEditor.this.ioaCanvas.setCanDelete(false);
            }
        });

        mazHranyCheckBox.addActionListener(e -> {
            if (mazHranyCheckBox.isSelected()) {
                pridajVrcholyCheckBox.setSelected(false);
                spajajHranamiCheckBox.setSelected(false);
                mazVrcholyCheckBox.setSelected(false);
                GrafickyEditor.this.ioaCanvas.setCanConnect(false);
                GrafickyEditor.this.ioaCanvas.setCanAddNew(false);
                GrafickyEditor.this.ioaCanvas.setCanDisconnect(true);
                GrafickyEditor.this.ioaCanvas.setCanDelete(false);
            } else {
                GrafickyEditor.this.ioaCanvas.setCanDisconnect(false);
            }
        });

        ulozSietButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GrafickyEditor.this.ioaCanvas.getSiet().uloz(GrafickyEditor.this.textField1.getText(), GrafickyEditor.this.textField2.getText());
            }
        });

        ukonciEditovanieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GrafickyEditor.this.dispose();
            }
        });

        platno.add(ioaCanvas);
        platno.revalidate();
        platno.repaint();

    }

    public void setSuvislyLabel(boolean jeSuvisly) {
        suvislyLabel.setText("Graf nie je súvislý");

        if (jeSuvisly) {
            suvislyLabel.setText("Graf je súvislý");
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootComponent = new JPanel();
        rootComponent.setLayout(new GridBagLayout());
        pridajVrcholyCheckBox = new JCheckBox();
        pridajVrcholyCheckBox.setText("pridaj vrcholy");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        rootComponent.add(pridajVrcholyCheckBox, gbc);
        platno = new JPanel();
        platno.setLayout(new GridBagLayout());
        platno.setMinimumSize(new Dimension(500, 500));
        platno.setPreferredSize(new Dimension(500, 500));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        rootComponent.add(platno, gbc);
        mazVrcholyCheckBox = new JCheckBox();
        mazVrcholyCheckBox.setText("maz vrcholy");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        rootComponent.add(mazVrcholyCheckBox, gbc);
        ulozSietButton = new JButton();
        ulozSietButton.setText("Uloz siet");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootComponent.add(ulozSietButton, gbc);
        ukonciEditovanieButton = new JButton();
        ukonciEditovanieButton.setText("Ukonci editovanie");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootComponent.add(ukonciEditovanieButton, gbc);
        textField2 = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootComponent.add(textField2, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("Subor kde sa ulozia hrany");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        rootComponent.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Subor kde sa ulozia vrcholy");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        rootComponent.add(label2, gbc);
        vycistiSietButton = new JButton();
        vycistiSietButton.setText("Vycisti siet");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootComponent.add(vycistiSietButton, gbc);
        mazHranyCheckBox = new JCheckBox();
        mazHranyCheckBox.setText("maz hrany");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        rootComponent.add(mazHranyCheckBox, gbc);
        spajajHranamiCheckBox = new JCheckBox();
        spajajHranamiCheckBox.setText("spajaj hranami");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        rootComponent.add(spajajHranamiCheckBox, gbc);
        cenyHranSaPocitajuRadioButton = new JRadioButton();
        cenyHranSaPocitajuRadioButton.setText("ceny hran sa pocitaju automatcky");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.WEST;
        rootComponent.add(cenyHranSaPocitajuRadioButton, gbc);
        cenyHranSaZadajuRadioButton = new JRadioButton();
        cenyHranSaZadajuRadioButton.setText("ceny hran sa zadaju manualne");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.WEST;
        rootComponent.add(cenyHranSaZadajuRadioButton, gbc);
        textField1 = new JTextField();
        textField1.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootComponent.add(textField1, gbc);
        suvislyLabel = new JLabel();
        suvislyLabel.setText("Graf nie je súvislý");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        rootComponent.add(suvislyLabel, gbc);
        vypocitajSweepAlgoritmusButton = new JButton();
        vypocitajSweepAlgoritmusButton.setText("Vypocitaj sweep algoritmus");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootComponent.add(vypocitajSweepAlgoritmusButton, gbc);
        kapacitaTF = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootComponent.add(kapacitaTF, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Nastav kapacitu vozidla pre sweep ");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        rootComponent.add(label3, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootComponent;
    }

}
