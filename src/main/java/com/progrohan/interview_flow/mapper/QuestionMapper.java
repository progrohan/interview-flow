package com.progrohan.interview_flow.mapper;

import com.progrohan.interview_flow.dto.QuestionRequestDto;
import com.progrohan.interview_flow.dto.QuestionResponseDto;
import com.progrohan.interview_flow.entity.Profession;
import com.progrohan.interview_flow.entity.Question;
import com.progrohan.interview_flow.entity.Topic;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring",
        uses = {
                AnswerOptionMapper.class,
                TextAnswerRuleMapper.class
        }
)
public interface QuestionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profession", source = "profession")
    @Mapping(target = "topic", source = "topic")
    @Mapping(target = "answerOptions", source = "dto.answerOptions")
    @Mapping(target = "textAnswerRule", source = "dto.textAnswerRule")
    Question toEntity(QuestionRequestDto dto,
                      Profession profession,
                      Topic topic);

    @AfterMapping
    default void linkChildren(@MappingTarget Question question) {

        if (question.getAnswerOptions() != null) {
            question.getAnswerOptions()
                    .forEach(opt -> opt.setQuestion(question));
        }

        if (question.getTextAnswerRule() != null) {
            question.getTextAnswerRule()
                    .setQuestion(question);
        }
    }


    @Mapping(target = "professionId", source = "profession.id")
    @Mapping(target = "topicId", source = "topic.id")
    QuestionResponseDto toDto(Question question);
}