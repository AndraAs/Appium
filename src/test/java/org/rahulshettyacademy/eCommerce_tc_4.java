package org.rahulshettyacademy;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.rahulshettyacademy.TestUtils.BaseClass;
import org.rahulshettyacademy.pageObjects.android.CartPage;
import org.rahulshettyacademy.pageObjects.android.FormPage;
import org.rahulshettyacademy.pageObjects.android.ProductCataloguePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class eCommerce_tc_4 extends BaseClass{
	
	@Test(dataProvider="getData")
	public void FillForm(HashMap<String, String> input) throws InterruptedException {
		formPage.setNameField(input.get("name"));
		formPage.setGender(input.get("gender"));
		formPage.setCountrySelection(input.get("country"));
		ProductCataloguePage productCataloguePage= formPage.submitForm(); 
		productCataloguePage.addItemToCartByIndex(0);
		productCataloguePage.addItemToCartByIndex(0);
		CartPage cartPage=productCataloguePage.goToCartPage();
		Thread.sleep(2000);
		double totalSum=cartPage.getProductSum();
		double displayFormattedSum=cartPage.getTotalAmountDisplayed();
		
		AssertJUnit.assertEquals(totalSum, displayFormattedSum);
		cartPage.acceptTermsConditions();
		cartPage.acceptTermsConditions();
		cartPage.submitOrder();
		Thread.sleep(6000);
		Set<String> contexts=driver.getContextHandles();
		for(String contextName:contexts) {
			System.out.println(contextName);
		}
		driver.context("WEBVIEW_com.androidsample.generalstore");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[text()='Ler mais']/parent::div")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[text()='Aceitar tudo']")).click();
		Thread.sleep(3000);
		driver.findElement(By.name("q")).sendKeys("Andra");
		driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		driver.context("NATIVE_APP");
	}
	@BeforeMethod
	public void preSetup() {
		formPage.setActivity();
	}
	@DataProvider
	public Object [][] getData() throws IOException{
	List<HashMap<String, String>>data=getJsonData(System.getProperty("user.dir")+"//src//test//java//org//rahulshettyacademy//TestData//eCommerce.json");
		return new Object[][] { {data.get(0)},{data.get(1)} };
	
	}

}
