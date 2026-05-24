package com.progrohan.interview_flow.controller;

import com.progrohan.interview_flow.dto.AttemptResultDto;
import com.progrohan.interview_flow.dto.ProfessionRequestDto;
import com.progrohan.interview_flow.dto.SafeQuestionDto;
import com.progrohan.interview_flow.dto.ValidationResultDto;
import com.progrohan.interview_flow.model.CustomUserDetails;
import com.progrohan.interview_flow.model.UserAnswer;
import com.progrohan.interview_flow.service.CustomUserDetailsService;
import com.progrohan.interview_flow.service.OngoingAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/attempt")
public class AttemptController {

    private final OngoingAttemptService ongoingAttemptService;
    private final CustomUserDetailsService userService;

    @PostMapping
    public ResponseEntity<UUID> startAttempt(@RequestBody ProfessionRequestDto profession, @AuthenticationPrincipal CustomUserDetails userDetails) throws URISyntaxException {

        UUID ongoingAttempt = ongoingAttemptService
                .createOngoingAttempt(profession.id(), userService.getUserId(userDetails.getUsername()));

        return ResponseEntity
                .created(new URI("/api/attempt/" + ongoingAttempt))
                .body(ongoingAttempt);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SafeQuestionDto> nextQuestion(@PathVariable UUID id, @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity.ok(ongoingAttemptService.nextQuestion(id));

    }

    @GetMapping("/{id}/result")
    public ResponseEntity<AttemptResultDto> getResult(@PathVariable UUID id, @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity.ok(ongoingAttemptService.getResult(id));

    }

    @PostMapping("/{id}/validate")
    public ResponseEntity<ValidationResultDto> validateAnswer( @PathVariable UUID id, @RequestBody UserAnswer userAnswer, @AuthenticationPrincipal CustomUserDetails userDetails){

        return ResponseEntity.ok(ongoingAttemptService.validateAnswer(id,userService.getUserId(userDetails.getUsername()), userAnswer));

    }



}
