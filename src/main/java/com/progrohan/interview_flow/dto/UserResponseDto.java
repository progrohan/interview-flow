package com.progrohan.interview_flow.dto;

import com.progrohan.interview_flow.entity.Role;

public record UserResponseDto(

        String name,

        Role role

) {
}
