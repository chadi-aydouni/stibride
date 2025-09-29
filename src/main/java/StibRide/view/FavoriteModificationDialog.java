package StibRide.view;

import StibRide.model.dto.FavoritesDto;
import StibRide.model.dto.StationsDto;
import StibRide.model.exception.RepositoryException;
import StibRide.model.repository.repository.StationsRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import org.controlsfx.control.SearchableComboBox;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class FavoriteModificationDialog extends Dialog<FavoritesDto> {

    @FXML
    private TextField favoriteName;

    @FXML
    private SearchableComboBox<StationsDto> favoriteStart;

    @FXML
    private SearchableComboBox<StationsDto> favoriteEnd;

    private final StationsRepository station_repo;
    private final FavoritesDto current_favorite;
    private FavoritesDto new_favorite;


    public FavoriteModificationDialog(FavoritesDto current_favorite, StationsRepository station_repo) {

        this.station_repo = station_repo;
        this.current_favorite = current_favorite;

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/dialog.fxml"));
            loader.setController(this);

            DialogPane dialogPane = loader.load();
            setDialogPane(dialogPane);

            setResultConverter(buttonType -> {
                if(!Objects.equals(ButtonBar.ButtonData.OK_DONE, buttonType.getButtonData()) || favoriteName.getText().trim().isEmpty()){
                    return null;
                }

                new_favorite = new FavoritesDto(current_favorite.getKey(), favoriteStart.getValue().getKey(), favoriteEnd.getValue().getKey(), favoriteName.getText());
                return new_favorite;
            });

            initialize();
        }
        catch (IOException | RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    private void initialize() throws RepositoryException {
        this.setTitle("Modification de favori");
        this.initStyle(StageStyle.UTILITY);

        ButtonType confirm = new ButtonType("Confirmer", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().addAll(cancel, confirm);

        // Mise à jour des boîtes
        favoriteName.setText(current_favorite.getName());

        List<StationsDto> list = station_repo.getAll();

        for(StationsDto dto : list){
            favoriteStart.getItems().add(dto);
            favoriteEnd.getItems().add(dto);
        }

        favoriteStart.setValue(station_repo.get(current_favorite.getId_start_station()));
        favoriteEnd.setValue(station_repo.get(current_favorite.getId_end_station()));
    }
}
