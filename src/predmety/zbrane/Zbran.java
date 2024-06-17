package predmety.zbrane;

import postavy.Hrac;
import predmety.Predmet;

/**
 * Interface pre predmety na útočenie - zbrane.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public interface Zbran extends Predmet {
    /**
     * Vráti akú veľkú silu má zbraň.
     *
     * @param hrac hráč, ktorý zbraň používa
     * @return sila zbrane
     */
    int silaNicenia(Hrac hrac);

    /**
     * Vráti či je zbraň kúzelná alebo nie
     *
     * @return kúzelnosť
     */
    boolean jeKuzelna();
}
