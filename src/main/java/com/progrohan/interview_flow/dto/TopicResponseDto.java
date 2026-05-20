package com.progrohan.interview_flow.dto;

public record TopicResponseDto(
        Long id,
        String name,
        ProfessionResponseDto profession
) {
}
