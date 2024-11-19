package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC_003_LoginDDT extends BaseClass
{
	@Test(dataProvider = "LoginData",dataProviderClass = DataProviders.class,groups="Datadriven")	//Getting data providers form the different class
	public void verify_loginDDT(String email, String pwd, String exp) {
	logger.info("***Starting the TC_003_LoginDDT****");
	
	try {
		HomePage hp = new HomePage(driver); // Home page
		hp.clickMyaccount();
		hp.clickLogin();

		
		LoginPage lp = new LoginPage(driver); // Login page
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();

		
		MyAccountPage myac = new MyAccountPage(driver); // MyAccount Page
		boolean targetpage = myac.isMyAccountPageExist();

		
		/*Daa is valid = login success - test pass - logout
						 login failed  - test fail
		 data is invalid - login success - test fail - logout
						 	 login failed - test pagg*/
		
		if(exp.equalsIgnoreCase("Valid"))
		{
			if(targetpage==true) 
			{
				myac.clickLogout();
				Assert.assertTrue(true);
			}
			else
			{
				Assert.assertTrue(false);
			}
		if(exp.equalsIgnoreCase("invalid"))
		{
			if(targetpage==false)
			{
				myac.clickLogout();
				Assert.assertTrue(false);
			}
			else
			{
				Assert.assertTrue(true);
			}
		}
		
		}
	}catch(Exception e) 
	{
		Assert.fail();
	}
	
	logger.info("***Finished the TC_002_LoginTest****");
}
}

