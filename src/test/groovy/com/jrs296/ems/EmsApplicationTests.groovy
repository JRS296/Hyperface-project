package com.jrs296.ems

import com.jrs296.ems.ControllerTests.EmployeeControllerTests;
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class EmsApplicationTests {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void BuildStepTest() throws Exception {
		assert(restTemplate.getForObject("http://localhost:" + port + "/api/test",
				String.class)).contains("Build Successful, server up and running");
	}

	@Test
	void contextLoads() {
		// This test ensures that the Spring application context loads without errors
	}
}
