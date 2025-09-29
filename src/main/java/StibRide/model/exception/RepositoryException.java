package StibRide.model.exception;

/**
 * En cas d'erreur au sein du Repository ou du DAO, cette exception est lancée.
 */
public class RepositoryException extends Exception {

    /**
     * Constructeur prenant en paramètre l'exception originale.
     *
     * @param exception l'exception originale.
     */
    public RepositoryException(Exception exception) {
        super(exception);
    }

    public RepositoryException(String error_msg) {
        super(error_msg);
    }

}
