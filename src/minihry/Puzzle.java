package minihry;

import hlavnaHra.Hra;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * Puzzle minihra 3x3 s troma obtiažnosťami. Potomok triedy Minihra.
 *
 * @author Bianka S. Húževková
 * @version 2.0.0
 */
public class Puzzle extends Minihra {
    private boolean ukazujeSpravne;
    private boolean plynieCas;
    private String obtiaznost;
    private JButton[][] plochaNaSkladanie;
    private JButton[] dieliky;
    private JButton hotovo;
    private boolean jeVybratyDielik;
    private int vybratyDielik;
    private Platno platno;
    private JLabel cas;

    /**
     * Vypíše potrebné pokyny, obtiažnosť a spustí hru.
     */
    public Puzzle(Hra hra) {
        super(hra, 1000, "Pokús sa \"opraviť\" hrad poskladaním puzzle.");
        int odpoved = JOptionPane.showOptionDialog(null, "Vyber si obtiažnosť:", "Minihra", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"ľahká", "stredná", "ťažká"}, 2);
        switch (odpoved) {
            case 0:
                this.ukazujeSpravne = true;
                this.plynieCas = false;
                this.obtiaznost = "ľahká";
                break;
            case 1:
                this.ukazujeSpravne = false;
                this.plynieCas = false;
                this.obtiaznost = "stredná";
                break;
            case 2:
                this.ukazujeSpravne = false;
                this.plynieCas = true;
                this.obtiaznost = "ťažká";
                break;
        }
    }

    /**
     * Vytvorenie novej hry, inicializácia potrebných polí a vykreslenie plochy.
     */
    @Override
    public void novaMinihra() {
        this.platno = new Platno(700, 700);
        this.platno.setPozadie(Color.white);

        this.jeVybratyDielik = false;
        this.plochaNaSkladanie = new JButton[3][3];
        this.vytvorPlochuNaSkladanie();

        JLabel labelUroven = new JLabel();
        labelUroven.setText("Obtiažnosť: " + this.obtiaznost);
        labelUroven.setBounds(300, 550, 200, 40);
        this.platno.pridajObjekt(labelUroven);

        this.hotovo = new JButton("Hotovo");
        this.hotovo.addActionListener(this);
        this.hotovo.setBounds(310, 600, 100, 40);
        this.platno.pridajObjekt(this.hotovo);

        this.dieliky = new JButton[9];
        this.rozlozDieliky();

        if (this.plynieCas) {
            this.cas = new JLabel();
            this.cas.setFont(new Font("Comic Sans", Font.PLAIN, 20));
            this.cas.setText("1:00");
            this.cas.setBounds(0, 0, 60, 50);
            this.platno.pridajObjekt(this.cas);
            super.setPlatno(this.platno);
            super.getTimer().start();
        } else {
            super.setPlatno(this.platno);
        }
    }

    /**
     * Prekrytá metóda predka, spúšťa hru odznova. Využíva metódu predka.
     */
    @Override
    public void znova() {
        super.znova();
        JOptionPane.showMessageDialog(null, "Nestihol si. Skús znova", "Znova", JOptionPane.ERROR_MESSAGE);
        this.novaMinihra();
    }

    /**
     * Vykoná potrebené akcie - ak treba zavolá metódu na kontrolu puzzle, alebo upravenie časovača.
     * Vždy skontroluje klik.
     *
     * @param e udalosť, ktorá ju spustila
     */
    @Override
    public void vykonajAkciu(ActionEvent e) {
        if (e.getSource() == this.hotovo) {
            this.kontrola();
        }
        if (e.getSource() == super.getTimer()) {
            this.ovladanieCasovaca();
        }
        this.skontrolujKlik(e);
    }

    /**
     * Vytvorí plochu, do ktorej sa má skladať puzzle, ako mriežku tlačidiel a pridá ju do plochy.
     */
    public void vytvorPlochuNaSkladanie() {
        JPanel hlavnyPanel = new JPanel();
        hlavnyPanel.setBounds(150, 150, 390, 390);
        hlavnyPanel.setLayout(null);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.plochaNaSkladanie[i][j] = new JButton();
                this.plochaNaSkladanie[i][j].addActionListener(this);
                this.plochaNaSkladanie[i][j].setFocusable(false);
                this.plochaNaSkladanie[i][j].setBackground(Color.white);
                this.plochaNaSkladanie[i][j].setBounds(i * 130, j * 130, 130, 130);
                hlavnyPanel.add(this.plochaNaSkladanie[i][j]);
            }
        }
        this.platno.pridajObjekt(hlavnyPanel);
    }

    /**
     * Vytvorí všetky dieliky ako tlačidlá s obrázkami a náhodne ich rozhádže po obvode hracej plochy.
     */
    public void rozlozDieliky() {
        JButton[] nacitaneDieliky = new JButton[9];
        int a = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String nazov = "zdroje/minihry/" + i + "" + j + ".jpg";
                nacitaneDieliky[a] = new JButton();
                nacitaneDieliky[a].setIcon(new ImageIcon(nazov));
                a++;
            }

        }
        Random rnd = new Random();
        for (int i = 0; i < 9; i++) {
            while (this.dieliky[i] == null) {
                int r = rnd.nextInt(9);
                if (nacitaneDieliky[r] != null) {
                    this.dieliky[i] = nacitaneDieliky[r];
                    nacitaneDieliky[r] = null;
                }
            }
            if (i < 3) {
                this.dieliky[i].setBounds(130 * (i + 1) + 20 * i, 0, 130, 130);
            } else if (i < 6) {
                this.dieliky[i].setBounds(0, 150 * (i - 2) + 20 * (i - 3), 130, 130);
            } else {
                this.dieliky[i].setBounds(560, 150 * (i - 5) + 20 * (i - 6), 130, 130);
            }
            this.dieliky[i].addActionListener(this);
            this.platno.pridajObjekt(this.dieliky[i]);
        }
    }

    /**
     * Skontroluje či sú všetky dieliky na správnych políčkach.
     * Podľa toho vypíše vhodnú správu.
     */
    public void kontrola() {
        for (int r = 0; r < 3; r++) {
            for (int s = 0; s < 3; s++) {
                try {
                    int stlpec = Integer.parseInt(this.plochaNaSkladanie[r][s].getIcon().toString().replace("zdroje/minihry/", "").substring(0, 1));
                    int riadok = Integer.parseInt(this.plochaNaSkladanie[r][s].getIcon().toString().replace("zdroje/minihry/", "").substring(1, 2));
                    if (r != riadok || s != stlpec) {
                        JOptionPane.showMessageDialog(null, "Niečo máš zle.", "Znova", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Niečo máš zle.", "Znova", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Super, všetko je na svojom mieste.", "Koniec", JOptionPane.INFORMATION_MESSAGE);
        super.koniec();
    }

    /**
     * Skontroluje či je konkrétny dielik na správnom políčku.
     *
     * @param r riadok ktorý kontrolujeme
     * @param s stĺpec ktorý kontrolujeme
     * @param tlacidlo tlačidlo, políčko ktoré kontrolujeme
     */
    public void kontrola(int r, int s, JButton tlacidlo) {
        int stlpec = Integer.parseInt(tlacidlo.getIcon().toString().replace("zdroje/minihry/", "").substring(0, 1));
        int riadok = Integer.parseInt(tlacidlo.getIcon().toString().replace("zdroje/minihry/", "").substring(1, 2));
        if (r == riadok && s == stlpec) {
            this.plochaNaSkladanie[r][s].setBorder(new LineBorder(Color.GREEN, 10));
        } else {
            this.plochaNaSkladanie[r][s].setBorder(new LineBorder(Color.RED, 10));
        }
    }

    /**
     * Skontroluje kam sa kliklo.
     * Podľa toho buď označí vybraný dielik, vloží vybraný dielik na kliknuté voľné políčko,
     * alebo z políčka odstráni dielik, prípadne kontroluje správnosť.
     */
    public void skontrolujKlik(ActionEvent e) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (e.getSource() == this.plochaNaSkladanie[i][j]) {
                    if (this.jeVybratyDielik && this.plochaNaSkladanie[i][j].getIcon() == null) {
                        this.plochaNaSkladanie[i][j].setIcon(this.dieliky[this.vybratyDielik].getIcon());
                        this.dieliky[this.vybratyDielik].setIcon(null);
                        this.dieliky[this.vybratyDielik].setBorder(null);
                        this.jeVybratyDielik = false;
                        if (this.ukazujeSpravne) {
                            this.kontrola(i, j, this.plochaNaSkladanie[i][j]);
                        }
                        return;
                    } else if (this.plochaNaSkladanie[i][j].getIcon() != null) {
                        for (int k = 0; k < 9; k++) {
                            if (this.dieliky[k].getIcon() == null) {
                                this.dieliky[k].setIcon(this.plochaNaSkladanie[i][j].getIcon());
                                this.plochaNaSkladanie[i][j].setIcon(null);
                                this.plochaNaSkladanie[i][j].setBorder(new JButton().getBorder());
                                return;
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == this.dieliky[i]) {
                this.dieliky[i].setBorder(new LineBorder(Color.BLUE, 10));
                if (this.jeVybratyDielik && i != this.vybratyDielik) {
                    this.dieliky[this.vybratyDielik].setBorder(null);
                }
                this.jeVybratyDielik = true;
                this.vybratyDielik = i;
                return;
            }
        }
    }

    /**
     * Odpočítava čas. Vprípade že čas vyprší, spustí hru odznova.
     */
    public void ovladanieCasovaca() {
        int a = Integer.parseInt(this.cas.getText().substring(2));
        if (this.cas.getText().substring(2).equals("00")) {
            a = 59;
        } else {
            a--;
        }
        if (a == 0) {
            this.znova();
        } else if (a < 10) {
            this.cas.setText("0:0" + a);
        } else {
            this.cas.setText("0:" + a);
        }
    }

}
