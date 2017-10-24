package us.codecraft.webmagic.processor;

import junit.framework.Assert;
import org.junit.Test;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.downloader.MockGithubDownloader;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author code4crafter@gmail.com
 */
public class GithubRepoProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
    	//*[@id='js-repo-pjax-container']/div[1]/div/ul/li[2]/div/form[1]/a
    	//*[@id='js-repo-pjax-container']/div[1]/div/ul/li[3]/a
    	
    	System.out.println(page.getHtml());
//        page.putField("star",page.getHtml().xpath("//ul[@class='pagehead-actions']/li[2]//a[@class='social-count js-social-count']/text()").toString());
//        page.putField("fork",page.getHtml().xpath("//ul[@class='pagehead-actions']/li[3]//a[@class='social-count']/text()").toString());
        
        page.putField("star",page.getHtml().xpath("//*[@id=\"js-repo-pjax-container\"]/div[1]/div/ul/li[2]/div/form[1]/a/text()").toString());
        page.putField("fork",page.getHtml().xpath("//*[@id=\"js-repo-pjax-container\"]/div[1]/div/ul/li[3]/a/text()").toString());
        
        
    }

    @Override
    public Site getSite() {
        return Site.me();
    }

    @Test
    public void test() {
        OOSpider.create(new GithubRepoProcessor()).addPipeline(new Pipeline() {
            @Override
            public void process(ResultItems resultItems, Task task) {
                Assert.assertEquals("5,448",((String)resultItems.get("star")).trim());
                Assert.assertEquals("2,733",((String)resultItems.get("fork")).trim());
            }
        })
        //.setDownloader(new MockGithubDownloader())
        .test("https://github.com/code4craft/webmagic");
    }

}
