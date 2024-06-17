package predmety.zbrane;

import postavy.Hrac;

/**
 * Trieda konkrétnej zbrane noz. S používaním sa opotrebováva do zničenia (4 použitia). Sila zostáva konštantná.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public class Noz implements Zbran {
    private static final int SILA = 2;
    private int opotrebovanie;

    /**
     * Bezparametrický konštruktor. Nastaví opotrebovanie na 0.
     */
    public Noz() {
        this.opotrebovanie = 0;
    }

    /**
     * Vráti názov predmetu - zbrane.
     *
     * @return noz
     */
    @Override
    public String getNazov() {
        return "noz";
    }

    /**
     * Vráti silu ničenia, prepočítanú podľa úrovne hráča.
     *
     * @param hrac hráč, ktorý zbraň používa
     * @return sila zbrane
     */
    @Override
    public int silaNicenia(Hrac hrac) {
        return hrac.getUroven() * Noz.SILA;
    }

    /**
     * Použije zbraň, zväčšuje jej opotrebovanie. Vracia či sa dá ešte použiť - keď opotrebovanie dosiahne viac ako 4, už sa nedá použiť.
     *
     * @return použiteľnosť
     */
    @Override
    public boolean pouzi() {
        this.opotrebovanie++;
        if (this.opotrebovanie > 4) {
            return false;
        }
        return true;
    }

    /**
     * Vracia či je zbraň kúzelná - false.
     *
     * @return kúzelnosť
     */
    @Override
    public boolean jeKuzelna() {
        return false;
    }
}
