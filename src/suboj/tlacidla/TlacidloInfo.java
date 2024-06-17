package suboj.tlacidla;

import ovladace.OvladacMys;
import postavy.Hrac;
import suboj.BojoveMenu;

import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

/**
 * Trieda tlačidla "i", info, ktoré zobrazí krátky popis nepriateľa.
 * Potomok triedy Tlacidlo.
 *
 * @author Bianka S. Húževková
 * @version 2.0.0
 */
public class TlacidloInfo extends Tlacidlo {

    /**
     * Parametrický konštruktor, nastaví atribúty, názov nastaví na null.
     *
     * @param bojoveMenu bojove menu, ktorého je súčasťou
     * @param hrac       hráč na ktorého sa vzťahuje
     * @param ovladac    ovládač myši, ktorým sa ovláda
     */
    public TlacidloInfo(BojoveMenu bojoveMenu, Hrac hrac, OvladacMys ovladac) {
        super(bojoveMenu, hrac, null, ovladac);
    }

    /**
     * Nachystá obrázok a pozície tlačidla, využije metódu predka.
     */
    @Override
    public void nachystaj() {
        super.nachystaj();
        this.setText(null);
        this.setIcon(new ImageIcon("zdroje/i.png"));
        this.setBounds(720, 0, 30, 30);
    }

    /**
     * Na klik zobrazí správu s popisom nepriateľa.
     */
    @Override
    public void klik() {
        JOptionPane.showMessageDialog(null, super.getBojoveMenu().getNepriatel().dajPopis());
    }
}