package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new FirefoxDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doMockSignUp(String firstName, String lastName, String userName, String password) {
        // Create a dummy account for logging in later.

        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill out credentials
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys(lastName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys(password);

        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
    }


    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doLogIn(String userName, String password) {
        // Log in to our dummy account.
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling redirecting users
     * back to the login page after a succesful sign up.
     * Read more about the requirement in the rubric:
     * https://review.udacity.com/#!/rubrics/2724/view
     */
    @Test
    public void testRedirection() {
        // Create a test account
        doMockSignUp("Redirection", "Test", "RT", "123");

        // Check if we have been redirected to the log in page.
        Assertions.assertEquals("http://localhost:" + this.port + "/login?signupSuccess", driver.getCurrentUrl());
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling bad URLs
     * gracefully, for example with a custom error page.
     * <p>
     * Read more about custom error pages at:
     * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
     */
    @Test
    public void testBadUrl() {
        // Create a test account
        doMockSignUp("URL", "Test", "UT", "123");
        doLogIn("UT", "123");

        // Try to access a random made-up URL.
        driver.get("http://localhost:" + this.port + "/some-random-page");
        Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
    }


    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling uploading large files (>1MB),
     * gracefully in your code.
     * <p>
     * Read more about file size limits here:
     * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
     */
    @Test
    public void testLargeUpload() {
        // Create a test account
        doMockSignUp("Large File", "Test", "LFT", "123");
        doLogIn("LFT", "123");

        // Try to upload an arbitrary large file
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        String fileName = "upload5m.zip";

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
        WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
        fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

        WebElement uploadButton = driver.findElement(By.id("uploadButton"));
        uploadButton.click();
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Large File upload failed");
        }
        Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

    }

    @Test
    public void testRedirectLoginPage() {
        driver.get("http://localhost:" + this.port + "/random-page");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    @Order(1)
    public void testSignUp() {
        doMockSignUp("Large File", "Test", "LFT", "123");
        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
    }

    @Test
    @Order(2)
    public void testFailedSignUp() {
        doMockSignUp("Large File", "Test", "LFT", "123");
        Assertions.assertTrue(driver.findElement(By.id("failed-msg")).getText().contains("Username is already exists"));
    }

    @Test
    public void testCreateNote() throws InterruptedException {
//        doMockSignUp("Large File", "Test", "LFT", "123");
        doLogIn("LFT", "123");
        Thread.sleep(1000);
        // Try to upload an arbitrary large file
        // Switch to notes tab
        driver.findElement(By.id("nav-notes-tab")).click();
        Thread.sleep(1000);

        // Create a new note
        boolean noteCreated = false;
        try {
            driver.findElement(By.id("new-note")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("note-title")).sendKeys("Note");
            driver.findElement(By.id("note-description")).sendKeys("Note Desc");
            driver.findElement(By.id("note-submit")).click();
            Thread.sleep(2000);
            noteCreated = true;
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        Assertions.assertTrue(noteCreated);
    }

    @Test
    public void testDeleteNote() throws InterruptedException {
//        doMockSignUp("Large File", "Test", "LFT", "123");
        doLogIn("LFT", "123");

        // Try to upload an arbitrary large file
        // Switch to notes tab
        driver.findElement(By.id("nav-notes-tab")).click();
        Thread.sleep(1000);

        // Create a new note
        boolean noteCreated = false;
        boolean noteDeleted = false;

        try {
            driver.findElement(By.id("new-note")).click();
            Thread.sleep(1000);
            driver.findElement(By.id("note-title")).sendKeys("Note 1");
            driver.findElement(By.id("note-description")).sendKeys("Note 1 Desc");
            driver.findElement(By.id("note-submit")).click();
            Thread.sleep(1000);
            noteCreated = true;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        // Delete a note
        Thread.sleep(2000);
        WebElement notesTable = driver.findElement(By.id("userTable"));
        List<WebElement> noteLink = notesTable.findElements(By.tagName("a"));
        for (WebElement deleteNoteButton : noteLink) {
            deleteNoteButton.click();
            noteDeleted = true;
            break;
        }

        Assertions.assertTrue(noteCreated);
        Assertions.assertTrue(noteDeleted);
    }

    @Test
    public void testEditNote() throws InterruptedException {
//        doMockSignUp("Large File", "Test", "LFT", "123");
        doLogIn("LFT", "123");

        // Try to upload an arbitrary large file
        // Switch to notes tab
        driver.findElement(By.id("nav-notes-tab")).click();
        Thread.sleep(1000);

        // Create a new note
        boolean noteCreated = false;
        boolean noteEdited = false;

        try {
            driver.findElement(By.id("new-note")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("note-title")).sendKeys(" (edited)");
            driver.findElement(By.id("note-description")).sendKeys(" (edited)");
            driver.findElement(By.id("note-submit")).click();
            Thread.sleep(2000);
            noteCreated = true;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        // Edit a note
        Thread.sleep(1000);
        WebElement notesTable = driver.findElement(By.id("userTable"));
        notesTable = driver.findElement(By.id("userTable"));
        List<WebElement> noteList = notesTable.findElements(By.tagName("td"));

        for (WebElement row : noteList) {
            WebElement editButton;
            editButton = row.findElement(By.tagName("button"));
            editButton.click();
            if (!ObjectUtils.isEmpty(editButton)) {
                Thread.sleep(2000);
                driver.findElement(By.id("note-title")).sendKeys("Edited Note");
                driver.findElement(By.id("note-description")).sendKeys("edit desc");
                driver.findElement(By.id("note-submit")).click();
                Thread.sleep(2000);
                noteEdited = true;
                Assertions.assertEquals("Home", driver.getTitle());
                break;
            }
        }

        Assertions.assertTrue(noteCreated);
        Assertions.assertTrue(noteEdited);
    }

    @Test
    public void testCredentials() throws InterruptedException {
//        doMockSignUp("Large File", "Test", "LFT", "123");
        doLogIn("LFT", "123");

        driver.findElement(By.id("nav-credentials-tab")).click();
        Thread.sleep(500);


        boolean credentialCreated = false;
        boolean credentialEdited = false;
        boolean credentialDeleted = false;
        boolean checkDecodedPassword = false;

        // Create a new credential
        try {
            driver.findElement(By.id("new-credential")).click();

            Thread.sleep(500);

            driver.findElement(By.id("credential-url")).sendKeys("https://www.google.com");
            driver.findElement(By.id("credential-username")).sendKeys("htruong@gmail.com");
            driver.findElement(By.id("credential-password")).sendKeys("plain-password");
            driver.findElement(By.id("credential-submit")).click();

            credentialCreated = true;

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        Thread.sleep(1000);

        // Edit a credential
        WebElement notesTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> noteList = notesTable.findElements(By.tagName("td"));

        for (WebElement row : noteList) {
            WebElement editButton = null;
            editButton = row.findElement(By.tagName("button"));
            editButton.click();
            if (!ObjectUtils.isEmpty(editButton)) {
                Thread.sleep(1000);
                checkDecodedPassword = driver.findElement(By.id("credential-password")).getAttribute("value").equals("plain-password");
                try {
                    driver.findElement(By.id("credential-url")).clear();
                    driver.findElement(By.id("credential-url")).sendKeys("https://www.youtube.com");
                    driver.findElement(By.id("credential-username")).clear();
                    driver.findElement(By.id("credential-username")).sendKeys("utube");
                    driver.findElement(By.id("credential-password")).clear();
                    driver.findElement(By.id("credential-password")).sendKeys("plain-text");
                    driver.findElement(By.id("credential-submit")).click();
                    credentialEdited = true;
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                Thread.sleep(2000);

                Assertions.assertEquals("Home", driver.getTitle());
                break;
            }
        }
        Thread.sleep(1000);

        // Delete a credential
        notesTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> noteLink = notesTable.findElements(By.tagName("a"));
        for (WebElement deleteNoteButton : noteLink) {
            deleteNoteButton.click();
            credentialDeleted = true;
            break;
        }

        Assertions.assertTrue(credentialCreated);
        Assertions.assertTrue(checkDecodedPassword);
        Assertions.assertTrue(credentialEdited);
        Assertions.assertTrue(credentialDeleted);
    }
}
