package com.progrohan.interview_flow.service;

import com.progrohan.interview_flow.entity.ReviewState;
import com.progrohan.interview_flow.entity.UserQuestionState;
import com.progrohan.interview_flow.repository.UserQuestionStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserQuestionStateRepository stateRepository;

    public void processAnswer(
            Long userId,
            Long questionId,
            boolean correct
    ) {

        UserQuestionState state =
                stateRepository
                        .findByUserIdAndQuestionId(userId, questionId)
                        .orElseGet(() -> createInitialState(userId, questionId));

        applyUpdate(state, correct);

        state.setLastReviewAt(Instant.now());

        stateRepository.save(state);
    }

    private UserQuestionState createInitialState(Long userId, Long questionId) {

        return UserQuestionState.builder()
                .userId(userId)
                .questionId(questionId)
                .state(ReviewState.NEW)
                .repetitions(0)
                .intervalDays(1)
                .easeFactor(2.5)
                .lapses(0)
                .nextReviewAt(Instant.now())
                .lastReviewAt(null)
                .build();
    }

    private void applyUpdate(UserQuestionState state, boolean correct) {

        if (!correct) {
            handleIncorrect(state);
            return;
        }

        handleCorrect(state);
    }

    private void handleIncorrect(UserQuestionState state) {

        state.setRepetitions(0);
        state.setIntervalDays(1);
        state.setLapses(state.getLapses() + 1);
        state.setState(ReviewState.RELEARNING);

        state.setNextReviewAt(
                Instant.now().plus(1, ChronoUnit.DAYS)
        );
    }

    private void handleCorrect(UserQuestionState state) {

        int reps = state.getRepetitions() + 1;
        double ef = state.getEaseFactor();

        int interval;

        if (reps == 1) {
            interval = 1;
        } else if (reps == 2) {
            interval = 6;
        } else {
            interval = (int) (state.getIntervalDays() * ef);
        }

        state.setRepetitions(reps);
        state.setIntervalDays(interval);
        state.setEaseFactor(ef);
        state.setState(ReviewState.REVIEW);

        state.setNextReviewAt(
                Instant.now().plus(interval, ChronoUnit.DAYS)
        );
    }
}
