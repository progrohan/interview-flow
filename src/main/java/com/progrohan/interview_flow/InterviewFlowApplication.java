package com.progrohan.interview_flow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class InterviewFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewFlowApplication.class, args);
	}

}
