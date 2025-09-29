package StibRide.model;

import StibRide.model.dijkstra.Dijkstra;
import StibRide.model.dijkstra.Graph;
import StibRide.model.dijkstra.Node;
import StibRide.model.dto.FavoritesDto;
import StibRide.model.dto.StationsDto;
import StibRide.model.dto.StopsDto;
import StibRide.model.exception.RepositoryException;
import StibRide.model.repository.repository.FavoritesRepository;
import StibRide.model.repository.repository.StationsRepository;
import StibRide.model.repository.repository.StopsRepository;
import StibRide.observer.Observable;
import StibRide.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Modèle du programme dans le pattern MVP.
 */
public class Model implements Observable {

    private final List<Observer> observers;
    private final FavoritesRepository favorite_repo;

    // Information de stockage
    private List<StationsDto> shortestPath;
    private List<Node> node_list;
    private FavoritesDto lastFavorite;
    private FavoritesDto deleteFavorite;

    /**
     * Constructeur du modèle
     * @param favorite_repo le repository de FAVORITES.
     */
    public Model(FavoritesRepository favorite_repo) {
        observers = new ArrayList<>();
        shortestPath = new ArrayList<>();
        this.favorite_repo = favorite_repo;
    }

    /**
     * Getter de shortestPath.
     *
     * @return la valeur de shortestPath.
     */
    public List<StationsDto> getShortestPath() {
        return shortestPath;
    }

    /**
     * Getter de lastFavorite.
     *
     * @return la valeur de lastFavorite.
     */
    public FavoritesDto getLastFavorite() {
        return lastFavorite;
    }

    /**
     * Getter de deleteFavorite.
     *
     * @return la valeur de deleteFavorite.
     */
    public FavoritesDto getDeleteFavorite() {
        return deleteFavorite;
    }

    /**
     * Génère le trajet entre les deux stations et notifie la vue.
     *
     * @param start_station la station de départ.
     * @param end_station   la station de fin.
     */
    public void generateRide(StationsDto start_station, StationsDto end_station) throws RepositoryException {
        // Nécessité de recréer la liste car pas trouvé comment faire la deep copie de la liste dans un graph.
        node_list = generateNodes();
        shortestPath = generateGraph(start_station, end_station);
        notifyObservers(1);
    }

    /**
     * Génère le graphe à partir de la station de départ et celle d'arrivée et renvoie le chemin le plus court.
     *
     * @param start_station la station de départ.
     * @param end_station   la station de fin.
     * @return la liste des stations entre les deux points, en prenant le chemin le plus court.
     */
    private List<StationsDto> generateGraph(StationsDto start_station, StationsDto end_station) {
        List<StationsDto> shortest_path = new ArrayList<>();

        // 1ère étape : générer le graphe des chemins les plus courts à partir de la station de départ

        Graph graph = new Graph();
        graph.getNodes().addAll(node_list);
        Node start_node = getNodeByDtoKey(node_list, start_station.getKey());
        graph = Dijkstra.calculateShortestPathFromSource(graph, Objects.requireNonNull(start_node));

        // 2ème étape : récupérer le chemin le plus court et exprimer ses items sous forme de DTO

        for (Node node : node_list) {
            if (node.getId() == end_station.getKey()) {
                for (Node inter_node : node.getShortestPath()) {

                    shortest_path.add(new StationsDto(inter_node.getId(), inter_node.getName()));
                }

                shortest_path.add(end_station); // Ne pas oublier d'ajouter la station de fin
                return shortest_path;
            }
        }

        return shortest_path;
    }

    /**
     * Génère l'ensemble des noeuds du graphe à partir de la base de données.
     * Chaque station est convertie en noeud.
     *
     * @return une liste de l'ensemble des noeuds du graphe.
     */
    private List<Node> generateNodes() throws RepositoryException {
        List<Node> node_list = new ArrayList<>();

        // 1) Récupérer l'ensemble des stations

        StationsRepository stations_repo = new StationsRepository();
        List<StationsDto> stations_dtos = stations_repo.getAll();

        for (StationsDto dto : stations_dtos) {
            node_list.add(new Node(dto.getKey(), dto.getName()));
        }

        // 2) Etablir l'ensemble des relations entre stations

        StopsRepository stops_repo = new StopsRepository();
        List<StopsDto> stops_dtos = stops_repo.getAll();

        //  ATTENTION ! On part du principe que stops_dto est trié selon la colonne id_line et id_order.

        StopsDto current_dto;
        StopsDto previous_dto = null;
        Node current_node;
        Node previous_node;

        for (StopsDto stops_dto : stops_dtos) {

            current_dto = stops_dto;

            if (previous_dto != null && previous_dto.getKey().getKey() == current_dto.getKey().getKey()) {

                current_node = getNodeByDtoKey(node_list, current_dto.getKey().getValue());
                previous_node = getNodeByDtoKey(node_list, previous_dto.getKey().getValue());

                if (current_node == null || previous_node == null) {
                    throw new RepositoryException("La station associée au stop n'existe pas dans la liste des noeuds");
                }

                // Ajout dans les deux sens de circulation. Pas de problème de duplicate : avec Map, on remplace.
                previous_node.addDestination(current_node);
                current_node.addDestination(previous_node);
            }
            previous_dto = current_dto;
        }

        // 3) Renvoyer la liste de noeuds qui a été mise à jour

        return node_list;
    }

    /**
     * Renvoie le noeud dans la liste dont l'identifiant est celui donné.
     *
     * @param node_list une liste de noeuds.
     * @param key       la clé du noeud recherché.
     * @return le noeud dans la liste dont l'identifiant est celui donné, sinon null si non trouvé.
     */
    private Node getNodeByDtoKey(List<Node> node_list, int key) {
        for (Node node : node_list) {
            if (node.getId() == key) {
                return node;
            }
        }

        return null;
    }

    /**
     * Ajoute le favori avec les paramètres fournis.
     *
     * @param start_key l'identifiant de la station de départ.
     * @param end_key   l'identifiant de la station de fin.
     * @param name      le nom du favori.
     */
    public void addFavorite(int start_key, int end_key, String name) throws RepositoryException {
        lastFavorite = favorite_repo.get(favorite_repo.add(new FavoritesDto(0, start_key, end_key, name)));
        notifyObservers(2);
    }

    /**
     * Supprime le favori donné en paramètre.
     *
     * @param dto le favori à supprimer.
     */
    public void deleteFavorite(FavoritesDto dto) throws RepositoryException {
        deleteFavorite = dto;
        favorite_repo.remove(dto.getKey());
        notifyObservers(3);
    }

    /**
     * Mets à jour le DTO donné en paramètre.
     *
     * @param dto le dto à mettre à jour.
     */
    public void updateFavorite(FavoritesDto dto) throws RepositoryException {
        favorite_repo.add(dto);
        lastFavorite = dto;
        notifyObservers(4);
    }

    /**
     * Permet d'enregistrer un observateur.
     *
     * @param obs un observateur.
     */
    @Override
    public void registerObserver(Observer obs) {
        if (!observers.contains(obs)) {
            observers.add(obs);
        }
    }

    /**
     * Permet de retirer un observateur.
     *
     * @param obs un observateur.
     */
    @Override
    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    /**
     * Permet de notifier les observateurs avec un numéro correspondant à l'action effectuée.
     */
    @Override
    public void notifyObservers(int actionNb) throws RepositoryException {
        for (Observer obs : observers) {
            obs.update(this, actionNb);
        }
    }
}
