package predmety.ochrana;

/**
 * Trieda predmetu ochraňujúca pred útokom. S používaním rastie jeho sila do určitej výšky.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public class KuzloOchrany implements Ochrana {

    private int sila;

    /**
     * Bezparametrický konštruktor, nastavuje počiatočnú silu.
     */
    public KuzloOchrany() {
        this.sila = 5;
    }

    /**
     * Vráti názov predmetu - ochranného.
     *
     * @return kuzlo
     */
    @Override
    public String getNazov() {
        return "kuzlo";
    }

    /**
     * Použije ochranný predmet. Keďže ide o kúzelný predmet, nezničí sa a pri použití zvýši jeho ochrannú silu, ale len do 10.
     *
     * @return použiteľnosť
     */
    @Override
    public boolean pouzi() {
        if (this.sila < 10) {
            this.sila++;
        }
        return true;
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
     * Vráti či je ochranný predmet kúzlo ochrany kúzelný - true.
     *
     * @return kúzelnosť
     */
    @Override
    public boolean jeKuzelna() {
        return true;
    }
}
