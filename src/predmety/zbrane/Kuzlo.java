package predmety.zbrane;

import postavy.Hrac;

/**
 * Trieda konkrétnej zbrane kuzlo. S používaním sa neopotrebováva ale rastie na sile.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public class Kuzlo implements Zbran {
    private int sila;
    private Hrac hrac;

    /**
     * Bezparametrický konštruktor. Nastaví začiatočnú silu na 3.
     */
    public Kuzlo() {
        this.sila = 3;
    }

    /**
     * Vráti názov predmetu - zbrane.
     *
     * @return kuzlo
     */
    @Override
    public String getNazov() {
        return "kuzlo";
    }

    /**
     * Vráti silu ničenia, prepočítanú podľa úrovne hráča.
     *
     * @param hrac hráč, ktorý zbraň používa
     * @return sila zbrane
     */
    @Override
    public int silaNicenia(Hrac hrac) {
        this.hrac = hrac;
        return hrac.getUroven() * this.sila;
    }

    /**
     * Použije zbraň, zväčšuje jej silu až do 10, potom za jej použitie odoberie hráčovi 1 život. Vracia, či sa dá ešte použiť - stále true,
     * keďže kúzlo sa neopotrebáva.
     *
     * @return použiteľnosť
     */
    @Override
    public boolean pouzi() {
        if (this.sila > 9) {
            this.hrac.zmenZivoty(1);
        } else {
            this.sila++;
        }
        return true;
    }

    /**
     * Vracia či je zbraň kúzelná - true.
     *
     * @return kúzelnosť
     */
    @Override
    public boolean jeKuzelna() {
        return true;
    }
}
