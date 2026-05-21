package com.progrohan.interview_flow.service.validator;

import com.progrohan.interview_flow.dto.ValidationResultDto;
import com.progrohan.interview_flow.entity.Question;
import com.progrohan.interview_flow.entity.QuestionType;

public interface AnswerValidator {

    boolean supports(QuestionType type);

    ValidationResultDto validate(Question question, Object answer, boolean hasNext);
}
