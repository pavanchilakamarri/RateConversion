package com.main.configurations;

import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class Config {

	private static Config instance;
	private TestConfig testConfig;
	private CucumberConfig cucumberConfig;

	private Config() {
	}

	public TestConfig getTestConfig() {
		return testConfig;
	}

	public void setTestConfig(TestConfig testConfig) {
		this.testConfig = testConfig;
	}

	public CucumberConfig getCucumberConfig() {
		return cucumberConfig;
	}

	public void setCucumberConfig(CucumberConfig cucumberConfig) {
		this.cucumberConfig = cucumberConfig;
	}

	   public static synchronized Config getInstance() {
	        if (instance == null) {
	            synchronized (Config.class) {
	                if (instance == null) {

	                    Yaml yaml = new Yaml(new Constructor(Config.class));
	                    InputStream inputStream = Config.class
	                            .getClassLoader()
	                            .getResourceAsStream("test-properties.yaml");
	                    instance = (Config) yaml.load(inputStream);

	                }
	            }
	        }
	        return instance;
	    }

}
