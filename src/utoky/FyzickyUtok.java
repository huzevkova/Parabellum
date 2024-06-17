package utoky;

import postavy.nepriatelia.Nepriatel;

/**
 * Trieda reprezentujúca konkrétny, fyzický útok nepriateľa. Potomok triedy Utok.
 *
 * @author Bianka s. Húževková
 * @version 1.0.0
 */
public class FyzickyUtok extends Utok {

    /**
     * Parametrický konštruktor, využívajúci konštruktor predka. Nastaví potrebné parametre a čarovnosť na false.
     *
     * @param nepriatel nepriateľ ktorý útok používa
     * @param nazov krátky názov útoku
     * @param sila sila útoku
     */
    public FyzickyUtok(Nepriatel nepriatel, String nazov, int sila) {
        super(nepriatel, nazov, sila, false);
    }
}
