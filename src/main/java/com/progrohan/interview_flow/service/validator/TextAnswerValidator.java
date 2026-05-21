package com.progrohan.interview_flow.service.validator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progrohan.interview_flow.dto.ValidationResultDto;
import com.progrohan.interview_flow.entity.Question;
import com.progrohan.interview_flow.entity.QuestionType;
import com.progrohan.interview_flow.entity.TextAnswerRule;
import org.springframework.stereotype.Component;


import java.util.Set;

@Component
public class TextAnswerValidator implements AnswerValidator {

    private final ObjectMapper mapper;

    public TextAnswerValidator() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public boolean supports(QuestionType type) {
        return type == QuestionType.TEXT;
    }

    @Override
    public ValidationResultDto validate(Question question, Object answer, boolean hasNext)  {

        String userText = ((String) answer).toLowerCase();

        TextAnswerRule rule = question.getTextAnswerRule();
        Set<String> keywords;

        try{
            keywords = mapper.readValue(rule.getKeywordsJson(), new TypeReference<Set<String>>() {});
        }catch(JsonProcessingException e){
            throw new RuntimeException("problem with mapping json");
        }

        long matches = keywords.stream()
                .filter(k -> userText.contains(k.toLowerCase()))
                .count();

        boolean correct = matches >= rule.getMinMatches();

        return new ValidationResultDto(
                correct,
                question.getExplanation(),
                hasNext
        );
    }
}