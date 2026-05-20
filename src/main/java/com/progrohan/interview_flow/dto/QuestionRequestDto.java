package com.progrohan.interview_flow.dto;

import com.progrohan.interview_flow.entity.QuestionType;

import java.util.List;

public record QuestionRequestDto(

        String text,

        QuestionType type,

        Long professionId,

        Long topicId,

        String explanation,

        String sourceUrl,

        List<AnswerOptionRequestDto> answerOptions,

        TextAnswerRuleRequestDto textAnswerRule

) {
}