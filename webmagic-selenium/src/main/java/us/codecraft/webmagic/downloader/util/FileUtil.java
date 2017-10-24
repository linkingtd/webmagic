package us.codecraft.webmagic.downloader.util;

import java.io.IOException;
import java.util.Properties;

public class FileUtil {
	private static Properties p = new Properties();

	/**
	 * 读取properties配置文件信息
	 */
	static {
		try {
			p.load(FileUtil.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据key得到value的值
	 */
	public static String getCommonProp(String key) {
		return p.getProperty(key);
	}
}
