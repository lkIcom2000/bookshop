package dk.au.userservice.controller;

import dk.au.userservice.config.TestConfig;
import dk.au.userservice.dto.UserDTO;
import dk.au.userservice.model.User;
import dk.au.userservice.model.UserRole;
import dk.au.userservice.model.PaymentOptions;
import dk.au.userservice.service.UserService;
import dk.au.userservice.utils.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private UserController userController;
    private User testUser;
    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {
        userController = new UserController(userService, userMapper);
        
        testUser = new User(
            "John Doe",
            new Date(System.currentTimeMillis()),
            UserRole.USER,
            "123 Main St",
            12345678,
            PaymentOptions.CREDITCARD
        );
        
        testUserDTO = new UserDTO(
            testUser.getName(),
            testUser.getBirth(),
            testUser.getRole().name(),
            testUser.getAdress(),
            testUser.getPhoneNumber(),
            testUser.getPayment().name()
        );

        when(userMapper.toDTO(any(User.class))).thenReturn(testUserDTO);
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(testUser);
    }

    // Direct Controller Tests
    @Test
    void getUserById_DirectCall_WhenUserExists_ShouldReturnUser() {
        when(userService.getUserById(1L)).thenReturn(Optional.of(testUser));

        ResponseEntity<UserDTO> response = userController.getUserById(1L);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo(testUser.getName());
        assertThat(response.getBody().getRole()).isEqualTo(testUser.getRole().name());
        
        verify(userService).getUserById(1L);
        verify(userMapper).toDTO(testUser);
    }

    @Test
    void getUserById_DirectCall_WhenUserDoesNotExist_ShouldReturn404() {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        ResponseEntity<UserDTO> response = userController.getUserById(1L);

        assertThat(response.getStatusCode().value()).isEqualTo(404);
        verify(userService).getUserById(1L);
        verify(userMapper, never()).toDTO(any());
    }

    @Test
    void createUser_DirectCall_ShouldReturnCreatedUser() {
        when(userService.createUser(any(User.class))).thenReturn(testUser);

        ResponseEntity<UserDTO> response = userController.createUser(testUserDTO);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo(testUser.getName());
        assertThat(response.getBody().getRole()).isEqualTo(testUser.getRole().name());
        
        verify(userMapper).toEntity(testUserDTO);
        verify(userService).createUser(any(User.class));
        verify(userMapper).toDTO(testUser);
    }

    // MockMvc Integration Tests
    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testUser.getName()))
                .andExpect(jsonPath("$.role").value(testUser.getRole().name()))
                .andExpect(jsonPath("$.adress").value(testUser.getAdress()))
                .andExpect(jsonPath("$.phoneNumber").value(testUser.getPhoneNumber()))
                .andExpect(jsonPath("$.payment").value(testUser.getPayment().name()));
    }

    @Test
    void getUserById_WhenUserDoesNotExist_ShouldReturn404() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(testUser);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testUser.getName()))
                .andExpect(jsonPath("$.role").value(testUser.getRole().name()))
                .andExpect(jsonPath("$.adress").value(testUser.getAdress()))
                .andExpect(jsonPath("$.phoneNumber").value(testUser.getPhoneNumber()))
                .andExpect(jsonPath("$.payment").value(testUser.getPayment().name()));
    }
} 