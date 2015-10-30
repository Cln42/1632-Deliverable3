import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SavedSearchTests
{
	private WebDriver driver;
	
	@Before
	public void setUp()
	{
		driver = new FirefoxDriver();
		driver.get("https://pittsburgh.craigslist.org");
	}
	
	public void save_search(String search)
	{
		driver.get("https://pittsburgh.craigslist.org");
		WebElement searchBar = driver.findElement(By.id("query"));
		searchBar.sendKeys("tire");
		searchBar.submit();
		
		WebElement saveSearchLink = driver.findElement(By.linkText("save search"));
		saveSearchLink.click();
	}
	
	private void log_in(WebDriver driver)
	{
		driver.get("https://accounts.craigslist.org/");
		WebElement usernameField = driver.findElement(By.id("inputEmailHandle"));
		WebElement passwordField = driver.findElement(By.id("inputPassword"));
		
		usernameField.sendKeys("mythrowawayhandle2");
		passwordField.sendKeys("thisisapassword");
		usernameField.submit();
	}
	
	//Ensure the website can save a single search.
	@Test
	public void savedsearch_test1()
	{
		log_in(driver);
		
		save_search("tire");
		
		assertTrue(driver.getPageSource().contains("search was saved."));
	}
	
	
	//Ensure the website will prompt you to log in when saving when you're not logged in.
	@Test
	public void savedsearch_test3()
	{		
		save_search("tire");
		
		String url = driver.getCurrentUrl();
		
		assertTrue(driver.getPageSource().contains("Log in to your craigslist account"));
		assertTrue(driver.getPageSource().contains("Create a craigslist account"));
		assertTrue(url.contains("accounts.craigslist.org/login"));
	}
	
	//Ensure website will save one that you already saved regardless.
	@Test
	public void savedsearch_test4()
	{
		log_in(driver);
		
		save_search("tire");
	
		save_search("tire");
		
		assertTrue(driver.getPageSource().contains("search was saved."));
	}
	
	
	//ensure website will save an empty search.
	@Test
	public void savedsearch_test5()
	{
		log_in(driver);
		
		save_search("");
		
		assertTrue(driver.getPageSource().contains("search was saved."));
	}
	
	//Ensure the website can sucessfully delete a search.
	public void savedsearch_test6()
	{
		log_in(driver);
		
		save_search("tire");
		
		driver.get("https://accounts.craigslist.org/login/home?show_tab=searches");
		WebElement deleteButton = driver.findElement(By.xpath("//button[contains(., 'delete')]"));
		deleteButton.click();
		
		assertTrue(driver.getPageSource().contains("You have no saved searches at this time."));
	}
}