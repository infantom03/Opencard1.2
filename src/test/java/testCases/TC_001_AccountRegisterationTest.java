package testCases;

import java.time.Duration;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC_001_AccountRegisterationTest extends BaseClass {

	@Test(groups={"Regression","Master"})
	public void verify_account_registration() {
		try {
			logger.info("****Starting the TC_001_AccountRegisterationTest**** ");

			HomePage hp = new HomePage(driver);
			hp.clickMyaccount();
			logger.info("Clicked on MyAccount link");
			hp.clickRegister();
			logger.info("Clicked on Register link");

			AccountRegistrationPage regpage = new AccountRegistrationPage(driver);

			logger.info("Provideing customer details");
			regpage.setFirstName(randomeString().toUpperCase());
			regpage.setLastName(randomeString().toUpperCase());
			regpage.setEmail(randomeString() + "@gmail.com");// randomly generated the email
			regpage.setTelephone(randomeNumber());

			// String password = randomeAlphaNumeric();
			String password = randomeAlphaNumberic();
			regpage.setPassword(password);
			regpage.setConfirmPassword(password);

			regpage.setPrivacyPolicy();
			regpage.clickContinue();

			logger.info("Validating expected message");
			String confmsg = regpage.getConfirmationMsg();

			if (confmsg.equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
			} else {
				logger.error("Test Failed ...");
				logger.debug("Debug logs");
				Assert.assertTrue(false);
			}
		} catch (Exception e) {
			logger.error("Test Failed ...");
			logger.debug("Debug logs");
			Assert.fail();
		}
		logger.info("****Finished the TC_001_AccountRegisterationTest**** ");

	}

}