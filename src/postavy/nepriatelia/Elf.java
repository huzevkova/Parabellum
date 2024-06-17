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
 * Trieda postavy a druhého najťažšieho nepriatela - elf, s konečnou úrovňou 4.
 *
 * @author Bianka S. Húževková
 * @version 2.0.0
 */
public class Elf implements Nepriatel {
    private JLabel elf;
    private Point pozicia;
    private Blok[][] mapa;
    private Plocha plocha;
    private Hrac hrac;
    private static final int UROVEN = 4;
    private int zivot;
    private ArrayList<Utok> utoky;
    private ArrayList<Integer> pouziteUtoky;

    /**
     * Parametrický konštruktor. Inicializuje základné parametre, nastavý obrázok a vytvorí množinu útokov.
     *
     * @param pozicia pozícia v poli mapy
     * @param hrac hrac s ktorým bude bojovať
     * @param mapa mapa na ktorej sa nahcádza
     * @param plocha plocha ktorej je súčasťou
     */
    public Elf(Point pozicia, Hrac hrac, Blok[][] mapa, Plocha plocha) {
        this.elf = new JLabel();
        this.elf.setIcon(new ImageIcon("zdroje/postavy/lightElf.png"));

        this.pozicia = pozicia;
        this.mapa = mapa;
        this.plocha = plocha;
        this.zivot = 55;
        this.hrac = hrac;

        this.utoky = new ArrayList<>();
        this.utoky.add(new CarovnyUtok(this, "kúzlo čarovných šípov", 3));
        this.utoky.add(new FyzickyUtok(this, "útok šípom", 2));
        this.utoky.add(new CarovnyUtok(this, "kúzlo prírodných živlov", 1));
        this.utoky.add(new FyzickyUtok(this, "útok mečom", 1));

        this.pouziteUtoky = new ArrayList<>();
    }

    /**
     * Vráti grafický obraz postavy - elfa.
     *
     * @return obrázok elfa
     */
    @Override
    public JLabel getObraz() {
        return this.elf;
    }

    /**
     *
     * Vráti názov postavy.
     *
     * @return elf
     */
    @Override
    public String getNazov() {
        return "elf";
    }

    /**
     * Vráti riadok na ktorom sa elf nachádza.
     *
     * @return riadok
     */
    @Override
    public int getRiadok() {
        return this.pozicia.x;
    }

    /**
     * Vráti stĺpec na ktorom sa elf nachádza.
     *
     * @return stĺpec
     */
    @Override
    public int getStlpec() {
        return this.pozicia.y;
    }

    /**
     * Vráti úroveň elfa.
     *
     * @return úroveň 1
     */
    @Override
    public int getUroven() {
        return Elf.UROVEN;
    }

    /**
     * Vráti popis elfa ako reťazec, jeho názov, úroveň, aké používa útoky a či je proti nejakým odolný.
     *
     * @return popis
     */
    @Override
    public String dajPopis() {
        return "Elf. Úroveň: " + this.getUroven() + ". Používa silné fyzické a slabé čarovné útoky. Proti čarom je menej odolný ako proti obyčajným zbraniam";
    }

    /**
     * Vráti počet životov elfa.
     *
     * @return životy
     */
    @Override
    public int getZivoty() {
        return this.zivot;
    }

    /**
     * Zmení počet životov, ak klesne na nulu vráti false, ako indikátor, že elf zomrel.
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
     * Vykoná akciu - elf sa dostane do akčného módu a začne súboj, ale iba ak je stále na ploche.
     */
    @Override
    public void akcia() {
        if (this.elf.isVisible()) {
            this.elf.setBorder(new LineBorder(Color.BLUE, 5));
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
     * Ak ale má hráč toľko alebo menej životov ako elfov najsilnejší útok, použije ten útok.
     * Zároveň ten istý útok môže použiť najviac 2x za sebou.
     *
     * @return či hráč prežil útok
     */
    @Override
    public boolean zautoc() {
        Random rnd = new Random();
        int i = rnd.nextInt(this.utoky.size());
        if (this.hrac.getZivoty() <= this.utoky.get(0).silaUtoku()) {
            i = 0;
        } else {
            while (this.pouziteUtoky.size() > 1 && this.pouziteUtoky.get(0) == this.pouziteUtoky.get(1) && this.pouziteUtoky.get(0) == i) {
                i = rnd.nextInt(this.utoky.size());
            }
        }
        this.pouziteUtoky.add(0, i);
        JOptionPane.showMessageDialog(null, "Nepriateľ sa rozhodol použiť " + this.utoky.get(i).getNazov());
        return this.utoky.get(i).pouziUtok(this.hrac);
    }

    /**
     * Ukončí boj pri prehre elfa, odoberie ho z mapy.
     */
    @Override
    public void koniecBoja() {
        this.elf.setBorder(null);
        ((Cesta)this.mapa[this.getRiadok()][this.getStlpec()]).odoberPostavu();
        this.elf.setVisible(false);

    }

    /**
     * Posunie elfa, ak je ešte na mape, ľubovoľne do jedného zo 4 smerov (hore, dole, vpravo, vľavo), ak je tam voľné miesto.
     * Ak narazí na hráča, dá sa do akcie a spustí súboj.
     */
    @Override
    public void posunSa() {
        if (this.elf.isVisible()) {
            ArrayList<Point> moznosti = new ArrayList<>();
            moznosti.add(new Point(this.pozicia.x, this.pozicia.y - 1));
            moznosti.add(new Point(this.pozicia.x, this.pozicia.y + 1));
            moznosti.add(new Point(this.pozicia.x - 1, this.pozicia.y));
            moznosti.add(new Point(this.pozicia.x + 1, this.pozicia.y));

            int pocetPokusov = 0;
            while (pocetPokusov < 8) {  //v priemere by malo stačiť 8 pokusov pri náhodnom generovaní na nájdenie voľného miesta ak existuje
                Point point = moznosti.get(new Random().nextInt(4));
                if (this.mapa[point.x][point.y].getPrechodnost()) {
                    ((Cesta)this.mapa[this.pozicia.x][this.pozicia.y]).odoberPostavu();
                    ((Cesta)this.mapa[point.x][point.y]).pridajPostavu(this);
                    this.elf.setLocation(point.y * 70, point.x * 70);
                    this.pozicia = new Point(point.x, point.y);
                    if (this.elf.getLocation().equals(this.hrac.getObraz().getLocation())) {
                        this.hrac.zastavCasovacPlochy();
                        this.hrac.akcia();
                        this.akcia();
                    }
                    return;
                }
                pocetPokusov++;
            }
        }
    }

    /**
     * Vráti či je elf odolný proti kúzelným útokom - false.
     *
     * @return odolnosť proti kúzlam
     */
    @Override
    public boolean odolnyProtiKuzlam() {
        return false;
    }

    /**
     * Vráti či je elf odolný proti fyzickým útokom - true.
     *
     * @return odolnosť proti fyzickým útokom
     */
    @Override
    public boolean odolnyProtiFyzickymUtokom() {
        return true;
    }
}
