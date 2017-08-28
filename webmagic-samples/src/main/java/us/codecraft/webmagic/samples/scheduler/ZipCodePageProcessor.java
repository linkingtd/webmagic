package us.codecraft.webmagic.samples.scheduler;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.PriorityScheduler;
import us.codecraft.webmagic.selector.Selectable;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static us.codecraft.webmagic.selector.Selectors.xpath;

/**
 * @author code4crafter@gmail.com
 */
public class ZipCodePageProcessor implements PageProcessor {

    private Site site = Site.me()
    		.setCharset("gb2312")
    		.setTimeOut(5000)
    		.setDomain("http://www.ip138.com")
    		.setRetryTimes(3)
    		.setUserAgent("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0;")
            .setSleepTime(1000);

    @Override
    public void process(Page page) {
        if (page.getUrl().toString().equals("http://www.ip138.com/post/")) {
        	//1
            processCountry(page);
        } else if (page.getUrl().regex("http://www\\.ip138\\.com/\\d{6}[/]?$").toString() != null) {
        	//3
            processDistrict(page);
            //2
        } else {
            processProvince(page);
        }

    }

    private void processCountry(Page page) {
        List<String> provinces = page.getHtml().xpath("//*[@id=\"newAlexa\"]/table/tbody/tr/td").all();
        for (String province : provinces) {
            String link = page.getRequest().getExtra("baseUrl") + xpath("a/@href").select(province);
            String title = xpath("a/text()").select(province);
            Request request = new Request(link).setPriority(0).putExtra("province", title).putExtra("baseUrl", "http://www.ip138.com");
            page.addTargetRequest(request);
        }
    }

    private void processDistrict(Page page) {
        String province = page.getRequest().getExtra("province").toString();
        String district = page.getRequest().getExtra("district").toString();
        String zipCode = page.getHtml().xpath("/html/body/table[4]/tbody/tr[2]/td[2]/a/text()").toString();
        page.putField("result", StringUtils.join(new String[]{province, district,zipCode}, "\t"));
        
        try {
//        	FileUtils.writeLines(file, encoding, lines);
//			FileUtils.writeStringToFile(new File("e:\\youbian.txt"), StringUtils.join(new String[]{province, district,zipCode}, "\t"), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        List<String> links = page.getHtml().links().regex("http://www\\.ip138\\.com/\\d{6}[/]?$").all();
        for (String link : links) {
            page.addTargetRequest(new Request(link).setPriority(2).putExtra("province", province).putExtra("district", district).putExtra("baseUrl", "http://www.ip138.com"));
        }

    }
    
    private void processProvince(Page page) {
        //这里仅靠xpath没法精准定位，所以使用正则作为筛选，不符合正则的会被过滤掉
        List<String> districts = page.getHtml().xpath("//body/table/tbody/tr[@bgcolor=\"#ffffff\"]").all();
        Pattern pattern = Pattern.compile("<td>([^<>]+)</td>.*?href=\"(.*?)\"",Pattern.DOTALL);
        for (String district : districts) {
            Matcher matcher = pattern.matcher(district);
            while (matcher.find()) {
                String title = matcher.group(1);
                String link = page.getRequest().getExtra("baseUrl") + matcher.group(2);
                Request request = new Request(link).setPriority(1).putExtra("province", page.getRequest().getExtra("province")).putExtra("district", title);
                page.addTargetRequest(request);
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
    	Request request = new Request("http://www.ip138.com/post/");
    	request.putExtra("baseUrl", "http://www.ip138.com");
        Spider spider = Spider.create(new ZipCodePageProcessor())
//        		.scheduler(new PriorityScheduler())
        		.setScheduler(new PriorityScheduler())
//        		.addUrl("http://www.ip138.com/post/")
        		.addRequest(request)
        		;
        spider.run();
    }
}
