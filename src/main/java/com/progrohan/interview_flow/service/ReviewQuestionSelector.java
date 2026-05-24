package com.progrohan.interview_flow.service;


import com.progrohan.interview_flow.repository.QuestionRepository;
import com.progrohan.interview_flow.repository.UserQuestionStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewQuestionSelector {

    private final UserQuestionStateRepository stateRepository;
    private final QuestionRepository questionRepository;

    public List<Long> selectQuestions(Long userId, Long professionId, int totalQuestions) {

        List<Long> result = stateRepository.findDueQuestionsIds(userId, professionId, Instant.now(), PageRequest.of(0, totalQuestions));

        int remaining = totalQuestions - result.size();

        if (remaining > 0) {

            List<Long> newQuestions =
                    questionRepository.findRandomNewQuestionsIds(userId, professionId, remaining);

            result.addAll(newQuestions);
        }

        return result;
    }
}
