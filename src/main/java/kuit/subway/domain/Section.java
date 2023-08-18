package kuit.subway.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Long id;
    private String name;
    private Long downLineId;
    private Long upLineId;
}
