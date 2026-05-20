package com.progrohan.interview_flow.mapper;


import com.progrohan.interview_flow.dto.TopicRequestDto;
import com.progrohan.interview_flow.dto.TopicResponseDto;
import com.progrohan.interview_flow.entity.Profession;
import com.progrohan.interview_flow.entity.Topic;
import com.progrohan.interview_flow.repository.ProfessionRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class TopicMapper {

    @Autowired
    protected ProfessionRepository professionRepository;

    public abstract TopicResponseDto toDto(Topic topic);

    @Mapping(target = "profession", source = "profession_id")
    public abstract Topic toEntity(TopicRequestDto topicRequestDto);

    protected Profession map(Long professionId) {

        return professionRepository.findById(professionId)
                .orElseThrow(() -> new RuntimeException("Profession not found"));

    }
}
