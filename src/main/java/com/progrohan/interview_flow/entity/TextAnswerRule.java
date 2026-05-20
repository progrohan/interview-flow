package com.progrohan.interview_flow.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "text_answer_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextAnswerRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "keywords_json",columnDefinition = "TEXT")
    private String keywordsJson;

    @Column(name = "min_matches")
    private Integer minMatches;
}
