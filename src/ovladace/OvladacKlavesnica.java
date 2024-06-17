package ovladace;

import postavy.Hrac;
import javax.swing.JComponent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.io.Serializable;

/**
 * Trieda, ktorá má na starosti ovládanie cez klávesnicu.
 *
 * @author Bianka S. Húževková
 * @version 2.0.0
 */
public class OvladacKlavesnica implements Serializable {
    private JComponent komponent;
    private int velkostPosunu;
    private Hrac hrac;
    private Action posunHore;
    private Action posunDole;
    private Action posunVpravo;
    private Action posunVlavo;
    private Action vykonajAkciu;
    private Action zobrazInfo;

    /**
     * Parametrický konštruktor inicializujúci pohyby alebo iné akcie a k nim prislúchajúce klávesy do mapy.
     *
     * @param komponent komponent ktorý sa bude ovládať
     * @param hrac hráč ktorého sa ovlládač týka
     */
    public OvladacKlavesnica(JComponent komponent, Hrac hrac) {
        this.komponent = komponent;
        this.velkostPosunu = 70;
        this.hrac = hrac;

        this.posunHore = new PosunHore();
        this.posunDole = new PosunDole();
        this.posunVlavo = new PosunVlavo();
        this.posunVpravo = new PosunVpravo();
        this.vykonajAkciu = new VykonajAkciu();
        this.zobrazInfo = new ZobrazInfo();

        this.komponent.getInputMap().put(KeyStroke.getKeyStroke("UP"), "hore");
        this.komponent.getActionMap().put("hore", this.posunHore);

        this.komponent.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "vlavo");
        this.komponent.getActionMap().put("vlavo", this.posunVlavo);

        this.komponent.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "dole");
        this.komponent.getActionMap().put("dole", this.posunDole);

        this.komponent.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "vpravo");
        this.komponent.getActionMap().put("vpravo", this.posunVpravo);

        this.komponent.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "akcia");
        this.komponent.getActionMap().put("akcia", this.vykonajAkciu);

        this.komponent.getInputMap().put(KeyStroke.getKeyStroke("I"), "info");
        this.komponent.getActionMap().put("info", this.zobrazInfo);
    }

    /**
     * Vnorená trieda ovládajúca posun smerom hore - šípka hore.
     * Obsahuje len prekrytú metódu actionPerformed(ActionEvent e).
     */
    public class PosunHore extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (OvladacKlavesnica.this.hrac.posunSa(-1, 0)) {
                OvladacKlavesnica.this.komponent.setLocation(OvladacKlavesnica.this.komponent.getX(), OvladacKlavesnica.this.komponent.getY() - OvladacKlavesnica.this.velkostPosunu);
            }
        }
    }

    /**
     * Vnorená trieda ovládajúca posun smerom dole - šípka dole.
     * Obsahuje len prekrytú metódu actionPerformed(ActionEvent e).
     */
    public class PosunDole extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (OvladacKlavesnica.this.hrac.posunSa(1, 0)) {
                OvladacKlavesnica.this.komponent.setLocation(OvladacKlavesnica.this.komponent.getX(), OvladacKlavesnica.this.komponent.getY() + OvladacKlavesnica.this.velkostPosunu);
            }
        }
    }

    /**
     * Vnorená trieda ovládajúca posun smerom vlavo - šípka vlavo.
     * Obsahuje len prekrytú metódu actionPerformed(ActionEvent e).
     */
    public class PosunVlavo extends AbstractAction {    
        @Override
        public void actionPerformed(ActionEvent e) {
            if (OvladacKlavesnica.this.hrac.posunSa(0, -1)) {
                OvladacKlavesnica.this.komponent.setLocation(OvladacKlavesnica.this.komponent.getX() - OvladacKlavesnica.this.velkostPosunu, OvladacKlavesnica.this.komponent.getY());
            }
        }
    }

    /**
     * Vnorená trieda ovládajúca posun smerom vpravo - šípka vpravo.
     * Obsahuje len prekrytú metódu actionPerformed(ActionEvent e).
     */
    public class PosunVpravo extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (OvladacKlavesnica.this.hrac.posunSa(0, 1)) {
                OvladacKlavesnica.this.komponent.setLocation(OvladacKlavesnica.this.komponent.getX() + OvladacKlavesnica.this.velkostPosunu, OvladacKlavesnica.this.komponent.getY());
            }
        }
    }

    /**
     * Vnorená trieda ovládajúca činnosť hráča - medzerník.
     * Obsahuje len prekrytú metódu actionPerformed(ActionEvent e).
     */
    public class VykonajAkciu extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            OvladacKlavesnica.this.hrac.akcia();
        }
    }

    /**
     * Vnorená trieda ovládajúca zobrazenie informácii o postave - "I".
     * Obsahuje len prekrytú metódu actionPerformed(ActionEvent e).
     */
    public class ZobrazInfo extends AbstractAction {    
        @Override
        public void actionPerformed(ActionEvent e) {
            String info = OvladacKlavesnica.this.hrac.dajPopis();
            info += "\n" + OvladacKlavesnica.this.hrac.getInventar().toString();
            JOptionPane.showMessageDialog(null, info);
        }
    }
}
