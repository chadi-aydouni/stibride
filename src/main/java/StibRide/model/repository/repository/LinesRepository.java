package StibRide.model.repository.repository;

import StibRide.model.dto.LinesDto;
import StibRide.model.exception.RepositoryException;
import StibRide.model.repository.dao.LinesDao;

import java.util.List;

/**
 * Repository de la table LINES.
 */
public class LinesRepository implements Repository<Integer, LinesDto> {

    private final LinesDao dao;

    public LinesRepository() throws RepositoryException{
        dao = LinesDao.getInstance();
    }

    LinesRepository(LinesDao dao){
        this.dao = dao;
    }

    @Override
    public Integer add(LinesDto item) throws RepositoryException {
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
    public List<LinesDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public LinesDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        LinesDto refreshItem = dao.select(key);
        return refreshItem != null;
    }
}
