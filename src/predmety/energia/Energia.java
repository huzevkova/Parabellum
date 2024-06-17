package predmety.energia;

import predmety.Predmet;

/**
 * Interface pre predmety dopĺňajúce energiu
 */
public interface Energia extends Predmet {

    /**
     * Vracia uzdravovaciu silu = silu dodania energie.
     * @return hodnota sily uzdravenia
     */
    int silaUzdravenia();
}
