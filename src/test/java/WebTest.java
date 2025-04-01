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
            //testAutocomplete();
            testButtons();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            testUrl.quit();
        }
    }


    // Тесты страниц

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

        // Явное ожидание загрузки страницы(вернее ожидания пока
        // не будет найден элемент, по конкретному параметру)
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
        // Возвращение к предыдущей страницы
        testUrl.navigate().back();
    }

    public static void testButtons() throws InterruptedException {
        // Переход на страницу "Buttons"
        WebElement buttonsLink = testUrl.findElement(By.linkText("Buttons"));
        buttonsLink.click();

        WebDriverWait wait = new WebDriverWait(testUrl, java.time.Duration.ofSeconds(5));

        // Первая группа кнопок
        elementClick(By.className("btn-primary"));
        elementClick(By.className("btn-success"));
        elementClick(By.className("btn-info"));
        elementClick(By.className("btn-warning"));
        elementClick(By.className("btn-danger"));
        elementClick(By.className("btn-link"));

        // Вторая группа кнопок
        elementClick(By.xpath("//button[text()='Left']"));
        elementClick(By.xpath("//button[text()='Middle']"));
        elementClick(By.xpath("//button[text()='Right']"));

        // Третья группа кнопок
        elementClick(By.xpath("//button[text()='1']"));
        elementClick(By.xpath("//button[text()='2']"));
        elementClick(By.id("btnGroupDrop1"));

        // Пока оно не дружелюбно
        //elementClick(By.cssSelector("a.dropdown-item"));

        Thread.sleep(3000); // Задержка перед возвратом
        testUrl.navigate().back(); // Возвращаемся на предыдущую страницу
    }

    // Функциональные методы

    // Метод для заполнения поля текстом
    private static void fillField(By locate, String value){
        WebElement field = testUrl.findElement(locate);
        field.clear(); // Очистка поля перед вводом
        field.sendKeys(value);
    }


    // Метод для нажатия на элемент
    private static void elementClick(By locate) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(testUrl, java.time.Duration.ofSeconds(3));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locate));
        element.click();
        Thread.sleep(200);
    }
}
