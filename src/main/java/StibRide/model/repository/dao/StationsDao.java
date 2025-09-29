package StibRide.model.repository.dao;

import StibRide.model.dto.StationsDto;
import StibRide.model.exception.RepositoryException;
import StibRide.model.jdbc.DBManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao de la table STATIONS.
 */
public class StationsDao implements Dao<Integer, StationsDto> {

    private final Connection connexion;

    public StationsDao() throws RepositoryException {
        connexion = DBManager.getInstance().getConnection();
    }

    public static StationsDao getInstance() throws RepositoryException {
        return StationsDaoHolder.getInstance();
    }

    @Override
    public Integer insert(StationsDto item) {
        return null;
    }

    @Override
    public void delete(Integer key) {

    }

    @Override
    public void update(StationsDto item) {

    }

    @Override
    public List<StationsDto> selectAll() throws RepositoryException {
        String sql = "SELECT id, name  FROM STATIONS";
        List<StationsDto> dtos = new ArrayList<>();
        try (Statement stmt = connexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                StationsDto dto = new StationsDto(rs.getInt(1), rs.getString(2));
                dtos.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dtos;
    }

    @Override
    public StationsDto select(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "SELECT id, name FROM STATIONS WHERE id = ?";
        StationsDto dto = null;
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                dto = new StationsDto(rs.getInt(1), rs.getString(2));
                count++;
            }
            if (count > 1) {
                throw new RepositoryException("Enregistrement non unique " + key);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dto;
    }

    /**
     * Propose une méthode permettant un retour d'une instance de la classe.
     */
    private static class StationsDaoHolder {
        private static StationsDao getInstance() throws RepositoryException {
            return new StationsDao();
        }
    }
}
