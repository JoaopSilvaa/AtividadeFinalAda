package br.ada.scheduleCreate;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ScheduleTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(
                new ChromeOptions().addArguments("--remote-allow-origins=*")
        );
    }

    @AfterAll
    public static void destroy() {
        driver.quit();
    }

    @Test
    @Order(1)
    public void naoCadastraUsuariosComMesmoUsername() {
        driver.get("http://localhost:8080/app/users");

        driver.findElement(By.className("create")).click();

        driver.findElement(By.id("name")).sendKeys("Joao");
        driver.findElement(By.id("username")).sendKeys("japs");
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElement(By.className("btn")).click();

        driver.findElement(By.className("create")).click();

        driver.findElement(By.id("name")).sendKeys("Joao");
        driver.findElement(By.id("username")).sendKeys("japs");
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElement(By.className("btn")).click();


        WebElement element = driver.findElement(
                By.className("user-form-error")
        );
        assertNotNull(element);
        assertEquals("Username already in use", element.getText());
    }

    @Test
    public void naoAlteraUsernameAposCadastro() {
        driver.get("http://localhost:8080/app/users");

        driver.findElement(By.className("edit")).click();

        WebElement element = driver.findElement(
                By.id("username")
        );

        assertEquals("true", element.getAttribute("readonly"));
    }

    @Test
    public void naoCadastraUsuarioSemNomeUsernameESenha() {
        driver.get("http://localhost:8080/app/users");

        driver.findElement(By.className("create")).click();
        driver.findElement(By.className("btn")).click();

        List<WebElement> elements = driver.findElements(
                By.className("user-form-error")
        );

        assertEquals(3, elements.size());
    }

    @Test
    public void listaTodosOsUsuarios() {
        driver.get("http://localhost:8080/app/users");

        List<WebElement> linhas = driver.findElements(
                By.tagName("tr")
        );

        assertEquals(2, linhas.size());

        driver.findElement(By.className("create")).click();

        driver.findElement(By.id("name")).sendKeys("Antonio");
        driver.findElement(By.id("username")).sendKeys("antony");
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElement(By.className("btn")).click();

        List<WebElement> newLinhas = driver.findElements(
                By.tagName("tr")
        );
        assertEquals(3, newLinhas.size());
    }

    @Test
    public void listaTodosOsUsuariosComDetalhesETemAcessoAPaginaDeEdicao() {
        driver.get("http://localhost:8080/app/users");

        List<WebElement> linhas = driver.findElements(
                By.tagName("tr")
        );

        assertEquals(3, linhas.get(1).findElements(By.tagName("td")).size());
        driver.findElement(By.className("edit")).click();
        assertEquals("http://localhost:8080/app/users/edit/1", driver.getCurrentUrl());
    }

    @Test
    @Order(2)
    public void senhaAntigaNaoCarregaNaEdicao() {
        driver.get("http://localhost:8080/app/users");

        driver.findElement(By.className("edit")).click();

        WebElement element = driver.findElement(
            By.id("password")
        );

        assertEquals("", element.getText());
    }

    @Test
    public void ePossivelTrocarNomeESenhaDoUsuario() {
        driver.get("http://localhost:8080/app/users");

        String name = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/table/tbody/tr[1]/td[1]")).getText();

        driver.findElement(By.className("edit")).click();

        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Joao Antonio");
        driver.findElement(By.id("username")).sendKeys("japs");
        driver.findElement(By.id("password")).sendKeys("1234567");
        driver.findElement(By.className("btn")).click();

        String newName = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/table/tbody/tr[1]/td[1]")).getText();

        assertNotEquals(name, newName);
    }
}
