package com.progrohan.interview_flow.service;

import com.progrohan.interview_flow.dto.UserRequestDto;
import com.progrohan.interview_flow.dto.UserResponseDto;
import com.progrohan.interview_flow.entity.Role;
import com.progrohan.interview_flow.entity.User;
import com.progrohan.interview_flow.exception.AuthException;
import com.progrohan.interview_flow.exception.UserExistException;
import com.progrohan.interview_flow.mapper.UserMapper;
import com.progrohan.interview_flow.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto createUser(UserRequestDto userRequestDto) {

        if (userRepository.findByName(userRequestDto.name()).isPresent())
            throw new UserExistException("User with username " + userRequestDto.name() + " already exists!");

        User user = User.builder()
                .name(userRequestDto.name())
                .password(passwordEncoder.encode(userRequestDto.password()))
                .role(Role.ROLE_USER)
                .build();

        user = userRepository.saveAndFlush(user);


        return userMapper.toDto(user);

    }

    public UserResponseDto loginUser(UserRequestDto user, HttpSession session) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.name(), user.password()));

            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(auth);

            session.setAttribute("SPRING_SECURITY_CONTEXT", context);

            return userMapper.toResponseDto(user);
        } catch (AuthenticationException e) {
            throw new AuthException("Credentials incorrect!");
        }
    }

}