package com.progrohan.interview_flow.dto;



public record ValidationResultDto (
        Boolean correct,

        String explanation,

        Boolean nextQuestionAvailable
){
}
