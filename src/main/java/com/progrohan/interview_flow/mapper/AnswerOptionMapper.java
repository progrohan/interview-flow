package com.progrohan.interview_flow.mapper;

import com.progrohan.interview_flow.dto.AnswerOptionRequestDto;
import com.progrohan.interview_flow.dto.AnswerOptionResponseDto;
import com.progrohan.interview_flow.entity.AnswerOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnswerOptionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    AnswerOption toEntity(AnswerOptionRequestDto dto);

    AnswerOptionResponseDto toDto(AnswerOption entity);

}