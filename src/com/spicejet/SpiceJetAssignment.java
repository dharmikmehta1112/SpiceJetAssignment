package com.spicejet;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SpiceJetAssignment {
	private static final Logger LOG = Logger.getLogger(SpiceJetAssignment.class);
	
	@Test (description = "To verify on next page Student is mentioned under Discount Field")
	public void verifyStudent() {
		WebDriverManager.chromedriver().setup();
		LOG.info("Launching Chrome Browser.");
		RemoteWebDriver driver = new ChromeDriver();
		LOG.info("Maximize browser window.");
		driver.manage().window().maximize();
		LOG.info("Deleting all browser cookies.");
		driver.manage().deleteAllCookies();		
		LOG.info("Implicitly waiting to 40 seconds for page to load.");
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		LOG.info("Implicitly waiting of 5 seconds started.");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		LOG.info("Launching application URL.");
		driver.get("https://www.spicejet.com/");
		LOG.info("Clicking on one way radio button.");
		WebElement oneWayRd = driver.findElement(By.cssSelector("#travelOptions td:nth-child(1) label"));
		oneWayRd.click();
		LOG.info("Checking for one way return date is disabled.");
		WebElement toDate = driver.findElement(By.id("ctl00_mainContent_txt_Todate"));
		boolean flag = toDate.isEnabled();
		if(true)
			System.out.println("For one way trip Return Date is enabled.");
		else
			System.out.println("For one way trip Return Date is disabled.");
		LOG.info("Selecting departure city as Bengaluru.");
		driver.findElement(By.id("ctl00_mainContent_ddl_originStation1_CTXT")).click();
		driver.findElement(By.xpath("//a[@value='BLR']")).click();
		LOG.info("Selecting arrival city as Pune.");	
		driver.findElement(By.id("ctl00_mainContent_ddl_destinationStation1_CTXT")).click();
		driver.findElement(By.xpath("(//a[@value='PNQ'])[2]")).click();
		LOG.info("Selecting departure date System Date + 2.");
		WebElement departDate = driver.findElement(By.xpath("//input[@id='ctl00_mainContent_txt_Fromdate']"));
		LocalDate locdate = java.time.LocalDate.now();
		int day = locdate.getDayOfMonth() + 2;
		int month = locdate.getMonthValue();
		int year = locdate.getYear();
		String dateVal = day + "-" + month + "-" + year; 
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('value','" +dateVal+ "')", departDate);		
		oneWayRd.click();			// click on anywhere
		LOG.info("Selecting 5 adulds as passenger.");
		driver.findElement(By.id("divpaxinfo")).click();
		WebElement adult = driver.findElement(By.id("ctl00_mainContent_ddl_Adult"));
		Select adultCount = new Select(adult);
		adultCount.selectByValue("5");
		LOG.info("Selecting curreny as INR.");
		WebElement curr = driver.findElement(By.cssSelector("#ctl00_mainContent_DropDownListCurrency"));
		Select currType = new Select(curr);
		currType.selectByIndex(3);
		LOG.info("Selecting the checkbox for Student.");
		driver.findElement(By.xpath("//input[@id='ctl00_mainContent_chk_StudentDiscount']")).click();
		LOG.info("Clicking on search.");
		driver.findElement(By.cssSelector("input[id='ctl00_mainContent_btn_FindFlights']")).click();
		LOG.info("Verifying on next page Student is mentioned under Discount field.");
		String expText = " Student";
		String actText = driver.findElement(By.xpath("//span[contains(text(),'Student')]")).getText();
		Assert.assertEquals(actText, expText, "Student Discount is not verified");
		driver.close();
	}	
}