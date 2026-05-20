package com.progrohan.interview_flow.dto;

import java.util.List;

public record TextAnswerRuleRequestDto(

        List<String> keywords,

        Integer minMatches

) {
}
