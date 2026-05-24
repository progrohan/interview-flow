package com.progrohan.interview_flow.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "user_question_state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserQuestionState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewState state;

    @Column()
    private Integer repetitions;

    @Column(name = "interval_days")
    private Integer intervalDays;

    @Column(name = "ease_factor")
    private Double easeFactor;

    @Column()
    private Integer lapses;

    @Column(name = "next_review_at")
    private Instant nextReviewAt;

    @Column(name = "last_review_at")
    private Instant lastReviewAt;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {

        Instant now = Instant.now();

        createdAt = now;
        updatedAt = now;

        if (state == null) {
            state = ReviewState.NEW;
        }

        if (repetitions == null) {
            repetitions = 0;
        }

        if (intervalDays == null) {
            intervalDays = 1;
        }

        if (easeFactor == null) {
            easeFactor = 2.5;
        }

        if (lapses == null) {
            lapses = 0;
        }

        if (nextReviewAt == null) {
            nextReviewAt = now;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}