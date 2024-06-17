package hlavnaHra;

import minihry.Hadik;
import minihry.Minihra;
import minihry.Puzzle;
import postavy.Hrac;
import postavy.nepriatelia.Carodejnik;
import postavy.nepriatelia.Elf;
import postavy.nepriatelia.Skriatok;
import postavy.nepriatelia.Zoldnier;
import postavy.nepriatelia.Nepriatel;
import svet.Blok;
import svet.Mapa;
import svet.Plocha;
import svet.Prekazka;
import svet.Cesta;

import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Trieda so samotnou hrou. Spúšťa, ukončuje, kontroluje a ukladá.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public class Hra implements ActionListener, Serializable {
    private Plocha plocha;
    private Timer casovac;
    private int level;
    private Mapa mapa;
    private Hrac hrac;

    /**
     * Parametrický konštruktor. Na začiatku sa opýta na meno a pohlavie. Nastavý level a spustí novú hru.
     *
     */
    public Hra(int level) {
        String[] moznosti = {"chlapec", "dievca"};
        int pohlavie = JOptionPane.showOptionDialog(null, "Chceš hrať ako: ", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, moznosti, 0);
        String meno = JOptionPane.showInputDialog(null, "Vitaj v mini fantasy hre Parabellum! Zadaj meno svojho hrdinu: ");
        this.level = level;
        this.novaHra(meno, pohlavie);
    }

    /**
     * Inicializuje atribúty, vytvorí novú mapu a plochu, do ktorej mapu vloží, spolu so všetkými postavami. Spustí časovač.
     *
     * @param meno meno hráča
     * @param pohlavie pohlavie hráča
     */
    public void novaHra(String meno, int pohlavie) {
        if (this.casovac != null) {
            this.casovac.stop();
        }
        this.mapa = new Mapa(11, this.level);
        JPanel obrazMapy = this.mapa.vytvorObrazMapy();
        this.plocha = new Plocha(this.mapa.getRozmer() * 70, this.mapa, this);
        this.hrac = new Hrac(meno, this.mapa.getMapaBlokov(), pohlavie, this);
        this.plocha.pridajPostavu(this.hrac);

        //niektorý nepriatelia vo forme poznámok pre prezentačné a testovacie účely
        if (this.level == 1) {
            //this.plocha.pridajPostavu(new Skriatok(this.plocha.volneMiesto(), this.hrac, this.mapa.getMapaBlokov(), this.plocha));
            //this.plocha.pridajPostavu(new Zoldnier(this.plocha.volneMiesto(), this.hrac, this.mapa.getMapaBlokov(), this.plocha));
            this.plocha.pridajPostavu(new Skriatok(this.plocha.volneMiesto(), this.hrac, this.mapa.getMapaBlokov(), this.plocha));
            this.plocha.pridajPostavu(new Zoldnier(this.plocha.volneMiesto(), this.hrac, this.mapa.getMapaBlokov(), this.plocha));
        } else if (this.level == 2) {
            this.plocha.pridajPostavu(new Skriatok(this.plocha.volneMiesto(), this.hrac, this.mapa.getMapaBlokov(), this.plocha));
            this.plocha.pridajPostavu(new Zoldnier(this.plocha.volneMiesto(), this.hrac, this.mapa.getMapaBlokov(), this.plocha));
            this.plocha.pridajPostavu(new Zoldnier(this.plocha.volneMiesto(), this.hrac, this.mapa.getMapaBlokov(), this.plocha));
            this.plocha.pridajPostavu(new Elf(this.plocha.volneMiesto(), this.hrac, this.mapa.getMapaBlokov(), this.plocha));
            this.plocha.pridajPostavu(new Elf(this.plocha.volneMiesto(), this.hrac, this.mapa.getMapaBlokov(), this.plocha));
        } else if (this.level == 3) {
            this.plocha.pridajPostavu(new Zoldnier(this.plocha.volneMiesto(), this.hrac, this.mapa.getMapaBlokov(), this.plocha));
            this.plocha.pridajPostavu(new Elf(this.plocha.volneMiesto(), this.hrac, this.mapa.getMapaBlokov(), this.plocha));
            this.plocha.pridajPostavu(new Elf(this.plocha.volneMiesto(), this.hrac, this.mapa.getMapaBlokov(), this.plocha));
            this.plocha.pridajPostavu(new Carodejnik(this.plocha.volneMiesto(), this.hrac, this.mapa.getMapaBlokov(), this.plocha));
            this.plocha.pridajPostavu(new Carodejnik(this.plocha.volneMiesto(), this.hrac, this.mapa.getMapaBlokov(), this.plocha));
        }
        this.plocha.pridajMapu(obrazMapy);
        this.plocha.zobrazPlochu();

        this.casovac = new Timer(1000, this);
        this.casovac.start();
    }

    /**
     * Spustí uloženú hru - zobrazí plochu a spustí časovač.
     */
    public void spustiUlozenu() {
        this.plocha.zobrazPlochu();
        this.casovac = new Timer(1000, this);
        this.casovac.start();
    }

    /**
     * Prejde na ďalší level hry. Ak hráč prešiel všetky (3) levely, pogratuluje mu a ukončí hru.
     */
    public void dalsiLevel() {
        this.plocha.ukonci();
        this.casovac.stop();
        if (this.level < 3) {
            JOptionPane.showMessageDialog(null, "Prešiel si " + this.level + " úroveň.", "Koniec úrovne", JOptionPane.INFORMATION_MESSAGE);
            this.level++;
            this.novaHra(this.hrac.getNazov(), this.hrac.getPohlavie());
        } else {
            JOptionPane.showMessageDialog(null, "Gratulujem, prešiel si všetky levely!", "Koniec hry", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Zastaví hlavnú hru a spustí bonusovú minihru, podľa toho na ktorom levely je hráč.
     */
    public void minihra() {
        this.plocha.ukonci();
        this.casovac.stop();
        Minihra minihra = null;
        if (this.level == 1) {
            minihra = new Hadik(this);
        } else if (this.level == 2) {
            minihra = new Puzzle(this);
        } else {
            this.dalsiLevel();
        }
        if (minihra != null) {
            minihra.novaMinihra();
        }
    }

    /**
     * Vykoná potrebné akcie - skontroluje stav hry a vykoná posun nepriateľov.
     *
     * @param e udalosť na spracovanie
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.skontrolujStav()) {
            this.minihra();
        }
        this.posunNepriatelov();
    }

    /**
     * Skontroluje stav hry, respektíve či už je plocha vyčistená od všetkých nepriateľov a či boli zničené všetky prekážky.
     *
     * @return či je plocha vyčistená
     */
    public boolean skontrolujStav() {
        boolean vycistene = true;
        outer:
        for (Blok[] riadok : this.mapa.getMapaBlokov()) {
            for (Blok stlpec : riadok) {
                if (stlpec instanceof Prekazka) {
                    if (!stlpec.getPrechodnost() || ((Prekazka)stlpec).getMaPredmet()) {
                        vycistene = false;
                        break outer;
                    }
                } else if (stlpec instanceof Cesta) {
                    if (!stlpec.getPrechodnost()) {
                        vycistene = false;
                        break outer;
                    }
                }
            }
        }
        return vycistene;
    }

    /**
     * Zavolá metódu posunu u všetkých nepriateľov, ktorý sú momentálne v hre.
     */
    public void posunNepriatelov() {
        for (Nepriatel nepriatel : this.plocha.getNepriatelia()) {
            nepriatel.posunSa();
        }
    }

    /**
     * Zastaví časovač hry.
     */
    public void zastavCasovac() {
        this.casovac.stop();
    }

    /**
     * Spustí časovač hry.
     */
    public void spustiCasovac() {
        this.casovac.start();
    }

    /**
     * Uloží kompletne celú hru v stave v čase ukladania.
     */
    public void uloz() {
        File file = new File("save.txt");
        try {
            FileOutputStream stream = new FileOutputStream(file);
            ObjectOutputStream zapisovac = new ObjectOutputStream(stream);
            zapisovac.writeObject(this);
            zapisovac.close();
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}