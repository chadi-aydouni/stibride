package StibRide.model.repository.dao;

import StibRide.model.config.ConfigManager;
import StibRide.model.dto.StopsDto;
import StibRide.model.exception.RepositoryException;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test de StopsDao.
 */
class StopsDaoTest {

    private final List<StopsDto> reference_subjects;
    private StopsDao instance;

    public StopsDaoTest() {
        // Initialisation d'une liste pour représenter l'état de la BDD de base

        reference_subjects = new ArrayList<>();
        reference_subjects.add(new StopsDto(new Pair<>(1, 8382), 1));
        reference_subjects.add(new StopsDto(new Pair<>(1, 8742), 2));
        reference_subjects.add(new StopsDto(new Pair<>(1, 8292), 3));
        reference_subjects.add(new StopsDto(new Pair<>(1, 8282), 4));
        reference_subjects.add(new StopsDto(new Pair<>(1, 8272), 5));
        reference_subjects.add(new StopsDto(new Pair<>(1, 8012), 6));
        reference_subjects.add(new StopsDto(new Pair<>(1, 8022), 7));
        reference_subjects.add(new StopsDto(new Pair<>(1, 8032), 8));
        reference_subjects.add(new StopsDto(new Pair<>(1, 8042), 9));
        reference_subjects.add(new StopsDto(new Pair<>(1, 8052), 10));
        reference_subjects.add(new StopsDto(new Pair<>(1, 8062), 11));
        reference_subjects.add(new StopsDto(new Pair<>(1, 8072), 12));
        reference_subjects.add(new StopsDto(new Pair<>(1, 8082), 13));
        reference_subjects.add(new StopsDto(new Pair<>(1, 8092), 14));
        reference_subjects.add(new StopsDto(new Pair<>(1, 8102), 15));
        reference_subjects.add(new StopsDto(new Pair<>(1, 8112), 16));
        reference_subjects.add(new StopsDto(new Pair<>(1, 8122), 17));
        reference_subjects.add(new StopsDto(new Pair<>(1, 8132), 18));
        reference_subjects.add(new StopsDto(new Pair<>(1, 8142), 19));
        reference_subjects.add(new StopsDto(new Pair<>(1, 8152), 20));
        reference_subjects.add(new StopsDto(new Pair<>(1, 8161), 21));
        reference_subjects.add(new StopsDto(new Pair<>(2, 8764), 1));
        reference_subjects.add(new StopsDto(new Pair<>(2, 8754), 2));
        reference_subjects.add(new StopsDto(new Pair<>(2, 8742), 3));
        reference_subjects.add(new StopsDto(new Pair<>(2, 8382), 4));
        reference_subjects.add(new StopsDto(new Pair<>(2, 8372), 5));
        reference_subjects.add(new StopsDto(new Pair<>(2, 8362), 6));
        reference_subjects.add(new StopsDto(new Pair<>(2, 8352), 7));
        reference_subjects.add(new StopsDto(new Pair<>(2, 8342), 8));
        reference_subjects.add(new StopsDto(new Pair<>(2, 8332), 9));
        reference_subjects.add(new StopsDto(new Pair<>(2, 8322), 10));
        reference_subjects.add(new StopsDto(new Pair<>(2, 8312), 11));
        reference_subjects.add(new StopsDto(new Pair<>(2, 8302), 12));
        reference_subjects.add(new StopsDto(new Pair<>(2, 8042), 13));
        reference_subjects.add(new StopsDto(new Pair<>(2, 8412), 14));
        reference_subjects.add(new StopsDto(new Pair<>(2, 8422), 15));
        reference_subjects.add(new StopsDto(new Pair<>(2, 8432), 16));
        reference_subjects.add(new StopsDto(new Pair<>(2, 8442), 17));
        reference_subjects.add(new StopsDto(new Pair<>(2, 8462), 18));
        reference_subjects.add(new StopsDto(new Pair<>(2, 8472), 19));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8641), 1));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8652), 2));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8662), 3));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8672), 4));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8682), 5));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8692), 6));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8702), 7));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8712), 8));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8722), 9));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8382), 10));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8742), 11));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8292), 12));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8282), 13));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8272), 14));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8012), 15));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8022), 16));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8032), 17));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8042), 18));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8052), 19));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8062), 20));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8072), 21));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8202), 22));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8212), 23));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8222), 24));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8232), 25));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8242), 26));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8252), 27));
        reference_subjects.add(new StopsDto(new Pair<>(5, 8262), 28));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8833), 1));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8824), 2));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8814), 3));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8804), 4));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8794), 5));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8784), 6));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8774), 7));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8764), 8));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8754), 9));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8742), 10));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8382), 11));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8372), 12));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8362), 13));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8352), 14));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8342), 15));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8332), 16));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8322), 17));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8312), 18));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8302), 19));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8042), 20));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8412), 21));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8422), 22));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8432), 23));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8442), 24));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8462), 25));
        reference_subjects.add(new StopsDto(new Pair<>(6, 8472), 26));

        try {
            ConfigManager.getInstance().load();
            instance = StopsDao.getInstance();
        } catch (RepositoryException | IOException e) {
            org.junit.jupiter.api.Assertions.fail("Erreur de connexion à la base de données de test", e);
        }
    }

    @Test
    void testSelectAllExists() throws Exception {
        // Arrange
        List<StopsDto> result;

        // Action
        result = instance.selectAll();

        // Assert
        assertEquals(reference_subjects, result);
    }

    @Test
    void testSelectAllWhenTooMany() throws Exception {
        // Arrange
        List<StopsDto> result;
        List<StopsDto> expected = new ArrayList<>(reference_subjects);
        expected.add(new StopsDto(new Pair<>(26, 474654), 1));

        // Action
        result = instance.selectAll();

        // Assert
        assertNotEquals(expected, result);
    }

    @Test
    void testSelectAllWhenOneLess() throws Exception {
        // Arrange
        List<StopsDto> result;
        List<StopsDto> expected = new ArrayList<>(reference_subjects);
        expected.remove(0);

        // Action
        result = instance.selectAll();

        // Assert
        assertNotEquals(expected, result);
    }

    @Test
    void testSelectExist() throws Exception {
        //Arrange

        // On note que seul l'identifiant est pris en compte dans le equals de DTO (normalement 4)
        Pair<Integer, Integer> key = new Pair<>(1, 8282);
        StopsDto expected = new StopsDto(key, 3);

        //Action
        StopsDto result = instance.select(key);

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void testSelectNotExist() throws Exception {
        //Arrange
        StopsDto result;
        Pair<Integer, Integer> key = new Pair<>(1, 42);

        //Action
        result = instance.select(key);

        //Assert
        assertNull(result);
    }

    @Test
    void testSelectIncorrectParameter() {
        //Arrange
        Pair<Integer, Integer> incorrect = null;

        //Assert
        assertThrows(RepositoryException.class, () -> {

            //Action
            instance.select(incorrect);
        });
    }

    @Test
    void testSelectLineExist() throws Exception {
        //Arrange
        int key = 8742;
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(5);
        expected.add(6);

        //Action
        List<Integer> result = instance.selectLines(key);

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void testSelectLineWrongExpected() throws Exception {
        //Arrange
        int key = 8742;
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(5);

        //Action
        List<Integer> result = instance.selectLines(key);

        //Assert
        assertNotEquals(expected, result);
    }

    @Test
    void testSelectLineNotExist() throws Exception {
        //Arrange
        int key = 13;
        List<Integer> expected = new ArrayList<>();

        //Action
        List<Integer> result = instance.selectLines(key);

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void testSelectLineIncorrectParameter() {
        //Arrange
        Integer key = null;

        //Assert
        assertThrows(RepositoryException.class, () -> {

            //Action
            instance.selectLines(key);
        });
    }

}