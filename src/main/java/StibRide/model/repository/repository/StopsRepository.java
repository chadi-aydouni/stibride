package StibRide.model.repository.repository;

import StibRide.model.dto.StopsDto;
import StibRide.model.exception.RepositoryException;
import StibRide.model.repository.dao.StopsDao;
import javafx.util.Pair;

import java.util.List;

/**
 * Repository de la table STOPS.
 */
public class StopsRepository implements Repository<Pair<Integer, Integer>, StopsDto>{

    private final StopsDao dao;

    public StopsRepository() throws RepositoryException{
        dao = StopsDao.getInstance();
    }

    StopsRepository(StopsDao dao){
        this.dao = dao;
    }

    @Override
    public Pair<Integer, Integer> add(StopsDto item) throws RepositoryException {
        Pair<Integer, Integer> key = item.getKey();
        if (contains(item.getKey())) {
            dao.update(item);
        } else {
            key = dao.insert(item);
        }
        return key;
    }

    @Override
    public void remove(Pair<Integer, Integer> key) throws RepositoryException {
        dao.delete(key);
    }

    @Override
    public List<StopsDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StopsDto get(Pair<Integer, Integer> key) throws RepositoryException {
        return dao.select(key);
    }

    public List<Integer> getLines(Integer key) throws RepositoryException{
        return dao.selectLines(key);
    }

    @Override
    public boolean contains(Pair<Integer, Integer> key) throws RepositoryException {
        StopsDto refreshItem = dao.select(key);
        return refreshItem != null;
    }
}
