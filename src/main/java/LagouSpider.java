import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.Driver;
import java.util.List;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:用java代码启动jar包，在运行webdriver最后操纵浏览器
 */
public class LagouSpider {
    //psvm
    public static void main(String[] args) {
        //1.让环境变量找到webdriver(设置webdriver路径）
        System.setProperty("webdriver.chrome.driver", LagouSpider.class.getClassLoader().getResource("chromedriver.exe").getPath());

        //2.创建webDriver
        WebDriver webDriver = new ChromeDriver();
        //3.打开浏览器地址
        webDriver.get("https://www.lagou.com/zhaopin/Java/?labelWords=label");

        //模拟各种要求的点击
    //    clickOption(webDriver, "工作经验", "应届毕业生");
  //      clickOption(webDriver, "学历", "本科");
        clickOption(webDriver, "融资阶段", "天使轮");
        clickOption(webDriver, "行业领域", "移动互联网");

        //5.解析页面元素 获取信息findElements 获取很多标签
        extractJobsByPagination(webDriver);
    }

    /*分页获取页面元素*/
    private static void extractJobsByPagination(WebDriver webDriver) {
        List<WebElement> jobElements = webDriver.findElements(By.className("con_list_item"));
        for (WebElement jobElement : jobElements) {
            WebElement moneyElement = jobElement.findElement(By.className("position")).findElement(By.className("money"));
            String companyName = jobElement.findElement(By.className("company_name")).getText();
            System.out.println(companyName+" :  "+moneyElement.getText());
        }

        WebElement pagerNext = webDriver.findElement(By.className("pager_next"));
        if(!pagerNext.getAttribute("class").contains("pager_next_disabled")){
            pagerNext.click();
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            extractJobsByPagination(webDriver);
        }
    }

    /*模拟点击某一个标签*/
    private static void clickOption(WebDriver webDriver, String chosenTitle, String optionTitle) {
        //获取某一个标签findElement
        WebElement chosenElement = webDriver.findElement(By.xpath("//li[@class='multi-chosen']//span[contains(text(),'" + chosenTitle + "')]"));
        WebElement optionElement = chosenElement.findElement(By.xpath("../a[contains(text(),'" + optionTitle + "')]"));
        optionElement.click();
    }
}

        /*
        根据鼠标悬浮找到位置
        //4.[拿到dom结点] li里class属性multi-chosen并且有个span标签文本信息包含工作经验  并且其兄弟节点（同一个父节点下的a标签文本内容为应届毕业生  模拟点击
        WebElement chosenElement = webDriver.findElement(By.xpath("//li[@class='multi-chosen']//span[contains(text(),'工作经验')]"));
        WebElement optionElement = chosenElement.findElement(By.xpath("../a[contains(text(),'应届毕业生')]"));
        optionElement.click();
        发现成功，抽成变量即可 ctrl+alt+m/n/v

        5.$(".pager_next ").click();模拟点击下一页
        */