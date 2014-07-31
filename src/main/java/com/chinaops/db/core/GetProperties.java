package com.chinaops.db.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class GetProperties {
	private static Properties config = null;

	static {
		InputStream in = GetProperties.class.getClassLoader().getResourceAsStream("config.properties");
		config = new Properties();
		try {
			config.load(in);
			in.close();
		} catch (IOException e) {
			System.out.println("No AreaPhone.properties defined error");
		}
	}

	// 根据key读取value
	public static String readValue(String key) {
		// Properties props = new Properties();
		try {
			String value = config.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ConfigInfoError" + e.toString());
			return null;
		}
	}

	// 读取properties的全部信息
	public static void readAllProperties() {
		try {
			Enumeration en = config.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = config.getProperty(key);
				System.out.println(key + " = " + Property);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ConfigInfoError" + e.toString());
		}
	}

	public static void main(String args[]) {
		// String LaSaPhone=config.getProperty("LaSaPhone");
		// System.out.println(LaSaPhone);
		// System.out.println(getPhone.readValue("LaSaPhone"));
		System.out.println(GetProperties.readValue("Y_RACENTER_URL"));
		System.out.println("***********************************************************");
		GetProperties.readAllProperties();
	}
}
