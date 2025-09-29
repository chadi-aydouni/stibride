package StibRide.model.repository.repository;

import StibRide.model.dto.Dto;
import StibRide.model.exception.RepositoryException;

import java.util.List;

/**
 * La logique des accès aux données : avant d'ajouter une donnée, il faut vérifier si elle n'existe pas déjà, avant de lire une donnée, il faut vérifier qu'on ne l'ai pas déjà en mémoire, etc.
 * @param <T> le type de données, qui doit être un DTO de même type de clé que K.
 * @param <K> le type de la clé.
 *
 */
public interface Repository<K, T extends Dto<K>> {

    /**
     * Ajoute un élément au repository. Si l'élément existe déjà, le repository le met à jour.
     *
     * @param item l'élément à ajouter.
     * @return la clé de l'élément, utile quand la clé est générée automatiquement.
     * @throws RepositoryException si le repository ne peut accéder à l'élément.
     */
    K add(T item) throws RepositoryException;

    /**
     * Supprime l'élément avec la clé spécifiée.
     *
     * @param key la clé de l'élément à supprimer.
     * @throws RepositoryException si le repository ne peut accéder à l'élément.
     */
    void remove(K key) throws RepositoryException;

    /**
     * Renvoie tous les éléments du repository.
     *
     * @return tous les éléments du repository.
     *
     * @throws RepositoryException si le repository ne peut accéder aux éléments.
     */
    List<T> getAll() throws RepositoryException;

    /**
     * Renvoie l'élément du repository avec la clé spécifiée.
     *
     * @param key la clé de l'élément.
     *
     * @return l'élément du repository avec la clé spécifiée.
     * @throws RepositoryException si le repository ne peut accéder à l'élément.
     */
    T get(K key) throws RepositoryException;

    /**
     * Renvoie vrai si l'élément existe dans le repository, sinon faux.
     * Un élément est recherché via sa clé.
     *
     * @param key la clé de l'élément.
     * @return vrai si l'élément existe dans le repository, sinon faux.
     * @throws RepositoryException si le repository ne peut accéder à l'élément.
     */
    boolean contains(K key) throws RepositoryException;

}