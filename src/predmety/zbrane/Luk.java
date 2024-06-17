package predmety.zbrane;

import postavy.Hrac;

/**
 * Trieda konkrétnej zbrane luk. S používaním sa opotrebováva do zničenia (7 použití). Sila zostáva konštantná.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public class Luk implements Zbran {
    private static final int SILA = 5;
    private int opotrebovanie;

    /**
     * Bezparametrický konštruktor. Nastaví opotrebovanie na 0.
     */
    public Luk() {
        this.opotrebovanie = 0;
    }

    /**
     * Vráti názov predmetu - zbrane.
     *
     * @return luk
     */
    @Override
    public String getNazov() {
        return "luk";
    }

    /**
     * Vráti silu ničenia, prepočítanú podľa úrovne hráča.
     *
     * @param hrac hráč, ktorý zbraň používa
     * @return sila zbrane
     */
    @Override
    public int silaNicenia(Hrac hrac) {
        return hrac.getUroven() * Luk.SILA;
    }

    /**
     * Použije zbraň, zväčšuje jej opotrebovanie. Vracia či sa dá ešte použiť - keď opotrebovanie dosiahne viac ako 7, už sa nedá použiť.
     *
     * @return použiteľnosť
     */
    @Override
    public boolean pouzi() {
        this.opotrebovanie++;
        if (this.opotrebovanie > 7) {
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
