package StibRide.model.dto;

/**
 * DTO contenant l'information d'une ligne : son identifiant.
 */
public class LinesDto extends Dto<Integer> {

    // La clé et l'identifiant sont les mêmes.

    /**
     * Constructeur d'un DTO de ligne.
     *
     * @param key l'identifiant du DTO.
     */
    public LinesDto(Integer key) {
        super(key);
    }

    /**
     * Renvoie une chaîne de caractères représentant le DTO.
     *
     * @return une chaîne de caractères représentant le DTO
     */
    @Override
    public String toString() {
        return "LineDto{" + "key=" + key + '}';
    }
}
