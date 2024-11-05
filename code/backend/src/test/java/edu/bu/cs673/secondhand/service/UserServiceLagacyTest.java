package edu.bu.cs673.secondhand.service;

import edu.bu.cs673.secondhand.domain.User;
import edu.bu.cs673.secondhand.service.UserServiceLegacy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceLegacyImplTest {

    @Resource
    private UserServiceLegacy userService;

    @BeforeEach
    void setUp() {
        System.out.println("Setting up UserServiceLegacy tests.");
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void createUser() {
        User user = new User();
        user.setNickname("anglee1993");
        user.setEmail("la1994@bu.edu");
        user.setUserPassword("123456");
        boolean success = userService.userSignIn(user);
        assertTrue(success);
        assertNotNull(user.getId());
    }

//    @Test
//    @org.junit.jupiter.api.Order(2)
//    void login() {
//        User user = new User();
//        user.setEmail("la1993@bu.edu");
//        user.setUserPassword("123456");
//
//        userService.userLogin(user.getEmail(),user.getUserPassword());
//        Long userId = user.getId();
//        User fetchedUser = userService.getUserById(userId);
//
//        assertNotNull(fetchedUser);
//        assertEquals(userId, fetchedUser.getId());
//        assertEquals("testuser2", fetchedUser.getUsername());
//    }
}
