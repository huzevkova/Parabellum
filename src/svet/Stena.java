package svet;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

/**
 * Trieda bloku neprechodnej a nezničiteľnej steny na mape, implementujúca interface Blok.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public class Stena implements Blok {
    private JLabel stena;

    /**
     * Parametrický konštruktor vytvára obraz steny a inicializuje vlastnosti steny.
     *
     * @param obrazokNazov cesta k obrázku steny
     */
    public Stena(String obrazokNazov) {
        this.stena = new JLabel();
        this.stena.setIcon(new ImageIcon(obrazokNazov));
    }

    /**
     * Vráti obraz steny.
     *
     * @return grafické zobrazenie steny
     */
    @Override
    public JLabel getObraz() {
        return this.stena;
    }

    /**
     * Vráti prechodnosť steny - false.
     *
     * @return prechodnosť
     */
    @Override
    public boolean getPrechodnost() {
        return false;
    }

    /**
     * Vráti zničiteľnosť steny - false.
     *
     * @return zničiteľnosť
     */
    @Override
    public boolean getZnicitelnost() {
        return false;
    }

}
