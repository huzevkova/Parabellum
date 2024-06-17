package predmety.ochrana;

/**
 * Trieda predmetu ochraňujúca pred útokom. S používaním jeho sila klesá po určitú hodnotu.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public class Stit implements Ochrana {

    private int sila;

    /**
     * Bezparametrický konštruktor, nastavuje počiatočnú silu.
     */
    public Stit() {
        this.sila = 10;
    }

    /**
     * Vráti názov predmetu - ochranného.
     *
     * @return stit
     */
    @Override
    public String getNazov() {
        return "stit";
    }

    /**
     * Vráti silu ochranného predmetu.
     *
     * @return sila
     */
    @Override
    public int silaOchrany() {
        return this.sila;
    }

    /**
     * Použije ochranný predmet. Nezničí sa, ale pri použití zníži jeho ochrannú silu, až do 5.
     *
     * @return použiteľnosť
     */
    @Override
    public boolean pouzi() {
        if (this.sila > 5) {
            this.sila--;
        }
        return true;
    }

    /**
     * Vráti či je ochranný predmet štít kúzelný - false.
     *
     * @return kúzelnosť
     */
    @Override
    public boolean jeKuzelna() {
        return false;
    }

}
