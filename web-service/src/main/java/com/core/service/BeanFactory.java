package com.core.service;

import org.springframework.context.ApplicationContext;

import java.util.Objects;

public interface BeanFactory {
    class Impl {
        private static ApplicationContext context;

        public static synchronized ApplicationContext getInstance() {
            if(Objects.isNull(context)) {
                //context = ApplicationProperties.applicationContext;
            }

            return context;
        }
        public static Object getBean(Class beanName) {
            ApplicationContext context = getInstance();
            return context.getBean(beanName.getSimpleName());
        }
    }
}
