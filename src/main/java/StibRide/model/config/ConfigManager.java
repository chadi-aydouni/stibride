package StibRide.model.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Ce signgleton donne l'accès à toutes les propriétés du fichier
 * model.config.properties.
 *
 * @author jlc (merci à lui)
 */
public class ConfigManager {

    private ConfigManager() {
        prop = new Properties();
        url = getClass().getClassLoader().getResource(FILE).getFile();
    }

    private static final String FILE = "./config/config.properties";

    private final Properties prop;

    private final String url;

    /**
     * Charge les propriétés via l'URL.
     *
     * @throws IOException si aucun fichier n'est trouvé.
     */
    public void load() throws IOException {
        try (InputStream input = new FileInputStream(url)) {
            prop.load(input);
        } catch (IOException ex) {
            throw new IOException("Chargement configuration impossible ", ex);
        }
    }

    /**
     * Renvoie la valeur à partir de la clé.
     *
     * @param name la clé à trouver.
     * @return la valeur de la paire clé-valeur.
     */
    public String getProperties(String name) {
        return prop.getProperty(name);
    }

    /**
     * Renvoie l'instance du singleton.
     *
     * @return l'instance du singleton.
     */
    public static ConfigManager getInstance() {
        return ConfigManagerHolder.INSTANCE;
    }

    private static class ConfigManagerHolder {
        private static final ConfigManager INSTANCE = new ConfigManager();
    }
}
