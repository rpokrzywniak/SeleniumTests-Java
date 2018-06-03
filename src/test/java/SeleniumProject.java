import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumProject {

	// private ChromeDriver driver;
	private FirefoxDriver driver;
	// private OperaDriver driver;
	// private EdgeDriver driver;

	// private PhantomJSDriver driver;
	// private HtmlUnitDriver driver;
	private FluentWait<WebDriver> wait;

	@SuppressWarnings("deprecation")
	public SeleniumProject(FirefoxDriver driver) {
		this.driver=driver;
		this.wait = new FluentWait<WebDriver>(driver).withTimeout(10, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
	}

	public Boolean login() {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Login")));
		driver.findElement(By.linkText("Login")).click();
		wait.until(ExpectedConditions.titleIs("CRUDigniter - The automatic code generator for Codeigniter"));
		driver.findElement(By.name("email")).sendKeys("robert.pokrzywniak@gmail.com");
		driver.findElement(By.name("password")).sendKeys("qazwsx");
		driver.findElement(By.cssSelector(".btn.btn-success")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("header-links")));
		return isElementPresent(By.className("header-links"));
	}

	public Boolean crudAdd(String columnName, String columnType) {
		setUp();
		String check = columnName + " | " + columnType;

		driver.findElement(By.linkText("Add Column")).click();
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.cssSelector("input[class='form-control new-column-name input-sm']")));
		sleep(1000);
		driver.findElement(By.cssSelector("input[class='form-control new-column-name input-sm']")).sendKeys(columnName);
		Select dropdown = new Select(driver.findElement(By.cssSelector(".form-control.new-column-type.input-sm")));
		dropdown.selectByVisibleText(columnType.toUpperCase());
		driver.findElement(By.cssSelector(".btn.btn-primary.create-column-save")).click();
		sleep(1000);
		return isElementPresent(By.linkText(check));
	}

	public Boolean crudEdit(String columnToEdit, String editedColumnName, String editedColumnType) {
		setUp();
		String editedColumn = editedColumnName + " | " + editedColumnType;

		driver.findElement(By.linkText(columnToEdit)).click();
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.cssSelector("input[class='form-control edit-column-name input-sm']")));
		sleep(1000);
		driver.findElement(By.cssSelector("input[class='form-control edit-column-name input-sm']")).clear();
		driver.findElement(By.cssSelector("input[class='form-control edit-column-name input-sm']"))
				.sendKeys(editedColumnName);
		Select dropdown = new Select(driver.findElement(By.cssSelector(".form-control.edit-column-type.input-sm")));
		dropdown.selectByVisibleText(editedColumnType.toUpperCase());
		driver.findElement(By.id("edit-is-null")).click();
		driver.findElement(By.cssSelector(".btn.btn-primary.update-column-save")).click();
		sleep(1000);
		return isElementPresent(By.linkText(editedColumn));
	}

	public Boolean crudDelete(String columnName, String columnType) {
		setUp();
		String columnToDelete = columnName + " | " + columnType;

		WebElement list = driver.findElement(By.className("table-fields"));
		List<WebElement> elements = list.findElements(By.tagName("a"));
		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i).getText().equals(columnToDelete)) {
				elements.get(i - 1).click();
				break;
			}
		}
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".btn.btn-blue")));
		driver.findElement(By.cssSelector(".btn.btn-blue")).click();
		sleep(1000);
		return isElementPresent(By.linkText(columnToDelete));
	}

	public int form() {
		setUp();

		WebElement menu = driver.findElement(By.id("tab-container"));
		List<WebElement> elements = menu.findElements(By.tagName("a"));
		for (WebElement a : elements) {
			if (a.getAttribute("href").equals("http://www.crudigniter.com/table/options?db_id=35742")) {
				a.click();
				break;
			}
		}
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("database-table")));
		Select dropdown = new Select(driver.findElement(By.id("database-table")));
		dropdown.selectByVisibleText("People");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".columns-list.table")));
		WebElement list = driver.findElement(By.cssSelector(".columns-list.table"));
		elements = list.findElements(By.tagName("a"));
		for (WebElement a : elements) {
			if (a.getText().equals("Add Rule")) {
				a.click();
				wait.until(ExpectedConditions
						.presenceOfElementLocated(By.cssSelector(".ci-rule-select-dropdown.form-control.input-sm")));
				dropdown = new Select(
						driver.findElement(By.cssSelector(".ci-rule-select-dropdown.form-control.input-sm")));
				dropdown.selectByVisibleText("max_length");
				wait.until(ExpectedConditions
						.presenceOfElementLocated(By.cssSelector(".parameter_value.form-control.input-sm")));
				driver.findElement(By.cssSelector(".parameter_value.form-control.input-sm")).sendKeys("20");
				driver.findElement(By.cssSelector(".btn.btn-success.save-custom-rule")).click();
				JavascriptExecutor js = ((JavascriptExecutor) driver);
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				sleep(1000);
				a.click();
				wait.until(ExpectedConditions
						.presenceOfElementLocated(By.cssSelector(".ci-rule-select-dropdown.form-control.input-sm")));
				dropdown = new Select(
						driver.findElement(By.cssSelector(".ci-rule-select-dropdown.form-control.input-sm")));
				dropdown.selectByVisibleText("min_length");
				wait.until(ExpectedConditions
						.presenceOfElementLocated(By.cssSelector(".parameter_value.form-control.input-sm")));
				driver.findElement(By.cssSelector(".parameter_value.form-control.input-sm")).sendKeys("3");
				driver.findElement(By.cssSelector(".btn.btn-success.save-custom-rule")).click();
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				sleep(1000);
				a.click();
				wait.until(ExpectedConditions
						.presenceOfElementLocated(By.cssSelector(".ci-rule-select-dropdown.form-control.input-sm")));
				dropdown = new Select(
						driver.findElement(By.cssSelector(".ci-rule-select-dropdown.form-control.input-sm")));
				dropdown.selectByVisibleText("required");
				driver.findElement(By.cssSelector(".btn.btn-success.save-custom-rule")).click();
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				sleep(1000);
			}
		}
		return list.findElements(By.tagName("img")).size();
	}

	public Boolean icon(String iconToSet) {
		setUp();
		String iconClass = ".fa.fa-" + iconToSet;

		WebElement menu = driver.findElement(By.id("tab-container"));
		List<WebElement> elements = menu.findElements(By.tagName("a"));
		for (WebElement a : elements) {
			if (a.getAttribute("href").equals("http://www.crudigniter.com/table/options?db_id=35742")) {
				a.click();
				break;
			}
		}
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("database-table")));
		Select dropdown = new Select(driver.findElement(By.id("database-table")));
		dropdown.selectByVisibleText("People");
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.cssSelector(".icp.entity_icon_btn.dropdown-toggle.iconpicker-component.iconpicker-element")));
		driver.findElement(
				By.cssSelector(".icp.entity_icon_btn.dropdown-toggle.iconpicker-component.iconpicker-element")).click();
		driver.findElement(By.cssSelector(iconClass)).click();
		WebDriverWait wait = new WebDriverWait(driver, 2);
		return isElementPresent(By.cssSelector(".notifyjs-bootstrap-base.notifyjs-bootstrap-success"));
	}

	private void setUp() {
		driver.findElement(By.cssSelector(".btn.btn-xs.btn-success")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("table-header")));
	}

	private Boolean isElementPresent(By by) {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}