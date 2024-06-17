package svet;

import predmety.Predmet;
import predmety.energia.Jablko;
import predmety.energia.Liek;
import predmety.ochrana.KuzloOchrany;
import predmety.zbrane.Mec;
import predmety.zbrane.Luk;
import predmety.zbrane.Noz;
import predmety.zbrane.Kuzlo;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Trieda generujúca a vytvárajúca mapu hry. Skladá sa z blokov cesta, prekážka a stena.
 *
 * @author Bianka S. Húževková
 * @version 1.0.0
 */
public class Mapa implements Serializable {
    private static final int STENA = 2;
    private static final int PREKAZKA = 1;
    private static final int CESTA = 0;
    private int rozmer;
    private int level;
    private int[][] mapa;
    private Blok[][] mapaBlokov;
    private ArrayList<Predmet> schovanePredmety;

    /**
     * Parametrický konštruktor, inicializuje atribúty, nachystá predmety a zavolá metódy na tvorbu mapy. Zapíše ju do súboru.
     *
     * @param rozmer velkosť mapy ako počet riadkov / stlpcov
     * @param level 1 až 3, určuje ako hustá má byť mapa
     */
    public Mapa(int rozmer, int level) {

        this.rozmer = rozmer;
        this.level = level;
        this.mapa = new int[this.rozmer][this.rozmer];
        this.schovanePredmety = new ArrayList<Predmet>() {
            {
                add(new Luk());
                add(new Noz());
                add(new Kuzlo());
                add(new KuzloOchrany());
                add(new Jablko());
                add(new Liek());
                add(new Mec());
            }
        };

        this.vygenerujMapu();
        try {
            this.zapis();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Vráti rozmer mapy.
     *
     * @return rozmer mapy
     */
    public int getRozmer() {
        return this.rozmer;
    }

    /**
     * Náhodne vygeneruje vždy inú mapu.
     * Po krajoch je vždy stena a v každom druhom riadku je každé druhé políčko taktiež stena, vytvárajúc niečo ako mriežku.
     * Keďže hráč začína v ľavom hornom rohu, to políčko bude vždy cesta a v okolí
     * vytvorí min 4 miestny priestor na pohyb - buď do jednej alebo druhej strany.
     * Podľa úrovne mapy pridáva menej alebo viac prekážok.
     * Cesta = 0, Prekazka = 1, Stena = 2
     */
    public void vygenerujMapu() {
        Random rnd = new Random();

        for (int i = 0; i < this.rozmer; i++) {
            for (int j = 0; j < this.rozmer; j++) {
                if (i == 0 || i == this.rozmer - 1 || j == 0 || j == this.rozmer - 1) {
                    this.mapa[i][j] = Mapa.STENA;
                } else if (i % 2 == 0 && j % 2 == 0) {
                    this.mapa[i][j] = Mapa.STENA;
                } else if (i == 1 && j == 1) {
                    this.mapa[i][j] = Mapa.CESTA;
                } else if (rnd.nextInt(2) == 0) {
                    if (i < 3 && j < 4 && j != 1) {
                        this.mapa[i][j] = Mapa.CESTA;
                    } else {
                        this.mapa[i][j] = rnd.nextInt(2);
                    }
                } else {
                    if (i < 4 && j < 3 && i != 1) {
                        this.mapa[i][j] = Mapa.CESTA;
                    } else {
                        if (this.level == 3) {
                            this.mapa[i][j] = rnd.nextInt(2);
                        } else if (this.level == 2) {
                            int cislo = rnd.nextInt(100);
                            if (cislo < 62) {
                                this.mapa[i][j] = Mapa.PREKAZKA;
                            } else {
                                this.mapa[i][j] = Mapa.CESTA;
                            }
                        } else if (this.level == 1) {
                            int cislo = rnd.nextInt(100);
                            if (cislo < 75) {
                                this.mapa[i][j] = Mapa.PREKAZKA;
                            } else {
                                this.mapa[i][j] = Mapa.CESTA;
                            }
                        }

                    }
                }
            }
        }
    }

    /**
     * Vráti mapu vo forme dvojrozmerného pola inštancií Blokov.
     *
     * @return dvojrozmerné pole Blokov
     */
    public Blok[][] getMapaBlokov() {
        return this.mapaBlokov;
    }

    /**
     * Vytvorí grafický obraz vygenerovanej mapy a náhodne do nej poschováva predmety z množiny.
     *
     * @return obraz mapy
     */
    public JPanel vytvorObrazMapy() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(700, 700));
        panel.setLayout(new GridLayout(this.rozmer, this.rozmer));

        this.mapaBlokov = new Blok[this.rozmer][this.rozmer];

        Random rnd = new Random();
        for (int i = 0; i < this.rozmer; i++) {
            for (int j = 0; j < this.rozmer; j++) {
                Blok blok = null;
                switch (this.mapa[i][j]) {
                    case 0:
                        blok = new Cesta("zdroje/prostredie/cesta.jpg");
                        break;
                    case 1:
                        blok = new Prekazka("zdroje/prostredie/prekazka.jpg", "zdroje/prostredie/cesta.jpg");
                        if (rnd.nextInt(10) > 6 && this.schovanePredmety.size() > 0) {
                            ((Prekazka)blok).schovajPredmet(this.schovanePredmety.remove(rnd.nextInt(this.schovanePredmety.size())));
                        }
                        break;
                    case 2:
                        blok = new Stena("zdroje/prostredie/stena.jpg");
                        break;
                }
                this.mapaBlokov[i][j] = blok;
                panel.add(blok.getObraz());
            }
        }
        return panel;
    }

    /**
     * Zapíše vygenerovanú mapu do súboru mapa.txt.
     *
     * @throws IOException
     */
    public void zapis() throws IOException {
        FileWriter file = new FileWriter("mapa.txt");
        PrintWriter writer = new PrintWriter(file);

        for (int[] riadok : this.mapa) {
            for (int stlpec : riadok) {
                writer.print(stlpec);
            }
            writer.println();
        }

        writer.close();
    }

}
