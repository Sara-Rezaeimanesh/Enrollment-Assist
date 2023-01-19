package ir.proprog.enrollassist;

import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

public class UntitledTestCase {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    JavascriptExecutor js;
    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\N.GH\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "https://www.google.com/";
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void testUntitledTestCase() throws Exception {
        driver.get("http://localhost:8080/swagger-ui/index.html#/");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div/span[2]")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/div[2]/label/div/select")).click();
        new Select(driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/div[2]/label/div/select"))).selectByVisibleText("application/json");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div/div[2]/button")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).click();
        //ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea | ]]
        driver.findElement(By.xpath("//*/text()[normalize-space(.)='']/parent::*")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).click();
        //ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea | ]]
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).click();
        //ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea | ]]
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).click();
        //ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea | ]]
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).clear();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).sendKeys("{\n  \"enrollmentListId\": 0,\n  \"enrollmentListName\": \"NargesList\",\n  \"sections\": [\n     \n  ],\n  \"student\": {\n    \"graduateLevel\": \"Undergraduate\",\n    \"studentId\": 0,\n    \"studentNo\": {\n      \"number\": \"810198000\"\n    },\n    \"userId\": \"string\"\n  }\n}");
        driver.findElement(By.xpath("//input[@value='']")).click();
        driver.findElement(By.xpath("//input[@value='810198000']")).clear();
        driver.findElement(By.xpath("//input[@value='810198000']")).sendKeys("810198000");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addSectionUsingPUT']/div/span[2]/a/span")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addSectionUsingPUT']/div[2]/div/div/div/div[2]/button")).click();
        driver.findElement(By.xpath("//input[@value='']")).click();
        driver.findElement(By.xpath("//input[@value='47']")).clear();
        driver.findElement(By.xpath("//input[@value='47']")).sendKeys("47");
        driver.findElement(By.xpath("//input[@value='']")).click();
        driver.findElement(By.xpath("//input[@value='33']")).clear();
        driver.findElement(By.xpath("//input[@value='33']")).sendKeys("33");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addSectionUsingPUT']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//input[@value='33']")).click();
        driver.findElement(By.xpath("//input[@value='33']")).click();
        driver.findElement(By.xpath("//input[@value='33']")).click();
        driver.findElement(By.xpath("//input[@value='34']")).clear();
        driver.findElement(By.xpath("//input[@value='34']")).sendKeys("34");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addSectionUsingPUT']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//input[@value='34']")).click();
        driver.findElement(By.xpath("//input[@value='37']")).clear();
        driver.findElement(By.xpath("//input[@value='37']")).sendKeys("37");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addSectionUsingPUT']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//input[@value='37']")).click();
        driver.findElement(By.xpath("//input[@value='38']")).clear();
        driver.findElement(By.xpath("//input[@value='38']")).sendKeys("38");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addSectionUsingPUT']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-checkRegulationsUsingGET']/div/span[2]/a/span")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-checkRegulationsUsingGET']/div[2]/div/div/div/div[2]/button")).click();
        driver.findElement(By.xpath("//input[@value='']")).click();
        driver.findElement(By.xpath("//input[@value='47']")).clear();
        driver.findElement(By.xpath("//input[@value='47']")).sendKeys("47");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-checkRegulationsUsingGET']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-emptyListUsingPUT']/div/span")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-emptyListUsingPUT']/div[2]/div/div/div/div[2]/button")).click();
        driver.findElement(By.xpath("//input[@value='']")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-emptyListUsingPUT']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/input")).clear();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-emptyListUsingPUT']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/input")).sendKeys("47");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-emptyListUsingPUT']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-emptyListUsingPUT']/div")).click();
        driver.findElement(By.xpath("//input[@value='38']")).click();
        driver.findElement(By.xpath("//input[@value='33']")).clear();
        driver.findElement(By.xpath("//input[@value='33']")).sendKeys("33");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addSectionUsingPUT']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//div[@id='swagger-ui']/section/div[2]/div[2]")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).clear();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).sendKeys("{\n  \"enrollmentListId\": 0,\n  \"enrollmentListName\": \"NargessList\",\n  \"sections\": [\n     \n  ],\n  \"student\": {\n    \"graduateLevel\": \"Undergraduate\",\n    \"studentId\": 0,\n    \"studentNo\": {\n      \"number\": \"810198000\"\n    },\n    \"userId\": \"string\"\n  }\n}");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div/span[2]/a/span")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-emptyListUsingPUT']/div/span[2]/a/span")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-emptyListUsingPUT']/div/span[2]/a/span")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addSectionUsingPUT']/div/span[2]/a/span")).click();
        driver.findElement(By.xpath("//input[@value='47']")).click();
        driver.findElement(By.xpath("//input[@value='47']")).click();
        driver.findElement(By.xpath("//input[@value='47']")).click();
        driver.findElement(By.xpath("//input[@value='48']")).clear();
        driver.findElement(By.xpath("//input[@value='48']")).sendKeys("48");
        driver.findElement(By.xpath("//input[@value='33']")).click();
        driver.findElement(By.xpath("//input[@value='37']")).clear();
        driver.findElement(By.xpath("//input[@value='37']")).sendKeys("37");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addSectionUsingPUT']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//input[@value='37']")).click();
        driver.findElement(By.xpath("//input[@value='38']")).clear();
        driver.findElement(By.xpath("//input[@value='38']")).sendKeys("38");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addSectionUsingPUT']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-checkRegulationsUsingGET']/div")).click();
        driver.findElement(By.xpath("//input[@value='47']")).click();
        driver.findElement(By.xpath("//input[@value='48']")).clear();
        driver.findElement(By.xpath("//input[@value='48']")).sendKeys("48");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-checkRegulationsUsingGET']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-removeSectionUsingDELETE']/div/span[2]/a/span")).click();
        driver.findElement(By.xpath("//input[@value='']")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-removeSectionUsingDELETE']/div[2]/div/div/div/div[2]/button")).click();
        driver.findElement(By.xpath("//input[@value='']")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-removeSectionUsingDELETE']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/input")).clear();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-removeSectionUsingDELETE']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/input")).sendKeys("48");
        driver.findElement(By.xpath("//input[@value='']")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-removeSectionUsingDELETE']/div[2]/div/div/div[2]/div/table/tbody/tr[2]/td[2]/input")).clear();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-removeSectionUsingDELETE']/div[2]/div/div/div[2]/div/table/tbody/tr[2]/td[2]/input")).sendKeys("38");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-removeSectionUsingDELETE']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//input[@value='38']")).click();
        driver.findElement(By.xpath("//input[@value='39']")).clear();
        driver.findElement(By.xpath("//input[@value='39']")).sendKeys("39");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addSectionUsingPUT']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//input[@value='39']")).click();
        driver.findElement(By.xpath("//input[@value='40']")).clear();
        driver.findElement(By.xpath("//input[@value='40']")).sendKeys("40");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addSectionUsingPUT']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//input[@value='40']")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-checkRegulationsUsingGET']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//input[@value='38']")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-removeSectionUsingDELETE']/div[2]/div/div/div[2]/div/table/tbody/tr[2]/td[2]/input")).clear();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-removeSectionUsingDELETE']/div[2]/div/div/div[2]/div/table/tbody/tr[2]/td[2]/input")).sendKeys("40");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-removeSectionUsingDELETE']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//input[@value='40']")).click();
        driver.findElement(By.xpath("//input[@value='41']")).clear();
        driver.findElement(By.xpath("//input[@value='41']")).sendKeys("41");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addSectionUsingPUT']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//input[@value='41']")).click();
        driver.findElement(By.xpath("//input[@value='42']")).clear();
        driver.findElement(By.xpath("//input[@value='42']")).sendKeys("42");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addSectionUsingPUT']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-checkRegulationsUsingGET']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-checkRegulationsUsingGET']/div[2]/div/div[3]/div[2]/div/div/table/tbody/tr/td[2]/div/div/pre/span[6]")).click();
        driver.findElement(By.xpath("//input[@value='42']")).click();
        driver.findElement(By.xpath("//input[@value='43']")).clear();
        driver.findElement(By.xpath("//input[@value='43']")).sendKeys("43");
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addSectionUsingPUT']/div[2]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-checkRegulationsUsingGET']/div[2]/div/div[2]/button")).click();
    }

    @After
    public void tearDown() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
