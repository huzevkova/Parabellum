package suboj.tlacidla;

import ovladace.OvladacMys;
import postavy.Hrac;
import suboj.BojoveMenu;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;

/**
 * Trieda, potomok triedy JButton, tlačidlo použité v bojovom menu, reagujúce na klik.
 *
 * @author Bianka S. Húževková
 * @version 2.0.0
 */
public abstract class Tlacidlo extends JButton {

    private String nazov;
    private Hrac hrac;
    private OvladacMys ovladac;
    private BojoveMenu bojoveMenu;

    /**
     * Parametrický konštruktor, nastaví atribúty.
     *
     * @param bojoveMenu bojove menu, ktorého je súčasťou
     * @param hrac       hráč na ktorého sa vzťahuje
     * @param nazov      názov na tlačidle
     * @param ovladac    ovládač myši, ktorým sa ovláda
     */
    public Tlacidlo(BojoveMenu bojoveMenu, Hrac hrac, String nazov, OvladacMys ovladac) {
        super(nazov);
        this.nazov = nazov;
        this.hrac = hrac;
        this.ovladac = ovladac;
        this.bojoveMenu = bojoveMenu;
    }

    /**
     * Vracia názov - nápis tlačidla.
     *
     * @return názov tlačidla
     */
    public String getNazov() {
        return this.nazov;
    }

    /**
     * Vráti hráča pre ktorého je tlačidlo určené
     *
     * @return hráč tlačidla
     */
    public Hrac getHrac() {
        return this.hrac;
    }

    /**
     * Vráti bojové menu, ktorého je súčasťou.
     *
     * @return bojové menu tlačidla
     */
    public BojoveMenu getBojoveMenu() {
        return this.bojoveMenu;
    }

    /**
     * Abstraktná metóda, vykoná akciu keď sa na tlačidlo klikne.
     */
    public abstract void klik();

    /**
     * Nastaví potrebné vlastnosti tlačidla (ovládač, farba, písmo).
     */
    public void nachystaj() {
        this.addMouseListener(this.ovladac);
        this.setFocusable(false);
        this.setBackground(Color.black);
        this.setForeground(Color.white);
        this.setFont(new Font("Times Roman", Font.BOLD, 30));
    }

    /**
     * Vyznačí tlačidlo pri vstupe.
     */
    public void enter() {
        this.setFocusable(true);
    }

    /**
     * Vráti pôvodný text na tlačidlo po jeho opustení.
     */
    public void exit() {
        if (this.nazov != null) {
            String s = "" + this.nazov;
            this.setText(s);
        }
    }
}
