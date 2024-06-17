package suboj.tlacidla;

import ovladace.OvladacMys;
import postavy.Hrac;
import predmety.energia.Energia;
import suboj.BojoveMenu;

import javax.swing.JOptionPane;

/**
 * Trieda konkrétnej možnosti výberu z predmetov energie, potomok Tlačidla.
 *
 * @author Bianka S. Húževková
 * @version 2.0.0
 */
public class TlacidloEnergia extends Tlacidlo {

    private Energia energia;

    /**
     * Parametrický konštruktor. Využije konštruktor predka.
     *
     * @param bojoveMenu bojove menu, ktorého je súčasťou
     * @param hrac hráč na ktorého sa vzťahuje
     * @param energia predmet energie ktorý tlačidlo reprezentuje
     * @param ovladac ovládač myši, ktorým sa ovláda
     */
    public TlacidloEnergia(BojoveMenu bojoveMenu, Hrac hrac, Energia energia, OvladacMys ovladac) {
        super(bojoveMenu, hrac, energia.getNazov(), ovladac);
        this.energia = energia;
    }

    /**
     * Na klik použije predmet energie a uzraví hráča. Využije aj metódu predka, keďže ide o ťah.
     */
    @Override
    public void klik() {
        JOptionPane.showMessageDialog(null, "Rozhodol si sa uzdraviť: " + super.getNazov());
        int silaUzdravenia = this.energia.silaUzdravenia();
        this.energia.pouzi();
        super.getHrac().getInventar().odoberPredmet(this.energia);
        super.getHrac().zmenZivoty(-silaUzdravenia);
        super.getBojoveMenu().getSuboj().tah();
    }

    /**
     * Zobrazí na tlačidle silu uzdrenia predmetu energie, ktorý reprezentuje.
     */
    @Override
    public void enter() {
        String s = "" + this.energia.silaUzdravenia();
        this.setText(s);
    }
}
