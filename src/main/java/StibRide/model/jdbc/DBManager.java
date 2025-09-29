package StibRide.model.jdbc;

import StibRide.model.config.ConfigManager;
import StibRide.model.exception.RepositoryException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe s'occupant de la gestion de la connexion (permet de répartir les responsabilités entre classes).
 *
 * @author : jlc (merci à lui)
 */
public class DBManager {

    private Connection connection;

    private DBManager() {
    }

    /**
     * Crée et renvoie la connexion à la BDD.
     * @return la connexion à la BDD.
     * @throws RepositoryException si la connexon est impossible.
     */
    public Connection getConnection() throws RepositoryException {
        String jdbcUrl = "jdbc:sqlite:" + ConfigManager.getInstance().getProperties("db.url");
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(jdbcUrl);
            } catch (SQLException ex) {
                throw new RepositoryException("Connexion impossible: " + ex.getMessage());
            }
        }
        return connection;
    }

    /**
     * Démarre une transaction.
     * @throws RepositoryException s'il est impossible de démarrer une transaction.
     */
    void startTransaction() throws RepositoryException {
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException ex) {
            throw new RepositoryException("Impossible de démarrer une transaction: " + ex.getMessage());
        }
    }

    /**
     * Démarre une transaction selon un certain niveau d'isolation.
     * @param isolationLevel le niveau d'isolation.
     * @throws RepositoryException s'il est impossible de démarrer une transaction.
     */
    void startTransaction(int isolationLevel) throws RepositoryException {
        try {
            getConnection().setAutoCommit(false);

            int isol = 0;
            switch (isolationLevel) {
                case 0:
                    isol = java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;
                    break;
                case 1:
                    isol = java.sql.Connection.TRANSACTION_READ_COMMITTED;
                    break;
                case 2:
                    isol = java.sql.Connection.TRANSACTION_REPEATABLE_READ;
                    break;
                case 3:
                    isol = java.sql.Connection.TRANSACTION_SERIALIZABLE;
                    break;
                default:
                    throw new RepositoryException("Degré d'isolation inexistant!");
            }
            getConnection().setTransactionIsolation(isol);
        } catch (SQLException ex) {
            throw new RepositoryException("Impossible de démarrer une transaction: " + ex.getMessage());
        }
    }

    /**
     * Valide une transaction.
     * @throws RepositoryException s'il est impossible de valider la transaction.
     */
    void validateTransaction() throws RepositoryException {
        try {
            getConnection().commit();
            getConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            throw new RepositoryException("Impossible de valider la transaction: " + ex.getMessage());
        }
    }

    /**
     * Annule une transaction.
     * @throws RepositoryException s'il est impossible d'annuler la transaction.
     */
    void cancelTransaction() throws RepositoryException {
        try {
            getConnection().rollback();
            getConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            throw new RepositoryException("Impossible d'annuler la transaction: " + ex.getMessage());
        }
    }

    /**
     * Renvoie une instance de DBManager.
     * @return une instance de DBManager.
     */
    public static DBManager getInstance() {
        return DBManagerHolder.INSTANCE;
    }

    private static class DBManagerHolder {

        private static final DBManager INSTANCE = new DBManager();
    }
}
