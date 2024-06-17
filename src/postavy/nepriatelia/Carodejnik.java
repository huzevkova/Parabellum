package postavy.nepriatelia;

import postavy.Hrac;
import utoky.CarovnyUtok;
import utoky.FyzickyUtok;
import utoky.Utok;
import suboj.Suboj;
import svet.Blok;
import svet.Cesta;
import svet.Plocha;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * Trieda postavy a najťažšieho nepriateľa - čarodejník, s konečnou úrovňou 5.
 *
 * @author Bianka S. Húževková
 * @version 2.0.0
 */
public class Carodejnik implements Nepriatel {
    private JLabel carodejnik;
    private Point pozicia;
    private Blok[][] mapa;
    private Plocha plocha;
    private Hrac hrac;
    private static final int UROVEN = 5;
    private int zivot;
    private ArrayList<Utok> utoky;
    private ArrayList<Integer> pouziteUtoky;

    /**
     * Parametrický konštruktor. Inicializuje základné parametre, nastavý obrázok a vytvorí množinu útokov.
     *
     * @param pozicia pozícia v poli mapy
     * @param hrac hrac s ktorým bude bojovať
     * @param mapa mapa na ktorej sa nachádza
     * @param plocha plocha ktorej je súčasťou
     */
    public Carodejnik(Point pozicia, Hrac hrac, Blok[][] mapa, Plocha plocha) {
        this.carodejnik = new JLabel();
        this.carodejnik.setIcon(new ImageIcon("zdroje/postavy/wizard.png"));

        this.pozicia = pozicia;

        this.mapa = mapa;
        this.plocha = plocha;
        this.zivot = 100;
        this.hrac = hrac;

        this.utoky = new ArrayList<>();
        this.utoky.add(new CarovnyUtok(this, "kúzlo znicenia", 4));
        this.utoky.add(new CarovnyUtok(this, "kúzlo padajúcich ostňov", 3));
        this.utoky.add(new CarovnyUtok(this, "kúzlo oslabenia mysle", 2));
        this.utoky.add(new FyzickyUtok(this, "útok dvojsečným mečom", 2));
        this.utoky.add(new FyzickyUtok(this, "útok temným mečom", 2));
        this.utoky.add(new FyzickyUtok(this, "útok temnou dýkou", 1));

        this.pouziteUtoky = new ArrayList<>();
    }

    /**
     * Vráti grafický obraz postavy - čarodejníka.
     *
     * @return obrázok čarodejníka
     */
    @Override
    public JLabel getObraz() {
        return this.carodejnik;
    }

    /**
     *
     * Vráti názov postavy.
     *
     * @return carodejnik
     */
    @Override
    public String getNazov() {
        return "carodejnik";
    }

    /**
     * Vráti riadok na ktorom sa čarodejník nachádza.
     *
     * @return riadok
     */
    @Override
    public int getRiadok() {
        return this.pozicia.x;
    }

    /**
     * Vráti stĺpec na ktorom sa čarodejník nachádza.
     *
     * @return stĺpec
     */
    @Override
    public int getStlpec() {
        return this.pozicia.y;
    }

    /**
     * Vráti úroveň čarodejníka.
     *
     * @return úroveň 1
     */
    @Override
    public int getUroven() {
        return Carodejnik.UROVEN;
    }

    /**
     * Vráti popis čarodejník ako reťazec, jeho názov, úroveň, aké používa útoky a či je proti nejakým odolný.
     *
     * @return popis
     */
    @Override
    public String dajPopis() {
        return "Čarodejník. Uroven: " + this.getUroven() + ". Používa slabšie fyzické útoky a silné čarovné útoky. Nemá takmer žiadne brnenie, ale kúzla odráža bez problémov.";
    }

    /**
     * Vráti počet životov čaarodejníka.
     *
     * @return životy
     */
    @Override
    public int getZivoty() {
        return this.zivot;
    }

    /**
     * Zmení počet životov, ak klesne na nulu vráti false, ako indikátor, že čarodejník zomrel.
     *
     * @param pocetZivotov počet ktorý chceme odobrať / pripočítať (vtedy pridať mínus)
     * @return či elf ešte žije
     */
    @Override
    public boolean zmenZivoty(int pocetZivotov) {
        this.zivot -= pocetZivotov;
        if (this.zivot <= 0) {
            return false;
        }
        return true;
    }

    /**
     * Vykoná akciu - čarodejník sa dostane do akčného módu a začne súboj, ale iba ak je stále na ploche.
     */
    @Override
    public void akcia() {
        if (this.carodejnik.isVisible()) {
            this.carodejnik.setBorder(new LineBorder(Color.BLUE, 5));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Suboj suboj = new Suboj(this.hrac, this, this.plocha);
        }
    }

    /**
     * Náhodne vyberie a vykoná útok na hráča s ktorým je v súboji.
     * Ak mu počet životov klesol pod 35, s 80% pravdepodobnosťou sa uzdraví o 15 životov. Uzdraviť sa ale nemôže 2x za sebou.
     * Svoj najsilnejší útok môže použiť len raz.
     * Ak má hráč toľko alebo menej životov ako jeho najsilnejší použiteľný útok (čiže ak už použil najsilnejší, tak jeho druhý najsilnejši),
     * použije ten útok.
     * Zároveň útok toho istého typu (kúzelný / fyzický) môže použiť najviac 3x za sebou.
     *
     * @return či hráč prežil útok
     */
    @Override
    public boolean zautoc() {
        Random rnd = new Random();
        int i;
        if (this.zivot < 35 && !this.pouziteUtoky.isEmpty() && this.pouziteUtoky.get(0) != this.utoky.size() && rnd.nextInt(100) < 80) {
            if (this.pouziteUtoky.contains(0)) {
                if (this.hrac.getZivoty() <= this.utoky.get(1).silaUtoku()) {
                    i = 1;
                    JOptionPane.showMessageDialog(null, "Nepriateľ sa rozhodol použiť " + this.utoky.get(i).getNazov() + " so silou " + this.utoky.get(i).silaUtoku());
                    this.pouziteUtoky.add(0, i);
                    return this.utoky.get(i).pouziUtok(this.hrac);
                } else {
                    i = this.utoky.size();
                    this.pouziteUtoky.add(0, i);
                    JOptionPane.showMessageDialog(null, "Nepriateľ sa rozhodol uzdraviť kúzlom so silou 15.");
                    this.zmenZivoty(-15);
                    return true;
                }
            } else {
                if (this.hrac.getZivoty() <= this.utoky.get(0).silaUtoku()) {
                    i = 0;
                    JOptionPane.showMessageDialog(null, "Nepriateľ sa rozhodol použiť " + this.utoky.get(i).getNazov() + " so silou " + this.utoky.get(i).silaUtoku());
                    this.pouziteUtoky.add(0, i);
                    return this.utoky.get(i).pouziUtok(this.hrac);
                } else {
                    i = this.utoky.size();
                    this.pouziteUtoky.add(0, i);
                    JOptionPane.showMessageDialog(null, "Nepriateľ sa rozhodol uzdraviť kúzlom so silou 15.");
                    this.zmenZivoty(-15);
                    return true;
                }
            }
        } else {
            if (this.pouziteUtoky.contains(0)) {
                if (this.hrac.getZivoty() <= this.utoky.get(1).silaUtoku()) {
                    i = 1;
                } else {
                    i = rnd.nextInt(this.utoky.size() - 1) + 1;
                }
            } else {
                if (this.hrac.getZivoty() <= this.utoky.get(0).silaUtoku()) {
                    i = 0;
                } else {
                    i = rnd.nextInt(this.utoky.size());
                    if (this.pouziteUtoky.indexOf(this.utoky.size()) > 2) {
                        while (this.pouziteUtoky.size() > 2 && this.utoky.get(this.pouziteUtoky.get(0)).carovny() == this.utoky.get(this.pouziteUtoky.get(1)).carovny() && this.utoky.get(this.pouziteUtoky.get(1)).carovny() == this.utoky.get(this.pouziteUtoky.get(2)).carovny() && this.pouziteUtoky.get(0) == i) {
                            i = rnd.nextInt(this.utoky.size());
                        }
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Nepriateľ sa rozhodol použiť " + this.utoky.get(i).getNazov() + " so silou " + this.utoky.get(i).silaUtoku());
            this.pouziteUtoky.add(0, i);
            return this.utoky.get(i).pouziUtok(this.hrac);
        }
    }

    /**
     * Ukončí boj pri prehre čarodejníka, odoberie ho z mapy.
     */
    @Override
    public void koniecBoja() {
        this.carodejnik.setBorder(null);
        ((Cesta)this.mapa[this.getRiadok()][this.getStlpec()]).odoberPostavu();
        this.carodejnik.setVisible(false);
    }

    /**
     * Posunie čarodejníka, ak je ešte na mape, do jedného zo 4 smerov (hore, dole, vpravo, vľavo), ak je tam voľné miesto,
     * tak, aby sa priblížil hráčovi ak sa dá (naháňa hráča).
     * Ak narazí na hráča, dá sa do akcie a spustí súboj.
     */
    @Override
    public void posunSa() {
        if (this.carodejnik.isVisible()) {
            ArrayList<Point> moznosti = new ArrayList<>();
            moznosti.add(new Point(this.pozicia.x, this.pozicia.y - 1)); //doľava
            moznosti.add(new Point(this.pozicia.x, this.pozicia.y + 1)); //doprava
            moznosti.add(new Point(this.pozicia.x - 1, this.pozicia.y)); //hore
            moznosti.add(new Point(this.pozicia.x + 1, this.pozicia.y)); //dole

            if (this.hrac.getRiadok() == this.pozicia.x) {
                if (this.hrac.getStlpec() < this.pozicia.y) {
                    Point point = moznosti.get(0);
                    if (this.skontrolujPosun(point)) {
                        return;
                    }
                } else {
                    Point point = moznosti.get(1);
                    if (this.skontrolujPosun(point)) {
                        return;
                    }
                }
            } else if (this.hrac.getStlpec() == this.pozicia.y) {
                if (this.hrac.getRiadok() < this.pozicia.x) {
                    Point point = moznosti.get(2);
                    if (this.skontrolujPosun(point)) {
                        return;
                    }
                } else {
                    Point point = moznosti.get(3);
                    if (this.skontrolujPosun(point)) {
                        return;
                    }
                }
            } else if (this.hrac.getRiadok() > this.pozicia.x) {
                Point point = moznosti.get(3);
                if (this.skontrolujPosun(point)) {
                    return;
                }
            } else if (this.hrac.getRiadok() < this.pozicia.x) {
                Point point = moznosti.get(2);
                if (this.skontrolujPosun(point)) {
                    return;
                }
            } else if (this.hrac.getStlpec() < this.pozicia.y) {
                Point point = moznosti.get(0);
                if (this.skontrolujPosun(point)) {
                    return;
                }
            } else if (this.hrac.getStlpec() > this.pozicia.y) {
                Point point = moznosti.get(1);
                if (this.skontrolujPosun(point)) {
                    return;
                }
            }
            int pocetPokusov = 0;
            while (pocetPokusov < 10) {
                Point point = moznosti.get(new Random().nextInt(4));
                if (this.skontrolujPosun(point)) {
                    return;
                }
                pocetPokusov++;
            }

        }
    }

    /**
     * Skontroluje či sa na danú pozíciu dá posunúť, či je tam voľné miesto.
     *
     * @param point pozícia na skontrolovanie
     * @return či sa tam dá premiestniť
     */
    private boolean skontrolujPosun (Point point) {
        if (this.mapa[point.x][point.y].getPrechodnost()) {
            ((Cesta)this.mapa[this.pozicia.x][this.pozicia.y]).odoberPostavu();
            ((Cesta)this.mapa[point.x][point.y]).pridajPostavu(this);
            this.carodejnik.setLocation(point.y * 70, point.x * 70);
            this.pozicia = new Point(point.x, point.y);
            if (this.carodejnik.getLocation().equals(this.hrac.getObraz().getLocation())) {
                this.hrac.zastavCasovacPlochy();
                this.hrac.akcia();
                this.akcia();
            }
            return true;
        }
        return false;
    }

    /**
     * Vráti či je čarodejník odolný proti kúzelným útokom - true.
     *
     * @return odolnosť proti kúzlam
     */
    @Override
    public boolean odolnyProtiKuzlam() {
        return true;
    }

    /**
     * Vráti či je čarodejník odolný proti fyzickým útokom - false.
     *
     * @return odolnosť proti fyzickým útokom
     */
    @Override
    public boolean odolnyProtiFyzickymUtokom() {
        return false;
    }
}
