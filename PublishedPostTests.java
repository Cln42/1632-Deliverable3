/*
As a seller
I want to delete a post
so that after I've sold the item, people stop trying to buy it.
-------------------------------
*/
import static org.junit.Assert.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class PublishedPostTests
{
	private WebDriver driver;
	private String baseUrl;
	
	@Before
	public void setUp() throws Exception 
	{
		driver = new FirefoxDriver();
		baseUrl = "https://pittsburgh.craigslist.org/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	public void log_in()
	{
		driver.get("https://accounts.craigslist.org/");
		WebElement usernameField = driver.findElement(By.id("inputEmailHandle"));
		WebElement passwordField = driver.findElement(By.id("inputPassword"));
		
		usernameField.sendKeys("mythrowawayhandle2");
		passwordField.sendKeys("thisisapassword");
		usernameField.submit();		
	}
	
	public void create_post()
	{
		driver.get("https://accounts.craigslist.org/login/home");
		driver.findElement(By.cssSelector("form.new_posting_thing > input[type=\"submit\"]")).click();
		driver.findElement(By.xpath("(//input[@name='id'])[6]")).click();
		driver.findElement(By.xpath("(//input[@name='id'])[27]")).click();
		driver.findElement(By.id("PostingTitle")).clear();
		driver.findElement(By.id("PostingTitle")).sendKeys("title");
		driver.findElement(By.id("wantamap")).click();
		driver.findElement(By.id("postal_code")).clear();
		driver.findElement(By.id("postal_code")).sendKeys("15213");
		driver.findElement(By.id("PostingBody")).clear();
		driver.findElement(By.id("PostingBody")).sendKeys("body");
		driver.findElement(By.name("go")).click();
		driver.findElement(By.cssSelector("button.continue.bigbutton")).click();
		driver.findElement(By.cssSelector("section.body > form > button[name=\"go\"]")).click();
		driver.findElement(By.name("go")).click();
	}
	
	
	//Begin to delete post but go back before deleting it.
	@Test
	public void publishedpost_test1()
	{
		log_in();
		create_post();
		driver.get(baseUrl + "/");
		driver.findElement(By.xpath("(//a[contains(text(),'my account')])[2]")).click();
		driver.findElement(By.linkText("active")).click();
		driver.findElement(By.name("go")).click();
		driver.findElement(By.name("go")).click();
		assertTrue(driver.getPageSource().contains("Your posting can be seen at"));
	}

	//Make a post and delete it proper.
	@Test
	public void publishedpost_test2()
	{
		log_in();
		create_post();
		driver.get(baseUrl + "/");
		driver.findElement(By.xpath("(//a[contains(text(),'my account')])[2]")).click();
		driver.findElement(By.linkText("active")).click();
		driver.findElement(By.name("go")).click();
		driver.findElement(By.xpath("(//input[@name='go'])[2]")).click();
		assertTrue(driver.getPageSource().contains("This posting has been deleted from craigslist."));
	}

	//Make a post and delete it and then undelete it.
	@Test
	public void publishedpost_test3()
	{
		log_in();
		create_post();
		driver.get(baseUrl + "/");
		driver.findElement(By.xpath("(//a[contains(text(),'my account')])[2]")).click();
		driver.findElement(By.linkText("active")).click();
		driver.findElement(By.name("go")).click();
		driver.findElement(By.xpath("(//input[@name='go'])[2]")).click();
		driver.findElement(By.name("go")).click();
		assertTrue(driver.getPageSource().contains("Your posting can be seen at"));
	}
}