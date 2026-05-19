package com.progrohan.interview_flow.mapper;


import com.progrohan.interview_flow.dto.ProfessionResponseDto;
import com.progrohan.interview_flow.entity.Profession;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfessionMapper {

    ProfessionResponseDto toDto(Profession profession);

    Profession toEntity(ProfessionResponseDto professionResponseDto);

}
