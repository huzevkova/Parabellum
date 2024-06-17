package utoky;

import postavy.Hrac;
import postavy.nepriatelia.Nepriatel;

import java.io.Serializable;

/**
 * Abstraktná trieda reprezentujúca útok nepriateľa, s názvom a silou. Dá sa použiť proti hráčovi.
 *
 * @author Bianka s. Húževková
 * @version 1.0.0
 */
public abstract class Utok implements Serializable {
    private Nepriatel nepriatel;
    private String nazov;
    private int sila;
    private boolean carovny;

    /**
     * Parametrický konštruktor nastavujúci nepriateľa, ktorý bude útok používať, názov útoku, jeho silu a či je čarovný.
     *
     * @param nepriatel nepriateľ ktorý útok používa
     * @param nazov krátky názov útoku
     * @param sila sila útoku
     * @param carovny či je útok čarovný
     */
    public Utok(Nepriatel nepriatel, String nazov, int sila, boolean carovny) {
        this.nepriatel = nepriatel;
        this.nazov = nazov;
        this.sila = sila;
        this.carovny = carovny;
    }

    /**
     * Vráti názov útoku ako reťazec.
     *
     * @return názov
     */
    public String getNazov() {
        return this.nazov;
    }

    /**
     * Vráti či je útok čarovne založený alebo fyzicky založený (čarovný = true, fyzický = false).
     *
     * @return čarovnosť
     */
    public boolean carovny() {
        return this.carovny;
    }

    /**
     * Útok sa použije proti zadanému hráčovi.
     * Prepočíta silu útoku, ak má hráč na sebe ochranu, a podľa toho zmení (odoberie) hráčovi životy.
     *
     * @param hrac hráč na ktorého sa útočí
     * @return či hráč útok prežil
     */
    public boolean pouziUtok(Hrac hrac) {
        if (hrac.getOchrana() != null) {
            if (hrac.getOchrana().jeKuzelna() == this.carovny) {
                return hrac.zmenZivoty((int)(this.silaUtoku() * 0.9));
            } else {
                return hrac.zmenZivoty((int)(this.silaUtoku() * 1.1));
            }
        }
        return hrac.zmenZivoty(this.silaUtoku());
    }

    /**
     * Vypočíta a vráti finálnu silu útoku - jednotková sila útoku vynásobená úrovňou nepriateľa, ktorý ho použije.
     *
     * @return sila útoku
     */
    public int silaUtoku() {
        return this.sila * this.nepriatel.getUroven();
    }
}
