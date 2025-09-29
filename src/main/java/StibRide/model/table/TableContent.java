package StibRide.model.table;

import java.util.List;

/**
 * Contient les informations nécessaires pour une insertion dans le tableau de la vue : un nom et l'ensemble
 * des lignes par laquelle passe la station.
 */
public class TableContent {

    private final String name;
    private final List<Integer> lines;

    /**
     * Constructeur de TableContent.
     *
     * @param name  le nom de la station.
     * @param lines les lignes qu'elle parcourt.
     */
    public TableContent(String name, List<Integer> lines) {
        this.name = name;
        this.lines = lines;
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
     * Getter de lines.
     *
     * @return la valeur de lines.
     */
    public List<Integer> getLines() {
        return lines;
    }
}
