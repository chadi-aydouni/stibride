package StibRide.model.repository.dao;

import StibRide.model.config.ConfigManager;
import StibRide.model.dto.LinesDto;
import StibRide.model.exception.RepositoryException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test de LinesDao.
 */
class LinesDaoTest {

    private final List<LinesDto> reference_subjects;
    private LinesDao instance;

    public LinesDaoTest() {
        // Initialisation d'une liste pour représenter l'état de la BDD de base

        reference_subjects = new ArrayList<>();
        reference_subjects.add(new LinesDto(1));
        reference_subjects.add(new LinesDto(2));
        reference_subjects.add(new LinesDto(5));
        reference_subjects.add(new LinesDto(6));

        try {
            ConfigManager.getInstance().load();
            instance = LinesDao.getInstance();
        } catch (RepositoryException | IOException e) {
            org.junit.jupiter.api.Assertions.fail("Erreur de connexion à la base de données de test", e);
        }
    }

    @Test
    void testSelectAllExists() throws Exception {
        // Arrange
        List<LinesDto> result;

        // Action
        result = instance.selectAll();

        // Assert
        assertEquals(reference_subjects, result);
    }

    @Test
    void testSelectAllWhenTooMany() throws Exception {
        // Arrange
        List<LinesDto> result;
        List<LinesDto> expected = List.of(new LinesDto(1),
                new LinesDto(2),
                new LinesDto(5),
                new LinesDto(6),
                new LinesDto(7));

        // Action
        result = instance.selectAll();

        // Assert
        assertNotEquals(expected, result);
    }

    @Test
    void testSelectAllWhenOneLess() throws Exception {
        // Arrange
        List<LinesDto> result;
        List<LinesDto> expected = List.of(new LinesDto(1),
                new LinesDto(2),
                new LinesDto(5));

        // Action
        result = instance.selectAll();

        // Assert
        assertNotEquals(expected, result);
    }

    @Test
    void testSelectExist() throws Exception {
        //Arrange
        LinesDto expected = new LinesDto(2);

        //Action
        LinesDto result = instance.select(2);

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void testSelectNotExist() throws Exception {
        //Arrange
        LinesDto result;

        //Action
        result = instance.select(18);

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