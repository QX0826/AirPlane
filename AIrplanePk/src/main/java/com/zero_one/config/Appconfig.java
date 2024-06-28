package com.zero_one.config;

/**
 * @Description: 配置文件加载
 * @Author: QX
 * @Date: 2024-06-22 15:45
 */

import java.io.FileInputStream;
import java.util.Objects;
import java.util.Properties;

public class Appconfig {
    /**
     * 资源文件路径
     */
    private static final String RESOURCE_PATH = Objects.requireNonNull(Appconfig.class.getClassLoader().getResource("").getPath());
    /**
     * config配置文件路径
     */
    private static final String CONFIG_PATH = RESOURCE_PATH.concat("config.properties");
    /**
     * 读取配置文件初始化
     */
    private static Properties properties;

    static {
        FileInputStream fis = null;
        try {
            properties = new Properties();
            fis = new FileInputStream(CONFIG_PATH);
            properties.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取配置文件中的值
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}