package suboj;

import predmety.energia.Energia;
import predmety.ochrana.Ochrana;
import predmety.zbrane.Zbran;
import ovladace.OvladacMys;
import postavy.Hrac;
import postavy.nepriatelia.Nepriatel;
import suboj.tlacidla.TlacidloEnergia;
import suboj.tlacidla.TlacidloZbran;
import suboj.tlacidla.TlacidloOchrana;
import suboj.tlacidla.TlacidloVyber;

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.util.List;

/**
 * Trieda ktorá má na starosti bojové menu s tlačidlami pre hráča. Možno meniť jeho výber, či dať ho do začiatočného stavu.
 *
 * @author Bianka S. Húževková
 * @version 2.0.0
 */
public class BojoveMenu extends JPanel {

    private Hrac hrac;
    private OvladacMys ovladac;
    private Suboj suboj;

    /**
     * Paramtrický konštruktor, uloží si hráča a súboj ktorého je súčasťou.
     *
     * @param hrac hráč ktorý hrá
     * @param suboj súboj v ktorom je
     */
    public BojoveMenu(Hrac hrac, Suboj suboj, OvladacMys ovladac) {
        this.hrac = hrac;
        this.suboj = suboj;
        this.ovladac = ovladac;
        this.dajDoZaciatocnehoStavu();
    }

    /**
     * Vráti súboj ktorého je súčasťou.
     *
     * @return súboj
     */
    public Suboj getSuboj() {
        return this.suboj;
    }

    /**
     * Vráti nepriateľa s ktorým sa bojuje.
     *
     * @return nepriateľ
     */
    public Nepriatel getNepriatel() {
        return this.suboj.getNepriatel();
    }

    /**
     * Dá menu do začiatočného stavu - 1 riadok x 3 stĺpce tlačidiel s možnosťami útok, obrana alebo energia.
     */
    public void dajDoZaciatocnehoStavu() {
        this.removeAll();
        this.setBounds(70, 400, 600, 90);
        this.setLayout(new GridLayout(1, 3));

        this.addMouseListener(this.ovladac);

        TlacidloVyber utok = new TlacidloVyber(this, this.hrac, "UTOK", this.ovladac);
        utok.nachystaj();
        this.add(utok);

        TlacidloVyber obrana = new TlacidloVyber(this, this.hrac, "OBRANA", this.ovladac);
        obrana.nachystaj();
        this.add(obrana);

        TlacidloVyber uzdravenie = new TlacidloVyber(this, this.hrac, "ENERGIA", this.ovladac);
        uzdravenie.nachystaj();
        this.add(uzdravenie);

        this.setVisible(true);
        this.revalidate();
    }

    /**
     * Schová menu.
     */
    public void schovaj() {
        this.setVisible(false);
        this.setEnabled(false);
        this.removeAll();
        this.revalidate();
    }

    /**
     * Na klik sa zmení a zobrazí novú množinu možností, ak množine nie je prázdna.
     *
     * @param mnozina množina ktorá sa má zobraziť
     */
    public void klik(List mnozina) {
        this.removeAll();
        this.setLayout(new GridLayout(1, mnozina.size()));
        if (mnozina.size() != 0) {
            if (mnozina.get(0) instanceof Zbran) {
                for (int i = 0; i < mnozina.size(); i++) {
                    TlacidloZbran zbran = new TlacidloZbran(this, this.hrac, (Zbran)mnozina.get(i), this.ovladac);
                    zbran.nachystaj();
                    this.add(zbran);
                }
            } else if (mnozina.get(0) instanceof Ochrana) {
                for (int i = 0; i < mnozina.size(); i++) {
                    TlacidloOchrana ochrana = new TlacidloOchrana(this, this.hrac, (Ochrana)mnozina.get(i), this.ovladac);
                    ochrana.nachystaj();
                    this.add(ochrana);
                }
            } else if (mnozina.get(0) instanceof Energia) {
                for (int i = 0; i < mnozina.size(); i++) {
                    TlacidloEnergia energia = new TlacidloEnergia(this, this.hrac, (Energia)mnozina.get(i), this.ovladac);
                    energia.nachystaj();
                    this.add(energia);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Predmety z tejto kategórie sa ti už minuli!");
            this.dajDoZaciatocnehoStavu();
        }
        this.revalidate();
    }

}
