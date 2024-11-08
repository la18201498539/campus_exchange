package edu.bu.cs673.secondhand.service.impl;

import edu.bu.cs673.secondhand.domain.Admin;
import edu.bu.cs673.secondhand.vo.PageVo;
import edu.bu.cs673.secondhand.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.annotation.Resource;

/***
 Email: qyyh@bu.edu
 DateTime: 11/06/24-16:39
 *****/

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminServiceImplTest {

    @Resource
    AdminService adminService;

    @BeforeEach
    void setUp() {
        System.out.println("Setup Admin Service By Spring Boot Annotation [Resource]!");
    }

    @Test
    @Order(1)
    void addAdmin() {
        Admin admin = new Admin();
        admin.setAccountNumber("ADMIN_TEST_003");
        admin.setAdminPassword("test123");
        admin.setAdminName("Test Admin");

        boolean success = adminService.addAdmin(admin);
        
        assertTrue(success);
        assertNotNull(admin.getAccountNumber());
        assertNotNull(admin.getAdminPassword());
        assertNotNull(admin.getAdminName());
    }

    @Test
    @Order(2)
    void login() {
        Admin admin = adminService.login("ADMIN_TEST_001", "test123");
        
        assertNotNull(admin);
        assertNotNull(admin.getId());
        assertEquals("ADMIN_TEST_001", admin.getAccountNumber());
        assertEquals("Test Admin", admin.getAdminName());
    }

    @Test
    @Order(3)
    void getAdminList() {
        PageVo<Admin> result = adminService.getAdminList(1, 10);

        assertNotNull(result);
        assertNotNull(result.getList());
        assertTrue(result.getList().size() > 0);
        assertTrue(result.getList().size() <= 10);
        
        // Verify the test admin exists in the list
        boolean found = result.getList().stream()
                .anyMatch(admin -> "ADMIN_TEST_001".equals(admin.getAccountNumber()));
        assertTrue(found, "Test admin should be in the list");
    }
}
