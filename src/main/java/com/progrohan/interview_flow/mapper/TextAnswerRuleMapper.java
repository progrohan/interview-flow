package com.progrohan.interview_flow.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.progrohan.interview_flow.dto.TextAnswerRuleRequestDto;
import com.progrohan.interview_flow.dto.TextAnswerRuleResponseDto;
import com.progrohan.interview_flow.entity.TextAnswerRule;
import com.progrohan.interview_flow.exception.InvalidStoredJsonException;
import java.util.Collections;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TextAnswerRuleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "keywordsJson", source = "keywords")
    TextAnswerRule toEntity(TextAnswerRuleRequestDto dto);

    @Mapping(target = "keywords", source = "keywordsJson")
    TextAnswerRuleResponseDto toDto(TextAnswerRule entity);

    default String map(List<String> keywords) {
        if (keywords == null) return null;

        try {
            return new ObjectMapper()
                    .writeValueAsString(keywords);
        } catch (Exception e) {
            throw new InvalidStoredJsonException("Failed to serialize keywords");
        }
    }
    default List<String> map(String json) {
        if (json == null) return Collections.emptyList();

        try {
            return new ObjectMapper()
                    .readValue(json, new TypeReference<>() {});
        } catch (Exception e) {
            throw new InvalidStoredJsonException("Failed to parse keywordsJson");
        }
    }

}
