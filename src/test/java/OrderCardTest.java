import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderCardTest {

    public WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
        //System.setProperty("webdriver.chrome.driver", "driver/win/chromedriver.exe");
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver();

    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    public void shouldSendValidForm1() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Алексей Смирнов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79152854444");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldSendValidForm2() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("шапша как");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79152854444");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldSendValidFormWithHyphen() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Анна-Мария");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79152854444");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldSendFormWithoutName() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79152854444");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    public void shouldSendFormEnglishName() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Alex Smirnov");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79152854444");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }


    @Test
    public void shouldSendFormWithoutPhone() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Алексей Смирнов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    public void shouldSendWithoutPlusPhone() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Алексей Смирнов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("79152854444");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldSendFromWith8Phone() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Алексей Смирнов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("89152854444");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldSendLess11Digits() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Алексей Смирнов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7915285444");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldSendFormWithoutCheckBox() {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Алексей Смирнов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79152854444");
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }

}
