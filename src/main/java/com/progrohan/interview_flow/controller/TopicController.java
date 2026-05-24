package com.progrohan.interview_flow.controller;

import com.progrohan.interview_flow.dto.TopicRequestDto;
import com.progrohan.interview_flow.dto.TopicResponseDto;
import com.progrohan.interview_flow.model.CustomUserDetails;
import com.progrohan.interview_flow.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/topic")
@RequiredArgsConstructor
public class TopicController {

    public final TopicService topicService;

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponseDto> getTopic(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity.ok(topicService.getTopicById(id));

    }

    @GetMapping
    public ResponseEntity<List<TopicResponseDto>> getTopics(@AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity.ok(topicService.getAllTopics());

    }

    @PostMapping
    public ResponseEntity<TopicResponseDto> createTopic(@RequestBody TopicRequestDto topicRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails) throws URISyntaxException {

        TopicResponseDto topic = topicService.createTopic(topicRequestDto);

        return ResponseEntity
                .created(new URI("/api/topic/" + topic.id()))
                .body(topic);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopic(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {

        topicService.deleteTopic(id);

    }



}
