package com.progrohan.interview_flow;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Disabled
@Import(TestcontainersConfiguration.class)
@SpringBootTest
class InterviewFlowApplicationTests {

	@Test
	void contextLoads() {
	}

}
