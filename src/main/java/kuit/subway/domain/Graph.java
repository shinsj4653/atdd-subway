package kuit.subway.domain;

import kuit.subway.dto.response.line.PathReadResponse;
import kuit.subway.dto.response.station.StationReadResponse;
import kuit.subway.exception.badrequest.line.InvalidPathNotConnectedException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
public class Graph {

    private final WeightedMultigraph<Station, DefaultWeightedEdge> graph = new WeightedMultigraph(DefaultWeightedEdge.class);

    @Builder.Default
    private List<Line> lines = new ArrayList<>();

    public static Graph of(List<Line> allLines) {
        return Graph.builder()
                .lines(allLines)
                .build();
    }

    // 경로 초기화
    public void initPath() {
        for (Line line : lines) {
            List<Section> sections = line.getSections();
            for (Section section : sections) {
                initEdge(section);
            }
        }
    }

    // 그래프 정점 및 간선, 그리고 각 간선에 거리 추가
    private void initEdge(Section section) {
        addVertex(section.getUpStation());
        addVertex(section.getDownStation());
        graph.setEdgeWeight(graph.addEdge(section.getUpStation(), section.getDownStation()), section.getDistance());
    }

    // 그래프 정점 추가함수
    private void addVertex(Station station) {
        if (!graph.containsVertex(station)) {
            graph.addVertex(station);
        }
    }
    
    // 그래프 경로 조회 응답 DTO 생성 함수
    public PathReadResponse getShortestPath(Station startStation, Station endStation) {
        DijkstraShortestPath<Station, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);

        // 경로 조회시, 출발역과 도착역이 연결되어 있지 않은 경우 판별
        validateStationExistInGraph(dijkstraShortestPath, startStation, endStation);

        List<StationReadResponse> path = dijkstraShortestPath.getPath(startStation, endStation)
                                                                .getVertexList().stream()
                                                                .map(StationReadResponse::of)
                                                                .collect(Collectors.toList());
        return PathReadResponse.of(path, dijkstraShortestPath.getPathWeight(startStation, endStation));
    }
    
    // 출발역과 도착역이 연결되어 있지 않은 경우, 예외 발생
    private void validateStationExistInGraph(DijkstraShortestPath<Station, DefaultWeightedEdge> dijkstraShortestPath,
                                             Station startStation, Station endStation) {
        try {
            // 노선끼리 서로 연결되어 있지 않은 경우
            if (dijkstraShortestPath.getPath(startStation, endStation) == null){
                throw new InvalidPathNotConnectedException();
            }
                
        } catch (IllegalArgumentException e) {
            // 역은 생성되어 있지만, 노선에는 포함되어 있지 않다.
            throw new InvalidPathNotConnectedException();
        }

    }

}
