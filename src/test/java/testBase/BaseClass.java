package testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

	public static WebDriver driver;
	public Logger logger;// log4j
	public Properties p;

	@BeforeClass(groups = { "sanity", "Regression", "Master" })
	@Parameters({ "os", "browser" })

	public void setUp(String os, String br) throws IOException {
		// LOGING CONFIG>PROPERTIES File
		FileInputStream file = new FileInputStream("./src//test//resources//config.properties");
		p = new Properties();
		p.load(file);

//		

		logger = LogManager.getLogger(this.getClass()); // dynamically check the class using this keyword

		// IF Remote
		if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();

			// OS
			if (os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			} else if (os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.WIN11);
			} else {
				System.out.println("No Matching os");
				return;
			}

			// Browser
			switch (br.toLowerCase()) {
			case "chrome":capabilities.setBrowserName("chrome");break;
			case "edge":capabilities.setBrowserName("MicrosoftEdge");break;
			case "firefox":capabilities.setBrowserName("firefox"); break;
			default:System.out.println("No Matching browser name");return;
			}
			
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
		}
		
		if (p.getProperty("execution_env").equalsIgnoreCase("local")) {
			switch (br.toLowerCase()) {
			case "chrome":driver = new ChromeDriver();break;
			case "edge":driver = new EdgeDriver();break;
			case "firefox":driver = new FirefoxDriver();break;
			default:System.out.println("Invalid browser name");return;
			}
		}
		
		

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(p.getProperty("appUrl")); // reading url form properties file
		driver.manage().window().maximize();
	}

	@AfterClass(groups = { "sanity", "Regression", "Master" })
	public void tearDown() {
		driver.quit();
	}

	public String randomeString() {
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}

	public String randomeNumber() {
		String generatedNumber = RandomStringUtils.randomNumeric(10);
		return generatedNumber;
	}

	public String randomeAlphaNumberic() {
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		String generatedNumber = RandomStringUtils.randomNumeric(10);
		return (generatedString + generatedNumber);
	}

	public String captureScreen(String tname) throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

		String targetFilePath = System.getProperty("user.dir") + "/screenshots/" + tname + "_" + timeStamp + ".png";
		File targetFile = new File(targetFilePath);

		// Move the screenshot to the desired location
		sourceFile.renameTo(targetFile);

		// Return the file path as a String
		return targetFilePath;
	}

}
