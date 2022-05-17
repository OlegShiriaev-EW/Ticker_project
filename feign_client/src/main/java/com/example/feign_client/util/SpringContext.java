package com.example.feign_client.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {
    private static ApplicationContext aplicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.aplicationContext = applicationContext;
    }

    public static <T extends Object>T getBean(Class<T> beanClass) {
        return aplicationContext.getBean(beanClass);
    }
}
