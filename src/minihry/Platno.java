package minihry;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Platno na ktoré sa budú minihry vykreslovať a do ktorého sa budú pridávať objekty minihier.
 *
 * @author: Bianka S. Húževková
 * @version 2.0.0
 */
public class Platno {
    private JFrame frame;
    private JPanel panel;
    private ArrayList<Object> objekty;

    /**
     * Vytvorí nový frame a panel so zadanou šírkou a výškou. Layout nastavý na null, všetko pôjde cez súradnice.
     * Zobrazí sa na stred plochy počítača.
     *
     * @param sirka sirka plochy
     * @param vyska vyska plochy
     */
    public Platno(int sirka, int vyska) {
        this.objekty = new ArrayList<Object>();
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize(new Dimension(sirka, vyska));
        this.frame.setLayout(null);

        this.panel = new JPanel();
        this.panel.setBounds(0, 0, sirka, vyska);
        this.panel.setLayout(null);
        this.frame.add(this.panel);
        this.dajDoPlatna();
    }

    /**
     * Nastaví farbu pozadia.
     *
     * @param color farba pozadia ktorú má nastaviť
     */
    public void setPozadie(Color color) {
        this.panel.setBackground(color);
        this.frame.setLocationRelativeTo(null);
    }

    /**
     * Pridá objekt do pola objektov ktoré chceme na platne zobraziť na dané súradnice.
     *
     * @param objekt akýkoľvek objekt ktorý má pridať
     */
    public void pridajObjekt(Object objekt) {
        this.objekty.add(objekt);
        this.dajDoPlatna();
    }

    /**
     * Nahradí objekt v poli novým objektom.
     *
     * @param staryObjekt objekt v poli ktorý má nahradiť
     * @param novyObjekt novy objekt
     */
    public void nahradObjekt(Object staryObjekt, Object novyObjekt) {
        int i = -1;
        for (Object objekt : this.objekty) {
            if (objekt.equals(staryObjekt)) {
                i = this.objekty.indexOf(objekt);
            }
        }
        try {
            this.objekty.set(i, novyObjekt);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
    }

    /**
     * Vloží do plátna všetky objekty z pola objektov.
     */
    public void dajDoPlatna() {
        if (!this.objekty.isEmpty()) {
            for (Object o : this.objekty) {
                Component objekt = (Component)o;
                int x = objekt.getX();
                int y = objekt.getY();
                objekt.setBounds(x, y, objekt.getWidth(), objekt.getHeight());
                objekt.requestFocus();
                this.panel.add(objekt);
            }
        }
        this.frame.setLocationRelativeTo(null);
        this.frame.pack();
        this.frame.setVisible(true);
        this.frame.validate();
    }

    /**
     * Odoberie z plátna všetky objekty.
     */
    public void odoberVsetko() {
        this.objekty.clear();
        this.panel.removeAll();
        this.frame.removeAll();
    }

    /**
     * Zavrie celé okno / plátno.
     */
    public void zavriOkno() {
        this.frame.dispose();
    }

    /**
     * Vráti obsah plochy - content pane.
     *
     * @return Container
     */
    public Container getObsahPlochy() {
        return this.frame.getContentPane();
    }
}