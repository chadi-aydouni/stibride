package StibRide.model.repository.repository;

import StibRide.model.dto.LinesDto;
import StibRide.model.exception.RepositoryException;
import StibRide.model.repository.dao.LinesDao;
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
 * Classe de test de LinesRepository.
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class LinesRepositoryTest {

    @Mock
    private LinesDao mock;

    private final LinesDto existing_reference; // une ligne qui existe
    private final LinesDto missing_reference; // une ligne qui n'existe pas
    private final List<LinesDto> reference_subjects;

    public LinesRepositoryTest() {
        // Setup reference_subjects
        existing_reference = new LinesDto(2);
        missing_reference = new LinesDto(13);

        reference_subjects = new ArrayList<>();
        reference_subjects.add(existing_reference);
    }

    @BeforeEach
    void init() throws RepositoryException {
        // Préparation du comportement du mock
        Mockito.lenient().when(mock.select(existing_reference.getKey())).thenReturn(existing_reference);
        Mockito.lenient().when(mock.select(missing_reference.getKey())).thenReturn(null);
        Mockito.lenient().when(mock.selectAll()).thenReturn(reference_subjects);
        Mockito.lenient().when(mock.select(null)).thenThrow(RepositoryException.class);
    }

    @Test
    public void testGetAll() throws Exception {
        //Arrange
        LinesRepository repository = new LinesRepository(mock);

        //Action
        List<LinesDto> result = repository.getAll();

        //Assert
        assertEquals(reference_subjects, result);
        Mockito.verify(mock, times(1)).selectAll();
    }

    @Test
    public void testGetExist() throws Exception {
        //Arrange
        int key = existing_reference.getKey();
        LinesRepository repository = new LinesRepository(mock);

        //Action
        LinesDto result = repository.get(key);

        //Assert
        assertEquals(existing_reference, result);
        Mockito.verify(mock, times(1)).select(key);
    }

    @Test
    public void testGetNotExist() throws Exception {
        //Arrange
        int key = missing_reference.getKey();
        LinesRepository repository = new LinesRepository(mock);

        //Action
        LinesDto result = repository.get(key);

        //Assert
        assertNull(result);
        Mockito.verify(mock, times(1)).select(key);
    }

    @Test
    public void testGetIncorrectParameter() throws Exception {
        //Arrange
        LinesRepository repository = new LinesRepository(mock);

        //Assert
        assertThrows(RepositoryException.class, () -> {

            //Action
            LinesDto result = repository.get(null);
        });

        Mockito.verify(mock, times(1)).select(null);
    }

    @Test
    public void testContainsExist() throws Exception {
        //Arrange
        int key = existing_reference.getKey();
        LinesRepository repository = new LinesRepository(mock);

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
        LinesRepository repository = new LinesRepository(mock);

        //Action
        boolean result = repository.contains(key);

        //Assert
        assertFalse(result);
        Mockito.verify(mock, times(1)).select(key);
    }

    @Test
    public void testContainsIncorrectParameter() throws Exception {
        //Arrange
        LinesRepository repository = new LinesRepository(mock);

        //Assert
        assertThrows(RepositoryException.class, () -> {

            //Action
            boolean result = repository.contains(null);
        });

        Mockito.verify(mock, times(1)).select(null);
    }
}