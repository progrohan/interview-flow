package com.progrohan.interview_flow.repository;

import com.progrohan.interview_flow.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
