import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;


public class testV1 {
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
    void shouldTestPostOfForm() {
        driver.get("http://localhost:9999");

        // сделаем выборку из трех элементов, определив их по тегу input
        List<WebElement> inputs = driver.findElements(By.tagName("input"));

        // отправим данные в текстовые поля формы
        inputs.get(0).sendKeys("Василий");
        inputs.get(1).sendKeys("+79998888888");

        // отправим данные в чекбокс
        driver.findElement(By.className("checkbox__box")).click();

        // жмём на кнопку "Отправить"
        driver.findElement(By.className("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText().trim();

        Assertions.assertEquals(expected, actual);
    }
}
