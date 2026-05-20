package com.progrohan.interview_flow.repository;

import com.progrohan.interview_flow.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {

}
