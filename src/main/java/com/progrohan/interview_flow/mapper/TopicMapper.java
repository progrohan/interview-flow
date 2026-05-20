package com.progrohan.interview_flow.mapper;


import com.progrohan.interview_flow.dto.TopicRequestDto;
import com.progrohan.interview_flow.dto.TopicResponseDto;
import com.progrohan.interview_flow.entity.Profession;
import com.progrohan.interview_flow.entity.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public abstract class TopicMapper {

    public abstract TopicResponseDto toDto(Topic topic);

    @Mapping(target = "profession", source = "profession")
    @Mapping(target = "name", source = "dto.name")
    public abstract Topic toEntity(TopicRequestDto dto, Profession profession);

}
