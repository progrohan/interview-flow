package com.progrohan.interview_flow.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAnswer {

    private Long questionId;

    private boolean correct;

    private Object answer;

    private Long topicId;

}
