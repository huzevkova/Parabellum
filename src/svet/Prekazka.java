package svet;

import predmety.Predmet;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

/**
 * Trieda bloku neprechodnej, ale zničiteľnej prekážky na mape, do ktorej sa dajú schovať predmety, implementujúca interface Blok.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public class Prekazka implements Blok {
    private JLabel prekazka;
    private ImageIcon znicenaPrekazka;
    private boolean prechodnost;
    private boolean znicitelnost;
    private Predmet schovanyPredmet;
    private boolean maPredmet;

    /**
     * Parametrický konštruktor vytvára obraz prekážky a inicializuje začiatočné vlastnosti prekážky.
     *
     * @param obrazokNazov cesta k obrázku prekážky
     * @param znicenaPrekazkaObrazok cesta k obrázku zničenej prekážky
     */
    public Prekazka(String obrazokNazov, String znicenaPrekazkaObrazok) {
        this.prekazka = new JLabel();
        this.prekazka.setIcon(new ImageIcon(obrazokNazov));
        this.znicenaPrekazka = new ImageIcon(znicenaPrekazkaObrazok);
        this.znicitelnost = true;
        this.prechodnost = false;
        this.maPredmet = false;
    }

    /**
     * Vráti obraz prekážky.
     *
     * @return grafické zobrazenie prekážky
     */
    @Override
    public JLabel getObraz() {
        return this.prekazka;
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
     * Vráti zničiteľnosť prekážky.
     *
     * @return zničiteľnosť
     */
    @Override
    public boolean getZnicitelnost() {
        return this.znicitelnost;
    }

    /**
     * Schová do vnútra prekážky predmet.
     *
     * @param predmet schovaný predmet
     */
    public void schovajPredmet(Predmet predmet) {
        this.schovanyPredmet = predmet;
        this.maPredmet = true;
    }

    /**
     * Vráti či je v prekážke schovaný predmet alebo nie.
     *
     * @return či má predmet
     */
    public boolean getMaPredmet() {
        return this.maPredmet;
    }

    /**
     * Vráti predmet schovaný v prekážke, vymaže ho zo seba, nastaví obraz cesty a prechodnosť.
     *
     * @return schovaný predmet
     */
    public Predmet zoberSchovanyPredmet() {
        this.prechodnost = true;
        this.prekazka.setIcon(this.znicenaPrekazka);
        Predmet predmet = this.schovanyPredmet;
        this.schovanyPredmet = null;
        this.maPredmet = false;
        return predmet;
    }

    /**
     * Zničí prekážku, ak schováva predmet zobrazí ho, inak zobrazí cestu.
     */
    public void znic() {
        if (this.maPredmet) {
            this.prekazka.setIcon(new ImageIcon("zdroje/predmety/" + this.schovanyPredmet.getNazov() + ".png"));
            this.znicitelnost = false;
        } else {
            this.prechodnost = true;
            this.prekazka.setIcon(this.znicenaPrekazka);
        }
    }

}
