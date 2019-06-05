package com.web.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.web.common.Settings;
import com.web.pom.HomePage;
import com.web.pom.SearchPage;

public class ImageSearchTest {

	private HomePage homePage;
	private SearchPage searchPage;
	WebDriver driver;

	// Test "Upload a Image" function

	@Test
	public void searchImageWithUpload() throws InterruptedException, Exception {

		// To click on Images link
		homePage.clickSearchByImage();
		
		// To verify if ui is navigated to Search by image page
		Assert.assertTrue(homePage.verifySearchByImageBox());

		// Upload an image from local machine
		searchPage.uploadLocalImage(Settings.getLocalImage());
		// To verify upload status
		Assert.assertTrue(searchPage.verifyUploadStatus());

		// Find specified image on GUI
		WebElement webElement = searchPage.navigateToImage(Settings.getVisitRule());

		// Take a snapshot for expected visit page
		searchPage.snapshotLastPage(webElement);

		// Download specified image by expected rule
		String fileName = Settings.getDownloadImage();
		boolean down = searchPage.downloadImagetoLocal(webElement, fileName);
		Assert.assertTrue(down);

		// verify two images match or not
		Assert.assertTrue(searchPage.verifyImageIsSame(Settings.getDownloadImage(), Settings.getLocalImage()));

	}

	// Test "Past Image URL" function
	@Test
	public void searchImageWithURL() throws InterruptedException, Exception {

		homePage.clickSearchByImage();
		Assert.assertTrue(homePage.verifySearchByImageBox());

		// Past image with URL
		searchPage.uploadWithImageURL(Settings.getLocalImage());
		// To verify upload status
		Assert.assertTrue(searchPage.verifyUploadStatus());

		// Find specified image on GUI
		WebElement webElement = searchPage.navigateToImage(Settings.getVisitRule());

		// Take a snapshot for expected visit page
		searchPage.snapshotLastPage(webElement);

		// Download specified image to local
		String downImage = "expectedImageDown";
		boolean down = searchPage.downloadImagetoLocal(webElement, downImage);
		Assert.assertTrue(down);

		// Down the image in src url to lcoal
		String srcUrlFile = "sourceUrlFile";
		boolean downSrc = searchPage.downloadImagetoLocal(Settings.getImageURL(), srcUrlFile);
		Assert.assertTrue(downSrc);

		// verify image match or not
		Assert.assertTrue(searchPage.verifyImageIsSame(downImage, srcUrlFile));
	}

	@BeforeSuite
	public void beforeSuite() {

		System.setProperty("webdriver.gecko.driver", Settings.getGeckoDriver());
		System.setProperty("webdriver.firefox.bin", Settings.getFirefoxpath());

	}

	@AfterSuite
	public void afterSuite() {
		homePage = null;
		searchPage = null;
		driver.quit();
	}

	@BeforeMethod
	public void beforeMethod() {
		homePage.gotoHomePage();
	}

	@BeforeClass
	public void beforeClass() {
		homePage = new HomePage();
		searchPage = new SearchPage();
		driver = (WebDriver) new FirefoxDriver();
		homePage.setWebDriver(driver);
	}

}
