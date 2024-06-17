package svet;

import javax.swing.JLabel;
import java.io.Serializable;

/**
 * Interface jedného bloku mapy.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public interface Blok extends Serializable {
    /**
     * Vráti grafickú verziu bloku.
     * @return obraz bloku
     */
    JLabel getObraz();

    /**
     * Vráti či sa dá prejsť cez blok - true = prechodný, false = neprechodný, obsahuje nejakú prekážku.
     * @return prechodnosť
     */
    boolean getPrechodnost();

    /**
     * Vráti či je možné blok zničiť a urobiť ho prechodným - true = zničiteľný, false = nezničiteľný.
     * @return zničiteľnosť
     */
    boolean getZnicitelnost();
}
