package com.tcl.tcloud.base.appschedule.config;

import com.tcl.tcloud.base.appschedule.utils.PropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @ClassName PropertiesConfig
 * @Description TODO
 * @Author xiaosheng1.li
 **/
@Configuration
public class PropertiesConfig {
    @Autowired
    private Environment env;

    @Bean
    public PropertiesUtils propertiesUtils() {
        PropertiesUtils ymlConfigurerUtil = new PropertiesUtils(env);
        return ymlConfigurerUtil;
    }
}
