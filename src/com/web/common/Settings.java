package com.web.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Settings {

    private static Properties config = new Properties();
    private static Properties configRule = new Properties();

    static {
        String settingPath = System.getProperty("config.properties");
        try {
            config.load(new FileInputStream(settingPath));
            
            InputStream input = new FileInputStream("visitrule.properties");
            configRule.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static int getVisitRule() {
    	String s = configRule.getProperty("VISIT_RESULT");
    	return Integer.parseInt(s);
        		
    } 

    public static String getBaseUrl() {
        return config.getProperty("baseUrl", "https://www.google.com");
    } 
    
    public static String getLocalImage() {
        return config.getProperty("imageName", "C:\\snow.png");
    } 
   
    public static String getGeckoDriver() {
        return config.getProperty("gecko");
    } 
    
    public static String getFirefoxpath() {
        return config.getProperty("firefoxpath");
    }
    
    public static String getImageURL() {
        return config.getProperty("imageurl");
    }
    
    public static String getDownloadImage() {
        return config.getProperty("downloadimage");
    }
    
    
    
    public static boolean hasWaitEvent() {
        return Boolean.parseBoolean(config.getProperty("hasWaitEvent", "true"));
    }

}
