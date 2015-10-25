/**
 * DraftTests.java
 * For CS1632 Deliverable 3
 * Casey Nispel Cln42
 * 
 * Tests the functionality to create draft posts
 * on Craigslist.com
 **/

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DraftTests {

	/*
	 * to set up code
	 * logs user in
	 */
	public static void login(WebDriver driver){
		driver.get("https://accounts.craigslist.org/");
		WebElement userName = driver.findElement(By.id("inputEmailHandle"));
		userName.sendKeys("mythrowawayhandle2");
		WebElement password = driver.findElement(By.id("inputPassword"));
		password.sendKeys("thisisapassword");
		WebElement submitButton = driver.findElement(By
				.cssSelector("button[type='submit']"));
		submitButton.click();
	}
	/*
	 * log in, create a post
	 * choose for sale by owner
	 * choose general for sale - by owner
	 */
	public static void set_up_code(WebDriver driver){
		login(driver);
		WebElement go = driver.findElement(By.cssSelector("form.new_posting_thing > input[type='submit']"));
		go.click();
		WebElement post_type = driver.findElement(By.xpath("(//input[@name='id'])[6]"));
		post_type.click();
		post_type = driver.findElement(By.xpath("(//input[@name='id'])[27]"));
		post_type.click();
	}
	
	/*
	 * 
	 */
	public static void fill_in_post(WebDriver driver, String t, String b, String z){
		WebElement title = driver.findElement(By.id("PostingTitle"));
		WebElement body = driver.findElement(By.id("PostingBody"));
		WebElement zip = driver.findElement(By.id("postal_code"));
		title.sendKeys(t);
		body.sendKeys(b);
		zip.sendKeys(z);
	}
	
	/*
	 * Create a valid draft with
	 * valid title, body, and zipcode
	 */
	@Test
	public void draft_valid(){
		WebDriver driver = new FirefoxDriver();
		set_up_code(driver);
		fill_in_post(driver, "title", "body", "15260");
		WebElement cont = driver.findElement(By.name("go"));
		cont.click();
		cont = driver.findElement(By.cssSelector("button.continue.bigbutton"));
		cont.click();
		cont = driver.findElement(By.cssSelector("section.body > form > button[name='go']"));
		cont.click();
		List<WebElement> list = driver
				.findElements(By
						.xpath("//*[contains(text(),'this is an unpublished draft.')]"));
		assertTrue("Error Message Not Found!", list.size() > 0);
		driver.quit();
	}
	
	/*
	 * Create invalid draft with
	 * invalid title
	 */
	@Test
	public void draft_invalid_title(){
		WebDriver driver = new FirefoxDriver();
		set_up_code(driver);
		fill_in_post(driver, "", "body", "15260");
		WebElement cont = driver.findElement(By.name("go"));
		cont.click();
		assertTrue(driver.findElements(By.cssSelector("span.err")).size() > 0);
		List<WebElement> list = driver
				.findElements(By
						.xpath("//*[contains(text(),'All postings must have a title.')]"));
		assertTrue("Error Message Not Found!", list.size() > 0);
		driver.quit();
	}
	
	/*
	 * Create invalid draft with
	 * invalid body
	 */
	@Test
	public void draft_invalid_body(){
		WebDriver driver = new FirefoxDriver();
		set_up_code(driver);
		fill_in_post(driver, "title", "", "15260");
		
		WebElement cont = driver.findElement(By.name("go"));
		cont.click();
		assertTrue(driver.findElements(By.cssSelector("span.err")).size() > 0);
		List<WebElement> list = driver
				.findElements(By
						.xpath("//*[contains(text(),'All postings must have a description')]"));
		assertTrue("Error Message Not Found!", list.size() > 0);
		driver.quit();
	}
	
	/*
	 * Create invalid draft with
	 * invalid zipcode 
	 */
	@Test
	public void draft_invalid_zipcode(){
		WebDriver driver = new FirefoxDriver();
		set_up_code(driver);
		fill_in_post(driver, "title", "body", "999999999999999");
		
		WebElement cont = driver.findElement(By.name("go"));
		cont.click();
		assertTrue(driver.findElements(By.cssSelector("span.err")).size() > 0);
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'That postal (zip) code is not valid for the area')]"));
		assertTrue("Error Message Not Found!", list.size() > 0);
		driver.quit();
	}
	
	/*
	 * Create invalid draft with
	 * invalid phone number
	 */
	@Test
	public void draft_invalid_phone(){
		WebDriver driver = new FirefoxDriver();
		set_up_code(driver);
		fill_in_post(driver, "title", "body", "15260");
		WebElement phone = driver.findElement(By.id("contact_phone"));
		phone.sendKeys("phone");
		
		WebElement cont = driver.findElement(By.name("go"));
		cont.click();
		assertTrue(driver.findElements(By.cssSelector("span.err")).size() > 0);
		List<WebElement> list = driver
				.findElements(By
						.xpath("//*[contains(text(),'That phone number is not valid for the country')]"));
		assertTrue("Error Message Not Found!", list.size() > 0);
		driver.quit();
	}
	
	
}
