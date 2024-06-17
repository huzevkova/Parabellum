package predmety.energia;

/**
 * Trieda predmetu dopĺňajúceho energiu - jablko, s konečnou silou 30.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public class Jablko implements Energia {

    private static final int SILA = 30;

    /**
     * Bezparametrický prázdny konštruktor.
     */
    public Jablko() {
    }

    /**
     * Vráti názov predmetu dopĺňajúceho energiu.
     *
     * @return jablko
     */
    @Override
    public String getNazov() {
        return "jablko";
    }

    /**
     * Použije predmet, a keďže sa dá použiť len jeden krát, vráti false.
     *
     * @return použiteľnosť
     */
    @Override
    public boolean pouzi() {
        return false;
    }

    /**
     * Vráti akú silu uzdravenia má predmet.
     *
     * @return sila uzdravenia
     */
    @Override
    public int silaUzdravenia() {
        return Jablko.SILA;
    }
}
