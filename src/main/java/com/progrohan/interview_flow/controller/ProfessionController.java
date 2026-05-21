package com.progrohan.interview_flow.controller;

import com.progrohan.interview_flow.dto.ProfessionCreateRequestDto;
import com.progrohan.interview_flow.dto.ProfessionResponseDto;
import com.progrohan.interview_flow.service.ProfessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController()
@RequestMapping("/api/profession")
@RequiredArgsConstructor
public class ProfessionController {

    private final ProfessionService professionService;

    @GetMapping
    public ResponseEntity<List<ProfessionResponseDto>> getProfessions() {

        return ResponseEntity.ok(professionService.getAllProfessions());

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionResponseDto> getProfession(@PathVariable Long id) {

        return ResponseEntity.ok(professionService.getProfessionById(id));

    }

    @PostMapping
    public ResponseEntity<ProfessionResponseDto> createProfession(@RequestBody ProfessionCreateRequestDto professionDto) throws URISyntaxException {

        ProfessionResponseDto profession = professionService.createProfession(professionDto.name());

        return ResponseEntity
                .created(new URI("/api/profession/" + profession.id()))
                .body(profession);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfession(@PathVariable Long id){

        professionService.deleteProfession(id);

    }


}
