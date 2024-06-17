package ovladace;

import suboj.tlacidla.Tlacidlo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

/**
 * Trieda, ktorá má na starosti ovládanie myšou v bojovom menu.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public class OvladacMys extends MouseAdapter implements Serializable {

    /**
     * Parametrický konštruktor, len na vytvorenie inštancie triedy.
     */
    public OvladacMys() {
    }

    /**
     * Prekrytá metóda, reagujúca na kliknutie myši. Vykoná metódu klik v danej triede tlačidla.
     *
     * @param e udalosť na spracovanie
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof Tlacidlo) {
            ((Tlacidlo)e.getSource()).klik();
        }
    }

    /**
     * Prekrytá metóda reagujúca na vstup myši do priestoru komponentu. Vykoná metódu enter v danej triede tlačidla.
     *
     * @param e udalosť na spracovanie
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof Tlacidlo) {
            ((Tlacidlo)e.getSource()).enter();
        }
    }

    /**
     * Prekrytá metóda reagujúca na odchod myši z priestoru komponentu. Vykoná metódu exit v danej triede tlačidla.
     *
     * @param e udalosť na spracovanie
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof Tlacidlo) {
            ((Tlacidlo)e.getSource()).exit();
        }
    }
}
