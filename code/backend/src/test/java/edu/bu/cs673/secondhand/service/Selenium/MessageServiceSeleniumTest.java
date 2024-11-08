package edu.bu.cs673.secondhand.service.Selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageServiceSeleniumTest {
    private WebDriver driver;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        // Set the ChromeDriver version for Chrome 130
        WebDriverManager.chromedriver().driverVersion("130.0.6723.92").setup();

        // Initialize the WebDriver
        driver = new ChromeDriver();
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testGetMessageInfo() throws InterruptedException {
        // Access the message info page (e.g., ID 66)
        driver.get("http://localhost:" + port + "/message/info?id=66");

        // Wait for the JSON response to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement resultContent;
        try {
            resultContent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("pre")));
        } catch (TimeoutException e) {
            System.out.println("Timeout occurred while waiting for the JSON content.");
            System.out.println(driver.getPageSource()); // Print the page source for debugging
            throw e; // Re-throw the exception if necessary
        }

        // Parse the JSON content
        String jsonText = resultContent.getText();
        System.out.println("JSON Response: " + jsonText); // Optional: Print the JSON response for verification

        // Assuming you can parse the JSON to get the content
        String expectedContent = "I like the gray writing desk, how much is it?";
        assertTrue(jsonText.contains(expectedContent), "Expected content not found in JSON response.");

        // Add a delay for observation
        Thread.sleep(5000); // Wait for 5 seconds
    }

    @Test
    public void testGetAllIdleMessage() throws InterruptedException {
        // Access the idle message endpoint with a specific idleId (e.g., 189)
        long idleId = 189; // The idleId you want to test
        driver.get("http://localhost:" + port + "/message/idle?idleId=" + idleId);

        // Wait for the JSON response to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement resultContent;
        try {
            resultContent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("pre")));
        } catch (TimeoutException e) {
            System.out.println("Timeout occurred while waiting for the JSON content.");
            System.out.println(driver.getPageSource()); // Print the page source for debugging
            throw e; // Re-throw the exception if necessary
        }

        // Parse the JSON content
        String jsonText = resultContent.getText();
        System.out.println("JSON Response: " + jsonText); // Optional: Print the JSON response for verification

        // Verify the response contains expected data
        String[] expectedMessages = {
                "Meet me at Building No. 1.",
                "come on",
                "come"
        };

        for (String expectedMessage : expectedMessages) {
            assertTrue(jsonText.contains(expectedMessage), "Expected message not found in JSON response: " + expectedMessage);
        }

        // Add a delay for observation
        Thread.sleep(5000); // Wait for 5 seconds
    }

    @Test
    public void testGetAllMyMessage() throws InterruptedException {
        // Access my message endpoint
        driver.get("http://localhost:" + port + "/message/my");

        // Set the cookie for shUserId to simulate a logged-in user
        driver.manage().addCookie(new org.openqa.selenium.Cookie("shUserId", "70")); // Use the appropriate user ID

        // Refresh the page after setting the cookie
        driver.navigate().refresh();

        // Wait for the JSON response to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement resultContent;
        try {
            resultContent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("pre")));
        } catch (TimeoutException e) {
            System.out.println("Timeout occurred while waiting for the JSON content.");
            System.out.println(driver.getPageSource()); // Print the page source for debugging
            throw e; // Re-throw the exception if necessary
        }

        // Parse the JSON content
        String jsonText = resultContent.getText();
        System.out.println("JSON Response: " + jsonText); // Optional: Print the JSON response for verification

        // Verify the response contains expected data
        String[] expectedMessages = {
                "nice item",
                "This is a great item! Can you reduce the price?"
        };

        for (String expectedMessage : expectedMessages) {
            assertTrue(jsonText.contains(expectedMessage), "Expected message not found in JSON response: " + expectedMessage);
        }

        // Add a delay for observation
        Thread.sleep(5000); // Wait for 5 seconds
    }

}
