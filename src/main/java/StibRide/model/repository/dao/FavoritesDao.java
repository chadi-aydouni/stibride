package StibRide.model.repository.dao;

import StibRide.model.dto.FavoritesDto;
import StibRide.model.exception.RepositoryException;
import StibRide.model.jdbc.DBManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao de la table FAVORITES.
 */
public class FavoritesDao implements Dao<Integer, FavoritesDto> {

    private final Connection connexion;

    public FavoritesDao() throws RepositoryException {
        connexion = DBManager.getInstance().getConnection();
    }

    public static FavoritesDao getInstance() throws RepositoryException {
        return FavoritesDaoHolder.getInstance();
    }

    @Override
    public Integer insert(FavoritesDto item) throws RepositoryException {
        if (item == null) {
            throw new RepositoryException("Aucune élément donné en paramètre");
        }
        int id = 0;
        String sql = "INSERT INTO FAVORITES(id_start_station,id_end_station, name) values(?, ?, ?)";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, item.getId_start_station());
            pstmt.setInt(2, item.getId_end_station());
            pstmt.setString(3, item.getName());
            pstmt.executeUpdate();

            ResultSet result = pstmt.getGeneratedKeys();
            while (result.next()) {
                id = result.getInt(1);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return id;
    }

    @Override
    public void delete(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "DELETE FROM FAVORITES WHERE id = ?";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(FavoritesDto item) throws RepositoryException {
        if (item == null) {
            throw new RepositoryException("Aucune élément donné en paramètre");
        }
        String sql = "UPDATE FAVORITES SET id_start_station=?,id_end_station=?, name=? where id=? ";
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {

            pstmt.setInt(1, item.getId_start_station());
            pstmt.setInt(2, item.getId_end_station());
            pstmt.setString(3, item.getName());
            pstmt.setInt(4, item.getKey());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public List<FavoritesDto> selectAll() throws RepositoryException {
        String sql = "SELECT id, id_start_station,id_end_station, name FROM FAVORITES";
        List<FavoritesDto> dtos = new ArrayList<>();
        try (Statement stmt = connexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                FavoritesDto dto = new FavoritesDto(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4));
                dtos.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dtos;
    }

    @Override
    public FavoritesDto select(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Aucune clé donnée en paramètre");
        }
        String sql = "SELECT id, id_start_station,id_end_station, name FROM FAVORITES WHERE id = ?";
        FavoritesDto dto = null;
        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                dto = new FavoritesDto(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4));
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
    private static class FavoritesDaoHolder {

        private static FavoritesDao getInstance() throws RepositoryException {
            return new FavoritesDao();
        }
    }
}
