package suboj.tlacidla;

import ovladace.OvladacMys;
import postavy.Hrac;
import predmety.ochrana.Ochrana;
import suboj.BojoveMenu;

import javax.swing.JOptionPane;

/**
 * Trieda konkrétnej možnosti výberu ochrany, potomok Tlačidla.
 *
 * @author Bianka S. Húževková
 * @version 2.0.0
 */
public class TlacidloOchrana extends Tlacidlo {
    private Ochrana ochrana;

    /**
     * Parametrický konštruktor. Využije konštruktor predka.
     *
     * @param bojoveMenu bojove menu, ktorého je súčasťou
     * @param hrac hráč na ktorého sa vzťahuje
     * @param ochrana predmet ochrany ktorý reprezentuje
     * @param ovladac ovládač myši, ktorým sa ovláda
     */
    public TlacidloOchrana(BojoveMenu bojoveMenu, Hrac hrac, Ochrana ochrana, OvladacMys ovladac) {
        super(bojoveMenu, hrac, ochrana.getNazov(), ovladac);
        this.ochrana = ochrana;
    }

    /**
     * Na klik použije ochranný predmet a pri nasledujúcom ťahu nepriateľa odoberie menej alebo žiadne životy hráčovi.
     * Využije aj metódu predka, keďže ide o ťah.
     */
    @Override
    public void klik() {
        JOptionPane.showMessageDialog(null, "Rozhodol si sa chrániť pred útokom: " + super.getNazov());
        Hrac hrac = super.getHrac();
        hrac.nastavOchranu(this.ochrana);
        int silaOchrany = this.ochrana.silaOchrany();
        this.ochrana.pouzi();
        int stareZivoty = hrac.getZivoty();

        super.getBojoveMenu().getSuboj().tah();
        int rozdiel = stareZivoty - hrac.getZivoty();
        if (rozdiel < silaOchrany) {
            hrac.zmenZivoty(-rozdiel);
        } else {
            if (!hrac.zmenZivoty(-silaOchrany)) {
                super.getBojoveMenu().getSuboj().koniec(super.getBojoveMenu().getNepriatel());
            }
        }
        hrac.nastavOchranu(null);

        super.getBojoveMenu().getSuboj().aktualizuj();
    }

    /**
     * Zobrazí na tlačidle silu ochrany, ktorú reprezentuje.
     */
    @Override
    public void enter() {
        String s = "" + this.ochrana.silaOchrany();
        this.setText(s);
    }
}
