package StibRide.model.repository.repository;

import StibRide.model.dto.StationsDto;
import StibRide.model.exception.RepositoryException;
import StibRide.model.repository.dao.StationsDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

/**
 * Classe de test de StationsRepository.
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class StationsRepositoryTest {

    @Mock
    private StationsDao mock;

    private final StationsDto existing_reference; // une ligne qui existe
    private final StationsDto missing_reference; // une ligne qui n'existe pas
    private final List<StationsDto> reference_subjects;

    public StationsRepositoryTest() {
        // Setup reference_subjects
        existing_reference = new StationsDto(8032, "PARC");
        missing_reference = new StationsDto(13, "XXX");

        reference_subjects = new ArrayList<>();
        reference_subjects.add(existing_reference);
    }

    @BeforeEach
    void init() throws RepositoryException {
        Mockito.lenient().when(mock.select(existing_reference.getKey())).thenReturn(existing_reference);
        Mockito.lenient().when(mock.select(missing_reference.getKey())).thenReturn(null);
        Mockito.lenient().when(mock.selectAll()).thenReturn(reference_subjects);
        Mockito.lenient().when(mock.select(null)).thenThrow(RepositoryException.class);
    }

    @Test
    public void testGetAll() throws Exception {
        //Arrange
        StationsRepository repository = new StationsRepository(mock);

        //Action
        List<StationsDto> result = repository.getAll();

        //Assert
        assertEquals(reference_subjects, result);
        Mockito.verify(mock, times(1)).selectAll();
    }

    @Test
    public void testGetExist() throws Exception {
        //Arrange
        int key = existing_reference.getKey();
        StationsRepository repository = new StationsRepository(mock);

        //Action
        StationsDto result = repository.get(key);

        //Assert
        assertEquals(existing_reference, result);
        Mockito.verify(mock, times(1)).select(key);
    }

    @Test
    public void testGetNotExist() throws Exception {
        //Arrange
        int key = missing_reference.getKey();
        StationsRepository repository = new StationsRepository(mock);

        //Action
        StationsDto result = repository.get(key);

        //Assert
        assertNull(result);
        Mockito.verify(mock, times(1)).select(key);
    }

    @Test
    public void testGetIncorrectParameter() throws Exception {
        //Arrange
        StationsRepository repository = new StationsRepository(mock);

        //Assert
        assertThrows(RepositoryException.class, () -> {

            //Action
            StationsDto result = repository.get(null);
        });

        Mockito.verify(mock, times(1)).select(null);
    }

    @Test
    public void testContainsExist() throws Exception {
        //Arrange
        int key = existing_reference.getKey();
        StationsRepository repository = new StationsRepository(mock);

        //Action
        boolean result = repository.contains(key);

        //Assert
        assertTrue(result);
        Mockito.verify(mock, times(1)).select(key);
    }

    @Test
    public void testContainsNotExist() throws Exception {
        //Arrange
        int key = missing_reference.getKey();
        StationsRepository repository = new StationsRepository(mock);

        //Action
        boolean result = repository.contains(key);

        //Assert
        assertFalse(result);
        Mockito.verify(mock, times(1)).select(key);
    }

    @Test
    public void testContainsIncorrectParameter() throws Exception {
        //Arrange
        StationsRepository repository = new StationsRepository(mock);

        //Assert
        assertThrows(RepositoryException.class, () -> {

            //Action
            boolean result = repository.contains(null);
        });

        Mockito.verify(mock, times(1)).select(null);
    }
}