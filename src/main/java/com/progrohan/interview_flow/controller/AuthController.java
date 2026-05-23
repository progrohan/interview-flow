package com.progrohan.interview_flow.controller;

import com.progrohan.interview_flow.dto.UserRequestDto;
import com.progrohan.interview_flow.dto.UserResponseDto;
import com.progrohan.interview_flow.mapper.UserMapper;
import com.progrohan.interview_flow.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
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

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequestDTO.name(),
                        userRequestDTO.password()
                )
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);

        SecurityContextHolder.setContext(context);
        session.setAttribute("SPRING_SECURITY_CONTEXT", context);


        return ResponseEntity.ok(userMapper.toResponseDto(userRequestDTO));

    }

}
