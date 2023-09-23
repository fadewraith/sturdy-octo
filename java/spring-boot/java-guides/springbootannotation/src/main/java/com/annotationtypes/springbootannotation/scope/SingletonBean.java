package com.annotationtypes.springbootannotation.scope;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
//@Scope(value = "singleton")
//@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON) // by default spring will provide this
public class SingletonBean {

    public SingletonBean() {
        System.out.println("SingletonBean");
    }
}
