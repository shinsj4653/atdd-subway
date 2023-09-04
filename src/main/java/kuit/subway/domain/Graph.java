package kuit.subway.domain;

import kuit.subway.dto.response.line.PathReadResponse;
import kuit.subway.dto.response.station.StationReadResponse;
import kuit.subway.exception.badrequest.line.InvalidPathNotConnectedException;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.List;
import java.util.stream.Collectors;


public class Graph {
    private final WeightedMultigraph<Station, DefaultWeightedEdge> graph = new WeightedMultigraph(DefaultWeightedEdge.class);

    public Graph(List<Line> lines) {
        for (Line line : lines) {
            List<Section> sections = line.getSections();
            initPath(sections);
        }
    }

    private void initPath(List<Section> sections) {
        for (Section section : sections) {
            initEdge(section);
        }
    }

    private void initEdge(Section section) {
        addVertex(section.getUpStation());
        addVertex(section.getDownStation());
        graph.setEdgeWeight(graph.addEdge(section.getUpStation(), section.getDownStation()), section.getDistance());
    }

    private void addVertex(Station station) {
        if (!graph.containsVertex(station)) {
            graph.addVertex(station);
        }
    }

    public PathReadResponse shortestPath(Station startStation, Station endStation) {
        DijkstraShortestPath<Station, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);

        // 경로 조회시, 출발역과 도착역이 연결되어 있지 않은 경우 판별
        validateStationExistInGraph(dijkstraShortestPath, startStation, endStation);

        List<StationReadResponse> path = dijkstraShortestPath.getPath(startStation, endStation)
                                                                .getVertexList().stream()
                                                                .map(StationReadResponse::of)
                                                                .collect(Collectors.toList());
        return PathReadResponse.of(path, dijkstraShortestPath.getPathWeight(startStation, endStation));
    }

    private void validateStationExistInGraph(DijkstraShortestPath<Station, DefaultWeightedEdge> dijkstraShortestPath,
                                             Station startStation, Station endStation) {
        try {
            dijkstraShortestPath.getPath(startStation, endStation);
        } catch (IllegalArgumentException e) {
            throw new InvalidPathNotConnectedException();
        }
    }

}
