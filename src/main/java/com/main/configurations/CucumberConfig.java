package com.main.configurations;

import java.util.Optional;

public class CucumberConfig {

	private String features;
	private String glue;
	private String plugins;
	private boolean monochrome;
    private boolean dryRun;
    private boolean strict;
   private String reportConfigPath;

	public String getFeatures() {
		Optional.ofNullable(features).orElseThrow(() -> new RuntimeException("Feature is not defined in CucumberConfig"));
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getGlue() {
		Optional.ofNullable(glue).orElseThrow(() -> new RuntimeException("Glue is not defined in CucumberConfig"));
		return glue;
	}

	public void setGlue(String glue) {
		this.glue = glue;
	}

	public boolean getMonochrome() {
	monochrome=	Optional.ofNullable(monochrome).orElse(false);
		return monochrome;
	}

	public void setMonochrome(boolean monochrome) {
		this.monochrome = monochrome;
	}

	public boolean getDryRun() {
		dryRun=Optional.ofNullable(dryRun).orElse(false);
		return dryRun;
	}

	public void setDryRun(boolean dryRun) {
		this.dryRun = dryRun;
	}

	public boolean getStrict() {
		strict=Optional.ofNullable(strict).orElse(false);
		return strict;
	}

	public void setStrict(boolean strict) {
		this.strict = strict;
	}

	public String getPlugins() {
		plugins=Optional.ofNullable(plugins).orElse("");
		return plugins;
	}

	public void setPlugins(String plugin) {
		this.plugins = plugin;
	}

	public String getReportConfigPath() {
		return reportConfigPath;
	}

	public void setReportConfigPath(String reportConfigPath) {
		this.reportConfigPath = reportConfigPath;
	}

}
