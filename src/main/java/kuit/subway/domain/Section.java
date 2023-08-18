package kuit.subway.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "line")
    private List<Line> lines = new ArrayList<>();

    @Builder
    public Section(String name, List<Line> lines) {
        this.name = name;
        this.lines = lines;
    }
}
