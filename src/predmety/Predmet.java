package predmety;

import java.io.Serializable;

/**
 * Interface pre všetky predmety.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public interface Predmet extends Serializable {
    /**
     * Vráti názov predmetu.
     *
     * @return názov predmetu
     */
    String getNazov();

    /**
     * Použije predmet - pri zbrani a ochrane sa zmení opotrebovanie alebo sila, pri energii sa použije kompletne.
     *
     * @return či sa ešte dá predmet použiť
     */
    boolean pouzi();

}
