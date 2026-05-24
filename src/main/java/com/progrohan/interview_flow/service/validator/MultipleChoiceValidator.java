package com.progrohan.interview_flow.service.validator;

import com.progrohan.interview_flow.dto.ValidationResultDto;
import com.progrohan.interview_flow.entity.AnswerOption;
import com.progrohan.interview_flow.entity.Question;
import com.progrohan.interview_flow.entity.QuestionType;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MultipleChoiceValidator implements AnswerValidator {

    @Override
    public boolean supports(QuestionType type) {
        return type == QuestionType.MULTIPLE;
    }

    @Override
    public ValidationResultDto validate(Question question, Object answer, boolean hasNext) {

        @SuppressWarnings("unchecked")
        List<Long> selectedIds = (List<Long>) answer;

        Set<Long> correctIds = question.getAnswerOptions()
                .stream()
                .filter(AnswerOption::getIsCorrect)
                .map(AnswerOption::getId)
                .collect(Collectors.toSet());

        boolean correct = correctIds.equals(new HashSet<>(selectedIds));

        return new ValidationResultDto(
                correct,
                question.getExplanation(),
                hasNext
        );
    }
}