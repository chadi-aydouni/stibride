package StibRide.model.dto;

import javafx.util.Pair;

/**
 * DTO contenant les informations d'un arrêt : les identifiants de la ligne et station, ainsi que l'ordre de la station
 * au sein de la ligne.
 */
public class StopsDto extends Dto<Pair<Integer, Integer>> {

    private final int id_order;

    /**
     * Constructeur d'un DTO d'arrêt.
     *
     * @param key      l'identifiant du DTO.
     * @param id_order la position de l'arrêt dans la correspondance.
     */
    public StopsDto(Pair<Integer, Integer> key, int id_order) {
        super(key);
        this.id_order = id_order;
    }

    /**
     * Getter de id_order.
     *
     * @return la valeur de id_order.
     */
    public int getId_order() {
        return id_order;
    }

    /**
     * Renvoie une chaîne de caractères représentant le DTO.
     *
     * @return une chaîne de caractères représentant le DTO
     */
    @Override
    public String toString() {
        return "StopDto{" +
                "key=" + key +
                ", id_order=" + id_order +
                '}';
    }
}
