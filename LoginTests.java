/**
 * LoginTests.java
 * For CS1632 Deliverable 3
 * Casey Nispel Cln42
 * 
 * Tests the log in functionality of Craigslist.com
 **/

import static org.junit.Assert.*;
import java.util.List;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

/*
 * As a craigslist seller,
 * I want to log in
 * in order to view my account;
 */


public class LoginTests {

	WebDriver driver = new FirefoxDriver();
	
	@Before
	public void setup(){
		driver.get("https://accounts.craigslist.org/");
	}
	
	public static void login(WebDriver driver, String user, String pass) {
		WebElement userName = driver.findElement(By.id("inputEmailHandle"));
		userName.sendKeys(user);
		WebElement password = driver.findElement(By.id("inputPassword"));
		password.sendKeys(pass);
		WebElement submitButton = driver.findElement(By
				.cssSelector("button[type='submit']"));
		submitButton.click();
	}

	/*
	 * When I am logged out, log in with a valid handle and valid password
	 */
	@Test
	public void valid_login() {
		login(driver, "mythrowawayhandle2", "thisisapassword");
		assertEquals("craigslist account", driver.getTitle());
	}

	/*
	 * When I am logged out, log in with a valid handle and invalid password
	 */
	@Test
	public void invalid_password_login() {
		login(driver, "mythrowawayhandle2", "thisisthewrongpassword");

		List<WebElement> list = driver
				.findElements(By
						.xpath("//*[contains(text(),'Your email address, handle or password is incorrect. Please try again.')]"));
		assertTrue("Error Message Not Found!", list.size() > 0);
	}

	/*
	 * When I am logged out, log in with an invalid handle and invalid password
	 */
	@Test
	public void invalid_handle_password_login() {
		login(driver, "myinvalidhandle", "thisisthewrongpassword");

		List<WebElement> list = driver
				.findElements(By
						.xpath("//*[contains(text(),'Your email address, handle or password is incorrect. Please try again.')]"));
		assertTrue("Error Message Not Found!", list.size() > 0);
	}

	/*
	 * When I am logged in, log out
	 */
	@Test
	public void valid_logout() {
		login(driver, "mythrowawayhandle2", "thisisapassword");
		WebElement logout = driver.findElement(By.linkText("log out"));
		logout.click();
		assertEquals("craigslist: account log in", driver.getTitle());
	}

	/*
	 * Test forgot my password with invalid email
	 */
	@Test
	public void forgot_password() {
		WebElement forgot_pass = driver.findElement(By
				.linkText("Forgot password?"));
		forgot_pass.click();
		WebElement email = driver.findElement(By.name("emailAddressHandle"));
		email.sendKeys("myInvalidEmail@nothing");
		WebElement reset_button = driver.findElement(By
				.cssSelector("input[type='submit']"));
		reset_button.click();

		List<WebElement> list = driver
				.findElements(By
						.xpath("//*[contains(text(),'Your email address does not look correct. Please try again.')]"));
		assertTrue("Error Message Not Found!", list.size() > 0);
	}
	
	@After
	public void teardown(){
		driver.quit();
	}

}
