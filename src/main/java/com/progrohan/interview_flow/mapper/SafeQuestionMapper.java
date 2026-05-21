package com.progrohan.interview_flow.mapper;

import com.progrohan.interview_flow.dto.QuestionResponseDto;
import com.progrohan.interview_flow.dto.SafeQuestionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {
                AnswerOptionMapper.class,
                TextAnswerRuleMapper.class
        })
public interface SafeQuestionMapper {

    SafeQuestionDto makeSafe(QuestionResponseDto dto);


}
