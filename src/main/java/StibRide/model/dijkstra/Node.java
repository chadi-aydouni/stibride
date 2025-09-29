package StibRide.model.dijkstra;

import java.util.*;

/**
 * Représente un noeud du graphe.
 * Dans notre cas, il s'agit d'une station.
 */
public class Node {

    /**
     * L'identifiant du noeud (station).
     */
    private final int id;

    /**
     * Le nom du noeud (station).
     */
    private final String name;

    /**
     * Représente la liste de noeuds décrivant le chemin le plus court calculé à partir du noeud de départ.
     */
    private List<Node> shortestPath = new LinkedList<>();

    /**
     * Représente la distance du noeud par rapport à celui de départ. Par défaut, il vaut l'infini.
     */
    private Integer distance = Integer.MAX_VALUE;

    /**
     * Représente les noeuds adjacents et la longueur qui les sépare.
     */
    final Map<Node, Integer> adjacentNodes = new HashMap<>();

    /**
     * Constructeur avec ID.
     *
     * @param id   l'identifiant du noeud.
     * @param name le nom du noeud.
     */
    public Node(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Ajoute le noeud en paramètre à la liste des destinations.
     *
     * @param destination un noeud.
     */
    public void addDestination(Node destination) {

        /*
          Poids d'une connexion entre deux stations
         */
        int EDGE_WEIGHT = 1;

        adjacentNodes.put(destination, EDGE_WEIGHT);
    }

    /**
     * Getter de id.
     *
     * @return la valeur de id.
     */
    public int getId() {
        return id;
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
     * Getter de shortestPath.
     *
     * @return la valeur de shortestPath.
     */
    public List<Node> getShortestPath() {
        return shortestPath;
    }

    /**
     * Getter de distance.
     *
     * @return la valeur de distance.
     */
    public Integer getDistance() {
        return distance;
    }

    /**
     * Getter de adjacentNodes.
     *
     * @return la valeur de adjacentNodes.
     */
    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    /**
     * Setter de distance.
     *
     * @param distance la nouvelle valeur de distance.
     */
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    /**
     * Setter de shortestPath.
     *
     * @param shortestPath la nouvelle valeur de shortestPath.
     */
    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }
}
