package kuit.subway.service;

import jakarta.persistence.EntityNotFoundException;
import kuit.subway.domain.Section;
import kuit.subway.dto.request.section.SectionCreateRequest;
import kuit.subway.dto.response.line.LineDto;
import kuit.subway.dto.response.section.SectionCreateResponse;
import kuit.subway.dto.response.section.SectionDto;
import kuit.subway.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;

//    @Transactional
//    public SectionCreateResponse addSection(SectionCreateRequest req) {
//
//    }

    @Transactional
    public SectionDto findSectionById(Long id) {
        Section section = sectionRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        // LineDto 리스트 생성 필요
        List<LineDto> = section.getLines().stream()
                .map(m -> new LineDto(m.getId(), ))

        SectionDto dto = SectionDto.builder()
                .id(section.getId())
                .name(section.getName())
                .lines(section.getLines()).build();
    }



}
