package minihry;

import hlavnaHra.Hra;

import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

/**
 * Trieda zastrešujúca základné metódy a parametre každej minihry. Vypisuje info o spustení minihry, ovláda časovač, znovaspustenie a koniec.
 *
 * @author: Bianka S. Húževková
 * @version 3.0  (5.11.2022)
 */
public abstract class Minihra implements ActionListener, Serializable {

    private Platno platno;
    private Timer timer;
    private Hra hra;

    /**
     * Parametrický konštruktor, vypíše správu, nastaví časovač a hlavnú hru, ktorej je minihra súčasťou.
     *
     * @param hra hlavná hra
     * @param casovac časovač minihry
     * @param sprava správa o minihre
     */
    public Minihra(Hra hra, int casovac, String sprava) {
        JOptionPane.showMessageDialog(null, "Bonusová minihra!");
        JOptionPane.showMessageDialog(null, sprava, "Start hry", JOptionPane.INFORMATION_MESSAGE);
        this.timer = new Timer(casovac, this);
        this.hra = hra;
    }

    /**
     * Abstraktná metóda, začne novú hru.
     */
    public abstract void novaMinihra();

    /**
     * Nastavý minihre plátno.
     *
     * @param platno plátno minihry
     */
    public void setPlatno(Platno platno) {
        this.platno = platno;
    }

    /**
     * Vráti časovač minihry.
     *
     * @return časovač
     */
    public Timer getTimer() {
        return this.timer;
    }

    /**
     * V prípade, že minihru treba spustiť znova, zastaví časovač a vyčistí plátno.
     */
    public void znova() {
        this.timer.stop();
        this.platno.odoberVsetko();
        this.platno.zavriOkno();
    }

    /**
     * Ukončí minihru zastavením časovača, zavrie okno, a spustí v hlavnej hre ďalší level.
     */
    public void koniec() {
        this.timer.stop();
        this.platno.zavriOkno();
        this.hra.dalsiLevel();
    }

    /**
     * Abstraktná metóda, ktorá vykoná potrebné akcie.
     *
     * @param e udalosť na spracovanie
     */
    public abstract void vykonajAkciu(ActionEvent e);

    /**
     * Prekrytá metóda, spustí sa pri udalosti (tu konkrétne časovač), zalová vykonanie akcie.
     *
     * @param e udalosť na spracovanie
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.vykonajAkciu(e);
    }
}
