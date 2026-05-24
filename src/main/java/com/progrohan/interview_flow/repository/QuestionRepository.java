package com.progrohan.interview_flow.repository;

import com.progrohan.interview_flow.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {


    @Query(value =
    """
        SELECT id
        FROM questions
        WHERE profession_id = :professionId
        ORDER BY RANDOM()
        LIMIT 7
    """, nativeQuery = true)
    List<Long> findRandomQuestionIds(Long professionId);


    @Query(value =
            """
                SELECT q.id
                FROM questions q
                WHERE q.profession_id = :professionId
                AND NOT EXISTS (
                    SELECT 1
                    FROM user_question_state uqs
                    WHERE uqs.user_id = :userId
                    AND uqs.question_id = q.id
                )
                ORDER BY RANDOM()
                LIMIT :limit
            """,
            nativeQuery = true)
    List<Long> findRandomNewQuestionsIds(Long userId, Long professionId, int limit);
}
