package StibRide.model.dto;

import java.util.Objects;

/**
 * Classe générique de DTO avec clé de type K.
 *
 * @param <K> le type de la clé.
 */
public class Dto<K> {

    protected final K key;

    /**
     * Constructeur à partir de clé
     *
     * @param key une clé.
     */
    public Dto(K key) {
        this.key = key;
    }

    /**
     * Accesseur de key.
     *
     * @return la valeur de key.
     */
    public K getKey() {
        return key;
    }

    /**
     * Vérifie l'égalité avec l'objet o. Pour que deux DTO soient égaux, ils doivent avoir la même clé et être
     * de même type.
     *
     * @param o un objet.
     * @return vrai si les deux objets sont égaux, faux sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dto<?> dto = (Dto<?>) o;
        return Objects.equals(key, dto.key);
    }

    /**
     * Retourne une valeur hashée correspondant à l'objet.
     *
     * @return une valeur hashée correspondant à l'objet.
     */
    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
