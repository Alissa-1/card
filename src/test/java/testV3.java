import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class testV3 {
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
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void shouldFindErrorMassageWhenAllFieldsAreEmpty() {
        driver.get("http://localhost:9999");

        // checkbox
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();

        // жмём на кнопку "Отправить"
        driver.findElement(By.className("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldFindErrorMassageWhenNameFieldIsEmpty() {
        driver.get("http://localhost:9999");

        // inputs
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998888888");

        // checkbox
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();

        // жмём на кнопку "Отправить"
        driver.findElement(By.className("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldFindErrorMassageWhenNumberFieldIsEmpty() {
        driver.get("http://localhost:9999");

        // inputs
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");

        // checkbox
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();

        // жмём на кнопку "Отправить"
        driver.findElement(By.className("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldFindErrorMassageWhenNameIsInEnglish() {
        driver.get("http://localhost:9999");

        // inputs
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("example");

        // checkbox
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();

        // жмём на кнопку "Отправить"
        driver.findElement(By.className("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldFindErrorMassageWhenNameIsInEnglishByInputInvalid() {
        driver.get("http://localhost:9999");

        // inputs
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("example");

        // checkbox
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();

        // жмём на кнопку "Отправить"
        driver.findElement(By.className("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldFindErrorMassageWhenPhoneNumberIsUnderElevenFigures() {
        driver.get("http://localhost:9999");

        // inputs
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7123456789");

        // checkbox
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();

        // жмём на кнопку "Отправить"
        driver.findElement(By.className("button")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }
}