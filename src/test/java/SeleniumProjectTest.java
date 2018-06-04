import static org.junit.Assert.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

import java.util.concurrent.TimeUnit;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import io.github.bonigarcia.SeleniumExtension;

@ExtendWith(SeleniumExtension.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SeleniumProjectTest {

	/*
	 * Metoda FluentWait() jest bardzo podobna do ocekiwania jawnego. Sprawdza
	 * co ustalony interwal czasu, czy dany element się pojawił. Różnica z
	 * oczekiwaniem jawnym polega na ignorowaniu wyjątków.
	 */

	// private ChromeDriver driver;
	//private FirefoxDriver driver;
	// private OperaDriver driver;
	// private EdgeDriver driver;

	// private PhantomJSDriver driver;
	// private HtmlUnitDriver driver;
	private FluentWait<WebDriver> wait;
	public SeleniumProject page;

	public SeleniumProjectTest(FirefoxDriver firefox) {
		String system = System.getProperty("os.name").toLowerCase();
		//if(system.contains("windows"));
		//else if(system.contains("linux"));
		//else driver = firefox;
		firefox.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		firefox.get("http://www.crudigniter.com/");

		page = new SeleniumProject(firefox);
	}

	@BeforeEach
	public void login() {
		page.login();
	}


	// CRUD
	// Adding
	@Test
	public void b() {
		String columnName = "Test";
		String columnType = "varchar(255)";

		assertThat(!page.crudAdd(columnName, columnType), equalTo(false));
	}

	// Edit
	@Test
	public void c() {
		String columnName = "Test";
		String columnType = "varchar(255)";
		String columnToEdit = columnName + " | " + columnType;
		String editedColumnName = "Edit";
		String editedColumnType = "text";

		assertTrue(page.crudEdit(columnToEdit, editedColumnName, editedColumnType));
	}

	// Delete
	@Test
	public void d() {
		String columnName = "Edit";
		String columnType = "text";

		assertFalse(page.crudDelete(columnName, columnType));
	}

	// Form
	//@Test
	//public void e() {
	//	int numberOfFields = 4;
	//	int numberOfAddedRules = page.form();
	//	
	//	assertEquals(numberOfAddedRules, numberOfFields * 3);
	//}

	@Test
	public void f() {
		String iconToSet = "heart";
		
		assertThat(page.icon(iconToSet),is(true));
	}
}
