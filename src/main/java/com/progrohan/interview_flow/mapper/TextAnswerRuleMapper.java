package com.progrohan.interview_flow.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.progrohan.interview_flow.dto.TextAnswerRuleRequestDto;
import com.progrohan.interview_flow.dto.TextAnswerRuleResponseDto;
import com.progrohan.interview_flow.entity.TextAnswerRule;
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
            throw new RuntimeException("Failed to serialize keywords", e);
        }
    }
    default List<String> map(String json) {
        if (json == null) return null;

        try {
            return new ObjectMapper()
                    .readValue(json, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse keywordsJson", e);
        }
    }

}
