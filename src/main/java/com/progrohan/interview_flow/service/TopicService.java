package com.progrohan.interview_flow.service;


import com.progrohan.interview_flow.dto.TopicRequestDto;
import com.progrohan.interview_flow.dto.TopicResponseDto;
import com.progrohan.interview_flow.entity.Topic;
import com.progrohan.interview_flow.mapper.TopicMapper;
import com.progrohan.interview_flow.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

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
                .orElseThrow(() -> new RuntimeException("No topic with id"));

        return topicMapper.toDto(topic);
    }

    public TopicResponseDto createTopic(TopicRequestDto topicRequestDto) {

        Topic topic = topicMapper.toEntity(topicRequestDto);


        topic = topicRepository.saveAndFlush(topic);

        return topicMapper.toDto(topic);

    }

    public void deleteTopic(Long id){

        if (!topicRepository.existsById(id)) {
            throw new RuntimeException("Topic not found");
        }

        topicRepository.deleteById(id);

    }



}
