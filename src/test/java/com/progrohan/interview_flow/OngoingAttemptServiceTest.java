package com.progrohan.interview_flow;

import com.progrohan.interview_flow.dto.AttemptResultDto;
import com.progrohan.interview_flow.dto.QuestionResponseDto;
import com.progrohan.interview_flow.dto.SafeQuestionDto;
import com.progrohan.interview_flow.dto.TopicMistakeDto;
import com.progrohan.interview_flow.dto.ValidationResultDto;
import com.progrohan.interview_flow.entity.Question;
import com.progrohan.interview_flow.entity.QuestionType;
import com.progrohan.interview_flow.exception.ResourceNotFoundException;
import com.progrohan.interview_flow.mapper.SafeQuestionMapper;
import com.progrohan.interview_flow.model.AttemptStatus;
import com.progrohan.interview_flow.model.OngoingAttempt;
import com.progrohan.interview_flow.model.UserAnswer;
import com.progrohan.interview_flow.service.OngoingAttemptService;
import com.progrohan.interview_flow.service.QuestionEvaluationService;
import com.progrohan.interview_flow.service.QuestionService;
import com.progrohan.interview_flow.service.TopicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OngoingAttemptServiceTest {

    @Mock
    private QuestionService questionService;

    @Mock
    private QuestionEvaluationService questionEvaluationService;

    @Mock
    private TopicService topicService;

    @Mock
    private SafeQuestionMapper safeQuestionMapper;

    private Clock clock;

    private OngoingAttemptService service;

    @BeforeEach
    void setUp() {
        clock = Clock.fixed(
                Instant.parse("2026-05-23T10:00:00Z"),
                ZoneOffset.UTC
        );

        service = new OngoingAttemptService(
                questionService,
                questionEvaluationService,
                safeQuestionMapper,
                topicService,
                clock
        );
    }

    @Test
    void shouldCreateAttempt() {

        List<Long> questionIds = List.of(1L, 2L, 3L);

        when(questionService.getRandomQuestionIds(1L))
                .thenReturn(questionIds);

        UUID uuid = service.createOngoingAttempt(1L);

        OngoingAttempt attempt = service.getOngoingAttempt(uuid);

        assertThat(attempt).isNotNull();
        assertThat(attempt.getProfessionId()).isEqualTo(1L);
        assertThat(attempt.getQuestionIds()).isEqualTo(questionIds);
        assertThat(attempt.getCurrentQuestionIndex()).isZero();
        assertThat(attempt.getAnswers()).isEmpty();
        assertThat(attempt.getStatus()).isEqualTo(AttemptStatus.ONGOING);
        assertThat(attempt.getStartedAt())
                .isEqualTo(Instant.now(clock));

    }

    @Test
    void shouldValidateAndSaveAnswer() {

        when(questionService.getRandomQuestionIds(1L))
                .thenReturn(List.of(10L));

        UUID uuid = service.createOngoingAttempt(1L);

        Question question = new Question();
        question.setId(10L);

        when(questionService.findEntityById(10L))
                .thenReturn(question);

        UserAnswer answer = new UserAnswer();

        ValidationResultDto validation =
                new ValidationResultDto(true, "correct", true);

        when(questionEvaluationService.validate(
                question,
                answer,
                true
        )).thenReturn(validation);

        ValidationResultDto result =
                service.validateAnswer(uuid, answer);

        assertThat(result).isEqualTo(validation);

        OngoingAttempt attempt = service.getOngoingAttempt(uuid);

        assertThat(attempt.getAnswers())
                .containsKey(10L);

        assertThat(answer.isCorrect()).isTrue();
    }

    @Test
    void shouldReturnNextQuestionAndIncrementIndex() {

        when(questionService.getRandomQuestionIds(1L))
                .thenReturn(List.of(10L));

        UUID uuid = service.createOngoingAttempt(1L);

        QuestionResponseDto dto = new QuestionResponseDto(  10L,
                "What is JVM?",
                QuestionType.SINGLE,
                1L,
                2L,
                "Explanation",
                "https://example.com",
                List.of(),
                null
        );

        SafeQuestionDto safeDto = new SafeQuestionDto( 10L,
                "What is JVM?",
                QuestionType.SINGLE,
                1L,
                2L,
                "Explanation",
                "https://example.com",
                List.of());

        when(questionService.findById(10L))
                .thenReturn(dto);

        when(safeQuestionMapper.makeSafe(dto))
                .thenReturn(safeDto);

        SafeQuestionDto result = service.nextQuestion(uuid);

        assertThat(result).isEqualTo(safeDto);

        OngoingAttempt attempt = service.getOngoingAttempt(uuid);

        assertThat(attempt.getCurrentQuestionIndex())
                .isEqualTo(1);

        assertThat(attempt.getStatus())
                .isEqualTo(AttemptStatus.COMPLETED);
    }

    @Test
    void shouldBuildResultCorrectly() {

        when(questionService.getRandomQuestionIds(1L))
                .thenReturn(List.of(1L, 2L));

        UUID uuid = service.createOngoingAttempt(1L);

        OngoingAttempt attempt = service.getOngoingAttempt(uuid);

        UserAnswer correct = new UserAnswer();
        correct.setCorrect(true);
        correct.setTopicId(1L);

        UserAnswer wrong = new UserAnswer();
        wrong.setCorrect(false);
        wrong.setTopicId(2L);

        attempt.getAnswers().put(1L, correct);
        attempt.getAnswers().put(2L, wrong);

        attempt.setStatus(AttemptStatus.COMPLETED);

        when(topicService.getTopicNameById(2L))
                .thenReturn("Collections");

        AttemptResultDto result = service.getResult(uuid);

        assertThat(result.totalQuestions()).isEqualTo(2);
        assertThat(result.correctAnswers()).isEqualTo(1);
        assertThat(result.percentage()).isEqualTo(50.0);

        assertThat(result.weakTopics())
                .hasSize(1);

        TopicMistakeDto topic = result.weakTopics().getFirst();

        assertThat(topic.topicId()).isEqualTo(2L);
        assertThat(topic.topicName()).isEqualTo("Collections");
        assertThat(topic.mistakesCount()).isEqualTo(1);
    }

    @Test
    void shouldRemoveExpiredCompletedAttempts() {

        when(questionService.getRandomQuestionIds(1L))
                .thenReturn(List.of(1L));

        UUID uuid = service.createOngoingAttempt(1L);

        OngoingAttempt attempt = service.getOngoingAttempt(uuid);

        attempt.setStatus(AttemptStatus.COMPLETED);

        attempt.setStartedAt(
                Instant.now(clock).minus(Duration.ofMinutes(31))
        );

        service.cleanupExpiredAttempts();

        assertThatThrownBy(() ->
                service.getOngoingAttempt(uuid))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldNotRemoveOngoingAttemptsUnder60Minutes() {

        when(questionService.getRandomQuestionIds(1L))
                .thenReturn(List.of(1L));

        UUID uuid = service.createOngoingAttempt(1L);

        OngoingAttempt attempt = service.getOngoingAttempt(uuid);

        attempt.setStartedAt(
                Instant.now(clock).minus(Duration.ofMinutes(40))
        );

        service.cleanupExpiredAttempts();

        assertThat(service.getOngoingAttempt(uuid))
                .isNotNull();
    }
}