package org.rahulshettyacademy.pageObjects.android;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.rahulshettyacademy.utils.AndroidActions;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class FormPage extends AndroidActions {
	AndroidDriver driver;
	public FormPage(AndroidDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	@AndroidFindBy(id="com.androidsample.generalstore:id/nameField")
	private WebElement nameField;
	@AndroidFindBy(xpath="//*[@text='Female']")
	private WebElement femaleOption;
	@AndroidFindBy(xpath="//*[@text='Male']")
	private WebElement maleOption;
	@AndroidFindBy(id="com.androidsample.generalstore:id/btnLetsShop")
	private WebElement shopButton;
	public void setNameField(String name) {
		nameField.sendKeys(name);
		driver.hideKeyboard();
	}
	
	public void setActivity() {
		//set Screen to Home page
				Activity activity= new Activity("com.androidsample.generalstore", "com.androidsample.generalstore.MainActivity");
				driver.startActivity(activity);
	}
	public void setGender(String gender) {
		if(gender.contains("female")) 
			femaleOption.click();
		else
			maleOption.click();
	}	
	@AndroidFindBy(id="com.androidsample.generalstore:id/spinnerCountry")
	private WebElement countrySelection;	

	
		public void setCountrySelection(String countryName) {
			countrySelection.click();
			scrollToText(countryName);
			driver.findElement(By.xpath("//android.widget.TextView[@text='"+countryName+"']")).click();
		}
		public ProductCataloguePage submitForm() {
			shopButton.click();
			return new ProductCataloguePage(driver);
		}
	}

