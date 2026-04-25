package com.haritonov.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:configurations/config.properties")
public interface Configuration extends Config{
    String baseUrl();
    String createUrl();
    String getUrl();
    int lowerLimitSizeImportantNumbers();
    int upperLimitSizeImportantNumbers();
    int lowerNumber();
    int upperNumber();
}
