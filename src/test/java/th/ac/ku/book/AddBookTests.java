package th.ac.ku.book;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddBookTests {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private static WebDriverWait wait;

    // able to reuse field element เพิ่มหนังสือหลายเล่มได้ โดยที่โค้ดสั้นลง
    @FindBy(id = "nameInput")
    private WebElement nameField;

    @FindBy(id = "authorInput")
    private WebElement authorField;

    @FindBy(id = "priceInput")
    private WebElement priceField;

    @FindBy(id = "submitButton")
    private WebElement submitButton;


    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 1000);
    }

    @BeforeEach
    public void beforeEach() {
        driver.get("http://localhost:" + port + "/book/add");
        PageFactory.initElements(driver, this); // @FindBy
    }

    @AfterEach
    public void afterEach() throws InterruptedException {
        Thread.sleep(3000);
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
    }




    @Test
    void testAddBook() {

        // find in /book/add ตาม beforeEach()
//        WebElement nameField = wait.until(webDriver -> webDriver.findElement(By.id("nameInput")));
//        WebElement authorField = driver.findElement(By.id("authorInput"));
//        WebElement priceField = driver.findElement(By.id("priceInput"));
//        WebElement submitButton = driver.findElement(By.id("submitButton"));

        nameField.sendKeys("The Cat in the Hat");
        authorField.sendKeys("Dr.Seuss");
        priceField.sendKeys("500");

        submitButton.click();

        // find in books.html (ได้หน้านี้หลังการกดปุ่ม summit)
        WebElement name = wait.until(webDriver -> webDriver.findElement(By.xpath("//table/tbody/tr[1]/td[1]")));
        WebElement author = driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]"));
        WebElement price = driver.findElement(By.xpath("//table/tbody/tr[1]/td[3]"));

        assertEquals("The Cat in the Hat", name.getText());
        assertEquals("Dr.Seuss", author.getText());
        assertEquals("500.00", price.getText());
    }

}


