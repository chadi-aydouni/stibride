package StibRide;

import StibRide.model.config.ConfigManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Application JavaFX de calcul et enregistrement de trajets dans le réseau de metro STIB.
 */
public class App extends Application {

    /**
     * Lance l'application JavaFX sur le stage donné en paramètre.
     *
     * @param stage le stage de l'application.
     * @throws IOException exception d'entrée/sortie
     */
    @Override
    public void start(Stage stage) throws IOException {

        // Chargement de la BDD
        ConfigManager.getInstance().load();
        String dbUrl = ConfigManager.getInstance().getProperties("db.url");
        System.out.println("Base de données stockée : " + dbUrl);

        // Initialisation de la vue
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/view.fxml"));
        VBox root = loader.load();

        // Affichage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("43729 - Recherche de chemin dans le réseau de métro de la STIB");
        stage.getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("img/logo.png"))));
        stage.show();

        // Clic sur bouton [X]
        stage.setOnCloseRequest(we -> {
            Platform.exit();
            System.exit(0);
        });

    }

    /**
     * Méthode principale de l'application.
     *
     * @param args liste d'arguments.
     */
    public static void main(String[] args) {
        launch();
    }

}