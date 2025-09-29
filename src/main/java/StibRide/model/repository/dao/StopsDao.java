package StibRide.model.repository.dao;

import StibRide.model.dto.StopsDto;
import StibRide.model.exception.RepositoryException;
import StibRide.model.jdbc.DBManager;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao de la table STOPS.
 */
public class StopsDao implements Dao<Pair<Integer, Integer>, StopsDto> {

    private final Connection connexion;

    public StopsDao() throws RepositoryException {
        connexion = DBManager.getInstance().getConnection();
    }

    public static StopsDao getInstance() throws RepositoryException {
        return StopsDaoHolder.getInstance();
    }

    @Override
    public Pair<Integer, Integer> insert(StopsDto item) {
        return null;
    }

    @Override
    public void delete(Pair<Integer, Integer> key) {

    }

    @Override
    public void update(StopsDto item) {

    }

    @Override
    public List<StopsDto> selectAll() throws RepositoryException {
        String sql = "SELECT id_line, id_station, id_order  FROM STOPS";
        List<StopsDto> dtos = new ArrayList<>();
        try (Statement stmt = connexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                StopsDto dto = new StopsDto(new Pair<>(rs.getInt(1), rs.getInt(2)), rs.getInt(3));
                dtos.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dtos;
    }

    @Override
    public StopsDto select(Pair<Integer, Integer> key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "SELECT id_line, id_station, id_order FROM STOPS WHERE id_line = ? AND id_station= ?";
        StopsDto dto = null;
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, key.getKey());
            pstmt.setInt(2, key.getValue());
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                dto = new StopsDto(new Pair<>(rs.getInt(1), rs.getInt(2)), rs.getInt(3));
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

    public List<Integer> selectLines(Integer key) throws RepositoryException {

        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }

        String sql = "SELECT id_line, id_station FROM STOPS WHERE id_station = ?";
        List<Integer> list = new ArrayList<>();

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return list;
    }

    /**
     * Propose une méthode permettant un retour d'une instance de la classe.
     */
    private static class StopsDaoHolder {
        private static StopsDao getInstance() throws RepositoryException {
            return new StopsDao();
        }
    }
}
