package hlavnaHra;

import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Trieda uvadzajúca hru. Má na starosti prípadné načítanie uloženej hry zo súboru.
 */
public class Uvod {

    /**
     * Bezparametrický konštruktor, spýta sa či che používateľ načítať uloženú hru, a ak sa dá, načíta ju a spustí.
     * Inak spustí hru normálne od začiatku.
     */
    public Uvod() {
        int odpoved = JOptionPane.showOptionDialog(null, "Chceš načítať uloženú hru?", "Úvod", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Áno.", "Nie."}, 0);
        if (odpoved == 0) {
            File file = new File("save.txt");
            try {
                FileInputStream stream = new FileInputStream(file);
                ObjectInputStream citac = new ObjectInputStream(stream);
                Hra hra = (Hra)citac.readObject();
                hra.spustiUlozenu();
                citac.close();
                stream.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Ľutujem, nastal nejaký problém s uloženým súborom. Hra začne od začiatku.");
                Hra hra = new Hra(1);
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Žiadna hra ešte uložená nie je. Hra začne od začiatku.");
                Hra hra = new Hra(1);
            }
        } else {
            Hra hra = new Hra(1);
        }
    }
}
