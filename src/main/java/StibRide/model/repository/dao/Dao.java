package StibRide.model.repository.dao;

import StibRide.model.dto.Dto;
import StibRide.model.exception.RepositoryException;

import java.util.List;

/**
 * L'accès à la ressource : faut-il se connecter à une BDD, comment ouvrir le fichier à consulter, etc.
 *
 * @param <T> le type de données, qui doit être un DTO de même type de clé que K.
 * @param <K> le type de la clé.
 */
public interface Dao<K, T extends Dto<K>> {

    /**
     * Insère un élément dans la ressource.
     *
     * @param item l'élément à insérer.
     * @return la clé de l'élément, utile quand la clé est générée automatiquement.
     * @throws RepositoryException si la ressource n'est pas accessible.
     */
    K insert(T item) throws RepositoryException;

    /**
     * Supprime l'élément avec la clé fournie de la ressource.
     *
     * @param key la clé de l'élément à supprimer.
     * @throws RepositoryException si la ressource n'est pas accessible.
     */
    void delete(K key) throws RepositoryException;

    /**
     * Mets à jour un élément de la ressource. La recherche de l'élément est basée sur la clé.
     *
     * @param item l'élément à mettre à jour.
     * @throws RepositoryException si la ressource n'est pas accessible.
     */
    void update(T item) throws RepositoryException;

    /**
     * Renvoie tous les éléments de la ressource. Cette méthode peut être longue.
     *
     * @return tous les éléments de la ressource.
     * @throws RepositoryException si la ressource n'est pas accessible.
     */
    List<T> selectAll() throws RepositoryException;

    /**
     * Renvoie un élément basé sur sa clé.
     *
     * @param key la clé de l'élément à sélectionner.
     * @return un élément sur base de sa clé.
     * @throws RepositoryException si la ressource n'est pas accessible.
     */
    T select(K key) throws RepositoryException;

}
