package com.progrohan.interview_flow.configuration;

import com.progrohan.interview_flow.dto.UserRequestDto;
import com.progrohan.interview_flow.dto.UserResponseDto;
import com.progrohan.interview_flow.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRequestDto userRequestDto){

        UserResponseDto user = authService.createUser(userRequestDto);

        return ResponseEntity
                .created(URI.create("api/user/me"))
                .body(user);

    }


    @PostMapping("/sign-in")
    public ResponseEntity<UserResponseDto> signIn( @RequestBody UserRequestDto userRequestDTO, HttpSession session){

        UserResponseDto user = authService.loginUser(userRequestDTO, session);

        return ResponseEntity.ok(user);

    }

}
