package com.web.pom;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.web.common.PageUtils;
import com.web.common.Settings;

public class HomePage {

	public WebDriver webDriver;

	public WebDriver getWebDriver() {
		return webDriver;
	}

	public void setWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public static String baseUrl = Settings.getBaseUrl();;

	public HomePage() {
		if (webDriver == null) {
			webDriver = new FirefoxDriver();
		}
	}

	public void gotoHomePage() {
		webDriver.navigate().to(baseUrl);
		webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void clickSearchByImage() {

		webDriver.findElement(By.linkText("Images")).click();
		By imageBy = By.xpath("//div[@title='Google Images']");

		// Wait for Images page show case
		PageUtils.waitElementDisplay(webDriver, imageBy, 100);

	}

	public boolean verifySearchByImageBox() {

		By findBy = By.xpath("//div[@aria-label=\"Search by image\"]");
		return PageUtils.verifyWebElement(webDriver, findBy);
	}
}
