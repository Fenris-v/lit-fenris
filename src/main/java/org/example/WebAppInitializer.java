package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.app.config.AppContextConfig;
import org.example.web.config.WebContextConfig;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {
    Logger logger = LogManager.getRootLogger();

    @Override
    public void onStartup(@NotNull javax.servlet.ServletContext servletContext) {
        logger.info("loading app config");
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(AppContextConfig.class);
        servletContext.addListener(new ContextLoaderListener(appContext));

        logger.info("loading web config");
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(WebContextConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        logger.info("dispatcher");
    }
}
