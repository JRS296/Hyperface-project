package com.jrs296.ems.ControllerTests

import com.jrs296.ems.controller.AuthController
import com.jrs296.ems.models.DTOs.InputDTOs.EmployeeLoginInputDTO;
import com.jrs296.ems.models.DTOs.InputDTOs.EmployeeRegisterInputDTO;
import com.jrs296.ems.service.EmployeeInfoService;
import com.jrs296.ems.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTests {

    //TODO Mock authorization Case Scenarios - As Employee, DEPT_Manager, PROJECT_Manager and ADMIN

    private AuthController authController;

    @Mock
    private EmployeeInfoService employeeInfoService;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void testWelcome() {
        String result = authController.welcome();
        assertEquals("Welcome this endpoint is not secure", result);
    }

    @Test
    void testAddNewUser() {
        EmployeeRegisterInputDTO employeeRegisterInputDTO = new EmployeeRegisterInputDTO();
        employeeRegisterInputDTO.setEmployeeName("test10-user")
        employeeRegisterInputDTO.setUserName("test10")
        employeeRegisterInputDTO.setEmployeeEmail("test10@gmail.com")
        employeeRegisterInputDTO.setPassword("12345678")
        String result = authController.addNewUser(employeeRegisterInputDTO);
        assertEquals("User Added Successfully", result);
    }

    @Test
    void testAuthenticateAndGetToken() {
        EmployeeLoginInputDTO inputDTO = new EmployeeLoginInputDTO();
        inputDTO.setUsername("username");
        inputDTO.setPassword("password");
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken("username")).thenReturn("token");
        String result = authController.authenticateAndGetToken(inputDTO);
        assertEquals("token", result);
    }

    @Test
    void testAuthenticateAndGetToken_InvalidUser() {
        EmployeeLoginInputDTO inputDTO = new EmployeeLoginInputDTO();
        inputDTO.setUsername("username");
        inputDTO.setPassword("password");
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);
        try {
            authController.authenticateAndGetToken(inputDTO);
        } catch (UsernameNotFoundException e) {
            assertEquals("invalid user request!", e.getMessage());
        }
    }

    @Test
    void testUserProfile() {
        // Mock authentication with user role USER
        Authentication authentication = mock(Authentication.class);
        when(authentication.getAuthorities()).thenReturn(Collections.singletonList(new SimpleGrantedAuthority("USER")));
        String result = authController.userProfile(authentication);
        assertEquals("Welcome to User Profile", result);
    }

    @Test
    void testAdminProfile() {
        // Mock authentication with user role ADMIN
        Authentication authentication = mock(Authentication.class);
        when(authentication.getAuthorities()).thenReturn(Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));
        String result = authController.adminProfile(authentication);
        assertEquals("Welcome to Admin Profile", result);
    }

    @Test
    void testManagerProfile() {
        // Mock authentication with user roles ADMIN and PROJECT_MANAGER
        List<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("ADMIN"),
                new SimpleGrantedAuthority("PROJECT_MANAGER"),
                new SimpleGrantedAuthority("DEPT_MANAGER")
        );
        Authentication authentication = mock(Authentication.class);
        when(authentication.getAuthorities()).thenReturn(authorities);
        String result = authController.managerProfile(authentication);
        assertEquals("Welcome to Manager Profile", result);

    }
}
