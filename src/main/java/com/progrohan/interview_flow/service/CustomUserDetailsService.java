package com.progrohan.interview_flow.service;

import com.progrohan.interview_flow.entity.User;
import com.progrohan.interview_flow.model.CustomUserDetails;
import com.progrohan.interview_flow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException(name));

        return new CustomUserDetails(user);
    }

    public User loadUserEntityByUsername(String username) throws UsernameNotFoundException{
        return userRepository
                .findByName(username)
                .orElseThrow(()-> new UsernameNotFoundException("User with username" + username + "does not exist!"));
    }

    public Long getUserId(String name){
        User user = userRepository
                .findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User with username" + name + "does not exist!"));

        return user.getId();
    }
}