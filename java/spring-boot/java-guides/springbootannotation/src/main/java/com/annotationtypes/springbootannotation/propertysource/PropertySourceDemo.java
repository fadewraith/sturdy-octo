package com.annotationtypes.springbootannotation.propertysource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PropertySourceDemo {

    @Autowired
    private Environment environment;

//    @Value("${test.host}")
    private String host;
//    @Value("${test.email}")
    private String email;
//    @Value("${test.password}")
    private String password;

//    @Value("${app.name}")
    private String appName;
//    @Value("${app.description}")
    private String appDescription;

    public String getAppName() {
//        return appName;
        return environment.getProperty("app.name");
    }

    public String getAppDescription() {
//        return appDescription;
        return environment.getProperty("app.description");
    }

    public String getHost() {
//        return host;
        return environment.getProperty("test.host");
    }

    public String getEmail() {
//        return email;
        return environment.getProperty("test.email");
    }


    public String getPassword() {
//        return password;
        return environment.getProperty("test.password");
    }
}
