package StibRide.model.dijkstra;

import java.util.HashSet;
import java.util.Set;

/**
 * Représente le graphe étudié au sein de l'algorithme Dijkstra.
 * Dans notre cas, il s'agit du réseau de la STIB où les noeuds sont des stations.
 */
public class Graph {

    /**
     * Ensemble des noeuds du graphe.
     */
    private final Set<Node> nodes = new HashSet<>();

    public void addNode(Node node) {
        nodes.add(node);
    }

    /**
     * Getter de nodes.
     *
     * @return la liste de nodes du graphe.
     */
    public Set<Node> getNodes() {
        return nodes;
    }
}
