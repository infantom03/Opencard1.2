package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "(//span[normalize-space()='My Account'])[1]")
	WebElement InkMyaccount;

	@FindBy(xpath = "(//a[normalize-space()='Register'])[1]")
	WebElement inkRegister;

	@FindBy(linkText = "Login")	
	WebElement linklogin;
	
	public void clickLogin() {
		linklogin.click();
	}
	
	public void clickMyaccount() {
		InkMyaccount.click();
	}

	public void clickRegister() {
		inkRegister.click();
	}
}
