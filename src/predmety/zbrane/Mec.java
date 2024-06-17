package predmety.zbrane;

import postavy.Hrac;

/**
 * Trieda konkrétnej zbrane mec. S používaním sa opotrebováva do zničenia (5 použití). Sila zostáva konštantná.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public class Mec implements Zbran {
    private int opotrebovanie;
    private static final int SILA = 10;

    /**
     * Bezparametrický konštruktor. Nastaví opotrebovanie na 0.
     */
    public Mec() {
        this.opotrebovanie = 0;
    }

    /**
     * Vráti názov predmetu - zbrane.
     *
     * @return mec
     */
    @Override
    public String getNazov() {
        return "mec";
    }

    /**
     * Vráti silu ničenia, prepočítanú podľa úrovne hráča.
     *
     * @param hrac hráč, ktorý zbraň používa
     * @return sila zbrane
     */
    @Override
    public int silaNicenia(Hrac hrac) {
        return hrac.getUroven() * Mec.SILA;
    }

    /**
     * Použije zbraň, zväčšuje jej opotrebovanie. Vracia či sa dá ešte použiť - keď opotrebovanie dosiahne viac ako 5, už sa nedá použiť.
     *
     * @return použiteľnosť
     */
    @Override
    public boolean pouzi() {
        this.opotrebovanie++;
        if (this.opotrebovanie > 5) {
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
