package com.progrohan.interview_flow;

import org.springframework.boot.SpringApplication;

public class TestInterviewFlowApplication {

	public static void main(String[] args) {
		SpringApplication.from(InterviewFlowApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
