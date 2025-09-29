package StibRide.model.repository.repository;

import StibRide.model.dto.StopsDto;
import StibRide.model.exception.RepositoryException;
import StibRide.model.repository.dao.StopsDao;
import javafx.util.Pair;
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
 * Classe de test de StopsRepository.
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class StopsRepositoryTest {

    @Mock
    private StopsDao mock;

    private final StopsDto existing_reference; // une ligne qui existe
    private final StopsDto missing_reference; // une ligne qui n'existe pas
    private final List<StopsDto> reference_subjects;
    private final List<Integer> lines_reference_subjects;

    public StopsRepositoryTest() {
        // Setup reference_subjects
        existing_reference = new StopsDto(new Pair<>(1, 8742), 2);
        missing_reference = new StopsDto(new Pair<>(1, 13), 3);

        reference_subjects = new ArrayList<>();
        reference_subjects.add(existing_reference);

        lines_reference_subjects = new ArrayList<>();
        lines_reference_subjects.add(1);
        lines_reference_subjects.add(2);
        lines_reference_subjects.add(5);
        lines_reference_subjects.add(6);
    }

    @BeforeEach
    void init() throws RepositoryException {
        Mockito.lenient().when(mock.select(existing_reference.getKey())).thenReturn(existing_reference);
        Mockito.lenient().when(mock.select(missing_reference.getKey())).thenReturn(null);
        Mockito.lenient().when(mock.selectAll()).thenReturn(reference_subjects);
        Mockito.lenient().when(mock.select(null)).thenThrow(RepositoryException.class);
        Mockito.lenient().when(mock.selectLines(null)).thenThrow(RepositoryException.class);
        Mockito.lenient().when(mock.selectLines(existing_reference.getKey().getValue())).thenReturn(lines_reference_subjects);
        Mockito.lenient().when(mock.selectLines(missing_reference.getKey().getValue())).thenReturn(new ArrayList<>());
    }

    @Test
    public void testGetAll() throws Exception {
        //Arrange
        StopsRepository repository = new StopsRepository(mock);

        //Action
        List<StopsDto> result = repository.getAll();

        //Assert
        assertEquals(reference_subjects, result);
        Mockito.verify(mock, times(1)).selectAll();
    }

    @Test
    public void testGetExist() throws Exception {
        //Arrange
        Pair<Integer, Integer> key = existing_reference.getKey();
        StopsRepository repository = new StopsRepository(mock);

        //Action
        StopsDto result = repository.get(key);

        //Assert
        assertEquals(existing_reference, result);
        Mockito.verify(mock, times(1)).select(key);
    }

    @Test
    public void testGetNotExist() throws Exception {
        //Arrange
        Pair<Integer, Integer> key = missing_reference.getKey();
        StopsRepository repository = new StopsRepository(mock);

        //Action
        StopsDto result = repository.get(key);

        //Assert
        assertNull(result);
        Mockito.verify(mock, times(1)).select(key);
    }

    @Test
    public void testGetIncorrectParameter() throws Exception {
        //Arrange
        StopsRepository repository = new StopsRepository(mock);

        //Assert
        assertThrows(RepositoryException.class, () -> {

            //Action
            StopsDto result = repository.get(null);
        });

        Mockito.verify(mock, times(1)).select(null);
    }

    @Test
    public void testContainsExist() throws Exception {
        //Arrange
        Pair<Integer, Integer> key = existing_reference.getKey();
        StopsRepository repository = new StopsRepository(mock);

        //Action
        boolean result = repository.contains(key);

        //Assert
        assertTrue(result);
        Mockito.verify(mock, times(1)).select(key);
    }

    @Test
    public void testContainsNotExist() throws Exception {
        //Arrange
        Pair<Integer, Integer> key = missing_reference.getKey();
        StopsRepository repository = new StopsRepository(mock);

        //Action
        boolean result = repository.contains(key);

        //Assert
        assertFalse(result);
        Mockito.verify(mock, times(1)).select(key);
    }

    @Test
    public void testContainsIncorrectParameter() throws Exception {
        //Arrange
        StopsRepository repository = new StopsRepository(mock);

        //Assert
        assertThrows(RepositoryException.class, () -> {

            //Action
            boolean result = repository.contains(null);
        });

        Mockito.verify(mock, times(1)).select(null);
    }

    @Test
    public void testGetLinesExist() throws Exception {
        //Arrange
        int key = existing_reference.getKey().getValue();

        StopsRepository repository = new StopsRepository(mock);

        //Action
        List<Integer> result = repository.getLines(key);

        //Assert
        assertEquals(lines_reference_subjects, result);
        Mockito.verify(mock, times(1)).selectLines(key);
    }

    @Test
    public void testGetLinesNotExist() throws Exception {
        //Arrange
        int key = missing_reference.getKey().getValue();

        List<Integer> expected = new ArrayList<>();

        StopsRepository repository = new StopsRepository(mock);

        //Action
        List<Integer> result = repository.getLines(key);

        //Assert
        assertEquals(expected, result);
        Mockito.verify(mock, times(1)).selectLines(key);
    }

    @Test
    public void testGetLinesIncorrectParameter() throws Exception {
        //Arrange
        StopsRepository repository = new StopsRepository(mock);

        //Assert
        assertThrows(RepositoryException.class, () -> {

            //Action
            List<Integer> result = repository.getLines(null);
        });

        Mockito.verify(mock, times(1)).selectLines(null);
    }
}