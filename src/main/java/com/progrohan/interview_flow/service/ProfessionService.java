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

    public ProfessionResponseDto getProfessionById(Long id) {

        Profession profession = professionRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No profession with id - " + id));

        return professionMapper.toDto(profession);
    }

    public ProfessionResponseDto createProfession(String professionName) {
        Profession profession = new Profession();
        profession.setName(professionName);
        profession = professionRepository.saveAndFlush(profession);
        return professionMapper.toDto(profession);
    }

    public void deleteProfession(Long id) {

        if (!professionRepository.existsById(id)) {
            throw new RuntimeException("No profession with id - " + id);
        }

        professionRepository.deleteById(id);

    }


}
