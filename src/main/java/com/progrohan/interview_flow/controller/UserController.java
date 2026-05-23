package com.progrohan.interview_flow.controller;

import com.progrohan.interview_flow.dto.UserResponseDto;
import com.progrohan.interview_flow.model.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getUser(@AuthenticationPrincipal CustomUserDetails userDetails){

        UserResponseDto userResponseDTO = new UserResponseDto(userDetails.getUsername(), userDetails.getRole());

        return ResponseEntity.ok(userResponseDTO);
    }

}
