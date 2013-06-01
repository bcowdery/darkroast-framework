package com.darkroast.config;

import java.math.BigDecimal;

/**
 * ApplicationConfig
 *
 * @author Brian Cowdery
 * @since 31-05-2013
 */
public interface ApplicationConfig {

    public Environment getEnvironment();

    public String getString(String key);
    public String getString(String key, String defaultValue);
    public int getInt(String key);
    public int getInt(String key, int defaultValue);
    public double getDouble(String key);
    public double getDouble(String key, double defaultValue);
    public BigDecimal getBigDecimal(String key);
    public BigDecimal getBigDecimal(String key, BigDecimal defaultValue);
    public boolean getBoolean(String key);
    public boolean getBoolean(String key, boolean defaultValue);
}