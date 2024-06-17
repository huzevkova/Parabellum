package predmety;

import predmety.energia.Energia;
import predmety.ochrana.Ochrana;
import predmety.zbrane.Zbran;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Trieda inventáry hráča. Ukladá všetky predmety, môže sa do neho pridávať alebo odoberať.
 *
 * @author Bianka S. Húževková
 * @version 2.0.0
 */
public class Inventar implements Serializable {

    private ArrayList<Zbran> zbrane;
    private ArrayList<Ochrana> ochrannePredmety;
    private ArrayList<Energia> predmetyDoplnajuceEnergiu;

    /**
     * Parametrický konštruktor. Inicializuje zoznamy predmetov - zbraní, ochranných predmetov a predmetov dopĺňajúcich energiu.
     *
     */
    public Inventar() {
        this.zbrane = new ArrayList<>();
        this.ochrannePredmety = new ArrayList<>();
        this.predmetyDoplnajuceEnergiu = new ArrayList<>();
    }

    /**
     * Pridá predmet do správneho zoznamu.
     *
     * @param predmet predmet na pridanie
     */
    public void pridajPredmet(Predmet predmet) {
        if (predmet instanceof Zbran) {
            this.zbrane.add((Zbran)predmet);
        } else if (predmet instanceof Ochrana) {
            this.ochrannePredmety.add((Ochrana)predmet);
        } else if (predmet instanceof Energia) {
            this.predmetyDoplnajuceEnergiu.add((Energia)predmet);
        }
    }

    /**
     * Nájde a odoberie daný predmet.
     *
     * @param predmet predmet na odobratie
     */
    public void odoberPredmet(Predmet predmet) {
        if (predmet instanceof Zbran) {
            this.zbrane.remove(predmet);
        } else if (predmet instanceof Ochrana) {
            this.ochrannePredmety.remove(predmet);
        } else if (predmet instanceof Energia) {
            this.predmetyDoplnajuceEnergiu.remove(predmet);
        }
    }

    /**
     * Vráti nemeniteľný zoznam zbraní.
     *
     * @return zbrane
     */
    public List<Zbran> getZbrane() {
        return Collections.unmodifiableList(this.zbrane);
    }

    /**
     * Vráti nemeniteľný zoznam ochranných predmetov.
     *
     * @return ochrany
     */
    public List<Ochrana> getOchrannePredmety() {
        return Collections.unmodifiableList(this.ochrannePredmety);
    }

    /**
     * Vráti nemeniteľný zoznam predmetov ktoré uzdravujú / dopĺňajú energiu.
     *
     * @return energia
     */
    public List<Energia> getPredmetyDoplnajuceEnergiu() {
        return Collections.unmodifiableList(this.predmetyDoplnajuceEnergiu);
    }

    /**
     * Vráti obsah inventára ako reťazec.
     *
     * @return obsah inventára
     */
    public String toString() {
        String s = "Inventár:\n";
        for (Zbran z : this.zbrane) {
            s += "  - " + z.getNazov() + "\n";
        }
        for (Ochrana o : this.ochrannePredmety) {
            s += "  - " + o.getNazov() + "\n";
        }
        for (Energia e : this.predmetyDoplnajuceEnergiu) {
            s += "  - " + e.getNazov() + "\n";
        }
        return s;
    }
}
