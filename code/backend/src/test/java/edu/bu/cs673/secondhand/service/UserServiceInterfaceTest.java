//package edu.bu.cs673.secondhand.service;
//
//import edu.bu.cs673.secondhand.domain.User;
//import edu.bu.cs673.secondhand.mapper.UserMapper;
//import edu.bu.cs673.secondhand.model.UserModel;
//import edu.bu.cs673.secondhand.service.impl.UserServiceImpl;
//import edu.bu.cs673.secondhand.utils.SecurityUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
///**
// * UserServiceInterfaceTest tests the UserService class for user-related operations.
// * Author: YQ
// */
//class UserServiceInterfaceTest {
//
//    @Mock
//    private UserMapper userMapper;  // Mocking the UserMapper
//    @Mock
//    private SecurityUtil securityUtil;  // Mocking the SecurityUtil
//    @Mock
//    private EmailService emailServiceInterface;  // Mocking the EmailServiceInterface
//
//    private UserServiceImpl userService;  // Instance of UserService to be tested
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);  // Initialize mocks
//        userService = new UserServiceImpl(userMapper, securityUtil, emailServiceInterface);  // Create UserService instance
//    }
//
//    /**
//     * Test registering a user with a valid BU email.
//     */
//    @Test
//    void registerUser_withValidBUEmail_shouldSucceed() {
//        // Arrange
//        UserModel userModel = new UserModel();
//        userModel.setEmail("test@bu.edu");
//        userModel.setUserPassword("password123");
//        userModel.setNickname("TestUser");
//
//        when(userMapper.findByEmail(anyString())).thenReturn(null);  // Mocking user not found
//        when(securityUtil.encodePassword(anyString())).thenReturn("encodedPassword");  // Mocking password encoding
//        when(userMapper.insertSelective(any())).thenReturn(1);  // Mocking successful insertion
//
//        // Act
//        boolean result = userService.registerUser(userModel);  // Attempt to register user
//
//        // Assert
//        assertTrue(result);  // Check that registration was successful
//        verify(userMapper).findByEmail("test@bu.edu");  // Verify email lookup
//        verify(securityUtil).encodePassword("password123");  // Verify password encoding
//        verify(userMapper).insertSelective(any());  // Verify user insertion
//        verify(emailServiceInterface).sendActivationEmail(eq("test@bu.edu"), anyString());  // Verify activation email sent
//    }
//
//    /**
//     * Test registering a user with a non-BU email.
//     */
//    @Test
//    void registerUser_withNonBUEmail_shouldFail() {
//        // Arrange
//        UserModel userModel = new UserModel();
//        userModel.setEmail("test@gmail.com");  // Non-BU email
//
//        // Act
//        boolean result = userService.registerUser(userModel);  // Attempt to register user
//
//        // Assert
//        assertFalse(result);  // Check that registration failed
//        verify(userMapper, never()).findByEmail(anyString());  // Verify no email lookup
//        verify(userMapper, never()).insertSelective(any());  // Verify no user insertion
//        verify(emailServiceInterface, never()).sendActivationEmail(anyString(), anyString());  // Verify no activation email sent
//    }
//
//    /**
//     * Test registering a user with an existing email.
//     */
//    @Test
//    void registerUser_withExistingEmail_shouldThrowException() {
//        // Arrange
//        UserModel userModel = new UserModel();
//        userModel.setEmail("existing@bu.edu");
//        when(userMapper.findByEmail("existing@bu.edu")).thenReturn(new User());  // Mocking existing user
//
//        // Act & Assert
//        assertThrows(RuntimeException.class, () -> userService.registerUser(userModel));  // Expect exception
//        verify(userMapper).findByEmail("existing@bu.edu");  // Verify email lookup
//        verify(userMapper, never()).insertSelective(any());  // Verify no user insertion
//        verify(emailServiceInterface, never()).sendActivationEmail(anyString(), anyString());  // Verify no activation email sent
//    }
//}