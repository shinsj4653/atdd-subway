//package kuit.subway.controller;
//
//import kuit.subway.dto.request.line.LineCreateRequest;
//import kuit.subway.dto.request.section.SectionCreateRequest;
//import kuit.subway.dto.request.section.SectionDeleteRequest;
//import kuit.subway.dto.response.line.LineCreateResponse;
//import kuit.subway.dto.response.line.LineDto;
//import kuit.subway.service.SectionService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.URI;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/sections")
//public class SectionController {
//
//    private final SectionService sectionService;
//
//    @PostMapping("/{id}")
//    public ResponseEntity<LineDto> addSection(@PathVariable("id") Long lineId, @RequestBody SectionCreateRequest request) {
//
//        LineDto res = sectionService.addSection(lineId, request);
//        return ResponseEntity.created(URI.create("/sections/" + res.getId()))
//                .body(res);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<LineDto> deleteSection(@PathVariable("id") Long lineId, @RequestBody SectionDeleteRequest request) {
//
//        LineDto res = sectionService.deleteSection(lineId, request);
//        return ResponseEntity.ok().body(res);
//    }
//}
