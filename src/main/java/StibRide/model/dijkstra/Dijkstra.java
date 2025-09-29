package StibRide.model.dijkstra;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Le but de l'algorithme Dijkstra est de trouver le chemin le plus court entre noeuds d'un graphe avec poids positifs.
 * Dans notre cas, on souhaite trouver le chemin le plus court entre deux stations.
 * Il commence au noeud choisi et tente de le chemin le plus court entre le dit noeud et tous les autres
 * noeuds du graphe.
 * <p>
 * Est fourni dans cette classe l'ensemble des méthodes permettant le calcul de chemin le plus court.
 */
public class Dijkstra {

    /**
     * Algorithme principal calculant la distance la plus courte pour atteindre chaque noeud du graphe à partir
     * de celui donné en paramètre.
     *
     * @param graph  le graphe étudié.
     * @param source le noeud de départ.
     * @return un graphe mis à jour avec des noeuds où figurent l'information de chemin le plus court.
     */
    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair :
                    currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    /**
     * Renvoie le noeud avec la distance la moins élevée à partir de la liste de noeuds non visités.
     *
     * @param unsettledNodes la liste de noeuds non visités.
     * @return le noeud avec la distance la moins élevée à partir de la liste de noeuds non visités.
     */
    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    /**
     * Compare la distance courante avec celle nouvellement calculée en explorant le nouveau chemin.
     *
     * @param evaluationNode le noeud évalué pour le nouveau chemin.
     * @param edgeWeigh      la distance à parcourir pour atteindre le dit noeud avec le chemin actuel.
     * @param sourceNode     le noeud de départ.
     */
    private static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

}
