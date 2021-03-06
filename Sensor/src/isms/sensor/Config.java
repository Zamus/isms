package isms.sensor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	private static final Properties properties;

	static {
		properties = new Properties();
		InputStream in = ClassLoader.class.getResourceAsStream("/app.properties");

		if (in != null) {
			try {
				properties.load(in);
			} catch (IOException e) {
				throw new RuntimeException("Could not load properties from app.properties file.", e);
			}
		}
	}

	public static String get(String key) {
		return properties.getProperty(key);
	}
}
