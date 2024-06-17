package suboj;

import postavy.Hrac;
import postavy.nepriatelia.Nepriatel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

/**
 * Trieda ktorá grafický zobrazí boj, t.j. bojovú plochu. Komponenty možno pridávať, aktualizovať skóre a zavrieť okno.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public class BojovaPlocha {
    private JFrame frame;
    private JPanel hlavnyPanel;
    private JLabel hracZivot;
    private JLabel nepriatelZivot;

    /**
     * Parametrický konštruktor, vytvorí okno, nastaví základné paramtre a pridá začiatočné skóre - životy hráča a nepriateľa,
     * aby ich mohol neskôr aktualizovať.
     *
     * @param hrac hráč
     * @param nepriatel nepriateľ
     */
    public BojovaPlocha(Hrac hrac, Nepriatel nepriatel) {
        this.frame = new JFrame();

        //pre účely testovania a prezentovania sa bude okno dať zatvoriť, pričom sa spustí časovač plochy,
        //v skutočnej hre by sa to nedalo, hráč by musel buď vyhrať alebo prehrať
        //this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                hrac.spustiCasovacPlochy();
            }
        });

        this.frame.setResizable(false);
        this.hlavnyPanel = new JPanel();
        this.hlavnyPanel.setLayout(null);
        this.hlavnyPanel.setPreferredSize(new Dimension(750, 500));

        this.hracZivot = new JLabel("Zivoty: " + hrac.getZivoty());
        this.pridajKomponent(this.hracZivot, null, 100, 40, 120, 40);
        this.nepriatelZivot = new JLabel("Zivoty: " + nepriatel.getZivoty());
        this.pridajKomponent(this.nepriatelZivot, null, 560, 40, 120, 40);

    }

    /**
     * Zobrazí plochu aj s pozadím.
     */
    public void zobrazPlochu() {
        JLabel pozadie = new JLabel();
        pozadie.setIcon(new ImageIcon("zdroje/prostredie/fantasy.jpg"));
        pozadie.setBounds(0, 0, 750, 500);

        this.hlavnyPanel.add(pozadie);
        this.frame.add(this.hlavnyPanel);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    /**
     * Pridá komponent a nastaví mu všetky zadané parametre.
     *
     * @param komponent pridávaný komponent
     * @param obrazok obrázok ktorý má mať
     * @param x x-ová súradnica
     * @param y y-ová súradnica
     * @param sirka šírka komponentu
     * @param vyska výška komponentu
     */
    public void pridajKomponent(JComponent komponent, String obrazok, int x, int y, int sirka, int vyska) {
        ((JLabel)komponent).setIcon(new ImageIcon(obrazok));
        komponent.setBounds(x, y, sirka, vyska);
        komponent.setFont(new Font("Times Roman", Font.ITALIC, 20));
        this.hlavnyPanel.add(komponent);
    }

    /**
     * Pridá už nachystaný komponent.
     *
     * @param komponent pridávaný komponent
     */
    public void pridajKomponent(JComponent komponent) {
        this.hlavnyPanel.add(komponent);
    }

    /**
     * Aktializuje štítky s životmi.
     *
     * @param hrac hráč
     * @param nepriatel nepriateľ
     */
    public void update(Hrac hrac, Nepriatel nepriatel) {
        this.hracZivot.setText("Zivoty: " + hrac.getZivoty());
        this.nepriatelZivot.setText("Zivoty: " + nepriatel.getZivoty());
    }

    /**
     * Zavrie okno.
     */
    public void zavri() {
        this.frame.dispose();
    }

}
