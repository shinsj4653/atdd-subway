package kuit.subway.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_id")
    private Long id;

    private String color;

    private int distance;


}
