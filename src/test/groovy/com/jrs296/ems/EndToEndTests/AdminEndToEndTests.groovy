package com.jrs296.ems.EndToEndTests

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class AdminEndToEndTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        //Create 3 Departments

        // Create 4 Projects - 1 for Dep1, 0 for Dep2, 3 for Dep3

        // Create 9 employees (total 10 w/ ADMIN)
    }

    /*
     TODO #1 - Assignment of managers

     TODO Test Case Scenario 1 - Assign a DEPT_MANAGER to a DEPT without any DEPT_MANAGER - SUCCESS
     TODO Test Case Scenario 2 - Assign a PROJECT_MANAGER to a DEPT without any DEPT_MANAGER - FAIL
     TODO Test Case Scenario 3 - Assign a PROJECT_MANAGER to a DEPT with a DEPT_MANAGER - SUCCESS
     TODO Test Case Scenario 4 - Assign a DEPT_MANAGER to a DEPT with a DEPT_MANAGER - SUCCESS + OLD DEPT MANAGER becomes USER and is in Unassigned
     TODO Test Case Scenario 5 - Assign a PROJECT_MANAGER to a DEPT with a DEPT_MANAGER - SUCCESS + OLD DEPT MANAGER becomes USER and is in Unassigned, Old Project's USERS become Unassigned
     TODO Test Case Scenario 6 - Assign a DEPT_MANAGER to a PROJECT without a PROJECT_MANAGER - SUCCESS + OLD DEPT becomes unusable un-till Another DEPT_MANAGER is placed (no new projects, no assigning of projects), Old DEPT managerID = -1
     TODO Test Case Scenario 7 - Assign a DEPT_MANAGER to a PROJECT as a USER - SUCCESS + OLD DEPT becomes unusable un-till Another DEPT_MANAGER is placed (no new projects, no assigning of projects), Old DEPT managerID = -1

     Other Case Scenarios

     */

    @Test
    public void testSomething() {
        assert ("" == "")
    }

}