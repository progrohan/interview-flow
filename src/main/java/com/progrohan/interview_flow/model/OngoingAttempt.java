package com.progrohan.interview_flow.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class OngoingAttempt {

    private UUID id;

    private Long professionId;

    private List<Long> questionIds;

    private int currentQuestionIndex;

    private Map<Long, UserAnswer> answers;

    private AttemptStatus status;

    private Instant startedAt;

}
