package postavy;

import javax.swing.JLabel;
import java.io.Serializable;

/**
 * Interface pre všetky postavy.
 *
 * @author Bianka S. Húževková
 * @version 2.0.0
 */
public interface Postava extends Serializable {
    /**
     * Vráti grafický obraz postavy.
     *
     * @return obrázok postavy
     */
    JLabel getObraz();

    /**
     * Vráti názov postavy.
     *
     * @return názov postavy
     */
    String getNazov();

    /**
     * Vráti riadok na ktorom sa postava nachádza.
     *
     * @return riadok
     */
    int getRiadok();

    /**
     * Vráti stĺpec na ktorom sa postava nachádza.
     *
     * @return stĺpec
     */
    int getStlpec();

    /**
     * Vráti úroveň postavy.
     *
     * @return úroveň
     */
    int getUroven();

    /**
     * Vráti popis postavy ako reťazec.
     *
     * @return popis
     */
    String dajPopis();

    /**
     * Vráti počet životov postavy.
     *
     * @return životy
     */
    int getZivoty();

    /**
     * Zmení počet životov, ak klesne na nulu vráti false, ako indikátor, že postava zomrela.
     *
     * @param pocetZivotov počet ktorý chceme odobrať / pripočítať (vtedy pridať mínus)
     * @return či postava ešte žije
     */
    boolean zmenZivoty(int pocetZivotov);

    /**
     * Postava vykoná akciu.
     */
    void akcia();
}
