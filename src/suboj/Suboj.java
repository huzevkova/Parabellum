package suboj;

import ovladace.OvladacMys;
import postavy.Hrac;
import postavy.Postava;
import postavy.nepriatelia.Nepriatel;
import suboj.tlacidla.TlacidloInfo;
import suboj.tlacidla.TlacidloX;
import svet.Plocha;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.io.IOException;

/**
 * Trieda ovládajúca súboj medzi hráčom a nepriateľom. Vytvorí plochu, sleduje kto je na ťahu, vykonáva ťahy nepriateľa a čaká na koniec súboja.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public class Suboj {
    private Hrac hrac;
    private Nepriatel nepriatel;
    private Postava postavaNaTahu;
    private BojovaPlocha bojovaPlocha;
    private BojoveMenu bojoveMenu;
    private Plocha plocha;

    /**
     * Parametrický konštruktor, nastaví hráča a nepriateľa a pridá do bojového okna potrebné časti ako menu a obrázky.
     *
     * @param hrac hráč ktorý hrá
     * @param nepriatel nepriateľ proti ktorému sa bojuje
     * @param plocha plocha z ktorej postavy prišli
     */
    public Suboj(Hrac hrac, Nepriatel nepriatel, Plocha plocha) {
        this.hrac = hrac;
        this.nepriatel = nepriatel;
        this.plocha = plocha;

        OvladacMys ovladac = new OvladacMys();

        this.bojovaPlocha = new BojovaPlocha(hrac, nepriatel);
        JLabel hrdina = new JLabel();
        String obrazok;
        if (this.hrac.getPohlavie() == 0) {
            obrazok = "zdroje/postavy/hrdina.png";
        } else {
            obrazok = "zdroje/postavy/hrdinka.png";
        }
        this.bojovaPlocha.pridajKomponent(hrdina, obrazok, 60, 80, 200, 300);
        this.bojovaPlocha.pridajKomponent(new JLabel("Uroven: " + this.hrac.getUroven()), null, 100, 0, 120, 40);
        this.bojovaPlocha.pridajKomponent(new JLabel(), String.format("zdroje/postavy/%s.png", nepriatel.getNazov()), 480, 80, 300, 300);
        this.bojovaPlocha.pridajKomponent(new JLabel(this.nepriatel.getNazov().substring(0, 1).toUpperCase() + this.nepriatel.getNazov().substring(1)), null, 560, 0, 120, 40);

        this.bojoveMenu = new BojoveMenu(this.hrac, this, ovladac);
        this.bojovaPlocha.pridajKomponent(this.bojoveMenu);

        TlacidloX tlacidloX = new TlacidloX(this.bojoveMenu, this.hrac, ovladac);
        tlacidloX.nachystaj();
        this.bojovaPlocha.pridajKomponent(tlacidloX);
        TlacidloInfo tlacidloInfo = new TlacidloInfo(this.bojoveMenu, this.hrac, ovladac);
        tlacidloInfo.nachystaj();
        this.bojovaPlocha.pridajKomponent(tlacidloInfo);

        this.bojovaPlocha.zobrazPlochu();
        this.postavaNaTahu = hrac;
    }

    /**
     * Vráti nepriateľa proti ktorému sa bojuje.
     *
     * @return nepriateľ
     */
    public Nepriatel getNepriatel() {
        return this.nepriatel;
    }

    /**
     * Na striedačku mení kto je práve na ťahu.
     */
    public void zmenPostavaNaTahu() {
        if (this.postavaNaTahu.equals(this.hrac)) {
            this.postavaNaTahu = this.nepriatel;
        } else {
            this.postavaNaTahu = this.hrac;
        }
        this.aktualizuj();
    }

    /**
     * Vykoná ťah nepriateľa a skontroluje či niekto nevyhral. Aktualizuje plochu, a ak treba, ukončí súboj.
     */
    public void tah() {
        this.zmenPostavaNaTahu();
        this.bojoveMenu.schovaj();
        if (this.postavaNaTahu.equals(this.nepriatel)) {
            if (!this.nepriatel.zautoc()) {
                this.koniec(this.nepriatel);
            }
            this.zmenPostavaNaTahu();
        }
        this.bojoveMenu.dajDoZaciatocnehoStavu();
        this.aktualizuj();
    }

    /**
     * Aktualizuje bojovú plochu zavolaním metódy update v bojovej ploche.
     */
    public void aktualizuj() {
        this.bojovaPlocha.update(this.hrac, this.nepriatel);
    }

    /**
     * Ukončí súboj. Ak vyhral nepriateľ, informuje hráča o prehre a nastaví plochu z ktorej sa prišlo znova na začiatok.
     *
     * @param vyherca výherca súboja
     */
    public void koniec(Postava vyherca) {
        this.bojovaPlocha.zavri();
        if (!vyherca.equals(this.hrac)) {
            this.hrac.zastavCasovacPlochy();
            JOptionPane.showMessageDialog(null, "Prehral si, skús znova.");
            try {
                this.plocha.znova();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
