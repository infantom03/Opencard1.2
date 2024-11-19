package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.apache.logging.log4j.core.config.Loggers;
import org.testng.ITestContext;
import org.testng.ITestNGListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestNGListener {
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;

	String repName;

	
		
		
	public void onStart(ITestContext testContext) {

		
	 
		
		
		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName = "Test-Report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter("./reports/" + repName+".html");

		sparkReporter.config().setDocumentTitle("Opencart Automation Report");
		sparkReporter.config().setReportName("opencart Functional Testing"); 
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		//It will take the Os and browser name where the script is running

		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating system", os);

		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		//If you are running the group then this code will help you to check
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if (!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println(result.getName() + " PASSED");
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());//to display groups in report
		test.log(Status.PASS, result.getName() + " Got Successfully Executed");
	}

	public void onTestFailure(ITestResult result) {
		System.out.println(result.getName() + " FAILED");
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());

		test.log(Status.FAIL, result.getName() + " got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());

		try {
			String imgPath = new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); //to display groups in reports
		test.log(Status.SKIP, result.getName() + " got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}

	public void onFinish(ITestResult testContext) {
		extent.flush();

		String pathOfExtentReport = System.getProperty("user.dir") +"/reports/" + repName;
		File extentReport = new File(pathOfExtentReport);

		//It will open the Report in the Browser automatically
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*try {
			//converting report into URL
			URL url = new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
			
			//Create the Email message to the team
			ImageHtmlEmail email = new ImageHtmlEmail();
			email.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("infantom123@gamil.com", "password"));
			email.setSSLOnConnect(true);
			email.setFrom("infantom123@gmail.com");//sender
			email.setSubject("Test Result");
			email.setMsg("Please find the attached Report");
			email.addTo("infantom03@gmail.com");//reciver
			email.attach(url,"extent report", "please check report");
			email.send();
		}
		catch(Exception e) {
			e.printStackTrace();
		}*/
	}
}
