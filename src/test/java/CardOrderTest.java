import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void shouldFindErrorMessageWhenAllFieldsAreEmpty() {
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().
                trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldFindErrorMessageWhenNameFieldIsEmpty() {
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998888888");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().
                trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldFindErrorMessageWhenNumberFieldIsEmpty() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий Первый-Второй");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().
                trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldFindErrorMessageWhenNoAgreement() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий Первый-Второй");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998888888");
        driver.findElement(By.className("button")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю " +
                "сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldFindErrorMessageWhenNameIsInEnglish() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("example");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().
                trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldFindErrorMessageWhenNameIsInEnglishByInputInvalid() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("example");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().
                trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldFindErrorMessageWhenPhoneNumberIsUnderElevenFigures() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий Первый-Второй");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7123456789");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().
                trim();
        Assertions.assertEquals(expected, actual);
    }
}
