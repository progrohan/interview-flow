package com.progrohan.interview_flow.controller;

import com.progrohan.interview_flow.dto.QuestionRequestDto;
import com.progrohan.interview_flow.dto.QuestionResponseDto;
import com.progrohan.interview_flow.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<QuestionResponseDto> createQuestion(@RequestBody QuestionRequestDto question) throws URISyntaxException {

        QuestionResponseDto questionResponseDto = questionService.create(question);

        return ResponseEntity
                .created(new URI("/api/question/" + questionResponseDto.id() ))
                .body(questionResponseDto);

    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDto> getQuestionById(@PathVariable Long id)  {

        return ResponseEntity.ok( questionService.findById(id));

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestionById(@PathVariable Long id)  {

        questionService.delete(id);

    }



}
