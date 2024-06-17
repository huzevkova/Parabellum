package suboj.tlacidla;

import ovladace.OvladacMys;
import postavy.Hrac;
import suboj.BojoveMenu;

import javax.swing.JOptionPane;

/**
 * Trieda konkrétnej možnosti výberu - útok, obrana, energia - potomok Tlačidla.
 *
 * @author Bianka S. Húževková
 * @version 2.0.0
 */
public class TlacidloVyber extends Tlacidlo {

    /**
     * Parametrický konštruktor. Využije konštruktor predka.
     *
     * @param bojoveMenu bojove menu, ktorého je súčasťou
     * @param hrac hráč na ktorého sa vzťahuje
     * @param nazov názov na tlačidle
     * @param ovladac ovládač myši, ktorým sa ovláda
     */
    public TlacidloVyber(BojoveMenu bojoveMenu, Hrac hrac, String nazov, OvladacMys ovladac) {
        super(bojoveMenu, hrac, nazov, ovladac);
    }

    /**
     * Na klik vytvorí podľa toho o aké tlačidlo išlo nové menu s možnosťami zbraní, predmetov obrany alebo uzdravujúcich predmetov.
     */
    @Override
    public void klik() {
        if (super.getNazov().equals("UTOK")) {
            if (super.getHrac().getInventar().getZbrane().size() != 0) {
                super.getBojoveMenu().klik(super.getHrac().getInventar().getZbrane());
            } else {
                int znova = JOptionPane.showConfirmDialog(null, "Už nemáš žiadnu zbraň! Bez nej nepriateľa neporazíš. Chceš začať odznova?", null, JOptionPane.YES_NO_OPTION);
                if (znova == 0) {
                    super.getBojoveMenu().getSuboj().koniec(super.getBojoveMenu().getNepriatel());
                }
            }
        } else if (super.getNazov().equals("OBRANA")) {
            super.getBojoveMenu().klik(super.getHrac().getInventar().getOchrannePredmety());
        } else if (super.getNazov().equals("ENERGIA")) {
            super.getBojoveMenu().klik(super.getHrac().getInventar().getPredmetyDoplnajuceEnergiu());
        }
    }
}
