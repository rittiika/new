//package com.browserstack;
import io.github.bonigarcia.wdm.WebDriverManager;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.net.URL;
import java.time.Duration;
import java.io.IOException;

public class Config {
    public WebDriver driver;
//Tried Running in Browserstack env but it was throwing Unable to parse remote response: Authorization required contacted TS
	//Running perfectly locally thats why commented the Browserstack code
    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://elpais.com/");
        driver.findElement(By.xpath("//*[@id=\"didomi-notice-agree-button\"]")).click();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }

    @SuppressWarnings("null")
    public String translateText(String text) throws Exception {
        OkHttpClient client = new OkHttpClient();

MediaType mediaType = MediaType.parse("application/json");
RequestBody body = RequestBody.create(mediaType, "{\"from\":\"es\",\"to\":\"en\",\"q\":\"" + text + "\"}");
Request request = new Request.Builder()
	.url("https://rapid-translate-multi-traduction.p.rapidapi.com/t")
	.post(body)
	.addHeader("x-rapidapi-key", "43c016d5a9msh5558b63acd2d0c0p195cf4jsn9687fc441b63")
	.addHeader("x-rapidapi-host", "rapid-translate-multi-traduction.p.rapidapi.com")
	.addHeader("Content-Type", "application/json")
	.build();

Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        return responseBody;
    }
}
   
