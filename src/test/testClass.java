package test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

public class testClass {

  public static final String USERNAME = "anirudh6";
  public static final String AUTOMATE_KEY = "TbD5WvQgynCcqf4RzYUj";
  public static final String URL = "http://" + USERNAME + ":" + AUTOMATE_KEY + "@hub.browserstack.com/wd/hub";

  public static void main(String[] args) throws Exception {
    
    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability("browser", "Chrome");
    caps.setCapability("browser_version", "33.0");
    caps.setCapability("os", "Windows");
    caps.setCapability("os_version", "8");
    caps.setCapability("build", "Anirudh Experiments");
    caps.setCapability("browserstack.debug", "true");
    caps.setCapability("resolution", "1280x1024");
    //caps.setCapability("browserstack.debug", "true");
    
    WebDriver driver = new RemoteWebDriver(new URL(URL), caps);
    driver.get("https://www.python.org");
    driver.manage().window().maximize();
    //WebElement element = driver.findElement(By.name("q"));

    //element.sendKeys("BrowserStack");
   // element.submit();
   
    JavascriptExecutor jsx = (JavascriptExecutor)driver;
    int totalHeight = ((Long)(jsx.executeScript("return document.body.clientHeight"))).intValue();
    //System.out.println(totalHeight);
    
    int viewPortHeight = ((Long)(jsx.executeScript("var w = window,d = document,e = d.documentElement,g = d.getElementsByTagName('body')[0],x = w.innerWidth || e.clientWidth || g.clientWidth,y = w.innerHeight|| e.clientHeight|| g.clientHeight; return y"))).intValue();
    //System.out.println(viewPortHeight);

    driver = new Augmenter().augment(driver);
    
    //ctr is the number of files generated
    
    int ctr,i;
    for(i= 0,ctr=0;i<totalHeight;i+=viewPortHeight,ctr++)
    {
    	int  newHeight = viewPortHeight;
    	if(i+viewPortHeight > totalHeight)
    	{
    		newHeight = totalHeight - i;
    	}
    	
    	//((JavascriptExecutor) driver).executeScript("scroll(0,250);");
    	((JavascriptExecutor) driver).executeScript("scroll(0,"+i+");");
    	((JavascriptExecutor) driver).executeScript("document.documentElement.style.overflowX = 'hidden'; document.documentElement.style.overflowY = 'hidden';");
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile,new File(String.valueOf(i+1)+".png"));
    }
    
    int finalImageHeight=0;
    int finalImageWidth=0;
    
    for(i=0;i<ctr;i++)
    {
    	BufferedImage image = ImageIO.read(new File(ctr+".png"));
    	finalImageHeight+= image.getHeight();
    	finalImageWidth=image.getWidth();
    }
    
    System.out.println(finalImageHeight);
    System.out.println(finalImageWidth);
    
    String cmd="convert ";
    
    for(i=0;i<ctr;i++)
    {
    	int k=i+1;
    	cmd+=+k+".png ";
    }
    
    cmd+=" -append output.png";
    System.out.println(cmd);
    ProcessBuilder pb = new ProcessBuilder(cmd);
    pb.redirectErrorStream(true);
    
    try {
		Process p = pb.start();
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null;
		while((line=br.readLine())!=null){
		    System.out.println(line);
		}
		System.out.println(p.waitFor());

	}catch(Exception e) {

	}

    //System.out.println(driver.getTitle());
    driver.quit();
  }

}