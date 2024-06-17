package svet;

import postavy.Postava;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

/**
 * Trieda bloku prechodnej a nezničiteľnej cesty na mape, na ktorú sa môžu postaviť nepriatelia, implementujúca interface Blok.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public class Cesta implements Blok {
    private JLabel cesta;
    private Postava postava;
    private boolean prechodnost;

    /**
     * Parametrický konštruktor vytvára obraz cesty a inicializuje vlastnosti cesty.
     *
     * @param obrazokNazov cesta k obrázku cesty
     */
    public Cesta(String obrazokNazov) {
        this.cesta = new JLabel();
        this.cesta.setIcon(new ImageIcon(obrazokNazov));
        this.prechodnost = true;
    }

    /**
     * Vráti obraz cesty.
     *
     * @return grafické zobrazenie cesty
     */
    @Override
    public JLabel getObraz() {
        return this.cesta;
    }

    /**
     * Vráti prechodnosť prekážky.
     *
     * @return prechodnosť
     */
    @Override
    public boolean getPrechodnost() {
        return this.prechodnost;
    }

    /**
     * Vráti zničiteľnosť prekážky - false.
     *
     * @return zničiteľnosť
     */
    @Override
    public boolean getZnicitelnost() {
        return false;
    }

    /**
     * Na cestu pridá postavu, stane sa tak neprechodnou.
     *
     * @param postava postava ktorú cheme pridať
     */
    public void pridajPostavu(Postava postava) {
        this.postava = postava;
        this.postava.getObraz().setVisible(true);
        this.prechodnost = false;
    }

    /**
     * Z cesty odoberie postavu, ktorá tam bola, stane sa tak prechodnou.
     */
    public void odoberPostavu() {
        this.postava = null;
        this.prechodnost = true;
    }

    /**
     * Vráti postavu ktorá je na ceste.
     *
     * @return postava na ceste
     */
    public Postava getPostava() {
        return this.postava;
    }

}
