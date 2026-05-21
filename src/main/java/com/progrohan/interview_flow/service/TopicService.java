package com.progrohan.interview_flow.service;


import com.progrohan.interview_flow.dto.TopicRequestDto;
import com.progrohan.interview_flow.dto.TopicResponseDto;
import com.progrohan.interview_flow.entity.Profession;
import com.progrohan.interview_flow.entity.Topic;
import com.progrohan.interview_flow.exception.ResourceNotFoundException;
import com.progrohan.interview_flow.mapper.TopicMapper;
import com.progrohan.interview_flow.repository.ProfessionRepository;
import com.progrohan.interview_flow.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

    public final ProfessionRepository professionRepository;
    public final TopicRepository topicRepository;
    public final TopicMapper topicMapper;

    public List<TopicResponseDto> getAllTopics() {

        return topicRepository
                .findAll()
                .stream()
                .map(topicMapper::toDto)
                .toList();

    }

    public TopicResponseDto getTopicById(Long id) {

        Topic topic = topicRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No topic with id"));

        return topicMapper.toDto(topic);
    }

    public String getTopicNameById(Long id) {

        return topicRepository.findById(id)
                .map(Topic::getName)
                .orElseThrow(() -> new ResourceNotFoundException("No topic with id"));

    }

    public TopicResponseDto createTopic(TopicRequestDto topicRequestDto) {

        Profession profession = professionRepository
                .findById(topicRequestDto.profession_id())
                .orElseThrow(() -> new ResourceNotFoundException("Profession not found"));

        Topic topic = topicMapper.toEntity(topicRequestDto, profession);


        topic = topicRepository.saveAndFlush(topic);

        return topicMapper.toDto(topic);

    }

    public void deleteTopic(Long id){

        if (!topicRepository.existsById(id)) {
            throw new ResourceNotFoundException("Topic not found");
        }

        topicRepository.deleteById(id);

    }



}
