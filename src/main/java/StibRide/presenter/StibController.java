package StibRide.presenter;

import StibRide.model.Model;
import StibRide.model.dto.FavoritesDto;
import StibRide.model.dto.StationsDto;
import StibRide.model.exception.RepositoryException;
import StibRide.model.repository.repository.FavoritesRepository;
import StibRide.model.repository.repository.StationsRepository;
import StibRide.model.repository.repository.StopsRepository;
import StibRide.model.table.TableContent;
import StibRide.observer.Observable;
import StibRide.observer.Observer;
import StibRide.view.FavoriteModificationDialog;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;

/**
 * Presenter dans le pattern MVP (la vue est le fichier .fxml).
 */
public class StibController implements Observer {

    private final Model model;
    private final StopsRepository stop_repo;
    private final StationsRepository station_repo;
    private final FavoritesRepository favorite_repo;

    @FXML
    private SearchableComboBox<StationsDto> startComboBox;

    @FXML
    private SearchableComboBox<StationsDto> endComboBox;

    @FXML
    private ComboBox<FavoritesDto> favoriteComboBox;

    @FXML
    private Button loadFavoriteButton;

    @FXML
    private Button deleteFavoriteButton;

    @FXML
    private Button modifyFavoriteButton;

    @FXML
    private TableView<TableContent> table;

    @FXML
    private TableColumn<TableContent, String> stationsCol;

    @FXML
    private TableColumn<TableContent, List<Integer>> linesCol;

    @FXML
    private Label stationsAmountLabel;

    /**
     * Constructeur de la classe.
     */
    public StibController() throws RepositoryException {

        station_repo = new StationsRepository();
        stop_repo = new StopsRepository();
        favorite_repo = new FavoritesRepository();

        this.model = new Model(favorite_repo);
        model.registerObserver(this);
    }


    /**
     * Initialise tous les éléments de la vue. Est appelé par JavaFX à la création du contrôleur.
     */
    public void initialize() throws RepositoryException {
        initializeTable();
        initializeStartEndComboBoxes();
        initializeFavoriteComboBox();
    }

    /**
     * Initialise la table et ses colonnes.
     */
    private void initializeTable() {
        stationsCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        linesCol.setCellValueFactory(new PropertyValueFactory<>("lines"));
    }

    /**
     * Initialise les ComboBox avec les différentes stations de départ et d'arrivée.
     */
    private void initializeStartEndComboBoxes() throws RepositoryException {
        for (StationsDto dto : station_repo.getAll()) {
            startComboBox.getItems().add(dto);
            endComboBox.getItems().add(dto);
        }

        if (!startComboBox.getItems().isEmpty() && !endComboBox.getItems().isEmpty()) {
            startComboBox.setValue(startComboBox.getItems().get(0));
            endComboBox.setValue(endComboBox.getItems().get(0));
        }
    }

    /**
     * Initialise la ComboBox de favoris.
     */
    private void initializeFavoriteComboBox() throws RepositoryException {

        // Insertion des favoris dans la ComboBox.
        final ObservableList<FavoritesDto> options = FXCollections.observableArrayList();
        options.setAll(favorite_repo.getAll());
        favoriteComboBox.setItems(options);

        // Mise à jour de l'affichage
        updateButtons();

        if (!favoriteComboBox.getItems().isEmpty()) {
            favoriteComboBox.setValue(favoriteComboBox.getItems().get(0));
        }

        // Permet l'affichage du nom des stations à côté des noms de favoris
        favoriteComboBox.setConverter(new StringConverter<>() {

            @Override
            public String toString(FavoritesDto favoritesDto) {

                StringBuilder stringBuilder = new StringBuilder();

                if (favoritesDto != null) {
                    stringBuilder.append(favoritesDto.getName()).append(" (");

                    try {
                        String start_station_name = station_repo.get(favoritesDto.getId_start_station()).toString();
                        stringBuilder.append(start_station_name).append(" -> ");

                        String end_station_name = station_repo.get(favoritesDto.getId_end_station()).toString();
                        stringBuilder.append(end_station_name).append(")");
                    } catch (RepositoryException e) {
                        e.printStackTrace();
                    }
                }
                return stringBuilder.toString();
            }

            @Override
            public FavoritesDto fromString(String s) {
                return null;
            }
        });
    }


    /**
     * Gère le comportement du programme lorsque l'on clique sur le bouton de recherche de trajet.
     *
     * @param event l'évenement de clic.
     */
    @FXML
    private void handleStartButtonAction(ActionEvent event) throws RepositoryException {
        if (startComboBox.getValue() != null && endComboBox.getValue() != null) {
            StationsDto start_station = startComboBox.getValue();
            StationsDto end_station = endComboBox.getValue();
            model.generateRide(start_station, end_station);
        }
    }

    /**
     * Gère le comportement du programme quand on clique sur le bouton d'ajout de favori.
     *
     * @param event l'évenement de clic.
     */
    @FXML
    private void handleAddFavoriteButton(ActionEvent event) {
        StationsDto start_station = startComboBox.getValue();
        StationsDto end_station = endComboBox.getValue();

        // Toujours vérifié à priori mais au cas où...
        if (startComboBox.getValue() != null && endComboBox.getValue() != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Nom du favori");
            dialog.setContentText("Veuillez entrer le nom du favori :");
            Optional<String> favorite_name = dialog.showAndWait();

            favorite_name.ifPresent(string -> {
                if (!favorite_name.get().isEmpty()) {
                    try {
                        model.addFavorite(start_station.getKey(), end_station.getKey(), favorite_name.get());
                    } catch (RepositoryException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * Gère le comportement du programme quand on charge un favori.
     *
     * @param event l'évenement de clic.
     */
    @FXML
    private void handleLoadFavoriteButton(ActionEvent event) throws RepositoryException {
        if (favoriteComboBox.getValue() != null) {
            StationsDto start_station = station_repo.get(favoriteComboBox.getValue().getId_start_station());
            StationsDto end_station = station_repo.get(favoriteComboBox.getValue().getId_end_station());
            model.generateRide(start_station, end_station);
        }
    }

    /**
     * Gère le comportement du programme quand on supprime un favori.
     *
     * @param event l'évenement de clic.
     */
    @FXML
    private void handleDeleteFavoriteButton(ActionEvent event) throws RepositoryException {
        if (favoriteComboBox.getValue() != null) {
            model.deleteFavorite(favoriteComboBox.getValue());
        }
    }

    /**
     * Gère le comportement du programme quand on modifie un favori.
     *
     * @param event l'évenement de clic.
     */
    @FXML
    private void handleModifyFavoriteButton(ActionEvent event) throws IOException {
        FavoritesDto current_favorite = favoriteComboBox.getValue();

        FavoriteModificationDialog dialog = new FavoriteModificationDialog(current_favorite, station_repo);
        dialog.showAndWait().ifPresent(result -> {
            try {
                model.updateFavorite(new FavoritesDto(result.getKey(), result.getId_start_station(), result.getId_end_station(), result.getName()));
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        });

    }

    /**
     * Gère le bouton pour quitter le programme (barre de menu).
     *
     * @param event l'événement de clic sur l'item du menu.
     */
    @FXML
    private void handleQuitButtonAction(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Gère le bouton pour avoir plus d'informations (barre de menu).
     *
     * @param event l'événement de clic sur l'item du menu.
     */
    @FXML
    private void handleAboutButtonAction(ActionEvent event) {
        String str = "Recherche de chemin dans le réseau de métro de la STIB " +
                "du cours d'ATLG4 avec TNI.";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("A propos...");
        alert.setContentText(str);
        alert.showAndWait();
    }

    /**
     * Mets à jour l'affichage avec les informations du modèle (ici, un nombre).
     *
     * @param observable l'objet observé (modèle).
     * @param arg        des arguments passés à la méthode.
     */
    @Override
    public void update(Observable observable, Object arg) throws RepositoryException {
        Model model = (Model) observable;

        // 1 = recherche de trajet
        // 2 = ajout de favori
        // 3 = suppression de favori
        // 4 = modification de favori

        switch ((Integer) arg) {
            case 1:
                updateWhenSearch(model);
                break;
            case 2:
                updateWhenAddFavorite(model);
                break;
            case 3:
                updateWhenDeleteFavorite(model);
                break;
            case 4:
                updateWhenModifyFavorite(model);
                break;
            default:
                break;
        }
    }

    /**
     * Mets à jour l'affichage quand il s'agit d'une recherche.
     *
     * @param model le modèle.
     */
    private void updateWhenSearch(Model model) throws RepositoryException {
        List<StationsDto> shortestPath = model.getShortestPath();
        table.getItems().clear();

        for (StationsDto dto : shortestPath) {
            TableContent content = new TableContent(dto.getName(), stop_repo.getLines(dto.getKey()));
            table.getItems().add(content);
        }

        stationsAmountLabel.setText("Nombre de stations : " + shortestPath.size());
    }

    /**
     * Mets à jour l'affichage quand il s'agit d'un ajout de favori.
     *
     * @param model le modèle.
     */
    private void updateWhenAddFavorite(Model model) {
        FavoritesDto lastFavorite = model.getLastFavorite();
        favoriteComboBox.getItems().add(lastFavorite);
        favoriteComboBox.setValue(lastFavorite);
        updateButtons();
    }

    /**
     * Mets à jour l'affichage quand il s'agit d'une suppression de favori.
     *
     * @param model le modèle.
     */
    private void updateWhenDeleteFavorite(Model model) {

        // J'utilise des index pour pouvoir jouer avec le fait de mettre à jour le contenu
        // de la box afin que l'élément suivant soit sélectionné automatiquement
        // (pratique quand on veut en supprimer beaucoup).

        int index = 0;
        ObservableList<FavoritesDto> list = favoriteComboBox.getItems();

        for (int i = 0; i < list.size(); i++) {
            if (model.getDeleteFavorite().getKey() == list.get(i).getKey()) {
                index = i;
                break;
            }
        }

        list.remove(index);

        if (index != list.size()) {
            favoriteComboBox.setValue(list.get(index));
        }

        table.getItems().clear();
        updateButtons();
    }

    /**
     * Mets à jour l'affichage quand il s'agit d'une modification de favori.
     *
     * @param model le modèle.
     */
    private void updateWhenModifyFavorite(Model model) {
        FavoritesDto lastFavorite = model.getLastFavorite();
        favoriteComboBox.getItems().remove(favoriteComboBox.getValue());
        favoriteComboBox.getItems().add(lastFavorite);
        favoriteComboBox.setValue(lastFavorite);
        table.getItems().clear();
    }

    /**
     * Active ou désactive les boutons liés aux favoris en fonction du contenu de la box.
     */
    private void updateButtons() {
        if (favoriteComboBox.getItems().isEmpty()) {
            favoriteComboBox.setDisable(true);
            loadFavoriteButton.setDisable(true);
            deleteFavoriteButton.setDisable(true);
            modifyFavoriteButton.setDisable(true);
        } else {
            favoriteComboBox.setDisable(false);
            loadFavoriteButton.setDisable(false);
            deleteFavoriteButton.setDisable(false);
            modifyFavoriteButton.setDisable(false);
        }
    }
}
