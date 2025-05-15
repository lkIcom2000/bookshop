package dk.au.userservice.service;

import dk.au.userservice.model.User;
import dk.au.userservice.model.UserRole;
import dk.au.userservice.model.PaymentOptions;
import dk.au.userservice.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(
            "John Doe",
            new Date(System.currentTimeMillis()),
            UserRole.USER,
            "123 Main St",
            12345678,
            PaymentOptions.CREDITCARD
        );
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        when(userRepo.findAll()).thenReturn(Arrays.asList(testUser));

        List<User> users = userService.getAllUsers();

        assertThat(users).hasSize(1);
        assertThat(users.get(0).getName()).isEqualTo(testUser.getName());
        assertThat(users.get(0).getRole()).isEqualTo(testUser.getRole());
        verify(userRepo).findAll();
    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(testUser));

        Optional<User> found = userService.getUserById(1L);

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(testUser.getName());
        assertThat(found.get().getRole()).isEqualTo(testUser.getRole());
        verify(userRepo).findById(1L);
    }

    @Test
    void getUserById_WhenUserDoesNotExist_ShouldReturnEmpty() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        Optional<User> found = userService.getUserById(1L);

        assertThat(found).isEmpty();
        verify(userRepo).findById(1L);
    }

    @Test
    void createUser_ShouldReturnCreatedUser() {
        when(userRepo.save(any(User.class))).thenReturn(testUser);

        User created = userService.createUser(testUser);

        assertThat(created.getName()).isEqualTo(testUser.getName());
        assertThat(created.getRole()).isEqualTo(testUser.getRole());
        verify(userRepo).save(testUser);
    }

    @Test
    void deleteUser_ShouldCallRepository() {
        userService.deleteUser(1L);

        verify(userRepo).deleteById(1L);
    }
} 