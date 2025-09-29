package StibRide.model.dto;

/**
 * DTO contenant les informations d'une station : son identifiant et son nom.
 */
public class StationsDto extends Dto<Integer> {

    // La clé et l'identifiant sont les mêmes.

    private final String name;

    /**
     * Constructeur d'un DTO de station.
     *
     * @param key  l'identifiant du DTO.
     * @param name le nom de la station.
     */
    public StationsDto(Integer key, String name) {
        super(key);
        this.name = name;
    }

    /**
     * Getter de name.
     *
     * @return la valeur de name.
     */
    public String getName() {
        return name;
    }

    /**
     * Renvoie une chaîne de caractères représentant le DTO.
     *
     * @return une chaîne de caractères représentant le DTO
     */
    @Override
    public String toString() {
        return name;
    }
}
