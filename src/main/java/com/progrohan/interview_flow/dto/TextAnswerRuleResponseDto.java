package com.progrohan.interview_flow.dto;

import java.util.List;

public record TextAnswerRuleResponseDto(

        Long id,

        List<String> keywords,

        Integer minMatches
) {
}
