package com.progrohan.interview_flow.service;

import com.progrohan.interview_flow.dto.ValidationResultDto;
import com.progrohan.interview_flow.entity.Question;
import com.progrohan.interview_flow.entity.QuestionType;
import com.progrohan.interview_flow.service.validator.AnswerValidator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionEvaluationService {

    private final Map<QuestionType, AnswerValidator> validators;


    public QuestionEvaluationService(List<AnswerValidator> validatorList) {

        this.validators = new HashMap<>();

        for (AnswerValidator validator : validatorList) {
            for (QuestionType type : QuestionType.values()) {
                if (validator.supports(type)) {
                    validators.put(type, validator);
                }
            }
        }

        for (Map.Entry<QuestionType, AnswerValidator> entry : validators.entrySet()) {

            System.out.println(
                    entry.getKey() + " -> " +
                    entry.getValue().getClass().getSimpleName()
            );
        }

    }

    public ValidationResultDto validate(Question question, Object answer, boolean hasNext) {

        AnswerValidator validator = validators.get(question.getType());

        if (validator == null) {
            throw new IllegalStateException(
                    "No validator for type: " + question.getType()
            );
        }

        return validator.validate(question, answer, hasNext);
    }





}
