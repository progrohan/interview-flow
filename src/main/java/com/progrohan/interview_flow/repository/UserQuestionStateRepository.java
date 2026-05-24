package com.progrohan.interview_flow.repository;

import com.progrohan.interview_flow.entity.UserQuestionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface UserQuestionStateRepository extends JpaRepository<UserQuestionState, Long> {


    @Query("""
        SELECT uqs.questionId
        FROM UserQuestionState uqs
        JOIN Question q ON q.id = uqs.questionId
        WHERE uqs.userId = :userId
        AND q.profession.id = :professionId
        AND uqs.nextReviewAt <= :now
        ORDER BY uqs.nextReviewAt ASC
        """)
    List<Long> findDueQuestionsIds(Long userId, Long professionId, Instant now, Pageable pageable);

    Optional<UserQuestionState> findByUserIdAndQuestionId(Long userId, Long questionId);
}
