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
 * Trieda postavy a najslabšieho nepriatela - skriatok, s konenčou úrovňou 1.
 *
 * @author Bianka S. Húževková
 * @version 2.0.0
 */
public class Skriatok implements Nepriatel {
    private JLabel skriatok;
    private Point pozicia;
    private Blok[][] mapa;
    private Plocha plocha;
    private Hrac hrac;
    private static final int UROVEN = 1;
    private int zivot;
    private ArrayList<Utok> utoky;

    /**
     * Parametrický konštruktor. Inicializuje základné parametre, nastavý obrázok a vytvorí množinu útokov.
     *
     * @param pozicia pozícia v poli mapy
     * @param hrac hrac s ktorým bude bojovať
     * @param mapa mapa na ktorej sa nachádza
     * @param plocha plocha ktorej je súčasťou
     */
    public Skriatok(Point pozicia, Hrac hrac, Blok[][] mapa, Plocha plocha) {
        this.skriatok = new JLabel();
        this.skriatok.setIcon(new ImageIcon("zdroje/postavy/goblin.png"));

        this.pozicia = pozicia;
        this.mapa = mapa;
        this.plocha = plocha;
        this.zivot = 30;
        this.hrac = hrac;

        this.utoky = new ArrayList<>();
        this.utoky.add(new FyzickyUtok(this, "útok kopijou", 3));
        this.utoky.add(new FyzickyUtok(this, "útok päsťami", 3));
    }

    /**
     * Vráti grafický obraz postavy - škriatka.
     *
     * @return obrázok škriatka
     */
    @Override
    public JLabel getObraz() {
        return this.skriatok;
    }

    /**
     * Vráti názov postavy.
     *
     * @return skriatok
     */
    @Override
    public String getNazov() {
        return "skriatok";
    }

    /**
     * Vráti riadok na ktorom sa škriatok nachádza.
     *
     * @return riadok
     */
    @Override
    public int getRiadok() {
        return this.pozicia.x;
    }

    /**
     * Vráti stĺpec na ktorom sa škriatok nachádza.
     *
     * @return stĺpec
     */
    @Override
    public int getStlpec() {
        return this.pozicia.y;
    }

    /**
     * Vráti úroveň škriatka.
     *
     * @return úroveň 1
     */
    @Override
    public int getUroven() {
        return Skriatok.UROVEN;
    }

    /**
     * Vráti popis škriatka ako reťazec, jeho názov, úroveň, aké používa útoky a či je proti nejakým odolný.
     *
     * @return popis
     */
    @Override
    public String dajPopis() {
        return "Škriatok. Úroveň " + this.getUroven() + " Používa len fyzické útoky. Nad ničím nepremýšľa, len dookola útočí. Nič ho nechráni.";
    }

    /**
     * Vráti počet životov škriatka.
     *
     * @return životy
     */
    @Override
    public int getZivoty() {
        return this.zivot;
    }

    /**
     * Zmení počet životov, ak klesne na nulu vráti false, ako indikátor, že škriatok zomrel.
     *
     * @param pocetZivotov počet ktorý chceme odobrať / pripočítať (vtedy pridať mínus)
     * @return či škriatok ešte žije
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
     * Vykoná akciu - škriatok sa dostane do akčného módu a začne súboj, ale iba ak je stále na ploche.
     */
    @Override
    public void akcia() {
        if (this.skriatok.isVisible()) {
            this.skriatok.setBorder(new LineBorder(Color.BLUE, 5));
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
     * Ak ale má hráč najviac toľko životov ako škriatkov najsilnejší útok, použije ten útok.
     *
     * @return či hráč prežil útok
     */
    @Override
    public boolean zautoc() {
        Random rnd = new Random();
        int i = rnd.nextInt(this.utoky.size());
        if (this.hrac.getZivoty() <= this.utoky.get(0).silaUtoku()) {
            i = 0;
        }
        JOptionPane.showMessageDialog(null, "Nepriateľ sa rozhodol použiť " + this.utoky.get(i).getNazov() + " so silou " + this.utoky.get(i).silaUtoku());
        return this.utoky.get(i).pouziUtok(this.hrac);
    }

    /**
     * Ukončí boj pri prehre škriatka, odoberie ho z mapy.
     */
    @Override
    public void koniecBoja() {
        this.skriatok.setBorder(null);
        ((Cesta)this.mapa[this.getRiadok()][this.getStlpec()]).odoberPostavu();
        this.skriatok.setVisible(false);
    }

    /**
     * Posunie, respektíve neposunie, škriatka.
     */
    @Override
    public void posunSa() {
    }

    /**
     * Vráti či je škriatok odolný proti kúzelným útokom - false.
     *
     * @return odolnosť proti kúzlam
     */
    @Override
    public boolean odolnyProtiKuzlam() {
        return false;
    }

    /**
     * Vráti či je škriatok odolný proti fyzickým útokom - false.
     *
     * @return odolnosť proti fyzickým útokom
     */
    @Override
    public boolean odolnyProtiFyzickymUtokom() {
        return false;
    }
}
