package pageObjects;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountRegistrationPage extends BasePage {

	public AccountRegistrationPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@id='input-firstname']")
	WebElement txtfirstname;

	@FindBy(xpath = "//input[@id='input-lastname']")
	WebElement txtlastname;

	@FindBy(xpath = "//input[@id='input-email']")
	WebElement txtemail;

	@FindBy(xpath = "//input[@id='input-telephone']")
	WebElement txtTelephone;

	@FindBy(xpath = "//input[@id='input-password']")
	WebElement txtpassword;

	@FindBy(xpath = "//input[@id='input-confirm']")
	WebElement txtconfirmpassword;

	@FindBy(xpath = "//input[@name='agree']")
	WebElement chkpolicy;

	@FindBy(xpath = "//input[@value='Continue']")
	WebElement btncontinue;

	@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgconfirmation;

	public void setFirstName(String fname) {
		txtfirstname.sendKeys(fname);
	}

	public void setLastName(String lname) {
		txtlastname.sendKeys(lname);
	}

	public void setEmail(String email) {
		txtemail.sendKeys(email);
	}

	public void setTelephone(String tele) {
		txtTelephone.sendKeys(tele);
	}

	public void setPassword(String pwd) {
		txtpassword.sendKeys(pwd);
	}

	public void setConfirmPassword(String pwd) {
		txtconfirmpassword.sendKeys(pwd);
	}

	public void setPrivacyPolicy() {
		chkpolicy.click();
	}

	public void clickContinue() {
		// solu 1
		btncontinue.click();

		// solu 2
//		btncontinue.submit();
//		
		// solu3
//		Actions act = new Actions(driver);
//		act.moveToElement(btncontinue).click().perform();

		// solu4
//		JavascriptExecutor js = (JavascriptExecutor)driver;
//		js.executeScript("arguments[0].click();", btncontinue);

		// sou5
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//		wait.until(ExpectedConditions.elementToBeClickable(btncontinue)).click();
	}

	public String getConfirmationMsg() {
		try {
			return (msgconfirmation.getText());
		} catch (Exception e) {
			return (e.getMessage());
		}
	}
}
