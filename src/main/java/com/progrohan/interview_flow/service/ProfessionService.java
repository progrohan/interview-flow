package com.progrohan.interview_flow.service;

import com.progrohan.interview_flow.dto.ProfessionResponseDto;
import com.progrohan.interview_flow.entity.Profession;
import com.progrohan.interview_flow.mapper.ProfessionMapper;
import com.progrohan.interview_flow.repository.ProfessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessionService {

    private final ProfessionRepository professionRepository;
    private final ProfessionMapper professionMapper;

    public List<ProfessionResponseDto> getAllProfessions() {

        List<Profession> professions = professionRepository.findAll();

        return professions
                .stream()
                .map(professionMapper::toDto)
                .toList();
    }


}
