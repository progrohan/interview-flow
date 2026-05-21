package com.progrohan.interview_flow.service.validator;

import com.progrohan.interview_flow.dto.ValidationResultDto;
import com.progrohan.interview_flow.entity.Question;
import com.progrohan.interview_flow.entity.QuestionType;
import org.springframework.stereotype.Component;

@Component
public class SingleChoiceValidator implements AnswerValidator {

    @Override
    public boolean supports(QuestionType type) {
        return type == QuestionType.SINGLE;
    }

    @Override
    public ValidationResultDto validate(Question question, Object answer, boolean hasNext) {

        Long selectedOptionId = (Long) answer;

        boolean correct = question.getAnswerOptions()
                .stream()
                .anyMatch(opt ->
                        opt.getId().equals(selectedOptionId) && opt.getCorrect()
                );

        return new ValidationResultDto(
                correct,
                question.getExplanation(),
                hasNext
        );
    }
}
