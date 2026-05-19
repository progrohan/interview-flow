package com.progrohan.interview_flow.controller;

import com.progrohan.interview_flow.dto.ProfessionResponseDto;
import com.progrohan.interview_flow.service.ProfessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
