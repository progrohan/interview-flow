package com.progrohan.interview_flow.service;

import com.progrohan.interview_flow.dto.QuestionRequestDto;
import com.progrohan.interview_flow.dto.QuestionResponseDto;
import com.progrohan.interview_flow.entity.Profession;
import com.progrohan.interview_flow.entity.Question;
import com.progrohan.interview_flow.entity.Topic;
import com.progrohan.interview_flow.mapper.QuestionMapper;
import com.progrohan.interview_flow.repository.ProfessionRepository;
import com.progrohan.interview_flow.repository.QuestionRepository;
import com.progrohan.interview_flow.repository.TopicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ProfessionRepository professionRepository;
    private final TopicRepository topicRepository;
    private final QuestionMapper questionMapper;

    @Transactional
    public QuestionResponseDto create(QuestionRequestDto dto) {

        Profession profession = professionRepository.findById(dto.professionId())
                .orElseThrow(() -> new RuntimeException("Profession not found"));

        Topic topic = topicRepository.findById(dto.topicId())
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        Question question = questionMapper.toEntity(dto, profession, topic);

        Question save = questionRepository.save(question);

        return questionMapper.toDto(save);
    }

    public QuestionResponseDto findById(Long id) {

        Question questionNotFound = questionRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        return questionMapper.toDto(questionNotFound);
    }

    public void delete(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new RuntimeException("No question with id - " + id);
        }

        questionRepository.deleteById(id);

    }

}
