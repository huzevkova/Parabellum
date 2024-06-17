package utoky;

import postavy.nepriatelia.Nepriatel;

/**
 * Trieda reprezentujúca konkrétny, čarovný útok nepriateľa. Potomok triedy Utok.
 *
 * @author Bianka s. Húževková
 * @version 1.0.0
 */
public class CarovnyUtok extends Utok {

    /**
     * Parametrický konštruktor, využívajúci konštruktor predka. Nastaví potrebné parametre a čarovnosť na true.
     *
     * @param nepriatel nepriateľ ktorý útok používa
     * @param nazov krátky názov útoku
     * @param sila sila útoku
     */
    public CarovnyUtok(Nepriatel nepriatel, String nazov, int sila) {
        super(nepriatel, nazov, sila, true);
    }
}
