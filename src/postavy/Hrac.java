package postavy;

import hlavnaHra.Hra;
import predmety.Inventar;
import predmety.energia.Jablko;
import predmety.ochrana.Ochrana;
import predmety.ochrana.Stit;
import predmety.zbrane.Mec;
import ovladace.OvladacKlavesnica;
import svet.Blok;
import svet.Cesta;
import svet.Prekazka;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import java.awt.Color;

/**
 * Centrálna trieda hráča (postavy), ktorá mu umožňuje vykonávať činnosti a ukladá si o ňom všetky informácie.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public class Hrac implements Postava {
    private JLabel hrac;
    private OvladacKlavesnica ovladac;
    private Inventar inventar;
    private int[] pozicia;
    private Blok[][] mapa;
    private boolean akcia;
    private int uroven;
    private int zivot;
    private int pohlavie;
    private String meno;
    private Hra hra;
    private int pocetPorazenych;
    private Ochrana ochrana;

    /**
     * Parametrický konštruktor. Inicializuje začiatočné parametre, nastavý obrázok, do inventára vloží 3 základné predmety.
     *
     * @param meno meno hráča
     * @param mapa mapa na ktorej sa nachádza
     * @param pohlavie pohlavie hráča
     */
    public Hrac(String meno, Blok[][] mapa, int pohlavie, Hra hra) {
        this.hra = hra;
        this.meno = meno;
        this.hrac = new JLabel();
        this.hrac.setLocation(70, 70);
        if (pohlavie == 0) {
            this.hrac.setIcon(new ImageIcon("zdroje/postavy/hero.png"));
        } else {
            this.hrac.setIcon(new ImageIcon("zdroje/postavy/heroine.png"));
        }
        this.pohlavie = pohlavie;

        this.ovladac = new OvladacKlavesnica(this.hrac, this);
        this.mapa = mapa;

        this.inventar = new Inventar();
        this.inventar.pridajPredmet(new Mec());
        this.inventar.pridajPredmet(new Stit());
        this.inventar.pridajPredmet(new Jablko());

        this.pozicia = new int[2];
        this.pozicia[0] = 1;
        this.pozicia[1] = 1;

        this.akcia = false;
        this.ochrana = null;

        this.uroven = 1;
        this.zivot = 100;
        this.pocetPorazenych = 0;
    }

    /**
     * Vráti grafický obraz hráča.
     *
     * @return obrázok postavy
     */
    @Override
    public JLabel getObraz() {
        return this.hrac;
    }

    /**
     * Vráti názov (meno) hráča, ktoré si zadal.
     *
     * @return meno
     */
    @Override
    public String getNazov() {
        return this.meno;
    }

    /**
     * Vráti riadok v ktorom sa hráč nachádza.
     *
     * @return riadok
     */
    @Override
    public int getRiadok() {
        return this.pozicia[0];
    }

    /**
     * Vráti stĺpec v ktorom sa hráč nachádza.
     *
     * @return stlpec
     */
    @Override
    public int getStlpec() {
        return this.pozicia[1];
    }

    /**
     * Vráti aktiálnu úroveň hráča.
     *
     * @return úroveň
     */
    @Override
    public int getUroven() {
        return this.uroven;
    }

    /**
     * Vráti popis hráča, meno úroveň a počet životov.
     *
     * @return reťazec popisu
     */
    @Override
    public String dajPopis() {
        return "Meno hrdinu: " + this.meno + "\nUroven: " + this.uroven + "\nZivoty: " + this.zivot;
    }

    /**
     * Vráti počet životov.
     *
     * @return životy
     */
    @Override
    public int getZivoty() {
        return this.zivot;
    }

    /**
     * Zmení o daný počet životov. Neprekročí 100, pod nulou vráti false.
     *
     * @param pocetZivotov počet ktorý chceme odobrať / pripočítať (vtedy pridať mínus)
     * @return či hráč ešte žije
     */
    @Override
    public boolean zmenZivoty(int pocetZivotov) {
        this.zivot -= pocetZivotov;
        if (this.zivot <= 0) {
            return false;
        } else if (this.zivot > 100) {
            this.zivot = 100;
        }
        return true;
    }

    /**
     * Vykoná akciu - hráč sa dostane do alebo z "akčného" módu.
     */
    @Override
    public void akcia() {
        if (this.akcia) {
            this.akcia = false;
            this.hrac.setBorder(null);
        } else {
            this.akcia = true;
            this.hrac.setBorder(new LineBorder(Color.RED, 5));
        }
    }

    /**
     * Vráti pohlavie hráča (0 = chlapec, 1 = dievca).
     *
     * @return pohlavie vo forme čísla
     */
    public int getPohlavie() {
        return this.pohlavie;
    }

    /**
     * Vráti inventár hráča.
     *
     * @return inventár
     */
    public Inventar getInventar() {
        return this.inventar;
    }

    /**
     * Zvýši úroveň hráča o 1, ak porazil ďalších 2 nepriateľov.
     */
    public boolean zvysUroven() {
        this.pocetPorazenych++;
        if (this.pocetPorazenych % 2 == 0) {
            this.uroven += 1;
            return true;
        }
        return false;
    }

    /**
     * Ak sa dá, posunie hráča o daný počet riadkov / stĺpcov. Ak je na novom mieste predmet, zoberie ho.
     * Ak sa na dané miesto nedá posunúť, neurobí nič, ale ak je v akčnom móde vykoná zničenie prekážky alebo začne boj.
     *
     * @param r o koľko riadkov posunúť (nadobudne len 1 alebo 0)
     * @param s o koľko stĺpcov posunúť (nadobudne len 1 alebo 0)
     * @return či sa podarilo vykonať posun
     */
    public boolean posunSa(int r, int s) {
        if (this.mapa[this.pozicia[0] + r][this.pozicia[1] + s].getPrechodnost()) {
            this.pozicia[0] += r;
            this.pozicia[1] += s;
            return true;
        } else if (!this.mapa[this.pozicia[0] + r][this.pozicia[1] + s].getZnicitelnost() && !this.mapa[this.pozicia[0] + r][this.pozicia[1] + s].getPrechodnost() && this.mapa[this.pozicia[0] + r][this.pozicia[1] + s] instanceof Prekazka) {
            Prekazka prekazka = (Prekazka)this.mapa[this.pozicia[0] + r][this.pozicia[1] + s];
            if (prekazka.getMaPredmet()) {
                this.inventar.pridajPredmet(prekazka.zoberSchovanyPredmet());
            }
            this.mapa[this.pozicia[0] + r][this.pozicia[1] + s] = new Cesta("zdroje/prostredie/cesta.jpg");
            this.pozicia[0] += r;
            this.pozicia[1] += s;
            return true;
        } else if (this.akcia && this.mapa[this.pozicia[0] + r][this.pozicia[1] + s].getZnicitelnost()) {
            Prekazka prekazka = (Prekazka)this.mapa[this.pozicia[0] + r][this.pozicia[1] + s];
            prekazka.znic();
            if (!prekazka.getMaPredmet()) {
                this.mapa[this.pozicia[0] + r][this.pozicia[1] + s] = new Cesta("zdroje/prostredie/cesta.jpg");
            }
        } else if (this.akcia && this.mapa[this.pozicia[0] + r][this.pozicia[1] + s] instanceof Cesta && !this.mapa[this.pozicia[0] + r][this.pozicia[1] + s].getPrechodnost()) {
            ((Cesta)this.mapa[this.pozicia[0] + r][this.pozicia[1] + s]).getPostava().akcia();
            this.zastavCasovacPlochy();
            this.akcia();
        }
        return false;
    }

    /**
     * Nastavý hráčovi predmet, ktorý ho má chrániť.
     *
     * @param ochrana ochranný predmet
     */
    public void nastavOchranu(Ochrana ochrana) {
        this.ochrana = ochrana;
    }

    /**
     * Vráti predmet, ktorý hráča chráni.
     *
     * @return ochranný predmet
     */
    public Ochrana getOchrana() {
        return this.ochrana;
    }

    /**
     * Zastaví časovač na ploche.
     */
    public void zastavCasovacPlochy() {
        this.hra.zastavCasovac();
    }

    /**
     * Spustí časovač na ploche.
     */
    public void spustiCasovacPlochy() {
        this.hra.spustiCasovac();
    }
}
