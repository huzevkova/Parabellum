package suboj.tlacidla;

import postavy.nepriatelia.Nepriatel;
import predmety.zbrane.Zbran;
import ovladace.OvladacMys;
import postavy.Hrac;
import suboj.BojoveMenu;

import javax.swing.JOptionPane;

/**
 * Trieda konkrétnej možnosti výberu zbrane, potomok Tlačidla.
 *
 * @author Bianka S. Húževková
 * @version 2.0.0
 */
public class TlacidloZbran extends Tlacidlo {
    private Zbran zbran;

    /**
     * Parametrický konštruktor. Využije konštruktor predka.
     *
     * @param bojoveMenu bojove menu, ktorého je súčasťou
     * @param hrac hráč na ktorého sa vzťahuje
     * @param zbran zbraň ktorú reprezentuje
     * @param ovladac ovládač myši, ktorým sa ovláda
     */
    public TlacidloZbran(BojoveMenu bojoveMenu, Hrac hrac, Zbran zbran, OvladacMys ovladac) {
        super(bojoveMenu, hrac, zbran.getNazov(), ovladac);
        this.zbran = zbran;
    }

    /**
     * Na klik použije zbraň aodoberie nepriateľovi životy. Ak životy nepriateľa klesli pod 0, ukončí súboj.
     * Využije aj metódu predka, keďže ide o ťah.
     */
    @Override
    public void klik() {
        JOptionPane.showMessageDialog(null, "Rozhodol si sa zaútočiť zbraňou: " + super.getNazov());
        int silaUtoku = this.prepocitajSiluUtoku(this.zbran.silaNicenia(super.getHrac()), super.getBojoveMenu().getNepriatel());
        if (!this.zbran.pouzi()) {
            super.getHrac().getInventar().odoberPredmet(this.zbran);
            JOptionPane.showMessageDialog(null, "Zbran: " + super.getNazov() + " sa opotrebovala a zničila používaním.");
        }
        if (!super.getBojoveMenu().getNepriatel().zmenZivoty(silaUtoku)) {
            String sprava = "Porazil si nepriateľa!";
            if (super.getHrac().zvysUroven()) {
                sprava += " Zvýšila sa ti úroveň.";
            }
            super.getHrac().spustiCasovacPlochy();
            JOptionPane.showMessageDialog(null, sprava);
            super.getBojoveMenu().getNepriatel().koniecBoja();
            super.getBojoveMenu().getSuboj().koniec(super.getHrac());
        } else {
            super.getBojoveMenu().getSuboj().tah();
        }
    }

    /**
     * Zobrazí na tlačidle silu zbrane, ktorú reprezentuje.
     */
    @Override
    public void enter() {
        String s = "" + this.zbran.silaNicenia(super.getHrac());
        this.setText(s);
    }

    /**
     * Podľa použitej zbrane a typu nepriateľa prepočíta silu útoku - zmenší alebo zväčš 0,2-krát.
     *
     * @param sila sila zbrane ako takej
     * @param nepriatel nepriateľ proti ktorému bude použitá
     * @return nová sila útoku
     */
    private int prepocitajSiluUtoku(int sila, Nepriatel nepriatel) {
        if (this.zbran.jeKuzelna()) {
            if (nepriatel.odolnyProtiKuzlam()) {
                return (int)(sila * 0.8);
            } else {
                return (int)(sila * 1.2);
            }
        } else {
            if (nepriatel.odolnyProtiFyzickymUtokom()) {
                return (int)(sila * 0.8);
            } else {
                return (int)(sila * 1.2);
            }
        }
    }

}
