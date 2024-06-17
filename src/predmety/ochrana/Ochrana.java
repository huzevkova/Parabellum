package predmety.ochrana;

import predmety.Predmet;

/**
 * Interface pre predmety ochraňujúce pred útokmi.
 */
public interface Ochrana extends Predmet {
    /**
     * Vracia silu ochrany.
     * @return hodnota sily ochrany
     */
    int silaOchrany();

    /**
     * Vracia či je ochrana kuzelna.
     * @return kuzelnosť alebo nie
     */
    boolean jeKuzelna();
}
