package com.wgu.taskmanagement.service;

import com.wgu.taskmanagement.exception.ResourceNotFoundException;
import com.wgu.taskmanagement.exception.ValidationException;
import com.wgu.taskmanagement.model.User;
import com.wgu.taskmanagement.model.UserRole;
import com.wgu.taskmanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserService.
 *
 * Test Plan Coverage:
 *   TC-39: create() encodes password before saving
 *   TC-40: create() sets default role USER when role is null
 *   TC-41: create() throws ValidationException when username is blank
 *   TC-42: create() throws ValidationException when email is invalid
 *   TC-43: create() throws ValidationException when username already exists
 *   TC-44: findByUsername() returns user when found
 *   TC-45: findByUsername() throws ResourceNotFoundException when not found
 *   TC-46: existsByUsername() returns true when username exists
 *   TC-47: existsByEmail() returns false when email does not exist
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Unit Tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User("testuser", "test@example.com", "plainpassword");
    }

    // TC-39
    @Test
    @DisplayName("TC-39: create() encodes password before saving")
    void create_EncodesPasswordBeforeSaving() {
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("plainpassword")).thenReturn("encodedpassword");
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);

        userService.create(sampleUser);

        verify(passwordEncoder, times(1)).encode("plainpassword");
        assertEquals("encodedpassword", sampleUser.getPassword());
    }

    // TC-40
    @Test
    @DisplayName("TC-40: create() sets default role USER when role is null")
    void create_SetsDefaultRoleWhenNull() {
        sampleUser.setRole(null);
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);

        userService.create(sampleUser);

        assertEquals(UserRole.USER, sampleUser.getRole());
    }

    // TC-41
    @Test
    @DisplayName("TC-41: create() throws ValidationException when username is blank")
    void create_ThrowsValidationExceptionWhenUsernameBlank() {
        User invalid = new User("  ", "valid@example.com", "password");

        assertThrows(ValidationException.class, () -> userService.create(invalid));
        verify(userRepository, never()).save(any());
    }

    // TC-42
    @Test
    @DisplayName("TC-42: create() throws ValidationException when email is invalid")
    void create_ThrowsValidationExceptionWhenEmailInvalid() {
        User invalid = new User("validuser", "not-an-email", "password");
        when(userRepository.existsByUsername("validuser")).thenReturn(false);

        assertThrows(ValidationException.class, () -> userService.create(invalid));
        verify(userRepository, never()).save(any());
    }

    // TC-43
    @Test
    @DisplayName("TC-43: create() throws ValidationException when username already exists")
    void create_ThrowsValidationExceptionWhenUsernameExists() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        assertThrows(ValidationException.class, () -> userService.create(sampleUser));
        verify(userRepository, never()).save(any());
    }

    // TC-44
    @Test
    @DisplayName("TC-44: findByUsername() returns user when found")
    void findByUsername_ReturnsUserWhenFound() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(sampleUser));

        User result = userService.findByUsername("testuser");

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    // TC-45
    @Test
    @DisplayName("TC-45: findByUsername() throws ResourceNotFoundException when not found")
    void findByUsername_ThrowsResourceNotFoundWhenMissing() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findByUsername("unknown"));
    }

    // TC-46
    @Test
    @DisplayName("TC-46: existsByUsername() returns true when username exists")
    void existsByUsername_ReturnsTrueWhenExists() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        assertTrue(userService.existsByUsername("testuser"));
    }

    // TC-47
    @Test
    @DisplayName("TC-47: existsByEmail() returns false when email does not exist")
    void existsByEmail_ReturnsFalseWhenNotExists() {
        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);

        assertFalse(userService.existsByEmail("new@example.com"));
    }
}
