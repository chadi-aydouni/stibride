package StibRide.observer;

import StibRide.model.exception.RepositoryException;

/**
 * Interface de l'observateur dans le patron Observateur - Observé.
 */
public interface Observer {

    /**
     * Mets à jour les observateurs du sujet. Peut également transmettre un paramètre avec.
     *
     * @param observable l'objet observé.
     * @param arg        des arguments passés à la méthode.
     */
    void update(Observable observable, Object arg) throws RepositoryException;
}
