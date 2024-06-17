package suboj.tlacidla;

import ovladace.OvladacMys;
import postavy.Hrac;
import suboj.BojoveMenu;

import javax.swing.ImageIcon;

/**
 * Trieda tlačidla "x", ktoré dá bojové menu do začiatočného stavu, keď používateľ zmení názor pri výbere.
 * Potomok triedy Tlacidlo.
 *
 * @author Bianka S. Húževková
 * @version 2.0.0
 */
public class TlacidloX extends Tlacidlo {

    /**
     * Parametrický konštruktor, nastaví atribúty, názov nastaví na null.
     *
     * @param bojoveMenu bojove menu, ktorého je súčasťou
     * @param hrac       hráč na ktorého sa vzťahuje
     * @param ovladac    ovládač myši, ktorým sa ovláda
     */
    public TlacidloX(BojoveMenu bojoveMenu, Hrac hrac, OvladacMys ovladac) {
        super(bojoveMenu, hrac, null, ovladac);
    }

    /**
     * Na klik dá bojové menu do začiatočného stavu.
     */
    @Override
    public void klik() {
        super.getBojoveMenu().dajDoZaciatocnehoStavu();
    }

    /**
     * Nachystá obrázok a pozície tlačidla, využije metódu predka.
     */
    @Override
    public void nachystaj() {
        super.nachystaj();
        this.setText(null);
        this.setIcon(new ImageIcon("zdroje/x.png"));
        this.setBounds(670, 400, 30, 30);
    }
}
