package pageObjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SitesPage {
    public WebDriver driver;
    private WebDriverWait wait;
    private ContentPage contentPage;

    private By wkndSiteXpath = By.xpath("//div[normalize-space()='wknd-cucumber']");
    private By wkndUSFolderXpath = By.xpath("//div[@title='us']");
    private By wkndENFolderXpath = By.xpath("//div[@title='en']");
    private By createButtonXpath = By.xpath("//button[@class='granite-collection-create foundation-toggleable-control coral3-Button coral3-Button--primary']");
    private By pageItemXpath = By.xpath("//a[@class='cq-siteadmin-admin-createpage foundation-collection-action coral-Link coral3-BasicList-item coral3-AnchorList-item']//coral-list-item-content[@class='coral3-BasicList-item-content'][normalize-space()='Page']");
    private By contentPageXpath = By.xpath("//coral-card-title[normalize-space()='Content Page']");
    private By nextButtonXpath = By.xpath("//coral-panel[@class='coral3-Panel is-selected']//button[@type='button']");
    private By titleInputName = By.name("./jcr:title");
    private By pageNameInputName = By.name("pageName");
    private By pageTitleInputName = By.name("./pageTitle");
    private By navTitleInputName = By.name("./navTitle");
    private By createPageButtonXpath = By.xpath("//coral-button-label[normalize-space()='Create']");
    private By doneButtonXpath = By.xpath("//coral-button-label[text()=\"Done\"]/..");

    public SitesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.contentPage = new ContentPage(driver);
    }

    public void open() {
        this.driver.navigate().to("http://localhost:4502" + "/sites.html/content");
    }

    public void selectEnglishSite() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(wkndSiteXpath));
        this.driver.findElement(wkndSiteXpath).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(wkndUSFolderXpath));
        this.driver.findElement(wkndUSFolderXpath).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(wkndENFolderXpath));
        this.driver.findElement(wkndENFolderXpath).click();

        // Needed to allow URL to update fully
        Thread.sleep(500);
    }

    public void clickCreateButton() {
        wait.until(ExpectedConditions.elementToBeClickable(createButtonXpath));
        this.driver.findElement(createButtonXpath).click();
    }

    public void createContentPage() {
        wait.until(ExpectedConditions.elementToBeClickable(pageItemXpath));
        this.driver.findElement(pageItemXpath).click();
        wait.until(ExpectedConditions.elementToBeClickable(contentPageXpath));
        this.driver.findElement(contentPageXpath).click();
        this.driver.findElement(nextButtonXpath).click();
    }

    public void enterPageDetails() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(titleInputName));
        this.driver.findElement(titleInputName).sendKeys("Hello-World");
        this.driver.findElement(pageNameInputName).sendKeys("Hello-Name");
        this.driver.findElement(pageTitleInputName).sendKeys("Hello-Page-Title");
        this.driver.findElement(navTitleInputName).sendKeys("Hello-Nav-Title");
    }

    public void publishContentPage() {
        this.driver.findElement(createPageButtonXpath).click();
        wait.until(ExpectedConditions.elementToBeClickable(doneButtonXpath));
        this.driver.findElement(doneButtonXpath).click();
    }

    public void openContentPage() {
        contentPage.open("/editor.html/content/wknd-cucumber/us/en/Hello-Name.html");
    }

    public void assertPageTitle() {
        String pageTitle = contentPage.getPageTitle();
        Assert.assertEquals("Hello-World", pageTitle);
    }

    public void deletePage(String pageTitle) throws InterruptedException {
        open();
        selectEnglishSite();
        contentPage.deletePage("Hello-World");
    }
}
