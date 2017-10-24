package us.codecraft.webmagic.downloader.selenium;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class TestDownloader implements PageProcessor {

    private Site site;
    private static WebDriverPool pool=new WebDriverPool();

    @Override
    public void process(Page page) {
    	Html html = page.getHtml();
    	System.out.println(html.toString());
    }

    @Override
    public Site getSite() {
        if (null == site) {
            site = Site.me().setSleepTime(0);
        }
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new TestDownloader())
        		.thread(1)
                .setDownloader(new SeleniumDownloader(3000,pool))
                //.addUrl("https://item.taobao.com/")  http://huaban.com/
                .addUrl("http://news.xinhuanet.com/politics/2017-10/16/c_1121811662.htm")
                .run();
        pool.shutdown();
    }
}
