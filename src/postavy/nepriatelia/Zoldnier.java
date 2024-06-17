package postavy.nepriatelia;

import postavy.Hrac;
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
 * Trieda postavy a druhého najslabšieho nepriatela - zoldnier, s úrovňou 2.
 *
 * @author Bianka S. Húževková
 * @version 2.0.0
 */
public class Zoldnier implements Nepriatel {

    private JLabel zoldnier;
    private Point pozicia;
    private Blok[][] mapa;
    private Plocha plocha;
    private Hrac hrac;
    private static final int UROVEN = 2;
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
    public Zoldnier(Point pozicia, Hrac hrac, Blok[][] mapa, Plocha plocha) {
        this.zoldnier = new JLabel();
        this.zoldnier.setIcon(new ImageIcon("zdroje/postavy/raider.png"));

        this.pozicia = pozicia;

        this.mapa = mapa;
        this.plocha = plocha;
        this.zivot = 40;
        this.hrac = hrac;

        this.utoky = new ArrayList<>();
        this.utoky.add(new FyzickyUtok(this, "útok dlhým mečom", 4));
        this.utoky.add(new FyzickyUtok(this, "útok zakrivenou šabľou", 2));
        this.utoky.add(new FyzickyUtok(this, "útok kopijou", 1));

        this.pouziteUtoky = new ArrayList<>();
    }

    /**
     * Vráti grafický obraz postavy - žoldniera.
     *
     * @return obrázok žoldniera
     */
    @Override
    public JLabel getObraz() {
        return this.zoldnier;
    }

    /**
     * Vráti názov postavy.
     *
     * @return zoldnier
     */
    @Override
    public String getNazov() {
        return "zoldnier";
    }

    /**
     * Vráti riadok na ktorom sa žoldnier nachádza.
     *
     * @return riadok
     */
    @Override
    public int getRiadok() {
        return this.pozicia.x;
    }

    /**
     * Vráti stĺpec na ktorom sa žoldnier nachádza.
     *
     * @return stĺpec
     */
    @Override
    public int getStlpec() {
        return this.pozicia.y;
    }

    /**
     * Vráti úroveň žoldniera.
     *
     * @return úroveň 1
     */
    @Override
    public int getUroven() {
        return Zoldnier.UROVEN;
    }

    /**
     * Vráti popis žoldniera ako reťazec, jeho názov, úroveň, aké používa útoky a či je proti nejakým odolný.
     *
     * @return popis
     */
    @Override
    public String dajPopis() {
        return "Žoldnier. Úroveň " + this.getUroven() + ". Používa len fyzické útoky. Proti mágii je slabo chránený, ale má silné brnenie.";
    }

    /**
     * Vráti počet životov žoldniera.
     *
     * @return životy
     */
    @Override
    public int getZivoty() {
        return this.zivot;
    }

    /**
     * Zmení počet životov, ak klesne na nulu vráti false, ako indikátor, že žoldnier zomrel.
     *
     * @param pocetZivotov počet ktorý chceme odobrať / pripočítať (vtedy pridať mínus)
     * @return či žoldnier ešte žije
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
     * Vykoná akciu - žoldnier sa dostane do akčného módu a začne súboj, ale iba ak je stále na ploche.
     */
    @Override
    public void akcia() {
        if (this.zoldnier.isVisible()) {
            this.zoldnier.setBorder(new LineBorder(Color.BLUE, 5));
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
     * Ak ale má hráč toľko alebo menej životov ako žoldnierov najsilnejší útok, použije ten útok.
     * Zároveň ten istý útok použije najviac 2x za sebou.
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
        JOptionPane.showMessageDialog(null, "Nepriateľ sa rozhodol použiť " + this.utoky.get(i).getNazov() + " so silou " + this.utoky.get(i).silaUtoku());
        return this.utoky.get(i).pouziUtok(this.hrac);
    }

    /**
     * Ukončí boj pri prehre žoldniera, odoberie ho z mapy.
     */
    @Override
    public void koniecBoja() {
        this.zoldnier.setBorder(null);
        ((Cesta)this.mapa[this.getRiadok()][this.getStlpec()]).odoberPostavu();
        this.zoldnier.setVisible(false);

    }

    /**
     * Posunie, respektíve neposunie, žoldniera.
     */
    @Override
    public void posunSa() {
    }

    /**
     * Vráti či je žoldnier odolný proti kúzelným útokom - false.
     *
     * @return odolnosť proti kúzlam
     */
    @Override
    public boolean odolnyProtiKuzlam() {
        return false;
    }

    /**
     * Vráti či je žoldnier odolný proti fyzickým útokom - true.
     *
     * @return odolnosť proti fyzickým útokom
     */
    @Override
    public boolean odolnyProtiFyzickymUtokom() {
        return true;
    }
}
