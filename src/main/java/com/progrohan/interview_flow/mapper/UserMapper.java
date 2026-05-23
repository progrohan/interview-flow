package com.progrohan.interview_flow.mapper;

import com.progrohan.interview_flow.dto.UserRequestDto;
import com.progrohan.interview_flow.dto.UserResponseDto;
import com.progrohan.interview_flow.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toDto(User user);

    UserResponseDto toResponseDto(UserRequestDto userRequestDto);

}
