package StibRide.model.repository.dao;

import StibRide.model.config.ConfigManager;
import StibRide.model.dto.FavoritesDto;
import StibRide.model.exception.RepositoryException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test de FavoriteDao.
 */
class FavoritesDaoTest {

    private final List<FavoritesDto> reference_subjects;
    private FavoritesDao instance;

    public FavoritesDaoTest() {

        // Initialisation d'une liste pour représenter l'état de la BDD de base

        reference_subjects = new ArrayList<>();
        reference_subjects.add(new FavoritesDto(1, 8012, 8142, "De brouckère vers Alma"));
        reference_subjects.add(new FavoritesDto(2, 8462, 8432, "Ribaucourt vers Rogier"));
        reference_subjects.add(new FavoritesDto(3, 8332, 8412, "Hotel vers Madou"));

        try {
            ConfigManager.getInstance().load();
            instance = FavoritesDao.getInstance();
        } catch (RepositoryException | IOException e) {
            org.junit.jupiter.api.Assertions.fail("Erreur de connexion à la base de données de test", e);
        }
    }

    @Test
    public void testInsertExist() throws Exception {

        // La différence entre update et insert étant gérée au niveau du Repository, on ajoute simplement une
        // entrée, quitte à ce que le matricule existe déjà ou non.

        // Arrange
        FavoritesDto new_favorite = new FavoritesDto(4, 8302, 8322, "Trone vers Louise");

        // Action
        instance.insert(new_favorite); // Insertion avec clé qui s'autoincrémente, on ne prend pas en compte l'id fourni.

        assertEquals(new_favorite, instance.select(4));
    }

    @Test
    public void testInsertWhenStationNotInTable() {
        // Arrange
        FavoritesDto new_favorite = new FavoritesDto(1, 13, 8322, "Trone vers Louise");

        //Assert
        assertThrows(RepositoryException.class, () -> {

            //Action
            instance.insert(new_favorite);
        });

    }

    @Test
    public void testInsertNotExist() throws Exception {
        // Arrange
        FavoritesDto new_favorite = new FavoritesDto(4, 8302, 8322, "Trone vers Louise");

        // Action
        instance.insert(new_favorite);

        //Assert
        assertEquals(new_favorite, instance.select(4));
    }

    @Test
    public void testInsertIncorrectParameter() {
        // Arrange
        FavoritesDto new_favorite = null;

        //Assert
        assertThrows(RepositoryException.class, () -> {

            //Action
            instance.insert(new_favorite);
        });
    }

    @Test
    public void testDeleteExist() throws Exception {
        // Action
        instance.delete(4);

        //Assert
        assertNull(instance.select(4));
    }

    @Test
    public void testDeleteNotExist() throws Exception {

        //Action
        assertNull(instance.select(4));
        instance.delete(0);

        //Assert
        assertNull(instance.select(0));
    }

    @Test
    public void testDeleteIncorrectParameter() {
        // Arrange
        Integer key = null;

        //Assert
        assertThrows(RepositoryException.class, () -> {

            //Action
            instance.delete(key);
        });
    }

    @Test
    public void testUpdateExist() throws Exception {
        // Arrange
        FavoritesDto new_favorite = new FavoritesDto(1, 8302, 8322, "Trone vers Louise");

        // Action
        instance.update(new_favorite);

        //Assert
        assertEquals(new_favorite, instance.select(1));
    }

    @Test
    public void testUpdateNotExist() throws Exception {

        // La méthode update ne fait que mettre à jour : si cela n'existe pas, ce n'est pas rajouté.

        // Arrange
        FavoritesDto new_favorite = new FavoritesDto(12, 8302, 8322, "Trone vers Louise");

        // Action
        instance.update(new_favorite);

        //Assert
        assertNull(instance.select(12));

    }

    @Test
    public void tesUpdateIncorrectParameter() {
        // Arrange
        FavoritesDto new_favorite = null;

        //Assert
        assertThrows(RepositoryException.class, () -> {

            //Action
            instance.update(new_favorite);
        });
    }

    @Test
    void testSelectAllExists() throws Exception {
        // Arrange
        List<FavoritesDto> result;

        // Action
        result = instance.selectAll();

        // Assert
        assertEquals(reference_subjects, result);
    }

    @Test
    void testSelectAllWhenTooMany() throws Exception {
        // Arrange
        List<FavoritesDto> result;
        List<FavoritesDto> expected = new ArrayList<>(reference_subjects);
        expected.add(new FavoritesDto(4, 8382, 8342, "Gare de l'ouest vers PDH"));

        // Action
        result = instance.selectAll();

        // Assert
        assertNotEquals(expected, result);
    }

    @Test
    void testSelectAllWhenOneLess() throws Exception {
        // Arrange
        List<FavoritesDto> result;
        List<FavoritesDto> expected = new ArrayList<>(reference_subjects);
        expected.remove(0);

        // Action
        result = instance.selectAll();

        // Assert
        assertNotEquals(expected, result);
    }

    @Test
    void testSelectExist() throws Exception {
        //Arrange

        // On note que seul l'identifiant est pris en compte dans le equals de DTO.
        FavoritesDto expected = new FavoritesDto(1, 8382, 8342, "Gare de l'ouest vers PDH");

        //Action
        FavoritesDto result = instance.select(1);

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void testSelectNotExist() throws Exception {
        //Arrange
        FavoritesDto result;

        //Action
        result = instance.select(43);

        //Assert
        assertNull(result);
    }

    @Test
    void testSelectIncorrectParameter() {
        //Arrange
        Integer incorrect = null;

        //Assert
        assertThrows(RepositoryException.class, () -> {

            //Action
            instance.select(incorrect);
        });
    }
}