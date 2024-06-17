package predmety.energia;

/**
 * Trieda predmetu dopĺňajúceho energiu - liek, s konečnou silou 20.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public class Liek implements Energia {

    private static final int SILA = 20;

    /**
     * Bezparametrický prázdny konštruktor.
     */
    public Liek() {
    }

    /**
     * Vráti akú silu uzdravenia má predmet.
     *
     * @return sila uzdravenia
     */
    @Override
    public int silaUzdravenia() {
        return Liek.SILA;
    }

    /**
     * Vráti názov predmetu dopĺňajúceho energiu.
     *
     * @return liek
     */
    @Override
    public String getNazov() {
        return "liek";
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
}
