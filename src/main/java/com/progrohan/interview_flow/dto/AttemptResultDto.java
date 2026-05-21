package com.progrohan.interview_flow.dto;

import java.util.List;

public record AttemptResultDto(
        int totalQuestions,

        int correctAnswers,

        List<TopicMistakeDto> weakTopics,

        double percentage
) {
}
