package StibRide.model.dto;

/**
 * DTO contenant les informations d'un favori : l'identifiant du favori,  l'identifiant de la station de départ,
 * l'identifiant de la station d'arrivée et le nom du favori.
 */
public class FavoritesDto extends Dto<Integer> {

    private final int id_start_station;
    private final int id_end_station;
    private final String name;

    /**
     * Constructeur de FavoritesDto.
     *
     * @param key              la clé du Dto (générée automatiquement)
     * @param id_start_station l'identifiant de la station de départ.
     * @param id_end_station   l'identifiant de la station de fin.
     * @param name             le nom du favori.
     */
    public FavoritesDto(Integer key, int id_start_station, int id_end_station, String name) {
        super(key);
        this.id_start_station = id_start_station;
        this.id_end_station = id_end_station;
        this.name = name;
    }

    /**
     * Getter de id_start_station.
     *
     * @return la valeur de id_start_station.
     */
    public int getId_start_station() {
        return id_start_station;
    }

    /**
     * Getter de id_end_station.
     *
     * @return la valeur de id_end_station.
     */
    public int getId_end_station() {
        return id_end_station;
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
