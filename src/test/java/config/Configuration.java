package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:configurations/config.properties")
public interface Configuration extends Config{
    String baseUrl();
    String createUrl();
    int lowerLimitSizeImportantNumbers();
    int upperLimitSizeImportantNumbers();
    int lowerNumber();
    int upperNumber();
}
