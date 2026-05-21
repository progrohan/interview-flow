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

}
