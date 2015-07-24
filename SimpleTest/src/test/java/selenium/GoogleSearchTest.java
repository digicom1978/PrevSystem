package selenium;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.*;
import static org.junit.Assert.*;

public class GoogleSearchTest {
	protected WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() {
		driver = new ChromeDriver();
		driver.get("http://www.google.com");
	}

	@Test
	public void testGoogleSearch() {
		try {
			// Find the text input element by its name
			WebElement element = driver.findElement(By.name("q"));

			// Enter something to search for
			element.sendKeys ("Selenium testing tools cookbook");

			// Now submit the form. WebDriver will find
			// the form for us from the element
			element.submit();

			// Google's search is rendered dynamically
			//with JavaScript.
			// Wait for the page to load, timeout after
			//10 seconds
			(new WebDriverWait(driver, 10)).until
				(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver d) {
						return d.getTitle().toLowerCase().startsWith("selenium testing tools cookbook");
					}
				});

			System.out.println(driver.getTitle());
			// Should see: selenium testing tools
			//cookbook - Google Search
			assertEquals("Selenium testing tools cookbook - Google Search", driver.getTitle());
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
}