package com.web.common;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Created by lixzhao on 2019/6/4.
 */
public class PageUtils {

    private static WebDriverWait getWebDriverWait(WebDriver webDriver, long timeoutSeconds){
        return (WebDriverWait) (new WebDriverWait(webDriver, timeoutSeconds)).ignoring(StaleElementReferenceException.class);
    }

    

    /**
     * Take a snapshot with specific name
     * @param webDriver
     * @param snapshotName
     */
 	public void takeSnaphot( WebDriver webDriver, String snapshotName ) {
 		  if (webDriver != null) { 
 	            String dir = System.getProperty("output.dir");
 	            if (dir != null && dir.length() != 0) {
 	                String fileName = dir + "/" + snapshotName + ".png";
 	                File scrFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
 	                try {
 	                    FileUtils.copyFile(scrFile, new File(fileName));
 	                } catch (IOException e) {
 	                    System.out.println("Screenshot for " + snapshotName + " is not saved." + e);
 	                }
 	                System.out.println(fileName + " is saved!");
 	            } else {
 	            	System.out.println("output.dir is not set, the screen shot cannot be saved.");
 	            }
 	           
 	        }

 	}
 	
    
    /**
     * Wait until the web element gets displayed.
     *
     * @param webDriver selenium web driver
     * @param by        web element location
     * @param timeoutInSeconds   time out seconds
     */
    public static void waitElementDisplay(WebDriver webDriver, By by, long timeoutInSeconds) {
        getWebDriverWait(webDriver, timeoutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    /**
     * Wait until the web element becomes hidden.
     *
     * @param webDriver selenium web driver
     * @param by        web element location
     * @param timeoutInSeconds   time out seconds
     */
    public static void waitElementHidden(WebDriver webDriver, By by, long timeoutInSeconds) {
        getWebDriverWait(webDriver, timeoutInSeconds).until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

   

    public static boolean verifyWebElement(WebDriver driver, By findBy) {
    	
    	boolean flag=false;	    	
    	
    	if(driver.findElements(findBy).size() != 0)
    	{
    		flag = true;	    	
    	}
    	
        return flag;
    }
   

	/**
	 * Compare the 2 image files
	 * @param fileInput
	 * @param fileOutPut
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	 public Boolean testImageComparison(File fileInput,File fileOutPut) throws IOException, InterruptedException {         
         
		 Boolean matchFlag = true;
         
         BufferedImage bufferfileInput = ImageIO.read(fileInput);
         DataBuffer bufferfileInput = bufferfileInput.getData().getDataBuffer();
         int sizefileInput = bufferfileInput.getSize();                     
         BufferedImage bufferfileOutPut = ImageIO.read(fileOutPut);
         DataBuffer datafileOutPut = bufferfileOutPut.getData().getDataBuffer();
         int sizefileOutPut = datafileOutPut.getSize();
         if(sizefileInput == sizefileOutPut) {                         
            for(int i=0; i<sizefileInput; i++) {
                  if(bufferfileInput.getElem(i) != datafileOutPut.getElem(i)) {
                        matchFlag = false;
                        break;
                  }
             }
         }
         else {                           
            matchFlag = false;
         return matchFlag;    
      }
	 }

	
}
