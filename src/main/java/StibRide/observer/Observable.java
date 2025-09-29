package StibRide.observer;

import StibRide.model.exception.RepositoryException;

/**
 * Interface du sujet dans le patron "Observateur - Observé".
 */
public interface Observable {

    /**
     * Ajoute un observateur qui observe le sujet.
     *
     * @param obs un observateur.
     */
    void registerObserver(Observer obs);

    /**
     * Retire un observateur qui observe le sujet.
     *
     * @param obs un observateur.
     */
    void removeObserver(Observer obs);

    /**
     * Notifie tous les observateurs d'un changement.
     *
     * @param actionNb le numéro associé à l'action du modèle.
     */
    void notifyObservers(int actionNb) throws RepositoryException;
}