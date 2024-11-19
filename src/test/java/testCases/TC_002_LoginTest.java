package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC_002_LoginTest extends BaseClass {

	@Test(groups={"sanity","Master"})
	public void verify_login() {
		logger.info("***Starting the TC_002_LoginTest****");

		try {
			HomePage hp = new HomePage(driver); // Home page
			hp.clickMyaccount();
			hp.clickLogin();

			LoginPage lp = new LoginPage(driver); // Login page
			lp.setEmail(p.getProperty("email"));
			Thread.sleep(1000);
			lp.setPassword(p.getProperty("password"));
			lp.clickLogin();

			MyAccountPage myac = new MyAccountPage(driver); // MyAccount Page
			boolean targetpage = myac.isMyAccountPageExist();

			Assert.assertEquals(targetpage, true, "Login failed"); // Verifing the element is visible or not
			// Assert.assertTrue(targetpage);
		} 
		catch (Exception e) 
		{
			Assert.fail();
		}
		logger.info("***Finished the TC_002_LoginTest****");
	}

}
