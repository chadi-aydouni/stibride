package StibRide.model.repository.dao;

import StibRide.model.config.ConfigManager;
import StibRide.model.dto.StationsDto;
import StibRide.model.exception.RepositoryException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test de StationsDao.
 */
class StationsDaoTest {

    private final List<StationsDto> reference_subjects;
    private StationsDao instance;

    public StationsDaoTest() {
        // Initialisation d'une liste pour représenter l'état de la BDD de base

        reference_subjects = new ArrayList<>();
        reference_subjects.add(new StationsDto(8012, "DE BROUCKERE"));
        reference_subjects.add(new StationsDto(8022, "GARE CENTRALE"));
        reference_subjects.add(new StationsDto(8032, "PARC"));
        reference_subjects.add(new StationsDto(8042, "ARTS-LOI"));
        reference_subjects.add(new StationsDto(8052, "MAELBEEK"));
        reference_subjects.add(new StationsDto(8062, "SCHUMAN"));
        reference_subjects.add(new StationsDto(8072, "MERODE"));
        reference_subjects.add(new StationsDto(8082, "MONTGOMERY"));
        reference_subjects.add(new StationsDto(8092, "JOSEPH.-CHARLOTTE"));
        reference_subjects.add(new StationsDto(8102, "GRIBAUMONT"));
        reference_subjects.add(new StationsDto(8112, "TOMBERG"));
        reference_subjects.add(new StationsDto(8122, "ROODEBEEK"));
        reference_subjects.add(new StationsDto(8132, "VANDERVELDE"));
        reference_subjects.add(new StationsDto(8142, "ALMA"));
        reference_subjects.add(new StationsDto(8152, "CRAINHEM"));
        reference_subjects.add(new StationsDto(8161, "STOCKEL"));
        reference_subjects.add(new StationsDto(8202, "THIEFFRY"));
        reference_subjects.add(new StationsDto(8212, "PETILLON"));
        reference_subjects.add(new StationsDto(8222, "HANKAR"));
        reference_subjects.add(new StationsDto(8232, "DELTA"));
        reference_subjects.add(new StationsDto(8242, "BEAULIEU"));
        reference_subjects.add(new StationsDto(8252, "DEMEY"));
        reference_subjects.add(new StationsDto(8262, "HERRMANN-DEBROUX"));
        reference_subjects.add(new StationsDto(8272, "SAINTE-CATHERINE"));
        reference_subjects.add(new StationsDto(8282, "COMTE DE FLANDRE"));
        reference_subjects.add(new StationsDto(8292, "ETANGS NOIRS"));
        reference_subjects.add(new StationsDto(8302, "TRONE"));
        reference_subjects.add(new StationsDto(8312, "PORTE DE NAMUR"));
        reference_subjects.add(new StationsDto(8322, "LOUISE"));
        reference_subjects.add(new StationsDto(8332, "HOTEL DES MONNAIES"));
        reference_subjects.add(new StationsDto(8342, "PORTE DE HAL"));
        reference_subjects.add(new StationsDto(8352, "GARE DU MIDI"));
        reference_subjects.add(new StationsDto(8362, "CLEMENCEAU"));
        reference_subjects.add(new StationsDto(8372, "DELACROIX"));
        reference_subjects.add(new StationsDto(8382, "GARE DE L'OUEST"));
        reference_subjects.add(new StationsDto(8412, "MADOU"));
        reference_subjects.add(new StationsDto(8422, "BOTANIQUE"));
        reference_subjects.add(new StationsDto(8432, "ROGIER"));
        reference_subjects.add(new StationsDto(8442, "YSER"));
        reference_subjects.add(new StationsDto(8462, "RIBAUCOURT"));
        reference_subjects.add(new StationsDto(8472, "ELISABETH"));
        reference_subjects.add(new StationsDto(8641, "ERASME"));
        reference_subjects.add(new StationsDto(8652, "EDDY MERCKX"));
        reference_subjects.add(new StationsDto(8662, "CERIA"));
        reference_subjects.add(new StationsDto(8672, "LA ROUE"));
        reference_subjects.add(new StationsDto(8682, "BIZET"));
        reference_subjects.add(new StationsDto(8692, "VEEWEYDE"));
        reference_subjects.add(new StationsDto(8702, "SAINT-GUIDON"));
        reference_subjects.add(new StationsDto(8712, "AUMALE"));
        reference_subjects.add(new StationsDto(8722, "JACQUES BREL"));
        reference_subjects.add(new StationsDto(8742, "BEEKKANT"));
        reference_subjects.add(new StationsDto(8754, "OSSEGHEM"));
        reference_subjects.add(new StationsDto(8764, "SIMONIS"));
        reference_subjects.add(new StationsDto(8774, "BELGICA"));
        reference_subjects.add(new StationsDto(8784, "PANNENHUIS"));
        reference_subjects.add(new StationsDto(8794, "BOCKSTAEL"));
        reference_subjects.add(new StationsDto(8804, "STUYVENBERGH"));
        reference_subjects.add(new StationsDto(8814, "HOUBA-BRUGMANN"));
        reference_subjects.add(new StationsDto(8824, "HEYSEL"));
        reference_subjects.add(new StationsDto(8833, "ROI BAUDOUIN"));

        try {
            ConfigManager.getInstance().load();
            instance = StationsDao.getInstance();
        } catch (RepositoryException | IOException e) {
            org.junit.jupiter.api.Assertions.fail("Erreur de connexion à la base de données de test", e);
        }
    }

    @Test
    void testSelectAllExists() throws Exception {
        // Arrange
        List<StationsDto> result;

        // Action
        result = instance.selectAll();

        // Assert
        assertEquals(reference_subjects, result);
    }

    @Test
    void testSelectAllWhenTooMany() throws Exception {
        // Arrange
        List<StationsDto> result;
        List<StationsDto> expected = new ArrayList<>(reference_subjects);
        expected.add(new StationsDto(13, "XXX"));

        // Action
        result = instance.selectAll();

        // Assert
        assertNotEquals(expected, result);
    }

    @Test
    void testSelectAllWhenOneLess() throws Exception {
        // Arrange
        List<StationsDto> result;
        List<StationsDto> expected = new ArrayList<>(reference_subjects);
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
        StationsDto expected = new StationsDto(8032, "CHOCOLAT");

        //Action
        StationsDto result = instance.select(8032);

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void testSelectNotExist() throws Exception {
        //Arrange
        StationsDto result;

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