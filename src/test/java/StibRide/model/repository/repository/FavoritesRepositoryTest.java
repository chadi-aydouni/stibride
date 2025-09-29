package StibRide.model.repository.repository;

import StibRide.model.dto.FavoritesDto;
import StibRide.model.exception.RepositoryException;
import StibRide.model.repository.dao.FavoritesDao;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;


/**
 * Classe de test de FavoritesRepository.
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class FavoritesRepositoryTest {

    @Mock
    private FavoritesDao mock;

    private final FavoritesDto existing_reference; // une ligne qui existe
    private final FavoritesDto missing_reference; // une ligne qui n'existe pas
    private final List<FavoritesDto> reference_subjects;

    public FavoritesRepositoryTest() {
        // Setup reference_subjects
        existing_reference = new FavoritesDto(1, 8012, 8142, "De brouckère vers Alma");
        missing_reference = new FavoritesDto(4, 8382, 8342, "Gare de l'ouest vers PDH");

        reference_subjects = new ArrayList<>();
        reference_subjects.add(existing_reference);
    }

    @BeforeEach
    void init() throws RepositoryException {
        Mockito.lenient().when(mock.select(existing_reference.getKey())).thenReturn(existing_reference);
        Mockito.lenient().when(mock.select(missing_reference.getKey())).thenReturn(null);
        Mockito.lenient().when(mock.selectAll()).thenReturn(reference_subjects);
        Mockito.lenient().when(mock.select(null)).thenThrow(RepositoryException.class);

        Mockito.lenient().doThrow(RepositoryException.class).when(mock).delete(missing_reference.getKey());
        Mockito.lenient().doThrow(RepositoryException.class).when(mock).delete(null);
    }

    @Test
    public void testAddWhenExisting() throws Exception {
        //Arrange
        FavoritesRepository repository = new FavoritesRepository(mock);

        //Action
        repository.add(existing_reference);

        //Assert
        Mockito.verify(mock, times(1)).select(existing_reference.getKey());
        Mockito.verify(mock, times(1)).update(existing_reference);
        Mockito.verify(mock, times(0)).insert(any(FavoritesDto.class));
    }

    @Test
    public void testAddWhenNotExisting() throws Exception {

        //Arrange
        FavoritesRepository repository = new FavoritesRepository(mock);

        //Action
        repository.add(missing_reference);

        //Assert
        Mockito.verify(mock, times(1)).select(missing_reference.getKey());
        Mockito.verify(mock, times(1)).insert(missing_reference);
        Mockito.verify(mock, times(0)).update(any(FavoritesDto.class));
    }

    @Test
    public void testAddIncorrectParameter() throws Exception {
        //Arrange
        FavoritesRepository repository = new FavoritesRepository(mock);

        //Assert
        assertThrows(RepositoryException.class, () -> {

            //Action
            repository.add(null);
        });

        // On est bloqué avant au niveau du repository
        Mockito.verify(mock, times(0)).insert(null);
        Mockito.verify(mock, times(0)).update(null);
    }

    @Test
    public void testRemoveExist() throws Exception {
        //Arrange
        FavoritesRepository repository = new FavoritesRepository(mock);

        //Action
        repository.remove(existing_reference.getKey());

        //Assert
        Mockito.verify(mock, times(1)).delete(existing_reference.getKey());
    }

    @Test
    public void testRemoveNotExist() throws Exception {
        //Arrange
        FavoritesRepository repository = new FavoritesRepository(mock);

        //Assert
        assertThrows(RepositoryException.class, () -> {

            //Action
            repository.remove(missing_reference.getKey());
        });

        Mockito.verify(mock, times(1)).delete(missing_reference.getKey());
    }

    @Test
    public void testRemoveIncorrectParameter() throws Exception {
        //Arrange
        FavoritesRepository repository = new FavoritesRepository(mock);

        //Assert
        assertThrows(RepositoryException.class, () -> {

            //Action
            repository.remove(null);
        });

        Mockito.verify(mock, times(1)).delete(null);
    }

    @Test
    public void testGetAll() throws Exception {
        //Arrange
        FavoritesRepository repository = new FavoritesRepository(mock);

        //Action
        List<FavoritesDto> result = repository.getAll();

        //Assert
        assertEquals(reference_subjects, result);
        Mockito.verify(mock, times(1)).selectAll();
    }

    @Test
    public void testGetExist() throws Exception {
        //Arrange
        int key = existing_reference.getKey();
        FavoritesRepository repository = new FavoritesRepository(mock);

        //Action
        FavoritesDto result = repository.get(key);

        //Assert
        assertEquals(existing_reference, result);
        Mockito.verify(mock, times(1)).select(key);
    }

    @Test
    public void testGetNotExist() throws Exception {
        //Arrange
        int key = missing_reference.getKey();
        FavoritesRepository repository = new FavoritesRepository(mock);

        //Action
        FavoritesDto result = repository.get(key);

        //Assert
        assertNull(result);
        Mockito.verify(mock, times(1)).select(key);
    }

    @Test
    public void testGetIncorrectParameter() throws Exception {
        //Arrange
        FavoritesRepository repository = new FavoritesRepository(mock);

        //Assert
        assertThrows(RepositoryException.class, () -> {

            //Action
            FavoritesDto result = repository.get(null);
        });

        Mockito.verify(mock, times(1)).select(null);
    }

    @Test
    public void testContainsExist() throws Exception {
        //Arrange
        int key = existing_reference.getKey();
        FavoritesRepository repository = new FavoritesRepository(mock);

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
        FavoritesRepository repository = new FavoritesRepository(mock);

        //Action
        boolean result = repository.contains(key);

        //Assert
        assertFalse(result);
        Mockito.verify(mock, times(1)).select(key);
    }

    @Test
    public void testContainsIncorrectParameter() throws Exception {
        //Arrange
        FavoritesRepository repository = new FavoritesRepository(mock);

        //Assert
        assertThrows(RepositoryException.class, () -> {

            //Action
            boolean result = repository.contains(null);
        });

        Mockito.verify(mock, times(1)).select(null);
    }
}