package StibRide.model.repository.repository;

import StibRide.model.dto.StationsDto;
import StibRide.model.exception.RepositoryException;
import StibRide.model.repository.dao.StationsDao;

import java.util.List;

/**
 * Repository de la table STATIONS.
 */
public class StationsRepository implements Repository<Integer, StationsDto> {

    private final StationsDao dao;

    public StationsRepository() throws RepositoryException{
        dao = StationsDao.getInstance();
    }

    StationsRepository(StationsDao dao){
        this.dao = dao;
    }

    @Override
    public Integer add(StationsDto item) throws RepositoryException {
        Integer key = item.getKey();
        if (contains(item.getKey())) {
            dao.update(item);
        } else {
            key = dao.insert(item);
        }
        return key;
    }

    @Override
    public void remove(Integer key) throws RepositoryException {
        dao.delete(key);
    }

    @Override
    public List<StationsDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StationsDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        StationsDto refreshItem = dao.select(key);
        return refreshItem != null;
    }
}
