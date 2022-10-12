package org.rahulshettyacademy.TestUtils;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.rahulshettyacademy.pageObjects.android.FormPage;
import org.rahulshettyacademy.utils.AppiumUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BaseClass extends AppiumUtils {
	public AndroidDriver driver;
	public AppiumDriverLocalService service;
	public FormPage formPage;
	
	@BeforeClass
	public void ConfigureAppium() throws IOException
	{
			Properties prop = new Properties();
			FileInputStream fis= new FileInputStream(System.getProperty("user.dir")+"src\\main\\java\\org\\rahulshettyacademy\\resources\\data.properties");
			prop.load(fis);
			String ipAddress=prop.getProperty("ipAddress");
			String port=prop.getProperty("port");
		    service=startAppiumServer(ipAddress, Integer.parseInt(port));
			UiAutomator2Options options= new UiAutomator2Options();
			options.setDeviceName(prop.getProperty("AndroidDeviceName"));
//			options.setDeviceName("Nexus 5X API 30");
			options.setChromedriverExecutable("C:\\Users\\andrada.astalus.FARFETCH\\Documents\\appium\\chromedriver.exe");
//			options.setApp("C:\\Users\\andrada.astalus.FARFETCH\\eclipse-workspace\\Appium\\src\\test\\java\\resources\\ApiDemos-debug.apk");
			options.setApp(System.getProperty("user.dir")+"src\\test\\java\\org\\rahulshettyacademy\\resources\\General-Store.apk");
			 driver = new AndroidDriver(service.getUrl(), options);
			 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			 formPage= new FormPage(driver);
}
	public void longPressAction(WebElement ele) {
		((JavascriptExecutor)driver).executeScript("mobile:longClickGesture", ImmutableMap.of("elementId",((RemoteWebElement)ele).getId(),"duration",2000));
		}
	public void scrollToEndAction() {
		boolean canScrollMore;
		do {
		 canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
			    "left", 100, "top", 100, "width", 200, "height",200, 
			    "direction", "down",
			    "percent", 3.0
			));
		}while(canScrollMore);
	}
	public void swipeAction(WebElement ele, String direction) {
		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
			    "elementId", ((RemoteWebElement)ele).getId(),
			    "direction", direction,
			    "percent", 0.75
			));
	}
	
	public Double getFormattedAmount(String amount) {
		Double price=Double.parseDouble(amount.substring(1));
		return price;
	}
	
	
	@AfterClass
	public void tearDown() {
		driver.quit();
		service.stop();
	}
}
