package selenium;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.*;
import static org.junit.Assert.*;

import lrapi.lr;

public class PrevHR {
	protected WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() {
		driver = new ChromeDriver();
		driver.get("http://localhost/PrevSystem/");
	}

	@Test
	public void testLocal() {
		try {
			// Google's search is rendered dynamically with JavaScript.
			// Wait for the page to load, timeout after 10 seconds
			(new WebDriverWait(driver, 10)).until
				(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver d) {
						return d.getTitle().toLowerCase().startsWith("login page");
					}
				});

			lr.start_transaction("PrevHR_Test");
			System.out.println(driver.getTitle());
			// Should see: selenium testing tools
			//cookbook - Google Search
			assertEquals("Login Page", driver.getTitle());

			// Find the text input element by its name
			WebElement username = driver.findElement(By.name("username"));

			// Enter something to search for
			username.sendKeys ("john");
			
			// Find the text input element by its name
			WebElement password = driver.findElement(By.name("password"));

			// Enter something to search for
			password.sendKeys ("1");

			// Now submit the form. WebDriver will find the form for us from the element
			password.submit();
			
			// Wait for the page to load, timeout after 10 seconds
			(new WebDriverWait(driver, 10)).until
				(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver d) {
						return d.getTitle().toLowerCase().startsWith("employee main page");
					}
				});
			
			WebElement h3 = driver.findElement(By.tagName("h3"));
			System.out.println( "H3 "+h3.getText() );
			assertEquals("Employee Main page", h3.getText());
			
			// Find the text input element by its name
			WebElement lout = driver.findElement(By.name("logoutBtn"));

			// Now submit the form. WebDriver will find the form for us from the element
			lout.submit();

			// Wait for the page to load, timeout after 10 seconds
			(new WebDriverWait(driver, 10)).until
				(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver d) {
						return d.getTitle().toLowerCase().startsWith("login page");
					}
				});
			
			WebElement f = driver.findElement(By.name("lout"));
			System.out.println( "Font "+f.getText() );
			assertEquals("Logged out.", f.getText());
			
			lr.end_transaction("PrevHR_Test", lr.PASS);
			
		} catch (Error e) {
			//Capture and append Exceptions/Errors
			verificationErrors.append(e.toString());
		}
	}

	@After
	public void tearDown() throws Exception {
		// Close the browser
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
	
	public static void main(String[] args) {
		
	}
}