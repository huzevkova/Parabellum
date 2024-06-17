package postavy.nepriatelia;

import postavy.Postava;

/**
 * Interface nepriateľov - rozšírenie interface Postava.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public interface Nepriatel extends Postava {

    /**
     * Metóda útoku.
     * @return true ak sa môže pokračovat v hre, false ak niekto prehral
     */
    boolean zautoc();

    /**
     * Ukončenie boja.
     */
    void koniecBoja();

    /**
     * Vykoná posun nepriateľa.
     */
    void posunSa();

    /**
     * Vráti či je nepriateľ odolný proti kúzeným zbraniam.
     *
     * @return odolnosť proti kúzlam
     */
    boolean odolnyProtiKuzlam();

    /**
     * Vráti či je nepriateľ odolný proti fyzickým zbraniam.
     *
     * @return odolnosť proti fyzickým útokom
     */
    boolean odolnyProtiFyzickymUtokom();

}
