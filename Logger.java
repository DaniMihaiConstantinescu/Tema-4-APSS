import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class Logger implements Observer {
    /**
     * Construieste o componenta client de iesire.
     * Noua componenta se inregistreaza pentru receptionarea evenimentelor de tip SHOW.
     */

    private String logFile;

    public Logger(String logFile) {
        // Inregistrare cu evenimente SHOW.
        EventBus.subscribeTo(EventBus.EV_SHOW, this);
        this.logFile = logFile;
    }

    /**
     * Handler-ul de evenimente ale componetei client de iesire.
     * La receptionarea evenimentului SHOW, obiectul de tip String primit
     * ca parametru este afisat la consola.
     *
     * @param event un obiect eveniment. (atentie: nu va fi referit direct)
     * @param param un obiect parametru al evenimentului. (va fi "cast" la tipul de date      * corespunzator
     */
    public void update(Observable event, Object param) {
        // Scrierea parametrului event (un sir de caractere) intr-un fisier.
        try {
            File file = new File(logFile);
            if (!file.exists()) {
                file.createNewFile();
            }

            try (FileWriter writer = new FileWriter(file, true)) {
                String timestamp = LocalDateTime.now().toString();
                writer.write("[" + timestamp + "] OUTPUT=" + param  + "\n");
            }

        } catch (IOException e) {
            System.err.println("Eroare la scrierea in fisierul de log: " + e.getMessage());
        }
    }

}