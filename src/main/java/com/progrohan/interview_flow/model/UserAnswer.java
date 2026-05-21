package com.progrohan.interview_flow.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAnswer {

    private Long questionId;

    private boolean correct;

    private Object answer;

    private Long topicId;

}
