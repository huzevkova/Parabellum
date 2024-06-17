package svet;

import hlavnaHra.Hra;
import postavy.Hrac;
import postavy.Postava;
import postavy.nepriatelia.Nepriatel;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Trieda s hracou plochou, grafické zobrazenie komponentov hry.
 *
 * @author Bianka S. Húževková
 * @version 2.0.0
 */
public class Plocha implements Serializable {
    private JFrame frame;
    private JPanel hlavnyPanel;
    private Mapa mapa;
    private int velkostPolicka;
    private Hrac hrac;
    private Hra hra;
    private ArrayList<Nepriatel> nepriatelia;

    /**
     * Parametrický konštruktor plochy, inicializuje okno a vnútroný panel do ktorého sa budú pridávať komponenty. Nezobrazí plochu.
     *
     * @param velkost veľkosť okna
     * @param mapa mapa ktora bude v ploche
     */
    public Plocha(int velkost, Mapa mapa, Hra hra) {
        this.mapa = mapa;
        this.hra = hra;

        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Plocha.this.hra.uloz();
            }
        });

        this.hlavnyPanel = new JPanel();
        this.hlavnyPanel.setLayout(null);
        this.hlavnyPanel.setPreferredSize(new Dimension(velkost, velkost));

        this.velkostPolicka = velkost / mapa.getRozmer();
        this.nepriatelia = new ArrayList<>();
    }

    /**
     * Pridá do plochy komponent - JLabel - vytvorený z postavy. Ak ide o hráča, uloží si ho.
     *
     * @param postava postava ktorú chceme pridať
     */
    public void pridajPostavu(Postava postava) {
        postava.getObraz().setBounds(postava.getStlpec() * this.velkostPolicka, postava.getRiadok() * this.velkostPolicka, this.velkostPolicka, this.velkostPolicka);
        if (postava instanceof Nepriatel) {
            ((Cesta)this.mapa.getMapaBlokov()[postava.getRiadok()][postava.getStlpec()]).pridajPostavu(postava);
            this.nepriatelia.add((Nepriatel)postava);
        } else if (postava instanceof Hrac) {
            this.hrac = (Hrac)postava;
        }
        this.hlavnyPanel.add(postava.getObraz());
    }

    /**
     * Prídá do plochy komponent mapy.
     *
     * @param plochaMapa vytvorený obraz mapy
     */
    public void pridajMapu(JPanel plochaMapa) {
        plochaMapa.setBounds(0, 0, this.velkostPolicka * this.mapa.getRozmer(), this.velkostPolicka * this.mapa.getRozmer());
        this.hlavnyPanel.add(plochaMapa);
    }

    /**
     * Zobrazí plochu so všetkým čo je do nej pridané. A vypíše pokyny hry.
     */
    public void zobrazPlochu() {
        this.frame.add(this.hlavnyPanel);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
        JOptionPane.showMessageDialog(null, "Ovládanie:" +
                "\nPohyb: šípky hore, dole, vpravo, vľavo" +
                "\nZničenie prekážky/začatie boja: medzerník" +
                "\nInformácie o hráčovi: písmeno i" +
                "\nPri súboji daj pozor - rôzne zbrane majú proti rôznym nepriateľom inú (väčšiu alebo menšiu) silu, takže si radšej čítaj aj info o nepriateľoch.");

        //zabezpečí, že okno bude pri spustení navrchu
        this.frame.setAlwaysOnTop(true);
        this.frame.setAlwaysOnTop(false);
    }

    /**
     * Nájde voľné miesto (políčko s neobsadenou cestou) na mape.
     *
     * @return suradnice voľneho bodu
     */
    public Point volneMiesto() {
        Random rnd = new Random();
        int x = rnd.nextInt(this.mapa.getRozmer() - 3) + 3;
        int y = rnd.nextInt(this.mapa.getRozmer() - 3) + 3;
        while (!this.mapa.getMapaBlokov()[x][y].getPrechodnost() || !this.kontrolaOkolia(x, y)) {
            x = rnd.nextInt(this.mapa.getRozmer() - 3) + 3;
            y = rnd.nextInt(this.mapa.getRozmer() - 3) + 3;
        }
        return new Point(x, y);
    }

    /**
     * Vráti nemeniteľný zoznam nepriateľov na ploche.
     *
     * @return zoznam nepriateľov
     */
    public List<Nepriatel> getNepriatelia() {
        return Collections.unmodifiableList(this.nepriatelia);
    }

    /**
     * Zahodí terajšiu plochu a začne novú hru s rovnakými parametrami.
     */
    public void znova() throws IOException {
        this.frame.dispose();
        this.hra.novaHra(this.hrac.getNazov(), this.hrac.getPohlavie());
    }

    /**
     * Zahodí terajšiu plochu.
     */
    public void ukonci() {
        this.frame.dispose();
    }

    /**
     * Metóda, ktorá skontroluje či sa v okolí bodu na mape nenachádza iná postava.
     *
     * @param x x-ová súradnica
     * @param y y-ová súradnica
     * @return boolean
     */
    private boolean kontrolaOkolia(int x, int y) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (this.mapa.getMapaBlokov()[i][j] instanceof Cesta && ((Cesta)this.mapa.getMapaBlokov()[i][j]).getPostava() != null) {
                    return false;
                }
            }
        }
        return true;
    }
}
