package com.web.pom;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.web.common.PageUtils;
import com.web.common.Settings;

public class SearchPage extends HomePage {

	public WebDriver driver;
	private String baseUrl = HomePage.baseUrl;

	public SearchPage() {
		driver = super.getWebDriver();
	}

	public void gotoHomePage() {
		driver.navigate().to(baseUrl);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public boolean verifyUploadStatus() {

		By findBy = By.xpath("//div[text()='Pages that include matching images']");
		return PageUtils.verifyWebElement(driver, findBy);
	}

	/**
	 * Compare two image files
	 * 
	 * @param downfile
	 * @return
	 * @throws Exception,InterruptedException
	 */
	public boolean verifyImageIsSame(String downfile,String localfile) throws Exception, InterruptedException {

		File f1 = new File(downfile);
		PageUtils pu = new PageUtils();
//		File f = new File(Settings.getImageName());
		File f = new File(localfile);

		
		return pu.testImageComparison(f1, f);

	}

	/**
	 * input a image url, and check "Visually similar images" characters on result page
	 * @param url
	 */
	public void uploadWithImageURL(String url) {

		driver.findElement(By.xpath("//span[text()='Paste image URL']")).click();

		// Input specific url
		
		//*[@id="qbui"]
		WebElement uploadElement = driver.findElement(By.id("qbui"));
		uploadElement.sendKeys(Settings.getImageURL());
		
		//Click Search By Image button
		driver.findElement(By.xpath("//*[@id=\"qbbtc\"]/input")).click();
		// Wait Upload finish
		By uploadStatus = By.xpath("//a[text()='Visually similar images']");
		PageUtils.waitElementDisplay(driver, uploadStatus, 200);
	}

	/**
	 * Test "Upload an image" Tab
	 * 
	 * @param imageName
	 */
	public void uploadLocalImage(String imageName) {

		driver.findElement(By.xpath("//span[text()='Upload an image']")).click();

		// Click Choose file button
		WebElement uploadElement = driver.findElement(By.id("qbfile"));
		uploadElement.sendKeys(imageName);

		// Wait Upload finish
		By uploadStatus = By.xpath("//span[text()='Uploading file']");
		PageUtils.waitElementHidden(driver, uploadStatus, 200);

	}

	/**
	 * Get specific image by config file
	 * 
	 * @param visitRule
	 */
	public WebElement navigateToImage(int visitRule) {

		// Get all similar images to a list
		List<WebElement> webElement = driver.findElements(By.xpath("//*/g-img[1]"));
		
		//Find expected image by visit rule 
		WebElement expect = webElement.get(visitRule);

		return expect;

	}

	public void snapshotLastPage(WebElement webElement) {

		String webUrl = webElement.getAttribute("title");
		driver.navigate().to(webUrl);
		PageUtils pu = new PageUtils();

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		pu.takeSnaphot(driver, sdf.format(cal.getTime()));

	}

	/**
	 * Donwload image to local with src property
	 * 
	 * @param webElement
	 * @return
	 */
	public boolean downloadImagetoLocal(WebElement webElement,String fileName) {

		boolean downloadImageStatus = false;
		URL imageURL;
		
		try {
			String websrc = webElement.getAttribute("src");
			imageURL = new URL(websrc);

			BufferedImage saveImage = ImageIO.read(imageURL);
			downloadImageStatus = ImageIO.write(saveImage, "png", new File(fileName));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return downloadImageStatus;

	}
	
	/**
	 * Donwload image to local with src property
	 * 
	 * @param webElement
	 * @return
	 */
	public boolean downloadImagetoLocal(String websrc,String fileName) {

		boolean downloadImageStatus = false;
		URL imageURL;
		try {
			imageURL = new URL(websrc);

			BufferedImage saveImage = ImageIO.read(imageURL);
			downloadImageStatus = ImageIO.write(saveImage, "png", new File(fileName));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return downloadImageStatus;

	}

}
