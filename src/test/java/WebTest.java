import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebTest {

    private static WebDriver testUrl;

    static {
        testUrl = new ChromeDriver();
        testUrl.get("https://formy-project.herokuapp.com");
    }

    public static void main(String[] args) {
        try{
            testAutocomplete();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            testUrl.quit();
        }
    }

    public static void testAutocomplete() throws InterruptedException {
        WebElement autocompleteLink = testUrl.findElement(By.linkText("Autocomplete"));
        autocompleteLink.click();

        // Массивы с данными
        String[] addresses = {"123 Main St", "456 Elm St", "789 Oak St"};
        String[] streetAddresses = {"Main Street", "Elm Street", "Oak Street"};
        String[] streetAddresses2 = {"Building A", "Tower B", "Unit C"};
        String[] cities = {"New York", "Los Angeles", "Chicago"};
        String[] states = {"NY", "CA", "IL"};
        String[] zipCodes = {"10001", "90001", "60601"};
        String[] countries = {"USA", "USA", "USA"};

        // Явное ожидание загрузки страницы
        WebDriverWait wait = new WebDriverWait(testUrl, java.time.Duration.ofSeconds(4));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("autocomplete")));

        // Заполнение полей данными из массивов
        fillField(By.id("autocomplete"), addresses[0]);
        fillField(By.id("street_number"), streetAddresses[0]);
        fillField(By.id("route"), streetAddresses[0]);
        fillField(By.id("locality"), streetAddresses2[0]);
        fillField(By.id("administrative_area_level_1"), cities[0]);
        fillField(By.id("postal_code"), zipCodes[0]);
        fillField(By.id("country"), countries[0]);

        Thread.sleep(3000);
        testUrl.navigate().back();
    }

    // Метод для заполнения поля текстом
    private static void fillField(By locate, String value){
        WebElement field = testUrl.findElement(locate);
        field.clear(); // Очистка поля перед вводом
        field.sendKeys(value);
    }
}
