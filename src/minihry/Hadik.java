package minihry;

import hlavnaHra.Hra;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

/**
 * Minihra inšpirovaná hadíkom. Hráč (zelený had) zbiera náhodne rozmiestnené vajíčka (korisť), pričom
 * ho naháňa nepriateľský čierny had. Potomok triedy Minihra.
 *
 * @author Bianka S. Húževková
 * @version 4.0.0
 */
public class Hadik extends Minihra {
    private static final int POCET_BODOV = 5;
    private static final int SIRKA = 400;
    private static final int VYSKA = 400;
    private int body;
    private JLabel hrac;
    private JLabel nepriatel;
    private JLabel korist;
    private JLabel pocet;
    private Platno platno;

    /**
     * Parametrický konkštrukror, spustí minihru hadíka. Využije konštruktor predka.
     *
     */
    public Hadik(Hra hra) {
        super(hra, 20, "Zahraj sa na hada. Utekaj pred čiernym dravcom (klávesy WASD) a nazbieraj " + Hadik.POCET_BODOV + " vajíčka.");
    }

    /**
     * Inicializuje všetky potrebné objekty s potrebnými parametrami, pridá ich na plátno a začne novú hru.
     * Hráčovi nastaví ovládanie klávesami WASD.
     */
    @Override
    public void novaMinihra() {
        this.platno = new Platno(Hadik.SIRKA, Hadik.VYSKA);
        this.platno.setPozadie(new Color(244, 164, 94));
        this.body = 0;
        this.pocet = new JLabel();
        this.pocet.setFont(new Font("Comic Sans", Font.PLAIN, 30));
        this.pocet.setText(String.valueOf(this.body));
        this.pocet.setBounds(Hadik.SIRKA - 40, 0, 40, 40);
        this.platno.pridajObjekt(this.pocet);

        this.hrac = new JLabel();
        this.hrac.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 'a' && Hadik.this.hrac.getX() - 7 >= 0) {
                    Hadik.this.hrac.setLocation(Hadik.this.hrac.getX() - 7, Hadik.this.hrac.getY());
                } else if (e.getKeyChar() == 'd' && Hadik.this.hrac.getX() + 7 <= Hadik.this.platno.getObsahPlochy().getWidth() - 20) {
                    Hadik.this.hrac.setLocation(Hadik.this.hrac.getX() + 7, Hadik.this.hrac.getY());
                } else if (e.getKeyChar() == 'w' && Hadik.this.hrac.getY() - 7 >= 0) {
                    Hadik.this.hrac.setLocation(Hadik.this.hrac.getX(), Hadik.this.hrac.getY() - 7);
                } else if (e.getKeyChar() == 's' && Hadik.this.hrac.getY() + 7 <= Hadik.this.platno.getObsahPlochy().getHeight() - 20) {
                    Hadik.this.hrac.setLocation(Hadik.this.hrac.getX(), Hadik.this.hrac.getY() + 7);
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        this.platno.pridajObjekt(this.novyKomponent(this.hrac, "hadik.png", Hadik.SIRKA / 2 - 16, Hadik.VYSKA / 2 - 38, 20));

        this.nepriatel = new JLabel();
        this.platno.pridajObjekt(this.novyKomponent(this.nepriatel, "zlyHad.png", 0, 0, 40));

        this.korist = new JLabel();
        this.platno.pridajObjekt(this.novyKomponent(this.korist, "korist.png", new Random().nextInt(Hadik.SIRKA - 70), new Random().nextInt(Hadik.VYSKA - 90), 10));

        super.setPlatno(this.platno);
        super.getTimer().start();
    }

    /**
     * Prekrytá metóda, spustí minihru odznova. Využíva metódu predka.
     */
    @Override
    public void znova() {
        super.znova();
        JOptionPane.showMessageDialog(null, "Chytil ta nepriatel. Skus znova.", "End Game", JOptionPane.INFORMATION_MESSAGE);
        this.novaMinihra();
    }

    /**
     * Nastavý komponentu zadané parametre a vráti ho upravený a nastavený.
     *
     * @param komponent komponent ktorý sa nastavuje
     * @param obrazok názov obrázku komponentu
     * @param x súradnica x
     * @param y súradnica y
     * @param rozmer rozmer komponentu
     * @return hotový komponent
     */
    public JLabel novyKomponent(JLabel komponent, String obrazok, int x, int y, int rozmer) {
        ImageIcon icon = new ImageIcon("zdroje/minihry/" + obrazok);
        komponent.setIcon(icon);
        komponent.setBounds(x, y, rozmer, rozmer);
        return komponent;
    }

    /**
     * Vytvorí novú korisť na náhodných súradniciach.
     */
    public void novaKorist() {
        Random rnd = new Random();
        int noveX = rnd.nextInt(Hadik.SIRKA - 70);
        int noveY = rnd.nextInt(Hadik.VYSKA - 90);
        this.korist.setBounds(noveX, noveY, this.korist.getIcon().getIconHeight(), this.korist.getIcon().getIconWidth());
    }

    /**
     * Prekrytá metóda, vykoná potrebné akcie - skontroluje stav a posunie nepriateľa.
     *
     * @param e udalosť, ktorá ju spustila
     */
    @Override
    public void vykonajAkciu(ActionEvent e) {
        this.kontrola();
        this.ovladanieNepriatela();
    }

    /**
     * Skontroluje či hráč chytil korisť, t.j. či sa ikona hráča dotkla ikony koristi.
     * Berie do úvahy, že ide o pravé horné súradnice a teda počíta aj s rozmerami ikon.
     * Ak treba, vytvorí novú korisť, zmení počet bodov alebo ukončí hru.
     */
    public void kontrola() {
        int hracX = this.hrac.getX();
        int hracY = this.hrac.getY();
        int koristX = this.korist.getX();
        int koristY = this.korist.getY();

        if (hracX < koristX + this.korist.getWidth() && hracX + this.hrac.getWidth() > koristX && hracY < koristY + this.korist.getHeight() && hracY + this.hrac.getHeight() > koristY) {
            this.novaKorist();
            this.body++;
        }
        if (this.body >= Hadik.POCET_BODOV) {
            super.koniec();
        }
        JLabel stary = this.pocet;
        this.pocet.setText(String.valueOf(this.body));
        this.platno.nahradObjekt(stary, this.pocet);
        this.hrac.requestFocusInWindow();
    }

    /**
     * Posúva nepriateľa podľa toho, kde sa nachádza vzhľadom na hráča.
     * Ošetruje všetky možnosti porovnaním súradníc.
     * Ak nepriateľ hráča chytí, spustí hru odznova.
     */
    public void ovladanieNepriatela() {
        int hracX = this.hrac.getX();
        int hracY = this.hrac.getY();
        int nepriatelX = this.nepriatel.getX();
        int nepriatelY = this.nepriatel.getY();

        if (hracX < nepriatelX + this.nepriatel.getWidth() && hracX + this.hrac.getWidth() > nepriatelX && hracY < nepriatelY + this.nepriatel.getHeight() && hracY + this.hrac.getHeight() > nepriatelY) {
            this.znova();
            return;
        }

        if (nepriatelX != hracX && nepriatelY != hracY) {
            if (nepriatelX > hracX && nepriatelY > hracY) {
                nepriatelX--;
                nepriatelY--;
            } else if (nepriatelX > hracX && nepriatelY < hracY) {
                nepriatelX--;
                nepriatelY++;
            } else if (nepriatelX < hracX && nepriatelY < hracY) {
                nepriatelX++;
                nepriatelY++;
            } else if (nepriatelX < hracX && nepriatelY > hracY) {
                nepriatelX++;
                nepriatelY--;
            }
        } else if (nepriatelX == hracX) {
            if (nepriatelY > hracY) {
                nepriatelY--;
            } else {
                nepriatelY++;
            }
        } else if (nepriatelY == hracY) {
            if (nepriatelX > hracX) {
                nepriatelX--;
            } else {
                nepriatelX++;
            }
        }
        this.nepriatel.setLocation(nepriatelX, nepriatelY);
    }
}

