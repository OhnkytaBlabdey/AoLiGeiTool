package org.ohnkyta.AoLiGeiTool.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	static Properties pps = null;

	protected static void init() throws IOException {
		pps = new Properties();
		pps.load(new FileInputStream("AoLiGei.properties"));
	}

	public static String get(String key) {
		if (pps == null) {
			try {
				init();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
		return pps.getProperty(key);
	}
}
