package com.progrohan.interview_flow.service;

import com.progrohan.interview_flow.dto.AttemptResultDto;
import com.progrohan.interview_flow.dto.ValidationResultDto;
import com.progrohan.interview_flow.entity.Question;
import com.progrohan.interview_flow.dto.QuestionResponseDto;
import com.progrohan.interview_flow.dto.SafeQuestionDto;
import com.progrohan.interview_flow.dto.TopicMistakeDto;
import com.progrohan.interview_flow.exception.AttemptNotEndedException;
import com.progrohan.interview_flow.exception.ResourceNotFoundException;
import com.progrohan.interview_flow.mapper.SafeQuestionMapper;
import com.progrohan.interview_flow.model.AttemptStatus;
import com.progrohan.interview_flow.model.OngoingAttempt;
import com.progrohan.interview_flow.model.UserAnswer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class OngoingAttemptService {

    private final QuestionService questionService;
    private final QuestionEvaluationService questionEvaluationService;
    private final TopicService topicService;
    private final SafeQuestionMapper safeQuestionMapper;
    
    private final ConcurrentHashMap<UUID, OngoingAttempt> ongoingAttempts;

    public OngoingAttemptService(QuestionService questionService, QuestionEvaluationService questionEvaluationService, SafeQuestionMapper safeQuestionMapper, TopicService topicService) {
        this.questionService = questionService;
        this.questionEvaluationService = questionEvaluationService;
        this.safeQuestionMapper = safeQuestionMapper;
        ongoingAttempts = new ConcurrentHashMap<>();
        this.topicService = topicService;
    }

    public UUID createOngoingAttempt(Long professionId) {

        UUID uuid = UUID.randomUUID();

        OngoingAttempt attempt = OngoingAttempt
                .builder()
                .id(uuid)
                .professionId(professionId)
                .questionIds(questionService.getRandomQuestionIds(professionId))
                .currentQuestionIndex(0)
                .answers(new HashMap<>())
                .status(AttemptStatus.ONGOING)
                .startedAt(Instant.now())
                .build();

        ongoingAttempts.put(uuid, attempt);

        return attempt.getId();

    }


    public ValidationResultDto validateAnswer(UUID uuid, UserAnswer userAnswer) {

        OngoingAttempt attempt = getOngoingAttempt(uuid);
        int currentQuestionIdx = attempt.getCurrentQuestionIndex();

        Question question = questionService.findEntityById((long) currentQuestionIdx);

        ValidationResultDto validate = questionEvaluationService.validate(question, userAnswer, checkIfNotEnded(uuid));

        userAnswer.setCorrect(validate.correct());

        attempt.getAnswers().put(question.getId(), userAnswer);

        return validate;

    }

    public SafeQuestionDto nextQuestion(UUID uuid) {
        OngoingAttempt attempt = getOngoingAttempt(uuid);
        int currentQuestionIdx = attempt.getCurrentQuestionIndex();
        Long questionId = attempt.getQuestionIds().get(currentQuestionIdx);


        QuestionResponseDto byId = questionService
                .findById(questionId);

        attempt.setCurrentQuestionIndex(currentQuestionIdx + 1);
        updateStatus(uuid);

        return safeQuestionMapper.makeSafe(byId);

    }

    public AttemptResultDto getResult(UUID uuid){

        if (checkIfNotEnded(uuid)) throw new AttemptNotEndedException("Attempt has not ended") ;

        OngoingAttempt attempt = ongoingAttempts.get(uuid);
        int totalQuestions = attempt.getQuestionIds().size();
        int correctAnswers = (int) attempt
                .getAnswers()
                .values()
                .stream()
                .filter(UserAnswer::isCorrect)
                .count();

        double percentage = ((double) correctAnswers / totalQuestions) * 100;

        Map<Long, Integer> mistakesByTopic = new HashMap<>();

        for (UserAnswer answer : attempt.getAnswers().values()) {
            if (!answer.isCorrect()) {
                Long topicId = answer.getTopicId();

                mistakesByTopic.put(
                        topicId,
                        mistakesByTopic.getOrDefault(topicId, 0) + 1
                );
            }
        }
        List<TopicMistakeDto> weakTopics = new ArrayList<>();

        for (Map.Entry<Long, Integer> entry : mistakesByTopic.entrySet()) {
            Long topicId = entry.getKey();
            Integer count = entry.getValue();

            String topicName = topicService.getTopicNameById(topicId);

            weakTopics.add(new TopicMistakeDto(
                    topicId,
                    topicName,
                    count
            ));
        }

        return new AttemptResultDto(totalQuestions, correctAnswers, weakTopics, percentage);

    }

    public void updateStatus(UUID uuid) {

        OngoingAttempt attempt = getOngoingAttempt(uuid);

        if(attempt.getCurrentQuestionIndex() >= attempt.getQuestionIds().size()){
            attempt.setStatus(AttemptStatus.COMPLETED);
        }

    }

    public boolean checkIfNotEnded(UUID uuid) {

        OngoingAttempt attempt = ongoingAttempts.get(uuid);

        return attempt.getStatus() != AttemptStatus.COMPLETED;

    }

    public OngoingAttempt getOngoingAttempt(UUID uuid) {

        if(!ongoingAttempts.containsKey(uuid)){
            throw new ResourceNotFoundException("OngoingAttempt not found");
        }

        return ongoingAttempts.get(uuid);

    }

    @Scheduled(fixedRate = 60000)
    public void cleanupExpiredAttempts(){
        Instant now = Instant.now();
        ongoingAttempts.entrySet().removeIf(entry -> {

            OngoingAttempt attempt = entry.getValue();

            boolean expired = attempt.getStartedAt()
                    .plus(Duration.ofMinutes(30))
                    .isBefore(now);

            boolean expiredTooMuch = attempt.getStartedAt()
                    .plus(Duration.ofMinutes(60))
                    .isBefore(now);

            boolean completed = attempt.getStatus() == AttemptStatus.COMPLETED;

            return expired && completed || expiredTooMuch;
        });
    }

}
