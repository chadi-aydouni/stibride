package StibRide.model.repository.repository;

import StibRide.model.dto.FavoritesDto;
import StibRide.model.exception.RepositoryException;
import StibRide.model.repository.dao.FavoritesDao;

import java.util.List;

/**
 * Repository de la table FAVORITES.
 */
public class FavoritesRepository implements Repository<Integer, FavoritesDto> {

    private final FavoritesDao dao;

    public FavoritesRepository() throws RepositoryException{
        dao = FavoritesDao.getInstance();
    }

    FavoritesRepository(FavoritesDao dao){this.dao = dao; }

    @Override
    public Integer add(FavoritesDto item) throws RepositoryException {

        if(item == null){
           throw new RepositoryException("L'item donné est null.");
        }

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
    public List<FavoritesDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public FavoritesDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        FavoritesDto refreshItem = dao.select(key);
        return refreshItem != null;
    }
}
